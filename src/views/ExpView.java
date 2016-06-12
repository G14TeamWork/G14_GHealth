package views;

import Controllers.*;
import graphics.GUIimage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import mainPackage.MainClass;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;

import com.alee.laf.text.WebEditorPane;

import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.border.LineBorder;
import java.awt.Cursor;

public class ExpView extends JPanel {
	private static final long serialVersionUID = 1L;
	public JButton btnLogout;
	public JSeparator separator;
	public JButton btnRecordAppointment;
	public JButton btnRequstDetails;
	public JButton btnViewMedicalHistory;
	public JLabel lblExpname;
	public WebEditorPane sched;
	public JCheckBox checkboxAll;
	public JLabel lblHiddenID;
		
	public ExpView() {
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setLayout(null);
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
		
		JLabel lblExpert = new JLabel("Expert");
		lblExpert.setHorizontalAlignment(SwingConstants.CENTER);
		lblExpert.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblExpert.setBounds(10, 151, 655, 65);
		add(lblExpert);
	
		lblExpname = new JLabel("");
		lblExpname.setHorizontalAlignment(SwingConstants.CENTER);
		lblExpname.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblExpname.setBounds(20, 203, 657, 31);
		add(lblExpname);
		
		lblHiddenID = new JLabel("");
		lblHiddenID.setVisible(false);
		add(lblHiddenID);
		
		btnLogout = new JButton();
		btnLogout.setText("Logout");
		
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainClass.masterControler.EXPVCont.expview.sched.setText("");
				MainClass.masterControler.EXPVCont.expview.checkboxAll.setSelected(false);
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
		
		btnRecordAppointment = new JButton();
		btnRecordAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if (arg0.getSource()==btnRecordAppointment)
				{
					String appID = JOptionPane.showInputDialog(null, "Insert appointment number : ");
					MainClass.masterControler.EXPVCont.checkApp(appID,lblHiddenID.getText());
				}
			}
		});
		btnRecordAppointment.setText("<html><center>Record<br />Appointment</html>");
		btnRecordAppointment.setBounds(490, 372, 140, 55);
		btnRecordAppointment.setIcon(new GUIimage("pen", 25, 23).image);
		add(btnRecordAppointment);
		
		btnRequstDetails = new JButton();
		btnRequstDetails.setMargin(new Insets(2, 9, 2, 9));
		btnRequstDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainClass.masterControler.EXPVCont.expview.sched.setText("");
				MainClass.masterControler.EXPVCont.expview.checkboxAll.setSelected(false);
				MainClass.masterControler.RDCont.RequestDetailsview.btnBySpec.setEnabled(false);
				MainClass.masterControler.RDCont.RequestDetailsview.btnEntireFile.setEnabled(false);
				MainClass.masterControler.setView(
						MainClass.masterControler.RDCont.RequestDetailsview,MainClass.masterControler.RDCont);
			}
		});
		btnRequstDetails.setText("Requst Details");
		btnRequstDetails.setBounds(490, 304, 140, 55);
		btnRequstDetails.setIcon(new GUIimage("requestdetails", 24, 23).image);
		add(btnRequstDetails);
		
		btnViewMedicalHistory = new JButton();
		btnViewMedicalHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainClass.masterControler.EXPVCont.expview.sched.setText("");
				MainClass.masterControler.EXPVCont.expview.checkboxAll.setSelected(false);
				MainClass.masterControler.EXPVCont.expview.sched.setText("");
				MainClass.masterControler.EXPVCont.expview.checkboxAll.setSelected(false);
				MainClass.masterControler.VMHCont.ViewMedicalHistoryview.textField_patname.setVisible(false);
				MainClass.masterControler.VMHCont.ViewMedicalHistoryview.textFieldid.setText("");
				MainClass.masterControler.VMHCont.ViewMedicalHistoryview.btnViewLabHis.setEnabled(false);
				MainClass.masterControler.VMHCont.ViewMedicalHistoryview.btnViewmedicalHis.setEnabled(false);
					MainClass.masterControler.setView(
						MainClass.masterControler.VMHCont.ViewMedicalHistoryview);
			
			}
		});

		btnViewMedicalHistory.setIcon(new GUIimage("medicalhistory", 25, 23).image);
		btnViewMedicalHistory.setText("<html><center>View medical<br />history</html>");
		btnViewMedicalHistory.setBounds(490, 232, 140, 55);
		add(btnViewMedicalHistory);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0), 2));

		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(30, 309, 445, 186);
		add(scrollPane);
		
		sched = new WebEditorPane("text/html","");
		sched.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		sched.setRequestFocusEnabled(false);
		sched.setSelectedTextColor(Color.WHITE);
		sched.setSelectionColor(Color.WHITE);
		scrollPane.setViewportView(sched);
		sched.setText("");
		sched.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		sched.setEditable(false);
		sched.setFont(new Font("Dialog", Font.PLAIN, 8));
		
		JButton btnSched = new JButton("");
		btnSched.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainClass.masterControler.EXPVCont.showExpSched();
			}
		});
		btnSched.setBounds(163, 273, 28, 25);
		btnSched.setIcon(new GUIimage("refresh",btnSched.getWidth()-7,btnSched.getHeight()-7).image);
		add(btnSched);
		
		JLabel lblRefresh = new JLabel("Refresh Schedule");
		lblRefresh.setBounds(59, 273, 104, 25);
		add(lblRefresh);
		
		checkboxAll = new JCheckBox("Show all appointments");
		checkboxAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		checkboxAll.setBounds(57, 248, 181, 23);
		add(checkboxAll);
	}
}