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
		
		String query2 ="SELECT COUNT(*) FROM ghealth.appointments where  `appstatus`='3' AND `appdate` BETWEEN "+generateDayDateToSql(date,0)+" AND "+generateDayDateToSql(date,7)+";";
		arrList2 = GHealthServer.sqlConn.sendSqlQuery(query2);
		if(arrList2 != null)
		this.numOfMiss = (long)arrList2.get(0);
		
		
		
		for(int j = 0 ; j < 7 ; j++,specific = addDays(date,1),currentDayVal = new DayReport())
		{
			String query ="SELECT app.dispatcherSettingDate,app.dispatcherSettingHour,app.appdate,app.start,app.end,app.realStart,app.realEnd FROM ghealth.appointments as app "
					+ "WHERE app.idclinic = "+String.valueOf(idclinic)+" AND app.appdate = "+generateDayDateToSql(date,j)+";";
			arrList = GHealthServer.sqlConn.sendSqlQuery(query);
			if((currentDayVal=currentDayVal.createDayliReport(specific,idclinic) )!= null)
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
		
		query2 =" INSERT INTO `ghealth`.`weeklyreport` (`idclinic`, `date`, `numofmiss`, `numoftreted`, `maxoftreted`, `minoftreted`, `avgoftreted`, `sdoftreted`, `maxdistoapp`, `mindistoapp`, `avgdistoapp`, `sddistoapp`, `maxapptoreal`, `minapptoreal`, `avgapptoreal`, `sdapptoreal`) "
				+ "VALUES ("+String.valueOf(idclinic)+", "+generateDayDateToSql(this.date,0)+", "+String.valueOf(numOfMiss)+","
				+ " "+String.valueOf(numOfPatientsTreated)+", "+String.valueOf(maxNumOfPatientsTreated)+","
				+ " "+String.valueOf(minNumOfPatientsTreated)+", "+String.valueOf(avgNumOfPatientsTreated)+", "+String.valueOf(sdNumOfPatientsTreated)+","
				+ " "+String.valueOf(maxFromDisToAppDateDiffInMinutes)+", "+String.valueOf(minFromDisToAppDateDiffInMinutes)+","
				+ " "+String.valueOf(avgFromDisToAppDateDiffInMinutes)+", "+String.valueOf(sdFromDisToAppDateDiffInMinutes)+", "+String.valueOf(maxFromAppDateToRealAppDateDiffInMinutes)+","
				+ " "+String.valueOf(minFromAppDateToRealAppDateDiffInMinutes)+", "+String.valueOf(avgFromAppDateToRealAppDateDiffInMinutes)+", "+String.valueOf(sdFromAppDateToRealAppDateDiffInMinutes)+");";
		GHealthServer.sqlConn.sendSqlUpdate(query2);
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
		
		str += "\nnumber of patients treated : "+String.valueOf(numOfPatientsTreated)
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
	
		DayReport temp = new DayReport();
		String str = "W-T Max :"+String.valueOf(temp.generateDiffToHoursDaysMinutes(maxNumOfPatientsTreated))
				+"\n\tW-T Min :"+String.valueOf(temp.generateDiffToHoursDaysMinutes(minNumOfPatientsTreated))
				+"\n\tW-T AVG :"+String.valueOf(temp.generateDiffToHoursDaysMinutes(avgNumOfPatientsTreated))
				+"\n\tW-T Sd :"+String.valueOf(temp.generateDiffToHoursDaysMinutes(sdNumOfPatientsTreated))
				+"\n\tW-A Max :"+String.valueOf(temp.generateDiffToHoursDaysMinutes(maxFromDisToAppDateDiffInMinutes))
				+"\n\tW-A Min:"+String.valueOf(temp.generateDiffToHoursDaysMinutes(minFromDisToAppDateDiffInMinutes))
				+"\n\tW-A AVG :"+String.valueOf(temp.generateDiffToHoursDaysMinutes(avgFromDisToAppDateDiffInMinutes))
				+"\n\tW-A Sd :"+String.valueOf(temp.generateDiffToHoursDaysMinutes(sdFromDisToAppDateDiffInMinutes))
				+"\n\tW-B Min :"+String.valueOf(temp.generateDiffToHoursDaysMinutes(maxFromAppDateToRealAppDateDiffInMinutes))
				+"\n\tW-B Min :"+String.valueOf(temp.generateDiffToHoursDaysMinutes(minFromAppDateToRealAppDateDiffInMinutes))
				+"\n\tW-B Min :"+String.valueOf(temp.generateDiffToHoursDaysMinutes(avgFromAppDateToRealAppDateDiffInMinutes))
				+"\n\tW-B Sd :"+String.valueOf(temp.generateDiffToHoursDaysMinutes(sdFromAppDateToRealAppDateDiffInMinutes));
		str +="\n";
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
	

}


