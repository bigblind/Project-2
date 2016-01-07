package com.gipf.client.player.bot.algorithm;

import java.util.ArrayList;

import com.gipf.client.game.player.Player;
import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;
import com.gipf.client.offline.logic.Board;

public abstract class Algorithm {

	protected final Player WHITE_PLAYER = new Player(0, Board.WHITE_VALUE);
	protected final Player BLACK_PLAYER = new Player(0, Board.BLACK_VALUE);
	
	public abstract ArrayList<Action> calculateBestActions(Tree tree, Bot player);

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
}
