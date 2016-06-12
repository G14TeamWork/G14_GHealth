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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

public class ClinicManagerView extends JPanel {
	private static final long serialVersionUID = 1L;
	public JButton btnLogout;
	public JSeparator separator;
	public JLabel lblClinicManagername;
	private JTextField year;
	private JTextField month;
	private JTextField textField_2;
	public ClinicManagerView() {
		setLayout(null);
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
		
		JLabel lblClinicManager = new JLabel("Clinic Manager");
		lblClinicManager.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblClinicManager.setBounds(243, 151, 166, 65);
		add(lblClinicManager);
		
		lblClinicManagername = new JLabel("");
		lblClinicManagername.setHorizontalAlignment(SwingConstants.CENTER);
		lblClinicManagername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblClinicManagername.setBounds(194, 203, 238, 31);
		add(lblClinicManagername);
		
		
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
		
		year = new JTextField();
		year.setText("0000");
		year.setBounds(124, 255, 31, 20);
		add(year);
		year.setColumns(10);
		
		month = new JTextField();
		month.setText("00");
		month.setBounds(158, 255, 20, 20);
		add(month);
		month.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setEnabled(false);
		textField_2.setBounds(182, 255, 20, 20);
		add(textField_2);
		textField_2.setColumns(10);
		
		JLabel searchIcon = new JLabel("");
		searchIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MainClass.masterControler.CMCont.searchClinicIdClient();
				System.out.println("fjsldfja;ldka");
			}
			});

		searchIcon.setBounds(292, 247, 29, 28);
		searchIcon.setIcon(new GUIimage("search",searchIcon.getWidth(),searchIcon.getHeight()).image);
		add(searchIcon);
		
		JCheckBox chckbxWeekly = new JCheckBox("Weekly");
		chckbxWeekly.setBounds(212, 254, 61, 23);
		add(chckbxWeekly);
	}
}
