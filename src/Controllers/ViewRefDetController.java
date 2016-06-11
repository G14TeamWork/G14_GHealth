package Controllers;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ocsf.server.GHealthServer;
import mainPackage.MainClass;
import views.ViewRefDetView;
import Controllers.IRefresh;
import Entities.RefDetailsEntity;
import Entities.Reference;

public class ViewRefDetController implements Observer,IRefresh  {
	public ViewRefDetView ViewRefDetview;
	public RefDetailsEntity rde;
	
	public ViewRefDetController() {
		ViewRefDetview = new ViewRefDetView();
	}
	
	public void getPatientRefs(String ID){//TODO
		rde.patID = ID;
		rde.rlist = new ArrayList<Reference>();
		MainClass.ghealth.sendMessegeToServer(rde);
	}
	
	public void serverGetPatientRefs(RefDetailsEntity RDE){//TODO
		ArrayList<Object> arrList = new ArrayList<Object>();
		String query = "SELECT * FROM ghealth.`references` WHERE idpatient='" + RDE.patID + "'";
		int i=0;
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		
		while (!arrList.isEmpty()){
			i++;
			RDE.rlist.add(new Reference());
			RDE.rlist.get(i).setId(Integer.valueOf((String)(arrList.get(0))));
			RDE.rlist.get(i).setPatid((String)(arrList.get(0)));
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