/**	public void ProduceMonthReport()
{
	ArrayList<Object> arrList = new ArrayList<Object>(); 
	Calendar c = c.getInstance();
	int dayInMonth = c.get(Calendar.DAY_OF_MONTH);
	
	String query = "SELECT ghealth.appointments.dispatcherSettingDate,ghealth.appointments.dispatcherSettingHour,ghealth.appointments.appdate,ghealth.appointments.start FROM ghealth.appointments  WHERE ghealth.appointments.appdate BETWEEN current_date() - interval 1 month - interval "+String.valueOf(dayInMonth)+" day AND current_date() - interval "+String.valueOf(dayInMonth)+" day;";
	arrList = GHealthServer.sqlConn.sendSqlQuery(query);
	
	
	
}
	
public void viewDaylyReport()
{
	PRE.setTaskToDo("viewDaylyReport");
	PRE.setId(Integer.valueOf(MainClass.masterControler.LoginCont.loginEntity.getUsername()));
	MainClass.ghealth.sendMessegeToServer(PRE); 
	
}

public void createWeeklyReport() 
{
	PRE.setTaskToDo("createWeeklyReport");
	PRE.setId(Integer.valueOf(MainClass.masterControler.LoginCont.loginEntity.getUsername()));
	MainClass.ghealth.sendMessegeToServer(PRE); 

}

public void searchClinicID(ProduceReportEntity pre)
{
	ArrayList<Object> arrList = new ArrayList<Object>();
	String query ="SELECT ghealth.clinic.idclinic FROM ghealth.clinic,ghealth.users WHERE ghealth.users.usertype='man' and ghealth.users.username = "+String.valueOf(pre.getId())+";";
	arrList = GHealthServer.sqlConn.sendSqlQuery(query);
	pre.setIdClinic((int)arrList.get(0));
	
}

public void checkWeeklyReport(ProduceReportEntity pre)
{
	ArrayList<Object> arrList = new ArrayList<Object>();
	Calendar calendar = Calendar.getInstance();
	int week = calendar.get(Calendar.WEEK_OF_MONTH);
	String query ="SELECT * FROM ghealth.daylyreport WHERE ghealth.weeklyreport.idclinic ="+String.valueOf(pre.getIdClinic())+" and ghealth.weeklyreport.week ="+String.valueOf(week-1)+";";
	arrList = GHealthServer.sqlConn.sendSqlQuery(query);
	if(arrList.size() > 0)
		pre.setReportExist(true);


}

public void getWeeklyReport(ProduceReportEntity pre)
{
	ArrayList<Object> arrList = new ArrayList<Object>();
	Calendar calendar = Calendar.getInstance();
	int day = calendar.get(Calendar.DAY_OF_WEEK);
	String query ="SELECT * FROM ghealth.daylyreport WHERE ghealth.weeklyreport.idclinic ="+String.valueOf(pre.getIdClinic())+" and ghealth.weeklyreport.day ="+String.valueOf(day)+";";
	arrList = GHealthServer.sqlConn.sendSqlQuery(query);
	
	long diffSeconds = Avg / 1000 % 60;
	long diffMinutes = Avg / (60 * 1000) % 60;
	long diffHours = Avg / (60 * 60 * 1000) % 24;
	long diffDays = Avg / (24 * 60 * 60 * 1000);
			
}

public void createWeeklyReport(ProduceWeeklyReportEntity pwre)
{
	ArrayList<Object> arrList = new ArrayList<Object>();
	Calendar calendar = Calendar.getInstance();
	int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
	long corent;
	
	
	int numberOfPatient = 0;
	long Max;
	long Min;
	long Avg = 0;
	long Sd = 0;
	
	String query = "SELECT ghealth.appointments.dispatcherSettingDate,ghealth.appointments.dispatcherSettingHour,"
			+ "ghealth.appointments.appdate,ghealth.appointments.start "
			+ "FROM ghealth.appointments WHERE ghealth.appointments.idclinic = 1234 and ghealth.appointments.appdate BETWEEN"
			+ " current_date() - interval 1 week - interval "+String.valueOf(dayofweek)+" day AND current_date() - interval "+String.valueOf(dayofweek)+" day;";
	arrList = GHealthServer.sqlConn.sendSqlQuery(query);
	
	numberOfPatient = arrList.size()/4;
	Max = Min = (((Date)arrList.get(2)).getTime() + ((Time)arrList.get(3)).getTime() ) - ( ((Time)arrList.get(1)).getTime() + ((Date)arrList.get(0)).getTime() ) ;
	
	for (int i  = 0 ; i < arrList.size() ; i+=4)
	{
		corent =(((Date)arrList.get(i+2)).getTime() + ((Time)arrList.get(i+3)).getTime() ) - ( ((Time)arrList.get(i+1)).getTime() + ((Date)arrList.get(i)).getTime() ) ;
		Max = Max >= corent ? Max : corent;
		Min = Min <= corent ? Min : corent;
		Avg += corent;
		
	}
	
	Avg = (Avg /(long)numberOfPatient);
	
	for (int i  = 0 ; i < arrList.size() ; i+=4)
	{
		corent = (((Date)arrList.get(i+2)).getTime() + ((Time)arrList.get(i+3)).getTime() ) - ( ((Time)arrList.get(i+1)).getTime() + ((Date)arrList.get(i)).getTime() - Avg) ;
		Sd += Math.pow(corent,(long)2.0);
	}
	Sd = Sd/(long)(numberOfPatient);
	Sd =  (long)Math.sqrt(Sd);

	pwre.setReport(new WeekReport(pwre.getIdClinic(),numberOfPatient,calendar.get(Calendar.WEEK_OF_MONTH),Max,Min,Avg,Sd));
		
	
}

public void createMonthlyReport(ProduceWeeklyReportEntity pwre)
{
	ArrayList<Object> arrList = new ArrayList<Object>();
	Calendar calendar = Calendar.getInstance();
	int dayofmonth = calendar.get(Calendar.DAY_OF_MONTH);
	long corent;
	
	
	int numberOfPatient = 0;
	long Max;
	long Min;
	long Avg = 0;
	long Sd = 0;
	
	String query = "SELECT ghealth.appointments.dispatcherSettingDate,ghealth.appointments.dispatcherSettingHour,"
			+ "ghealth.appointments.appdate,ghealth.appointments.start "
			+ "FROM ghealth.appointments WHERE ghealth.appointments.idclinic = 1234 and ghealth.appointments.appdate BETWEEN"
			+ " current_date() - interval 1 month - interval "+String.valueOf(dayofmonth)+" day AND current_date() - interval "+String.valueOf(dayofmonth)+" day;";
	arrList = GHealthServer.sqlConn.sendSqlQuery(query);
	
	numberOfPatient = arrList.size()/4;
	Max = Min = (((Date)arrList.get(2)).getTime() + ((Time)arrList.get(3)).getTime() ) - ( ((Time)arrList.get(1)).getTime() + ((Date)arrList.get(0)).getTime() ) ;
	
	for (int i  = 0 ; i < arrList.size() ; i+=4)
	{
		corent =(((Date)arrList.get(i+2)).getTime() + ((Time)arrList.get(i+3)).getTime() ) - ( ((Time)arrList.get(i+1)).getTime() + ((Date)arrList.get(i)).getTime() ) ;
		Max = Max >= corent ? Max : corent;
		Min = Min <= corent ? Min : corent;
		Avg += corent;
		
	}
	
	Avg = (Avg /(long)numberOfPatient);
	
	for (int i  = 0 ; i < arrList.size() ; i+=4)
	{
		corent = (((Date)arrList.get(i+2)).getTime() + ((Time)arrList.get(i+3)).getTime() ) - ( ((Time)arrList.get(i+1)).getTime() + ((Date)arrList.get(i)).getTime() - Avg) ;
		Sd += Math.pow(corent,(long)2.0);
	}
	Sd = Sd/(long)(numberOfPatient);
	Sd =  (long)Math.sqrt(Sd);

	pwre.setReport(new WeekReport(pwre.getIdClinic(),numberOfPatient,calendar.get(Calendar.WEEK_OF_MONTH),Max,Min,Avg,Sd));
		
}


public void createWeeklyReport(ProduceWeeklyReportEntity pwre)
{
	
	ArrayList<Object> arrList = new ArrayList<Object>();
	ArrayList<Long> allWeekValues = new ArrayList<Long>();
	Calendar calendar = Calendar.getInstance();
	Report tempReport ;
	int day = calendar.get(Calendar.DAY_OF_WEEK);

	day =calendar.get(Calendar.DAY_OF_WEEK) + 7;
	
	long corent;
	
	int numberOfPatient = 0;
	long Max;
	long Min;
	long Avg;
	long Sd;
	
	int WeekNumberOfPatient = 0;
	int weekNum;
	long MaxWaitTime;
	long MinWaitTime;
	long AvgWaitTime;
	long SdWaitTime;
	int MaxPatient;
	int MinPatient;
	float AvgPatient = 0;
	float SdPatient = 0;

	for(int j = 0 ;  j < 5 ; j++)
	{
	
			String query = "SELECT ghealth.appointments.dispatcherSettingDate,"
			+"ghealth.appointments.dispatcherSettingHour,ghealth.appointments.appdate,ghealth.appointments.start"
			+" FROM ghealth.appointments"
			+" WHERE ghealth.appointments.appstatus = 2 and ghealth.appointments.appdate = current_date()-"+String.valueOf(day-j)+" DAY and ghealth.appointments.idclinic="+String.valueOf(pwre.getIdClinic())+";";
			
			
			arrList = GHealthServer.sqlConn.sendSqlQuery(query);
			
			
			Max = Min = (((Date)arrList.get(2)).getTime() + ((Time)arrList.get(3)).getTime() ) - ( ((Time)arrList.get(1)).getTime() + ((Date)arrList.get(0)).getTime() ) ;
			Avg = Sd = numberOfPatient = 0;
	
			for (int i  = 0 ; i < arrList.size() ; i+=4)
			{
				corent =(((Date)arrList.get(i+2)).getTime() + ((Time)arrList.get(i+3)).getTime() ) - ( ((Time)arrList.get(i+1)).getTime() + ((Date)arrList.get(i)).getTime() ) ;
				Max = Max >= corent ? Max : corent;
				Min = Min <= corent ? Min : corent;
				Avg += corent;
				numberOfPatient++;
				allWeekValues.add(corent);
				
			}
			
			Avg = (Avg /(long)numberOfPatient);
				
			for (int i  = 0 ; i < arrList.size() ; i+=4)
			{
				corent = (((Date)arrList.get(i+2)).getTime() + ((Time)arrList.get(i+3)).getTime() ) - ( ((Time)arrList.get(i+1)).getTime() + ((Date)arrList.get(i)).getTime() - Avg) ;
				Sd += Math.pow(corent,(long)2.0);
			}
			
			Sd = Sd/(long)(numberOfPatient);
			Sd =  (long)Math.sqrt(Sd);

			String newRow = "INSERT INTO ghealth.daylyreport (idclinic, day, clientstreated, maxwaittime, minwitetime, avgwaittime, sdwaittime)"
					+" VALUES ("+String.valueOf(pwre.getIdClinic())+","+String.valueOf(day)+","+String.valueOf(numberOfPatient)+","+String.valueOf(Max)+","+String.valueOf(Min)+","+String.valueOf(Avg)+","+String.valueOf(Sd)+");";
			GHealthServer.sqlConn.sendSqlUpdate(newRow);

			pwre.getAllDaysReports().add(new Report(pwre.getIdClinic(),day+j,numberOfPatient,Max,Min,Avg,Sd));
			
		
	}
	long temp1 =  pwre.getAllDaysReports().get(0).getAvg() * pwre.getAllDaysReports().get(0).getNumberOfPatient() 
				+ pwre.getAllDaysReports().get(1).getAvg() * pwre.getAllDaysReports().get(1).getNumberOfPatient() 
				+ pwre.getAllDaysReports().get(2).getAvg() * pwre.getAllDaysReports().get(2).getNumberOfPatient() 
				+ pwre.getAllDaysReports().get(3).getAvg() * pwre.getAllDaysReports().get(3).getNumberOfPatient() 
				+ pwre.getAllDaysReports().get(4).getAvg() * pwre.getAllDaysReports().get(4).getNumberOfPatient();
		
	int num = pwre.getAllDaysReports().get(0).getNumberOfPatient()
			+ pwre.getAllDaysReports().get(1).getNumberOfPatient()
			+ pwre.getAllDaysReports().get(2).getNumberOfPatient()
			+ pwre.getAllDaysReports().get(3).getNumberOfPatient()
			+ pwre.getAllDaysReports().get(4).getNumberOfPatient();
	AvgWaitTime = temp1/(long)num;
	for(int i = 0 ; i < allWeekValues.size() ; i++ )
	{
		SdWaitTime += Math.pow(allWeekValues.get(i)-SdWaitTime, 2.0);
	}
	SdWaitTime =  SdWaitTime/(long)num;
	SdWaitTime = (long) Math.sqrt(SdWaitTime);
	
	MaxWaitTime = pwre.getAllDaysReports().get(0).getMax();
	MinWaitTime = pwre.getAllDaysReports().get(0).getMin();
	MaxPatient =  pwre.getAllDaysReports().get(0).getNumberOfPatient();
	MinPatient =  pwre.getAllDaysReports().get(0).getNumberOfPatient();
	
	for(int i = 1 ; i < 5 ; i++)
	{
		MaxWaitTime = (MaxWaitTime >= pwre.getAllDaysReports().get(i).getMax()) ? MaxWaitTime : pwre.getAllDaysReports().get(i).getMax();
		MinWaitTime = (MinWaitTime <= pwre.getAllDaysReports().get(i).getMin()) ? MinWaitTime : pwre.getAllDaysReports().get(i).getMin();	
		MaxPatient = (MaxPatient >= pwre.getAllDaysReports().get(i).getNumberOfPatient()) ? MaxPatient : pwre.getAllDaysReports().get(i).getNumberOfPatient();
		MinPatient = (MinPatient <= pwre.getAllDaysReports().get(i).getNumberOfPatient()) ? MinPatient : pwre.getAllDaysReports().get(i).getNumberOfPatient();
	}
	
	
	pwre.setWeekreport(new WeekReport(pwre.getIdClinic(),calendar.get(Calendar.WEEK_OF_MONTH),num,MaxWaitTime,MinWaitTime,AvgWaitTime,SdWaitTime));;
	
}






	
	long diffSeconds = Avg / 1000 % 60;
	long diffMinutes = Avg / (60 * 1000) % 60;
	long diffHours = Avg / (60 * 60 * 1000) % 24;
	long diffDays = Avg / (24 * 60 * 60 * 1000);
	
	System.out.print(diffDays + " days, ");
	System.out.print(diffHours + " hours, ");
	System.out.print(diffMinutes + " minutes, ");
	System.out.print(diffSeconds + " seconds.");
	
	diffSeconds = Max / 1000 % 60;
	diffMinutes = Max / (60 * 1000) % 60;
	diffHours = Max / (60 * 60 * 1000) % 24;
	diffDays = Max / (24 * 60 * 60 * 1000);
	
	System.out.print(diffDays + " days, ");
	System.out.print(diffHours + " hours, ");
	System.out.print(diffMinutes + " minutes, ");
	System.out.print(diffSeconds + " seconds.");
	

public void func1(Appointment appArr[])
{
	Date d1,d2;
	long diff = d2.getTime() - d1.getTime();

	long diffSeconds = diff / 1000 % 60;
	long diffMinutes = diff / (60 * 1000) % 60;
	long diffHours = diff / (60 * 60 * 1000) % 24;
	long diffDays = diff / (24 * 60 * 60 * 1000);

	System.out.print(diffDays + " days, ");
	System.out.print(diffHours + " hours, ");
	System.out.print(diffMinutes + " minutes, ");
	System.out.print(diffSeconds + " seconds.");
	
	int maxVal = appArr[0].getIdexpert();
	int minVal;
	int sum; 
	for (Appointment i : appArr) {
		

	}
}
	

public ProduceReportEntity getPRE() {
	return PRE;
}

public void setPRE(ProduceReportEntity pRE) {
	PRE = pRE;
}

@Override
public void refreshView() {
	// TODO Auto-generated method stub
	
}

@Override
public void update(Observable o, Object arg) {
	if (arg instanceof ProduceReportEntity && ((ProduceReportEntity)arg).isSuccess() && ((ProduceReportEntity)arg).getTaskToDo().equals("createDaylyReport")) {
		System.out.println("success");
	//	PRE.setSuccess(true);
		JOptionPane.showMessageDialog(null," Produce Dayly Report succeed");
	}
	else if (arg instanceof ProduceReportEntity && ((ProduceReportEntity)arg).isSuccess() && ((ProduceReportEntity)arg).getTaskToDo().equals("checkDaylyReport")) {
	}

}
**/





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
 
