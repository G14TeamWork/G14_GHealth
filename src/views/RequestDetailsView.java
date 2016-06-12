package views;

import graphics.GUIimage;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import mainPackage.MainClass;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JComboBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ScrollPaneConstants;
import java.awt.Insets;
import javax.swing.SwingConstants;

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
		lblExpert.setHorizontalAlignment(SwingConstants.CENTER);
		lblExpert.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblExpert.setBounds(10, 134, 655, 71);
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
		btnBack.setBounds(490, 452, 140, 55);
		add(btnBack);
		btnBack.setIcon(new GUIimage("back", 25, 23).image);
		
		btnEntireFile = new JButton("<html><center>View entire<br />medical file</html>");
		btnEntireFile.setMargin(new Insets(2, 10, 2, 10));
		btnEntireFile.setEnabled(false);
		btnEntireFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				specs.setVisible(false);
				if (MainClass.masterControler.RDCont.mf != null){
					fileArea.setText(MainClass.masterControler.RDCont.entireFileFormat());
				}
			}
		});
		btnEntireFile.setBounds(490, 288, 142, 55);
		btnEntireFile.setIcon(new GUIimage("medicalFile", 25, 23).image);
		btnEntireFile.setIconTextGap(10);
		btnEntireFile.setBounds(490, 386, 140, 55);
		add(btnEntireFile);

		btnBySpec = new JButton("<html><center>View by<br />speciality</html>");
		btnBySpec.setEnabled(false);
		btnBySpec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				specs.setVisible(true);
				specs.setSelectedItem("");
			}
		});
		btnBySpec.setIcon(new GUIimage("medicalDocument", 25, 23).image);
		btnBySpec.setIconTextGap(11);
		btnBySpec.setBounds(490, 322, 140, 55);
		add(btnBySpec);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(45, 288, 433, 233);
		add(scrollPane);
		
				
		fileArea = new JTextArea();
		fileArea.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		fileArea.setLineWrap(true);
		scrollPane.setViewportView(fileArea);
		fileArea.setEditable(false);
		fileArea.setFont(new Font("Dialog", Font.PLAIN, 14));
	
		JLabel lblEnterPatientId = new JLabel("Enter patient ID:");
		lblEnterPatientId.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblEnterPatientId.setBounds(70, 216, 120, 28);
		add(lblEnterPatientId);
	
		JButton searchIcon = new JButton("");
		searchIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				specs.setVisible(false);
				specs.setSelectedItem("");
				btnBySpec.setEnabled(false);
				btnEntireFile.setEnabled(false);
				fileArea.setText("");
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
		searchIcon.setBounds(303, 218, 29, 28);
		searchIcon.setIcon(new GUIimage("search",searchIcon.getWidth()-7,searchIcon.getHeight()-7).image);
		this.add(searchIcon);
		
		idrec = new JTextField();
		idrec.setBounds(190, 222, 101, 20);
		add(idrec);
		idrec.setColumns(10);
		
		errorlbl = new JLabel("");
		errorlbl.setFont(new Font("Tahoma", Font.BOLD, 17));
		errorlbl.setBounds(70, 255, 306, 28);
		add(errorlbl);
		
		specs = new JComboBox();
		specs.setMaximumRowCount(4);
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

		specs.setBounds(490, 276, 140, 33);
		specs.setVisible(false);
		add(specs);

	}
}
