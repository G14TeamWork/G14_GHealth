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
 * entity class. suppose to have all fields that might be sent to or from server in producing monthly reports.
 * being sent from and to client/server
 * 
 * @author Ruslan
 *
 */
public class WeekReport implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String massage;
	private Date date;
	private int idClinic;
	private long numOfMiss;
	private int numOfPatientsTreated = 0;
	private long maxNumOfPatientsTreated;
	private long minNumOfPatientsTreated;
	private long avgNumOfPatientsTreated;
	private long sdNumOfPatientsTreated;
	private long maxFromDisToAppDateDiffInMinutes;
	private long minFromDisToAppDateDiffInMinutes;
	private long avgFromDisToAppDateDiffInMinutes;
	private long sdFromDisToAppDateDiffInMinutes;
	private long maxFromAppDateToRealAppDateDiffInMinutes;
	private long minFromAppDateToRealAppDateDiffInMinutes;
	private long avgFromAppDateToRealAppDateDiffInMinutes;
	private long sdFromAppDateToRealAppDateDiffInMinutes;
	private ArrayList<DayReport> allDaysReport;
	private ArrayList<AppointmentTimeValues> allWeekAppointments;
	
	
	public WeekReport()
	{
		massage = "empty";
	}
	
	public WeekReport createWeeklyReport(Date date , int idclinic)
	{
		this.date = date;
		this.idClinic = idclinic;
		this.allDaysReport = new ArrayList<DayReport>();
		this.allWeekAppointments = new ArrayList<AppointmentTimeValues>();
		ArrayList<Object> arrList = new ArrayList<Object>();
		ArrayList<Object> arrList2 = new ArrayList<Object>();
		AppointmentTimeValues currentAppVal;
		DayReport currentDayVal = new DayReport();
		Date specific = new Date();
		long dateInLong = date.getTime();
		specific = date;
		this.massage = "not empty";
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int daysInMonth = c.getActualMaximum(Calendar.DATE);		
		
		String query2 ="SELECT COUNT(*) FROM ghealth.appointments where  `appstatus`='3' AND ghealth.appointments.idclinic=" +String.valueOf(idclinic) + "  AND `appdate` BETWEEN "+generateDayDateToSql(date,0)+" AND "+generateDayDateToSql(date,6)+";";
		arrList2 = GHealthServer.sqlConn.sendSqlQuery(query2);
		if(arrList2 != null)
		this.numOfMiss = (long)arrList2.get(0);
		specific =date;
		
		boolean flag=true;//end of month
		for(int j = 0 ; j < 7 ; j++,specific = addDays(specific,1),currentDayVal = new DayReport())
		{
			
			if(flag)
			{
				if(daysInMonth==Integer.valueOf(new SimpleDateFormat("dd").format(specific)))
					flag=false;
				String query ="SELECT app.dispatcherSettingDate,app.dispatcherSettingHour,app.appdate,app.start,app.end,app.realStart,app.realEnd FROM ghealth.appointments as app "
						+ "WHERE app.idclinic = "+String.valueOf(idclinic)+" AND app.appdate = "+generateDayDateToSql(date,j)+ " AND app.appstatus = 2;";
				arrList = GHealthServer.sqlConn.sendSqlQuery(query);
			//	if(arrList.size() == 0) return null;
				currentDayVal = currentDayVal.createDayliReport(specific,idclinic);
				if( currentDayVal.getMassage().equals( "not empty"))
				{		
							allDaysReport.add(currentDayVal);
						
						dateInLong +=24 * 60 * 60 * 1000;
						
						for(int i = 0 ; i < arrList.size() ; i+=7)
						{
							numOfPatientsTreated++;
							allWeekAppointments.add(new AppointmentTimeValues((Date)arrList.get(i),(Time)arrList.get(i+1),(Date)arrList.get(i+2),(Time)arrList.get(i+3),(Time)arrList.get(i+4) ,(Time)arrList.get(i+5),(Time)arrList.get(i+6)));
							
					
						}
				}
			}
		}

		if(allDaysReport.size() == 0 ) return new WeekReport();
		sdFromDisToAppDateDiffInMinutes = avgFromDisToAppDateDiffInMinutes = 0;
		sdFromAppDateToRealAppDateDiffInMinutes = avgFromAppDateToRealAppDateDiffInMinutes = 0;
		sdNumOfPatientsTreated = avgNumOfPatientsTreated = 0;
		
		maxFromDisToAppDateDiffInMinutes = allDaysReport.get(0).getMaxFromDisToAppDateDiffInMinutes();
		minFromDisToAppDateDiffInMinutes = allDaysReport.get(0).getMinFromDisToAppDateDiffInMinutes();
		maxFromAppDateToRealAppDateDiffInMinutes = allDaysReport.get(0).getMaxFromAppDateToRealAppDateDiffInMinutes();
		minFromAppDateToRealAppDateDiffInMinutes = 	allDaysReport.get(0).getMinFromAppDateToRealAppDateDiffInMinutes();	
		maxNumOfPatientsTreated = minNumOfPatientsTreated = allDaysReport.get(0).getNumOfPatientsTreated();
		
		
		for(int i = 0 ; i < allDaysReport.size() ; i++ )
		{
			currentDayVal = allDaysReport.get(i);
			maxFromDisToAppDateDiffInMinutes = maxFromDisToAppDateDiffInMinutes >= currentDayVal.getMaxFromDisToAppDateDiffInMinutes() ? 
					maxFromDisToAppDateDiffInMinutes : currentDayVal.getSdFromDisToAppDateDiffInMinutes();
			maxFromAppDateToRealAppDateDiffInMinutes = maxFromAppDateToRealAppDateDiffInMinutes >= currentDayVal.getMaxFromAppDateToRealAppDateDiffInMinutes() ?
					maxFromAppDateToRealAppDateDiffInMinutes : currentDayVal.getMaxFromAppDateToRealAppDateDiffInMinutes();
			minFromDisToAppDateDiffInMinutes = minFromDisToAppDateDiffInMinutes <= currentDayVal.getMinFromDisToAppDateDiffInMinutes() ? 
					minFromDisToAppDateDiffInMinutes : currentDayVal.getMinFromDisToAppDateDiffInMinutes() ;
			minFromAppDateToRealAppDateDiffInMinutes = minFromAppDateToRealAppDateDiffInMinutes <= currentDayVal.getMinFromAppDateToRealAppDateDiffInMinutes() ?
					minFromAppDateToRealAppDateDiffInMinutes : currentDayVal.getMinFromAppDateToRealAppDateDiffInMinutes();
			maxNumOfPatientsTreated = maxNumOfPatientsTreated >= currentDayVal.getNumOfPatientsTreated() ?
					maxNumOfPatientsTreated : currentDayVal.getNumOfPatientsTreated();
			minNumOfPatientsTreated = minNumOfPatientsTreated <= currentDayVal.getNumOfPatientsTreated() ?
					minNumOfPatientsTreated : currentDayVal.getNumOfPatientsTreated();
			avgFromDisToAppDateDiffInMinutes += currentDayVal.getAvgFromDisToAppDateDiffInMinutes() * currentDayVal.getNumOfPatientsTreated() ;
			avgFromAppDateToRealAppDateDiffInMinutes += currentDayVal.getAvgFromAppDateToRealAppDateDiffInMinutes() * currentDayVal.getNumOfPatientsTreated();	
			avgNumOfPatientsTreated += allDaysReport.get(i).getNumOfPatientsTreated();
		}
		
		
		avgFromDisToAppDateDiffInMinutes = (avgFromDisToAppDateDiffInMinutes /(long)numOfPatientsTreated);
		avgFromAppDateToRealAppDateDiffInMinutes = (avgFromAppDateToRealAppDateDiffInMinutes /(long)numOfPatientsTreated);
		avgNumOfPatientsTreated = (avgNumOfPatientsTreated/(int)allDaysReport.size());
		

		for (int i  = 0 ; i < allDaysReport.size() ; i++)
		{
			currentDayVal = allDaysReport.get(i);
			sdNumOfPatientsTreated += Math.pow(currentDayVal.getNumOfPatientsTreated() - avgNumOfPatientsTreated,(long)2.0);
		}
		sdNumOfPatientsTreated = (sdNumOfPatientsTreated/(long)(numOfPatientsTreated));
		sdNumOfPatientsTreated =  (long)Math.sqrt(sdNumOfPatientsTreated);

		
		for (int i  = 0 ; i < allWeekAppointments.size() ; i++)
		{
			currentAppVal = allWeekAppointments.get(i);
			sdFromDisToAppDateDiffInMinutes += Math.pow(currentAppVal.getFromDisToAppDateDiffInMinutes() - avgFromDisToAppDateDiffInMinutes,(long)2.0);
			sdFromAppDateToRealAppDateDiffInMinutes += Math.pow(currentAppVal.getFromAppDateToRealAppDateDiffInMinutes() - avgFromAppDateToRealAppDateDiffInMinutes,(long)2.0);
		}
		sdFromDisToAppDateDiffInMinutes = (sdFromDisToAppDateDiffInMinutes/(long)(numOfPatientsTreated));
		sdFromDisToAppDateDiffInMinutes =  (long)Math.sqrt(sdFromDisToAppDateDiffInMinutes);
		sdFromAppDateToRealAppDateDiffInMinutes = (sdFromAppDateToRealAppDateDiffInMinutes/(long)(numOfPatientsTreated));
		sdFromAppDateToRealAppDateDiffInMinutes =  (long)Math.sqrt(sdFromAppDateToRealAppDateDiffInMinutes);
		
		
		query2 = "SELECT idclinic FROM ghealth.weeklyreport as d WHERE d.idclinic = "+String.valueOf(idClinic)+" AND d.date = "+generateDayDateToSql(date,0)+";";
		arrList = GHealthServer.sqlConn.sendSqlQuery(query2);
		if(arrList.size() == 0)
		{
		query2 =" INSERT INTO `ghealth`.`weeklyreport` (`idclinic`, `date`, `numofmiss`, `numoftreted`, `maxoftreted`, `minoftreted`, `avgoftreted`, `sdoftreted`, `maxdistoapp`, `mindistoapp`, `avgdistoapp`, `sddistoapp`, `maxapptoreal`, `minapptoreal`, `avgapptoreal`, `sdapptoreal`) "
				+ "VALUES ("+String.valueOf(idclinic)+", "+generateDayDateToSql(this.date,0)+", "+String.valueOf(numOfMiss)+","
				+ " "+String.valueOf(numOfPatientsTreated)+", "+String.valueOf(maxNumOfPatientsTreated)+","
				+ " "+String.valueOf(minNumOfPatientsTreated)+", "+String.valueOf(avgNumOfPatientsTreated)+", "+String.valueOf(sdNumOfPatientsTreated)+","
				+ " "+String.valueOf(maxFromDisToAppDateDiffInMinutes)+", "+String.valueOf(minFromDisToAppDateDiffInMinutes)+","
				+ " "+String.valueOf(avgFromDisToAppDateDiffInMinutes)+", "+String.valueOf(sdFromDisToAppDateDiffInMinutes)+", "+String.valueOf(maxFromAppDateToRealAppDateDiffInMinutes)+","
				+ " "+String.valueOf(minFromAppDateToRealAppDateDiffInMinutes)+", "+String.valueOf(avgFromAppDateToRealAppDateDiffInMinutes)+", "+String.valueOf(sdFromAppDateToRealAppDateDiffInMinutes)+");";
		GHealthServer.sqlConn.sendSqlUpdate(query2);
		}
		return this;
		
		
	}

	public String generateDiffToHoursDaysMinutesForWeekly(DayReport allDaysInWeek)
	{
		
		String str ="";

		for(int i = 0 ; i < allDaysReport.size() ; i++)
		{
			str += allDaysReport.get(i).getDate()+allDaysReport.get(i).generateDiffToHoursDaysMinutes(i)+" \n";
		}
		str += "\nnumber of patients treated : "+String.valueOf(numOfPatientsTreated)
				+"\nmax number of patients treated :"+String.valueOf(maxNumOfPatientsTreated)
				+"\nmin number of patients treated :"+String.valueOf(minNumOfPatientsTreated)
				+"\navg number of patients treated :"+String.valueOf(avgNumOfPatientsTreated)
				+"\nsd number of patients treated :"+String.valueOf(sdNumOfPatientsTreated);
		
		return str;
	}

	public String generateDiffToHoursDaysMinutesForWeekly(Date date)
	{
		/*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime date1= LocalDateTime.parse(date.toString(), formatter); 
		String str =date1.toString();
		long dateInLong = date.getTime();
		dateInLong +=7*24 * 60 * 60 * 1000;
		date.setTime(dateInLong);
		date1= LocalDateTime.parse(date.toString(), formatter);*/
		String str ="";
		//str +="-"+date1.toString();
		
		str += new SimpleDateFormat("dd/MM/yyyy").format(date)
				+"\nnumber of patients treated : "+String.valueOf(numOfPatientsTreated)
				+"\nmax number of patients treated :"+String.valueOf(maxNumOfPatientsTreated)
				+"\nmin number of patients treated :"+String.valueOf(minNumOfPatientsTreated)
				+"\navg number of patients treated :"+String.valueOf(avgNumOfPatientsTreated)
				+"\nsd number of patients treated :"+String.valueOf(sdNumOfPatientsTreated);
		
		return str;
	}
	
	public Date addDays(Date date,int inc)
	{
		if(inc != 0)
		{
			Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.add(Calendar.DATE, inc); //minus number would decrement the days
	        date = cal.getTime();
		}
		
        return date;
	}
	public String generateDiffToHoursDaysMinutes(long allTimeInMinutes)
	{
		/*
		 * days = allTimeInMinutes/24*60;
		 * hours = (allTimeInMinutes%24*60)/60;
		 * Minutes = allTimeInMinutes%60;
		 * */
	/*
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime date= LocalDateTime.parse(getDate().toString(), formatter); 
		*/
		long days = allTimeInMinutes/(24*60);
		long hours = (allTimeInMinutes%(24*60))/60;
		long minutes = allTimeInMinutes%60;
		String str="";
		str =new SimpleDateFormat("dd/MM/yyyy").format(date)+" :"
			+ String.valueOf(days)+" d "
			+ String.valueOf(hours)+" h "
			+ String.valueOf(minutes)+" m \n";
		
		return str;
	}
	public String generateDayDateToSql(Date date,int inc)
	{
		
		/*	Calendar cal  = Calendar.getInstance();
		cal.set(2016, 5, 20);
		Date date = cal.getTime();
		this.CME.setTaskToDo("createDailyReport");
		this.CME.setFrom(date);
		MainClass.ghealth.sendMessegeToServer(CME);*/
		if(inc != 0)
		{
			Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.add(Calendar.DATE, inc); //minus number would decrement the days
	        date = cal.getTime();
		}
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}
	
	@Override
	public String toString() {
 
		String str = new SimpleDateFormat("dd/MM/yyyy").format(date)+" - "+new SimpleDateFormat("dd/MM/yyyy").format(addDays(date,6))+" :\n"
				+"PatientsTreated:\n\tMax = "+String.valueOf(maxNumOfPatientsTreated)
				+"\tMin = "+String.valueOf(minNumOfPatientsTreated)
				+"\tAVG = "+String.valueOf(avgNumOfPatientsTreated)
				+"\tSd = "+String.valueOf(sdNumOfPatientsTreated)
				+"\nDisToApp: \n\tMax = "+String.valueOf(generateDiffToHoursDaysMinutes(maxFromDisToAppDateDiffInMinutes))
				+"\tMin = "+String.valueOf(generateDiffToHoursDaysMinutes(minFromDisToAppDateDiffInMinutes))
				+"\tAVG = "+String.valueOf(generateDiffToHoursDaysMinutes(avgFromDisToAppDateDiffInMinutes))
				+"\tSd = "+String.valueOf(generateDiffToHoursDaysMinutes(sdFromDisToAppDateDiffInMinutes))
				+"\nAppDateToReal:\n\tMax = "+String.valueOf(generateDiffToHoursDaysMinutes(maxFromAppDateToRealAppDateDiffInMinutes))
				+"\tMin = "+String.valueOf(generateDiffToHoursDaysMinutes(minFromAppDateToRealAppDateDiffInMinutes))
				+"\tAVG = "+String.valueOf(generateDiffToHoursDaysMinutes(avgFromAppDateToRealAppDateDiffInMinutes))
				+"\tSd  ="+String.valueOf(generateDiffToHoursDaysMinutes(sdFromAppDateToRealAppDateDiffInMinutes))
				+"\n";
		for(int i = 0 ; i < allDaysReport.size() ; i++)
			str +=allDaysReport.get(i).toString();
		return "WeekReport \n" +str ;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getIdClinic() {
		return idClinic;
	}

	public void setIdClinic(int idClinic) {
		this.idClinic = idClinic;
	}

	public long getNumOfMiss() {
		return numOfMiss;
	}

	public void setNumOfMiss(long numOfMiss) {
		this.numOfMiss = numOfMiss;
	}

	public int getNumOfPatientsTreated() {
		return numOfPatientsTreated;
	}

	public void setNumOfPatientsTreated(int numOfPatientsTreated) {
		this.numOfPatientsTreated = numOfPatientsTreated;
	}

	public long getMaxNumOfPatientsTreated() {
		return maxNumOfPatientsTreated;
	}

	public void setMaxNumOfPatientsTreated(long maxNumOfPatientsTreated) {
		this.maxNumOfPatientsTreated = maxNumOfPatientsTreated;
	}

	public long getMinNumOfPatientsTreated() {
		return minNumOfPatientsTreated;
	}

	public void setMinNumOfPatientsTreated(long minNumOfPatientsTreated) {
		this.minNumOfPatientsTreated = minNumOfPatientsTreated;
	}

	public long getAvgNumOfPatientsTreated() {
		return avgNumOfPatientsTreated;
	}

	public void setAvgNumOfPatientsTreated(long avgNumOfPatientsTreated) {
		this.avgNumOfPatientsTreated = avgNumOfPatientsTreated;
	}

	public long getSdNumOfPatientsTreated() {
		return sdNumOfPatientsTreated;
	}

	public void setSdNumOfPatientsTreated(long sdNumOfPatientsTreated) {
		this.sdNumOfPatientsTreated = sdNumOfPatientsTreated;
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

	public ArrayList<DayReport> getAllDaysReport() {
		return allDaysReport;
	}

	public void setAllDaysReport(ArrayList<DayReport> allDaysReport) {
		this.allDaysReport = allDaysReport;
	}

	public ArrayList<AppointmentTimeValues> getAllWeekAppointments() {
		return allWeekAppointments;
	}

	public void setAllWeekAppointments(
			ArrayList<AppointmentTimeValues> allWeekAppointments) {
		this.allWeekAppointments = allWeekAppointments;
	}

	public String getMassage() {
		return massage;
	}

	public void setMassage(String massage) {
		this.massage = massage;
	}
	

}