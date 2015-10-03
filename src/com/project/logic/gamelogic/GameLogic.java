package com.project.logic.gamelogic;

import java.util.ArrayList;

import com.project.logic.Game;
import com.project.logic.Row;
import com.project.logic.board.Board;
import com.project.logic.player.Player;
import com.project.logic.player.PlayerEvent;
import com.project.logic.player.PlayerListener;

public abstract class GameLogic implements PlayerListener {

	protected Game game;
	protected Player currentPlayer;
	protected ArrayList<Row> removeOptions = new ArrayList<Row>(); 
	
	public GameLogic(Game game) {
		this.game = game;
		this.currentPlayer = game.getPlayerOne();
		this.game.getPlayerOne().addPlayerListener(this);
		this.game.getPlayerTwo().addPlayerListener(this);
	}

	public abstract void loop();

	public void eventPerformed(PlayerEvent e) {
		System.out.println(e);
	}
	
	protected void moveToNextPlayer(){
		if(currentPlayer == game.getPlayerOne()){
			System.out.println("Switching to player 2");
			currentPlayer = game.getPlayerTwo();
		}else{
			System.out.println("Switching to player 1");
			currentPlayer = game.getPlayerOne();
		}
	}
	
	
	public Player checkPlayer(int stoneColor){
		if(stoneColor == Board.BLACK_VALUE) return game.getPlayerTwo();
		return game.getPlayerOne();
	}
	
	
	private Player returnWinner(){
		if(game.getPlayerOne().getStoneAccount() == 0)
			return game.getPlayerTwo();
		
		if(game.getPlayerTwo().getStoneAccount() == 0)
			return game.getPlayerOne();
		
		return null;
	}
	
	
	protected boolean checkForWin(){
		if(game.getPlayerOne().getStoneAccount() == 0 || game.getPlayerTwo().getStoneAccount() == 0)
			return true;
		
		return false;
	}

	public Player getActivePlayer() {
		return currentPlayer;
	}
	
	
}