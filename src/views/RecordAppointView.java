package views;

import views.*;
import graphics.GUIimage;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
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
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import com.alee.laf.text.WebEditorPane;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.ComponentOrientation;

public class RecordAppointView extends JPanel {

	private static final long serialVersionUID = 1L;
	public JSeparator separator;
	public WebEditorPane record;
	public String appNo;
	public JLabel idPatientLabel ;
	public JLabel appNoLabel;
	public JLabel startHourLabel;
	public JCheckBox notappear;
	public JButton btnSave;
	public JComboBox rtypes;
	public JButton btnproduce;
	public JButton btnProduceLabReference ;
	public boolean newRefs = false;
	
	public RecordAppointView() {
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
					rtypes.setVisible(false);
					btnproduce.setVisible(false);
					btnProduceLabReference.setVisible(false);
					record.setEditable(false);
				}else{
					record.setEditable(true);
					//rtypes.setVisible(true);
					//btnproduce.setVisible(true);
					btnProduceLabReference.setVisible(true);
				}
			}
		});
		notappear.setBounds(43, 229, 140, 24);
		add(notappear);
		
		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(25, 270, 435, 213);
		add(scrollPane);
		
		record = new WebEditorPane("","");
		record.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		record.setText("");
		record.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		scrollPane.setViewportView(record);
		record.setEditable(true);
		record.setFont(new Font("Dialog", Font.PLAIN, 14));
		
		
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
					rtypes.setEditable(false); 
					rtypes.setVisible(false);
					rtypes.setSelectedItem("");
					btnproduce.setVisible(false);
					btnproduce.setEnabled(false);
					//TODO if new refs, delete refs
					if (newRefs){
						MainClass.masterControler.RACont.removeReferences();
						newRefs = false;
					}
				}
			}
		});
		btnCancel.setBounds(478, 424, 155, 55);
		btnCancel.setIcon(new GUIimage("xSign", 25, 23).image);
		add(btnCancel);
		
		btnSave = new JButton("Save   ");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Save Record?",null,JOptionPane.YES_NO_OPTION)==0){
					if (notappear.isSelected()){
						MainClass.masterControler.EXPVCont.RAE1.appointment.setAppstatus("3");
						MainClass.masterControler.EXPVCont.RAE1.appointment.setRecord("Patient didnt appear!");
						MainClass.masterControler.EXPVCont.RAE1.appointment.setStartS("00:00:00");
						MainClass.masterControler.EXPVCont.RAE1.appointment.setEndS("00:00:00");
						MainClass.masterControler.RACont.removeReferences();
						//System.out.println(MainClass.masterControler.EXPVCont.RAE1.appointment.getRecord());
					}else{
						MainClass.masterControler.EXPVCont.RAE1.appointment.setAppstatus("2");
						MainClass.masterControler.EXPVCont.RAE1.appointment.setRecord(record.getText());
						MainClass.masterControler.EXPVCont.RAE1.appointment.setEndS(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
					}
					MainClass.masterControler.EXPVCont.RAE1.appointment.setRecord("Appointment ID No. : " + MainClass.masterControler.EXPVCont.RAE1.appID +
							"\nAppointment Date : " + new SimpleDateFormat("dd/MM/yyyy   HH:mm").format(Calendar.getInstance().getTime()) + "\n"+
							MainClass.masterControler.EXPVCont.RAE1.appointment.getRecord());
					MainClass.masterControler.EXPVCont.RAE1.taskToDo="update";
					MainClass.masterControler.RACont.saveRecord(MainClass.masterControler.EXPVCont.RAE1);
					MainClass.masterControler.setView(MainClass.masterControler.EXPVCont.expview);//back to exp window
					record.setText("");

					notappear.setSelected(false);
					record.setEditable(true);
					btnproduce.setVisible(false);
					btnproduce.setEnabled(false);
					newRefs = false;
					rtypes.setVisible(false);
					rtypes.setSelectedItem("");
					
				}
			}
		});
		btnSave.setBounds(478, 358, 155, 55);
		btnSave.setIconTextGap(10);
		btnSave.setIcon(new GUIimage("save", 25, 23).image);
		add(btnSave);
			
		JLabel lblAppointmentRecord = new JLabel("Appointment Record :");
		lblAppointmentRecord.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAppointmentRecord.setBounds(43, 196, 173, 29);
		add(lblAppointmentRecord);
		
		appNoLabel = new JLabel("");
		appNoLabel.setBounds(243, 196, 155, 22);
		add(appNoLabel);
		
		idPatientLabel = new JLabel("");
		idPatientLabel.setBounds(243, 218, 155, 22);
		add(idPatientLabel);
		
		startHourLabel = new JLabel("");
		startHourLabel.setBounds(243, 240, 155, 22);
		add(startHourLabel);
		
		btnProduceLabReference = new JButton("<html><center>Produce Lab<br />Reference</html>");
		btnProduceLabReference.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rtypes.setVisible(true);
				btnproduce.setVisible(true);
			}
		});
		btnProduceLabReference.setBounds(478, 169, 155, 59);
		btnProduceLabReference.setIconTextGap(10);
		btnProduceLabReference.setIcon(new GUIimage("labref", 29, 27).image);
		add(btnProduceLabReference);
		
		rtypes = new JComboBox();
		rtypes.setMaximumRowCount(6);
		rtypes.setEditable(false); 
		rtypes.setVisible(false);
		rtypes.setBounds(490, 270, 140, 22);
		rtypes.setAlignmentX(CENTER_ALIGNMENT);
		rtypes.addItem("");
		rtypes.addItem("Blood");
		rtypes.addItem("rentgen - hand");
		rtypes.addItem("rentgen - chest");
		rtypes.addItem("rentgen - basin");
		rtypes.addItem("ct - neck");
		rtypes.addItem("ct - brain");
		rtypes.addItem("mri - brain");
		rtypes.addItem("mri - knee");
		rtypes.addItem("mri - shoulder");
		rtypes.setBounds(478, 241, 155, 35);
		rtypes.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if((e.getStateChange()==ItemEvent.SELECTED)&&(!(((String)rtypes.getSelectedItem()).equals("")))){
					btnproduce.setEnabled(true);
					btnSave.setEnabled(false);
				//	MainClass.masterControler.RDCont.manageComboBox();//TODO
				}else{
					btnproduce.setEnabled(false);
					btnSave.setEnabled(true);
				}	
			}
		});
		add(rtypes);
		
		btnproduce = new JButton(" Produce ");
		btnproduce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainClass.masterControler.RACont.handleProduceBtn((String)rtypes.getSelectedItem());
				btnproduce.setEnabled(false);
				rtypes.setSelectedItem("");
				newRefs = true;
			}
		});
		btnproduce.setBounds(478, 296, 155, 49);
		btnproduce.setEnabled(false);
		btnproduce.setVisible(false);
		btnproduce.setIcon(new GUIimage("pen", 25, 23).image);
		add(btnproduce);
			
	}
	public JSeparator getSeparator() {
		return separator;
	}
	public void setSeparator(JSeparator separator) {
		this.separator = separator;
	}
	public WebEditorPane getRecord() {
		return record;
	}
	public void setRecord(WebEditorPane record) {
		this.record = record;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
}