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
		RAE1.appID=appID1;
		RAE1.taskToDo="search";
		MainClass.ghealth.sendMessegeToServer(RAE1);
	}
	public void checkAppSQL(RecordAppointmentEntity rae) {
		ArrayList<Object> arrList = new ArrayList<Object>();
		String query = "SELECT * FROM ghealth.appointments WHERE idappointment ='" + rae.appID+"'" ;
			arrList=GHealthServer.sqlConn.sendSqlQuery(query);
		if (!arrList.isEmpty()){
			rae.appointment.setIdpatient(String.valueOf((int)arrList.get(2)));
			rae.appointment.setRecord((String)arrList.get(11));
		}
		else
			rae.appointment.setIdpatient(null);
	}
	
	@Override
	public void refreshView() {
		
	}

	@Override
	public void update(Observable o, Object arg) {
			if(arg instanceof RecordAppointmentEntity)
				if(((RecordAppointmentEntity)arg).taskToDo.equals("search")){
					MainClass.masterControler.EXPVCont.RAE1.appointment.setIdpatient(((RecordAppointmentEntity)arg).appointment.getIdpatient());
					MainClass.masterControler.EXPVCont.RAE1.appointment.setRecord(((RecordAppointmentEntity)arg).appointment.getRecord());
				}
		
	}
}