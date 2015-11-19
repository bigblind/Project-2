package com.gipf.client.utils;

import com.project.client.board.Board;

public class CenterEval  {
	
		
	public int evaluate(Board board,int whiteStones, int blackStones) {
		
		int [][] grid= board.getGrid();
		int white=0;
		int black=0;
		
		for(int x = 0; x < 9; x++) {
			for(int y = 0; y < 9; y++) {
				if (grid[x][y] == board.WHITE_VALUE)
					white = white + (4 - max(x,y)*10)+ (int)reserve(whiteStones);
				if (grid[x][y] == board.GIPF_WHITE_VALUE)
					white = white + (4 - max(x,y)*20)+ (int)reserve(whiteStones);
				if (grid[x][y] ==board.BLACK_VALUE)
					black = black+ -((4 - max(x,y)*10)+ (int)reserve(blackStones));
				if (grid[x][y] == board.GIPF_BLACK_VALUE)
					black = black+ -((4 - max(x,y)*20)+ (int)reserve(blackStones));
				else
					white+=0;
			}
		}
		return white+black;
	}
	
	private int max(int x, int y) {
		int a = Math.max(Math.abs(x-5), Math.abs(y-5));
		int max = Math.max(a, Math.abs(x-y));
		
		return max;
	}

	private double reserve(int stones){
		
		double reserveVal= stones*(280-Math.pow(stones-1,1/3)*20);
		
		return  reserveVal;
	}
}
