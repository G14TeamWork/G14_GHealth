package Controllers;

import java.util.Observable;
import java.util.Observer;

import views.ViewPeriodicReportView;
import Controllers.IRefresh;

public class ViewPeriodicReportController implements Observer,IRefresh  {
	public ViewPeriodicReportView ViewPeriodicReportview;
	
	public ViewPeriodicReportController() {
		ViewPeriodicReportview = new ViewPeriodicReportView();
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