package com.project.logic.player;

import java.util.ArrayList;

import com.project.logic.board.Board;
import com.project.logic.Game;
import com.project.logic.Row;

public class Player {

	private String name;
	private int stoneAccount;
	private int stoneColor;
	
	
	public Player(){
		this.name = null;
		this.stoneAccount = 18;
		this.stoneColor = Board.BLACK_VALUE;
	}
	
	public Player(int stoneColor){
		this.name = null;
		this.stoneAccount = 18;
		this.stoneColor = stoneColor;
	}
	
	public Player(String name, int stones, int stoneColor){
		this.name = name;
		this.stoneAccount = stones;
		this.stoneColor = stoneColor;
	}
	
	public void makeTurn(Game game){
		//the player should call notifyListeners when he has made his turn.
	}
	
	public void chooseRowToRemove(Game game, ArrayList<Row> rows){
		//The player should call notifyListeners with the row he wants to remove.
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getStoneAccount(){
		return this.stoneAccount;
	}
	
	public void setStoneAccount(int stones){
		this.stoneAccount = stones;
	}
	
	public int getStoneColor(){
		return this.stoneColor;
	}

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
