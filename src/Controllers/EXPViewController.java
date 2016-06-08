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
		//System.out.println(MainClass.masterControler.EXPVCont.RAE.appointment.getIdpatient());
		//RAE.appointment.getIdpatient();
	}
	public void checkAppSQL(RecordAppointmentEntity rae) {
		ArrayList<Object> arrList = new ArrayList<Object>();
		String query = "SELECT * FROM ghealth.appointments WHERE idappointment =" + rae.appID ;
			arrList=GHealthServer.sqlConn.sendSqlQuery(query);
		rae.appointment.setIdpatient(String.valueOf((int)arrList.get(2)));
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
		
		System.out.println("In Update!");
		if(arg instanceof RecordAppointmentEntity){
		
			RAE.appointment.setIdpatient(((RecordAppointmentEntity)arg).appointment.getIdpatient());
			System.out.println(RAE.appointment.getIdpatient()+"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			refreshView();
		}
		
	}
}