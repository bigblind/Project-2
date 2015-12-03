
package com.project.game.ai.minimax;

import java.util.ArrayList;
import java.util.ListIterator;

import com.project.game.ai.node.Node;
import com.project.game.ai.tree.Tree;
import com.gipf.client.game.Game;

public class AlphaBeta2 extends MiniMax {

	public AlphaBeta2(Game game){
		super(game, new Tree(new Node()));	
	}

	
	public Node alphaBeta(int depth) {
		Node alpha = new Node(Integer.MIN_VALUE);
		Node beta = new Node(Integer.MAX_VALUE);
		return minValue(tree.root(), alpha, beta, depth);
	}
	
	
	public Node alphaBeta(Node node, int depth) {
		Node alpha = new Node(Integer.MIN_VALUE);
		Node beta = new Node(Integer.MAX_VALUE);
		this.tree = new Tree(node);
		return minValue(tree.root(), alpha, beta, depth);
	}

	
	private Node maxValue(Node node, Node alpha, Node beta, int depth) {
		if (depth == 0 || (tree.isExternal(node) && !node.isRoot()))
			return node;
		
		ArrayList<Node> newChildren = generateMove(node);
		node.addChildren(newChildren);

		ListIterator<Node> children = tree.children(node);
		Node child;
		Node currAlpha = alpha;
		Node possibleAlpha;
		

		while (children.hasNext()) {
			child = (Node) children.next();
			// alpha = Math.max(alpha.element(), minValue(child, currAlpha,
			// beta, depth-1).element());
			possibleAlpha = minValue(child, currAlpha, beta, depth - 1);

			if (alpha.element() < possibleAlpha.element())
				alpha = possibleAlpha;

			if (alpha.element() >= beta.element())
				return beta;
		}
		return alpha;
	}

	
	private Node minValue(Node node, Node alpha, Node beta, int depth) {
		if (depth == 0 || (tree.isExternal(node) && !node.isRoot()))
			return node;
		
		ArrayList<Node> newChildren = generateMove(node);
		node.addChildren(newChildren);

		ListIterator<Node> children = tree.children(node);
		Node child;
		Node currBeta = beta;
		Node possibleBeta;

		while (children.hasNext()) {
			child = (Node) children.next();
			// beta = Math.min(beta.element(), maxValue(child, alpha,
			// currBeta, depth-1).element());

			possibleBeta = maxValue(child, alpha, currBeta, depth - 1);

			if (beta.element() > possibleBeta.element())
				beta = possibleBeta;

			if (beta.element() <= alpha.element())
				return alpha;
		}
		return beta;
	}
}
