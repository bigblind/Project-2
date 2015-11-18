package com.project.game.ai.minimax;

import java.util.ArrayList;

import javax.xml.soap.Node;

import com.project.client.board.Board;
import com.project.common.player.PlayerEvent;
import com.project.common.utils.Point;
import com.project.server.logic.Game;

public class MiniMax {
	private Game game;
	
	public MiniMax(Game game){
		this.game = game;
	}
	
	
	public PlayerEvent miniMax(ArrayList<Node> tree, int depth, boolean onTurn){
		int bestValue;
		PlayerEvent bestMove;
		
		if(depth == 0 || game.getGameLogic().checkForWin())
			return null;
		
		if(onTurn == true){
			bestValue = Integer.MIN_VALUE;
			bestMove = null;
		
			for(Node node: tree){
				PlayerEvent tmpEvent = miniMax(tree.breadthFirstSearch(node), depth-1, false);
				if(node.getValue() > bestValue){
					bestValue = node.getValue();
					bestMove = tmpEvent;
				}
			}
			return bestMove;
		}
		
		if(onTurn == false){
			bestValue = Integer.MAX_VALUE;
			bestMove = null;
			
			for(Node node: tree){
				PlayerEvent tmpEvent = miniMax(tree.breadthFirstSerach(node), depth-1, true);
				if(node.getValue() < bestMove){
					bestValue = node.getValue();
					bestMove = tmpEvent;
				}
			}
			return bestMove;
		};
		
	}
	
	
	
	
	
	

}
