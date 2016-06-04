package Controllers;

import java.util.Observable;
import java.util.Observer;
import views.GeneralManagerView;
import Controllers.IRefresh;

public class GeneralManagerController implements Observer,IRefresh  {
	public GeneralManagerView generalmanagerview;
	
	public GeneralManagerController() {
		generalmanagerview = new GeneralManagerView();
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
