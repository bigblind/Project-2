package com.project.server.logic;

import com.project.common.player.Player;
import com.project.server.logic.board.Board;
import com.project.server.logic.gamelogic.GameLogic;

public class Game {

	private GameLogic logic;
	private Player playerOne, playerTwo;
	private Board board;

	public Game() {
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
}