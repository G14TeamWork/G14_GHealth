package views;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import graphics.GUIimage;
import javax.swing.JButton;
import mainPackage.MainClass;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
/**
 * This class extends JPanel. <br>
 * It is a view containing components of gui. <br>
 * This is the screen that opens when expert enters view appointment history. <br>
 * Almost all functionality of GUI is here.
 * @author Ruslan
 *
 */
public class ViewAppHistoryView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	public JLabel labelPatName;
	public JTextArea fileArea;
	public ViewAppHistoryView() {
		
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
	
		JLabel lblviewapphis = new JLabel("View appontments history");
		lblviewapphis.setHorizontalAlignment(SwingConstants.CENTER);
		lblviewapphis.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblviewapphis.setBounds(40, 134, 625, 29);
		add(lblviewapphis);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainClass.masterControler.VMHCont.ViewMedicalHistoryview.btnViewLabHis.setEnabled(false);
				MainClass.masterControler.VMHCont.ViewMedicalHistoryview.btnViewmedicalHis.setEnabled(false);
					MainClass.masterControler.setView(
						MainClass.masterControler.VMHCont.ViewMedicalHistoryview);	
			}
		});
		btnBack.setBounds(479, 455, 151, 55);
		add(btnBack);
		btnBack.setIcon(new GUIimage("back", 25, 23).image);
		
		labelPatName = new JLabel("");
		labelPatName.setHorizontalAlignment(SwingConstants.CENTER);
		labelPatName.setFont(new Font("Tahoma", Font.BOLD, 17));
		labelPatName.setBounds(10, 162, 655, 29);
		add(labelPatName);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(45, 204, 422, 317);
		add(scrollPane);
		
	    fileArea = new JTextArea();
	    scrollPane.setViewportView(fileArea);
	    fileArea.setLineWrap(true);
	    fileArea.setEditable(false);
	    fileArea.setFont(new Font("Dialog", Font.PLAIN, 14));
		
		
		
	}
}
