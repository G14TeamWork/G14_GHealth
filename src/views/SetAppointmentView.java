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
	public JTextField textField_first;
	public JTextField textField_last;
	public JTextField textField_phone;
	public JTextField textField_email;
	public JTextField textField_adress;
	public SetAppointmentView() {
		setLayout(null);
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
		
		JLabel lblExpert = new JLabel("Set Appointment");
		lblExpert.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblExpert.setBounds(255, 126, 253, 55);
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
		textFieldid.setColumns(10);
		textFieldid.setBounds(154, 171, 148, 28);
		add(textFieldid);
		
		JLabel lblEnterPatientId = new JLabel("Enter patient ID:");
		lblEnterPatientId.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblEnterPatientId.setBounds(22, 170, 120, 28);
		add(lblEnterPatientId);
		
		
		JButton searchIcon = new JButton("");
		searchIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MainClass.masterControler.SACont.setPatient();
			}
		});
		searchIcon.setBounds(314, 171, 29, 28);
		searchIcon.setIcon(new GUIimage("search",searchIcon.getWidth()-7,searchIcon.getHeight()-7).image);
		this.add(searchIcon);
		
		textField_first = new JTextField();
		textField_first.setEditable(false);
		textField_first.setColumns(10);
		textField_first.setBounds(154, 209, 148, 28);
		add(textField_first);
		
		textField_last = new JTextField();
		textField_last.setEditable(false);
		textField_last.setColumns(10);
		textField_last.setBounds(466, 209, 148, 28);
		add(textField_last);
		
		JLabel lblFirstName = new JLabel("First name:");
		lblFirstName.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblFirstName.setBounds(22, 208, 120, 28);
		add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last name:");
		lblLastName.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblLastName.setBounds(334, 212, 120, 28);
		add(lblLastName);
		
		JLabel lblPhone = new JLabel("Phone:");
		lblPhone.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblPhone.setBounds(22, 250, 120, 28);
		add(lblPhone);
		
		textField_phone = new JTextField();
		textField_phone.setEditable(false);
		textField_phone.setColumns(10);
		textField_phone.setBounds(154, 250, 148, 28);
		add(textField_phone);
		
		textField_email = new JTextField();
		textField_email.setEditable(false);
		textField_email.setColumns(10);
		textField_email.setBounds(466, 250, 148, 28);
		add(textField_email);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblEmail.setBounds(334, 250, 120, 28);
		add(lblEmail);
		
		textField_adress = new JTextField();
		textField_adress.setEditable(false);
		textField_adress.setColumns(10);
		textField_adress.setBounds(154, 292, 148, 28);
		add(textField_adress);
		
		JLabel lblAdress = new JLabel("Adress:");
		lblAdress.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblAdress.setBounds(22, 292, 120, 28);
		add(lblAdress);
	}
}
