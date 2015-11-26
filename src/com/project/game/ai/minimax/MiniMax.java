package com.project.game.ai.minimax;

import java.util.ArrayList;

import com.gipf.client.game.Game;
import com.project.client.board.Board;

import com.project.game.ai.node.Node;
import com.project.game.ai.tree.Tree;


public class MiniMax {
	protected Game game;
	protected Tree tree;
	
	public MiniMax(Game game, Tree tree){
		this.game = game;
		this.tree = tree;
	}
	
	
	public Node miniMax(ArrayList<Node> nodeList, int depth, boolean turnOfPlayer){
		int bestValue;
//		PlayerEvent bestMove;
		Node bestNode;
		
		if(depth == 0 || game.getGameLogic().checkForWin())
			return null;
		
		if(turnOfPlayer == true){
			bestValue = Integer.MIN_VALUE;
//			bestMove = null;
			bestNode = null;
			for(Node node: nodeList){				
				Node tmpNode = miniMax(node.getChildren(), depth-1, false);
//				Node tmpNode = miniMax(tree.bfSearch(node), depth-1, false);
				if(node.element() > bestValue){
					bestValue = node.element();
					bestNode = tmpNode;
				}
			}
//			return bestMove;
			return bestNode;
		}
		
		if(turnOfPlayer == false){
			bestValue = Integer.MAX_VALUE;
//			bestMove = null;
			bestNode = null;
			for(Node node: nodeList){
//				PlayerEvent tmpEvent = miniMax(tree.bfSearch(node), depth-1, true);
				Node tmpNode = miniMax(node.getChildren(), depth-1, true);
//				Node tmpNode = miniMax(tree.bfSearch(node), depth-1, true);
				if(node.element() < bestValue){
					bestValue = node.element();
//					bestMove = node.getPlayerEvent();
					bestNode = tmpNode;
				}
			}
//			return bestMove;
			return bestNode;
		};
		return null;
	}
}
