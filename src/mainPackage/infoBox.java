package mainPackage;

import javax.swing.JOptionPane;

public class infoBox {

	public static void show (String infoMessage, String titleBar)
	{
		JOptionPane.showMessageDialog(null,infoMessage, titleBar, JOptionPane.ERROR_MESSAGE);
	}

}
