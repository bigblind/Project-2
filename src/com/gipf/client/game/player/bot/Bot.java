package com.gipf.client.game.player.bot;

import com.gipf.client.game.GameController;
import com.gipf.client.game.player.Player;
import com.gipf.client.player.bot.algorithm.Algorithm;
import com.gipf.client.player.bot.evaluation.EvaluationFunction;

public class Bot extends Player {

	private GameController controller;
	private Algorithm algorithm;
	
	public Bot(Algorithm algorithm) {
		this.algorithm = algorithm;
	}
	
	public void update(String state) {
		// TODO think about remove state
		BotThread botThread = new BotThread(this, controller, algorithm);
		botThread.start();
	}

	public void setController(GameController controller) {
		this.controller = controller;
		this.algorithm.setGame(controller.getController().getGame());
	}
	
	public void setAlgorithm(Algorithm algorithm) {
		algorithm.setGame(this.algorithm.getGame());
		this.algorithm = algorithm;
	}
	
	public void setEvaluationFunction(EvaluationFunction function) {
		this.algorithm.setEvaluationFunction(function);
	}
}
