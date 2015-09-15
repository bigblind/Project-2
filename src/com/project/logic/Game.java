package com.project.logic;

public class Game {

	private GameLogic logic;
	private Player playerOne, playerTwo;
	private Board board;
	
	public Game() {
		this.board = new Board();
		this.playerOne = new Player();
		this.playerTwo = new Player();
	}
	
	public void setGameLogic(GameLogic logic) {
		this.logic = logic;
	}
	
	public GameLogic getGameLogic() {
		return this.logic;
	}
	
	public Player getPlayerOne() {
		return this.playerOne;
	}
	
	public Player getPlayerTwo() {
		return this.playerTwo;
	}
	
	public Board getBoard() {
		return this.board;
	}
}
