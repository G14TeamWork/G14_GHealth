package views;

import graphics.GUIimage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSeparator;

import mainPackage.MainClass;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Component;
import javax.swing.SwingConstants;
import java.awt.Insets;
/**
 * This class extends JPanel. <br>
 * It is a view containing components of gui. <br>
 * This is the screen that opens when lab worker logs in. <br>
 * Almost all functionality of GUI is here.
 * @author Ruslan
 *
 */
public class LabWorkerView extends JPanel {
	private static final long serialVersionUID = 1L;
	public JButton btnLogout;
	public JSeparator separator;
	public JLabel lblLabworkername;
	public LabWorkerView() {
		setLayout(null);
		this.setBounds(0, 0, 677, 562);
		this.setLayout(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 126, 677, 12);
		add(separator);
		
		JLabel lblLabWorker = new JLabel("Lab Worker");
		lblLabWorker.setFont(new Font("Dialog", Font.BOLD, 26));
		lblLabWorker.setBounds(243, 151, 166, 65);
		add(lblLabWorker);
		
		lblLabworkername = new JLabel("");
		lblLabworkername.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabworkername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLabworkername.setBounds(194, 203, 238, 31);
		add(lblLabworkername);
		
		btnLogout = new JButton();
		btnLogout.setText("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainClass.masterControler.LoginCont.loginEntity.setLogout(true);
				MainClass.masterControler.LoginCont.loginView.getTextFieldUserName().setText("");
				MainClass.masterControler.LoginCont.loginView.getPwdPassword().setText("");
				MainClass.masterControler.setView(
						MainClass.masterControler.LoginCont.loginView,
						MainClass.masterControler.LoginCont);
				// change user status to false
			}
		});
		btnLogout.setBounds(490, 440, 140, 55);
		add(btnLogout);
		btnLogout.setIcon(new GUIimage("logout", 25, 23).image);
		// TODO TODO  all initial actions of view reference details window
		JButton btnViewReferenceDetails = new JButton();
		btnViewReferenceDetails.setMargin(new Insets(2, 7, 2, 7));
		btnViewReferenceDetails.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnViewReferenceDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainClass.masterControler.VRDCont.ViewRefDetview.comboRefs.setEnabled(false);
				MainClass.masterControler.VRDCont.ViewRefDetview.lblErrorLabel.setText("");
				MainClass.masterControler.VRDCont.ViewRefDetview.inputPatId.setText("");
				MainClass.masterControler.VRDCont.ViewRefDetview.redDetails.setText("");
				MainClass.masterControler.VRDCont.ViewRefDetview.comboRefs.removeAllItems();
				MainClass.masterControler.setView(
						MainClass.masterControler.VRDCont.ViewRefDetview,
						MainClass.masterControler.VRDCont);
			}
		});
		btnViewReferenceDetails.setText("<html><center>View reference<br />details</html>");
		btnViewReferenceDetails.setBounds(490, 372, 140, 55);
		btnViewReferenceDetails.setIcon(new GUIimage("eye", 25, 23).image);
		add(btnViewReferenceDetails);
		
		JButton btnFillTestResult = new JButton();
		btnFillTestResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainClass.masterControler.setView(
						MainClass.masterControler.FTRCont.FillTestResview,
						MainClass.masterControler.FTRCont);
				MainClass.masterControler.FTRCont.FillTestResview.textField_first.setText(""); 
				MainClass.masterControler.FTRCont.FillTestResview.textFieldid.setText("");
				MainClass.masterControler.FTRCont.FillTestResview.textField_TestResult.setText("");
				MainClass.masterControler.FTRCont.FillTestResview.btnAddPhoto.setEnabled(false);
				MainClass.masterControler.FTRCont.FillTestResview.btnSave.setEnabled(false);
				MainClass.masterControler.FTRCont.FillTestResview.comboBox_test.setVisible(false);
				MainClass.masterControler.FTRCont.FillTestResview.comboBox_test.setSelectedItem("");
				MainClass.masterControler.FTRCont.FillTestResview.lblTesttype.setVisible(false);
			}
		});
		btnFillTestResult.setText("Fill test result");
		btnFillTestResult.setBounds(490, 304, 140, 55);
		btnFillTestResult.setIcon(new GUIimage("pen", 25, 23).image);
		add(btnFillTestResult);
		

	}
}
