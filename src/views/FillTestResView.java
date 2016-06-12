package views;

import graphics.GUIimage;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JFileChooser;

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

import javax.swing.JComboBox;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.io.File;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

public class FillTestResView extends JPanel {
	private static final long serialVersionUID = 1L;
	public JSeparator separator;
	public JTextField textFieldid;
	public JTextArea  textField_TestResult;
	public JButton 	  btnSave;
	public JButton	  btnAddPhoto;
	public String file_path="photo-not-available";
	//public File file;
	public JLabel textField_first;
	public JComboBox comboBox_test;
	public JLabel lblTesttype;
	//public boolean flagg;
	public FillTestResView() {
		setLayout(null);
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
		file_path="photo-not-available";
		JLabel lblExpert = new JLabel("Filling Test Result");
		lblExpert.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblExpert.setBounds(247, 134, 222, 47);
		add(lblExpert);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lblTesttype.isShowing())
				{
					if(JOptionPane.showConfirmDialog(null, "Are you sure?",null,JOptionPane.YES_NO_OPTION)==0) //if pressed yes
					{
						file_path="photo-not-available";
						MainClass.masterControler.setView(
								MainClass.masterControler.LWCont.labworkerview);
					}
				}
				else	MainClass.masterControler.setView(
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
		textFieldid.setBounds(149, 194, 148, 28);
		add(textFieldid);
		
		JLabel lblEnterPatientId = new JLabel("Enter patient ID:");
		lblEnterPatientId.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblEnterPatientId.setBounds(32, 193, 120, 28);

		add(lblEnterPatientId);
		
		JButton searchIcon = new JButton("");
		searchIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnAddPhoto.setIcon(new GUIimage("xSign", 25, 23).image);
				MainClass.masterControler.FTRCont.setFTR_Patient();
			}
		});
		searchIcon.setBounds(309, 194, 29, 28);
		searchIcon.setIcon(new GUIimage("search",searchIcon.getWidth()-7,searchIcon.getHeight()-7).image);
		this.add(searchIcon);
		
		JLabel lblTestResult = new JLabel("Test result:");
		lblTestResult.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblTestResult.setBounds(32, 276, 120, 28);
		add(lblTestResult);
		
		btnAddPhoto = new JButton("<html>Add photo<br />(Optional)</html>");
		btnAddPhoto.setIcon(new GUIimage("xSign", 25, 23).image);
		btnAddPhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser filebutton = new JFileChooser();
				if (filebutton.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
				{
					btnAddPhoto.setIcon(new GUIimage("vSign", 25, 23).image);
					file_path=filebutton.getSelectedFile().getName();
					//file = filebutton.getSelectedFile().getAbsoluteFile();
				}
				else btnAddPhoto.setIcon(new GUIimage("xSign", 25, 23).image);
			}
		});
		btnAddPhoto.setBounds(490, 305, 140, 55);
		add(btnAddPhoto);
		
		textField_TestResult = new JTextArea();
		textField_TestResult.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		textField_TestResult.setEditable(false);
		textField_TestResult.setLineWrap(true);
		textField_TestResult.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_TestResult.setBounds(32, 312, 437, 183);
		add(textField_TestResult);
		
		btnSave = new JButton("  Save   ");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if (textField_TestResult.getText().isEmpty()) 
					JOptionPane.showMessageDialog(null, "You cannot enter empty test result!","",JOptionPane.ERROR_MESSAGE);//.showConfirmDialog(null, "ytry",null,JOptionPane.ERROR_MESSAGE);
				else if (comboBox_test.getSelectedItem()=="") 
					JOptionPane.showMessageDialog(null, "Choose test type!","",JOptionPane.ERROR_MESSAGE);	
				else if(JOptionPane.showConfirmDialog(null, "Did you finish your report?",null,JOptionPane.YES_NO_OPTION)==0) //if pressed yes
						{
							btnSave.setEnabled(false);
							btnAddPhoto.setEnabled(false);
							MainClass.masterControler.setView(MainClass.masterControler.LWCont.labworkerview);
							MainClass.masterControler.FTRCont.insertTestRes();
							MainClass.masterControler.FTRCont.FTRpat1.taskToDo="";
						}
			}
		});
		btnSave.setIcon(new GUIimage("save", 25, 23).image);
		btnSave.setBounds(490, 374, 140, 53);
		add(btnSave);
		
		textField_first = new JLabel("");
		
		



		textField_first.setFont(new Font("Tahoma", Font.BOLD, 17));
		textField_first.setBounds(32, 235, 306, 29);
		add(textField_first);
		
		comboBox_test = new JComboBox();
	//	comboBox_test.addActionListener(new ActionListener() {
	//		public void actionPerformed(ActionEvent arg0) {
	//			MainClass.masterControler.FTRCont.FTRpat1.testIndex=comboBox_test.getSelectedIndex();
	//		}
	//	});
		comboBox_test.setEditable(true); 
		comboBox_test.setBounds(490, 270, 140, 22);
		comboBox_test.setAlignmentX(CENTER_ALIGNMENT);
		comboBox_test.setSelectedItem("");
		//לשמור אינדקס קומבו שנבחר
		comboBox_test.setVisible(false);
		add(comboBox_test);
		
		lblTesttype = new JLabel("Choose test type:");
		lblTesttype.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				MainClass.masterControler.FTRCont.FTRpat1.taskToDo="searchRef";
				MainClass.masterControler.FTRCont.findRef();
			}
		});
		lblTesttype.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTesttype.setBounds(490, 235, 140, 28);
		lblTesttype.setVisible(false);
		add(lblTesttype);
	}
}
