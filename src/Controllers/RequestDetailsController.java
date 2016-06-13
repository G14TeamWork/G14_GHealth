package Controllers;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ocsf.server.GHealthServer;
import mainPackage.MainClass;
import views.RequestDetailsView;
import Controllers.IRefresh;
import Entities.MedicalFile;

/**
 * This clas is request details controller
 * is in charge of getting medical files for patients
 * @author Ruslan
 *@param requestdetailview - panel that contains all buttons and features of request details
 * @param mf - medical file entity - used to be sent between client and server
 */
public class RequestDetailsController implements Observer,IRefresh, Serializable  {
	
	private static final long serialVersionUID = 1L;
	public RequestDetailsView RequestDetailsview;
	public MedicalFile mf;
	
	/**
	 * constructor - creates new requestdetails view
	 * @return like every constructor - returns its' type..
	 */
	public RequestDetailsController() {
		RequestDetailsview = new RequestDetailsView();
	}
	
	/**
	 * client side. preparing mf to be sent to server. get medical file of patient ID.
	 * @param patID - patient id of whom medical file is requested
	 */
	public void getMedicalFile(String patID){
		mf = new MedicalFile(patID);
		MainClass.ghealth.sendMessegeToServer(mf);
	}
	
	/**
	 * server side. should transfer file to client 
	 * gets file from sql
	 * @param MF - message received from client and sent back to him with details
	 * @param query - the string that is sent to sql
	 * @param arrList - the receiver of sql's answer
	 */
	public void serverGetMedicalFile(MedicalFile MF){
		ArrayList<Object> arrList = new ArrayList<Object>();
	
		String query = "SELECT * FROM ghealth.medicalfile WHERE idpatient='" + MF.getPatID()+"'" ;
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		if (!arrList.isEmpty()){
			MF.exists = true;
			
			MF.setCardio((String)arrList.get(1));
			MF.setNeuro((String)arrList.get(2));
			MF.setGenyc((String)arrList.get(3));
			MF.setOnco((String)arrList.get(4));
			query = "SELECT * FROM ghealth.patient WHERE id='" + MF.getPatID()+"'" ;
			arrList = new ArrayList<Object>();
			arrList = GHealthServer.sqlConn.sendSqlQuery(query);
			if (arrList.isEmpty()){
				MF.exists = false;
			}
			else MF.setPatName((String)arrList.get(1)+" "+(String)arrList.get(2));
		}
		else
			MF.exists = false;	
	}
	
	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof MedicalFile){
			mf.setCardio(((MedicalFile)arg).getCardio());
			mf.setGenyc(((MedicalFile)arg).getGenyc());
			mf.setOnco(((MedicalFile)arg).getOnco());
			mf.setNeuro(((MedicalFile)arg).getNeuro());
			mf.setPatName(((MedicalFile)arg).getPatName());
			if (((MedicalFile)arg).exists){
				MainClass.masterControler.RDCont.RequestDetailsview.errorlbl.setText("Patient Name: " + mf.getPatName());
				MainClass.masterControler.RDCont.RequestDetailsview.errorlbl.setForeground(Color.BLACK);
				MainClass.masterControler.RDCont.RequestDetailsview.btnEntireFile.setEnabled(true);
				MainClass.masterControler.RDCont.RequestDetailsview.btnBySpec.setEnabled(true);
				if(mf.getCardio()==null) 
					mf.setCardio("No cardiologic file.");
				if(mf.getNeuro()==null)
					mf.setNeuro("No neurologic file.");
				if(mf.getGenyc()==null) 
					mf.setGenyc("No geniologic file.");
				if(mf.getOnco()==null)
					mf.setOnco("No oncologic file.");
			}
			else{
				RequestDetailsview.fileArea.setText("");
				MainClass.masterControler.RDCont.RequestDetailsview.errorlbl.setText("Please enter valid patient ID.");
				MainClass.masterControler.RDCont.RequestDetailsview.errorlbl.setForeground(Color.RED);
				MainClass.masterControler.RDCont.RequestDetailsview.btnEntireFile.setEnabled(false);
				MainClass.masterControler.RDCont.RequestDetailsview.btnBySpec.setEnabled(false);
			}
		}
	}
	/**
	 * method that get medical file and to strings it. 
	 * @return str is the string that is created.
	 */
	public String entireFileFormat(){
		String str="";
		
		str = str + "Patient name : \t" + mf.getPatName() + "  \tPatient ID = " + mf.getPatID();
		str = str + "\n\n";
		str = str + "Cardiology history : \t" + mf.getCardio();
		str = str + "\n\n";
		str = str + "Neurology history : \t" + mf.getNeuro();
		str = str + "\n\n";
		str = str + "Genycology history : \t" + mf.getGenyc();
		str = str + "\n\n";
		str = str + "Oncology history : \t" + mf.getOnco();
		
		return  str;
	}
	
	/**
	 * function that manages comboBox changing items.
	 * in case of switching seleceted item.
	 */
	public void manageComboBox(){
		if (RequestDetailsview.specs.getSelectedItem().equals("Cardiology")){
			RequestDetailsview.fileArea.setText("Cardiology history : " + mf.getCardio());
		}else if (RequestDetailsview.specs.getSelectedItem().equals("Neurology")){
			RequestDetailsview.fileArea.setText("Neurology history : " + mf.getNeuro());
		}else if (RequestDetailsview.specs.getSelectedItem().equals("Genycology")){
			RequestDetailsview.fileArea.setText("Genycology history : " + mf.getGenyc());
		}else if (RequestDetailsview.specs.getSelectedItem().equals("Oncology")){
			RequestDetailsview.fileArea.setText("Oncology history : " + mf.getOnco());
		}
	}
}