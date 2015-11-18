package com.project.game.ai.minimax;

import java.util.ArrayList;


import com.project.client.board.Board;
import com.project.common.player.PlayerEvent;
import com.project.common.utils.Point;
import com.project.game.ai.node.*;
import com.project.game.ai.tree.*;
import com.project.server.logic.Game;

public class MiniMax<E> {
	private Game game;
	private Tree<E> tree;
	
	public MiniMax(Game game, Tree<E> tree){
		this.game = game;
		this.tree = tree;
	}
	
	
	public PlayerEvent miniMax(ArrayList<Node<E>> tree, int depth, boolean onTurn){
		int bestValue;
		PlayerEvent bestMove;
		
		if(depth == 0 || game.getGameLogic().checkForWin())
			return null;
		
		if(onTurn == true){
			bestValue = Integer.MIN_VALUE;
			bestMove = null;
			for(Node<E> node: tree){
				PlayerEvent tmpEvent = miniMax(tree.breadthFirstSearch(node), depth-1, false);
				if(node.getEvalValue() > bestValue){
					bestValue = node.getEvalValue();
					bestMove = tmpEvent;
				}
			}
			return bestMove;
		}
		
		if(onTurn == false){
			bestValue = Integer.MAX_VALUE;
			bestMove = null;
			
			for(Node<E> node: tree){
				PlayerEvent tmpEvent = miniMax(((Tree<E>) tree.breadthFirstSearch(node), depth-1, true);
				if(node.getEvalValue() < bestValue){
					bestValue = node.getEvalValue();
					bestMove = tmpEvent;
				}
			}
			return bestMove;
		};
		
	}
	
	
	
	
	
	

}
