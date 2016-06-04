package views;

import Entities.LoginEntity;
import graphics.GUIimage;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import mainPackage.MainClass;
import ocsf.server.GHealthServer;

import com.alee.laf.progressbar.WebProgressBar;

public class mainView {

	public Boolean debug = false;
	
	public JFrame frame;
	public JPanel panel;
	public JLabel Logo;
	public JSeparator separator;
	public SlideContainer slideContainer;
	public static JPanel loadingPanel;
	
	/**
	 * Create the application.
	 * @throws URISyntaxException 
	 */
	public mainView() {
		initialize();
	}
	
	public mainView(Boolean debug) {
		this.debug = debug;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws URISyntaxException 
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(10, 100, 677, 562);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int locationX = dim.width /2-(677/2); 
		int	locationY = dim.height/2-(562/2);
		
		if(GHealthServer.ChangeTitleBool){
			frame.setTitle("Server - GHealth");
			GHealthServer.ChangeTitleBool = false;
		}
		else
			frame.setTitle("Client - GHealth");
		
		frame.setBounds(locationX,locationY ,677, 562);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		slideContainer = new SlideContainer();
		//slideContainer.setBounds(0, 0, 677, 562);
		//slideContainer.setLayout(null);
		
		Logo = new JLabel("");
		Logo.setBounds(0, 0, 677, 123);
		Logo.setIcon(new GUIimage("MainLogo",Logo.getWidth(),Logo.getHeight()).image);  
		
		frame.getContentPane().add(Logo);
		if (!debug) frame.setVisible(true);
		
		
		// Simple indetrminate progress bar
		loadingPanel = new JPanel();
		loadingPanel.setBounds(0, 0, 677, 562);
		loadingPanel.setLayout(null);
		
        WebProgressBar progressBar3 = new WebProgressBar ();
        progressBar3.setBounds(135, 334, 409, 25);
        progressBar3.setIndeterminate ( true );
        progressBar3.setStringPainted ( true );
        progressBar3.setString ( "Please wait..." );
        
        loadingPanel.add(progressBar3);
        
        frame.getContentPane().add(loadingPanel);
		frame.getContentPane().add(slideContainer);
		
		
		//logout automatically when the window is closing
		frame.addWindowListener(new WindowAdapter() {
		
			@Override
			public void windowClosing(WindowEvent e) {

				frame.setVisible(false);
				
				try {
					LoginEntity entity = MainClass.masterControler.LoginCont.loginEntity;
					if (entity!=null && entity.isLoginOK() ) // && !entity.isLogout() ???
						MainClass.ghealth.sendMessegeToServer(entity);
				} catch (Exception x) {}
				
				System.exit(0);

			}
				
		});
    
	}
}








/*
 
 
 		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int locationX = dim.width /2-(677/2); 
		int	locationY = dim.height/2-(562/2);
		
		if(GasServer.ChangeTitleBool){
//			frame.setBounds(690, 100, 677, 562);
			frame.setBounds(locationX, locationY, 677, 562);
			frame.setTitle("Server - GHealth");
			GasServer.ChangeTitleBool = false;
		}
		else{ 
			frame.setTitle("Client - GHealth");
//			frame.setBounds(10, 100, 677, 562);
			frame.setBounds(locationX,locationY ,677, 562);
		}
 
 
 
 
 */
