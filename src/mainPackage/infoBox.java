package mainPackage;

import javax.swing.JOptionPane;
/**
 * Class that shows error pop up 
 * @author Ruslan
 *
 */
public class infoBox {

	public static void show (String infoMessage, String titleBar)
	{
		JOptionPane.showMessageDialog(null,infoMessage, titleBar, JOptionPane.ERROR_MESSAGE);
	}

}
