package com.gipf.client.game;

import com.gipf.client.game.player.Player;
import com.project.client.board.Board;

public class Game {

	private Player playerOne, playerTwo;
	private Board board;
	
	public Game(Player playerOne, Player playerTwo) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.board = new Board();
	}
	
	public Game() {
		this.board = new Board();
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public void setPlayerOne(Player player) {
		this.playerOne = player;
	}
	
	public void setPlayerTwo(Player player) {
		this.playerTwo = player;
	}
	
	public Player getPlayerOne() {
		return this.playerOne;
	}
	
	public Player getPlayerTwo() {
		return this.playerTwo;
	}
}
