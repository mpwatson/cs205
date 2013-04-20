package objects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Stores a sound effect or peice of game music
 * @author Adam
 *
 */
public class Sound {
	
	private String ID;
	
	private String fileName;
	
	private boolean loop;
	
	private Clip clip;
	
	private InputStream input;
	
	private AudioInputStream audio;
	
	Sound(String iID, String iFileName, boolean isMusic){
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e1) {
			throw new Error("LineUnavailableException - Sound.Sound");
		}
		
		ID = iID;
		fileName = iFileName;
		loop = isMusic;
		
		try {
			input = new FileInputStream(fileName);
		} catch (FileNotFoundException e1) {
			throw new Error();
		}
		
		try {
			audio = AudioSystem.getAudioInputStream(input);
		} catch (UnsupportedAudioFileException e) {
			throw new Error();
		} catch (IOException e) {
			throw new Error();
		}
		
		try {
			clip.open(audio);
		} catch (LineUnavailableException e) {
			throw new Error();
		} catch (IOException e) {
			throw new Error();
		}
	}
	
	/**
	 * Start playing the sound
	 */
	public void start(){
		// Play as sound effect
		if(!loop){
			clip.start();
			return;
		}
		
		// Play as music
		clip.loop(1000);
	}
	
	/**
	 * Stop playing the sound
	 */
	public void stop(){
		clip.stop();
	}
	
	public String getFileName(){
		return fileName;
	}
	
	public String getID(){
		return ID;
	}
}
