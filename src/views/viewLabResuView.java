package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import graphics.GUIimage;
import javax.swing.JButton;
import mainPackage.MainClass;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.border.BevelBorder;
import com.alee.laf.progressbar.WebProgressBar;
import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;

public class viewLabResuView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	public JPanel panel1;
	public JPanel panel2;
	public JLabel labelPatName;
	public JLabel lblTesttype;
	public JComboBox comboBoxChooseTest;
	private JLabel lblLblphoto;

	public viewLabResuView() {
		
		//*** DO NOT DELETE! ***//
		
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
		
		JLabel lblviewlabres = new JLabel("View lab results");
		lblviewlabres.setHorizontalAlignment(SwingConstants.CENTER);
		lblviewlabres.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblviewlabres.setBounds(40, 134, 603, 29);
		add(lblviewlabres);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainClass.masterControler.setView(
						MainClass.masterControler.VMHCont.ViewMedicalHistoryview);
			}
		});
		btnBack.setBounds(479, 455, 151, 55);
		add(btnBack);
		btnBack.setIcon(new GUIimage("back", 25, 23).image);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(40, 227, 603, 218);
		add(tabbedPane);
		
		//*****************************************************************************//
		
		panel1= new JPanel();
		panel1.setLayout(null);
		tabbedPane.addTab("result", null, panel1, null);
		
		//*****************************************************************************//
		
		panel2 = new JPanel();
		panel2.setLayout(null);
		//panel2.(new GUIimage("photo-not-available", 25, 23).image);
		tabbedPane.addTab("photo", null, panel2, null);
		
		lblLblphoto = new JLabel("");
		lblLblphoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblLblphoto.setBounds(0, 0, 598, 188);
		lblLblphoto.setIcon(new GUIimage("photo-not-available", lblLblphoto.getWidth(), lblLblphoto.getHeight()).image);
		panel2.add(lblLblphoto);
		
		labelPatName = new JLabel("");
		labelPatName.setHorizontalAlignment(SwingConstants.CENTER);
		labelPatName.setFont(new Font("Tahoma", Font.BOLD, 17));
		labelPatName.setBounds(193, 162, 306, 29);
		add(labelPatName);
		
		comboBoxChooseTest = new JComboBox();
		comboBoxChooseTest.setBounds(398, 219, 245, 22);
		comboBoxChooseTest.setEditable(true); 
		comboBoxChooseTest.setAlignmentX(CENTER_ALIGNMENT);
		comboBoxChooseTest.setSelectedItem("");
		add(comboBoxChooseTest);
		
		lblTesttype = new JLabel("Choose test:");
		lblTesttype.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTesttype.setBounds(398, 192, 245, 22);
		lblTesttype.setVisible(true);
		add(lblTesttype);
		
	}
}
