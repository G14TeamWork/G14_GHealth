package Controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import mainPackage.MainClass;
import ocsf.server.GHealthServer;
import views.SetAppointmentView;
import Controllers.IRefresh;
import Entities.Patient;
import Entities.SetAppointmentEntity;

public class SetAppointmentController implements Observer,IRefresh,Serializable {
	private static final long serialVersionUID = 1L;
	public SetAppointmentView SetAppointmentview;
	ArrayList<Object> arrList = new ArrayList<>();
	
	public SetAppointmentEntity SApat1;
	public Patient newPatient;
	
	public SetAppointmentController() {
		SetAppointmentview = new SetAppointmentView();
	}
	
	public void setPatient()
	{
		SApat1=new SetAppointmentEntity();
		if(!SetAppointmentview.textFieldid.getText().equals(null))
		{
			SApat1.pat.setId(SetAppointmentview.textFieldid.getText());
			SApat1.pat.setTask("searchPatient");
			MainClass.ghealth.sendMessegeToServer(SApat1);
		}
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
	
	public void searchExperts(Object object) {
		// TODO Auto-generated method stub
		
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
	}
}