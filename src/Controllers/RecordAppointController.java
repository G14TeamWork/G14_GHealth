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
/**
 * This class is the record appointment controller.
 * in charge of all actions in record appointment window.
 * @author Ruslan
 *@param RecordAppointview - panel with all record appointment features
 */
public class  RecordAppointController implements Observer,IRefresh, Serializable  {
	private static final long serialVersionUID = 1L;
	public RecordAppointView RecordAppointview;
	public Reference ref;
	/**
	 * constructor - creates new record appointment view
	 * @return like every constructor returns the type he got
	 */
	public RecordAppointController() {
		RecordAppointview = new RecordAppointView();
		
	}
	/**
	 * This method receives record appointment entity and sends it to sql. 
	 * preperation of RAE was done before.
	 * client side.
	 * @param RAE contains all required instructions for server
	 */
	public void saveRecord(RecordAppointmentEntity RAE){
		MainClass.ghealth.sendMessegeToServer(RAE);
	}
	/**
	 * in charge of updating appointment record, status, start and end time in db of recorded appointment.
	 * server side.
	 * @param rae -message sent from client.
	 */
	public void serverSaveRecord(RecordAppointmentEntity rae){
		
		String query = "UPDATE ghealth.appointments SET record='" + rae.appointment.getRecord() + 
				"', appStatus='" + rae.appointment.getAppstatus() + "',realStart='"+ rae.appointment.getStartS()
				+ "', realEnd='" + rae.appointment.getEndS() + "' WHERE idappointment=" + rae.appID;
		GHealthServer.sqlConn.sendSqlUpdate(query);
	}
	
	/**
	 * this method is in charge of managin produce button that is in record appointment view.
	 * client side.
	 * sets ref ready and sends it to server in order to add new ref 
	 * @param reftype the item in the scroll box before button was pressed.
	 */
	public void handleProduceBtn(String reftype){
		ref= new Reference(MainClass.masterControler.EXPVCont.RAE1.appointment.getIdpatient(),(String)RecordAppointview.rtypes.getSelectedItem(),MainClass.masterControler.EXPVCont.RAE1.appID);
		MainClass.ghealth.sendMessegeToServer(ref);
	}
	/**
	 * sever side of creating reference
	 * in charge of adding new ref to db and updating ref id counter
	 * @param REF = message received from client, orders what to insert
	 * @param string - query that will be sent to db
	 */
	public void serverCreateRef(Reference REF){
		ArrayList<Object> arrList = new ArrayList<Object>();
		String query = "SELECT MAX(idcounter_ref) FROM ghealth.counter_ref";
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		REF.setId(Integer.valueOf((String)arrList.get(0))+1);
		query = "INSERT INTO `ghealth`.`counter_ref` (`idcounter_ref`) VALUES (" + String.valueOf(REF.getId())+")";
		GHealthServer.sqlConn.sendSqlUpdate(query);
		query = "INSERT INTO `ghealth`.`references` (`idreferences`, `idpatient`, `reftype`, `appid`) VALUES ('"+String.valueOf(REF.getId()) +
				"', '"+ REF.getPatid() +"', '" +REF.getReftype() + "', '" + REF.getAppid() + "')";
		GHealthServer.sqlConn.sendSqlUpdate(query);
	}
	/**
	 * method in charge of removing references for certain appointment id
	 * .prepares RAE1 and sends it to server
	 */
	public void removeReferences (){
		MainClass.masterControler.EXPVCont.RAE1.taskToDo = "rmref";
		MainClass.ghealth.sendMessegeToServer(MainClass.masterControler.EXPVCont.RAE1);
	}
	/**
	 * receives data from client
	 * incharge of sending delete request to db
	 * . delete is done based on  app id
	 * @param rae - contains appid to delete
	 */
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