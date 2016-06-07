package views;

import graphics.GUIimage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import mainPackage.MainClass;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import java.awt.Font;

import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;

import java.awt.Color;

public class GeneralManagerView extends JPanel {
	private static final long serialVersionUID = 1L;
	public JButton btnLogout;
	public JSeparator separator;
	public JButton btnViewWeeklyReport;
	public JLabel lblGeneralManagername;
	
	public GeneralManagerView() {
		setLayout(null);
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
		
		JLabel GeneralManager = new JLabel("General Manager");
		GeneralManager.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		GeneralManager.setBounds(241, 151, 181, 65);
		add(GeneralManager);
		
		lblGeneralManagername = new JLabel("");
		lblGeneralManagername.setHorizontalAlignment(SwingConstants.CENTER);
		lblGeneralManagername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGeneralManagername.setBounds(198, 203, 238, 31);
		add(lblGeneralManagername);
		
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
		
		btnViewWeeklyReport = new JButton();
		btnViewWeeklyReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainClass.masterControler.setView(
						MainClass.masterControler.VPRCont.ViewPeriodicReportview,
						MainClass.masterControler.VPRCont);
			}
		});
		btnViewWeeklyReport.setText("View weekly report");
		btnViewWeeklyReport.setBounds(449, 372, 181, 55);
		add(btnViewWeeklyReport);
		
		JButton btnViewMonthlyReport = new JButton("View monthly report");
		btnViewMonthlyReport.setBounds(449, 306, 181, 55);
		add(btnViewMonthlyReport);
		
		JFormattedTextField frmtdtxtfldEnterClinicId = new JFormattedTextField();
		frmtdtxtfldEnterClinicId.setForeground(new Color(192, 192, 192));
		frmtdtxtfldEnterClinicId.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frmtdtxtfldEnterClinicId.setText("Enter Clinic ID");
		frmtdtxtfldEnterClinicId.setBounds(133, 311, 245, 28);
		add(frmtdtxtfldEnterClinicId);
		
		JLabel searchIcon = new JLabel("");
		searchIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MainClass.masterControler.GMCont.checkClinicExist(frmtdtxtfldEnterClinicId.getText());
			}
		});
		searchIcon.setBounds(388, 310, 29, 28);
		searchIcon.setIcon(new GUIimage("search",searchIcon.getWidth(),searchIcon.getHeight()).image);
		add(searchIcon);
	}
}
