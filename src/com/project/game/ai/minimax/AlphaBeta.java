package com.project.game.ai.minimax;

import java.util.ArrayList;

import com.gipf.client.offline.logic.Game;
import com.project.game.ai.node.Node;
import com.project.game.ai.tree.Tree;

public class AlphaBeta<E> extends MiniMax<E> {

	public AlphaBeta(Game game, Tree<E> tree) {
		super(game, tree);
	}

	public Node<E> alphaBeta(ArrayList<Node<E>> nodeList, int depth, int alpha, int beta) {
		if (depth == 0 || game.getGameLogic().checkForWin()) {
			return null;
		}

		int bestValue = Integer.MIN_VALUE;
		Node<E> bestNode = null;

		for (Node<E> node : nodeList) {
			Node<E> tmpNode = alphaBeta(tree.bfSearch(node), depth - 1, -alpha, -beta);
			if (bestValue < tmpNode.getValue()) {
				bestValue = tmpNode.getValue();
				bestNode = tmpNode;
			}

			if (tmpNode.getValue() > alpha) alpha = tmpNode.getValue();

			if (tmpNode.getValue() >= beta) break;
		}
		return bestNode;
	}

}
