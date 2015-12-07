package com.gipf.client.game.player.bot;

import com.gipf.client.game.GameController;
import com.gipf.client.game.player.Player;
import com.gipf.client.player.bot.algorithm.Algorithm;
import com.gipf.client.player.bot.evaluation.EvaluationFunction;

public class Bot extends Player {

	private GameController gameController;
	private Algorithm algorithm;
	
	public Bot(Algorithm algorithm) {
		this.algorithm = algorithm;
	}
	
	public void update(String state) {
		if (state.equals("move")) {
			BotMoveThread botThread = new BotMoveThread(this, this.gameController, this.algorithm);
			botThread.start();
		} else if (state.equals("remove")) { //TODO 
			
		}
	}

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
		this.algorithm.setGame(gameController.getController().getGame());
	}
	
	public void setAlgorithm(Algorithm algorithm) {
		algorithm.setGame(this.algorithm.getGame());
		this.algorithm = algorithm;
	}
	
	public void setEvaluationFunction(EvaluationFunction function) {
		this.algorithm.setEvaluationFunction(function);
	}
}
