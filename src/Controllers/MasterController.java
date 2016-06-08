package Controllers;

import javax.swing.JPanel;

import mainPackage.MainClass;
import ocsf.client.GHealthClient;
import views.SlideContainer;
import views.mainView;

/**
 *	Master Controller runs as a static object in the
 *	main class. It holds all the other controllers
 *	and they hold their views inside them.
 */
public class MasterController {
	
	public MainViewController 			   mainViewCont;
	public LoginController 				   LoginCont;
	public ServerViewController			   serverVCont;
	public EXPViewController 			   EXPVCont;
	public DispatcherController 		   DISCont;
	public LabWorkerController 			   LWCont;
	public ClinicManagerController         CMCont;
	public GeneralManagerController        GMCont;
	public FillTestResController 	       FTRCont;
	public ViewRefDetController 		   VRDCont;  
	public RecordAppointController 		   RACont;		
	public ProduceMonthlyReportController  PMRCont;	
	public ViewMedicalHistoryController	   VMHCont;
	public CancelAppointmentController     CACont;
	public SetAppointmentController 	   SACont;
	public ViewPeriodicReportController    VPRCont;
	public RequestDetailsController	       RDCont;
	public ViewReportsController		   VRCont;
	public viewLabResuControlller	  	   VLRCont;
	/*
	public StationManagerController stationManagerCont;
	
	public FuelingViewController 	fuelingVCont;
	public HeatingFuelController 	heatingfuelcont;
	public SetMinLvController       minLvcont;
	public MarketMngController 		marketManagCont;
	public MarketAgentController 	marketAgentCont;
	public InventoryController 		inventoryCont;
	public StationManagerReportsController staMngReportsCont;
	public StartNewCmpnControlelr	StartNewCmpCont;
	public MngCustomersController	mngCustomersCont;
	public OrderStatusController 	OrderStatCont;
	public SetPriceController		setPriceController;
	public NetworkMngController		networkMngController;
	public NfcController			nfcController;
	*/
// Constructor --------------------------------------------------------------
	
	public MasterController(final Object obj){
		this(obj, false);
	}
	
	public MasterController(final Object obj, Boolean debug){
		
		final MasterController master = this;
		
		mainViewCont  		= new MainViewController(debug);
		LoginCont     		= new LoginController();
		serverVCont			= new ServerViewController();
		
		
		if (obj instanceof GHealthClient) {
		//	LoginCont.setConnectionButton("loading");
			((GHealthClient)obj).addObserver(master.LoginCont);
		}
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				EXPVCont= new EXPViewController();
				DISCont= new DispatcherController();
				LWCont= new LabWorkerController();
				CMCont= new ClinicManagerController();
				GMCont= new GeneralManagerController();
				if (!MainClass.debug) {
					FTRCont= new FillTestResController();
					VRDCont= new ViewRefDetController(); 
					RACont = new RecordAppointController();
					PMRCont= new ProduceMonthlyReportController();
					VMHCont= new ViewMedicalHistoryController();  
					CACont = new CancelAppointmentController();
					SACont = new SetAppointmentController();
					VPRCont= new ViewPeriodicReportController();
					RDCont = new RequestDetailsController();
					VRCont = new ViewReportsController();
					VLRCont= new viewLabResuControlller();
				}
				/*
				 * 
				CustomerVCont 		= new CustomerViewController();
				heatingfuelcont		= new HeatingFuelController();
				OrderStatCont       = new OrderStatusController();
				if (!MainClass.debug) {
					nfcController		= new NfcController();
					stationManagerCont 	= new StationManagerController();
					fuelingVCont		= new FuelingViewController();
					minLvcont           = new SetMinLvController();
					marketManagCont		= new MarketMngController();
					marketAgentCont		= new MarketAgentController();
					inventoryCont       = new InventoryController();
					staMngReportsCont   = new StationManagerReportsController();
					StartNewCmpCont		= new StartNewCmpnControlelr();
					mngCustomersCont	= new MngCustomersController();
					setPriceController	= new SetPriceController();
					networkMngController= new NetworkMngController();
				}
				*/
				if (obj instanceof GHealthClient) {
					GHealthClient ghealth = (GHealthClient)obj;
					if (!MainClass.debug) ghealth.addAllControllersAsObservers(master);  
					System.out.println("number of observers on client: " + ghealth.countObservers());
					
					//try the default connection settings
					ghealth.ConnectToServer();
				
				
					}
		}});
		t.start();
		
	}
	
// Instance Methods ---------------------------------------------------------
	
	/** Takes a JPanel object and slides it into view 
	 * 	on top of the MainView */
	public void setView(JPanel panel) {
		
		if (mainView.loadingPanel.isVisible())
			mainView.loadingPanel.setVisible(false);
		
		mainView MV= mainViewCont.MainView;
		
		MV.frame.remove(MV.slideContainer);
	
		MV.slideContainer = new SlideContainer();
		MV.slideContainer.setBounds(0, 0, 677, 562);
		
		MV.frame.add(MV.slideContainer);
		
		MV.slideContainer.add(panel);
		
	}
	
	/** Method overloading that adds the option to 
	 * 	refresh a view using its IRefresh interface */
	public void setView(JPanel panel,IRefresh cont)
	{
		cont.refreshView();
		this.setView(panel);
	}
	
}
