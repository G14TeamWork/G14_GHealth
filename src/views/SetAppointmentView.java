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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;

import javax.swing.JTextField;

import Entities.Appointment;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JComboBox;

import com.alee.extended.date.DateSelectionListener;
import com.alee.extended.date.WebDateField;
import com.alee.laf.list.WebList;
import com.alee.laf.scroll.WebScrollPane;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
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
	public JButton btnSetAppointment;
	public JLabel Jlabel_patientName;
	public JComboBox comboBox_expertise;
	public JComboBox comboBox_doctors;
	public JLabel lblExpertType;
	public JLabel lblDoctors;
	public ArrayList<Integer> AppointmentIDList;
	public final WebDateField datePickerfrom;
	public WebList editableList;
	public WebScrollPane WebScrollPane1;
	public boolean Flag=true;
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
				if (arg0.getKeyChar()==10 && textFieldid.getText().length()>=1 )
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
		btnsearch.setToolTipText("Press for search patient");
		btnsearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(textFieldid.getText().length()>=1)
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
		
		Jlabel_patientName = new JLabel("");
		Jlabel_patientName.setFont(new Font("Dialog", Font.BOLD, 16));
		Jlabel_patientName.setBounds(32, 208, 306, 29);
		add(Jlabel_patientName);
		
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
		textField_adress.setBounds(154, 291, 148, 28);
		textField_adress.setVisible(false);
		add(textField_adress);
		
		lblAdress = new JLabel("Adress:");
		lblAdress.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblAdress.setBounds(22, 292, 120, 28);
		lblAdress.setVisible(false);
		add(lblAdress);
		
		btnNewPatient = new JButton("Add Patient");
		btnNewPatient.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MainClass.masterControler.SACont.setNewPatient();
			}
		});
		btnNewPatient.setBounds(490, 366, 140, 55);
		btnNewPatient.setVisible(false);
		btnNewPatient.setIcon(new GUIimage("Adduser",25,25).image);
		add(btnNewPatient);
		
		comboBox_expertise = new JComboBox();
		comboBox_expertise.setFont(new Font("Dialog", Font.PLAIN, 14));
		comboBox_expertise.setEditable(true); 
		comboBox_expertise.setAlignmentX(CENTER_ALIGNMENT);
		comboBox_expertise.setBounds(194, 250, 260, 28);
		comboBox_expertise.setSelectedItem("");
		comboBox_expertise.addItem("Cardiologist");
		comboBox_expertise.addItem("Gynecologist");
		comboBox_expertise.addItem("Neurologist");
		comboBox_expertise.addItem("Oncologist");
		comboBox_expertise.addItem("Orthopedic Surgeon");
		comboBox_expertise.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				MainClass.masterControler.SACont.searchExperts((String)comboBox_expertise.getSelectedItem());
			}
		});
		comboBox_expertise.setVisible(false);
		add(comboBox_expertise);
		
		comboBox_doctors = new JComboBox();
		comboBox_doctors.setFont(new Font("Dialog", Font.PLAIN, 14));
		comboBox_doctors.setEditable(true); 
		comboBox_doctors.setAlignmentX(CENTER_ALIGNMENT);
		comboBox_doctors.setBounds(194, 291, 260, 28);
		comboBox_expertise.setSelectedItem("");
		comboBox_doctors.setVisible(false);
		add(comboBox_doctors);
		
		lblExpertType = new JLabel("Choose expert type:");
		lblExpertType.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblExpertType.setBounds(22, 250, 148, 28);
		lblExpertType.setVisible(false);
		add(lblExpertType);
		
		lblDoctors = new JLabel("Choose specific expert:");
		lblDoctors.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblDoctors.setBounds(22, 290, 172, 28);
		lblDoctors.setVisible(false);
		add(lblDoctors);
		
		btnSetAppointment = new JButton("Set Appointment");
		btnSetAppointment.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			//	MainClass.masterControler.SACont.setNewPatient();
			}
		});
		btnSetAppointment.setBounds(490, 366, 140, 55);
		btnSetAppointment.setVisible(false);
		btnSetAppointment.setIcon(new GUIimage("calendarAdd",25,25).image);
		add(btnSetAppointment);
		
		datePickerfrom = new WebDateField();
		datePickerfrom.setBounds(336, 332, 118, 28);
		String timeStamp = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
		datePickerfrom.setInputPrompt(timeStamp);
	    datePickerfrom.setInputPromptPosition ( SwingConstants.CENTER );
/*	    datePickerfrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Flag=true;
			}
		});*/
	    datePickerfrom.addDateSelectionListener(new DateSelectionListener(){
			@Override
			public void dateSelected(Date date) {
				if (Flag&&!comboBox_expertise.getSelectedItem().equals("")&& datePickerfrom.getDate()!=null) {
					String timeStamp1 = new SimpleDateFormat("yyyyMMdd").format(datePickerfrom.getDate());
					MainClass.masterControler.SACont.searchAvailableAppointment(MainClass.masterControler.SACont.expIDlist.get(comboBox_expertise.getSelectedIndex()),timeStamp1);
					Flag=false;
				}}});
/*	    datePickerfrom.getDocument().addDocumentListener(new DocumentListener(){

			public void changedUpdate(DocumentEvent arg0) {
				tot();
			}
			public void insertUpdate(DocumentEvent arg0) {
				tot();
			}
			public void removeUpdate(DocumentEvent arg0) {
				tot();
			}
			public void tot() {
				if (!comboBox_expertise.getSelectedItem().equals("")&& datePickerfrom.getDate()!=null) {
					String timeStamp1 = new SimpleDateFormat("yyyyMMdd").format(datePickerfrom.getDate());
					MainClass.masterControler.SACont.searchAvailableAppointment(MainClass.masterControler.SACont.expIDlist.get(comboBox_expertise.getSelectedIndex()),timeStamp1);
				}
/////////////////////////////////////////////////
			}
	    	
	    });*/
		add(datePickerfrom);
		
	       	editableList = new WebList ( createSampleData () );
	        editableList.setVisibleRowCount ( 6 );
	        editableList.setSelectedIndex ( 0 );
	        editableList.setEditable ( false );
	        WebScrollPane1 = new WebScrollPane ( editableList );
	        WebScrollPane1.setBounds(50, 328, 250, 200);
	        WebScrollPane1.setVisible(true);
	        add(WebScrollPane1);
	//        WebScrollPane1.removeAll();
	    
	}
	public String[] createSampleData (ArrayList<Appointment> AppList)
    {
		String []st = new String[AppList.size()];
		for (int i  = 0 ; i < AppList.size() ; i ++)
		{
			st[i]="Start Time: "+AppList.get(i).getStart()+", End Time: "+AppList.get(i).getEnd();
			AppointmentIDList.add(i, Integer.valueOf(AppList.get(i).getIdappointment()));
		}
		return st;
    }
	public String[] createSampleData ()
    {
        
		return new String[]{"aaa" };
    }
}
