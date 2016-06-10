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
import javax.swing.JComboBox;

public class RecordAppointView extends JPanel {
	private static final long serialVersionUID = 1L;
	public JSeparator separator;
	public JTextArea record;
	public String appNo;
	public JLabel idPatientLabel ;
	public JLabel appNoLabel;
	public JLabel startHourLabel;
	public JCheckBox notappear;
	public JComboBox rtypes;
	public JButton btnproduce;
	public JButton btnProduceLabReference ;
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
				if(notappear.isSelected()){
					
					record.setEditable(false);
				}
				else record.setEditable(true);
			}
		});
		notappear.setBounds(33, 229, 140, 24);
		add(notappear);
		
		record = new JTextArea();
		record.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		record.setForeground(Color.BLACK);
		record.setLineWrap(true);
		record.setBounds(33, 273, 435, 206);
		add(record);
		
		JLabel lblExpert = new JLabel("Record Appointment");
		lblExpert.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblExpert.setBounds(160, 126, 243, 59);
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
		btnCancel.setBounds(478, 424, 155, 55);
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
		btnSave.setBounds(478, 358, 155, 55);
		add(btnSave);
			
		JLabel lblAppointmentRecord = new JLabel("Appointment Record :");
		lblAppointmentRecord.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAppointmentRecord.setBounds(33, 193, 173, 29);
		add(lblAppointmentRecord);
		
		appNoLabel = new JLabel("ap");
		appNoLabel.setBounds(243, 196, 155, 22);
		add(appNoLabel);
		
		idPatientLabel = new JLabel("pt");
		idPatientLabel.setBounds(243, 218, 155, 22);
		add(idPatientLabel);
		
		startHourLabel = new JLabel("hr");
		startHourLabel.setBounds(243, 240, 155, 22);
		add(startHourLabel);
		
		btnProduceLabReference = new JButton("Produce Lab Reference");
		btnProduceLabReference.setBounds(478, 169, 155, 59);
		add(btnProduceLabReference);
		
		rtypes = new JComboBox();
		
		rtypes.setBounds(478, 241, 155, 35);
		add(rtypes);
		
		btnproduce = new JButton("Produce");
		btnproduce.setBounds(478, 296, 155, 40);
		add(btnproduce);
			
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