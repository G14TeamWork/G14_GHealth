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
/**
 * This class extends JPanel. <br>
 * It is a view containing components of gui. <br>
 * This is the screen that opens when clinic logs in. <br>
 * Almost all functionality of GUI is here.
 * @author Ruslan
 *
 */
public class ClinicManagerView extends JPanel {
	private static final long serialVersionUID = 1L;
	public JButton btnLogout;
	public JSeparator separator;
	public JButton btnViewReports;
	public JButton btnProduceReport;
	public JLabel lblClinicManagername;
	public ClinicManagerView() {
		setLayout(null);
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
		
		JLabel lblClinicManager = new JLabel("Clinic Manager");
		lblClinicManager.setHorizontalAlignment(SwingConstants.CENTER);
		lblClinicManager.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblClinicManager.setBounds(10, 151, 655, 65);
		add(lblClinicManager);
		
		lblClinicManagername = new JLabel("");
		lblClinicManagername.setHorizontalAlignment(SwingConstants.CENTER);
		lblClinicManagername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblClinicManagername.setBounds(20, 203, 645, 31);
		add(lblClinicManagername);
		
		
		btnLogout = new JButton();
		btnLogout.setText("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainClass.masterControler.LoginCont.loginEntity.setLogout(true);
				MainClass.masterControler.LoginCont.loginView.getTextFieldUserName().setText("");
				MainClass.masterControler.LoginCont.loginView.getPwdPassword().setText("");
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
				MainClass.masterControler.CMCont.searchClinicIdClient();
				MainClass.masterControler.setView(
						MainClass.masterControler.VRCont.ViewReportsview,
						MainClass.masterControler.VRCont);
			}
		});
		btnViewReports.setText("View reports");
		btnViewReports.setIcon(new GUIimage("eye", 25, 23).image);
		btnViewReports.setBounds(490, 372, 140, 55);
		add(btnViewReports);

		btnProduceReport = new JButton("<html><center>Produce<br />Report</html>");
		btnProduceReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
/*				MainClass.masterControler.setView(
						MainClass.masterControler.PMRCont.ProduceMonthlyReportview,
						MainClass.masterControler.PMRCont);*/
				MainClass.masterControler.CMCont.searchClinicIdClient();
			}
		});
		btnProduceReport.setBounds(490, 306, 140, 55);
		btnProduceReport.setIcon(new GUIimage("report", 25, 23).image);
		add(btnProduceReport);
	}
}