package Controllers;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import com.alee.laf.list.WebList;
import com.alee.laf.scroll.WebScrollPane;

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
	public Appointment AppToSet=new Appointment();
	public SetAppointmentEntity SApat1;
	public SetAppointmentEntity SAexp;
	public SetAppointmentEntity SAapp;
	public Patient newPatient;
	public ArrayList<Integer> expIDlist;
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
		newPatient.setId(SetAppointmentview.textFieldid.getText());
		newPatient.setFirstname(SetAppointmentview.textField_first.getText());
		newPatient.setLastname(SetAppointmentview.textField_last.getText());
		newPatient.setPhone(SetAppointmentview.textField_phone.getText());
		newPatient.setEmail(SetAppointmentview.textField_email.getText());
		newPatient.setAddress(SetAppointmentview.textField_adress.getText());
		String fillERR = "Please enter ";
		if(newPatient.getId().length()<1)
			fillERR+="id, ";
		if(newPatient.getFirstname().length()<1)
			fillERR+="first name, ";
		if(newPatient.getLastname().length()<1)
			fillERR+="last name, ";
		if(newPatient.getPhone().length()<1)
			fillERR+="phone, ";
		if(newPatient.getEmail().length()<1)
			fillERR+="email, ";
		if(newPatient.getAddress().length()<1)
			fillERR+="address";
		if (fillERR.equals("Please enter "))
			MainClass.ghealth.sendMessegeToServer(newPatient);
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
			System.out.println("arrList is empty!!!!!!!");
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
	
	public void AddNewPatient(Patient pat)
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
	}
	
	public void searchExperts(String Expertise) {
		SAexp=new SetAppointmentEntity();
		SAexp.exp.setExpertise(Expertise);
		SAexp.setTask("searchExpert");
		MainClass.ghealth.sendMessegeToServer(SAexp);
	}
	
	public void searchExpertSql(SetAppointmentEntity msg) {
		// TODO Auto-generated method stub
		String query = "";
		query = "SELECT distinct u.username, u.firstname, u.lastname, c.Name, e.StartWorkingHours, e.EndWorkingHours FROM ghealth.users as u, ghealth.clinic as c, ghealth.expert as e WHERE u.username=e.id and e.experties=\"" +((SetAppointmentEntity)msg).exp.getExpertise()+"\" and e.clinic=c.idclinic";
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		
		for (int i  = 0 ; i < arrList.size() ; i +=6)
			msg.expList.add(new Expert((int)arrList.get(i), (String)arrList.get(i+1), (String)arrList.get(i+2), (String)arrList.get(i+3), (Time)arrList.get(i+4), (Time)arrList.get(i+5)));
			
		arrList.clear();
	}
	
	public void searchAvailableAppointment(int id, String timeStamp1) {
		// TODO Auto-generated method stub
		SAapp=new SetAppointmentEntity();
		SAapp.app.setAppdateString(timeStamp1);
		SAapp.app.setIdexpert(id);
		SAapp.setTask("searchAvailableAppointment");
		System.out.println(SAapp.toString());
		MainClass.ghealth.sendMessegeToServer(SAapp);
		System.out.println("after send message to server--->"+SAapp.toString());
	}
	
	public void searchAvailableAppointmentSql(SetAppointmentEntity msg) {
		String query = "";
		query = "SELECT idappointment,appdate,start,end FROM ghealth.appointments WHERE idexpert="+((SetAppointmentEntity)msg).app.getIdexpert()+" AND appstatus=0 AND appdate="+((SetAppointmentEntity)msg).app.getAppdateString();
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		
		for (int i  = 0 ; i < arrList.size() ; i +=4)
			{
			((SetAppointmentEntity)msg).AppList.add(new Appointment((int)arrList.get(i),(Date)arrList.get(i+1), (Time)arrList.get(i+2), (Time)arrList.get(i+3)));
			System.out.println(((SetAppointmentEntity)msg).AppList.get(i/4).toString());
			}
		arrList.clear();
	}
	
	@Override
	public void refreshView() {
		MainClass.masterControler.setView(MainClass.masterControler.SACont.SetAppointmentview);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
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
			SetAppointmentview.btnSetAppointment.setVisible(true);
			SetAppointmentview.Jlabel_patientName.setText("Patient name: "+((Patient) arg).getFirstname()+" "+((Patient) arg).getLastname());
		}
		
		if (arg instanceof SetAppointmentEntity)
		{
			if(((SetAppointmentEntity)arg).getTask().equals("searchExpert"))
			{
				expIDlist = new ArrayList<Integer>();
				SetAppointmentview.comboBox_doctors.removeAllItems();
				for (int i  = 0 ; i < ((SetAppointmentEntity)arg).expList.size() ; i ++)
				{
					SetAppointmentview.comboBox_doctors.addItem(((SetAppointmentEntity)arg).expList.get(i).getFirstName()+" "+((SetAppointmentEntity)arg).expList.get(i).getLastName()+", Clinic name: "+((SetAppointmentEntity)arg).expList.get(i).getClinicName());
					expIDlist.add(i, ((SetAppointmentEntity)arg).expList.get(i).getId());
				}
			
			}
			else if(((SetAppointmentEntity)arg).getTask().equals("searchPatient"))
			{
				SetAppointmentview.comboBox_doctors.removeAllItems();
				SetAppointmentview.comboBox_doctors.setSelectedItem("");
				SetAppointmentview.comboBox_expertise.setSelectedItem("");
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
						SetAppointmentview.lblDoctors.setVisible(false);
						SetAppointmentview.lblExpertType.setVisible(false);
						SetAppointmentview.comboBox_expertise.setVisible(false);
						SetAppointmentview.comboBox_doctors.setVisible(false);
						SetAppointmentview.btnSetAppointment.setVisible(false);
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
						SetAppointmentview.lblDoctors.setVisible(false);
						SetAppointmentview.lblExpertType.setVisible(false);
						SetAppointmentview.comboBox_expertise.setVisible(false);
						SetAppointmentview.comboBox_doctors.setVisible(false);
						SetAppointmentview.btnSetAppointment.setVisible(false);
					}
				}
				else
				{
					SetAppointmentview.Jlabel_patientName.setText("Patient name: "+((SetAppointmentEntity) arg).pat.getFirstname()+" "+((SetAppointmentEntity) arg).pat.getLastname());
					SetAppointmentview.lblDoctors.setVisible(true);
					SetAppointmentview.lblExpertType.setVisible(true);
					SetAppointmentview.comboBox_expertise.setVisible(true);
					SetAppointmentview.comboBox_doctors.setVisible(true);
					SetAppointmentview.btnSetAppointment.setVisible(true);
				}
			}
			else if(((SetAppointmentEntity)arg).getTask().equals("searchAvailableAppointment"))
			{
				SetAppointmentview.editableList = new WebList ( SetAppointmentview.createSampleData (((SetAppointmentEntity)arg).AppList) );
				SetAppointmentview.editableList.setVisibleRowCount ( 6 );
				SetAppointmentview.editableList.setSelectedIndex ( 0 );
				SetAppointmentview.editableList.setEditable ( false );
				SetAppointmentview.WebScrollPane1 = new WebScrollPane ( SetAppointmentview.editableList );
				SetAppointmentview.WebScrollPane1.setBounds(50, 328, 250, 200);
				SetAppointmentview.WebScrollPane1.setVisible(true);				
			}
			((SetAppointmentEntity)arg).setTask("");
		}
	}
}