package Controllers;

import java.util.Observable;
import java.util.Observer;

import views.ClinicManagerView;
import views.DispatcherView;
import views.LoginView;
import Controllers.IRefresh;

public class ClinicManagerController implements Observer,IRefresh  {
	public ClinicManagerView clinicmanagerview;
	
	public ClinicManagerController() {
		clinicmanagerview = new ClinicManagerView();
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
