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
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import com.alee.laf.text.WebEditorPane;

import java.awt.Color;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.ScrollPaneConstants;

public class ViewRefDetView extends JPanel {
	private static final long serialVersionUID = 1L;
	public JSeparator 	separator;
	public JTextField 	inputPatId;
	public JTextArea	redDetails;
	public JLabel 		lblChooseRef;
	public JComboBox 	comboRefs;
	public JLabel 		lblErrorLabel;
	public JLabel 		lblPatId;
	public JButton 		btnSearch;
	public JButton 		btnBack;
	public JLabel		lblExpert;
	
	public ViewRefDetView() {
		setLayout(null);
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
		
		lblExpert = new JLabel("View reference details");
		lblExpert.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblExpert.setBounds(212, 132, 223, 55);
		add(lblExpert);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainClass.masterControler.setView(
						MainClass.masterControler.LWCont.labworkerview);
			}
		});
		btnBack.setBounds(457, 442, 140, 55);
		add(btnBack);
		btnBack.setIcon(new GUIimage("back", 25, 23).image);
		
		inputPatId = new JTextField();
		inputPatId.setBounds(142, 206, 121, 25);
		add(inputPatId);
		inputPatId.setColumns(10);
		
		lblPatId = new JLabel("Patient ID:");
		lblPatId.setBounds(68, 206, 76, 24);
		add(lblPatId);
		
		btnSearch = new JButton("");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboRefs.removeAllItems();
				MainClass.masterControler.VRDCont.getPatientRefs(inputPatId.getText());
			}
		});
		btnSearch.setBounds(262, 206, 25, 25);
		btnSearch.setIcon(new GUIimage("search",btnSearch.getWidth()-7,btnSearch.getHeight()-7).image);

		add(btnSearch);
		
		lblErrorLabel = new JLabel("Error  Label");
		lblErrorLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblErrorLabel.setBounds(68, 241, 244, 22);
		add(lblErrorLabel);
		
		comboRefs= new JComboBox();
		
		comboRefs.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if((e.getStateChange()==ItemEvent.SELECTED)&&(!(((String)comboRefs.getSelectedItem()).equals(""))))
					 redDetails.setText(MainClass.masterControler.VRDCont.getDetailsFromList((String)comboRefs.getSelectedItem()));
				else redDetails.setText("");
			}
		});
		comboRefs.setBounds(474, 207, 123, 22);
		add(comboRefs);
		
		lblChooseRef = new JLabel("Choose Reference:");
		lblChooseRef.setBounds(366, 206, 107, 25);
		add(lblChooseRef);
	/*
		redDetails.setEditable(false);
		redDetails.setLineWrap(true);
		redDetails.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		redDetails.setBounds(68, 285, 529, 146);
		add(redDetails);
		*/
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(68, 285, 529, 146);
		add(scrollPane);
		
		redDetails = new JTextArea();
		scrollPane.setViewportView(redDetails);
		redDetails.setLineWrap(true);
		//redDetails = new WebEditorPane("text/html","");
		redDetails.setEditable(false);
		redDetails.setFont(new Font("Dialog", Font.PLAIN, 14));
		
	}
	
}
