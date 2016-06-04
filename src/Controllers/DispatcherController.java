package Controllers;

import java.util.Observable;
import java.util.Observer;

import views.DispatcherView;
import views.LoginView;
import Controllers.IRefresh;

public class DispatcherController implements Observer,IRefresh  {
	public DispatcherView dispatcherview;
	
	public DispatcherController() {
		dispatcherview = new DispatcherView();
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
