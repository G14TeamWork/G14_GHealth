package Controllers;///////yoni gal

import java.util.Observable;//////fish
import java.util.Observer;

import views.CancelAppointmentView;
import Controllers.IRefresh;

public class CancelAppointmentController implements Observer,IRefresh  {
	public  CancelAppointmentView  CancelAppointmentview;
	
	public CancelAppointmentController() {
		 CancelAppointmentview = new  CancelAppointmentView();
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
