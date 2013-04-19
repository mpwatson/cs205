package objects;

import java.util.ArrayList;

/**
 * A collection of sounds and music belonging to a GameState
 * @author Adam
 *
 */
public class MusicBox {
	// Stores all sounds
	private ArrayList<Sound> soundList;
	
	private boolean running;
	
	public MusicBox(boolean turnedOn){
		soundList = new ArrayList<Sound>();
		running = turnedOn;
	}
	
	public void turnOn(){
		running = true;
	}
	
	public void addSound(String name, String fileName, boolean isMusic){
		soundList.add(new Sound(name, fileName, isMusic));
	}
	
	public void playSound(String name){
		if(!running) return;
		
		for(int i = 0;i < soundList.size();i++){
			if(soundList.get(i).getID() == name){
				soundList.get(i).start();
				return;
			}
		}
	}
	
	public void stopSound(String name){
		for(int i = 0;i < soundList.size();i++){
			if(soundList.get(i).getID() == name){
				soundList.get(i).stop();
				return;
			}
		}
	}
	
	public void turnOff(){
		for(int i = 0;i < soundList.size();i++){
			soundList.get(i).stop();
		}
		running = false;
	}
}
