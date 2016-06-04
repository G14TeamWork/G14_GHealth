package graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class GUIimage extends ImageIcon {

	private static final long serialVersionUID = 1L;
	public ImageIcon image;
	
	public GUIimage(String name, int weigh, int height) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(this.getClass().getResourceAsStream("/" + name + ".png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(weigh, height,Image.SCALE_SMOOTH);
		image = new ImageIcon(dimg);
		
	}
	
	public GUIimage(URL add, int weigh, int height) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(add);
		    if (img == null) throw new Exception();
		} catch (Exception e) {
			System.err.println("Can't get input stream from URL");
		    try { img = ImageIO.read(this.getClass().getResourceAsStream("/profile_picture.png")); } 
		    catch (IOException e1) { e1.printStackTrace(); }
		} finally {
			Image dimg = img.getScaledInstance(weigh, height,Image.SCALE_SMOOTH);
			image = new ImageIcon(dimg);
		}
		
	}
	
}
