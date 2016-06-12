package Controllers;

import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import mainPackage.MainClass;
import ocsf.server.GHealthServer;
import views.SetAppointmentView;
import Controllers.IRefresh;
import Entities.Appointment;
import Entities.Expert;
import Entities.Patient;
import Entities.SetAppointmentEntity;

public class SetAppointmentController implements Observer,IRefresh,Serializable {
	private static final long serialVersionUID = 1L;
	public SetAppointmentView SetAppointmentview;
	ArrayList<Object> arrList = new ArrayList<>();
	ArrayList<Object> arrList1 = new ArrayList<>();
	public Appointment AppToSet= new Appointment();
	public SetAppointmentEntity SApat1;
	public SetAppointmentEntity SAexp;
	public SetAppointmentEntity SAapp;
	public Patient newPatient;
	public ArrayList<Integer> expIDlist;
	public ArrayList<Integer> AppIDlist;
	public SetAppointmentController() {
		SetAppointmentview = new SetAppointmentView();
	}
	
	public void setPatient()
	{
		SApat1=new SetAppointmentEntity();
		SApat1.pat.setId(SetAppointmentview.textFieldid.getText());
		SApat1.setTask("searchPatient");
		MainClass.ghealth.sendMessegeToServer(SApat1);
	}
	
	public void setNewPatient() {
		newPatient=new Patient();
		String fillERR = "Please enter ";
		if(SetAppointmentview.textFieldid.getText().length()<1)
			fillERR+="id, ";
		if(SetAppointmentview.textField_first.getText().length()<1)
			fillERR+="first name, ";
		if(SetAppointmentview.textField_last.getText().length()<1)
			fillERR+="last name, ";
		if(SetAppointmentview.textField_phone.getText().length()<1)
			fillERR+="phone, ";
		if(SetAppointmentview.textField_email.getText().length()<1)
			fillERR+="email, ";
		else
		{
			boolean emailFLAG=false;
			for(int i = 0 ; i < SetAppointmentview.textField_email.getText().length() ; i++)
				if(SetAppointmentview.textField_email.getText().charAt(i)=='@')
					emailFLAG=true;
			if(!emailFLAG)
				fillERR+="email with valid structure (@), ";
		}
		if(SetAppointmentview.textField_adress.getText().length()<1)
			fillERR+="address";
		if (fillERR.equals("Please enter "))
		{
			newPatient.setId(SetAppointmentview.textFieldid.getText());
			newPatient.setFirstname(SetAppointmentview.textField_first.getText());
			newPatient.setLastname(SetAppointmentview.textField_last.getText());
			newPatient.setPhone(SetAppointmentview.textField_phone.getText());
			newPatient.setEmail(SetAppointmentview.textField_email.getText());
			newPatient.setAddress(SetAppointmentview.textField_adress.getText());
			MainClass.ghealth.sendMessegeToServer(newPatient);
		}
		else
		{
			JOptionPane.showMessageDialog(null,fillERR);
		}
	}
	
	public void checkExistanceSql(SetAppointmentEntity SApat)
	{
		String query = "";
		query = "SELECT firstname,lastname,phone,email,address FROM ghealth.patient where "
				+ "id = \"" + SApat.pat.getId() + "\"";
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		if (arrList.isEmpty())
		{
			System.out.println("Patient not found!");
		}
		else
		{
			SApat.pat.setFirstname((String)arrList.get(0));
			SApat.pat.setLastname((String)arrList.get(1));
			SApat.pat.setPhone((String)arrList.get(2));
			SApat.pat.setEmail((String)arrList.get(3));
			SApat.pat.setAddress((String)arrList.get(4));
		}
		arrList.clear();
	}
	
	public void AddNewPatientSql(Patient pat)
	{
		String query = "";
		query = "INSERT INTO ghealth.patient (id, firstname, lastname, phone, email, address)"+
		"VALUES ("+pat.getId()+
		", \""+pat.getFirstname()+
		"\", \""+pat.getLastname()+
		"\", "+pat.getPhone()+
		", \""+pat.getEmail()+
		"\", \""+pat.getAddress()+"\");";
		
		GHealthServer.sqlConn.sendSqlUpdate(query);
		query = "INSERT INTO ghealth.medicalfile (idpatient) "+
				"VALUES ("+pat.getId()+");";
		GHealthServer.sqlConn.sendSqlUpdate(query);
		
	}
	
