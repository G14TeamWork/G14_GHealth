package mainPackage;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.progressbar.WebProgressBar;

import ocsf.client.GHealthClient;
import Controllers.MasterController;
import mainPackage.MainClass;

/**
 * Main class of client <br> this is where it all begins.
 * @author Ruslan
 * @splash - jframe that contains all panels we will use
 * @ghealth the client 
 * @masterController - master controller of all controllers
 */
public class MainClass {
	
	public static Boolean debug = false;
	
	public static JFrame splash;
	public static GHealthClient ghealth;
	public static MasterController masterControler;
	
	/**
	 * method that sets default host, creates master controller
	 */
	public static void testMain(String[] args) {
		
		MainClass.debug = true;
		
		//setup default host:port configuration.
		ghealth = new GHealthClient("localhost", 5555);
		
		//one master to rule them all.
		masterControler = new MasterController(ghealth, true);
		
		System.out.println("Test Client");
		
		masterControler.setView(masterControler.LoginCont.loginView);

	}
	/**
	 * main method of client
	 * @param args - non
	 */
	public static void main(String[] args) {
		
		splash = splash();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {  
		
	        WebLookAndFeel.install ();
			WebLookAndFeel.setDecorateDialogs ( true );
			
			//setup default host:port configuration.
			ghealth = new GHealthClient("localhost", 5555);
	
			//one master to rule them all.
			masterControler = new MasterController(ghealth);
			
			splash.setVisible(false);
			System.out.println("Client");
			
			masterControler.setView(masterControler.LoginCont.loginView);
		
			}
		});
	}
	
	public static JFrame splash() {
		JFrame frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int locationX = dim.width /2-(430/2); 
		int	locationY = dim.height/2-(65/2);
		
		frame.setTitle("GHealth");
		frame.setBounds(locationX,locationY, 430, 65);
	
        WebProgressBar progressBar3 = new WebProgressBar ();
        progressBar3.setBounds(8, 5, 409, 25);
        progressBar3.setIndeterminate ( true );
        progressBar3.setStringPainted ( true );
        progressBar3.setString ( "Please wait..." );
        
        frame.add(progressBar3);
        frame.setVisible(true);
        
		return frame;
		
	}
	
}
