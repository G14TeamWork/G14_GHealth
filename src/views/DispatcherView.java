package views;

import graphics.GUIimage;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

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
	public JLabel lblDisname;
	public DispatcherView() {
		setLayout(null);
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
		
		JLabel lblDisName = new JLabel("Dispatcher");
		lblDisName.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblDisName.setBounds(266, 151, 166, 65);
		add(lblDisName);
		
		lblDisname = new JLabel("");
		lblDisname.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisname.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDisname.setBounds(198, 203, 238, 31);
		add(lblDisname);
		
		
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
				MainClass.masterControler.CACont.CancelAppointmentview  = new CancelAppointmentView();
				MainClass.masterControler.setView(
						MainClass.masterControler.CACont.CancelAppointmentview,
						MainClass.masterControler.CACont);
				
				
			}
		});
		btnCancelAppointment.setText("Cancel appointment");
		btnCancelAppointment.setBounds(469, 372, 161, 55);
		btnCancelAppointment.setIcon(new GUIimage("calendarCancel",26,26).image);
		add(btnCancelAppointment);
		
		btnSetappointment = new JButton();
		btnSetappointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainClass.masterControler.setView(
						MainClass.masterControler.SACont.SetAppointmentview,
						MainClass.masterControler.SACont);
				MainClass.masterControler.SACont.SetAppointmentview.textField_first.setVisible(false);
				MainClass.masterControler.SACont.SetAppointmentview.textField_last.setVisible(false);
				MainClass.masterControler.SACont.SetAppointmentview.textField_phone.setVisible(false);
				MainClass.masterControler.SACont.SetAppointmentview.textField_email.setVisible(false);
				MainClass.masterControler.SACont.SetAppointmentview.textField_adress.setVisible(false);
				MainClass.masterControler.SACont.SetAppointmentview.lblFirstName.setVisible(false);
				MainClass.masterControler.SACont.SetAppointmentview.lblLastName.setVisible(false);
				MainClass.masterControler.SACont.SetAppointmentview.lblPhone.setVisible(false);
				MainClass.masterControler.SACont.SetAppointmentview.lblEmail.setVisible(false);
				MainClass.masterControler.SACont.SetAppointmentview.lblAdress.setVisible(false);
				MainClass.masterControler.SACont.SetAppointmentview.textField_first.setText("");	
				MainClass.masterControler.SACont.SetAppointmentview.textField_last.setText("");
				MainClass.masterControler.SACont.SetAppointmentview.textFieldid.setText("");
				MainClass.masterControler.SACont.SetAppointmentview.textField_phone.setText("");
				MainClass.masterControler.SACont.SetAppointmentview.textField_email.setText("");
				MainClass.masterControler.SACont.SetAppointmentview.textField_adress.setText("");
				MainClass.masterControler.SACont.SetAppointmentview.Jlabel_patientName.setText("");
				MainClass.masterControler.SACont.SetAppointmentview.btnNewPatient.setVisible(false);
				MainClass.masterControler.SACont.SetAppointmentview.btnsearch.setVisible(true);
				MainClass.masterControler.SACont.SetAppointmentview.lblDoctors.setVisible(false);
				MainClass.masterControler.SACont.SetAppointmentview.lblExpertType.setVisible(false);
				MainClass.masterControler.SACont.SetAppointmentview.comboBox_expertise.setVisible(false);
				MainClass.masterControler.SACont.SetAppointmentview.comboBox_doctors.setVisible(false);
				MainClass.masterControler.SACont.SetAppointmentview.lblAvailableAppointmentsDates.setVisible(false);
				MainClass.masterControler.SACont.SetAppointmentview.lblAvailableAppointmentsHours.setVisible(false);
				MainClass.masterControler.SACont.SetAppointmentview.comboBox_AvailableAppointmentsDates.setVisible(false);
				MainClass.masterControler.SACont.SetAppointmentview.comboBox_AvailableAppointmentsHours.setVisible(false);
				MainClass.masterControler.SACont.SetAppointmentview.btnSetAppointment.setVisible(false);
			}
		});
		btnSetappointment.setText("Set appointment");
		btnSetappointment.setBounds(469, 304, 161, 55);
		btnSetappointment.setIcon(new GUIimage("calendarAdd",26,26).image);
		add(btnSetappointment);
	}
}
