package Controllers;

import java.util.Observable;
import java.util.Observer;

import views.DispatcherView;
import views.LoginView;
import Controllers.IRefresh;
/**
 * This class implements observer and irefesh.
 * it was created to simplify actions and panels switch.
 * @param dispatcherview is the panel of all dispatcher features
 * @author Ruslan
 *
 */
public class DispatcherController implements Observer,IRefresh  {
	public DispatcherView dispatcherview;
	
	/**
	 * @return this is the constructor of dispatcher controller. creates a new view in it
	 */
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
