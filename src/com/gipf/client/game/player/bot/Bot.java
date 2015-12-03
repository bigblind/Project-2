package com.gipf.client.game.player.bot;

import com.gipf.client.game.GameController;
import com.gipf.client.game.player.Player;
import com.gipf.client.player.bot.algorithm.Algorithm;
import com.gipf.client.player.bot.evaluation.Evaluator;

public class Bot extends Player {

	private GameController gameController;
	private Algorithm algorithm;
	private Evaluator evaluator;
	
	public Bot(Algorithm algorithm, Evaluator evaluator) {
		this.algorithm = algorithm;
		this.evaluator = evaluator;
	}
	
	public void update(String state) {
		if (state.equals("move")) {
			BotMoveThread botThread = new BotMoveThread(this, this.gameController, this.algorithm, this.evaluator);
			botThread.start();
		} else if (state.equals("remove")) { //TODO 
			
		}
	}

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}
	
	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}
	
	public void setEvaluator(Evaluator evaluator) {
		this.evaluator = evaluator;
	}
}
