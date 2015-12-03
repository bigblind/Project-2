package com.project.game.ai.minimax;

import java.util.ArrayList;

import com.gipf.client.game.Game;
import com.project.game.ai.node.Node;
import com.project.game.ai.tree.Tree;

public class IterativeAlphaBeta extends AlphaBeta2{

	public IterativeAlphaBeta(Game game) {
		super(game);
	}

	
	public Node iterate(int depth){
		Node result = tree.root();
		
		for(int x = depth; x > 0; x--){
			result = this.alphaBeta(result, x);
		}
		
		return result;
	}
	

	
}
