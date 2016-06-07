package Controllers;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import mainPackage.MainClass;
import ocsf.server.GHealthServer;
import views.LoginView;
import views.mainView;
import Entities.LoginEntity;
import graphics.GUIimage;

/**
 * Responsible for the processing of the user
 * input in the loginView. Works on the Client AND the
 * Server side. 
 *
 */
public class LoginController implements Observer,IRefresh,Serializable {


	private static final long serialVersionUID = 1L;
	public LoginView loginView;
	public LoginEntity loginEntity;
	
	
	
	/** Constructor */
	public LoginController() {
		loginView = new LoginView();
		
	}
	
public void sendLogOutToSql(LoginEntity LE)
	{
		String query="UPDATE ghealth.users SET status=0 " +
						"WHERE username =" + LE.getUsername();
		GHealthServer.sqlConn.sendSqlUpdate(query);
		LE.setStatus(0);
	}
	
public void setConnectionButton(String state){
		
	
		loginView.smallLoadingAnimation.stop();
		switch (state) {
		case "connected":
			loginView.status.setVisible(true);
			loginView.btnLogin.setEnabled(true);
			loginView.status.setIcon(new GUIimage("connect",loginView.status.getWidth(),loginView.status.getHeight()).image);
			break;

		case "disconnected":
			loginView.status.setVisible(true);
			loginView.btnLogin.setEnabled(false);
			loginView.status.setIcon(new GUIimage("disconnect",loginView.status.getWidth(),loginView.status.getHeight()).image);
			break;
			
		case "loading":
			loginView.status.setVisible(false);
			loginView.smallLoadingAnimation.start();
			break;
		}
		
	}
	public void toSQL(LoginEntity LE) {
		String query = "";
		ArrayList<Object> arrList = new ArrayList<>();
		query = "SELECT usertype,loginatmps,status FROM ghealth.users where "
				+ "username = \"" + LE.getUsername() + "\"";
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		if (arrList.isEmpty())
		{
			LE.setArrayListReturnedEmpty(true);
			return;
		}
		LE.setUsertype ((String)arrList.get(0));
		LE.setloginAttempts ((int)arrList.get(1));
		LE.setStatus((int)arrList.get(2));
		if ( LE.getloginAttempts()>=3 || LE.getStatus()==1 )
		{
			LE.setArrayListReturnedEmpty(true);
			return;
		}
		arrList.clear();
		query = "SELECT * FROM ghealth.users where "
				+ "username = \"" + LE.getUsername() + "\" and "
				+ "password = \"" + LE.getPassword() + "\"";
		
		arrList = GHealthServer.sqlConn.sendSqlQuery(query);
		if(!arrList.isEmpty()){
			LE.setArrayListReturnedEmpty(false);
			LE.setLoginOK(true);
	
			LE.setUsername (Integer.toString((Integer)arrList.get(0)));
			LE.setPassword ("");
			LE.setFirstname((String)arrList.get(3));
			LE.setLastname ((String)arrList.get(4));
			LE.setEmail    ((String)arrList.get(5));
			LE.setStatus   (1);
			LE.setloginAttempts(0);
			
			query="UPDATE ghealth.users SET status=1 " +
					"WHERE username =" + LE.getUsername();
			
			GHealthServer.sqlConn.sendSqlUpdate(query);
			query="UPDATE ghealth.users SET loginatmps=0 " +
					"WHERE username =" + LE.getUsername();
			
			GHealthServer.sqlConn.sendSqlUpdate(query);
			
			arrList.clear();
			String userType=LE.getUsertype();
			switch (userType) {
			case "exp":
				//query="SELECT stationName FROM ghealth.station,g.workers "
					//	+ "WHERE workers.username = "+ LE.getUsername() +" AND station.idStation = workers.stationID";
				//arrList= GasServer.sqlConn.sendSqlQuery(query);
				//LE.setStationName((String)arrList.get(0));
				break;
			case "dis":
				// למלא פרטים להצגה בחלון הבא
				break;
			case "lab":
			    break; 
			case "gmanager":
				
				break;
			}	
		}
		else{
			LE.setArrayListReturnedEmpty(true);
			query="UPDATE ghealth.users SET loginatmps=loginatmps+1 " +
					"WHERE username =" + LE.getUsername();
			GHealthServer.sqlConn.sendSqlUpdate(query);
			query = "SELECT loginatmps FROM ghealth.users where "
					+ "username = \"" + LE.getUsername() + "\"";
			arrList=GHealthServer.sqlConn.sendSqlQuery(query);
			LE.setloginAttempts((int)arrList.get(0));
		}
	
	}//end tosql
	
	
	public void CheckLogin(LoginEntity msg){

		final LoginView 		 LV 	= MainClass.masterControler.LoginCont.loginView;
		EXPViewController 		 EVC = MainClass.masterControler.EXPVCont;
		DispatcherController     DVC = MainClass.masterControler.DISCont;
		LabWorkerController		 LWC = MainClass.masterControler.LWCont;
		ClinicManagerController  CMC = MainClass.masterControler.CMCont;
		GeneralManagerController GMC = MainClass.masterControler.GMCont;
		
		
		if( ((LoginEntity)msg).getArrayListReturnedEmpty())
		{
			LV.lblError.setFont(new Font("Tahoma", Font.PLAIN, 14));
			if ( ((LoginEntity)msg).getUsertype()==null ) 
						LV.lblError.setText("Login Failed: Username does not exist");
			else 
			{
				if (((LoginEntity)msg).getStatus()==1) 	
					LV.lblError.setText("Login Failed: User already logged in");
				else 
				{
					LV.lblError.setText("Login Failed: Wrong username or password");	
					if (((LoginEntity)msg).getloginAttempts()==3)
					{
						LV.lblError.setText(
								"<html><center><b>Account temporarily blocked<br>"
								+ "after too many unsuccessful attempts</b><br>"
								+ "Contact network manager for help<center><html>");
					}
				}
			}
			Timer timer = new Timer();
			TimerTask t = new TimerTask() {
				public void run() { 
					
					LV.popover.show(LV.btnLogin);
					loginView.loadingAnimation.stop();
					loginView.btnLogin.setText("Login");
					
				}
			};
			timer.schedule(t, 500); //0.5 sec delay
		}
		if( ((LoginEntity)msg).isLoginOK() ){

			switch(((LoginEntity)msg).getUsertype()){
			case "exp":
				System.out.println("Usertype: Expert");
				MainClass.masterControler.setView(EVC.expview);
				MainClass.masterControler.EXPVCont.expview.lblExpname.setText(((LoginEntity)msg).getFirstname()+" "+((LoginEntity)msg).getLastname());

				break;
			case "dis":
				System.out.println("Usertype: Dispatcher");
				MainClass.masterControler.setView(DVC.dispatcherview);
				MainClass.masterControler.DISCont.dispatcherview.lblDisname.setText(((LoginEntity)msg).getFirstname()+" "+((LoginEntity)msg).getLastname());
				break;	
			case "lab":
				System.out.println("Usertype: Lab Worker");
				MainClass.masterControler.setView(LWC.labworkerview);
				MainClass.masterControler.LWCont.labworkerview.lblLabworkername.setText(((LoginEntity)msg).getFirstname()+" "+((LoginEntity)msg).getLastname());
				break;
			case "man":
				System.out.println("Usertype: Clinic Manager");
				MainClass.masterControler.setView(CMC.clinicmanagerview);
				MainClass.masterControler.CMCont.clinicmanagerview.lblClinicManagername.setText(((LoginEntity)msg).getFirstname()+" "+((LoginEntity)msg).getLastname());
				break;
			case "gmanager":
				System.out.println("Usertype: General Manager");
				MainClass.masterControler.setView(GMC.generalmanagerview);
				MainClass.masterControler.GMCont.generalmanagerview.lblGeneralManagername.setText(((LoginEntity)msg).getFirstname()+" "+((LoginEntity)msg).getLastname());
				break;
			}
		}
	}

