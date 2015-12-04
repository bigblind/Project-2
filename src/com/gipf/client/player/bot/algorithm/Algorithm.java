package com.gipf.client.player.bot.algorithm;

import java.util.ArrayList;

import com.gipf.client.game.player.Player;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;

public abstract class Algorithm {

	public abstract ArrayList<Action> calculateBestActions(Tree tree, Player player);

	public ArrayList<Action> getActionsToNode(Tree tree, Node node) {
		ArrayList<Action> result = new ArrayList<Action>();
		while (!node.equals(tree.root())) {
			if (node.getEndState()) {
				result.clear();
				result.add(node.getAction());
			} else {
				result.add(node.getAction());
			}
			node = node.getParent();
		}
		ArrayList<Action> actualResult = new ArrayList<Action>();
		for (int i = result.size() - 1; i >= 0; i--) {
			actualResult.add(result.get(i));
		}
		return actualResult;
	}
}
