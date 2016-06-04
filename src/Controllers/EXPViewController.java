package Controllers;

import java.util.Observable;
import java.util.Observer;

import views.ExpView;
import views.LoginView;
import Controllers.IRefresh;

public class EXPViewController implements Observer,IRefresh  {
	public ExpView expview;
	
	public EXPViewController() {
		expview = new ExpView();
	}
	
	public void saveApp(){
		
		
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
