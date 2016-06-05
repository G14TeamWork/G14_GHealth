package Controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import mainPackage.MainClass;
import ocsf.server.GHealthServer;
import views.SetAppointmentView;
import Controllers.IRefresh;
import Entities.Patient;

public class SetAppointmentController implements Observer,IRefresh,Serializable {
	private static final long serialVersionUID = 1L;
	public SetAppointmentView SetAppointmentview;
	ArrayList<Object> arrList = new ArrayList<>();
	public Patient pat;
	
	public SetAppointmentController() {
		SetAppointmentview = new SetAppointmentView();
	}
	
	public void setPatient()
	{
		 pat=new Patient();
		 pat.setId(SetAppointmentview.textFieldid.getText());
		 MainClass.ghealth.sendMessegeToServer(pat);
	}
	public void checkExistanceSql(Patient pat)
	{
		String query = "";
		query = "SELECT firstname,lastname,phone,email,address FROM ghealth.patient where "
				+ "id = \"" + pat.getId() + "\"";
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		if (arrList.isEmpty())
		{
			System.out.println("noooooooooo");
		}
		else
		{
			pat.setFirstname((String)arrList.get(0));
			pat.setLastname((String)arrList.get(1));
			pat.setPhone((String)arrList.get(2));
			pat.setEmail((String)arrList.get(3));
			pat.setAddress((String)arrList.get(4));
			arrList.clear();
		}

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
			pat.setFirstname(((Patient) arg).getFirstname());
			pat.setLastname(((Patient) arg).getLastname());
			SetAppointmentview.textField_first.setText(((Patient) arg).getFirstname());
			SetAppointmentview.textField_last.setText(((Patient) arg).getLastname());
			SetAppointmentview.textField_phone.setText(((Patient) arg).getPhone());
			SetAppointmentview.textField_email.setText(((Patient) arg).getEmail());
			SetAppointmentview.textField_adress.setText(((Patient) arg).getAddress());
			refreshView();
		}
	}
}