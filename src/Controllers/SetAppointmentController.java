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
	
	@Override
	public void refreshView() {
		MainClass.masterControler.setView(MainClass.masterControler.SACont.SetAppointmentview);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof SetAppointmentEntity)
		{
			if((((SetAppointmentEntity) arg).pat.getId()!=null) &&(((SetAppointmentEntity) arg).pat.getFirstname()==null)&& !SetAppointmentview.textFieldid.getText().equals(null))
			{
				if(JOptionPane.showConfirmDialog(null, "Patient not found, are you want to enter new patient?",null,JOptionPane.YES_NO_OPTION)==0)
				{	
					SetAppointmentview.textField_first.setEditable(true);
					SetAppointmentview.textField_last.setEditable(true);
					SetAppointmentview.textField_phone.setEditable(true);
					SetAppointmentview.textField_email.setEditable(true);
					SetAppointmentview.textField_adress.setEditable(true);
					SetAppointmentview.textField_first.setText("");
					SetAppointmentview.textField_last.setText("");
					SetAppointmentview.textField_phone.setText("");
					SetAppointmentview.textField_email.setText("");
					SetAppointmentview.textField_adress.setText("");
					SetAppointmentview.btnNewPatient.setVisible(true);
					SetAppointmentview.btnsearch.setVisible(false);
					}
				else
				{

					SetAppointmentview.textField_first.setEditable(false);
					SetAppointmentview.textField_last.setEditable(false);
					SetAppointmentview.textField_phone.setEditable(false);
					SetAppointmentview.	textField_email.setEditable(false);
					SetAppointmentview.textField_adress.setEditable(false);
				}
			}
			else
			{
				SApat1.pat.setFirstname(((SetAppointmentEntity) arg).pat.getFirstname());
				SApat1.pat.setLastname(((SetAppointmentEntity) arg).pat.getLastname());
				SApat1.pat.setPhone(((SetAppointmentEntity) arg).pat.getPhone());
				SApat1.pat.setEmail(((SetAppointmentEntity) arg).pat.getEmail());
				SApat1.pat.setAddress(((SetAppointmentEntity) arg).pat.getAddress());
				SetAppointmentview.textField_first.setText(SApat1.pat.getFirstname());
				SetAppointmentview.textField_last.setText(SApat1.pat.getLastname());
				SetAppointmentview.textField_phone.setText(SApat1.pat.getPhone());
				SetAppointmentview.textField_email.setText(SApat1.pat.getEmail());
				SetAppointmentview.textField_adress.setText(SApat1.pat.getAddress());
				SetAppointmentview.textField_first.setEditable(false);
				SetAppointmentview.textField_last.setEditable(false);
				SetAppointmentview.textField_phone.setEditable(false);
				SetAppointmentview.	textField_email.setEditable(false);
				SetAppointmentview.textField_adress.setEditable(false);
			}
			refreshView();
		}
	}
}