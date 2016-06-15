package Controllers;

import java.util.Observable;
import java.util.Observer;

import views.ProduceMonthlyReportView;
import views.ViewReportsView;
import Controllers.IRefresh;
/**
 * This class is the controller of viewing reports
 * This class is in charge of all server client communication.
 * also this class checks if reports exist or not
 * @param ViewReportsview is a panel that opens when view reports is pressed.
 * @author Ruslan
 *
 */
public class ViewReportsController implements Observer,IRefresh  {
	public ViewReportsView ViewReportsview;
	public ProduceMonthlyReportView ProduceMonthlyReportView; 
	
	public ViewReportsController() {
		ViewReportsview = new ViewReportsView();
		ProduceMonthlyReportView = new ProduceMonthlyReportView();
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