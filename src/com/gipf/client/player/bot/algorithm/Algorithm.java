package com.gipf.client.player.bot.algorithm;

import java.util.ArrayList;

import com.gipf.client.game.player.Player;
import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.offline.logic.Board;
import com.gipf.client.offline.logic.Game;
import com.gipf.client.player.bot.evaluation.EvaluationFunction;
import com.gipf.client.player.bot.generator.ActionGenerator;

public abstract class Algorithm extends Thread {

	protected final Player WHITE_PLAYER = new Player(0, Board.WHITE_VALUE);
	protected final Player BLACK_PLAYER = new Player(0, Board.BLACK_VALUE);

	protected final int TREE_DEPTH = 8;

	protected String name;

	protected ActionGenerator generator;

	public abstract ArrayList<Action> calculateBestActions(Game game, Bot player, EvaluationFunction evaluator);

	public abstract Node calculateBestNode(Game game, Bot player, EvaluationFunction evaluator);

	public Algorithm() {
		this.generator = new ActionGenerator();
	}

	public ArrayList<Action> getActionsToNode(Node node) {
		ArrayList<Action> result = new ArrayList<Action>();
		while (node.getParent() != null) {
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

	protected Node performAction(Action action, Game game, Bot player, EvaluationFunction evaluator) {
		Node child = new Node(null, game, action, false);
		child.setAction(action);
		child.getGame().getBoard().place(player.getStoneColor(), action.getPoints()[0], action.getPoints()[1]);
		if (player.getStoneColor() == Board.WHITE_VALUE) child.getGame().getPlayerOne().addStones(-1);
		else child.getGame().getPlayerTwo().addStones(-1);
		player.getLogic().performLogic(player, child);
		for (Node bareBottom : child.bottomChildren()) {
//			if (child.bottomChildren().size() > 1) {
//				child.getGame().getBoard().print();
//				System.out.println();
//			}
			bareBottom.setValue(evaluator.evaluate(bareBottom.getGame(), player));
//			System.out.println("in performAction: " + bareBottom.getGame().getPlayerOne().getStoneAccount() + " " + bareBottom.getGame().getPlayerTwo().getStoneAccount());
		}
		return child;
	}

	protected int opponentStoneColor(int stoneColor) {
		if (stoneColor == Board.WHITE_VALUE) return Board.BLACK_VALUE;
		else return Board.WHITE_VALUE;
	}

	protected Player getOpponent(Player player, Game game) {
		if (player.getStoneColor() == Board.WHITE_VALUE) {
			return game.getPlayerTwo();
		}
		return game.getPlayerOne();
	}

	public String toString() {
		return this.name;
	}
}
