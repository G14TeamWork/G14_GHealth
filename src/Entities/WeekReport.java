package Entities;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ocsf.server.GHealthServer;

public class WeekReport {
	
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
	private ArrayList<AppointmentTimeValues> allWeekAppointments = new ArrayList<AppointmentTimeValues>();
	
	public WeekReport createWeeklyReport(Date date , int idclinic)
	{
		ArrayList<Object> arrList = new ArrayList<Object>();
		AppointmentTimeValues currentAppVal;
		DayReport currentDayVal = new DayReport();

		Date specific = new Date();
		long dateInLong = date.getTime();
		for(int j = 0 ; j < 7 ; j++)
		{
			String query ="SELECT app.dispatcherSettingDate,app.dispatcherSettingHour,app.appdate,app.start,app.end,app.realStart,app.realEnd FROM ghealth.appointments as app "
					+ "WHERE app.idclinic = "+String.valueOf(idclinic)+" app.appdate="+generateDayDateToSql(date)+" +"+String.valueOf(j)+" DAY;";
			arrList = GHealthServer.sqlConn.sendSqlQuery(query);
			
			dateInLong +=24 * 60 * 60 * 1000;
			specific.setTime(dateInLong);
			if(currentDayVal.createDayliReport(specific,idclinic) != null)
				allDaysReport.add(currentDayVal);		
			for(int i = 0 ; i < arrList.size() ; i+=7)
			{
				numOfPatientsTreated++;
				allWeekAppointments.add(new AppointmentTimeValues((Date)arrList.get(i),(Time)arrList.get(i+1),(Date)arrList.get(i+2),(Time)arrList.get(i+3),(Time)arrList.get(i+4) ,(Time)arrList.get(i+5),(Time)arrList.get(i+6)));
				
			}
		}

		sdFromDisToAppDateDiffInMinutes = avgFromDisToAppDateDiffInMinutes = 0;
		sdFromAppDateToRealAppDateDiffInMinutes = avgFromAppDateToRealAppDateDiffInMinutes = 0;
		sdNumOfPatientsTreated = avgNumOfPatientsTreated = 0;
		
		maxFromDisToAppDateDiffInMinutes = allDaysReport.get(0).getMaxFromDisToAppDateDiffInMinutes();
		minFromDisToAppDateDiffInMinutes = allDaysReport.get(0).getMinFromDisToAppDateDiffInMinutes();
		maxFromAppDateToRealAppDateDiffInMinutes = allDaysReport.get(0).getMaxFromAppDateToRealAppDateDiffInMinutes();
		minFromAppDateToRealAppDateDiffInMinutes = 	allDaysReport.get(0).getMinFromAppDateToRealAppDateDiffInMinutes();	
		maxNumOfPatientsTreated = minNumOfPatientsTreated = allDaysReport.get(0).getNumOfPatientsTreated();
		
		
		for(int i = 1 ; i < allDaysReport.size() ; i++ )
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
		
		return this;
		
		
	}
	public String generateDiffToHoursDaysMinutes(DayReport allDaysInWeek)
	{
		/*
		 * days = allTimeInMinutes/24*60;
		 * hours = (allTimeInMinutes%24*60)/60;
		 * Minutes = allTimeInMinutes%60;
		 * */
		/*
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime date= LocalDateTime.parse(getDate().toString(), formatter); 
		long days = allTimeInMinutes/24*60;
		long hours = (allTimeInMinutes%24*60)/60;
		long minutes = allTimeInMinutes%60;
		String str = null;
		str += date.toString()+" : ";
		str += String.valueOf(days)+" d ";
		str += String.valueOf(hours)+" h ";
		str += String.valueOf(minutes)+" m \n";
		*/

		String str ="";
		for(int i = 0 ; i < allDaysReport.size() ; i++)
		{
			str += allDaysReport.get(i).getDate()+allDaysReport.get(i).generateDiffToHoursDaysMinutes(i)+" \n";
		}
		
		return str;
	}

	
	public String generateDayDateToSql(Date date)
	{
		
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}
	
	
	

}

/**
 * minFromDisToAppDateDiffInMinutes = maxFromDisToAppDateDiffInMinutes = dayValues.get(0).getFromDisToAppDateDiffInMinutes();
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
		}
		sdFromDisToAppDateDiffInMinutes = (sdFromDisToAppDateDiffInMinutes/(long)(numOfPatientsTreated));
		sdFromDisToAppDateDiffInMinutes =  (long)Math.sqrt(sdFromDisToAppDateDiffInMinutes);

		for (int i  = 0 ; i < dayValues.size() ; i++)
		{
			current = dayValues.get(i);
			sdFromAppDateToRealAppDateDiffInMinutes += Math.pow(current.getFromAppDateToRealAppDateDiffInMinutes() - avgFromAppDateToRealAppDateDiffInMinutes,(long)2.0);
		}
		sdFromAppDateToRealAppDateDiffInMinutes = (sdFromAppDateToRealAppDateDiffInMinutes/(long)(numOfPatientsTreated));
		sdFromAppDateToRealAppDateDiffInMinutes =  (long)Math.sqrt(sdFromAppDateToRealAppDateDiffInMinutes);
 */
 
