package gui;

import java.applet.Applet;
import java.awt.Color;
import java.awt.GridLayout;

import netscape.javascript.JSException;
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
	
	/**
	 * A panel spanning the applet on which the game is drawn
	 */
	private GraphicPanel panel;
	
	/**
	 * The window in which the applet is running
	 */
	public static JSObject window;
	
	/**
	 * The dimensions of the applet
	 */
	public static Vector2D dimensions = new Vector2D(1200, 600);

	/**
	 * Initialize the applet.
	 */
	public void init() {
		this.setSize((int)dimensions.x, (int)dimensions.y);
		
		this.setBackground(new Color(220, 220, 220));
		this.setLayout(new GridLayout());
		
		try{
			window = JSObject.getWindow(this);
		} catch(JSException e){
			window = null;
		}
		
		panel = new GraphicPanel();
		this.add(panel);
	}
}
