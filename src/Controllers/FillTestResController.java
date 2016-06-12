package Controllers;

import graphics.GUIimage;

import java.awt.Color;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;
import ocsf.server.GHealthServer;
import mainPackage.MainClass;
import views.FillTestResView;
import Controllers.IRefresh;
import Entities.FillTestResEntity;


public class FillTestResController implements Observer,IRefresh  ,Serializable {
	private static final long serialVersionUID = 1L;
	public FillTestResView FillTestResview;
	ArrayList<Object> arrList = new ArrayList<>();
	public FillTestResEntity FTRpat1;
	public FillTestResController() {
		FillTestResview = new FillTestResView();
	}
	
	public void setFTR_Patient()
	{
		 FTRpat1=new FillTestResEntity();
		 FTRpat1.pat.setId(FillTestResview.textFieldid.getText());
		 FTRpat1.taskToDo="searchPat";
		 MainClass.ghealth.sendMessegeToServer(FTRpat1);
	}
	public void checkExistanceSql(FillTestResEntity FTRpat)
	{
		String query = "";
		query = "SELECT firstname,lastname FROM ghealth.patient WHERE "
				+ "id = \"" + FTRpat.pat.getId() + "\"";
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		if (arrList.isEmpty())
		{
			System.out.println("noooooooooo");
			FTRpat.taskToDo="noPat";
		}
		else
		{
			FTRpat.taskToDo="yesPat";
			FTRpat.pat.setFirstname((String)arrList.get(0));
			FTRpat.pat.setLastname((String)arrList.get(1));
			arrList.clear();
		}
		
		
	}
	public void findRef()
	{
		 FTRpat1.taskToDo="searchRef";
		 MainClass.ghealth.sendMessegeToServer(FTRpat1);
	}
	public void checkExistanceReferenceSql(FillTestResEntity FTRpat)
	{
		String query = "";
		query = "SELECT idreferences, reftype FROM ghealth.references WHERE "
				+ "idpatient = \"" + FTRpat.pat.getId() +"\"";
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		if (arrList.isEmpty())
		{
			//System.out.println("No");
			FTRpat.taskToDo="noRef";
		}
		else
		{
			FTRpat.taskToDo="yesRef";
			FTRpat.arrRef=arrList;
		}
	}
	
	public void insertTestRes()
	{
		FTRpat1.TestRes=FillTestResview.textField_TestResult.getText();
		FTRpat1.TestType=(String)FillTestResview.comboBox_test.getSelectedItem();
		FTRpat1.PhotoPath=FillTestResview.file_path;
		FTRpat1.taskToDo="insertTest";
		FTRpat1.arrRefid.clear();
		FTRpat1.testIndex=MainClass.masterControler.FTRCont.FillTestResview.comboBox_test.getSelectedIndex()-1;
		MainClass.ghealth.sendMessegeToServer(FTRpat1);
	}
	
	public void insertTestResSql(FillTestResEntity FTRpat)
	{
		String query = "";
		String labworker =FTRpat.labworkerFirstName+" "+FTRpat.labworkerLastName;
		
		String date = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
	    String filledtime = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		if (FTRpat.PhotoPath.contains("."))
			FTRpat.PhotoPath=FTRpat.PhotoPath.substring(0, FTRpat.PhotoPath.lastIndexOf("."));

		query = "INSERT INTO ghealth.test_results (idref, patientid, date, filledtime, testtype, testresult, photo, labworker)"+
		"VALUES ("+(String)(FTRpat.arrRef.get(((int)(FTRpat.testIndex))*2))+
		", \""+FTRpat.pat.getId()+"\""+
		", \""+date+"\""+
		", \""+ filledtime+"\""+
		", \""+FTRpat.TestType+
		"\", \""+FTRpat.TestRes+
		"\", \""+FTRpat.PhotoPath+
		"\", \""+labworker+"\");";
		GHealthServer.sqlConn.sendSqlUpdate(query);	
		
		query="DELETE FROM ghealth.references WHERE idreferences='"
		+(String)(FTRpat.arrRef.get(((int)(FTRpat.testIndex))*2))+"\';";
		GHealthServer.sqlConn.sendSqlUpdate(query);	
	}
	
	@Override
	public void refreshView() {
		//MainClass.masterControler.setView(MainClass.masterControler.FTRCont.FillTestResview);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof FillTestResEntity)
		{

		    if (((FillTestResEntity) arg).taskToDo.equals("yesPat"))
			{
			FTRpat1.pat.setFirstname(((FillTestResEntity) arg).pat.getFirstname());
			FTRpat1.pat.setLastname(((FillTestResEntity) arg).pat.getLastname());
			FillTestResview.textField_first.setText("Patient name: "+ FTRpat1.pat.getFirstname()+" "+FTRpat1.pat.getLastname());
			FillTestResview.textField_TestResult.setEditable(true);
			FillTestResview.btnSave.setEnabled(true);
			FillTestResview.btnAddPhoto.setEnabled(true);
			FillTestResview.textField_first.setForeground(Color.BLACK);
			FillTestResview.comboBox_test.setVisible(true);
			FillTestResview.lblTesttype.setVisible(true);
			MainClass.masterControler.FTRCont.findRef();	
			}
		    else if (((FillTestResEntity) arg).taskToDo.equals("noPat"))
			{
				FillTestResview.comboBox_test.setVisible(false);
				FillTestResview.lblTesttype.setVisible(false);
				FillTestResview.textField_TestResult.setEditable(false);
				FillTestResview.btnSave.setEnabled(false);
				FillTestResview.btnAddPhoto.setEnabled(false);
				FillTestResview.btnAddPhoto.setIcon(new GUIimage("xSign", 25, 23).image);
				FillTestResview.textField_first.setText("Error! enter valid patient ID!");
				FillTestResview.textField_first.setForeground(Color.RED);
			}
		    else if (((FillTestResEntity) arg).taskToDo.equals("noRef"))
			{
		     FTRpat1.arrRef.clear();
		     FillTestResview.comboBox_test.removeAllItems();
			 FillTestResview.textField_first.setForeground(Color.RED);
			 FillTestResview.textField_first.setText("Error! no open references!");
			 FillTestResview.comboBox_test.setVisible(false);
			 FillTestResview.lblTesttype.setVisible(false);
			 FillTestResview.textField_TestResult.setText("");
			 FillTestResview.textField_TestResult.setEditable(false);
			 FillTestResview.btnSave.setEnabled(false);
			 FillTestResview.btnAddPhoto.setEnabled(false);
			 FillTestResview.btnAddPhoto.setIcon(new GUIimage("xSign", 25, 23).image);
			
			}
		    else if (((FillTestResEntity) arg).taskToDo.equals("yesRef"))
		    {
		     FTRpat1.arrRefid.clear();
	    	 FillTestResview.textField_TestResult.setEditable(true);
			 FillTestResview.btnSave.setEnabled(true);
			 FillTestResview.btnAddPhoto.setEnabled(true);
			 FillTestResview.comboBox_test.setVisible(true);
			 FillTestResview.lblTesttype.setVisible(true);
		     FillTestResview.comboBox_test.removeAllItems();
		     FillTestResview.comboBox_test.addItem("");
			 FTRpat1.arrRef=(((FillTestResEntity) arg).arrRef);
			 
			 for (int i=0;i<FTRpat1.arrRef.size();i+=2)
			 {
				FillTestResview.comboBox_test.addItem(FTRpat1.arrRef.get(i+1));
				FTRpat1.arrRefid.add(FTRpat1.arrRef.get(i));
			 }
		    }

		
		}
			
	}
}
