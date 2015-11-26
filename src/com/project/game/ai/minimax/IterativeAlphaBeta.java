package com.project.game.ai.minimax;

import java.util.ArrayList;

import com.gipf.client.game.Game;
import com.project.game.ai.node.Node;
import com.project.game.ai.tree.Tree;

public class IterativeAlphaBeta<E> extends AlphaBeta<E>{

	public IterativeAlphaBeta(Game game, Tree<E> tree) {
		super(game, tree);
	}

	
	public Node<E> iterate(int depth){
		Node<E> result = tree.root();
		
		for(int x = depth; x > 0; x--){
			result = this.alphaBeta(result, x);
		}
		
		return result;
	}
	
	
	private Node<E> alphaBeta(Node<E> node, int depth){
		Node<E> alpha = new Node<E>(Integer.MIN_VALUE);
		Node<E> beta = new Node<E>(Integer.MAX_VALUE);

		return minValue(node, alpha, beta, depth);
	}
	
}
