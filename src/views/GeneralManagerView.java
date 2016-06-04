package views;

import graphics.GUIimage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSeparator;

import mainPackage.MainClass;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import java.awt.Font;

public class GeneralManagerView extends JPanel {
	private static final long serialVersionUID = 1L;
	public JButton btnLogout;
	public JSeparator separator;
	public JButton btnViewPeriodicreport;
	public GeneralManagerView() {
		setLayout(null);
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
		
		JLabel lblExpert = new JLabel("General Manager");
		lblExpert.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblExpert.setBounds(211, 182, 253, 118);
		add(lblExpert);
		
		btnLogout = new JButton();
		btnLogout.setText("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainClass.masterControler.LoginCont.loginEntity.setLogout(true);
				MainClass.masterControler.setView(
						MainClass.masterControler.LoginCont.loginView,
						MainClass.masterControler.LoginCont);
				// change user status to false
			}
		});
		btnLogout.setBounds(490, 440, 140, 55);
		add(btnLogout);
		btnLogout.setIcon(new GUIimage("logout", 25, 23).image);
		
		btnViewPeriodicreport = new JButton();
		btnViewPeriodicreport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainClass.masterControler.setView(
						MainClass.masterControler.VPRCont.ViewPeriodicReportview,
						MainClass.masterControler.VPRCont);
			}
		});
		btnViewPeriodicreport.setText("View periodic report");
		btnViewPeriodicreport.setBounds(449, 372, 181, 55);
		add(btnViewPeriodicreport);
	}
}
