package Controllers;

import java.util.Observable;
import java.util.Observer;

import views.viewLabResuView;
import Controllers.IRefresh;

public class viewLabResuControlller implements Observer,IRefresh  {
	public viewLabResuView viewLabResuview;
	
	public viewLabResuControlller() {
		viewLabResuview = new viewLabResuView();
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