package Controllers;

import java.util.Observable;
import java.util.Observer;

import views.ProduceMonthlyReportView;
import Controllers.IRefresh;
/**
 * This class is for making connections to db when producing monthly report
 * in charge of all server client connection.
 * @param ProduceMonthlyReportview is the panel opened when  produce monthly reports is pressed.
 * @author Ruslan
 *
 */
public class ProduceMonthlyReportController implements Observer,IRefresh  {
	public ProduceMonthlyReportView ProduceMonthlyReportview;
	
	public ProduceMonthlyReportController() {
		ProduceMonthlyReportview = new ProduceMonthlyReportView();
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