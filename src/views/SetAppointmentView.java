package views;

import graphics.GUIimage;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSeparator;

import mainPackage.MainClass;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;

import javax.swing.JTextField;
import ocsf.server.GHealthServer;
import Controllers.MasterController;

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
	public JLabel lblFirstName;
	public JLabel lblLastName;
	public JLabel lblPhone;
	public JLabel lblEmail;
	public JLabel lblAdress;
	public JButton btnNewPatient;
	public JButton btnsearch;
	public JLabel Jlabel_first;
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
		
		textFieldid = new JTextField(){
			  /**
			 * limit to only numbers
			 */
			private static final long serialVersionUID = 1L;
			public void processKeyEvent(KeyEvent ev) {
				    char c = ev.getKeyChar();
				    int d = ev.getKeyCode();
				    if ((c >= 48 && c <= 57) || c==127 || c==8 || c==224  || c==39 || d==37 || d==39 || ev.getKeyChar()==10) { // c = '0' ... c = '9'
				      super.processKeyEvent(ev);
				    }
				  }
				};
		textFieldid.setColumns(10);
		textFieldid.setBounds(154, 171, 148, 28);
		textFieldid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) { // ENTER pressed
				if (arg0.getKeyChar()==10)
				{
					MainClass.masterControler.SACont.setPatient();
				}
				}
			});
		
		add(textFieldid);
		
		JLabel lblEnterPatientId = new JLabel("Enter patient ID:");
		lblEnterPatientId.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblEnterPatientId.setBounds(22, 170, 120, 28);
		add(lblEnterPatientId);
		
		
		btnsearch = new JButton("");
		btnsearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MainClass.masterControler.SACont.setPatient();
			}
		});
		
		btnsearch.setBounds(314, 171, 29, 28);
		btnsearch.setIcon(new GUIimage("search",btnsearch.getWidth()-7,btnsearch.getHeight()-7).image);
		this.add(btnsearch);
		
		textField_first = new JTextField();
		textField_first.setColumns(10);
		textField_first.setBounds(154, 209, 148, 28);
		textField_first.setVisible(false);
		add(textField_first);
		
		textField_last = new JTextField();
		textField_last.setColumns(10);
		textField_last.setBounds(466, 209, 148, 28);
		textField_last.setVisible(false);
		add(textField_last);
		
		Jlabel_first = new JLabel("");
		Jlabel_first.setFont(new Font("Tahoma", Font.BOLD, 17));
		Jlabel_first.setBounds(32, 208, 306, 29);
		add(Jlabel_first);
		
		lblFirstName = new JLabel("First name:");
		lblFirstName.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblFirstName.setBounds(22, 208, 120, 28);
		lblFirstName.setVisible(false);
		add(lblFirstName);
		
		lblLastName = new JLabel("Last name:");
		lblLastName.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblLastName.setBounds(334, 212, 120, 28);
		lblLastName.setVisible(false);
		add(lblLastName);
		
		lblPhone = new JLabel("Phone:");
		lblPhone.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblPhone.setBounds(22, 250, 120, 28);
		lblPhone.setVisible(false);
		add(lblPhone);
		
		textField_phone = new JTextField(){
			  /**
			 * limit to only numbers
			 */
			private static final long serialVersionUID = 1L;
			public void processKeyEvent(KeyEvent ev) {
				    char c = ev.getKeyChar();
				    int d = ev.getKeyCode();
				    if ((c >= 48 && c <= 57) || c==127 || c==8 || c==224  || c==39 || d==37 || d==39) { // c = '0' ... c = '9'
				      super.processKeyEvent(ev);
				    }
				  }
				};
		textField_phone.setColumns(10);
		textField_phone.setBounds(154, 250, 148, 28);
		textField_phone.setVisible(false);
/*		textField_phone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) { // ENTER pressed
					if( arg0.getKeyChar()<48 || arg0.getKeyChar()>57 )
					{
						if( arg0.getKeyChar()!=127 && arg0.getKeyChar()!=8)
						{
							JOptionPane.showMessageDialog(null,"Enter only numbers in Phone field");
							if(textFieldid.getText().length()>0)
								textField_phone.setText(textField_phone.getText().substring(0, textField_phone.getText().length()-1));
						}
					}
				}
			});*/
		add(textField_phone);
		
		textField_email = new JTextField();
		textField_email.setColumns(10);
		textField_email.setBounds(466, 250, 148, 28);
		textField_email.setVisible(false);
		add(textField_email);
		
		lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblEmail.setBounds(334, 250, 120, 28);
		lblEmail.setVisible(false);
		add(lblEmail);
		
		textField_adress = new JTextField();
		textField_adress.setColumns(10);
		textField_adress.setBounds(154, 292, 148, 28);
		textField_adress.setVisible(false);
		add(textField_adress);
		
		lblAdress = new JLabel("Adress:");
		lblAdress.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblAdress.setBounds(22, 292, 120, 28);
		lblAdress.setVisible(false);
		add(lblAdress);
		
		btnNewPatient = new JButton("Enter new Patient");
		btnNewPatient.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MainClass.masterControler.SACont.setNewPatient();
			}
		});
		btnNewPatient.setBounds(490, 366, 140, 55);
		btnNewPatient.setVisible(false);
		add(btnNewPatient);
		
	}
}
