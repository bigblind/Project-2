package com.project.logic;

import java.util.ArrayList;

public abstract class GameLogic implements PlayerListener {

	protected Game game;
	protected Player currentPlayer;
	protected ArrayList<PlayerEvent> removeOptions = new ArrayList<PlayerEvent>(); 
	
	public GameLogic(Game game) {
		this.game = game;
	}

	public abstract void loop();

	public boolean checkForLines() {
		boolean hasLine = false;
		int[][] grid = game.getBoard().getGrid();

		int lineStartX = -1, lineStartY = -1, lineEndX = -1, lineEndY = -1;

		int counter = -1;
		int prevValue = -1;

		// checks for vertical lines
		for (int i = 1; i < grid.length - 1; i++) {
			for (int j = 1; j < grid[i].length - 1; j++) {
				if (prevValue == grid[i][j] && prevValue > 0) counter++;
				else {
					counter = 1;
					prevValue = grid[i][j];
				}
				if (counter == 4) {
					lineEndX = i;
					lineEndY = j;
					lineStartX = i;
					lineStartY = j - 3;
					hasLine = true;
					removeOptions.add(new PlayerEvent(new Point(lineStartX, lineStartY), new Point(lineEndX, lineEndY), checkPlayer(prevValue)));
				}
			}
			counter = 0;
			prevValue = -1;
		}

		// checks for left down right up lines
		for (int j = 0; j < grid[0].length; j++) {
			for (int i = 0; i < grid.length; i++) {
				if (prevValue == grid[i][j] && prevValue > 0) counter++;
				else {
					counter = 1;
					prevValue = grid[i][j];
				}
				if (counter == 4) {
					lineEndX = i;
					lineEndY = j;
					lineStartX = i - 3;
					lineStartY = j;
				
					hasLine = true;					
					removeOptions.add(new PlayerEvent(new Point(lineStartX, lineStartY), new Point(lineEndX, lineEndY), checkPlayer(prevValue)));
				}
			}
			counter = 1;
			prevValue = -1;
		}

		// checks for lines left top right bottom
		for (int j = 3; j >= 0; j--) {
			for (int i = 1; i < (9 - j) - 1; i++) {
				if (prevValue == grid[i][j + i] && prevValue > 0) counter++;
				else {
					counter = 1;
					prevValue = grid[i][j + i];
				}
				if (counter == 4) {
					lineEndX = i;
					lineEndY = j + i;
					lineStartX = i - 3;
					lineStartY = j - 3 + i;

					hasLine = true;
					removeOptions.add(new PlayerEvent(new Point(lineStartX, lineStartY), new Point(lineEndX, lineEndY), checkPlayer(prevValue)));
				}
			}
			counter = 0;
			prevValue = -1;
		}

		for (int i = 1; i < 4; i++) {
			for (int j = 1; j < (9 - i) - 1; j++) {
				if (prevValue == grid[i + j][j] && prevValue > 0) counter++;
				else {
					counter = 1;
					prevValue = grid[i + j][j];
				}
				if (counter == 4) {
					lineEndX = i + j;
					lineEndY = j;
					lineStartX = i - 3 + j;
					lineStartY = j - 3;
					
					hasLine = true;
					removeOptions.add(new PlayerEvent(new Point(lineStartX, lineStartY), new Point(lineEndX, lineEndY), checkPlayer(prevValue)));
				}
			}
			counter = 0;
			prevValue = -1;
		}

//		System.out.println(new Point(lineStartX, lineStartY) + " " + new Point(lineEndX, lineEndY));
		return hasLine;
	}

	public void eventPerformed(PlayerEvent e) {
		game.getBoard().place(e.getPlayer().getStoneColor(), e.getFromPoint(), e.getToPoint());
	}
	
	private void moveToNextPlayer(){
		if(currentPlayer == game.getPlayerOne()){
			currentPlayer = game.getPlayerTwo();
		}else{
			currentPlayer = game.getPlayerOne();
		}
	}
	
	
	private Player checkPlayer(int stoneColor){
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
	
	
	private boolean checkForWin(){
		if(game.getPlayerOne().getStoneAccount() == 0 || game.getPlayerTwo().getStoneAccount() == 0)
			return true;
		
		return false;
	}
	
	
	
}