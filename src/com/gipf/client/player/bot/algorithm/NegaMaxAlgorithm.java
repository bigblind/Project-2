package com.gipf.client.player.bot.algorithm;

import java.util.ArrayList;
import java.util.List;

import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;

public class NegaMaxAlgorithm extends Algorithm {

	private static Node bestNode;
	
	public ArrayList<Action> calculateBestActions(Tree tree, Bot player) {
		negaMax(tree, tree.root(), true);
		return super.getActionsToNode(tree, bestNode);
	}

	private static final int negaMax(Tree tree, Node node, boolean maximizingPlayer) {
		if (tree.isExternal(node)) {
			return node.getValue();
		}
		
		int max = Integer.MIN_VALUE;
		Node bestChildSoFar = null;
		List<Node> children = node.getChildren();

		for (Node child : children) {
			int score = -negaMax(tree, child, !maximizingPlayer);
			if (score > max) {
				bestChildSoFar = child;
				max = score;
			}
		}
		bestNode = bestChildSoFar;
		return max;
	}
}