	public void update(Observable o, Object msg) {
		
		//if (msg instanceof LoginEntity
		//		&& (((LoginEntity)msg).getStatus()==1 || ((LoginEntity)msg).getloginAttempts()>0 )){
		
		if (msg instanceof LoginEntity){
			System.out.println("LoginController notified.");
			
			if (((LoginEntity)msg).isLogout()) return;
			
			loginEntity = (LoginEntity)msg;
			CheckLogin((LoginEntity)msg);
		}
		if (MainClass.debug) {
			LoginEntity logEnt = new Entities.LoginEntity("1","gal");
			MainClass.ghealth.sendMessegeToServer(logEnt);}
		}

	public LoginView getLoginView() {
		return loginView;
	}

	/** Implements the IRefresh interface for the login view*/
	public void refreshView() {
		
		loginView.btnLogin.setText("Login");
		loginView.loadingAnimation.stop();
		
		if (loginEntity!=null && loginEntity.isConnectionLost()) {
			setConnectionButton("disconnected");
			return;
		}
		
		if (loginEntity!=null) 
			MainClass.ghealth.sendMessegeToServer(loginEntity);
	
	}
	
	
	public String getServerIP(){
		return loginView.textFieldServerIP.getText();
	}
	
	class ListenToReadMessage implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			
		}
		
	}


}
