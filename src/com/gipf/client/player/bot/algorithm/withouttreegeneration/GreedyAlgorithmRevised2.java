package com.gipf.client.player.bot.algorithm.withouttreegeneration;

import java.util.ArrayList;

import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;
import com.gipf.client.offline.logic.Board;
import com.gipf.client.offline.logic.Game;
import com.gipf.client.player.bot.algorithm.Algorithm;
import com.gipf.client.player.bot.evaluation.EvaluationFunction;
import com.gipf.client.player.bot.evaluation.EvaluationFunctionC;

public class GreedyAlgorithmRevised2 extends Algorithm {

	public GreedyAlgorithmRevised2() {
		super();
		this.name = "Greedy Algorithm Revised P";
	}

	public ArrayList<Action> calculateBestActions(Game game, Bot player, EvaluationFunction evaluator) {
		Game untouched = game.copy();
		Game use = game.copy();

		Node root = new Node(null, game, null, true);
		root.setPossibleActions(generator.getPossibleActions(root));

		Node currentOptimal = null;
		boolean care = false;
		for (Action action : root.getPossibleActions()) {
			Node child = new Node(root, use, action, false);
			child.setAction(action);
			root.addChild(child);
			child.getGame().getBoard().place(player.getStoneColor(), action.getPoints()[0], action.getPoints()[1]);
			if (player.getStoneColor() == Board.WHITE_VALUE) child.getGame().getPlayerOne().addStones(-1);
			else child.getGame().getPlayerTwo().addStones(-1);
			player.getLogic().performLogic(player, child);
			child.setValue(evaluator.evaluate(child.getGame().getBoard(), child.getGame().getPlayerOne().getStoneAccount(), child.getGame().getPlayerTwo().getStoneAccount(), true)); //TODO this boolean is for if it is standard

			if (currentOptimal == null) {
				currentOptimal = child;
				care = true;
			} else if (player.getStoneColor() == Board.WHITE_VALUE && child.getValue() > currentOptimal.getValue()) {
				currentOptimal = child;
				care = true;
			} else if (player.getStoneColor() == Board.BLACK_VALUE && -child.getValue() > -currentOptimal.getValue()) {
				currentOptimal = child;
				care = true;
			}

			if (!care) {
				root.removeChild(child);
				child = null;
			}

			use.getBoard().setGrid(untouched.getBoard().getGrid());
			use.getPlayerOne().setStoneAccount(untouched.getPlayerOne().getStoneAccount());
			use.getPlayerTwo().setStoneAccount(untouched.getPlayerTwo().getStoneAccount());
		}
		return super.getActionsToNode(new Tree(root), currentOptimal);
	}

	public ArrayList<Action> calculateBestActions(Tree tree, Bot player) {
		return calculateBestActions(tree.root().getGame(), player, new EvaluationFunctionC());
	}
}
