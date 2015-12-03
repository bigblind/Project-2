package com.project.game.ai.minimax;

import java.util.ArrayList;

import com.gipf.client.game.Game;
import com.project.client.board.Board;

import com.project.game.ai.node.Node;
import com.project.game.ai.tree.Tree;


public class MiniMax2 {
	protected Game game;
	protected Tree tree;
	
	public MiniMax2(Game game){
		this.game = game;
		this.tree = new Tree(new Node());
	}
	
	
	public Node miniMax(Node node, int depth, boolean turnOfPlayer){
		return pMiniMax(tree.root(), depth, turnOfPlayer);
	}
	
	
	private Node pMiniMax(Node node, int depth, boolean turnOfPlayer){
		if(depth == 0 || game.getGameLogic().checkForWin())
			return node;
		
		ArrayList<Node> children = generateMove(node);
		
		int bestValue;
		Node bestNode;
		
		if(turnOfPlayer == true){
			bestValue = Integer.MIN_VALUE;
//			bestMove = null;
			bestNode = null;
			for(Node child: children){				
				Node tmpNode = miniMax(child, depth-1, false);
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
			for(Node child: children){
//				PlayerEvent tmpEvent = miniMax(tree.bfSearch(node), depth-1, true);
				Node tmpNode = miniMax(child, depth-1, true);
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
