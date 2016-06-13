package Controllers;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFormattedTextField;

import views.GeneralManagerView;
import Controllers.IRefresh;
/**
 * This clan controls all general manager features.
 * All connections to server are through here
 * @param GeneralManagerView is a panel that opens when gerneral manager logs in
 * @author Ruslan
 *
 */
public class GeneralManagerController implements Observer,IRefresh  {
	public GeneralManagerView generalmanagerview;
	
	public GeneralManagerController() {
		generalmanagerview = new GeneralManagerView();
	}
	public Boolean checkClinicExist(String idClinic)
	{
		return null;
		
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
