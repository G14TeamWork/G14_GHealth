package ocsf.server;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.awt.Color;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
				
				if(((RecordAppointmentEntity)msg).taskToDo.equals("search")){
					System.out.println("GHealth server going to checkappsql");
					SMC.EXPVCont.checkAppSQL((RecordAppointmentEntity)msg);
					System.out.println("GHealth server coming back from checkappsql");
				}
				break;
				
			case "FillTestResEntity":
				if(!((FillTestResEntity)msg).updateFlag)
					SMC.FTRCont.checkExistanceSql((FillTestResEntity)msg);
				else SMC.FTRCont.insertTestResSql((FillTestResEntity)msg);
				break;
				
			case "SetAppointmentEntity":
				SMC.SACont.checkExistanceSql((SetAppointmentEntity)msg);
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
		/*		
			case "HeatingFuelEnt":{
				
				if (!((HeatingFuelEnt)msg).toUpdate)SMC.heatingfuelcont.GetPriceFromSQL((HeatingFuelEnt)msg);
				else ((HeatingFuelEnt)msg).flag= SMC.heatingfuelcont.SendToDB((HeatingFuelEnt)msg);
					
				break;
			}
				
			case "InventoryEntity":
				if (!((InventoryEntity)msg).toUpdate)SMC.inventoryCont.getStockFromSQL((InventoryEntity)msg);
				else SMC.inventoryCont.updateStockFromSql((InventoryEntity)msg);
				break;
				
			case "ReportEntity":
				{
					String s=((ReportEntity)msg).getReportType();
					switch (s){
					case  "Incomes":
						{
							SMC.staMngReportsCont.getIncomesFromSQL((ReportEntity)msg);
							break;
						}
					case "Purchases":
						{	
							for (int i=1 ; i<4 ; i++) SMC.staMngReportsCont.getFuelingFromSQL((ReportEntity)msg, i);
							SMC.staMngReportsCont.getHomeHeatFromSql((ReportEntity)msg);
							break;					
						}
					case "Inventory":
						{
							SMC.staMngReportsCont.getStockFromSQL((ReportEntity)msg);
							break;
						}
					}
					break;
				}
				
			case "GasStationController":		
				break;	
				
			case "StartNewCmpEntity":
				if (((StartNewCmpEntity)msg).getDataUpload() == null) SMC.StartNewCmpCont.getCampaignsFromSql((StartNewCmpEntity)msg);
				else SMC.StartNewCmpCont.updateCampaingSettings((StartNewCmpEntity)msg);
				break;
				
			case "AnalyticSystemEntity":
				SMC.marketManagCont.updateDataSql();
				break;
				
			case "CustomerOrdersEntity":
				SMC.OrderStatCont.getOrdersFromSQL((CustomerOrdersEntity)msg);
				break;
				
			case "MngCustomersEntity":
				int state = ((MngCustomersEntity)msg).state;
				
				switch (state) {
				case 0:
					SMC.mngCustomersCont.getCustomerFromSql(((MngCustomersEntity)msg));
					break;
				case 1:
					SMC.mngCustomersCont.setCustomerToSql(((MngCustomersEntity)msg));
					break;
				case 2:
					SMC.mngCustomersCont.getNetworksFromSql(((MngCustomersEntity)msg));
					break;
				case 3:
					SMC.mngCustomersCont.createCustomerInSql(((MngCustomersEntity)msg));
					break;
				}

				break;
				
			case "SetPriceEntity":
				//if upload -> set, else -> get
				if(((SetPriceEntity)msg).upload)SMC.setPriceController.setDatatoSql((SetPriceEntity)msg);
				else SMC.setPriceController.getDataFromSql((SetPriceEntity)msg);
				break;
				
			case "NetworkMngEntity":
				//if upload -> set, else -> get
				if(((NetworkMngEntity)msg).upload)SMC.networkMngController.setDataToSql((NetworkMngEntity)msg);//upload data
				else SMC.networkMngController.getDataFromSql((NetworkMngEntity)msg);
				break;
			case "MarketingReportEnt":
			{
				
				if (((MarketingReportEnt)msg).gettypes) {
					SMC.marketManagCont.getTypes((MarketingReportEnt)msg);
				} else {
				
					String s=((MarketingReportEnt)msg).getReportType();
					switch (s){
					case  "Customers by rate":
						{
							SMC.marketManagCont.getCostomerListFromSQL((MarketingReportEnt)msg);
							break;
						}
					case "Fueling in sale":
						{	
							SMC.marketManagCont.getFuelingListFromSql((MarketingReportEnt)msg);
							break;					
						}
					}
					break;*/
					
				}
			sendBackToClient(msg,client);	
		}
	//}
		//**************  Return the object  *****************//
			
		
	//}



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
