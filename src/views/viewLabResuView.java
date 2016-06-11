package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import graphics.GUIimage;

import javax.swing.JButton;

import mainPackage.MainClass;

import javax.swing.JTextPane;
import javax.swing.JScrollPane;

import java.awt.Component;
import java.io.InputStream;

import javax.swing.border.BevelBorder;

import Controllers.ViewMedicalHistoryController;
import Controllers.viewLabResuControlller;
import Entities.ViewHistoryEntity;

import com.alee.laf.progressbar.WebProgressBar;

import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JTextArea;

public class viewLabResuView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	public static String photoPath="photo-not-available";
	public JPanel panel1;
	public JPanel panel2;
	public JLabel labelPatName;
	public JLabel lblTesttype;
	public JComboBox comboBoxChooseTest;
	private JLabel lblLblphoto;
	private JButton btnBiggerPhoto;
	public JTextArea txtrTestresult;
	public  String testRes;
	public  String selectedItem="";
	public viewLabResuView() {
		
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
	
		JLabel lblviewlabres = new JLabel("View lab results");
		lblviewlabres.setHorizontalAlignment(SwingConstants.CENTER);
		lblviewlabres.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblviewlabres.setBounds(40, 134, 603, 29);
		add(lblviewlabres);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainClass.masterControler.VMHCont.ViewMedicalHistoryview.taskToDo="secTime";
					MainClass.masterControler.setView(
						MainClass.masterControler.VMHCont.ViewMedicalHistoryview);
					//MainClass.masterControler.setView(
						//masterControler.VMHCont.ViewMedicalHistoryview,MainClass.masterControler.VMHCont);
					///MainClass.masterControler.VMHCont.setVHEnt_Patient();
					
			}
		});
		btnBack.setBounds(479, 455, 151, 55);
		add(btnBack);
		btnBack.setIcon(new GUIimage("back", 25, 23).image);
		
		btnBiggerPhoto = new JButton("Full screen");
		btnBiggerPhoto.setActionCommand("");
		btnBiggerPhoto.setBounds(40, 455, 151, 55);
		btnBiggerPhoto.setIcon(new GUIimage("fullscreen", 25, 23).image);
		add(btnBiggerPhoto);
		btnBiggerPhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				seeBigPhotoView sb = new seeBigPhotoView();
				sb.setVisible(true);
			}
		});
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (tabbedPane.getSelectedComponent()==panel2)
				{
					//btnBiggerPhoto.setEnabled(false);
					btnBiggerPhoto.setVisible(true);
				}
				else btnBiggerPhoto.setVisible(false);
			}
		});
		tabbedPane.setBounds(40, 227, 603, 218);
		add(tabbedPane);
		
		//*****************************************************************************//
		
		panel1= new JPanel();
		panel1.setLayout(null);
		tabbedPane.addTab("result", null, panel1, null);
		
	    txtrTestresult = new JTextArea();
	    txtrTestresult.setEditable(false);
		txtrTestresult.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtrTestresult.setBounds(0, 0, 598, 188);
		panel1.add(txtrTestresult);
		
		
		panel2 = new JPanel();
		panel2.setLayout(null);
		tabbedPane.addTab("photo", null, panel2, null);
	
		lblLblphoto = new JLabel("");
		lblLblphoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblLblphoto.setBounds(0, 0, 598, 188);
		//lblLblphoto.setIcon(new GUIimage("ct - brain", lblLblphoto.getWidth(), lblLblphoto.getHeight()).image);
		lblLblphoto.setIcon(new GUIimage("photo-not-available", lblLblphoto.getWidth(), lblLblphoto.getHeight()).image);
		panel2.add(lblLblphoto);
		
		labelPatName = new JLabel("");
		labelPatName.setHorizontalAlignment(SwingConstants.CENTER);
		labelPatName.setFont(new Font("Tahoma", Font.BOLD, 17));
		labelPatName.setBounds(193, 162, 306, 29);
		add(labelPatName);
		
		comboBoxChooseTest = new JComboBox();
		comboBoxChooseTest.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxChooseTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((selectedItem=(String) comboBoxChooseTest.getSelectedItem())!= "")
				{
					
					lblLblphoto.setIcon(new GUIimage("photo-not-available", lblLblphoto.getWidth(), lblLblphoto.getHeight()).image);
					MainClass.masterControler.VLRCont.viewLabResuview.txtrTestresult.setText("");
					int testResIndex = (comboBoxChooseTest.getSelectedIndex()-1)*4+2 ;
				    testRes = (String) MainClass.masterControler.VLRCont.VLREnt1.arrTest.get(testResIndex);
					MainClass.masterControler.VLRCont.viewLabResuview.txtrTestresult.append(testRes);
					photoPath=(String)MainClass.masterControler.VLRCont.VLREnt1.arrTest.get(testResIndex+1);
					if (photoPath!="photo-not-available")
					{
						btnBiggerPhoto.setEnabled(true);
						lblLblphoto.setIcon(new GUIimage(photoPath, lblLblphoto.getWidth(), lblLblphoto.getHeight()).image);
					}
					else 
						{
							photoPath="photo-not-available";
							lblLblphoto.setIcon(new GUIimage(photoPath, lblLblphoto.getWidth(), lblLblphoto.getHeight()).image);
							btnBiggerPhoto.setEnabled(false);
						}
				}
			}
		});
		comboBoxChooseTest.setBounds(398, 212, 245, 29);
		comboBoxChooseTest.setAlignmentX(CENTER_ALIGNMENT);
		comboBoxChooseTest.setSelectedItem("");
		add(comboBoxChooseTest);
		
		lblTesttype = new JLabel("Choose test:");
		lblTesttype.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTesttype.setBounds(398, 191, 245, 22);
		lblTesttype.setVisible(true);
		add(lblTesttype);
		
		
		
	}
}
