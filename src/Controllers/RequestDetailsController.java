package Controllers;

import java.util.Observable;
import java.util.Observer;

import mainPackage.MainClass;
import views.RequestDetailsView;
import Controllers.IRefresh;
import Entities.MedicalFile;

public class RequestDetailsController implements Observer,IRefresh  {
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
		
	
	}
	
	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
	}
}