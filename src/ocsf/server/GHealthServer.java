package ocsf.server;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.awt.Color;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import mainPackage.MainClass;
import ocsf.client.GHealthClient;
import views.ServerView;
import Controllers.*;
import db.SQLconnection;
import Entities.*;

/** Server class responsible for the server communication and data base connection */
public class GHealthServer extends ObservableServer{

	public static Boolean debug = false;
	
	public static SQLconnection sqlConn;
	public static GHealthServer ghealth_server;
	public static MasterController ServerMasterCont;
	public static boolean ChangeTitleBool;
	public static String IP;
	
	public static void testMain(String[] args) {
		
		GHealthServer.debug = true;
		GHealthServer.main(null);

	}
	
	/** Starts the server and tests the connection with the data base.*/
	public static void main(String[] args) {
		
		ChangeTitleBool = true;
		
		// get hosts IP
		try {IP = "" + InetAddress.getLocalHost().getHostAddress();} 
		catch (UnknownHostException e) {e.printStackTrace();}
				
		ServerMasterCont = new MasterController(ghealth_server, debug);
		ServerMasterCont.setView(ServerMasterCont.serverVCont.serverView);
		
		
		System.out.println("SERVER : " + IP);
		
//		for debuggin - skip connection:
		StartServer(5555);
		ConnectToSQL("root","Braude");
		sendAutoEmailAlert();
		
		
		
	}

	
	
	/** Starts listening for clients */
	public static void StartServer(int port){
		
		ServerView SV = ServerMasterCont.serverVCont.serverView;
		
		if(ghealth_server == null)
			ghealth_server = new GHealthServer(port);

		try {
			ghealth_server.listen();
		} catch (IOException e) {
			System.out.println("ghealth_server.listen in StartServer");
		}
		
		if(ghealth_server.isListening()){
			  System.out.println("Listenning on port "+ port);
			  SV.lblServerStateWarning("Online" ,Color.GREEN);
		}else SV.lblServerStateWarning("Offline",Color.RED);
		
	}
	
	
	/** Close the server connection, disconnecting all clients 
	 *	and stops listening for new ones.*/
	public static void StopServer(){
		
		boolean catched = false;
		ServerView SV = ServerMasterCont.serverVCont.serverView;
		
		try {
			ghealth_server.close();
		} catch (IOException e) {
			System.err.println("Cant close connection - StopServer");
			catched = true;
			//e.printStackTrace();
		}
		
		if(!catched) SV.lblServerStateWarning("Offline",Color.RED);
		else SV.lblServerStateWarning("ERROR",Color.BLACK);
	}
	
	
	/** Makes a single connection to test connectivity */
	public static void ConnectToSQL(String username,String password){
		try {

			sqlConn = new SQLconnection(username,password);

		}catch (Exception ex){
			//System.err.println("Sql Connection error");
			}// exception thrown in the constructor if needed.
		
		if (sqlConn != null) {
			String query = "UPDATE ghealth.users SET status=0";
			sqlConn.sendSqlUpdate(query);
		}
			
	}

	public GHealthServer(int port) {
		super(port);
	}

