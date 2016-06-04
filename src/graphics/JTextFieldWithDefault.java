package graphics;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class JTextFieldWithDefault extends JTextField {

	private static final long serialVersionUID = 1L;

	
	public JTextFieldWithDefault() {
        super();
        setup();
    }
	
	public JTextFieldWithDefault(String s) {
        super(s);
        setup();
    }
    
	@Override
    public String getText() {
        String s = super.getText();
        return s.trim().equals("")?"-1":s;
    }
	
	public void setup() {
		
		addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {} // must implement interface
			public void focusGained(FocusEvent e) { selectAll(); }
		});
		
		getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				foo();
			}
			public void removeUpdate(DocumentEvent e) {
				foo();
			}
			public void insertUpdate(DocumentEvent e) {
				foo();
			}
			
			public void foo() {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if (getText().equals("-1")) {
							setText("0");
							selectAll();
						}
					}
				});
			}
			
		});
	
	}
}
