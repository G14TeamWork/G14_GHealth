package Controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import mainPackage.MainClass;
import ocsf.server.GHealthServer;
import views.RecordAppointView;
import Controllers.IRefresh;

public class  RecordAppointController implements Observer,IRefresh  {
	public RecordAppointView RecordAppointview;
	
	public RecordAppointController() {
		int flag = 1;
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		ArrayList<Object> arrList = new ArrayList<>();
		String query = "";
		
		while (flag != 0){
			try {
				String appID = JOptionPane.showInputDialog(null, "Please enter appointment number:");
				query = "SELECT idappointment,idpatient FROM ghealth.appointments where "
						+ "idappointment = \"" + appID + "\"";
				arrList = GHealthServer.sqlConn.sendSqlQuery(query);
				
				/* ************************************************************************** */
				for ( int i = 0 ; i< arrList.size() ; i++)
					JOptionPane.showMessageDialog(null, arrList.get(i));
				JOptionPane.showMessageDialog(null, "array list size is " + arrList.size());

				/* ************************************************************************** */
				RecordAppointview = new RecordAppointView(arrList.get(0).toString(),arrList.get(1).toString(),sdf.format(cal.getTime()).toString());
				flag = 0;
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "Please make sure appointment number is correct and try again.");
				flag++;
			}finally{ 
				if ( flag == 3 ){
					flag =  0;
					MainClass.masterControler.EXPVCont.expview.flag = false;
					}
			}
		}
	}
	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
	}
}