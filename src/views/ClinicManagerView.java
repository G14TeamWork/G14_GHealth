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
import javax.swing.SwingConstants;

public class ClinicManagerView extends JPanel {
	private static final long serialVersionUID = 1L;
	public JButton btnLogout;
	public JSeparator separator;
	public JButton btnViewReports;
	public JButton btnProducemonthlyreport;
	public JLabel lblClinicManagername;
	public ClinicManagerView() {
		setLayout(null);
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
		
		JLabel lblClinicManager = new JLabel("Clinic Manager");
		lblClinicManager.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblClinicManager.setBounds(243, 151, 166, 65);
		add(lblClinicManager);
		
		lblClinicManagername = new JLabel("");
		lblClinicManagername.setHorizontalAlignment(SwingConstants.CENTER);
		lblClinicManagername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblClinicManagername.setBounds(194, 203, 238, 31);
		add(lblClinicManagername);
		
		
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
		
		btnViewReports = new JButton();
		btnViewReports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainClass.masterControler.setView(
						MainClass.masterControler.VRCont.ViewReportsview,
						MainClass.masterControler.VRCont);
			}
		});
		btnViewReports.setText("View reports");
		btnViewReports.setBounds(490, 372, 140, 55);
		add(btnViewReports);
		
		btnProducemonthlyreport = new JButton("<html>Produce monthly<br /><center>Report</center></html>");
		btnProducemonthlyreport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainClass.masterControler.setView(
						MainClass.masterControler.PMRCont.ProduceMonthlyReportview,
						MainClass.masterControler.PMRCont);
			}
		});
		btnProducemonthlyreport.setBounds(490, 306, 140, 55);
		add(btnProducemonthlyreport);
	}
}