	public void searchExperts(String Expertise, String patientID) {
		SAexp=new SetAppointmentEntity();
		SAexp.exp.setExpertise(Expertise);
		SAexp.exp.setPatID(patientID);
		SAexp.setTask("searchExpert");
		MainClass.ghealth.sendMessegeToServer(SAexp);
	}
	
	public void searchExpertSql(SetAppointmentEntity msg) {
		String query = "";
		query = "SELECT distinct u.username, u.firstname, u.lastname, c.Name, e.StartWorkingHours, e.EndWorkingHours FROM ghealth.users as u, ghealth.clinic as c, ghealth.expert as e WHERE u.username=e.id and e.experties=\"" +((SetAppointmentEntity)msg).exp.getExpertise()+"\" and e.clinic=c.idclinic";
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		//TODO
		query = "SELECT DISTINCT ghealth.appointments.idexpert "
		+"FROM ghealth.appointments, (SELECT ghealth.expert.id FROM ghealth.expert WHERE ghealth.expert.experties='"+((SetAppointmentEntity)msg).exp.getExpertise()+"') as e "
		+"WHERE ghealth.appointments.idpatient="+((SetAppointmentEntity)msg).exp.getPatID()+" AND ghealth.appointments.appstatus='2' AND ghealth.appointments.idexpert=e.id "
		+"ORDER BY ghealth.appointments.appdate DESC";
		arrList1 = GHealthServer.sqlConn.sendSqlQuery(query);
		
		for (int i  = 0 ; i < arrList1.size() ; i ++)
			for (int j  = 0 ; j < arrList.size() ; j +=6)
			{
				if((int)arrList1.get(i)==(int)arrList.get(j))
					msg.ExpList.add(new Expert((int)arrList.get(j), (String)arrList.get(j+1), (String)arrList.get(j+2), (String)arrList.get(j+3), (Time)arrList.get(j+4), (Time)arrList.get(j+5)));
			}
		for (int j  = 0 ; j < arrList.size() ; j +=6)
		{
			boolean flag=true;
			for (int i  = 0 ; i < msg.ExpList.size() ; i ++)
			{
					if((int)arrList.get(j)==msg.ExpList.get(i).getId())
						flag=false;
			}
			if(flag)
				msg.ExpList.add(new Expert((int)arrList.get(j), (String)arrList.get(j+1), (String)arrList.get(j+2), (String)arrList.get(j+3), (Time)arrList.get(j+4), (Time)arrList.get(j+5)));		
		}
		arrList.clear();
		arrList1.clear();
	}
	
	public void searchAvailableAppointmentDates(int id) {
		SAapp=new SetAppointmentEntity();
		SAapp.app.setIdexpert(id);
		SAapp.setTask("searchAvailableAppointmentDates");
		MainClass.ghealth.sendMessegeToServer(SAapp);
	}
	
	public void searchAvailableAppointmentDatesSql(SetAppointmentEntity msg) {
		String query = "";
		query = "SELECT DISTINCT appdate FROM ghealth.appointments WHERE idexpert="+((SetAppointmentEntity)msg).app.getIdexpert()+" AND appstatus=0 ORDER BY appdate";
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		
		for (int i  = 0 ; i < arrList.size() ; i++)
			((SetAppointmentEntity)msg).AppList.add(new Appointment((Date)arrList.get(i)));
		
		arrList.clear();
	}


	public void searchAvailableAppointmentHours(java.sql.Date date) {
		SAapp.app.setAppdate(date);
		SAapp.setTask("searchAvailableAppointmentHours");
		MainClass.ghealth.sendMessegeToServer(SAapp);
	}
	
	public void searchAvailableAppointmentHoursSql(SetAppointmentEntity msg) {
		String query = "";
		query = "SELECT idappointment,start,end FROM ghealth.appointments WHERE idexpert="+((SetAppointmentEntity)msg).app.getIdexpert()+" AND appstatus=0 AND appdate='"+((SetAppointmentEntity)msg).app.getAppdate()+"'";
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		for (int i  = 0 ; i < arrList.size() ; i +=3)
		{
			((SetAppointmentEntity)msg).AppIDlist.add((Integer)arrList.get(i));
			((SetAppointmentEntity)msg).HourList.add((Time)arrList.get(i+1));
			((SetAppointmentEntity)msg).HourList.add((Time)arrList.get(i+2));
		}
		arrList.clear();
	}

	public void setNewAppointment(String patID, String appID) {

		AppToSet.setIdpatient(patID);
		AppToSet.setIdappointment(appID);
		MainClass.ghealth.sendMessegeToServer(AppToSet);
	}
	
