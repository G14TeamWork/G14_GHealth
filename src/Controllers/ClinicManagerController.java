package Controllers;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ocsf.server.GHealthServer;
import mainPackage.MainClass;
import Entities.ProduceReportEntity;
import Entities.SendEmail;
import views.ClinicManagerView;
import views.DispatcherView;
import views.LoginView;
import Controllers.IRefresh;
import Entities.Appointment;

public class ClinicManagerController implements Observer,IRefresh  {
	public ClinicManagerView clinicmanagerview;
	private ProduceReportEntity PRE = new ProduceReportEntity();
	public ClinicManagerController() {
		clinicmanagerview = new ClinicManagerView();
	}
	
	public void returnExpertClinic()
	{
		PRE.setTaskToDo("search clinic id");
		PRE.setId(Integer.valueOf(MainClass.masterControler.LoginCont.loginEntity.getUsername()));
		 MainClass.ghealth.sendMessegeToServer(PRE);
	}
	
	public ProduceReportEntity getPRE() {
		return PRE;
	}

	public void setPRE(ProduceReportEntity pRE) {
		PRE = pRE;
	}

	public void createDaylyReport() {
		
	}
	
	
	

/*	
	
	
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
*/		
	
	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
