package Controllers;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ocsf.server.GHealthServer;
import mainPackage.MainClass;
import views.ViewRefDetView;
import Controllers.IRefresh;
import Entities.RecordAppointmentEntity;
import Entities.RefDetailsEntity;
import Entities.Reference;
/**
 * This class is the controller of view request details. 
 * is responsible for  client server interaction. 
 * @param rae  - record appointment entity - is here as msg sent to server.
 * @param ViewRefDetview - is the panel view of view ref details window
 * @param rde  - entity that is used as msg same as rae.
 * @author Ruslan
 *
 */
public class ViewRefDetController implements Observer,IRefresh, Serializable {
	private static final long serialVersionUID = 1L;
	public ViewRefDetView ViewRefDetview;
	public RefDetailsEntity rde = new RefDetailsEntity();
	public RecordAppointmentEntity rae;
	/**
	 * constructor - creates view ref det view
	 * return returnd view ref det controller
	 */
	public ViewRefDetController() {
		ViewRefDetview = new ViewRefDetView();
	}
	/**
	 * prepares rde as message to server for getting patient's references
	 * client side
	 * @param ID gets appointment ID and sets it in rde. 
	 */
	public void getPatientRefs(String ID){
		rde.patID = ID;
		rde.patName = null;
		rde.rlist = new ArrayList<Reference>();
		MainClass.ghealth.sendMessegeToServer(rde);
	}
	
	/**
	 * method in charge of getting from sql all references given in an appointment
	 * server side
	 * param query - string that is sent to sql 
	 * @param RDE - reference detail entity - used as the message between client and server
	 * param arrList -used to save reply from sql to query
	 */
	public void serverGetPatientRefs(RefDetailsEntity RDE){
	
		ArrayList<Object> arrList = new ArrayList<Object>();
		String query = "SELECT * FROM ghealth.patient WHERE id='" + RDE.patID +"'" ;
		int i;
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		if (!arrList.isEmpty()){
			RDE.patName=((String)arrList.get(1)+" "+(String)arrList.get(2));
			query = "SELECT * FROM ghealth.`references` WHERE idpatient='" + RDE.patID + "'";
			i=0;
			arrList = GHealthServer.sqlConn.sendSqlQuery(query);
			while (!arrList.isEmpty()){
				RDE.rlist.add(new Reference());
				RDE.rlist.get(i).setId(Integer.valueOf((String)(arrList.get(0))));
				arrList.remove(0);
				RDE.rlist.get(i).setPatid((String)(arrList.get(0)));
				arrList.remove(0);
				RDE.rlist.get(i).setReftype((String)(arrList.get(0)));
				arrList.remove(0);
				RDE.rlist.get(i).setAppid((String)(arrList.get(0)));
				arrList.remove(0);
				i++;
			}
		}else RDE.patName="0";
	}
	
	/**
	 * this function manages gui after update is done. all buttons set and so on
	 * in charge of setting buttons visible and etc...
	 */
	public void manageGUI(){
		int tmp;
		if(rde.patName.equals("0")){
			ViewRefDetview.lblErrorLabel.setVisible(true);
			ViewRefDetview.lblErrorLabel.setForeground(Color.RED);
			ViewRefDetview.lblErrorLabel.setText("Wrong patient ID!");
			ViewRefDetview.comboRefs.setSelectedItem("");
			ViewRefDetview.comboRefs.setEnabled(false);
		}else if(rde.rlist.isEmpty()){
			ViewRefDetview.lblErrorLabel.setVisible(true);
			ViewRefDetview.lblErrorLabel.setForeground(Color.RED);
			ViewRefDetview.lblErrorLabel.setText("No Open references for this patient!");
			ViewRefDetview.comboRefs.setSelectedItem("");
			ViewRefDetview.comboRefs.setEnabled(false);
		}else{
			ViewRefDetview.lblErrorLabel.setVisible(true);
			ViewRefDetview.lblErrorLabel.setForeground(Color.BLACK);
			ViewRefDetview.lblErrorLabel.setText( "Patient Name : " + rde.patName );
			ViewRefDetview.comboRefs.setEnabled(true);
			ViewRefDetview.comboRefs.setSelectedItem("");
			ViewRefDetview.comboRefs.setEnabled(true);
			ViewRefDetview.comboRefs.addItem("");
			for(int i = 0  ; i < rde.rlist.size() ; i++ ) {
				tmp = i+1;
				ViewRefDetview.comboRefs.addItem(tmp + " - "+ rde.rlist.get(i).getReftype());
			}
		}
	}
	
	/**
	 * this method is in charge of getting record appointmnet for certain reference
	 * client side
	 * @param index = is the index of array list in rde  ( index of reference in arraylist )
	 * @return returns appointment record
	 */
	public void getDetailsFromList(int index){
		String appid = "";
	
		appid = rde.rlist.get(index).getAppid();
		rae = new RecordAppointmentEntity();
		rae.appID = appid;
		rae.taskToDo = "getRecord";
		MainClass.ghealth.sendMessegeToServer(rae);
	}
	/** 
	 * server side
	 * in charge of detting record of a certain appointment.
	 * set record that is braught from db in RAE
	 * @param RAE message between server and client. will contain the record of the appointment
	 */
	public void serverGetRecord(RecordAppointmentEntity RAE){
		ArrayList<Object> arrList = new ArrayList<Object>();
		String query = "SELECT record FROM ghealth.appointments WHERE idappointment='" + RAE.appID +"'";
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		RAE.appointment.setRecord((String)arrList.get(0));
	}
	
	@Override
	public void refreshView() {
		
		
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if (arg instanceof RefDetailsEntity){
			rde.rlist = ((RefDetailsEntity)arg).rlist;
			rde.patName = ((RefDetailsEntity)arg).patName;
			manageGUI();
		}else if (arg instanceof RecordAppointmentEntity){
			if (((RecordAppointmentEntity)arg).taskToDo.equals("getRecord"))
			{
				rae.appointment.setRecord(((RecordAppointmentEntity)arg).appointment.getRecord());
				ViewRefDetview.redDetails.setText(rae.appointment.getRecord());
			}
		}
		
	}
}