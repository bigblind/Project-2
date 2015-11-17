package com.gipf.client.utils;

import com.project.client.board.Board;

public class CenterEval  {
	
	Board board;
	int [][] grid = board.getGrid();
	
	public CenterEval(Board board) {
		this.board= board;
	}
	
	public int evaluate(int x, int y,int whiteStones, int blackStones) {
		int white=0;
		int black=0;
		
		if (grid[x][y] == board.WHITE_VALUE)
			white = (4 - max(x,y)*10)+ (int)reserve(whiteStones);
		else
			black = (4 - max(x,y)*10)+ (int)reserve(blackStones);
		
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
