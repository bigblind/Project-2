package com.project.logic;

import java.util.ArrayList;

public class Player {

	private ArrayList<PlayerListener> listeners;
	
	public void addPlayerListener(PlayerListener listener) {
		this.listeners.add(listener);
	}
	
	public void removePlayerListener(PlayerListener listener) {
		this.listeners.remove(listener);
	}
	
	private void notifyListeners(PlayerEvent e) {
		for (PlayerListener l : this.listeners) l.eventPerformed(e);
	}
}
