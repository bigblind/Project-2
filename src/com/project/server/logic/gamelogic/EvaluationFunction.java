package com.project.server.logic.gamelogic;


import com.project.client.board.Board;

public class EvaluationFunction {
    private Board board;
    private int blackStoneCnt;
    private int whiteStoneCnt;
    public static  int CENTER_WEIGHT = 100;
    public static  int STONECOUNT_WEIGHT = 200;
    public static  int DIAGONAL_WEIGHT = 80;
    public EvaluationFunction(Board board, int blackStoneCnt, int whiteStoneCnt){
	this.board = board;
	this.blackStoneCnt = blackStoneCnt;
	this.whiteStoneCnt = whiteStoneCnt;
    }
    public int evaluate(){
	int boardValue = 0;

	boardValue =  STONECOUNT_WEIGHT * (whiteStoneCnt - blackStoneCnt) + CENTER_WEIGHT * centerStones() + DIAGONAL_WEIGHT * diagonal(); 
	
		 return boardValue;
	
    }
    
    private int centerStones(){
	int extBonus = 30;
	int mediumBonus = 50;
	int centerBonus = 100;
	int centerValue = 0;
	int[][] externalCoor = new int[][]{{2,2}, {3,2},{4,2}, {2,3},{2,4},{3,5},{4,6},
	    				   {5,6},{6,6},{6,5},{6,4},{5,3}};
	int[][] mediumCoor = new int[][]{{3,3}, {3,4},{4,5}, {5,5},{5,4},{4,3}};
	for(int i = 0; i < 12; i++){
	    int x = externalCoor[i][0];
	    int y = externalCoor[i][1];
	    
		if(board.getGrid()[x][y] == Board.WHITE_VALUE)
		    centerValue += extBonus;
		else if (board.getGrid()[x][y] == Board.BLACK_VALUE)
		    centerValue -= extBonus;
		 
	    
	    
	}
	for(int i = 0; i < 6; i++){
	    int x = mediumCoor[i][0];
	    int y = mediumCoor[i][1];
	    if(board.getGrid()[x][y] == Board.WHITE_VALUE)
		    centerValue += mediumBonus;
		else if (board.getGrid()[x][y] == Board.BLACK_VALUE)
		    centerValue -= mediumBonus;
	    
	}
	if(board.getGrid()[4][4] == Board.WHITE_VALUE)
	    centerValue += centerBonus;
	else if(board.getGrid()[4][4] == Board.BLACK_VALUE)
	    centerValue -= centerBonus;
	
	return centerValue;
    }
    
    private int diagonal(){
	int diagonalValue = 0;
	int diagonalBonus = 30;
	int cnt = 1;
	
	for(int i = 1; i < 8; i++){
	    if(board.getGrid()[i][4] == Board.WHITE_VALUE)
		diagonalValue += diagonalBonus;
	    else if(board.getGrid()[i][4] == Board.BLACK_VALUE)
		diagonalValue -= diagonalBonus;
	}
	for(int i = 1; i < 7; i++){
	    if(board.getGrid()[4][i] == Board.WHITE_VALUE)
		diagonalValue += diagonalBonus;
	    else if(board.getGrid()[4][i] == Board.BLACK_VALUE)
		diagonalValue -= diagonalBonus;
	}
	for(int i = 1; i < 7; i++){
	    if(board.getGrid()[i][cnt] == Board.WHITE_VALUE)
		diagonalValue += diagonalBonus;
	    else if(board.getGrid()[i][cnt] == Board.BLACK_VALUE)
		diagonalValue -= diagonalBonus;
	    cnt++;
	}
	return diagonalValue;
    }
    
}
