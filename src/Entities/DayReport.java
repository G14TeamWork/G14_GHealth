package Entities;

import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ocsf.server.GHealthServer;
/**
 * entity class. suppose to have all fields that might be sent to or from server in production of reports.
 * being sent from and to client/server
 * 
 * @author Ruslan
 *
 */
public class DayReport implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Date date;
	private int idClinic;
	private long numOfMiss;
	private int numOfPatientsTreated;
	private long maxFromDisToAppDateDiffInMinutes;
	private long minFromDisToAppDateDiffInMinutes;
	private long avgFromDisToAppDateDiffInMinutes;
	private long sdFromDisToAppDateDiffInMinutes;
	private long maxFromAppDateToRealAppDateDiffInMinutes;
	private long minFromAppDateToRealAppDateDiffInMinutes;
	private long avgFromAppDateToRealAppDateDiffInMinutes;
	private long sdFromAppDateToRealAppDateDiffInMinutes;
	private ArrayList<AppointmentTimeValues> dayValues;
	
	public DayReport()
	{
		
	}
	
	public DayReport(Date date , int idclinic)
	{
		this.date = date;
		this.idClinic = idclinic;
	}
	
	public DayReport createDayliReport(Date date , int idclinic)
	{
		
		this.date = date;
		this.idClinic = idclinic;
		ArrayList<Object> arrList = new ArrayList<Object>();
		ArrayList<Object> arrList2 = new ArrayList<Object>();
		AppointmentTimeValues current;
		
		String query2 ="SELECT COUNT(*) FROM ghealth.appointments where  `appstatus`='3' AND `appdate` BETWEEN "+generateDayDateToSql(date)+" AND "+generateDayDateToSql(date)+";";
		arrList2 = GHealthServer.sqlConn.sendSqlQuery(query2);
		this.numOfMiss = (long)arrList2.get(0);
		
		String query ="SELECT app.dispatcherSettingDate,app.dispatcherSettingHour,app.appdate,app.start,app.end,app.realStart,app.realEnd FROM ghealth.appointments as app where app.idclinic = "+String.valueOf(idclinic)+" AND app.appdate="+generateDayDateToSql(date)+" AND app.appstatus = 2;";
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		
		if(!arrList.isEmpty())
		{
				this.numOfPatientsTreated = arrList.size()/7;
				dayValues = new ArrayList<AppointmentTimeValues>();
				for(int i = 0 ; i < arrList.size() ; i+=7)
					dayValues.add(new AppointmentTimeValues((Date)arrList.get(i),(Time)arrList.get(i+1),(Date)arrList.get(i+2),(Time)arrList.get(i+3),(Time)arrList.get(i+4) ,(Time)arrList.get(i+5),(Time)arrList.get(i+6)));
			
			minFromDisToAppDateDiffInMinutes = maxFromDisToAppDateDiffInMinutes = dayValues.get(0).getFromDisToAppDateDiffInMinutes();
			minFromAppDateToRealAppDateDiffInMinutes = maxFromAppDateToRealAppDateDiffInMinutes = dayValues.get(0).getFromAppDateToRealAppDateDiffInMinutes();
			sdFromDisToAppDateDiffInMinutes = avgFromDisToAppDateDiffInMinutes = 0;
			sdFromAppDateToRealAppDateDiffInMinutes = avgFromAppDateToRealAppDateDiffInMinutes = 0;
	
			for (int i  = 0 ; i < dayValues.size() ; i++)
			{
				current = dayValues.get(i);
				maxFromDisToAppDateDiffInMinutes = maxFromDisToAppDateDiffInMinutes >= current.getFromDisToAppDateDiffInMinutes() ? 
						maxFromDisToAppDateDiffInMinutes : current.getFromDisToAppDateDiffInMinutes();
				maxFromAppDateToRealAppDateDiffInMinutes = maxFromAppDateToRealAppDateDiffInMinutes >= current.getFromAppDateToRealAppDateDiffInMinutes() ?
						maxFromAppDateToRealAppDateDiffInMinutes : current.getFromAppDateToRealAppDateDiffInMinutes();
				minFromDisToAppDateDiffInMinutes = minFromDisToAppDateDiffInMinutes <= current.getFromDisToAppDateDiffInMinutes() ? 
						minFromDisToAppDateDiffInMinutes : current.getFromDisToAppDateDiffInMinutes() ;
				minFromAppDateToRealAppDateDiffInMinutes = minFromAppDateToRealAppDateDiffInMinutes <= current.getFromAppDateToRealAppDateDiffInMinutes() ?
						minFromAppDateToRealAppDateDiffInMinutes : current.getFromAppDateToRealAppDateDiffInMinutes();
				avgFromDisToAppDateDiffInMinutes += current.getFromDisToAppDateDiffInMinutes();
				avgFromAppDateToRealAppDateDiffInMinutes +=current.getFromAppDateToRealAppDateDiffInMinutes();	
			}
			
			avgFromDisToAppDateDiffInMinutes = (avgFromDisToAppDateDiffInMinutes /(long)numOfPatientsTreated);
			avgFromAppDateToRealAppDateDiffInMinutes = (avgFromAppDateToRealAppDateDiffInMinutes /(long)numOfPatientsTreated);
				
			
			for (int i  = 0 ; i < dayValues.size() ; i++)
			{
				current = dayValues.get(i);
				sdFromDisToAppDateDiffInMinutes += Math.pow(current.getFromDisToAppDateDiffInMinutes() - avgFromDisToAppDateDiffInMinutes,(long)2.0);
				sdFromAppDateToRealAppDateDiffInMinutes += Math.pow(current.getFromAppDateToRealAppDateDiffInMinutes() - avgFromAppDateToRealAppDateDiffInMinutes,(long)2.0);
			}
			
			sdFromDisToAppDateDiffInMinutes = (sdFromDisToAppDateDiffInMinutes/(long)(numOfPatientsTreated));
			sdFromDisToAppDateDiffInMinutes =  (long)Math.sqrt(sdFromDisToAppDateDiffInMinutes);
			
			sdFromAppDateToRealAppDateDiffInMinutes = (sdFromAppDateToRealAppDateDiffInMinutes/(long)(numOfPatientsTreated));
			sdFromAppDateToRealAppDateDiffInMinutes =  (long)Math.sqrt(sdFromAppDateToRealAppDateDiffInMinutes);
			
			
			query2 = "SELECT idclinic FROM ghealth.daylireport as d WHERE d.idclinic = "+String.valueOf(idClinic)+" AND d.date = "+generateDayDateToSql(date)+";";
			arrList = GHealthServer.sqlConn.sendSqlQuery(query2);
			if(arrList.size() == 0)
			{
			query2 = "INSERT INTO `ghealth`.`daylireport` (`idclinic`, `date`, `numofmiss`, `numoftreted`, `maxdistoapp`, `mindistoapp`, `avgdistoapp`, `sddistoapp`, `maxapptoreal`, `minapptoreal`, `avgapptoreal`, `sdapptoreal`)"
														+ " VALUES ("+String.valueOf(idClinic)+","+generateDayDateToSql(date)+","+String.valueOf(numOfPatientsTreated)+","+String.valueOf(numOfMiss)+","+String.valueOf(maxFromDisToAppDateDiffInMinutes)+","+String.valueOf(minFromDisToAppDateDiffInMinutes)+","+String.valueOf(avgFromDisToAppDateDiffInMinutes)+","+String.valueOf(sdFromDisToAppDateDiffInMinutes)+", "+String.valueOf(maxFromAppDateToRealAppDateDiffInMinutes)+", "+String.valueOf(minFromAppDateToRealAppDateDiffInMinutes)+", "+String.valueOf(avgFromAppDateToRealAppDateDiffInMinutes)+", "+String.valueOf(sdFromAppDateToRealAppDateDiffInMinutes)+");";
			
			GHealthServer.sqlConn.sendSqlUpdate(query2);
			}
			return this;
		}
		return null;
	}
	
	public String generateDayDateToSql(Date date)
	{
		
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}
	
	public String generateDiffToHoursDaysMinutes(long allTimeInMinutes)
	{
		/*
		 * days = allTimeInMinutes/24*60;
		 * hours = (allTimeInMinutes%24*60)/60;
		 * Minutes = allTimeInMinutes%60;
		 * */
	/*	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime date= LocalDateTime.parse(getDate().toString(), formatter); 
		*/
		long days = allTimeInMinutes/(24*60);
		long hours = (allTimeInMinutes%(24*60))/60;
		long minutes = allTimeInMinutes%60;
		String str="";
		str += date.toString() + " : ";
		str += String.valueOf(days)+" d ";
		str += String.valueOf(hours)+" h ";
		str += String.valueOf(minutes)+" m \n";
		
		
		return str;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getNumOfPatientsTreated() {
		return numOfPatientsTreated;
	}

	public void setNumOfPatientsTreated(int numOfPatientsTreated) {
		this.numOfPatientsTreated = numOfPatientsTreated;
	}

	public long getMaxFromDisToAppDateDiffInMinutes() {
		return maxFromDisToAppDateDiffInMinutes;
	}

	public void setMaxFromDisToAppDateDiffInMinutes(
			long maxFromDisToAppDateDiffInMinutes) {
		this.maxFromDisToAppDateDiffInMinutes = maxFromDisToAppDateDiffInMinutes;
	}

	public long getMinFromDisToAppDateDiffInMinutes() {
		return minFromDisToAppDateDiffInMinutes;
	}

	public void setMinFromDisToAppDateDiffInMinutes(
			long minFromDisToAppDateDiffInMinutes) {
		this.minFromDisToAppDateDiffInMinutes = minFromDisToAppDateDiffInMinutes;
	}

	public long getAvgFromDisToAppDateDiffInMinutes() {
		return avgFromDisToAppDateDiffInMinutes;
	}

	public void setAvgFromDisToAppDateDiffInMinutes(
			long avgFromDisToAppDateDiffInMinutes) {
		this.avgFromDisToAppDateDiffInMinutes = avgFromDisToAppDateDiffInMinutes;
	}

	public long getSdFromDisToAppDateDiffInMinutes() {
		return sdFromDisToAppDateDiffInMinutes;
	}

	public void setSdFromDisToAppDateDiffInMinutes(
			long sdFromDisToAppDateDiffInMinutes) {
		this.sdFromDisToAppDateDiffInMinutes = sdFromDisToAppDateDiffInMinutes;
	}

	public long getMaxFromAppDateToRealAppDateDiffInMinutes() {
		return maxFromAppDateToRealAppDateDiffInMinutes;
	}

	public void setMaxFromAppDateToRealAppDateDiffInMinutes(
			long maxFromAppDateToRealAppDateDiffInMinutes) {
		this.maxFromAppDateToRealAppDateDiffInMinutes = maxFromAppDateToRealAppDateDiffInMinutes;
	}

	public long getMinFromAppDateToRealAppDateDiffInMinutes() {
		return minFromAppDateToRealAppDateDiffInMinutes;
	}

	public void setMinFromAppDateToRealAppDateDiffInMinutes(
			long minFromAppDateToRealAppDateDiffInMinutes) {
		this.minFromAppDateToRealAppDateDiffInMinutes = minFromAppDateToRealAppDateDiffInMinutes;
	}

	public long getAvgFromAppDateToRealAppDateDiffInMinutes() {
		return avgFromAppDateToRealAppDateDiffInMinutes;
	}

	public void setAvgFromAppDateToRealAppDateDiffInMinutes(
			long avgFromAppDateToRealAppDateDiffInMinutes) {
		this.avgFromAppDateToRealAppDateDiffInMinutes = avgFromAppDateToRealAppDateDiffInMinutes;
	}

	public long getSdFromAppDateToRealAppDateDiffInMinutes() {
		return sdFromAppDateToRealAppDateDiffInMinutes;
	}

	public void setSdFromAppDateToRealAppDateDiffInMinutes(
			long sdFromAppDateToRealAppDateDiffInMinutes) {
		this.sdFromAppDateToRealAppDateDiffInMinutes = sdFromAppDateToRealAppDateDiffInMinutes;
	}

	public ArrayList<AppointmentTimeValues> getDayValues() {
		return dayValues;
	}

	public void setDayValues(ArrayList<AppointmentTimeValues> dayValues) {
		this.dayValues = dayValues;
	}

	@Override
	public String toString() {
		
		String str = date.toString() + " : "
					+"A : \n\tMax :"+generateDiffToHoursDaysMinutes(getMaxFromDisToAppDateDiffInMinutes())
					+"\n\tMin :"+generateDiffToHoursDaysMinutes(getMinFromDisToAppDateDiffInMinutes())
					+"\n\tAvg :"+generateDiffToHoursDaysMinutes(getAvgFromDisToAppDateDiffInMinutes())
					+"\n\tSd :"+generateDiffToHoursDaysMinutes(getSdFromDisToAppDateDiffInMinutes())
					+"B : \n\tMax :"+ generateDiffToHoursDaysMinutes(getMaxFromAppDateToRealAppDateDiffInMinutes())
					+"\n\tMin :"+generateDiffToHoursDaysMinutes(getMinFromAppDateToRealAppDateDiffInMinutes())
					+"\n\tAvg :"+generateDiffToHoursDaysMinutes(getAvgFromAppDateToRealAppDateDiffInMinutes())
					+"\n\tSd :"+generateDiffToHoursDaysMinutes(getSdFromAppDateToRealAppDateDiffInMinutes());
			
		return  str ;
	}

	public long getNumOfMiss() {
		return numOfMiss;
	}

	public void setNumOfMiss(long numOfMiss) {
		this.numOfMiss = numOfMiss;
	}
	
}
