package ocsf.client;

import java.io.IOException;

import Controllers.MasterController;
import mainPackage.MainClass;
import mainPackage.infoBox;


public class GHealthClient extends ObservableClient{

	/**
	 * Constructor. Sends host and port to super. 
	 * @param host
	 * @param port
	 */
	public GHealthClient(String host, int port) {
		super(host, port);
	}

	
	
	/**
	 * Tries to continually open a connection  
	 * with the server side until success.
	 */
	public void ConnectToServer(){
		
		if(MainClass.ghealth.isConnected()) {
			try { closeConnection(); } 
			catch (IOException e1) { e1.printStackTrace(); }
		}
		
		String userInput = MainClass.masterControler.LoginCont.getServerIP();
		String hostNport[] = (userInput.split(":"));
		setHost(hostNport[0]);
		setPort(Integer.parseInt(hostNport[1]));
		
		// Connection to the server.
		try {
			MainClass.masterControler.LoginCont.setConnectionButton("loading");
			openConnection();
		} catch (IOException e) {
			System.err.println("Connection to server Failed");
			MainClass.masterControler.LoginCont.setConnectionButton("disconnected");
		}
		
		if(MainClass.ghealth.isConnected()) {
			if (MainClass.masterControler.LoginCont.loginEntity!=null)
				MainClass.masterControler.LoginCont.loginEntity.setConnectionLost(false);
			MainClass.masterControler.LoginCont.setConnectionButton("connected");
		}

		MainClass.masterControler.LoginCont.refreshView();
	}	


	/**
	 * Sends a message to the server with the 
	 * sendToServer method.
	 * @param msg
	 */
	public void sendMessegeToServer(Object msg){
		
			try {
				this.sendToServer(msg);
			} catch (IOException e) {
				System.err.println("sendToServer");
				e.printStackTrace();
				System.out.println("Message to Server ERROR");
			}
	}


	@Override
	/**
	 * Most of the time this method is used to send the item
	 * that returned from the server to the observers. every
	 * observer checks the message to see if it is for him.
	 */
	protected void handleMessageFromServer(Object msg) {
		System.out.println("notifyObservers: " + msg.getClass().getSimpleName());
		setChanged();
		notifyObservers(msg);
	}
		

	protected void connectionClosed() {
		System.err.println("Connection closed");
	}


	/**
	 * This method is called when connection to the server
	 * is made. it also notify all the GHealthClient observer. 
	 */
	public void connectionEstablished(){
		setChanged();
		System.out.println("OCSF Connection Established");
		
	}
	
	
	@Override
	protected void connectionException(Exception exception) {
				
		MainClass.masterControler.LoginCont.loginEntity.setLogout(true);
		MainClass.masterControler.LoginCont.loginEntity.setConnectionLost(true);
		MainClass.masterControler.setView(
				MainClass.masterControler.LoginCont.loginView,
				MainClass.masterControler.LoginCont);
		infoBox.show("Connection is lost", "Connection Error");
		
	}


	/**
	 * Adds all the controllers as observers of the GHealthClient
	 * class in order to be notified when messages come from the server.
	 */
	public void addAllControllersAsObservers(MasterController master){
		//addObserver(master.LoginCont);
		addObserver(master.FTRCont); //???
		addObserver(master.VRDCont);
		//addObserver(master.RACont);
		addObserver(master.PMRCont);
		addObserver(master.VMHCont);
		addObserver(master.CACont);
		addObserver(master.SACont);
		addObserver(master.VPRCont);
		addObserver(master.RDCont);
		addObserver(master.VRCont);
		/*
		addObserver(master.minLvcont);
		addObserver(master.fuelingVCont);
		addObserver(master.heatingfuelcont);
		addObserver(master.inventoryCont);
		addObserver(master.staMngReportsCont);
		addObserver(master.StartNewCmpCont);
		addObserver(master.mngCustomersCont);
		addObserver(master.marketManagCont);
		addObserver(master.OrderStatCont);
		addObserver(master.setPriceController);
		addObserver(master.networkMngController);
		addObserver(master.nfcController);
		*/
	}
}
