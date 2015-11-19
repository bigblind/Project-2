package com.project.game.ai.tree;

import com.project.common.player.PlayerEvent;
import com.project.common.utils.Point;
import com.project.game.ai.node.Node;
import com.project.server.logic.board.Board;

public class MoveGenerator {
	
	Board board;
	Tree<Integer> tree;
	
	public MoveGenerator(Board board){
		this.board = board;
		this.tree = new Tree<Integer>(new Node<Integer>(board));
	}
	

	
	public Tree<Integer> generateMove(int ply){
		if(ply > 0){
			Point from;
			Point to1;
			Point to2;
			PlayerEvent event;
			for (int i = 4; i < 9; i++){
				from = new Point(i,8);
				to1 = new Point(i-1,8);
				to2 = new Point(i,8+1);
			
				if(board.isValidMove(from, to1)){
					Board tmp = board.copy();
					tmp.push(from, to1);
					if(ply%2!=0){
						Node<Integer> node = new Node<Integer>(new Evaluation(tmp), new PlayerEvent(from, to1, ai));
					}
						
				}
				if(board.isValidMove(from, to2)){
					board.copy().push(from, to2);
				}
				
			}



			for (int j = 8; j >= 4; j--)
				this.board.getGrid()[8][j] = Board.BOARD_EDGE;

			for (int i = 0; i < 5; i++)
				this.board.getGrid()[i][0] = Board.BOARD_EDGE;

			for (int j = 0; j < 5; j++)
				this.board.getGrid()[0][j] = Board.BOARD_EDGE;
			
			
//			this.board.getGrid()[5][1];
//			this.board.getGrid()[6][2];
//			this.board.getGrid()[7][3];
//
//			this.board.getGrid()[1][5];
//			this.board.getGrid()[2][6];
//			this.board.getGrid()[3][7];
		}
		
		return tree;
	}
	
	

}
