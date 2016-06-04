package Controllers;

import java.util.Observable;
import java.util.Observer;

import views.RecordAppointView;
import Controllers.IRefresh;

public class  RecordAppointController implements Observer,IRefresh  {
	public RecordAppointView RecordAppointview;
	
	public RecordAppointController() {
		RecordAppointview = new RecordAppointView();
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