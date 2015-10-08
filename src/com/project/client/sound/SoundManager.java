package com.project.client.sound;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.project.client.visuals.ResourceLoader;

public class SoundManager {
	private static Clip invalid, background, valid, win;
	private AudioInputStream inValidMove, backgroundSound, validMove;
	public SoundManager() {
		
		try {
			inValidMove = AudioSystem.getAudioInputStream(ResourceLoader.ON_INVALID_MOVE);
			backgroundSound = AudioSystem.getAudioInputStream(ResourceLoader.BACKGROUND);
			validMove = AudioSystem.getAudioInputStream(ResourceLoader.ON_VALID_MOVE);
			
			
			invalid = AudioSystem.getClip();
			invalid.open(inValidMove);
			background = AudioSystem.getClip();
			background.open(backgroundSound);
			valid = AudioSystem.getClip();
			valid.open(validMove);
			
			
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

			
		
	}
	
	public void backgroundPlay(){
		if(!background.isRunning()){
			FloatControl gain = (FloatControl) background.getControl(FloatControl.Type.MASTER_GAIN);
			gain.setValue(-20.0f);
			background.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	
	public void winPlay(){
		if(!win.isRunning()){
			win.flush();
			win.setMicrosecondPosition(0);
			FloatControl gain = (FloatControl) background.getControl(FloatControl.Type.MASTER_GAIN);
			gain.setValue(2.0f);
			win.start();
		}else {
			win.stop();
		}

	}

	public void movePlay(Boolean validOrNot){
		if(!validOrNot){
		if(!invalid.isActive() || !invalid.isRunning()){

			
			invalid.flush();
			invalid.setMicrosecondPosition(0);
			FloatControl gainControl = (FloatControl) invalid.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(6.0f); 
			invalid.start();
		}else{
			invalid.stop();
			
		}} else{
			
			if(!valid.isActive() || !invalid.isRunning()){
				valid.setMicrosecondPosition(0);
				FloatControl gainControl = (FloatControl) valid.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(6.0f); 
				valid.start();
			}else{
				valid.stop();
			}
			
		}
	}
	
	

}
