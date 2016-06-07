package Controllers;

import graphics.GUIimage;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import ocsf.server.GHealthServer;
import mainPackage.MainClass;
import views.ViewMedicalHistoryView;
import Controllers.IRefresh;
import Entities.ViewHistoryEntity;

public class ViewMedicalHistoryController implements Observer,IRefresh,Serializable {
	private static final long serialVersionUID = 1L;
	public ViewMedicalHistoryView ViewMedicalHistoryview;
	public ViewHistoryEntity VHEnt1;
	ArrayList<Object> arrList = new ArrayList<>();
	public ViewMedicalHistoryController() {
		ViewMedicalHistoryview = new ViewMedicalHistoryView();
	}
	
	public void setVHEnt_Patient()
	{
		VHEnt1 = new ViewHistoryEntity();
		VHEnt1.pat.setId(ViewMedicalHistoryview.textFieldid.getText());
		 MainClass.ghealth.sendMessegeToServer(VHEnt1);
	}
	public void checkExistanceSql(ViewHistoryEntity VHEnt)
	{
		String query = "";
		query = "SELECT firstname,lastname FROM ghealth.patient where "
				+ "id = \"" + VHEnt.pat.getId() + "\"";
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		if (arrList.isEmpty())
		{
			System.out.println("noooooooooo");
			VHEnt.showbuttonflag=false;
		}
		else
		{
			VHEnt.showbuttonflag=true;
			VHEnt.pat.setFirstname((String)arrList.get(0));
			VHEnt.pat.setLastname((String)arrList.get(1));
			arrList.clear();
		}
	}
	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Observable o, Object arg) 
	{
		if (arg instanceof ViewHistoryEntity)
		{
			VHEnt1.pat.setFirstname(((ViewHistoryEntity) arg).pat.getFirstname());
			VHEnt1.pat.setLastname(((ViewHistoryEntity) arg).pat.getLastname());
			ViewMedicalHistoryview.textField_patname.setText("Patient name: "+ VHEnt1.pat.getFirstname()+" "+VHEnt1.pat.getLastname());
			VHEnt1.showbuttonflag=((ViewHistoryEntity) arg).showbuttonflag;
			if (VHEnt1.showbuttonflag)
			{
				ViewMedicalHistoryview.btnViewmedicalHis.setEnabled(true);
				ViewMedicalHistoryview.btnViewLabHis.setEnabled(true);
				ViewMedicalHistoryview.textField_patname.setForeground(Color.BLACK);
			}
			else 
			{
				ViewMedicalHistoryview.btnViewLabHis.setEnabled(false);
				ViewMedicalHistoryview.btnViewmedicalHis.setEnabled(false);
				ViewMedicalHistoryview.textField_patname.setText("Error! enter valid patient ID!");
				ViewMedicalHistoryview.textField_patname.setForeground(Color.RED);
			}
		}
	}
}