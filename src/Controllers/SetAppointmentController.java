package Controllers;

import java.util.Observable;
import java.util.Observer;

import views.SetAppointmentView;
import Controllers.IRefresh;

public class SetAppointmentController implements Observer,IRefresh  {
	public SetAppointmentView SetAppointmentview;
	
	public SetAppointmentController() {
		SetAppointmentview = new SetAppointmentView();
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