package com.gipf.client.player.bot.evaluation;

import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.offline.logic.Game;

public class Evaluator {

	private EvaluationFunction function;

	public Evaluator(EvaluationFunction function) {
		this.function = function;
	}

	public void setEvaulationFunction(EvaluationFunction function) {
		this.function = function;
	}

	public Node evalToNode(Game game) {
		Node node = new Node(null, game, null, true);
		node.setValue(function.evaluate(game.getBoard(), game.getPlayerOne().getStoneAccount(), game.getPlayerTwo().getStoneAccount()));
		return node;
	}

	public int evaluate(Game game) {
		return function.evaluate(game.getBoard(), game.getPlayerOne().getStoneAccount(), game.getPlayerTwo().getStoneAccount());
	}
}
