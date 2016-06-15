package Entities;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ocsf.server.GHealthServer;

import java.io.Serializable;
/**
 * entity class. suppose to have all fields that might be sent to or from server in producing monthly reports.
 * being sent from and to client/server
 * 
 * @author Ruslan
 *
 */
public class MonthReport implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Date date;
	private int idClinic;
	private long numOfMiss;
	private long maxNumOfMiss;
	private long minNumOfMiss;
	private int avgNumOfMiss;
	private int sdNumOfMiss;
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
	private ArrayList<WeekReport> allWeeksReport;
	private ArrayList<AppointmentTimeValues> allMonthReport;
	private ArrayList<DayReport> allDaysReport;
	
	public MonthReport createMonthlyReport(Date date , int idclinic)
	{
		this.date = date;
		this.idClinic = idclinic;
		this.allWeeksReport = new ArrayList<WeekReport>();
		this.allMonthReport = new ArrayList<AppointmentTimeValues>();
		this.allDaysReport = new ArrayList<DayReport>();
		DayReport currentDayVal = new DayReport();
		WeekReport currentWeekVal = new WeekReport();
		ArrayList<Object> arrList = new ArrayList<Object>();
		ArrayList<Object> arrList2 = new ArrayList<Object>();
		AppointmentTimeValues currentAppVal;

		Date specific = date;
	//	long dateInLong = date.getTime();
		
		
		String query2 ="SELECT COUNT(*) FROM ghealth.appointments where  `appstatus`='3' AND ghealth.appointments.idclinic=" +String.valueOf(idclinic)+" AND `appdate` BETWEEN "+generateDayDateToSql(date)+" AND "+generateDayDateToSql(date)+" +INTERVAL 1 MONTH;";
		arrList2 = GHealthServer.sqlConn.sendSqlQuery(query2);
		this.numOfMiss = (long)arrList2.get(0);
		
		
		
		for(int j = 0 ; j < 5 ; j++,specific = addDays(specific,7) )
		{
			String query ="SELECT app.dispatcherSettingDate,app.dispatcherSettingHour,app.appdate,app.start,app.end,app.realStart,app.realEnd FROM ghealth.appointments as app "
					+ "WHERE app.idclinic = "+String.valueOf(idclinic)+" AND app.appstatus = 2 AND app.appdate BETWEEN "+generateDayDateToSql(specific)+" AND "+generateDayDateToSql(specific)+" +INTERVAL 1 WEEK;";
			arrList = GHealthServer.sqlConn.sendSqlQuery(query);
					
			currentWeekVal=  currentWeekVal.createWeeklyReport(specific,idclinic);
			if(currentWeekVal.getMassage().equals("not empty"))
				this.allWeeksReport.add(currentWeekVal);	
			
			
			for(int i = 0 ; i < arrList.size() ; i+=7)
			{
				
				allMonthReport.add(new AppointmentTimeValues((Date)arrList.get(i),(Time)arrList.get(i+1),(Date)arrList.get(i+2),(Time)arrList.get(i+3),(Time)arrList.get(i+4) ,(Time)arrList.get(i+5),(Time)arrList.get(i+6)));
				numOfPatientsTreated++;
			}
			
		}
		
		
		sdFromDisToAppDateDiffInMinutes = avgFromDisToAppDateDiffInMinutes = 0;
		sdFromAppDateToRealAppDateDiffInMinutes = avgFromAppDateToRealAppDateDiffInMinutes = 0;
		sdNumOfPatientsTreated = avgNumOfPatientsTreated = 0;
		sdNumOfMiss = avgNumOfMiss = 0;
		
		maxFromDisToAppDateDiffInMinutes = allWeeksReport.get(0).getMaxFromDisToAppDateDiffInMinutes();
		minFromDisToAppDateDiffInMinutes = allWeeksReport.get(0).getMinFromDisToAppDateDiffInMinutes();
		maxFromAppDateToRealAppDateDiffInMinutes = allWeeksReport.get(0).getMaxFromAppDateToRealAppDateDiffInMinutes();
		minFromAppDateToRealAppDateDiffInMinutes = 	allWeeksReport.get(0).getMinFromAppDateToRealAppDateDiffInMinutes();	
		maxNumOfPatientsTreated = minNumOfPatientsTreated = allWeeksReport.get(0).getNumOfPatientsTreated();
		maxNumOfMiss = minNumOfMiss = allWeeksReport.get(0).getNumOfMiss();
		
		
		for(int i = 0 ; i < allWeeksReport.size() ; i++ )
		{
			currentWeekVal = allWeeksReport.get(i);
			maxFromDisToAppDateDiffInMinutes = maxFromDisToAppDateDiffInMinutes >= currentWeekVal.getMaxFromDisToAppDateDiffInMinutes() ? 
					maxFromDisToAppDateDiffInMinutes : currentWeekVal.getSdFromDisToAppDateDiffInMinutes();
			maxFromAppDateToRealAppDateDiffInMinutes = maxFromAppDateToRealAppDateDiffInMinutes >= currentWeekVal.getMaxFromAppDateToRealAppDateDiffInMinutes() ?
					maxFromAppDateToRealAppDateDiffInMinutes : currentWeekVal.getMaxFromAppDateToRealAppDateDiffInMinutes();
			minFromDisToAppDateDiffInMinutes = minFromDisToAppDateDiffInMinutes <= currentWeekVal.getMinFromDisToAppDateDiffInMinutes() ? 
					minFromDisToAppDateDiffInMinutes : currentWeekVal.getMinFromDisToAppDateDiffInMinutes() ;
			minFromAppDateToRealAppDateDiffInMinutes = minFromAppDateToRealAppDateDiffInMinutes <= currentWeekVal.getMinFromAppDateToRealAppDateDiffInMinutes() ?
					minFromAppDateToRealAppDateDiffInMinutes : currentWeekVal.getMinFromAppDateToRealAppDateDiffInMinutes();
			maxNumOfPatientsTreated = maxNumOfPatientsTreated >= currentWeekVal.getNumOfPatientsTreated() ?
					maxNumOfPatientsTreated : currentWeekVal.getNumOfPatientsTreated();
			minNumOfPatientsTreated = minNumOfPatientsTreated <= currentWeekVal.getNumOfPatientsTreated() ?
					minNumOfPatientsTreated : currentWeekVal.getNumOfPatientsTreated();
			maxNumOfMiss = maxNumOfMiss >= currentWeekVal.getNumOfMiss() ?
					maxNumOfMiss : currentWeekVal.getNumOfMiss();
			minNumOfMiss = minNumOfMiss <= currentWeekVal.getNumOfMiss() ?
					minNumOfMiss : currentWeekVal.getNumOfMiss();
			
			avgFromDisToAppDateDiffInMinutes += currentWeekVal.getAvgFromDisToAppDateDiffInMinutes() * currentWeekVal.getNumOfPatientsTreated() ;
			avgFromAppDateToRealAppDateDiffInMinutes += currentWeekVal.getAvgFromAppDateToRealAppDateDiffInMinutes() * currentWeekVal.getNumOfPatientsTreated();	
			avgNumOfPatientsTreated += currentWeekVal.getNumOfPatientsTreated();
			avgNumOfMiss += currentWeekVal.getNumOfMiss();
		}
		
		
		avgFromDisToAppDateDiffInMinutes = (avgFromDisToAppDateDiffInMinutes /(long)numOfPatientsTreated);
		avgFromAppDateToRealAppDateDiffInMinutes = (avgFromAppDateToRealAppDateDiffInMinutes /(long)numOfPatientsTreated);
		avgNumOfPatientsTreated = (avgNumOfPatientsTreated/(int)allWeeksReport.size());
		avgNumOfMiss = (avgNumOfMiss/(int)allWeeksReport.size());
		
/*
		for (int i  = 0 ; i < allWeeksReport.size() ; i++)
		{
			for(int j = 0 ; j  < allWeeksReport.get(i).getAllDaysReport().size() ; j++ )
			{
			currentDayVal = allWeeksReport.get(i).getAllDaysReport().get(j);
			sdNumOfPatientsTreated += Math.pow(currentDayVal.getNumOfPatientsTreated() - avgNumOfPatientsTreated,(long)2.0);
			sdNumOfMiss += Math.pow(currentDayVal.getNumOfMiss() - avgNumOfMiss,(long)2.0);
			}
		}*/
		sdNumOfPatientsTreated = (sdNumOfPatientsTreated/(long)(numOfPatientsTreated));
		sdNumOfPatientsTreated =  (long)Math.sqrt(sdNumOfPatientsTreated);
		sdNumOfMiss = (int) (sdNumOfMiss/(long)(numOfMiss));
		sdNumOfMiss =  (int)Math.sqrt(sdNumOfMiss);

		
		
		for (int i  = 0 ; i < allMonthReport.size() ; i++)
		{
			currentAppVal = allMonthReport.get(i);
			sdFromDisToAppDateDiffInMinutes += Math.pow(currentAppVal.getFromDisToAppDateDiffInMinutes() - avgFromDisToAppDateDiffInMinutes,(long)2.0);
			sdFromAppDateToRealAppDateDiffInMinutes += Math.pow(currentAppVal.getFromAppDateToRealAppDateDiffInMinutes() - avgFromAppDateToRealAppDateDiffInMinutes,(long)2.0);
		}
		sdFromDisToAppDateDiffInMinutes = (sdFromDisToAppDateDiffInMinutes/(long)(numOfPatientsTreated));
		sdFromDisToAppDateDiffInMinutes =  (long)Math.sqrt(sdFromDisToAppDateDiffInMinutes);
		sdFromAppDateToRealAppDateDiffInMinutes = (sdFromAppDateToRealAppDateDiffInMinutes/(long)(numOfPatientsTreated));
		sdFromAppDateToRealAppDateDiffInMinutes =  (long)Math.sqrt(sdFromAppDateToRealAppDateDiffInMinutes);
		
		return this;
		
		
		
	}
	
	public String generateDayDateToSql(Date date)
	{
		
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}

	@Override
	public String toString() {
		
		String str = allWeeksReport.toString();

		
		str = "MonthReport"+new SimpleDateFormat("dd/MM/yyyy").format(date)+" :\n idClinic=" + idClinic
				+ "\n\t numOfMiss=" + numOfMiss + ", maxNumOfMiss=" + maxNumOfMiss
				+ ", minNumOfMiss=" + minNumOfMiss + ", avgNumOfMiss="
				+ avgNumOfMiss + ", sdNumOfMiss=" + sdNumOfMiss
				+ "\n\t num Of Patients Treated : " 
				+ "\nnum to all month :"+ numOfPatientsTreated
				+ "\n\tmax =" + maxNumOfPatientsTreated
				+ "\n\tmin =" + minNumOfPatientsTreated
				+ "\n\tavg =" + avgNumOfPatientsTreated
				+ "\n\tsd =" + sdNumOfPatientsTreated
				+ "\nDisToApp:"
				+ "\n\tmax ="+ maxFromDisToAppDateDiffInMinutes
				+ "\n\tmin ="+ minFromDisToAppDateDiffInMinutes
				+ "\n\tavg ="+ avgFromDisToAppDateDiffInMinutes
				+ "\n\tsd ="+ sdFromDisToAppDateDiffInMinutes
				+"\nAppDateToReal :"
				+ "\n\tmax ="+ maxFromAppDateToRealAppDateDiffInMinutes
				+ "\n\tmin ="+ minFromAppDateToRealAppDateDiffInMinutes
				+ "\n\tavg ="+ avgFromAppDateToRealAppDateDiffInMinutes
				+ "\n\tsd ="+ sdFromAppDateToRealAppDateDiffInMinutes 
				+ "\nallWeeksReport :"+ allWeeksReport 
				+ "\n\tall Days Report =" + allDaysReport.toString() ;
		 
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
	
}
