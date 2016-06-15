package views;

import graphics.GUIimage;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import mainPackage.MainClass;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import java.awt.Color;


import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.alee.laf.table.WebTable;

import java.awt.Component;
import java.util.Calendar;
import java.util.Date;

import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.JComboBox;
import javax.swing.JTextField;
/**
 * This class extends JPanel. <br>
 * It is a view containing components of gui. <br>
 * This is the screen that opens when clinic manager enters produce monthly report view. <br>
 * Almost all functionality of GUI is here.
 * @author Ruslan
 *
 */
public class ProduceMonthlyReportView extends JPanel {
	private static final long serialVersionUID = 1L;
	public JSeparator separator;
	public JTextArea fileArea;
	public  JComboBox comboBoxYear;
	public  JComboBox comboBoxMonth;
	public  JButton btnViewReport;
	public int Month;
	public int Year;
	public ProduceMonthlyReportView() {
		setLayout(null);
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
		
		JLabel lblExpert = new JLabel("Produce Monthly Report");
		lblExpert.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblExpert.setBounds(243, 123, 253, 87);
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(45, 212, 422, 317);
		add(scrollPane);
		
		   fileArea = new JTextArea();
		    scrollPane.setViewportView(fileArea);
		    fileArea.setLineWrap(true);
		    fileArea.setEditable(false);
		    fileArea.setFont(new Font("Arial", Font.PLAIN, 9));
		    
		    JLabel lblLblchoosemonth = new JLabel("Choose month:");
		    lblLblchoosemonth.setFont(new Font("Tahoma", Font.BOLD, 14));
		    lblLblchoosemonth.setHorizontalAlignment(SwingConstants.CENTER);
		    lblLblchoosemonth.setBounds(490, 246, 140, 28);
		    add(lblLblchoosemonth);
		    
		    comboBoxMonth = new JComboBox();
		    comboBoxMonth.addItem("");
		    comboBoxMonth.setEnabled(false);
		    comboBoxMonth.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent arg0) {
		    		if (comboBoxYear.getSelectedItem()!="")
		    		{
		    			btnViewReport.setEnabled(true);
		    		}
		    		else 
		    		  btnViewReport.setEnabled(false);
		    	}
		    });
		    comboBoxMonth.setBounds(490, 274, 140, 28);
		    comboBoxMonth.addItem("January");
		    comboBoxMonth.addItem("February");
		    comboBoxMonth.addItem("March");
		    comboBoxMonth.addItem("April");
		    comboBoxMonth.addItem("May");
		    comboBoxMonth.addItem("June");
		    comboBoxMonth.addItem("July");
		    comboBoxMonth.addItem("August");
		    comboBoxMonth.addItem("September");
		    comboBoxMonth.addItem("October");
		    comboBoxMonth.addItem("November");
		    comboBoxMonth.addItem("December");
		    add(comboBoxMonth);
		    
		    JLabel lblLblchooseyear = new JLabel("Choose year:");
		    lblLblchooseyear.setFont(new Font("Tahoma", Font.BOLD, 14));
		    lblLblchooseyear.setHorizontalAlignment(SwingConstants.CENTER);
		    lblLblchooseyear.setBounds(490, 190, 140, 28);
		    add(lblLblchooseyear);
		    
		    comboBoxYear = new JComboBox();
		    comboBoxYear.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		if (comboBoxYear.getSelectedItem()!="")
		    		{
		    			comboBoxMonth.setEnabled(true);
		    	
		    		}
		    		else 
		    		{
		    			comboBoxMonth.setEnabled(false);
		    			//comboBoxMonth.setSelectedItem("");
		    		}
		    	}
		    });
		    comboBoxYear.setBounds(490, 220, 140, 22);
		    comboBoxYear.addItem("");
		    comboBoxYear.addItem("2016");
		    comboBoxYear.addItem("2017");
		    add(comboBoxYear);
		    
		    btnViewReport = new JButton("View report");
		    btnViewReport.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		Year=comboBoxYear.getSelectedIndex()+2015;
		    		Month=comboBoxMonth.getSelectedIndex()-1;
		    		
		
		    		//Date date =(Date)strDate;
					Calendar cal  = Calendar.getInstance();
					cal.set(Year, Month,1);
					Date date = cal.getTime();
					MainClass.masterControler.CMCont.CME.setFrom(date);
					MainClass.masterControler.CMCont.viewMonthlyReport();
					
					//MainClass.masterControler.CMCont.CME.
		    	}
		    });
		    btnViewReport.setEnabled(false);
		    btnViewReport.setBounds(490, 374, 140, 53);
		    add(btnViewReport);
		    
	}
}
