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
			handlePlayerMove(e);
		}else{ //this is a row removal action
			handleRowChoice((Row)e);
		}
		boolean waitingForAny = (waitingForWhite || waitingForBlack) == true;
		
		if(game.getBoard().checkForLines().size() != 0){
			handleLines();
			if(! waitingForAny && checkForWin()){
				//TODO somehow indicate that the game is over, so the GUI can show the winner
			}
		}
		if(!waitingForAny){
			moveToNextPlayer();
		}
	}
	
	private void handlePlayerMove(PlayerEvent e){
		game.getBoard().place(e.getPlayer().getStoneColor(), e.getFromPoint(), e.getToPoint());
		e.getPlayer().setStoneAccount(e.getPlayer().getStoneAccount() - 1);
	}
	
	private void handleRowChoice(Row row){
		handleSingleRow(row);
		if(row.getPlayer().getStoneColor() == game.getBoard().WHITE_VALUE)
			waitingForWhite = false;
		else
			waitingForBlack = false;
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
				handlePlayerRows(game.getBoard().WHITE_VALUE, game.getGameLogic().removeOptions);
			}
			if(blackRowCount > 0 && !waitingForWhite){
				handlePlayerRows(game.getBoard().BLACK_VALUE, game.getGameLogic().removeOptions);
			}
		}
		
	}
	
	private void handleSingleRow(Row row){
		game.getBoard().removeRowAndExtensions(row);
		Player rowPlayer = row.getPlayer();
		rowPlayer.setStoneAccount(rowPlayer.getStoneAccount() + row.getLength());
		handleExtensions(row);
	}
	
	private void handlePlayerRows(int color, ArrayList<Row> possibleRows){
		ArrayList<Row> rows = rowsForPlayer(color, possibleRows);
		if(rows.size() == 1){
			handleSingleRow(rows.get(0));
		}else{
			Player p = checkPlayer(color);
			if(p.getStoneColor() == game.getBoard().WHITE_VALUE) waitingForWhite = true;
			else waitingForBlack = true;
			p.chooseRowToRemove(game, rows);
		}
	}
	
	private ArrayList<Row> rowsForPlayer(int color, ArrayList<Row> possibleRows) {
		ArrayList<Row> rowsForPlayer = new ArrayList<Row>();
		
		for(int x = 0; x < possibleRows.size(); x++){
			Row tmp = possibleRows.get(x);
			if(color == tmp.getWhiteExtensionStones() && tmp.getWhiteExtensionStones() >= 4)
				rowsForPlayer.add(new Row(tmp.getFromPoint(), tmp.getToPoint(), tmp.getPlayer(), tmp.getLength(), tmp.getWhiteExtensionStones(), tmp.getBlackExtensionStones()));

			if(color == tmp.getBlackExtensionStones() && tmp.getBlackExtensionStones() >= 4)
				rowsForPlayer.add(new Row(tmp.getFromPoint(), tmp.getToPoint(), tmp.getPlayer(), tmp.getLength(), tmp.getWhiteExtensionStones(), tmp.getBlackExtensionStones()));		
		}
		// rows only for white and rows only for black
		return rowsForPlayer;
	}
	
	private void handleExtensions(Row row){
		if(currentPlayer.getStoneColor() == game.getBoard().WHITE_VALUE){
			currentPlayer.setStoneAccount(currentPlayer.getStoneAccount() + row.getWhiteExtensionStones());
		}else{
			currentPlayer.setStoneAccount(currentPlayer.getStoneAccount() + row.getBlackExtensionStones());
		}
	}
}
