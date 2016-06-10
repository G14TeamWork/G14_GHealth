package views;

import views.*;
import graphics.GUIimage;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSeparator;

import mainPackage.MainClass;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.JTextPane;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.JCheckBox;

public class RecordAppointView extends JPanel {
	private static final long serialVersionUID = 1L;
	public JSeparator separator;
	public JTextArea record;
	public String appNo;
	public JLabel idPatientLabel ;
	public JLabel appNoLabel;
	public JLabel startHourLabel;
	public JCheckBox notappear;
	//public RecordAppointView(){};
	public RecordAppointView() {
		//appNo = JOptionPane.showInputDialog(null,"Enter appointment number : ");
		setLayout(null);
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
		
		notappear = new JCheckBox("Patient didn't appear");
		notappear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(notappear.isSelected())
					record.setEditable(false);
				else record.setEditable(true);
			}
		});
		notappear.setBounds(229, 230, 140, 24);
		add(notappear);
		
		record = new JTextArea();
		record.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		record.setForeground(Color.BLACK);
		record.setLineWrap(true);
		record.setBounds(28, 273, 435, 206);
		add(record);
		
		JLabel lblExpert = new JLabel("Record Appointment");
		lblExpert.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblExpert.setBounds(192, 136, 243, 89);
		add(lblExpert);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setForeground(Color.BLACK);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Cancel record?",null,JOptionPane.YES_NO_OPTION)==0){
					MainClass.masterControler.setView(MainClass.masterControler.EXPVCont.expview);
					record.setText("");
					notappear.setSelected(false);
					record.setEditable(true);
				}
			}
		});
		btnCancel.setBounds(493, 424, 140, 55);
		add(btnCancel);
		btnCancel.setIcon(null);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Save Record?",null,JOptionPane.YES_NO_OPTION)==0){
					MainClass.masterControler.EXPVCont.RAE1.taskToDo="update";
					if (notappear.isSelected()){
						MainClass.masterControler.EXPVCont.RAE1.appointment.setAppstatus("3");
						MainClass.masterControler.EXPVCont.RAE1.appointment.setRecord("Patient didnt appear!");
						MainClass.masterControler.EXPVCont.RAE1.appointment.setStartS("00:00:00");
						MainClass.masterControler.EXPVCont.RAE1.appointment.setEndS("00:00:00");
					}else{
						MainClass.masterControler.EXPVCont.RAE1.appointment.setAppstatus("2");
						MainClass.masterControler.EXPVCont.RAE1.appointment.setRecord(record.getText());
						MainClass.masterControler.EXPVCont.RAE1.appointment.setEndS(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
					}
					MainClass.masterControler.RACont.saveRecord(MainClass.masterControler.EXPVCont.RAE1);
					MainClass.masterControler.setView(MainClass.masterControler.EXPVCont.expview);//back to exp window
					record.setText("");
					notappear.setSelected(false);
					record.setEditable(true);
				}
			}
		});
		btnSave.setBounds(493, 348, 132, 55);
		add(btnSave);
			
		JLabel lblAppointmentRecord = new JLabel("Appointment Record :");
		lblAppointmentRecord.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAppointmentRecord.setBounds(28, 225, 173, 29);
		add(lblAppointmentRecord);
		
		appNoLabel = new JLabel("");
		appNoLabel.setBounds(493, 212, 132, 22);
		add(appNoLabel);
		
		idPatientLabel = new JLabel("");
		idPatientLabel.setBounds(493, 268, 132, 22);
		add(idPatientLabel);
		
		startHourLabel = new JLabel("");
		startHourLabel.setBounds(493, 301, 132, 22);
		add(startHourLabel);
			
	}
	public JSeparator getSeparator() {
		return separator;
	}
	public void setSeparator(JSeparator separator) {
		this.separator = separator;
	}
	public JTextArea getRecord() {
		return record;
	}
	public void setRecord(JTextArea record) {
		this.record = record;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
}