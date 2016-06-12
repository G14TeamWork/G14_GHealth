package views;


import graphics.GUIimage;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

//import javafx.stage.FileChooser;



import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ocsf.server.GHealthServer;

import com.alee.laf.button.WebButton;
import com.alee.laf.optionpane.WebOptionPane;

import db.SET_DB_WITH_SQL_FILE;

public class ServerView extends JPanel {

	private static final long serialVersionUID = 1L;
	//private JPanel contentPane;
	private JTextField textFieldPort;
	private JTextField textFieldSQLusr;
	private JTextField textFieldSQLpass;
	private JLabel lblServerStateWarning;
	private JLabel lblMysqlStateWarning;
	private JLabel lblMysqlState;
	private JLabel lblServerState;
	private JLabel lblIP;

	/**
	 * Create the frame.
	 */
	public ServerView() {

		setName("Server State");
		
		//*** DO NOT DELETE! ***//
		
				this.setBounds(0, 0, 677, 562);
				this.setLayout(null);
				
				JSeparator separator = new JSeparator();
				separator.setBounds(0, 126, 677, 12);
				add(separator);

				
		JLabel lblNewLabel = new JLabel("Server Side");
		lblNewLabel.setBounds(220, 195, 145, 33);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 27));
		add(lblNewLabel);
		
		JLabel lblPort = new JLabel("Listen on Port:");
		lblPort.setBounds(220, 263, 91, 16);
		add(lblPort);
		
		textFieldPort = new JTextField();
		textFieldPort.setBounds(316, 257, 134, 28);
		textFieldPort.setText("5555");
		add(textFieldPort);
		textFieldPort.setColumns(10);
		
		final WebButton toggleButton = new WebButton("Stop");
		toggleButton.setBounds(227, 433, 223, 47);
		toggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(toggleButton.getText().equals("Start")){// if start:
					toggleButton.setText("Stop");
					GHealthServer.StartServer(Integer.parseInt(textFieldPort.getText()));
					GHealthServer.ConnectToSQL(textFieldSQLusr.getText(),textFieldSQLpass.getText());
				}else{// if stop:
					GHealthServer.StopServer();
					if(GHealthServer.sqlConn != null)
						GHealthServer.sqlConn.closeSqlConnection();
					toggleButton.setText("Start");
				}
			}
		});
		
		JLabel lblSqlUsername = new JLabel("SQL Username");
		lblSqlUsername.setBounds(220, 296, 90, 16);
		add(lblSqlUsername);
		
		textFieldSQLusr = new JTextField();
		textFieldSQLusr.setBounds(316, 290, 134, 28);
		textFieldSQLusr.setText("root");
		textFieldSQLusr.setColumns(10);
		add(textFieldSQLusr);
		
		JLabel lblSqlPassword = new JLabel("SQL Password");
		lblSqlPassword.setBounds(220, 328, 87, 16);
		add(lblSqlPassword);
		
		
		textFieldSQLpass = new JTextField();
		textFieldSQLpass.setText("Braude");
		textFieldSQLpass.setBounds(316, 323, 134, 28);
		textFieldSQLpass.setColumns(10);
		add(textFieldSQLpass);
		textFieldSQLpass.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {} // must implement interface
			public void focusGained(FocusEvent e) {textFieldSQLpass.selectAll();}
		});
		
		
		textFieldSQLpass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				toggleButton.doClick();
			}
		});
		
		lblServerState = new JLabel("Server State: ");
		lblServerState.setBounds(245, 384, 68, 14);
		lblServerState.setHorizontalAlignment(SwingConstants.CENTER);
		lblServerState.setFont(new Font("Tahoma", Font.PLAIN, 11));
		add(lblServerState);
		
		lblServerStateWarning = new JLabel("Offline");
		lblServerStateWarning.setBounds(379, 384, 34, 14);
		lblServerStateWarning.setHorizontalAlignment(SwingConstants.CENTER);
		lblServerStateWarning.setForeground(Color.RED);
		lblServerStateWarning.setFont(new Font("Tahoma", Font.PLAIN, 11));
		add(lblServerStateWarning);
		
		toggleButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(toggleButton);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(209, 240, 252, 12);
		add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(209, 363, 252, 12);
		add(separator_2);
		
		lblMysqlState = new JLabel("MySQL State:");
		lblMysqlState.setHorizontalAlignment(SwingConstants.CENTER);
		lblMysqlState.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMysqlState.setBounds(245, 408, 66, 14);
		add(lblMysqlState);
		
		lblMysqlStateWarning = new JLabel("Offline");
		lblMysqlStateWarning.setHorizontalAlignment(SwingConstants.CENTER);
		lblMysqlStateWarning.setForeground(Color.RED);
		lblMysqlStateWarning.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMysqlStateWarning.setBounds(379, 408, 34, 14);
		add(lblMysqlStateWarning);
		
		lblIP = new JLabel(GHealthServer.IP);//"xxx.xxx.xxx.xxx");
		lblIP.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblIP.setBounds(368, 211, 107, 14);
		add(lblIP);
	}


	public void lblServerStateWarning(String str,Color color) {
		this.lblServerStateWarning.setText(str);
		this.lblServerStateWarning.setForeground(color);
	}
	
	public void lblMysqlStateWarning(String str,Color color) {
		this.lblMysqlStateWarning.setText(str);
		this.lblMysqlStateWarning.setForeground(color);
	}
}



