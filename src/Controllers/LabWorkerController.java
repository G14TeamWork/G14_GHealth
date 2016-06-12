package Controllers;

import java.util.Observable;
import java.util.Observer;

import views.LabWorkerView;
import views.LoginView;
import Controllers.IRefresh;
/**
 * This is a class that contains lab worker view.
 * This class is created in order to simplify switching panels
 * @author Ruslan
 * @param labworkerview is the panel that holds all labworker features
 */
public class LabWorkerController implements Observer,IRefresh  {
	public  LabWorkerView labworkerview;
	
	/**
	 * this is labworkercontroller constructor  - creates labworkercontroller and a creates a new view.
	 * @return it is a constructor therefore it obviously returns the LabWorkerController type..
	 */
	public LabWorkerController() {
		labworkerview = new LabWorkerView();
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
