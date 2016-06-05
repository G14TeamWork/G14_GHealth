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

public class DispatcherView extends JPanel {
	private static final long serialVersionUID = 1L;
	public JButton btnLogout;
	public JSeparator separator;
	public JButton btnSetappointment;
	public JButton btnCancelAppointment;
	public DispatcherView() {
		setLayout(null);
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
		
		JLabel lblExpert = new JLabel("Dispatcher");
		lblExpert.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblExpert.setBounds(211, 182, 253, 118);
		add(lblExpert);
		
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
		
		btnCancelAppointment = new JButton();
		btnCancelAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainClass.masterControler.setView(
						MainClass.masterControler.CACont.CancelAppointmentview,
						MainClass.masterControler.CACont);
			}
		});
		btnCancelAppointment.setText("Cancel appointment");
		btnCancelAppointment.setBounds(469, 372, 161, 55);
		add(btnCancelAppointment);
		
		btnSetappointment = new JButton();
		btnSetappointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainClass.masterControler.setView(
						MainClass.masterControler.SACont.SetAppointmentview,
						MainClass.masterControler.SACont);
				MainClass.masterControler.SACont.SetAppointmentview.textField_first.setText("");	
				MainClass.masterControler.SACont.SetAppointmentview.textField_last.setText("");
				MainClass.masterControler.SACont.SetAppointmentview.textFieldid.setText("");
			}
		});
		btnSetappointment.setText("Set appointment");
		btnSetappointment.setBounds(469, 304, 161, 55);
		add(btnSetappointment);
	}
}
