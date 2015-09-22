package com.project.logic;

import java.util.ArrayList;

public class BasicGameLogic extends GameLogic{
	private boolean waitingForWhite = false;
	private boolean waitingForBlack = false;
	
	public BasicGameLogic(Game game) {
		super(game);
	}

	public void loop() {
		currentPlayer.makeTurn(game);
	}
	
	public void eventPerformed(PlayerEvent e) {
		if(!(e instanceof Row)){ //if the action is not to pick a row.
			super.eventPerformed(e); //because super.eventPerformed places a stone on the board.
		}else{ //this is a row removal action
			handleSingleRow((Row)e);
			if(e.getPlayer().getStoneColor() == game.getBoard().WHITE_VALUE)
				waitingForWhite = false;
			else
				waitingForBlack = false;
		}
		boolean waitingForAny = waitingForWhite || waitingForBlack;
		if(checkForLines()){
			handleLines();
			if(! waitingForAny && checkForWin()){
				//TODO somehow indicate that the game is over, so the GUI can show the winner
			}
		}
		if(! waitingForAny)){
			moveToNextPlayer();
		}
	}
	
	private void handleLines(){
		if(removeOptions.size() == 1){
			handleSingleRow(removeOptions.get(0));
		}else{
			int whiteRowCount = 0;
			int blackRowCount = 0;
			for(Row row: removeOptions){
				if (row.getPlayer().getStoneColor() == game.getBoard().WHITE_VALUE) whiteRowCount += 1;
				else blackRowCount += 1;
			}
			if(whiteRowCount > 0){
				handlePlayerRows(game.getBoard().WHITE_VALUE);
			}
			if(blackRowCount > 0 && !waitingForWhite){
				handlePlayerRows(game.getBoard().BLACK_VALUE);
			}
		}
		
	}
	
	private void handleSingleRow(Row row){
		game.getBoard().removeRowAndExtensions(row.getFromPoint(), row.getToPoint());
		Player rowPlayer = row.getPlayer();
		rowPlayer.setStoneAccount(rowPlayer.getStoneAccount() + row.getLength());
		handleExtensions(row);
	}
	
	private void handlePlayerRows(int color){
		ArrayList<Row> rows = rowsForPlayer(color);
		if(rows.size() == 1){
			handleSingleRow(rows.get(0));
		}else{
			Player p = checkPlayer(color);
			if(p.getStoneColor() == game.getBoard().WHITE_VALUE) waitingForWhite = true;
			else waitingForBlack = true;
			p.chooseRowToRemove(game, rows);
		}
	}

	private ArrayList<Row> rowsForPlayer(int color) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void handleExtensions(Row row){
		if(currentPlayer.getStoneColor() == game.getBoard().WHITE){
			currentPlayer.setStoneAccount(currentPlayer.getStoneAccount() + row.getWhiteExtensionStones());
		}else{
			currentPlayer.setStoneAccount(currentPlayer.getStoneAccount() + row.getBlackExtensionStones());
		}
	}
}
