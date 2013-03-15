package misc;

import java.util.LinkedList;

/**
 * Records which keys are currently being held down
 * @author Adam
 *
 */
public class Keyboard {
    
	private LinkedList<Integer> keysDown;
	
	public Keyboard(){
		keysDown = new LinkedList<Integer>();
	}
	
	/**
	 * Return whether or not a key is being pressed
	 * @param keyCode
	 * @return
	 */
	public boolean keyDown(int keyCode){
		return keysDown.contains(keyCode);
	}
	
	/**
	 * Press a key
	 * @param keyCode
	 */
	public void keyPressed(int keyCode){
		if(!keysDown.contains(keyCode)) keysDown.add(keyCode);
	}
	
	/**
	 * Release a key
	 * @param keyCode
	 */
	public void keyReleased(int keyCode){
		if(keysDown.contains(keyCode)) keysDown.remove(keysDown.indexOf(keyCode));
	}
}
