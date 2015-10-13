package com.project.client.sound;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.project.client.visuals.ResourceLoader;

public class SoundManager {
	
	private Clip invalid, background, valid, win, lose;
	private AudioInputStream inValidMove, backgroundSound, validMove, loseSound, winSound;
	
	public SoundManager() {
		try {
			inValidMove = AudioSystem.getAudioInputStream(ResourceLoader.ON_INVALID_MOVE);
			backgroundSound = AudioSystem.getAudioInputStream(ResourceLoader.BACKGROUND);
			validMove = AudioSystem.getAudioInputStream(ResourceLoader.ON_VALID_MOVE);
			loseSound = AudioSystem.getAudioInputStream(ResourceLoader.LOSE_SOUND);
			winSound = AudioSystem.getAudioInputStream(ResourceLoader.WIN_SOUND);
			
			invalid = AudioSystem.getClip();
			invalid.open(inValidMove);
			background = AudioSystem.getClip();
			background.open(backgroundSound);
			lose = AudioSystem.getClip();
			lose.open(loseSound);
			valid = AudioSystem.getClip();
			valid.open(validMove);
			win = AudioSystem.getClip();
			win.open(winSound);

		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void losePlay() {
		if (!lose.isRunning()) {
			lose.flush();
			lose.setMicrosecondPosition(0);
			FloatControl gain = (FloatControl) background.getControl(FloatControl.Type.MASTER_GAIN);
			gain.setValue(2.0f);
			lose.start();
		} else {
			lose.stop();
		}
	}

	public void backgroundPlay() {
		if (!background.isRunning()) {
			FloatControl gain = (FloatControl) background.getControl(FloatControl.Type.MASTER_GAIN);
			gain.setValue(-20.0f);
			background.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	
	public void stopPlayBackground() {
		background.stop();
		background.flush();
		background.stop();
		background.drain();
	}

	public void winPlay() {
		if (!win.isRunning()) {
			win.flush();
			win.setMicrosecondPosition(0);
			FloatControl gain = (FloatControl) background.getControl(FloatControl.Type.MASTER_GAIN);
			gain.setValue(2.0f);
			win.start();
		} else {
			win.stop();
		}
	}
	

	public void movePlay(Boolean validOrNot) {
		if (!validOrNot) {
			if (!invalid.isActive() || !invalid.isRunning()) {

				invalid.flush();
				invalid.setMicrosecondPosition(0);
				FloatControl gainControl = (FloatControl) invalid.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(6.0f);
				invalid.start();
			} else {
				invalid.stop();

			}
		} else {

			if (!valid.isActive() || !invalid.isRunning()) {
				valid.setMicrosecondPosition(0);
				FloatControl gainControl = (FloatControl) valid.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(6.0f);
				valid.start();
			} else {
				valid.stop();
			}

		}
	}

}