package views;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.Timer;

public class SlideContainer extends JLayeredPane {
	/**
	 * This class extends JPanel. <br>
	 * It is a view containing components of gui. <br>
	 * This is the screen that opens every change of screens.<br>
	 * Almost all functionality of GUI is here.
	 * @author Ruslan
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static final int PREF_W = 677;
	   private static final int PREF_H = 562;
	   private static final int SLIDE_DELAY = 1;
	   protected static final int DELTA_X = 20;
	   Component oldComponent;

	   public SlideContainer() {
	      setLayout(null);
	   }

	   @Override
	   public Dimension getPreferredSize() {
	      return new Dimension(PREF_W, PREF_H);
	   }

	   @Override
	   public Component add(Component comp) {
	      Component[] comps = getComponents();
	      if (comps.length > 0) {
	         oldComponent = comps[0];
	      }
	      if (oldComponent == comp) {
	         return super.add(comp);
	      }
	      if (oldComponent != null) {
	         putLayer((JComponent) oldComponent, JLayeredPane.DEFAULT_LAYER);
	      }
	      Component returnResult = super.add(comp);
	      putLayer((JComponent) comp, JLayeredPane.DRAG_LAYER);
	      comp.setSize(getPreferredSize());
	      comp.setVisible(true);
	      comp.setLocation(getPreferredSize().width, 0);
	      slideFromRight(comp, oldComponent);
	      return returnResult;
	   }

	   private void slideFromRight(final Component comp, final Component oldComponent2) {
	      new Timer(SLIDE_DELAY, new ActionListener() {

	         public void actionPerformed(ActionEvent aEvt) {
	            int x = comp.getX();
	            if (x <= 0) {
	               comp.setLocation(0, 0);
	               putLayer((JComponent) comp, JLayeredPane.DEFAULT_LAYER);
	               if (oldComponent2 != null) {
	                  remove(oldComponent2);
	               }
	               ((Timer) aEvt.getSource()).stop();
	            } else {
	               x -= DELTA_X;
	               comp.setLocation(x, 0);
	            }
	            repaint();
	         }

	      }).start();
	   }
}