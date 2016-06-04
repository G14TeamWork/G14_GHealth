package Controllers;

import java.util.Observable;
import java.util.Observer;

import views.LabWorkerView;
import views.LoginView;
import Controllers.IRefresh;

public class LabWorkerController implements Observer,IRefresh  {
	public  LabWorkerView labworkerview;
	
	public LabWorkerController() {
		labworkerview = new LabWorkerView();
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
