package views;

import graphics.GUIimage;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSeparator;

import mainPackage.MainClass;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.alee.laf.table.WebTable;

import java.awt.Component;
/**
 * This class extends JPanel. <br>
 * It is a view containing components of gui. <br>
 * This is the screen that opens when clinic manager enters view reports view. <br>
 * Almost all functionality of GUI is here.
 * @author Ruslan
 *
 */
public class ViewReportsView extends JPanel {
	private static final long serialVersionUID = 1L;
	public JSeparator separator;
	public ViewReportsView() {
		setLayout(null);
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
		
		JLabel lblExpert = new JLabel("View Reports");
		lblExpert.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblExpert.setBounds(211, 182, 253, 118);
		add(lblExpert);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainClass.masterControler.setView(
						MainClass.masterControler.CMCont.clinicmanagerview);
			}
		});
		btnBack.setBounds(490, 440, 140, 55);
		add(btnBack);
		btnBack.setIcon(new GUIimage("back", 25, 23).image);
/*
		 WebTable table = new WebTable ( data, headers );
	        table.setEditable ( false );
	        table.setAutoResizeMode ( WebTable.AUTO_RESIZE_OFF );
	        table.setRowSelectionAllowed ( false );
	        table.setColumnSelectionAllowed ( true );
	        table.setPreferredScrollableViewportSize ( new Dimension ( 300, 100 ) );
	        return new WebScrollPane ( table );*/

	}
}





