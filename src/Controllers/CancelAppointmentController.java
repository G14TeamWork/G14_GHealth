package Controllers;

import java.awt.Color;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Date;

import javax.swing.JOptionPane;

import mainPackage.MainClass;
import ocsf.server.GHealthServer;
import views.CancelAppointmentView;
import Controllers.IRefresh;
import Entities.Appointment;
import Entities.CancelAppointmentEntity;
import Entities.FillTestResEntity;
import Entities.Patient;

public class CancelAppointmentController implements Observer,IRefresh  {
	public  CancelAppointmentView  CancelAppointmentview;
	public CancelAppointmentEntity CAP;

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

	public void checkExistanceSql(CancelAppointmentEntity cap)
	{
		ArrayList<Object> arrList = new ArrayList<Object>();
		String query = "";
		query = "SELECT firstname,lastname FROM ghealth.patient where "
				+ "id = \"" + cap.getIdPatient() + "\"";
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		if (arrList.isEmpty()) cap.setTaskToDo("Error! enter valid patient ID!");
		else{
				cap.setFirstName((String)arrList.get(0));
				cap.setLastName((String)arrList.get(1));
				arrList.clear();
			}
		
	}
	public void searchAppointmentSQL(CancelAppointmentEntity cap )
	{
		cap.setAppList(new ArrayList<Appointment>());
				ArrayList<Object> arrList = new ArrayList<Object>();
				String query="SELECT ghealth.appointments.appdate,ghealth.appointments.start,ghealth.appointments.end,ghealth.expert.experties,ghealth.users.firstname,ghealth.users.lastname,ghealth.appointments.idappointment,ghealth.clinic.Name"
								+" FROM ghealth.appointments,ghealth.users,ghealth.expert,ghealth.clinic" 
								+" WHERE ghealth.appointments.idpatient=" + cap.getIdPatient()+" AND"
								+" ghealth.appointments.idexpert = ghealth.expert.id AND"
								+" ghealth.users.username = ghealth.expert.id AND ghealth.appointments.appstatus=1"
								+" AND ghealth.expert.clinic=ghealth.clinic.idclinic";
				arrList = GHealthServer.sqlConn.sendSqlQuery(query);
			

				for (int i  = 0 ; i < arrList.size() ; i +=8)
					cap.getAppList().add(new Appointment((Date)arrList.get(i),(Time)arrList.get(i+1),(Time)arrList.get(i+2),(String)arrList.get(i+3),(String)arrList.get(i+4),(String)arrList.get(i+5),String.valueOf((int)arrList.get(i+6)),(String)arrList.get(i+7)));

	}
	
	public void deleteAppintmentSQL(CancelAppointmentEntity cap )
	{
		String query="UPDATE ghealth.appointments SET idpatient=0, appstatus=0, dispatcherSettingDate=00000000 ,dispatcherSettingHour=000000,sentemail=0 WHERE idappointment="+cap.getIdapp();
		GHealthServer.sqlConn.sendSqlUpdate(query);
	}
		
	@Override
	public void refreshView() {
		MainClass.masterControler.setView(CancelAppointmentview);	
	}

	@Override
	public void update(Observable o, Object arg) {
	
		if (arg instanceof CancelAppointmentEntity && ((CancelAppointmentEntity)arg).getTaskToDo().equals("search"))
		{
			
			CancelAppointmentview.getComboBox().removeAllItems();
			
			if(((CancelAppointmentEntity)arg).getAppList().size() != 0)
			{
				CancelAppointmentview.getNotificationlbl().setText("Patient name: "+
						((CancelAppointmentEntity)arg).getFirstName()+" "+
						((CancelAppointmentEntity)arg).getLastName());
				
				CAP = ((CancelAppointmentEntity)arg);
				for (int i  = 0 ; i < ((CancelAppointmentEntity)arg).getAppList().size() ; i ++)
					CancelAppointmentview.getComboBox().addItem(((CancelAppointmentEntity)arg).getAppList().get(i));
				CancelAppointmentview.btnCancelApp.setEnabled(true);
			}
			else
			{
				CancelAppointmentview.getNotificationlbl().setText("Patient name: "+((CancelAppointmentEntity)arg).getFirstName()+" "+((CancelAppointmentEntity)arg).getLastName()+", no appointments to show");
				CancelAppointmentview.btnCancelApp.setEnabled(false);
			}
			CancelAppointmentview.getNotificationlbl().setEnabled(true);
		}
		if (arg instanceof CancelAppointmentEntity && ((CancelAppointmentEntity)arg).getTaskToDo().equals("Error! enter valid patient ID!") )
		{
			CancelAppointmentview.getComboBox().removeAllItems();
			CancelAppointmentview.getNotificationlbl().setText("Patient doesn't exist");
			CancelAppointmentview.getNotificationlbl().setEnabled(true);
			CancelAppointmentview.btnCancelApp.setEnabled(false);
			
		}
		if (arg instanceof CancelAppointmentEntity && ((CancelAppointmentEntity)arg).getTaskToDo().equals("delete") )
		{
			JOptionPane.showMessageDialog(null,"Appointment "+((CancelAppointmentEntity)arg).getIdapp() + " deleted");
			System.out.println("appointment"+((CancelAppointmentEntity)arg).getIdapp() + "deleted");
			MainClass.masterControler.setView(
					MainClass.masterControler.DISCont.dispatcherview);
		}
		
	}
}
