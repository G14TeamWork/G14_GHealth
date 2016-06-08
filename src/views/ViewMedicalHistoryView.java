package views;

import graphics.GUIimage;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import mainPackage.MainClass;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.io.File;

public class ViewMedicalHistoryView extends JPanel {
	private static final long serialVersionUID = 1L;
	public JSeparator separator;
	public JTextField textFieldid;
	public JButton 	  btnViewLabHis;
	public JButton	  btnViewmedicalHis;
	public JLabel     textField_patname;
	public File 	  file;
	public JPanel 	  photoPanel;
	public ViewMedicalHistoryView() {
		setLayout(null);
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
		
		JLabel lblTitle = new JLabel("View Medical History");
		lblTitle.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblTitle.setBounds(214, 126, 253, 74);
		add(lblTitle);
		
		btnViewLabHis = new JButton("View lab history");
		btnViewLabHis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainClass.masterControler.VMHCont.askPhoto_Patient();
			}
		});
		btnViewLabHis.setBounds(479, 306, 151, 53);
		add(btnViewLabHis);
		
		btnViewmedicalHis = new JButton("View medical history");
		btnViewmedicalHis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnViewmedicalHis.setBounds(479, 372, 151, 55);
		add(btnViewmedicalHis);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainClass.masterControler.setView(
						MainClass.masterControler.EXPVCont.expview);
			}
		});
		btnBack.setBounds(479, 440, 151, 55);
		add(btnBack);
		btnBack.setIcon(new GUIimage("back", 25, 23).image);
		
		textFieldid = new JTextField();
	//	textFieldid.addKeyListener(new KeyAdapter() {
		//	@Override
		//	public void keyPressed(KeyEvent arg0) { // ENTER pressed
		//		if (arg0.getKeyChar()==10){}
			//		MainClass.masterControler.VMHCont.setVHEnt_Patient();					
		//}
	//	});
		textFieldid.setColumns(10);
		textFieldid.setBounds(149, 194, 148, 28);
		add(textFieldid);
		
		JLabel lblEnterPatientId = new JLabel("Enter patient ID:");
		lblEnterPatientId.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblEnterPatientId.setBounds(32, 193, 120, 28);
		add(lblEnterPatientId);
		
		JButton searchIcon = new JButton("");
		searchIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				textField_patname.setVisible(true);
				MainClass.masterControler.VMHCont.setVHEnt_Patient();
			}
		});
		searchIcon.setBounds(309, 194, 29, 28);
		searchIcon.setIcon(new GUIimage("search",searchIcon.getWidth()-7,searchIcon.getHeight()-7).image);
		this.add(searchIcon);
		
		textField_patname = new JLabel("");
		textField_patname.setFont(new Font("Tahoma", Font.BOLD, 17));
		textField_patname.setBounds(32, 235, 306, 29);
		add(textField_patname);
		
		photoPanel = new JPanel();
		photoPanel.setBounds(42, 277, 148, 118);
		add(photoPanel);
	}
}

