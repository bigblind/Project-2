package com.gipf.client.player.bot.algorithm;

import java.util.ArrayList;

import com.gipf.client.game.player.Player;
import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;
import com.gipf.client.offline.logic.Board;
import com.gipf.client.player.bot.generator.TreeGenerator;

public abstract class Algorithm {

	protected final Player WHITE_PLAYER = new Player(0, Board.WHITE_VALUE);
	protected final Player BLACK_PLAYER = new Player(0, Board.BLACK_VALUE);
	protected final int TREE_DEPTH = 3;
	protected final TreeGenerator generator = new TreeGenerator();
	
	public abstract ArrayList<Action> calculateBestActions(Tree tree, Bot player);

	public abstract ArrayList<Action> calculateBestActions(Node node, int depth, Bot player);
	
	public abstract Node calculateBestNode(Tree tree, Bot player);
	
	protected String name;
	
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
	
	protected Player opponent(Player player) {
		if (player.getStoneColor() == Board.WHITE_VALUE) return this.BLACK_PLAYER;
		else return this.WHITE_PLAYER;
	}
	
	public String toString() {
		return this.name;
	}
}
