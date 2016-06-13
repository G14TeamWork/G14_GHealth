package Controllers;

import graphics.GUIimage;

import java.awt.Color;
import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.JLabel;

import com.thoughtworks.xstream.security.ForbiddenClassException;

import ocsf.server.GHealthServer;
import mainPackage.MainClass;
import views.ViewMedicalHistoryView;
import views.viewLabResuView;
import Controllers.IRefresh;
import Entities.FillTestResEntity;
import Entities.ViewHistoryEntity;
import Entities.ViewLabResEntity;

/**
 * This class is in charge of viewing medical history features
 * showing all appointments history of patient
 * @param viewmedicalhistoryview is a panel that contains the view of view medical history
 * @param VHEnt1 - an entity that will be sent to server as msg, and received as one. in it there are appointments and other data
 * @param arrList array list of object to get data from sql
 * @author Ruslan
 *
 */
public class ViewMedicalHistoryController implements Observer,IRefresh,Serializable {
	private static final long serialVersionUID = 1L;
	public ViewMedicalHistoryView ViewMedicalHistoryview;
	public ViewHistoryEntity VHEnt1;
	ArrayList<Object> arrList = new ArrayList<>();
	/**
	 * constructor - builds a new view panel
	 * @return like a constructor returns viewmedicalfilehistory
	 */
	public ViewMedicalHistoryController() {
		ViewMedicalHistoryview = new ViewMedicalHistoryView();
	}
	
	/**
	 * this method prepares VHEnt1 for searching patient in db.
	 * client side
	 */
	public void setVHEnt_Patient()
	{
	
		VHEnt1 = new ViewHistoryEntity();
		arrList.clear();
		VHEnt1.pat.setId(ViewMedicalHistoryview.textFieldid.getText());
		MainClass.ghealth.sendMessegeToServer(VHEnt1);
	}
	/**
	 * server side <br>
	 * gets patient name by id
	 * using the VHENT which is the message sent from client that contains id inside it
	 * @param VHEnt message sent from client. contains patid inside it
	 */
	public void checkExistanceSql(ViewHistoryEntity VHEnt)
	{
		String query = "";
		query = "SELECT firstname,lastname FROM ghealth.patient where "
				+ "id = \"" + VHEnt.pat.getId() + "\"";
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		if (arrList.isEmpty())
		{
			//System.out.println("noooooooooo");
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
		MainClass.masterControler.VLRCont.viewLabResuview= new viewLabResuView();
	}

	@Override
	public void update(Observable o, Object arg) 
	{
		if (arg instanceof ViewHistoryEntity)
		{
			{
				
				VHEnt1.pat.setFirstname(((ViewHistoryEntity) arg).pat.getFirstname());
				VHEnt1.pat.setLastname(((ViewHistoryEntity) arg).pat.getLastname());
				ViewMedicalHistoryview.textField_patname.setText("Patient name: "+ VHEnt1.pat.getFirstname()+" "+VHEnt1.pat.getLastname());
				VHEnt1.showbuttonflag=((ViewHistoryEntity) arg).showbuttonflag;
				VHEnt1.photoflag=((ViewHistoryEntity) arg).photoflag;
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
}