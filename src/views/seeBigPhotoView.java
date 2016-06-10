package views;

import graphics.DropShadowPanel;
import graphics.GUIimage;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.BorderLayout;
import java.awt.Window.Type;

public class seeBigPhotoView extends JFrame {


	private static final long serialVersionUID = 1L;
	public JLabel lblreerer;
	public String photoName="photo-not-available";
	/**
	 * Create the application.
	 * @throws URISyntaxException 
	 */
	public seeBigPhotoView() {
		setType(Type.UTILITY);
		setLocationByPlatform(true);
		setSize(1000, 800);
		getContentPane().setLayout(null);
		lblreerer = new JLabel();
		lblreerer.setBounds(204, 76, 572, 615);
		if (viewLabResuView.photoPath!="")
			photoName=viewLabResuView.photoPath;
		lblreerer.setIcon(new GUIimage(photoName, lblreerer.getWidth(),lblreerer.getHeight()).image);
		lblreerer.setName("ff");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		getContentPane().add(lblreerer);


	}
}