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
	public String tmp;
	public WebEditorPane sched;
	public JCheckBox checkboxAll;
		
	public ExpView() {
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setLayout(null);
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
		
		JLabel lblExpert = new JLabel("Expert");
		lblExpert.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblExpert.setBounds(290, 151, 181, 65);
		add(lblExpert);
	
		lblExpname = new JLabel("");
		lblExpname.setHorizontalAlignment(SwingConstants.CENTER);
		lblExpname.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblExpname.setBounds(198, 203, 238, 31);
		add(lblExpname);
		
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
			public void actionPerformed(ActionEvent arg0) {
				if (arg0.getSource()==btnRecordAppointment){
					try{
						String appID = JOptionPane.showInputDialog(null, "Insert appointment number : ");
						MainClass.masterControler.EXPVCont.checkApp(appID);
						Thread.sleep(200);
						if(MainClass.masterControler.EXPVCont.RAE1.appointment.getIdpatient()!=null){
							MainClass.masterControler.EXPVCont.expview.sched.setText("");
							MainClass.masterControler.EXPVCont.expview.checkboxAll.setSelected(false);
							MainClass.masterControler.RACont.RecordAppointview.idPatientLabel.setText("Patient : " + MainClass.masterControler.EXPVCont.RAE1.appointment.getIdpatient());
							MainClass.masterControler.RACont.RecordAppointview.appNoLabel.setText("Appointment : " + appID);
							tmp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
							MainClass.masterControler.RACont.RecordAppointview.newRefs = false;
							MainClass.masterControler.RACont.RecordAppointview.startHourLabel.setText("Start Time : " + tmp);
							MainClass.masterControler.EXPVCont.RAE1.appointment.setStartS(tmp);
							MainClass.masterControler.RACont.RecordAppointview.btnProduceLabReference.setVisible(true);
							MainClass.masterControler.RACont.RecordAppointview.record.setText(MainClass.masterControler.EXPVCont.RAE1.appointment.getRecord());
							if (MainClass.masterControler.RACont.RecordAppointview.record.getText().equals("0"))
								MainClass.masterControler.RACont.RecordAppointview.record.setText("");
							MainClass.masterControler.setView(MainClass.masterControler.RACont.RecordAppointview,MainClass.masterControler.RACont);
						}
						else JOptionPane.showMessageDialog(null, "No matching appointment in data server!");
					}catch(Exception e){
						
					}
				}
				/*if (MainClass.masterControler.EXPVCont.checkApp(appID)){
					MainClass.masterControler.RACont = new RecordAppointController();
					MainClass.masterControler.setView(
						MainClass.masterControler.RACont.RecordAppointview,
							MainClass.masterControler.RACont);
				}*/
			}
				
		});
		btnRecordAppointment.setText("Record Appointment");
		btnRecordAppointment.setBounds(490, 372, 140, 55);
		add(btnRecordAppointment);
		
		btnRequstDetails = new JButton();
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
		btnViewMedicalHistory.setText("View Medical History");
		btnViewMedicalHistory.setBounds(490, 232, 140, 55);
		add(btnViewMedicalHistory);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0), 2));

		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(57, 309, 410, 186);
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
		btnSched.setBounds(157, 273, 28, 25);
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
