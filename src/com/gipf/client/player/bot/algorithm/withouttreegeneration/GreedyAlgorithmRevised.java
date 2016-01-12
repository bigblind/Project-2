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
import com.gipf.client.player.bot.evaluation.EvaluationFunctionA;

public class GreedyAlgorithmRevised extends Algorithm {

	public GreedyAlgorithmRevised() {
		super();
		this.name = "Greedy Algorithm Revised";
	}

	public ArrayList<Action> calculateBestActions(Game game, Bot player, EvaluationFunction evaluator) {
		Game untouched = game.copy();
		Game use = game.copy();

		Node root = new Node(null, game, null, true);
		root.setPossibleActions(generator.getPossibleActions(root));

		Node currentOptimal = null;
		for (Action action : root.getPossibleActions()) {
			Game untouchedChild = use.copy();
			Game useChild = use.copy();

			Node child = new Node(root, use, action, false);
			child.setAction(action);
			root.addChild(child);
			child.getGame().getBoard().place(player.getStoneColor(), action.getPoints()[0], action.getPoints()[1]);
			if (player.getStoneColor() == Board.WHITE_VALUE) child.getGame().getPlayerOne().addStones(-1);
			else child.getGame().getPlayerTwo().addStones(-1);
			player.getLogic().performLogic(player, child);
			child.setValue(evaluator.evaluate(child.getGame().getBoard(), child.getGame().getPlayerOne().getStoneAccount(), child.getGame().getPlayerTwo().getStoneAccount(), true)); //TODO this boolean is for if it is standard
			child.setPossibleActions(generator.getPossibleActions(child));

			boolean care = false;
			for (Action childAction : child.getPossibleActions()) {
				Node grandChild = new Node(child, useChild, childAction, false);
				grandChild.setAction(childAction);
				child.addChild(grandChild);
				grandChild.getGame().getBoard().place(super.opponentStoneColor(player.getStoneColor()), childAction.getPoints()[0], childAction.getPoints()[1]);
				if (player.getStoneColor() == Board.WHITE_VALUE) grandChild.getGame().getPlayerTwo().addStones(-1);
				else grandChild.getGame().getPlayerOne().addStones(-1);
				player.getLogic().performLogic(player, grandChild);
				grandChild.setValue(evaluator.evaluate(grandChild.getGame().getBoard(), grandChild.getGame().getPlayerOne().getStoneAccount(), grandChild.getGame().getPlayerTwo().getStoneAccount(), true)); //TODO this boolean is for if it is standard
				if (currentOptimal == null) {
					currentOptimal = grandChild;
					care = true;
				} else if (player.getStoneColor() == Board.WHITE_VALUE && grandChild.getValue() > currentOptimal.getValue()) {
					currentOptimal = grandChild;
					care = true;
				} else if (player.getStoneColor() == Board.BLACK_VALUE && -grandChild.getValue() > -currentOptimal.getValue()) {
					currentOptimal = grandChild;
					care = true;
				}

				useChild.getBoard().setGrid(untouchedChild.getBoard().getGrid());
				useChild.getPlayerOne().setStoneAccount(untouchedChild.getPlayerOne().getStoneAccount());
				useChild.getPlayerTwo().setStoneAccount(untouchedChild.getPlayerTwo().getStoneAccount());
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
		return calculateBestActions(tree.root().getGame(), player, new EvaluationFunctionA("fucking cock"));
	}
}
