package com.gipf.client.player.bot.algorithm;

import com.gipf.client.game.player.Player;
import com.gipf.client.offline.logic.Board;
import com.gipf.client.offline.logic.Game;
import com.gipf.client.player.bot.evaluation.EvaluationFunction;
import com.gipf.client.player.bot.evaluation.Evaluator;
import com.gipf.client.player.bot.generator.StateGenerator;
import com.gipf.client.utils.Point;

public abstract class Algorithm {

	protected StateGenerator generator;
	protected Evaluator evaluator;
	protected Game game;
	
	public Algorithm(EvaluationFunction function) {
		this.generator = new StateGenerator();
		this.evaluator = new Evaluator(function);
	}
	
	public void setEvaluationFunction(EvaluationFunction function) {
		this.evaluator.setEvaulationFunction(function);
	}
	
	public abstract Point[] returnBestMove(Board board, Player player);
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public Game getGame() {
		return this.game;
	}
}
