package Controllers;

import java.util.Observable;
import java.util.Observer;

import views.ViewReportsView;
import Controllers.IRefresh;

public class ViewReportsController implements Observer,IRefresh  {
	public ViewReportsView ViewReportsview;
	
	public ViewReportsController() {
		ViewReportsview = new ViewReportsView();
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