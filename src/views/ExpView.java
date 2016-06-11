package views;

import Controllers.*;
import graphics.GUIimage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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

import javax.swing.ImageIcon;

import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ExpView extends JPanel {
	private static final long serialVersionUID = 1L;
	public JButton btnLogout;
	public JSeparator separator;
	public JButton btnRecordAppointment;
	public JButton btnRequstDetails;
	public JButton btnViewMedicalHistory;
	public JLabel lblExpname;
	public String tmp;
		
	public ExpView() {
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
		
		btnRecordAppointment = new JButton();
		btnRecordAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (arg0.getSource()==btnRecordAppointment){
					try{
						String appID = JOptionPane.showInputDialog(null, "Insert appointment number : ");
						MainClass.masterControler.EXPVCont.checkApp(appID);
						Thread.sleep(200);
						if(MainClass.masterControler.EXPVCont.RAE1.appointment.getIdpatient()!=null){
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
				MainClass.masterControler.VMHCont.ViewMedicalHistoryview.textField_patname.setVisible(false);
				MainClass.masterControler.VMHCont.ViewMedicalHistoryview.textFieldid.setText("");
				MainClass.masterControler.VMHCont.ViewMedicalHistoryview.btnViewLabHis.setEnabled(false);
				MainClass.masterControler.VMHCont.ViewMedicalHistoryview.btnViewmedicalHis.setEnabled(false);
					MainClass.masterControler.setView(
						MainClass.masterControler.VMHCont.ViewMedicalHistoryview,MainClass.masterControler.VMHCont);
			
			}
		});
		btnViewMedicalHistory.setText("View medical history");
		btnViewMedicalHistory.setBounds(490, 232, 140, 55);
		add(btnViewMedicalHistory);
	}
}
