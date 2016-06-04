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
import java.awt.Button;

public class FillTestResView extends JPanel {
	private static final long serialVersionUID = 1L;
	public JSeparator separator;
	private JTextField txtPatientid;
	public FillTestResView() {
		setLayout(null);
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
		
		JLabel lblExpert = new JLabel("Filling Test Result");
		lblExpert.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblExpert.setBounds(249, 151, 222, 47);
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
		
		JLabel lblEnterPatientId = new JLabel("Enter patient ID:");
		lblEnterPatientId.setBounds(239, 291, 104, 16);
		add(lblEnterPatientId);
		
		txtPatientid = new JTextField();
		txtPatientid.setText("Patient ID");
		txtPatientid.setBounds(355, 288, 116, 22);
		add(txtPatientid);
		txtPatientid.setColumns(10);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnOk.setBounds(294, 338, 121, 47);
		add(btnOk);

	}
}
