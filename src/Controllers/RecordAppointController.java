package Controllers;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import mainPackage.MainClass;
import ocsf.server.GHealthServer;
import views.RecordAppointView;
import Controllers.IRefresh;
import Entities.FillTestResEntity;
import Entities.RecordAppointmentEntity;
public class  RecordAppointController implements Observer,IRefresh, Serializable  {
	
	private static final long serialVersionUID = 1L;
	public RecordAppointView RecordAppointview;
	
	public RecordAppointController() {
		RecordAppointview = new RecordAppointView();
		
	}
	public void saveRecord(RecordAppointmentEntity RAE){
		MainClass.ghealth.sendMessegeToServer(RAE);
	}
	public void serverSaveRecord(RecordAppointmentEntity rae){
		String query = "UPDATE ghealth.appointments SET appointments.record='" + rae.appointment.getRecord() + "' WHERE idappointment=" + rae.appID;
		GHealthServer.sqlConn.sendSqlUpdate(query);
		//"UPDATE ghealth.users SET status=0 " +
	//	"WHERE username =" + LE.getUsername();
	}
	
	@Override
	public void refreshView() {
			//MainClass.masterControler.setView(panel, cont);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
	}
}