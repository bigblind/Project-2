package com.project.game.ai.tree;

import java.util.ArrayList;

import com.gipf.client.game.Game;
import com.gipf.client.game.player.Player;
import com.gipf.client.game.player.PlayerEvent;
import com.gipf.client.utils.Point;
import com.project.game.ai.evaluation.EvaluationFunction;
import com.project.game.ai.node.Node;
import com.project.server.logic.board.Board;

public class MoveGenerator {

	Board board;
	Tree<Integer> tree;
	Game game;
	
	public MoveGenerator(Game game) {
		this.game = game;
		this.tree = new Tree<Integer>(new Node<Integer>(board));
	}

	
	public Tree<Integer> generateMove(Board board, int ply, Player player) {
		this.board = board;
		this.tree.root().addChildren(generateMove(ply, this.tree.root(), player));
		return this.tree;
	}

	private ArrayList<Node<Integer>> generateMove(int ply, Node<Integer> parent, Player player){
		Player nextPlayer;
		if (player == game.getPlayerOne()) 
			nextPlayer = game.getPlayerTwo();
		else nextPlayer = game.getPlayerOne();
			
		
		ArrayList<Node<Integer>> result = new ArrayList<Node<Integer>>();
		if(ply > 0){
			Point from;
			Point to1;
			Point to2;
			
			//correct, bottom right
			for (int i = 5; i < 8; i++){
				from = new Point(i,8);
				to1 = new Point(i-1, 7);
				to2 = new Point(i,7);
				if(board.isValidMove(from, to1))
					result.add(addMove(from, to1, parent, player, nextPlayer, ply-1));
			
				if(board.isValidMove(from, to2))
					result.add(addMove(from, to2, parent, player, nextPlayer, ply-1));
			}
			// correct, right
			for (int j = 7; j >= 5; j--){
				from = new Point(8,j);
				to1 = new Point(7,j-1);
				to2 = new Point(7,j);
				if(board.isValidMove(from, to1))
					result.add(addMove(from, to1, parent, player, nextPlayer, ply-1));
			
				if(board.isValidMove(from, to2))
					result.add(addMove(from, to2, parent, player, nextPlayer, ply-1));
			}

			// correct, top left
			for (int i = 1; i < 4; i++){
				from = new Point(i,0);
				to1 = new Point(i,1);
				to2 = new Point(i+1,1);
				if(board.isValidMove(from, to1))
					result.add(addMove(from, to1, parent, player, nextPlayer, ply-1));
			
				if(board.isValidMove(from, to2))
					result.add(addMove(from, to2, parent, player, nextPlayer, ply-1));
			}

			// correct, left
			for (int j = 1; j < 4; j++){
				from = new Point(0,j);
				to1 = new Point(1,j);
				to2 = new Point(1,j+1);
				if(board.isValidMove(from, to1))
					result.add(addMove(from, to1, parent, player, nextPlayer, ply-1));
			
				if(board.isValidMove(from, to2))
					result.add(addMove(from, to2, parent, player, nextPlayer, ply-1));
			}
			
			// top right
			for(int i = 5; i < 8; i++)
				for(int j = 1; j < 4; j++){
					from = new Point(i,j);
					to1 = new Point(i-1,j);
					to2 = new Point(i,j+1);
					if(board.isValidMove(from, to1))
						result.add(addMove(from, to1, parent, player, nextPlayer, ply-1));
				
					if(board.isValidMove(from, to2))
						result.add(addMove(from, to2, parent, player, nextPlayer, ply-1));
				}
			
			// bottom left
			for(int i = 1; i < 4; i++)
				for(int j = 5; j < 8; j++){
					from = new Point(i,j);
					to1 = new Point(i-1,j);
					to2 = new Point(1,j+1);
					if(board.isValidMove(from, to1))
						result.add(addMove(from, to1, parent, player, nextPlayer, ply-1));
				
					if(board.isValidMove(from, to2))
						result.add(addMove(from, to2, parent, player, nextPlayer, ply-1));
				}
			
			from = new Point(8,4);
			to1 = new Point(7,4);
			if(board.isValidMove(from, to1))
				result.add(addMove(from, to1, parent, player, nextPlayer, ply-1));
			
			from = new Point(8,8);
			to1 = new Point(7,7);
			if(board.isValidMove(from, to1))
				result.add(addMove(from, to1, parent, player, nextPlayer, ply-1));
			
			from = new Point(4,8);
			to1 = new Point(4,7);
			if(board.isValidMove(from, to1))
				result.add(addMove(from, to1, parent, player, nextPlayer, ply-1));
			
			from = new Point(0,4);
			to1 = new Point(1,4);
			if(board.isValidMove(from, to1))
				result.add(addMove(from, to1, parent, player, nextPlayer, ply-1));
			
			from = new Point(0,0);
			to1 = new Point(1,1);
			if(board.isValidMove(from, to1))
				result.add(addMove(from, to1, parent, player, nextPlayer, ply-1));
			
			from = new Point(4,0);
			to1 = new Point(4,1);
			if(board.isValidMove(from, to1))
				result.add(addMove(from, to1, parent, player, nextPlayer, ply-1));
			
		}
		return result;
	}

	private Node<Integer> addMove(Point from, Point to, Node<Integer> parent, Player player, Player nextPlayer, int ply){
			Board tmp = board.copy();
			tmp.push(from, to);
			Node<Integer> node = new Node<Integer>(new EvaluationFunction().evaluate(tmp, game.getPlayerOne().getStoneAccount(), game.getPlayerTwo().getStoneAccount()), new PlayerEvent(from, to, player), tmp);
			node.setParent(parent);
			Tree<Integer> result = new Tree<Integer>(node);
			node.addChildren(generateMove(ply, parent, nextPlayer));
			return node;
	}
}
