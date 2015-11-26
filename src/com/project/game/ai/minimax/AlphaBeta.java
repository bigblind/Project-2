
package com.project.game.ai.minimax;

import java.util.ArrayList;
import java.util.ListIterator;

import com.project.game.ai.tree.Tree;
import com.gipf.client.game.Game;
import com.project.game.ai.node.Node;

public class AlphaBeta<E> extends MiniMax<E> {

	public AlphaBeta(Game game, Tree<E> tree) {
		super(game, tree);
	}

	
	public Node<E> alphaBeta(ArrayList<Node<E>> nodeList, int depth) {
		Node<E> alpha = new Node<E>(Integer.MIN_VALUE);
		Node<E> beta = new Node<E>(Integer.MAX_VALUE);

		return minValue(tree.root(), alpha, beta, depth);
	}

	
	public Node<E> maxValue(Node<E> node, Node<E> alpha, Node<E> beta, int depth) {
		if (depth == 0 || tree.isExternal(node))
			return node;

		ListIterator<Node<E>> children = tree.children(node);
		Node<E> child;
		Node<E> currAlpha = alpha;
		Node<E> possibleAlpha;

		while (children.hasNext()) {
			child = (Node<E>) children.next();
			// alpha = Math.max(alpha.getEvalValue(), minValue(child, currAlpha,
			// beta, depth-1).getEvalValue());
			possibleAlpha = minValue(child, currAlpha, beta, depth - 1);

			if (alpha.getEvalValue() < possibleAlpha.getEvalValue())
				alpha = possibleAlpha;

			if (alpha.getEvalValue() >= beta.getEvalValue())
				return beta;
		}
		return alpha;
	}

	
	public Node<E> minValue(Node<E> node, Node<E> alpha, Node<E> beta, int depth) {
		if (depth == 0 || tree.isExternal(node))
			return node;

		ListIterator<Node<E>> children = tree.children(node);
		Node<E> child;
		Node<E> currBeta = beta;
		Node<E> possibleBeta;

		while (children.hasNext()) {
			child = (Node<E>) children.next();
			// beta = Math.min(beta.getEvalValue(), maxValue(child, alpha,
			// currBeta, depth-1).getEvalValue());

			possibleBeta = maxValue(child, alpha, currBeta, depth - 1);

			if (beta.getEvalValue() > possibleBeta.getEvalValue())
				beta = possibleBeta;

			if (beta.getEvalValue() <= alpha.getEvalValue())
				return alpha;
		}
		return beta;
	}

	// public Node<E> alphaBeta(ArrayList<Node<E>> nodeList, int depth, int
	// alpha, int beta){
	//// if(depth == 0 || game.getGameLogic().checkForWin()){
	//// return null;
	//// }
	////
	//// int bestValue = Integer.MIN_VALUE;
	//// Node<E> bestNode = null;
	////
	//// for(Node<E> node: nodeList){
	//// Node<E> tmpNode = alphaBeta(tree.bfSearch(node), depth-1, -alpha,
	// -beta);
	//// if(bestValue < tmpNode.getEvalValue()){
	//// bestValue = tmpNode.getEvalValue();
	//// bestNode = tmpNode;
	//// }
	////
	//// if(tmpNode.getEvalValue() > alpha)
	//// alpha = tmpNode.getEvalValue();
	////
	//// if(tmpNode.getEvalValue() >= beta)
	//// break;
	//// }
	//// return bestNode;
	//
	// return null;
	//
	// }

}
