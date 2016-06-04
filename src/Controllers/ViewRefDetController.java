package Controllers;

import java.util.Observable;
import java.util.Observer;
import views.ViewRefDetView;
import Controllers.IRefresh;

public class ViewRefDetController implements Observer,IRefresh  {
	public ViewRefDetView ViewRefDetview;
	
	public ViewRefDetController() {
		ViewRefDetview = new ViewRefDetView();
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