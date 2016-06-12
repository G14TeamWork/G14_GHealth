package Controllers;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class old {

};
/**
	public void ProduceMonthReport()
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
 
 Date x1 = (Date)arrList.get(0);
		Time y1 = (Time)arrList.get(1);
		long x2  = ((Date)arrList.get(0)).getTime() + ((Time)arrList.get(1)).getTime();
		Date x3 = new Date(x2);
		//Timestamp z = new Timestamp();
		//String date = new SimpleDateFormat("yyyy-MM-dd").format(x1);
		//String filledtime = new SimpleDateFormat("HH:mm:ss").format(y1);		
		System.out.println(x1+"\n"+y1+"\n"+x2+"\n"+x3);
		String date = new SimpleDateFormat("yyyy-MM-dd").format(x3);
		String filledtime = new SimpleDateFormat("HH:mm:ss").format(x3);
		System.out.println("------------------------------->>>"+x3 
				+ "\n------------------------------->>>"+filledtime
				+ "\n------------------------------->>>"+date);
 * 
 * 
2016-02-20
13:30:00
1455960600000
Sat Feb 20 11:30:00 IST 2016
------------------------------->>>Sat Feb 20 11:30:00 IST 2016
------------------------------->>>11:30:00
------------------------------->>>2016-02-20

 */
