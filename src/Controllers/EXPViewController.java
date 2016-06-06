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
	
	public void checkApp(String appID){
		RAE = new RecordAppointmentEntity(appID,"search");
		MainClass.ghealth.sendMessegeToServer(RAE);
		RAE.appointment.getIdpatient();
	}
	public void checkAppSQL() {
		ArrayList<Object> arrList = new ArrayList<Object>();
		String query = "SELECT * FROM ghealth.appointments WHERE idappointment =" + RAE.appID;
		arrList=GHealthServer.sqlConn.sendSqlQuery(query);
		RAE.appointment.setIdpatient((String)arrList.get(2));
	}
	
	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if(arg instanceof RecordAppointmentEntity){
			if (((RecordAppointmentEntity)arg).appointment.getIdpatient()!=null){
				MainClass.masterControler.RACont = new RecordAppointController();
				MainClass.masterControler.RACont.RecordAppointview.getPatientID().setText("Patient ID : " +
						((RecordAppointmentEntity)arg).appointment.getIdpatient() );
				MainClass.masterControler.setView(
						MainClass.masterControler.RACont.RecordAppointview,
							MainClass.masterControler.RACont);
				
			}
				
		}
		
	}

}
