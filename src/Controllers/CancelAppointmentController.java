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
	private boolean flag = false;
	

	public CancelAppointmentController() {
		 CancelAppointmentview = new  CancelAppointmentView();

	}
	public void searchAppointments(){
		this.CAP = new CancelAppointmentEntity("search" , CancelAppointmentview.getSearchField().getText());
		 MainClass.ghealth.sendMessegeToServer(CAP);
	}
	public void cancelAppointment()
	{
		CAP = new CancelAppointmentEntity("delete" ,((Appointment)CancelAppointmentview.getComboBox().getSelectedItem()).getIdappointment());
		MainClass.ghealth.sendMessegeToServer(CAP);
	}
	
	public void searchAppointmentSQL(CancelAppointmentEntity cap )
	{
		cap.setAppList(new ArrayList<Appointment>());
				ArrayList<Object> arrList = new ArrayList<Object>();
				String query="SELECT ghealth.appointments.appdate,ghealth.expert.experties,ghealth.users.firstname,ghealth.users.lastname,ghealth.appointments.idappointment"
								+" FROM ghealth.appointments,ghealth.users,ghealth.expert" 
								+" WHERE ghealth.appointments.idpatient=" + cap.getIdPatient()+" AND"
								+" ghealth.appointments.idexpert = ghealth.expert.id AND"
								+" ghealth.users.username = ghealth.expert.id";
				arrList = GHealthServer.sqlConn.sendSqlQuery(query);
			

				for (int i  = 0 ; i < arrList.size() ; i +=5)
					cap.getAppList().add(new Appointment((Timestamp)arrList.get(i),(String)arrList.get(i+1),(String)arrList.get(i+2),(String)arrList.get(i+3),String.valueOf((int)arrList.get(i+4))));

	}
	
	public void deleteAppintmentSQL(CancelAppointmentEntity cap )
	{
		String query="DELETE FROM ghealth.appointments WHERE idappointment="+ cap.getIdapp();
		GHealthServer.sqlConn.sendSqlUpdate(query);
	}
		
	@Override
	public void refreshView() {
		MainClass.masterControler.setView(CancelAppointmentview);	
	}

	@Override
	public void update(Observable o, Object arg) {
	
		if (arg instanceof CancelAppointmentEntity && ((CancelAppointmentEntity)arg).getTaskToDo().equals("search") )
		{
			CancelAppointmentview.getComboBox().removeAllItems();
			if(((CancelAppointmentEntity)arg).getAppList().size() != 0)
			{
				setFlag(true);

				CAP = ((CancelAppointmentEntity)arg);
				for (int i  = 0 ; i < ((CancelAppointmentEntity)arg).getAppList().size() ; i ++)
					CancelAppointmentview.getComboBox().addItem(((CancelAppointmentEntity)arg).getAppList().get(i));
				
				System.out.println(CAP.toString() );
			}
			else setFlag(false);

		}
		if (arg instanceof CancelAppointmentEntity && ((CancelAppointmentEntity)arg).getTaskToDo().equals("delete") )
		{
			System.out.println("appointment"+((CancelAppointmentEntity)arg).getIdapp() + "deleted");
		}
		
	}
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
