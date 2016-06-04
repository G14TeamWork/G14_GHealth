package graphics;

import javax.swing.JButton;

public class GButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	public GButton(String label) {
		super(label);
		setUI(new RoundedCornerButtonUI());
	}
	
	
}
