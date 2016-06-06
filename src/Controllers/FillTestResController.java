package Controllers;

import graphics.GUIimage;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
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
	public void SaveTestResult()
	{
		FTRpat1.TestRes=FillTestResview.textField_TestResult.getText();
		FTRpat1.TestType=(String)FillTestResview.comboBox_test.getSelectedItem();
		 MainClass.ghealth.sendMessegeToServer(FTRpat1);
	}
	public void setFTR_Patient()
	{
		 FTRpat1=new FillTestResEntity();
		 FTRpat1.pat.setId(FillTestResview.textFieldid.getText());
		 MainClass.ghealth.sendMessegeToServer(FTRpat1);
	}
	public void checkExistanceSql(FillTestResEntity FTRpat)
	{
		String query = "";
		
		query = "SELECT firstname,lastname FROM ghealth.patient where "
				+ "id = \"" + FTRpat.pat.getId() + "\"";
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		if (arrList.isEmpty())
		{
			System.out.println("noooooooooo");
			FTRpat.showbuttonflag=false;
		}
		else
		{
			FTRpat.showbuttonflag=true;
			FTRpat.pat.setFirstname((String)arrList.get(0));
			FTRpat.pat.setLastname((String)arrList.get(1));
			arrList.clear();
		}
		
	}
	
	@Override
	public void refreshView() {
		MainClass.masterControler.setView(MainClass.masterControler.FTRCont.FillTestResview);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof FillTestResEntity)
		{
			FTRpat1.pat.setFirstname(((FillTestResEntity) arg).pat.getFirstname());
			FTRpat1.pat.setLastname(((FillTestResEntity) arg).pat.getLastname());
			FillTestResview.textField_first.setText("Patient name: "+ FTRpat1.pat.getFirstname()+" "+FTRpat1.pat.getLastname());
			FTRpat1.showbuttonflag=((FillTestResEntity) arg).showbuttonflag;
			if (FTRpat1.showbuttonflag)
			{
				FillTestResview.textField_TestResult.setEditable(true);
				FillTestResview.btnSave.setEnabled(true);
				FillTestResview.btnAddPhoto.setEnabled(true);
				FillTestResview.textField_first.setForeground(Color.BLACK);
				FillTestResview.comboBox_test.setVisible(true);
			}
			else 
			{
				FillTestResview.comboBox_test.setVisible(false);
				FillTestResview.textField_TestResult.setEditable(false);
				FillTestResview.btnSave.setEnabled(false);
				FillTestResview.btnAddPhoto.setEnabled(false);
				FillTestResview.btnAddPhoto.setIcon(new GUIimage("xSign", 25, 23).image);
				FillTestResview.textField_first.setText("Error! enter valid patient ID!");
				FillTestResview.textField_first.setForeground(Color.RED);
			}
			
		}
	}
}