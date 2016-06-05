package views;

import graphics.GUIimage;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSeparator;

import mainPackage.MainClass;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.JTextField;

import ocsf.server.GHealthServer;
import Controllers.MasterController;
import Entities.Patient;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SetAppointmentView extends JPanel {
	private static final long serialVersionUID = 1L;
	public JSeparator separator;
	public JTextField textFieldid;
	public String patientID;
	public JTextField textField_first;
	public JTextField textField_last;
	public Patient pat;
	ArrayList<Object> arrList = new ArrayList<>();
	public SetAppointmentView() {
		setLayout(null);
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
		
		JLabel lblExpert = new JLabel("Set Appointment");
		lblExpert.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblExpert.setBounds(211, 182, 253, 55);
		add(lblExpert);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainClass.masterControler.setView(
						MainClass.masterControler.DISCont.dispatcherview);
			}
		});
		btnBack.setBounds(490, 440, 140, 55);
		add(btnBack);
		btnBack.setIcon(new GUIimage("back", 25, 23).image);
		
		textFieldid = new JTextField();
		//textFieldid.setText("");
		textFieldid.setColumns(10);
		textFieldid.setBounds(271, 236, 148, 28);
		add(textFieldid);
		
		JLabel lblEnterPatientId = new JLabel("Enter patient ID:");
		lblEnterPatientId.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblEnterPatientId.setBounds(146, 235, 120, 28);
		add(lblEnterPatientId);
		
		
		JLabel searchIcon = new JLabel("");
		searchIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MainClass.masterControler.SACont.setPatient();
			}
		});
		searchIcon.setBounds(431, 236, 29, 28);
		searchIcon.setIcon(new GUIimage("search",searchIcon.getWidth(),searchIcon.getHeight()).image);
		this.add(searchIcon);
		
		textField_first = new JTextField();
		textField_first.setEditable(false);
		//textField_first.setText("");
		textField_first.setColumns(10);
		textField_first.setBounds(271, 277, 148, 28);
		add(textField_first);
		
		textField_last = new JTextField();
		textField_last.setEditable(false);
		//textField_last.setText("");
		textField_last.setColumns(10);
		textField_last.setBounds(271, 318, 148, 28);
		add(textField_last);
		
		JLabel lblFirstName = new JLabel("First name:");
		lblFirstName.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblFirstName.setBounds(146, 276, 120, 28);
		add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last name:");
		lblLastName.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblLastName.setBounds(146, 317, 120, 28);
		add(lblLastName);
	}
}