	public void SetAppointmentSql(Appointment msg) {
		String Hour = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
		String Date = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		
		String query="UPDATE ghealth.appointments SET idpatient="+((Appointment)msg).getIdpatient()+", appstatus=1, dispatcherSettingDate="+Date+" ,dispatcherSettingHour="+Hour+" WHERE idappointment="+((Appointment)msg).getIdappointment();
		GHealthServer.sqlConn.sendSqlUpdate(query);
	
	}	
	
	@Override
	public void refreshView() {
		MainClass.masterControler.setView(MainClass.masterControler.SACont.SetAppointmentview);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Patient)
		{
			JOptionPane.showMessageDialog(null,"A new patient was entered into the system successfully" );
			SetAppointmentview.btnNewPatient.setVisible(false);
			SetAppointmentview.textField_first.setVisible(false);
			SetAppointmentview.textField_last.setVisible(false);
			SetAppointmentview.textField_phone.setVisible(false);
			SetAppointmentview.textField_email.setVisible(false);
			SetAppointmentview.textField_adress.setVisible(false);
			SetAppointmentview.lblFirstName.setVisible(false);
			SetAppointmentview.lblLastName.setVisible(false);
			SetAppointmentview.lblPhone.setVisible(false);
			SetAppointmentview.lblEmail.setVisible(false);
			SetAppointmentview.lblAdress.setVisible(false);
			SetAppointmentview.lblDoctors.setVisible(true);
			SetAppointmentview.lblExpertType.setVisible(true);
			SetAppointmentview.comboBox_expertise.setVisible(true);
			SetAppointmentview.comboBox_doctors.setVisible(true);
			SetAppointmentview.lblAvailableAppointmentsDates.setVisible(true);
			SetAppointmentview.lblAvailableAppointmentsHours.setVisible(true);
			SetAppointmentview.comboBox_AvailableAppointmentsDates.setVisible(true);
			SetAppointmentview.comboBox_AvailableAppointmentsHours.setVisible(true);
			SetAppointmentview.btnSetAppointment.setVisible(true);
			SetAppointmentview.Jlabel_patientName.setText("Patient name: "+((Patient) arg).getFirstname()+" "+((Patient) arg).getLastname());
		}
		
