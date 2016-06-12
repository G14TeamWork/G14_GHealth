package Controllers;

import java.util.ArrayList;
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

public class viewLabResuControlller implements Observer,IRefresh  {
	public viewLabResuView viewLabResuview;
	public ViewAppHistoryView viewapphistoryview;
	public ViewLabResEntity VLREnt1;
	public ViewAppHistoryEntity VAHEnt1;
	public viewLabResuControlller() {
		viewLabResuview = new viewLabResuView();
		viewapphistoryview =  new ViewAppHistoryView();
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
	
	public void getAppRecord()
	{
		VAHEnt1=new ViewAppHistoryEntity();
		VAHEnt1.appResultsFlag=true;
		VAHEnt1.pat.setId(MainClass.masterControler.VMHCont.ViewMedicalHistoryview.id);//????
		MainClass.ghealth.sendMessegeToServer(VAHEnt1);
	}
	
	public void askForAppRecordSql(ViewAppHistoryEntity VAHEnt)
	{
		String query = "";
		
		query ="SELECT idappointment,idexpert,appdate,realStart,realEnd,appstatus,record,idclinic FROM ghealth.appointments WHERE idpatient ='"+
		VAHEnt.pat.getId()+"' AND (appstatus='2' OR appstatus='3')";
		ArrayList<Object> arrAppList = GHealthServer.sqlConn.sendSqlQuery(query);
		if (arrAppList.isEmpty())
		{
			System.out.println("noooooooooo");
			VAHEnt.appResultsFlag=false;
		}
		else
		{
			VAHEnt.appResultsFlag=true;
			query ="SELECT id,experties FROM ghealth.expert";
			ArrayList<Object> arrExpert = GHealthServer.sqlConn.sendSqlQuery(query);
			query="SELECT idclinic,Name FROM ghealth.clinic";
			ArrayList<Object> arrClinic = GHealthServer.sqlConn.sendSqlQuery(query);
			
			System.out.println("before\n"+arrAppList);
			System.out.println(arrExpert);
			System.out.println(arrClinic);
			for (int i=1;i<arrAppList.size();i+=8)//8?
				arrAppList.set(i, arrExpert.get((arrExpert.indexOf(arrAppList.get(i))+1)));
			for (int i=7;i<arrAppList.size();i+=8)//8?
				arrAppList.set(i, arrClinic.get((arrClinic.indexOf(arrAppList.get(i))+1)));
			System.out.println("after\n"+arrAppList);
			VAHEnt.arrApp=arrAppList;
			arrExpert.clear();
			arrClinic.clear();
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
		if (arg instanceof ViewAppHistoryEntity)
		{
			if (((ViewAppHistoryEntity)arg).appResultsFlag)
			{

				VAHEnt1.arrApp=(ArrayList<Object>) ((ViewAppHistoryEntity)arg).arrApp;
				
				MainClass.masterControler.VLRCont.viewapphistoryview.fileArea.setText("");
				for (int i=0;i<VAHEnt1.arrApp.size();i+=7)
					MainClass.masterControler.VLRCont.viewapphistoryview.fileArea.setText((String)VAHEnt1.arrApp.get(i)+
					" "+(String)VAHEnt1.arrApp.get(i+1)+
					" "+(String)VAHEnt1.arrApp.get(i+2)+
					" "+(String)VAHEnt1.arrApp.get(i+3)+
					" "+(String)VAHEnt1.arrApp.get(i+4)+
					" "+(String)VAHEnt1.arrApp.get(i+5)+
					" "+(String)VAHEnt1.arrApp.get(i+6)+
					" "+(String)VAHEnt1.arrApp.get(i+7));
				MainClass.masterControler.setView(MainClass.masterControler.VLRCont.viewapphistoryview);
			}
			else 
			{
				JOptionPane.showMessageDialog(null, "No appointment record for patient!","",JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}