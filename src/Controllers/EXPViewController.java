package Controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ocsf.server.GHealthServer;
import mainPackage.MainClass;
import views.ExpView;
import views.LoginView;
import Controllers.IRefresh;
import Entities.RecordAppointmentEntity;

public class EXPViewController implements Observer,IRefresh, Serializable {
	
	private static final long serialVersionUID = 1L;
	public ExpView expview;
	public RecordAppointmentEntity RAE1 = new RecordAppointmentEntity();
	public EXPViewController() {
		expview = new ExpView();
	}
	
	public void checkApp(String appID1){
	//	RAE = new RecordAppointmentEntity(appID1,"search");
		RAE1.appID=appID1;
		RAE1.taskToDo="search";
		MainClass.ghealth.sendMessegeToServer(RAE1);
		System.out.println(MainClass.masterControler.EXPVCont.RAE1.appointment.getIdpatient());
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
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

		if(arg instanceof RecordAppointmentEntity){
		
			RAE1.appointment.setIdpatient(((RecordAppointmentEntity)arg).appointment.getIdpatient());
			refreshView();
		}
		
	}
}