		if (arg instanceof SetAppointmentEntity)
		{
			if(((SetAppointmentEntity)arg).getTask().equals("searchExpert"))
			{
				expIDlist = new ArrayList<Integer>();
				SetAppointmentview.comboBox_doctors.removeAllItems();
				SetAppointmentview.comboBox_AvailableAppointmentsDates.removeAllItems();
				SetAppointmentview.comboBox_AvailableAppointmentsHours.removeAllItems();
				SetAppointmentview.comboBox_doctors.addItem("");
				for (int i  = 0 ; i < ((SetAppointmentEntity)arg).ExpList.size() ; i ++)
				{
					SetAppointmentview.comboBox_doctors.addItem(((SetAppointmentEntity)arg).ExpList.get(i).getFirstName()+" "+((SetAppointmentEntity)arg).ExpList.get(i).getLastName()+", Clinic name: "+((SetAppointmentEntity)arg).ExpList.get(i).getClinicName());
					expIDlist.add(i, ((SetAppointmentEntity)arg).ExpList.get(i).getId());
				}
			}
			else if(((SetAppointmentEntity)arg).getTask().equals("searchPatient"))
			{
				SetAppointmentview.comboBox_doctors.removeAllItems();
				SetAppointmentview.comboBox_expertise.setSelectedIndex(1);
				SetAppointmentview.comboBox_expertise.setSelectedIndex(0);
				SetAppointmentview.comboBox_AvailableAppointmentsDates.removeAllItems();
				SetAppointmentview.comboBox_AvailableAppointmentsHours.removeAllItems();
				if((((SetAppointmentEntity) arg).pat.getId()!=null) &&(((SetAppointmentEntity) arg).pat.getFirstname()==null)&& !SetAppointmentview.textFieldid.getText().equals(null))
				{
					if(JOptionPane.showConfirmDialog(null, "Patient was not found, are you want to enter new patient?",null,JOptionPane.YES_NO_OPTION)==0)
					{	
						SetAppointmentview.Jlabel_patientName.setText("");
						SetAppointmentview.textField_first.setVisible(true);
						SetAppointmentview.textField_last.setVisible(true);
						SetAppointmentview.textField_phone.setVisible(true);
						SetAppointmentview.textField_email.setVisible(true);
						SetAppointmentview.textField_adress.setVisible(true);
						SetAppointmentview.lblFirstName.setVisible(true);
						SetAppointmentview.lblLastName.setVisible(true);
						SetAppointmentview.lblPhone.setVisible(true);
						SetAppointmentview.lblEmail.setVisible(true);
						SetAppointmentview.lblAdress.setVisible(true);
						SetAppointmentview.btnNewPatient.setVisible(true);
						SetAppointmentview.btnsearch.setVisible(false);
					}
					else
					{
						SetAppointmentview.Jlabel_patientName.setText("");
						SetAppointmentview.textField_first.setVisible(false);
						SetAppointmentview.textField_last.setVisible(false);
						SetAppointmentview.textField_phone.setVisible(false);
						SetAppointmentview.textField_email.setVisible(false);
						SetAppointmentview.textField_adress.setVisible(false);
						SetAppointmentview.lblFirstName.setVisible(false);
						SetAppointmentview.lblLastName.setVisible(false);
						SetAppointmentview.lblPhone.setVisible(false);
						SetAppointmentview.lblEmail.setVisible(false);
						SetAppointmentview.lblAdress.setVisible(false);
						SetAppointmentview.btnNewPatient.setVisible(false);
						SetAppointmentview.btnsearch.setVisible(true);
					}
					SetAppointmentview.lblDoctors.setVisible(false);
					SetAppointmentview.lblExpertType.setVisible(false);
					SetAppointmentview.comboBox_expertise.setVisible(false);
					SetAppointmentview.comboBox_doctors.setVisible(false);
					SetAppointmentview.lblAvailableAppointmentsDates.setVisible(false);
					SetAppointmentview.lblAvailableAppointmentsHours.setVisible(false);
					SetAppointmentview.comboBox_AvailableAppointmentsDates.setVisible(false);
					SetAppointmentview.comboBox_AvailableAppointmentsHours.setVisible(false);
					SetAppointmentview.btnSetAppointment.setVisible(false);
				}
				else
				{
					SetAppointmentview.Jlabel_patientName.setText("Patient name: "+((SetAppointmentEntity) arg).pat.getFirstname()+" "+((SetAppointmentEntity) arg).pat.getLastname());
					SetAppointmentview.lblDoctors.setVisible(true);
					SetAppointmentview.lblExpertType.setVisible(true);
					SetAppointmentview.comboBox_expertise.setVisible(true);
					SetAppointmentview.comboBox_doctors.setVisible(true);
					SetAppointmentview.btnSetAppointment.setVisible(true);
					SetAppointmentview.lblAvailableAppointmentsDates.setVisible(true);
					SetAppointmentview.lblAvailableAppointmentsHours.setVisible(true);
					SetAppointmentview.comboBox_AvailableAppointmentsDates.setVisible(true);
					SetAppointmentview.comboBox_AvailableAppointmentsHours.setVisible(true);
				}
			}
			else if(((SetAppointmentEntity)arg).getTask().equals("searchAvailableAppointmentDates"))
			{
				SetAppointmentview.comboBox_AvailableAppointmentsHours.removeAllItems();
				SetAppointmentview.comboBox_AvailableAppointmentsDates.removeAllItems();
				SetAppointmentview.comboBox_AvailableAppointmentsDates.addItem("");
				for (int i  = 0 ; i < ((SetAppointmentEntity)arg).AppList.size() ; i ++)
				{
					SetAppointmentview.comboBox_AvailableAppointmentsDates.addItem(((SetAppointmentEntity)arg).AppList.get(i).getAppdate());
				}	
			}
			else if(((SetAppointmentEntity)arg).getTask().equals("searchAvailableAppointmentHours"))
			{
				SetAppointmentview.comboBox_AvailableAppointmentsHours.removeAllItems();
				for (int i  = 0 ; i < ((SetAppointmentEntity)arg).HourList.size() ; i +=2)
					SetAppointmentview.comboBox_AvailableAppointmentsHours.addItem(((SetAppointmentEntity)arg).HourList.get(i)+"-"+((SetAppointmentEntity)arg).HourList.get(i+1));	
				for (int i  = 0 ; i < ((SetAppointmentEntity)arg).AppIDlist.size() ; i ++)
					AppIDlist.add(((SetAppointmentEntity)arg).AppIDlist.get(i));	

			}
			((SetAppointmentEntity)arg).setTask("");
		}
		if(arg instanceof Appointment)
		{
			JOptionPane.showMessageDialog(null,"New appointment was scheduled");
			MainClass.masterControler.setView(
					MainClass.masterControler.DISCont.dispatcherview);
		}
	}
}