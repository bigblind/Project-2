package com.gipf.client.game;

public class GameController {

	private Game game;
	
	public GameController(Game game) {
		this.game = game;
	}
	
	public GameController() {

	}
	
	public void input(String text) {
		
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public Game getGame() {
		return this.game;
	}
}
