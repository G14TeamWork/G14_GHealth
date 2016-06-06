package Controllers;

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
	public boolean flag;
	public FillTestResController() {
		FillTestResview = new FillTestResView();
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
			//MainClass.masterControler.FTRCont.FillTestResview.flagg=false;
			System.out.println("noooooooooo");
			FTRpat.showbuttomflag=false;
			//MainClass.masterControler.FTRCont.flag=false;
		}
		else
		{
			FTRpat.showbuttomflag=true;
			//MainClass.masterControler.FTRCont.FillTestResview.flagg=true;
			FTRpat.pat.setFirstname((String)arrList.get(0));
			FTRpat.pat.setLastname((String)arrList.get(1));
			arrList.clear();
			//MainClass.masterControler.FTRCont.flag=true;
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
			FillTestResview.textField_first.setText(FTRpat1.pat.getFirstname());
			FillTestResview.textField_last.setText(FTRpat1.pat.getLastname());
			FTRpat1.showbuttomflag=((FillTestResEntity) arg).showbuttomflag;
			if (FTRpat1.showbuttomflag)
			{
				FillTestResview.textField_TestResult.setEditable(true);
				FillTestResview.btnSave.setEnabled(true);
				FillTestResview.btnAddPhoto.setEnabled(true);
			}
			else 
			{
				FillTestResview.textField_TestResult.setEditable(false);
				FillTestResview.btnSave.setEnabled(false);
				FillTestResview.btnAddPhoto.setEnabled(false);
			}
			refreshView();
			
		}
	}
}