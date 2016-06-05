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
//		 MainClass.masterControler.SACont.refreshView();
	}
	public void checkExistanceSql(Patient pat)
	{
		String query = "";
		query = "SELECT firstname,lastname FROM ghealth.patient where "
				+ "id = \"" + pat.getId() + "\"";
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		if (arrList.isEmpty())
		{
			System.out.println("noooooooooo");
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
			SetAppointmentview.textField_first.setText(pat.getFirstname());
			SetAppointmentview.textField_last.setText(pat.getLastname());
			refreshView();
		}
	}
}