	/** 
	 * 	Override abstract server class method. Handles messages from the client side.
	 * 	taking an object as a parameter and performing actions according to its name.
	 * 	Eventually sending back the object after it has been changed.
	 * 
	 */
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {

		MasterController SMC = ServerMasterCont;	// SMC is here to make it short.
		
		String className = msg.getClass().getSimpleName();
		System.out.println(className + " in GHealth Server Switch");
		
		switch(className){
	
			case "String":
				break;
			case "RecordAppointmentEntity":
			{	
				if(((RecordAppointmentEntity)msg).taskToDo.equals("search")){
					//System.out.println("GHealth server going to checkappsql");
					SMC.EXPVCont.checkAppSQL((RecordAppointmentEntity)msg);
					//System.out.println("GHealth server coming back from checkappsql");
				}
			}
				break;
				
			case "ViewHistoryEntity":
				if(((ViewHistoryEntity)msg).photoflag)
					SMC.VMHCont.askPhotoFromTestResSql((ViewHistoryEntity)msg);
				else 
					SMC.VMHCont.checkExistanceSql((ViewHistoryEntity)msg);
				break;
				
			case "FillTestResEntity":
				if(!((FillTestResEntity)msg).updateFlag)
					SMC.FTRCont.checkExistanceSql((FillTestResEntity)msg);
				else SMC.FTRCont.insertTestResSql((FillTestResEntity)msg);
				break;
				
			case "SetAppointmentEntity":
			{
				if(((SetAppointmentEntity)msg).getTask().equals("searchPatient"))
					SMC.SACont.checkExistanceSql((SetAppointmentEntity)msg);
				else 
				{
					if(((SetAppointmentEntity)msg).getTask().equals("searchExpert"))
						SMC.SACont.searchExpertSql((SetAppointmentEntity)msg); 
					else if(((SetAppointmentEntity)msg).getTask().equals("searchAvailableAppointment"))
						SMC.SACont.searchAvailableAppointmentSql((SetAppointmentEntity)msg);
				}
						
				
				
			}
				break;
				
			case "LoginEntity":
				if (((LoginEntity)msg).getStatus()==1) SMC.LoginCont.sendLogOutToSql((LoginEntity)msg);
				else SMC.LoginCont.toSQL((LoginEntity)msg); 
				break;
		
			case "CancelAppointmentEntity":
				if(((CancelAppointmentEntity)msg).getTaskToDo().equals("search"))
					SMC.CACont.checkExistanceSql((CancelAppointmentEntity)msg);
				if(((CancelAppointmentEntity)msg).getTaskToDo().equals("search"))
					SMC.CACont.searchAppointmentSQL((CancelAppointmentEntity)msg);
				else if(((CancelAppointmentEntity)msg).getTaskToDo().equals("delete"))
					SMC.CACont.deleteAppintmentSQL(((CancelAppointmentEntity)msg));
				break;
			case "Patient":
				SMC.SACont.AddNewPatient((Patient)msg);
				break;		
				}
			
			sendBackToClient(msg,client);	
		}
	//}
		//**************  Return the object  *****************//
			
		
	//}

	public static void sendAutoEmailAlert()	
	{
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					
					ArrayList<Object> arrList = new ArrayList<Object>();
					String query =	"select ghealth.appointments.idappointment,ghealth.appointments.dispatcherSettingHour,ghealth.patient.email,"
									+"ghealth.expert.experties,ghealth.users.firstname,ghealth.users.lastname,"
									+"ghealth.clinic.Name,ghealth.clinic.Address,ghealth.clinic.Phone,ghealth.appointments.sentemail,"
									+"ghealth.patient.firstname,ghealth.patient.lastname "
									+"FROM "
									+"ghealth.expert,ghealth.patient,ghealth.appointments,ghealth.users,ghealth.clinic "
									+"WHERE "
									+"ghealth.appointments.appdate  = current_date()+ INTERVAL +1 DAY and ghealth.expert.id = ghealth.appointments.idexpert "
									+"and ghealth.patient.id  = ghealth.appointments.idpatient and ghealth.users.username = ghealth.appointments.idexpert "
									+"and ghealth.clinic.idclinic = ghealth.appointments.idclinic and ghealth.appointments.sentemail = 0";
							
					String query2  = "UPDATE ghealth.appointments SET ghealth.appointments.sentemail=1 WHERE ghealth.appointments.idappointment=";
					
					arrList = GHealthServer.sqlConn.sendSqlQuery(query);		
					
					for(int i = 0 ; i < arrList.size() ; i +=12)
					{
						System.out.println(arrList.get(0));
						GHealthServer.sqlConn.sendSqlUpdate(query2+String.valueOf(arrList.get(i))+";");
						// mail: 		ghealthg14@gmail.com
						// password: 	g14ghealth
						SendEmail.sendFromGMail("ghealthg14@gmail.com", "g14ghealth"
								,(String)arrList.get(i+2) , "Alert appointment with a specialist",
								"Hello "+(String)arrList.get(i+10)+" "+(String)arrList.get(i+11)
								+"\nTomorrow you will have an appointment at "+(Time)arrList.get(i+1)
								+" with our "+(String)arrList.get(i+3)
								+" "+(String)arrList.get(i+4)+" "+(String)arrList.get(i+5)
								+"\nin clinic: "+(String)arrList.get(i+6)
								+"\nAddress: "+(String)arrList.get(i+7)
								+"\nClinic phone number: "+(String)arrList.get(i+8)
								+"\nThank you,\n     Ghealth");
					}
					
			
					
				}
			});
			t.setPriority(Thread.MIN_PRIORITY);
			t.start();
	}			
		
	
	
	
	/** Send the object back to the client */
	protected void sendBackToClient(Object msg, ConnectionToClient client) {
		try {client.sendToClient(msg);} 
		catch (IOException e) {
			e.printStackTrace();
			System.err.println("SendToClient: " + msg.getClass().getSimpleName());}
	}
	
	
	protected synchronized void clientConnected(ConnectionToClient client) {
		System.out.println("Client Connected");
		super.clientConnected(client);
	}
}
