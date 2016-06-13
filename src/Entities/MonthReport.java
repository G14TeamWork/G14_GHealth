package Entities;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	private int numOfMiss;
	private int maxNumOfMiss;
	private int minNumOfMiss;
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
		ArrayList<Object> arrList = new ArrayList<Object>();
		ArrayList<Object> arrList2 = new ArrayList<Object>();
		AppointmentTimeValues currentAppVal;
		DayReport currentDayVal = new DayReport();
		WeekReport currentWeekVal = new WeekReport();
		Date specific = new Date();
		long dateInLong = date.getTime();
		
		
		String query2 ="SELECT COUNT(*) FROM ghealth.appointments where  `appstatus`='2' AND `appdate` BETWEEN "+generateDayDateToSql(date)+" AND "+generateDayDateToSql(date)+" + 1 MONTH;";
		arrList2 = GHealthServer.sqlConn.sendSqlQuery(query2);
		this.numOfMiss = (int)arrList2.get(0);
		
		
		
		for(int j = 0 ; j < 5 ; j++)
		{
			String query ="SELECT app.dispatcherSettingDate,app.dispatcherSettingHour,app.appdate,app.start,app.end,app.realStart,app.realEnd FROM ghealth.appointments as app "
					+ "WHERE app.idclinic = "+String.valueOf(idclinic)+" AND app.appstatus = 2 AND app.appdate="+generateDayDateToSql(date)+" +"+String.valueOf(j)+" WEEK;";
			arrList = GHealthServer.sqlConn.sendSqlQuery(query);
			
			
			specific.setTime(dateInLong);
			if(currentWeekVal.createWeeklyReport(specific,idclinic) != null)
				allWeeksReport.add(currentWeekVal);	
			
			dateInLong +=7*24 * 60 * 60 * 1000;
			
			for(int i = 0 ; i < arrList.size() ; i+=7)
			{
				numOfPatientsTreated++;
				allMonthReport.add(new AppointmentTimeValues((Date)arrList.get(i),(Time)arrList.get(i+1),(Date)arrList.get(i+2),(Time)arrList.get(i+3),(Time)arrList.get(i+4) ,(Time)arrList.get(i+5),(Time)arrList.get(i+6)));
				
			}
			
		}

		numOfPatientsTreated /=7;
		
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
		
		
		for(int i = 1 ; i < allWeeksReport.size() ; i++ )
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
		

		for (int i  = 0 ; i < allDaysReport.size() ; i++)
		{
			currentDayVal = allDaysReport.get(i);
			sdNumOfPatientsTreated += Math.pow(currentDayVal.getNumOfPatientsTreated() - avgNumOfPatientsTreated,(long)2.0);
			sdNumOfMiss += Math.pow(currentDayVal.getNumOfMiss() - avgNumOfMiss,(long)2.0);
		}
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
		
		
		str = "MonthReport [date=" + date + ", idClinic=" + idClinic
				+ ", numOfMiss=" + numOfMiss + ", maxNumOfMiss=" + maxNumOfMiss
				+ ", minNumOfMiss=" + minNumOfMiss + ", avgNumOfMiss="
				+ avgNumOfMiss + ", sdNumOfMiss=" + sdNumOfMiss
				+ ", numOfPatientsTreated=" + numOfPatientsTreated
				+ ", maxNumOfPatientsTreated=" + maxNumOfPatientsTreated
				+ ", minNumOfPatientsTreated=" + minNumOfPatientsTreated
				+ ", avgNumOfPatientsTreated=" + avgNumOfPatientsTreated
				+ ", sdNumOfPatientsTreated=" + sdNumOfPatientsTreated
				+ ", maxFromDisToAppDateDiffInMinutes="
				+ maxFromDisToAppDateDiffInMinutes
				+ ", minFromDisToAppDateDiffInMinutes="
				+ minFromDisToAppDateDiffInMinutes
				+ ", avgFromDisToAppDateDiffInMinutes="
				+ avgFromDisToAppDateDiffInMinutes
				+ ", sdFromDisToAppDateDiffInMinutes="
				+ sdFromDisToAppDateDiffInMinutes
				+ ", maxFromAppDateToRealAppDateDiffInMinutes="
				+ maxFromAppDateToRealAppDateDiffInMinutes
				+ ", minFromAppDateToRealAppDateDiffInMinutes="
				+ minFromAppDateToRealAppDateDiffInMinutes
				+ ", avgFromAppDateToRealAppDateDiffInMinutes="
				+ avgFromAppDateToRealAppDateDiffInMinutes
				+ ", sdFromAppDateToRealAppDateDiffInMinutes="
				+ sdFromAppDateToRealAppDateDiffInMinutes + ", allWeeksReport="
				+ allWeeksReport + ", allMonthReport=" + allMonthReport
				+ ", allDaysReport=" + allDaysReport + "]";
		 
		 return str;
	}
	
}
