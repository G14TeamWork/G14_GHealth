package Controllers;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import mainPackage.MainClass;
import ocsf.server.GHealthServer;
import views.RecordAppointView;
import Controllers.IRefresh;
import Entities.FillTestResEntity;
import Entities.RecordAppointmentEntity;
public class  RecordAppointController implements Observer,IRefresh, Serializable  {
	
	private static final long serialVersionUID = 1L;
	public RecordAppointView RecordAppointview;
	
	public RecordAppointController() {
		RecordAppointview = new RecordAppointView();
		
	}
	public void saveRecord(){
		String str = RecordAppointview.record.getText();
		
	}
	
	@Override
	public void refreshView() {
			//MainClass.masterControler.setView(panel, cont);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
	}
}