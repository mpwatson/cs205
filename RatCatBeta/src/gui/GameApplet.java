package gui;

import java.applet.Applet;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import misc.Vector2D;

/**
 * The Applet running the game
 * @author Adam
 *
 */
public class GameApplet extends Applet {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	private GraphicPanel panel;
	
	/**
	 * The dimensions of the applet
	 */
	public static Vector2D dimensions = new Vector2D(1200, 600);
	
	/**
	 * The location of the applet
	 */
	public static String location = "C:/";

	/**
	 * Initialize the contents of the frame.
	 */
	public void init() {
		this.setSize((int)dimensions.x, (int)dimensions.y);
		
		this.setBackground(new Color(220, 220, 220));
		this.setLayout(new GridLayout());
		
		this.addKeyListener(new KeyInputListener());
		
		location = this.getCodeBase().toString().substring(6);
		
		panel = new GraphicPanel();
		this.add(panel);
	}
	
	class KeyInputListener implements KeyListener {
		
		public void keyPressed(KeyEvent e) {
			panel.keyPressed(e.getKeyCode());
		}
		 
		public void keyReleased(KeyEvent e) {
			panel.keyReleased(e.getKeyCode());
		}
		
		public void keyTyped(KeyEvent e) {}
	}
}
