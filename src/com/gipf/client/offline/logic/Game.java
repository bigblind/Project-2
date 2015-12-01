package com.gipf.client.offline.logic;

import com.gipf.client.game.player.Player;

public class Game {

	private GameLogic logic;
	private Player playerOne, playerTwo;
	private Board board;
	
	private boolean finished;

	public Game() {
		this.board = new Board();
		this.board.basicInit();
	}
	
	public Game(Board board, Player playerOne, Player playerTwo) {
		this.board = board;
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
	}
	
	public Game(Player playerOne, Player playerTwo) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.board = new Board();
	}
	
	public void setPlayerOne(Player player) {
		this.playerOne = player;
	}
	
	public void setPlayerTwo(Player player) {
		this.playerTwo = player;
	}

	public void setGameLogic(GameLogic logic) {
		this.logic = logic;
		this.board.setLogic(logic);
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
	
	public Game copy() {
		Game game = new Game(this.board.copy(), this.playerOne.copy(), this.playerTwo.copy());
		game.setGameLogic(this.logic);
		return game;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	
	public void finish() {
		this.finished = true;
	}
	
	public boolean isFinished() {
		return this.finished;
	}
}