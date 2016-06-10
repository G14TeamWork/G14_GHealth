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

public class RequestDetailsController implements Observer,IRefresh, Serializable  {
	
	private static final long serialVersionUID = 1L;
	public RequestDetailsView RequestDetailsview;
	public MedicalFile mf;
	
	public RequestDetailsController() {
		RequestDetailsview = new RequestDetailsView();
	}
	
	public void getMedicalFile(String patID){
		mf = new MedicalFile(patID);
		MainClass.ghealth.sendMessegeToServer(mf);
	}
	
	public void serverGetMedicalFile(MedicalFile MF){
		ArrayList<Object> arrList = new ArrayList<Object>();
	
		String query = "SELECT * FROM ghealth.medicalfile WHERE idpatient=" + MF.getPatID() ;
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		if (!arrList.isEmpty()){
			MF.exists = true;
			
			MF.setCardio((String)arrList.get(1));
			MF.setNeuro((String)arrList.get(2));
			MF.setGenyc((String)arrList.get(3));
			MF.setOnco((String)arrList.get(4));
			
			query = "SELECT * FROM ghealth.patient WHERE id=" + MF.getPatID() ;
			
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
			}
			else{
				MainClass.masterControler.RDCont.RequestDetailsview.errorlbl.setText("Please enter valid patient ID.");
				MainClass.masterControler.RDCont.RequestDetailsview.errorlbl.setForeground(Color.RED);
			}
		}
	}
}