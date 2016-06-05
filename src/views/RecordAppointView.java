package views;

import views.*;
import graphics.GUIimage;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSeparator;

import mainPackage.MainClass;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.JTextPane;

import java.awt.Color;
import javax.swing.JTextField;

public class RecordAppointView extends JPanel {
	private static final long serialVersionUID = 1L;
	public JSeparator separator;
	private JTextField patientID;
	private JTextField appID;
	private JTextField StarHour;
	
	//public RecordAppointView(){};
	public RecordAppointView(String ID, String app, String time) {
		setLayout(null);
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
				
		JTextPane record = new JTextPane();
		record.setBounds(30, 212, 421, 221);
		add(record);
		
		JLabel lblExpert = new JLabel("Record Appointment");
		lblExpert.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblExpert.setBounds(192, 136, 253, 118);
		add(lblExpert);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setForeground(Color.BLACK);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Cancel record?",null,JOptionPane.YES_NO_OPTION)==0){
					MainClass.masterControler.setView(MainClass.masterControler.EXPVCont.expview);
					record.setText("");
					
				}
			}
		});
		btnCancel.setBounds(493, 462, 140, 55);
		add(btnCancel);
		btnCancel.setIcon(null);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Save Record?",null,JOptionPane.YES_NO_OPTION)==0){
					MainClass.masterControler.setView(MainClass.masterControler.EXPVCont.expview);
					MainClass.masterControler.EXPVCont.expview.flag=false;
					record.setText("");
				}
			}
		});
		btnSave.setBounds(493, 382, 140, 55);
		add(btnSave);
		
		patientID = new JTextField();
		patientID.setEditable(false);
		patientID.setText("Patient ID : "+ ID);
		patientID.setBounds(493, 212, 140, 20);
		add(patientID);
		patientID.setColumns(10);
		
		appID = new JTextField();
		appID.setText("Appointment No. : " + app);
		appID.setEditable(false);
		appID.setBounds(493, 248, 140, 20);
		add(appID);
		appID.setColumns(10);
		
		StarHour = new JTextField();
		StarHour.setEditable(false);
		StarHour.setText("Start Hour : " + time);
		StarHour.setBounds(493, 288, 140, 20);
		add(StarHour);
		StarHour.setColumns(10);
		
	}
}