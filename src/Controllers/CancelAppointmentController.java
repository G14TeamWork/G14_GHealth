package Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
				System.out.println(arrList);
				

				for (int i  = 0 ; i < arrList.size() ; i +=4)
				{
					cap.getAppList().add(new Appointment(arrList.get(i).toString(),(String)arrList.get(i+1),(String)arrList.get(i+2),(String)arrList.get(i+3)));
				}
				
	}
		
	@Override
	public void refreshView() {
		MainClass.masterControler.setView(MainClass.masterControler.CACont.CancelAppointmentview);		
	}

	@Override
	public void update(Observable o, Object arg) {
		//if(arg)
		/*if (arg instanceof Appointment )
		{
			
			for (int i  = 0 ; i < appList.size() ; i ++)
			CancelAppointmentview.getComboBox().addItem(appList.get(i).getAppDate()+" "+ appList.get(i).getExperties()+" "+appList.get(i).getFirstName()+" "+appList.get(i).getLastName());
			refreshView();
		}*/
	}
}