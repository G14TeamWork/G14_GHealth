package Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import mainPackage.MainClass;
import ocsf.server.GHealthServer;
import views.CancelAppointmentView;
import Controllers.IRefresh;
import Entities.Appointment;
import Entities.CancelAppointmentEntity;
import Entities.Patient;

public class CancelAppointmentController implements Observer,IRefresh  {
	public  CancelAppointmentView  CancelAppointmentview;
	public CancelAppointmentEntity CAP;
	
	public CancelAppointmentController() {
		 CancelAppointmentview = new  CancelAppointmentView();

	}
	public void searchAppointments(){
		CAP = new CancelAppointmentEntity("search" , CancelAppointmentview.getSearchField().getText());
		 MainClass.ghealth.sendMessegeToServer(CAP);
	}
	
	public void searchAppintmentSQL(CancelAppointmentEntity cap )
	{
				ArrayList<Object> arrList = new ArrayList<Object>();
				String query="SELECT ghealth.appointments.appdate,ghealth.expert.experties,ghealth.users.firstname,ghealth.users.lastname"
								+" FROM ghealth.appointments,ghealth.users,ghealth.expert" 
								+" WHERE ghealth.appointments.idpatient=" + cap.getIdPatient()+" AND"
								+" ghealth.appointments.idexpert = ghealth.expert.id AND"
								+" ghealth.users.username = ghealth.expert.id";
				arrList = GHealthServer.sqlConn.sendSqlQuery(query);
				System.out.println(arrList  + arrList.get(0).getClass().getName() + arrList.get(1).getClass().getName() + arrList.get(2).getClass().getName() + arrList.get(3).getClass().getName());
				

				for (int i  = 0 ; i < arrList.size() ; i +=4)
				{
					cap.getAppList().add(new Appointment((Timestamp)arrList.get(i),(String)arrList.get(i+1),(String)arrList.get(i+2),(String)arrList.get(i+3)));
				}
				
	}
		
	@Override
	public void refreshView() {
		MainClass.masterControler.setView(MainClass.masterControler.CACont.CancelAppointmentview);		
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if (arg instanceof CancelAppointmentEntity )
		{
			
			for (int i  = 0 ; i < ((CancelAppointmentEntity)arg).getAppList().size() ; i ++)
			CancelAppointmentview.getComboBox().addItem(
					
					((CancelAppointmentEntity)arg).getAppList().get(i).getAppdate().toString()
					+" "+ ((CancelAppointmentEntity)arg).getAppList().get(i).getEX().getFirstName()
					+" "+((CancelAppointmentEntity)arg).getAppList().get(i).getEX().getLastName()
					+" "+((CancelAppointmentEntity)arg).getAppList().get(i).getEX().getExperties()
					);
			refreshView();
		}
	}
}