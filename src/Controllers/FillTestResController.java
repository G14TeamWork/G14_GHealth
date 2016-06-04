package Controllers;

import java.util.Observable;
import java.util.Observer;
import views.FillTestResView;
import Controllers.IRefresh;

public class FillTestResController implements Observer,IRefresh  {
	public FillTestResView FillTestResview;
	
	public FillTestResController() {
		FillTestResview = new FillTestResView();
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