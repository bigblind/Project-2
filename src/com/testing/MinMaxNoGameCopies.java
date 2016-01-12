package com.testing;

import java.util.ArrayList;

import com.gipf.client.game.player.Player;
import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;

public class MinMaxNoGameCopies {

 	// generate tree while we go
	// no copies
	// take care of what player ( evaluation - )
	// end state thing
	public MinMaxNoGameCopies() {
	}
	
	public Action calculateBestAction(Node node, Bot player) {

	}
		
	public Node max(Tree tree, Node node, Player player) {
		if (tree.isExternal(node)) return node;
		else {
			ArrayList<Node> candidateList = new ArrayList<Node>();
			for (Node child : node.getChildren()) {
				candidateList.add(min(tree, child, player));
			}
			
			Node maxNode = candidateList.get(0);
			for (Node candidates : candidateList) {
				if (candidates.getValue() > maxNode.getValue()) maxNode = candidates;
			}
			
			return maxNode;
		}
	}
	
	public Node min(Tree tree, Node node, Player player) {
		if (tree.isExternal(node)) return node;
		else {
			
			ArrayList<Node> candidateList = new ArrayList<Node>();
			for (Node child : node.getChildren()) {
				candidateList.add(max(tree, child, player));
			}
			
			
			Node minNode = candidateList.get(0);
			for (Node candidates : candidateList) {
				if (candidates.getValue() < minNode.getValue()) minNode = candidates;
			}
			
			return minNode;
		}
	}
	
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
