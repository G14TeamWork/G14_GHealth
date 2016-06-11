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
import Entities.Reference;
public class  RecordAppointController implements Observer,IRefresh, Serializable  {
	
	private static final long serialVersionUID = 1L;
	public RecordAppointView RecordAppointview;
	public Reference ref;
	
	public RecordAppointController() {
		RecordAppointview = new RecordAppointView();
		
	}
	public void saveRecord(RecordAppointmentEntity RAE){
		System.out.println(MainClass.masterControler.EXPVCont.RAE1.appointment.getRecord()+"AAAAAAAAAAAAAA");
		MainClass.ghealth.sendMessegeToServer(RAE);
	}
	public void serverSaveRecord(RecordAppointmentEntity rae){
		
		String query = "UPDATE ghealth.appointments SET record='" + rae.appointment.getRecord() + 
				"', appStatus='" + rae.appointment.getAppstatus() + "',realStart='"+ rae.appointment.getStartS()
				+ "', realEnd='" + rae.appointment.getEndS() + "' WHERE idappointment=" + rae.appID;
		GHealthServer.sqlConn.sendSqlUpdate(query);
	}
	
	public void handleProduceBtn(String reftype){
		ref= new Reference(MainClass.masterControler.EXPVCont.RAE1.appointment.getIdpatient(),(String)RecordAppointview.rtypes.getSelectedItem(),MainClass.masterControler.EXPVCont.RAE1.appID);
		MainClass.ghealth.sendMessegeToServer(ref);
	}
	public void serverCreateRef(Reference REF){
		ArrayList<Object> arrList = new ArrayList<Object>();
		String query = "SELECT MAX(idreferences) FROM ghealth.references";
		//int temp;
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		REF.setId(Integer.valueOf((String)arrList.get(0))+1);
		query = "INSERT INTO `ghealth`.`references` (`idreferences`, `idpatient`, `reftype`, `appid`) VALUES ('"+String.valueOf(REF.getId()) +
				"', '"+ REF.getPatid() +"', '" +REF.getReftype() + "', '" + REF.getAppid() + "')";
		GHealthServer.sqlConn.sendSqlUpdate(query);
	}
	
	public void removeReferences (){
		MainClass.masterControler.EXPVCont.RAE1.taskToDo = "rmref";
		MainClass.ghealth.sendMessegeToServer(MainClass.masterControler.EXPVCont.RAE1);
	}
	public void serverRemoveReferences(RecordAppointmentEntity rae){
		String query = "DELETE FROM `ghealth`.`references` WHERE `appid`='" + rae.appID + "'";
		GHealthServer.sqlConn.sendSqlUpdate(query);
	}
	
	@Override
	public void refreshView() {
			//MainClass.masterControler.setView(panel, cont);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof Reference){
			RecordAppointview.record.setText(RecordAppointview.record.getText()+ "\nNew reference ( " + ((Reference)arg).getReftype() +
			 " ) was set ( Reference No. " + ((Reference)arg).getId()+	" ).");	
		}
	}
}