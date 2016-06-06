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
import javax.swing.JLayeredPane;
import javax.swing.JDesktopPane;
import javax.swing.JScrollBar;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JComboBox;

import ocsf.server.GHealthServer;
import java.awt.Color;
import javax.swing.DropMode;

public class CancelAppointmentView extends JPanel {
	private static final long serialVersionUID = 1L;
	public JSeparator separator;
	private JTextField searchField;
	private JButton btnSerch;
	private JComboBox comboBox;
	
	public CancelAppointmentView() {
		setLayout(null);
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
		
		JLabel lblExpert = new JLabel("Cancel Appointment");
		lblExpert.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblExpert.setBounds(211, 182, 253, 118);
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
		
		searchField = new JTextField();
		searchField.setForeground(new Color(0, 0, 0));
		searchField.setToolTipText("");
		searchField.setText("type patient id ");
		searchField.setBounds(73, 280, 401, 28);
		add(searchField);
		searchField.setColumns(10);
		
		JLabel searchIcon = new JLabel("");
		searchIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MainClass.masterControler.CACont.searchAppointments();
			}
			});

		searchIcon.setBounds(490, 280, 29, 28);
		searchIcon.setIcon(new GUIimage("search",searchIcon.getWidth(),searchIcon.getHeight()).image);
		add(searchIcon);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		comboBox.setBounds(73, 334, 401, 28);
		add(comboBox);
		
		JButton btnCancelApp = new JButton("Cancel Appointment");
		btnCancelApp.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				MainClass.masterControler.CACont.cancelAppointment(
						String.valueOf(MainClass.masterControler.CACont.CAP.getAppList().get(comboBox.getSelectedIndex()).getIdappointment()));
					
				
			}
		});
		btnCancelApp.setBounds(490, 374, 140, 55);
		add(btnCancelApp);
	}
	public JComboBox getComboBox() {
		return comboBox;
	}
	public void setComboBox(JComboBox comboBox) {
		this.comboBox = comboBox;
	}
	public JTextField getSearchField() {
		return searchField;
	}
	public void setSearchField(JTextField searchField) {
		this.searchField = searchField;
	}
	public JButton getBtnSerch() {
		return btnSerch;
	}
	public void setBtnSerch(JButton btnSerch) {
		this.btnSerch = btnSerch;
	}
}
