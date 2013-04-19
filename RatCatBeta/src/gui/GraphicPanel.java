package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Timer;
import javax.swing.JPanel;
import main.Switchboard;


/**
 * A panel for updating and drawing the game
 * @author Adam
 *
 */
public class GraphicPanel extends JPanel {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Color backColor = new Color(0, 0, 200);
	
	private GraphicPanel panel = this;
	
	private Timer timer;
	
	public GraphicPanel(){
		super();
		
		this.addMouseListener(new MouseInputListener());
		
		initialize();
	}
	
	private void initialize(){
		timer = new Timer(Switchboard.frameLength, new TimerListener());
		
		this.setBackground(backColor);
		
		Switchboard.initialize();
		timer.start();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Switchboard.draw(g);
	}
	
	public Timer getTimer(){
		return timer;
	}
	
	class TimerListener implements ActionListener {
		/**
		 * Called whenever the timer ticks
		 */
		public void actionPerformed(ActionEvent e) {
			Switchboard.update(panel.getMousePosition());
			panel.repaint();
		}
	}
	
	class MouseInputListener implements MouseListener {
		
		public void mouseClicked(MouseEvent e) {
			Switchboard.handleMouseClick(e.getPoint());
		}
		
		public void mouseEntered(MouseEvent e) {}
		
		public void mouseExited(MouseEvent e) {}
		
		public void mousePressed(MouseEvent e) {
			Switchboard.handleMousePress(e.getPoint());
		}
		
		public void mouseReleased(MouseEvent e) {
			Switchboard.handleMouseRelease(e.getPoint());
		}
	}
	
	public void keyPressed(int keyID){
		Switchboard.handleKeyPress(keyID);
	}
	
	public void keyReleased(int keyID){
		Switchboard.handleKeyRelease(keyID);
	}
}
