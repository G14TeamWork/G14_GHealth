package Controllers;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ocsf.server.GHealthServer;
import mainPackage.MainClass;
import views.ExpView;
import views.LoginView;
import Controllers.IRefresh;
import Entities.RecordAppointmentEntity;

public class EXPViewController implements Observer,IRefresh  {
	public ExpView expview;
	public RecordAppointmentEntity RAE;
	public EXPViewController() {
		expview = new ExpView();
	}
	
	public void checkApp(String appID1){
		RAE = new RecordAppointmentEntity(appID1,"search");
		MainClass.ghealth.sendMessegeToServer(RAE);
		//RAE.appointment.getIdpatient();
	}
	public void checkAppSQL(RecordAppointmentEntity rae) {
		ArrayList<Object> arrList = new ArrayList<Object>();
		System.out.println("start check app sql");
		String query = "SELECT * FROM ghealth.appointments WHERE idappointment =" + rae.appID ;
		System.out.println("ASKING QUERY ");
		System.out.println(rae.appID);
		arrList=GHealthServer.sqlConn.sendSqlQuery(query);
		System.out.println("back from sqlconn.sendsqlquery");
		System.out.println(arrList.get(2)+"LALALALAL");
		rae.appID=((String)arrList.get(2));
		System.out.println(rae.appointment.getIdpatient());
	}
	
	@Override
	public void refreshView() {
		//MainClass.masterControler.setView(
			//	MainClass.masterControler.RACont.RecordAppointview,
			//		MainClass.masterControler.RACont);
			MainClass.masterControler.setView(MainClass.masterControler.RACont.RecordAppointview);
	}

	@Override
	public void update(Observable o, Object arg) {
		/*
		if(arg instanceof RecordAppointmentEntity){
			if (((RecordAppointmentEntity)arg).appointment.getIdpatient()!=null){
				MainClass.masterControler.RACont = new RecordAppointController();
				MainClass.masterControler.RACont.RecordAppointview.getPatientID().setText("Patient ID : " +
						((RecordAppointmentEntity)arg).appointment.getIdpatient() );
				refreshView();
				
			}*/
		if(arg instanceof RecordAppointmentEntity){
		}
			RAE.appointment.setIdpatient(((RecordAppointmentEntity)arg).appID);
			refreshView();
		}
		
	}
