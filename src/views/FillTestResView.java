package views;

import graphics.GUIimage;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSeparator;

import mainPackage.MainClass;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

import javax.swing.JTextField;

import java.awt.Button;

import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FillTestResView extends JPanel {
	private static final long serialVersionUID = 1L;
	public JSeparator separator;
	public JTextField textField_first;
	public JTextField textFieldid;
	public JTextField textField_last;
	public JTextArea  textField_TestResult;
	public JButton 	  btnSave;
	public JButton	  btnAddPhoto;
	//public boolean flagg;
	public FillTestResView() {
		setLayout(null);
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
		
		JLabel lblExpert = new JLabel("Filling Test Result");
		lblExpert.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblExpert.setBounds(247, 134, 222, 47);
		add(lblExpert);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainClass.masterControler.setView(
						MainClass.masterControler.LWCont.labworkerview);
			}
		});
		btnBack.setBounds(490, 440, 140, 55);
		add(btnBack);
		btnBack.setIcon(new GUIimage("back", 25, 23).image);
		
		textFieldid = new JTextField();
		textFieldid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) { // ENTER pressed
				if (arg0.getKeyChar()==10)
					MainClass.masterControler.FTRCont.setFTR_Patient();
				//btnSave.setEnabled(false);
				//btnAddPhoto.setEnabled(false);
					
			}
		});
		//textFieldid.setText("");
		textFieldid.setColumns(10);
		textFieldid.setBounds(128, 194, 148, 28);
		add(textFieldid);
		
		JLabel lblEnterPatientId = new JLabel("Enter patient ID:");
		lblEnterPatientId.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblEnterPatientId.setBounds(12, 193, 120, 28);

		add(lblEnterPatientId);
		
		JButton searchIcon = new JButton("");
		searchIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MainClass.masterControler.FTRCont.setFTR_Patient();
				//btnSave.setEnabled(false);
				//btnAddPhoto.setEnabled(false);
			}
		});
		searchIcon.setBounds(288, 194, 29, 28);
		searchIcon.setIcon(new GUIimage("search",searchIcon.getWidth()-7,searchIcon.getHeight()-7).image);
		this.add(searchIcon);
		
		textField_first = new JTextField();
		textField_first.setEditable(false);
		//textField_first.setText("");
		textField_first.setColumns(10);
		textField_first.setBounds(128, 236, 148, 28);
		add(textField_first);
		
		textField_last = new JTextField();
		textField_last.setEditable(false);
		//textField_last.setText("");
		textField_last.setColumns(10);
		textField_last.setBounds(390, 236, 148, 28);
		add(textField_last);
		
		JLabel lblFirstName = new JLabel("First name:");
		lblFirstName.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblFirstName.setBounds(12, 235, 120, 28);
		add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last name:");
		lblLastName.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblLastName.setBounds(302, 235, 120, 28);
		add(lblLastName);
		
		JLabel lblTestResult = new JLabel("Test result:");
		lblTestResult.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblTestResult.setBounds(32, 276, 120, 28);
		add(lblTestResult);
		
		btnAddPhoto = new JButton("<html>Add photo<br />(Optional)</html>");
		btnAddPhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnAddPhoto.setBounds(490, 305, 140, 55);
		add(btnAddPhoto);
		
		textField_TestResult = new JTextArea();
		textField_TestResult.setEditable(false);
		textField_TestResult.setLineWrap(true);
		textField_TestResult.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_TestResult.setBounds(32, 312, 390, 183);
		add(textField_TestResult);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(JOptionPane.showConfirmDialog(null, "Save data?",null,JOptionPane.YES_NO_OPTION)==0) //if pressed yes
					{
						System.out.println("yessss");
						btnSave.setEnabled(false);
						btnAddPhoto.setEnabled(false);
						MainClass.masterControler.setView(MainClass.masterControler.LWCont.labworkerview);
					}
			}
		});
		btnSave.setBounds(490, 374, 140, 53);
		add(btnSave);
		//btnSave.setEnabled(false);
		//btnAddPhoto.setEnabled(false);
		

	}
}
