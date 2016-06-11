package views;

import graphics.GUIimage;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSeparator;

import mainPackage.MainClass;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class RequestDetailsView extends JPanel {
	private static final long serialVersionUID = 1L;
	public JSeparator separator;
	public JTextArea fileArea ;
	public JTextField idrec;
	public JLabel errorlbl; 
	public JComboBox specs;
	public JButton btnEntireFile;
	public JButton btnBySpec;
	
	
	public RequestDetailsView() {
		setLayout(null);
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
		
		JLabel lblExpert = new JLabel("Request Details");
		lblExpert.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblExpert.setBounds(190, 134, 186, 71);
		add(lblExpert);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileArea.setText("");
				idrec.setText("");
				errorlbl.setText("");
				specs.setVisible(false);
				MainClass.masterControler.RDCont.mf=null;
				MainClass.masterControler.setView(
						MainClass.masterControler.EXPVCont.expview);
			}
		});
		btnBack.setBounds(490, 466, 140, 55);
		add(btnBack);
		btnBack.setIcon(new GUIimage("back", 25, 23).image);
		
		btnEntireFile = new JButton("View entire medical file");
		btnEntireFile.setEnabled(false);
		btnEntireFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				specs.setVisible(false);
				if (MainClass.masterControler.RDCont.mf != null){
					fileArea.setText(MainClass.masterControler.RDCont.entireFileFormat());
				}
			}
		});
		btnEntireFile.setBounds(488, 274, 142, 55);
		add(btnEntireFile);
		
		btnBySpec = new JButton("View by speciality");
		btnBySpec.setEnabled(false);
		btnBySpec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				specs.setVisible(true);
			}
		});
		btnBySpec.setBounds(490, 356, 140, 55);
		add(btnBySpec);
		
		fileArea = new JTextArea();
		fileArea.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		fileArea.setEditable(false);
		fileArea.setLineWrap(true);
		fileArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
		fileArea.setBounds(75, 315, 389, 206);
		add(fileArea);
	
		JLabel lblEnterPatientId = new JLabel("Enter patient ID:");
		lblEnterPatientId.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblEnterPatientId.setBounds(70, 216, 120, 28);
		add(lblEnterPatientId);
	
		JButton searchIcon = new JButton("");
		searchIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				specs.setVisible(false);
				specs.setSelectedItem("");
				if (idrec.getText().equals("")){
					errorlbl.setForeground(Color.RED);
					errorlbl.setText("Please enter patient id!");
				} else MainClass.masterControler.RDCont.getMedicalFile(idrec.getText()); 
			}
		});
		searchIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
			}
		});
		searchIcon.setBounds(296, 216, 29, 28);
		searchIcon.setIcon(new GUIimage("search",searchIcon.getWidth()-7,searchIcon.getHeight()-7).image);
		this.add(searchIcon);
		
		idrec = new JTextField();
		idrec.setBounds(200, 222, 86, 20);
		add(idrec);
		idrec.setColumns(10);
		
		errorlbl = new JLabel("");
		errorlbl.setFont(new Font("Tahoma", Font.BOLD, 17));
		errorlbl.setBounds(70, 255, 255, 28);
		add(errorlbl);
		
		specs = new JComboBox();
		specs.addItem("");
		specs.addItem("Cardiology");
		specs.addItem("Neurology");
		specs.addItem("Genycology");
		specs.addItem("Oncology");
		specs.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if((e.getStateChange()==ItemEvent.SELECTED)&&(!(((String)specs.getSelectedItem()).equals(""))))
						MainClass.masterControler.RDCont.manageComboBox();
				else fileArea.setText("");
			}
		});

		specs.setBounds(490, 422, 140, 33);
		specs.setVisible(false);
		add(specs);

	}
}
