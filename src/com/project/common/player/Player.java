package com.project.common.player;

import java.util.ArrayList;

import com.project.common.utils.Point;
import com.project.server.logic.Game;
import com.project.server.logic.Row;

public class Player {

	private ArrayList<PlayerListener> listeners;
	private String name;
	private int stoneAccount;
	private int stoneColor;

	public Player() {
		this.listeners = new ArrayList<PlayerListener>();
	}
	

	public Player(int stoneColor) {
		this.name = "Player " + String.valueOf(stoneColor);
		this.stoneAccount = 15;
		this.stoneColor = stoneColor;
		this.listeners = new ArrayList<PlayerListener>();
	}

	public Player(String name, int stones, int stoneColor) {
		this.name = name;
		this.stoneAccount = stones;
		this.stoneColor = stoneColor;
		this.listeners = new ArrayList<PlayerListener>();
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStoneAccount() {
		if (name == null)
			this.name = "Player " + String.valueOf(stoneColor);
		return this.stoneAccount;
	}

	public void setStoneAccount(int stones) {
		this.stoneAccount = stones;
	}

	public void setStoneColor(int color) {
		this.stoneColor = color;
	}

	public int getStoneColor() {
		return this.stoneColor;
	}

	public void addPlayerListener(PlayerListener listener) {
		this.listeners.add(listener);
	}

	public void removePlayerListener(PlayerListener listener) {
		this.listeners.remove(listener);
	}

	private void notifyListeners(PlayerEvent e) {
		for (PlayerListener l : this.listeners)
			l.playerEventPerformed(e);
	}

	public void locationsClicked(Point a, Point b) {
		PlayerEvent e = new PlayerEvent(a, b, this);
		this.notifyListeners(e);
	}

	public String toString() {
		return this.name;
	}
	
	public void makeTurn(Game game){
		//the player should call notifyListeners when he has made his turn.
	}
	
	public void chooseRowToRemove(Game game, ArrayList<Row> rows){
		//The player should call notifyListeners with the row he wants to remove.
	}
}
