package com.project.logic;

import java.util.ArrayList;

public abstract class GameLogic implements PlayerListener {

	protected Game game;
	protected Player currentPlayer;
	protected ArrayList<Row> removeOptions = new ArrayList<Row>(); 
	
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
					prevValue = grid[i][j];
					if (counter >= 4) {
						lineEndX = i;
						lineEndY = j-1; //the current stone's color is different, so it doesn't count towards the row.
						lineStartX = i;
						lineStartY = j - counter;
						hasLine = true;
						//TODO implement a way to count the number of white and black stones that extend the row.
						int whiteExtensionStones = 0;
						int blackExtensionStones = 0;
						removeOptions.add(
								new Row(new Point(lineStartX, lineStartY),
										new Point(lineEndX, lineEndY),
										checkPlayer(prevValue),
										counter,
										whiteExtensionStones,
										blackExtensionStones
									)
								);
						counter = 1;
					}
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
					prevValue = grid[i][j];
					if (counter >= 4) {
						lineEndX = i-1;
						lineEndY = j;
						lineStartX = i - counter;
						lineStartY = j;
				
						hasLine = true;					
						//TODO implement a way to count the number of white and black stones that extend the row.
						int whiteExtensionStones = 0;
						int blackExtensionStones = 0;
						removeOptions.add(
								new Row(new Point(lineStartX, lineStartY),
										new Point(lineEndX, lineEndY),
										checkPlayer(prevValue),
										counter,
										whiteExtensionStones,
										blackExtensionStones
									)
								);
						counter = 1;
					}
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
					prevValue = grid[i][j + i];
					if (counter >= 4) {
						lineEndX = i-1;
						lineEndY = (j+1) + (i-1);
						lineStartX = i - counter;
						lineStartY = j - counter + i;
						
						hasLine = true;
						//TODO implement a way to count the number of white and black stones that extend the row.
						int whiteExtensionStones = 0;
						int blackExtensionStones = 0;
						removeOptions.add(
								new Row(new Point(lineStartX, lineStartY),
										new Point(lineEndX, lineEndY),
										checkPlayer(prevValue),
										counter,
										whiteExtensionStones,
										blackExtensionStones
									)
								);
						counter = 1;
					}
				}
			}
			counter = 0;
			prevValue = -1;
		}
		
		//The maximum row length you can create is 7, by pushing a stone between 2 rows of 3.
		for (int i = 1; i < 7; i++) {
			for (int j = 1; j < (9 - i) - 1; j++) {
				if (prevValue == grid[i + j][j] && prevValue > 0) counter++;
				else {
					prevValue = grid[i + j][j];
					if (counter == 4) {
						lineEndX = i + (j-1);
						lineEndY = j-1;
					lineStartX = i - counter + j;
					lineStartY = j - counter;
					
					hasLine = true;
					//TODO implement a way to count the number of white and black stones that extend the row.
					int whiteExtensionStones = 0;
					int blackExtensionStones = 0;
					removeOptions.add(
							new Row(new Point(lineStartX, lineStartY),
									new Point(lineEndX, lineEndY),
									checkPlayer(prevValue),
									counter,
									whiteExtensionStones,
									blackExtensionStones
								)
							);
					}
				}
			}
			counter = 0;
			prevValue = -1;
		}

//		System.out.println(new Point(lineStartX, lineStartY) + " " + new Point(lineEndX, lineEndY));
		return hasLine;
	}

	public void eventPerformed(PlayerEvent e) {
		
	}
	
	protected void moveToNextPlayer(){
		if(currentPlayer == game.getPlayerOne()){
			currentPlayer = game.getPlayerTwo();
		}else{
			currentPlayer = game.getPlayerOne();
		}
	}
	
	
	protected Player checkPlayer(int stoneColor){
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
	
	
	
}