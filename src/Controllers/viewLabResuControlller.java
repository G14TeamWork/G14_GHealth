package Controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import ocsf.server.GHealthServer;
import mainPackage.MainClass;
import views.ViewAppHistoryView;
import views.viewLabResuView;
import Controllers.IRefresh;
import Entities.ViewAppHistoryEntity;
import Entities.ViewLabResEntity;

/**
 * This class is a controller for viewing test results. also managing view appointment record
 * The controller is in charge of communication with the server.
 * Also in charge of filling entities that are sent as msg between server and client
 * param viewLabResuview is the window with all features of lab history
 * param viewapphistory is the window with all features of appointment history
 * param VLREnt1 is the msg sent between server and client in case of lab history
 * param VAHEnt1 is the msg sent between server and client in case of app history
 * @author Ruslan
 *
 */
public class viewLabResuControlller implements Observer,IRefresh  {
	public viewLabResuView viewLabResuview;
	public ViewAppHistoryView viewapphistoryview;
	public ViewLabResEntity VLREnt1;
	public ViewAppHistoryEntity VAHEnt1;
	
	/**
	 * constroctor
	 * creates a new panel
	 * return constructor returns viewLabResultController
	 */
	public viewLabResuControlller() {
		viewLabResuview = new viewLabResuView();
		viewapphistoryview =  new ViewAppHistoryView();
	}
	
	/**
	 * function prepares message to server
	 * is called from view as response to getting test results
	 * client side
	 */
	public void getTestResults()
	{
		VLREnt1=new ViewLabResEntity();
		VLREnt1.testResultsFlag=true;
		VLREnt1.pat.setId(MainClass.masterControler.VMHCont.ViewMedicalHistoryview.id);
		MainClass.ghealth.sendMessegeToServer(VLREnt1);
	}
	
	/**
	 * server side - server queries the db for patient with given id
	 * should return in entity all test results for patient
	 * param query - the string that is sent to db
	 * @param VLREnt - holds patient id for db query
	 */
	public void askForTestResultSql(ViewLabResEntity VLREnt)
	{
		String query = "";
		query = "SELECT date ,testtype, testresult,photo FROM ghealth.test_results WHERE "
				+ "patientid = \"" + VLREnt.pat.getId() + "\"";
		ArrayList<Object> arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		if (arrList.isEmpty())
		{
			//System.out.println("noooooooooo");
			VLREnt.testResultsFlag=false;
		}
		else
		{
			VLREnt.testResultsFlag=true;
			VLREnt.arrTest=arrList;
			//arrList.clear();
		}
	}
	
	/**
	 * method sets entity prepared for getting appointment record
	 * client side
	 * in this method all requiered data is put into VAHEnt1 and sent to sever
	 */
	public void getAppRecord()
	{
		VAHEnt1=new ViewAppHistoryEntity();
		VAHEnt1.appResultsFlag=true;
		VAHEnt1.pat.setId(MainClass.masterControler.VMHCont.ViewMedicalHistoryview.id);
		MainClass.ghealth.sendMessegeToServer(VAHEnt1);
	}
	
	/**
	 * server side of get appointment record
	 * method gets appointments record and puts it in VAHEnt and sends it back to client
	 * param query is the string that sent to sql
	 * @param VAHEnt
	 */
	public void askForAppRecordSql(ViewAppHistoryEntity VAHEnt)
	{
		String query = "";
		
		query ="SELECT app.idappointment,users.firstname,users.lastname,exps.experties,app.appdate,app.realStart,app.realEnd,app.appstatus,app.record,"+
		"clinics.Name FROM ghealth.appointments as app, ghealth.users, (SELECT id,experties FROM ghealth.expert)as exps,(SELECT idclinic,Name FROM ghealth.clinic)"+
		"as clinics WHERE idpatient ='"+VAHEnt.pat.getId()+"' AND (appstatus='2' OR appstatus='3') AND app.idexpert=exps.id AND app.idclinic=clinics.idclinic AND "+
		"app.idexpert=users.username";
		ArrayList<Object> arrAppList = GHealthServer.sqlConn.sendSqlQuery(query);
		if (arrAppList.isEmpty())
			VAHEnt.appResultsFlag=false;
		else
		{
			VAHEnt.appResultsFlag=true;
			VAHEnt.arrApp=arrAppList;
		}
	}
	
	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
		
	}
	
	@Override
	public void update(Observable o, Object arg) 
	{
		if (arg instanceof ViewLabResEntity)
		{
			if (((ViewLabResEntity)arg).testResultsFlag)
			{
				//System.out.println(((ViewLabResEntity)arg).arrTest);
				//VLREnt1.arrTest.clear();
				VLREnt1.arrTest=(ArrayList<Object>) ((ViewLabResEntity)arg).arrTest;
				
				MainClass.masterControler.VLRCont.viewLabResuview.comboBoxChooseTest.removeAllItems();
				MainClass.masterControler.VLRCont.viewLabResuview.comboBoxChooseTest.addItem("");
				for (int i=0;i<VLREnt1.arrTest.size();i+=4)
					MainClass.masterControler.VLRCont.viewLabResuview.comboBoxChooseTest.addItem(VLREnt1.arrTest.get(0+i)+" "+VLREnt1.arrTest.get(1+i));
				MainClass.masterControler.setView(MainClass.masterControler.VLRCont.viewLabResuview);
			}
			else 
			{
				JOptionPane.showMessageDialog(null, "No test results for patient!","",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else if (arg instanceof ViewAppHistoryEntity)
		{
			if (((ViewAppHistoryEntity)arg).appResultsFlag)
			{

				VAHEnt1.arrApp=((ViewAppHistoryEntity)arg).arrApp;
				
				MainClass.masterControler.VLRCont.viewapphistoryview.fileArea.setText("");
				String strTemp = "";
				for (int i=0;i<VAHEnt1.arrApp.size();i+=10)
				{
					strTemp=strTemp+
					" "+(String)VAHEnt1.arrApp.get(i+8)+//record
					"\nDoctor:"
					+(String)VAHEnt1.arrApp.get(i+1)+
					" "+(String)VAHEnt1.arrApp.get(i+2)+
					","+(String)VAHEnt1.arrApp.get(i+3)+
					",clinic "+(String)VAHEnt1.arrApp.get(i+9)+"\n******************************************************************************\n";
				}
				MainClass.masterControler.VLRCont.viewapphistoryview.fileArea.setText(strTemp);
				MainClass.masterControler.setView(MainClass.masterControler.VLRCont.viewapphistoryview);
			}
			else 
			{
				JOptionPane.showMessageDialog(null, "No appointment record for patient!","",JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}