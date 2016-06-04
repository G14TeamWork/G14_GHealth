package Controllers;

import java.util.Observable;
import java.util.Observer;

import views.ViewMedicalHistoryView;
import Controllers.IRefresh;

public class ViewMedicalHistoryController implements Observer,IRefresh  {
	public ViewMedicalHistoryView ViewMedicalHistoryview;
	
	public ViewMedicalHistoryController() {
		ViewMedicalHistoryview = new ViewMedicalHistoryView();
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