package Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ocsf.server.GHealthServer;
import views.CancelAppointmentView;
import Controllers.IRefresh;

public class CancelAppointmentController implements Observer,IRefresh  {
	public  CancelAppointmentView  CancelAppointmentview;
	
	public CancelAppointmentController() {
		 CancelAppointmentview = new  CancelAppointmentView();

	}
	
	public void toSQL(String idpatient )
	{
				ArrayList<Object> arrList = new ArrayList<Object>();
				String query="	SELECT  ghealth.appointments.appdate,ghealth.expert.experties,ghealth.users.firstname,ghealth.users.lastname"
								+"FROM ghealth.appointments,ghealth.users,ghealth.expert" 
								+"WHERE ghealth.appointments.idpatient="+ idpatient+"AND"
								+"ghealth.appointments.idexpert = ghealth.expert.id AND"
								+"ghealth.users.username = ghealth.expert.id";
				arrList = GHealthServer.sqlConn.sendSqlQuery(query);
				for (int i  = 0 ; i < arrList.size() ; i +=4)
				CancelAppointmentview.getComboBox().addItem((String)arrList.get(i)+" "+ (String)arrList.get(i+1)+" "+(String)arrList.get(i+2)+" "+(String)arrList.get(i+3));
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