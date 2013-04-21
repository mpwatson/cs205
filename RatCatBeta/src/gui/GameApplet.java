package gui;

import java.applet.Applet;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import netscape.javascript.JSObject;

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
	
	public static JSObject window;
	
	/**
	 * The dimensions of the applet
	 */
	public static Vector2D dimensions = new Vector2D(1200, 600);

	/**
	 * Initialize the contents of the frame.
	 */
	public void init() {
		this.setSize((int)dimensions.x, (int)dimensions.y);
		
		this.setBackground(new Color(220, 220, 220));
		this.setLayout(new GridLayout());
		
		//window = JSObject.getWindow(this);
		
		this.addKeyListener(new KeyInputListener());
		
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
