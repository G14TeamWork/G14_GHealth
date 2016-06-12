package Controllers;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import ocsf.server.GHealthServer;
import mainPackage.MainClass;
import Entities.AppointmentTimeValues;
import Entities.ClinicManagerEntity;
import views.ClinicManagerView;



public class ClinicManagerController implements Observer,IRefresh  {
	public ClinicManagerView clinicmanagerview;
	private ClinicManagerEntity CME = new ClinicManagerEntity();

	
	public ClinicManagerController() {
		clinicmanagerview = new ClinicManagerView();
	}
	
	public void searchClinicIdClient()
	{
		this.CME.setTaskToDo("seting clinic manager id");
		this.CME.setManagerId(Integer.valueOf(MainClass.masterControler.LoginCont.loginEntity.getUsername())); 
		MainClass.ghealth.sendMessegeToServer(CME);
	}
	
	
	public void searchClinicIdServer(ClinicManagerEntity cme)
	{
		/**
		 * days = allTimeInMinutes/24*60;
		 * hours = (allTimeInMinutes%24*60)/60;
		 * Minutes = allTimeInMinutes%60;
		 * 
		 * 
		 * 
		 * String date = new SimpleDateFormat("yyyyMMdd").format(x3); //format for update mySql workbench
		 * 
		 * 
		 * 
		 * 
		 * 	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			LocalDateTime dateTime1= LocalDateTime.parse(x1+" "+y1, formatter); //convert (Date and Time) to LocalDateTime ->>> "yyyy-MM-dd HH:mm:ss"
			LocalDateTime dateTime2= LocalDateTime.parse(x2+" "+y2, formatter);
		
		 * long diffInMinutes = java.time.Duration.between(dateTime1, dateTime2).toMinutes(); // diff dateTime1 - dateTime2
		 * 
		 * 
		 * 
		 * query = "UPDATE ghealth.appointments as app SET sentemail=2 WHERE app.appdate="+date+";";  //update status in appointment table [sentemail = 2] 
			GHealthServer.sqlConn.sendSqlUpdate(query);
		 * 
		 * 
		long diffInMilli = java.time.Duration.between(dateTime1, dateTime2).toMillis();
		long diffInSeconds = java.time.Duration.between(dateTime1, dateTime2).getSeconds();
		long diffInMinutes = java.time.Duration.between(dateTime1, dateTime2).toMinutes()%60;
		long diffInHours = java.time.Duration.between(dateTime1, dateTime2).toHours()%24;
		long diffInDays = java.time.Duration.between(dateTime1, dateTime2).toDays();
		
		 */
		
		
		ArrayList<Object> arrList = new ArrayList<Object>();
		String query ="SELECT ghealth.clinic.idclinic FROM ghealth.clinic WHERE ghealth.clinic.managerid ="+String.valueOf(cme.getManagerId())+";";
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		cme.setClinicManegerId(Integer.valueOf((String)arrList.get(0)));
		createDailyReport(cme);
		
	}
	
	public void createDailyReport(ClinicManagerEntity cme)
	{
		ArrayList<Object> arrList = new ArrayList<Object>();
		
		String date = new SimpleDateFormat("yyyyMMdd").format(cme.getDay());
	
		String query ="SELECT app.dispatcherSettingDate,app.dispatcherSettingHour,app.appdate,app.start,app.end,app.realStart,app.realEnd FROM ghealth.appointments as app where app.appdate="+date+";";
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		for(int i=0 ; i < arrList.size() ; i+=6)	//[   dispatcher	seted DateTime	 ] [   Appointment DateTime		   ] [ Appointment End Time ] [ Appointment realStart ,realEnd  ]
		cme.getMonth().add(new AppointmentTimeValues((Date)arrList.get(i),(Time)arrList.get(i+1),(Date)arrList.get(i+2),(Time)arrList.get(i+3),(Time)arrList.get(i+4) ,(Time)arrList.get(i+5),(Time)arrList.get(i+6)));
		
		
		
		
		query = "UPDATE ghealth.appointments as app SET sentemail=2 WHERE app.appdate="+date+";";  //update status in appointment table [sentemail = 2] 
		GHealthServer.sqlConn.sendSqlUpdate(query);

	}
	
	
	public void PreduceReport(String taskToDo,Date from ,Date to)
	{
		
	}
	
	

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof ClinicManagerEntity) {
			CME.setClinicManegerId(((ClinicManagerEntity)arg).getClinicManegerId());
			System.out.println("update clinic manager id");
		}
		
	}


	
}
