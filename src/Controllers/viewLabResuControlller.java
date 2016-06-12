package Controllers;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import ocsf.server.GHealthServer;
import mainPackage.MainClass;
import views.viewLabResuView;
import Controllers.IRefresh;
import Entities.ViewHistoryEntity;
import Entities.ViewLabResEntity;

public class viewLabResuControlller implements Observer,IRefresh  {
	public viewLabResuView viewLabResuview;
	public ViewLabResEntity VLREnt1;
	public viewLabResuControlller() {
		viewLabResuview = new viewLabResuView();
	}
	
	public void getTestResults()
	{
		VLREnt1=new ViewLabResEntity();
		VLREnt1.testResultsFlag=true;
		VLREnt1.pat.setId(MainClass.masterControler.VMHCont.ViewMedicalHistoryview.id);
		MainClass.ghealth.sendMessegeToServer(VLREnt1);
	}
	
	public void askForTestResultSql(ViewLabResEntity VLREnt)
	{
		String query = "";
		query = "SELECT date ,testtype, testresult,photo FROM ghealth.test_results WHERE "
				+ "patientid = \"" + VLREnt.pat.getId() + "\"";
		ArrayList<Object> arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		if (arrList.isEmpty())
		{
			System.out.println("noooooooooo");
			VLREnt.testResultsFlag=false;
		}
		else
		{
			VLREnt.testResultsFlag=true;
			VLREnt.arrTest=arrList;
			//arrList.clear();
		}
	}
	
	/*
	public void getTestResults()
	{
		VLREnt1=new ViewLabResEntity();
		VLREnt1.testResultsFlag=true;
		VLREnt1.pat.setId(MainClass.masterControler.VMHCont.VHEnt1.pat.getId());
		 MainClass.ghealth.sendMessegeToServer(VLREnt1);
	}
	
	public void askForTestResultSql(ViewLabResEntity VLREnt)
	{
		String query = "";
		query = "SELECT date ,testtype, testresult,photo FROM ghealth.test_results WHERE "
				+ "id = \"" + VLREnt.pat.getId() + "\"";
		ArrayList<Object> arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		if (arrList.isEmpty())
		{
			System.out.println("noooooooooo");
			VLREnt.testResultsFlag=false;
		}
		else
		{
			VLREnt.testResultsFlag=true;
			VLREnt.arrTest=arrList;
			arrList.clear();
		}
	}
	
	*/
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
	}
}