package Controllers;

import java.util.Observable;
import java.util.Observer;

import views.RequestDetailsView;
import Controllers.IRefresh;

public class RequestDetailsController implements Observer,IRefresh  {
	public RequestDetailsView RequestDetailsview;
	
	public RequestDetailsController() {
		RequestDetailsview = new RequestDetailsView();
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