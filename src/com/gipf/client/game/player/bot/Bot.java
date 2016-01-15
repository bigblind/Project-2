package com.gipf.client.game.player.bot;

import java.util.ArrayList;

import com.gipf.client.game.GameController;
import com.gipf.client.game.player.Player;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.player.bot.algorithm.Algorithm;
import com.gipf.client.player.bot.evaluation.EvaluationFunction;

public class Bot extends Player {

	private GameController gameController;
	private Algorithm algorithm;
	private BotLogic botLogic;
	private EvaluationFunction evaluator;

	private ArrayList<Action> upcomingActions;

	public Bot(Algorithm algorithm, EvaluationFunction evaluator) {
		this.algorithm = algorithm;
		this.evaluator = evaluator;
		this.botLogic = new BotLogic(this);
		this.upcomingActions = new ArrayList<Action>();
	}

	public void update(String state) {
		if (state.equals("move")) {
			BotMoveThread moveThread = new BotMoveThread(this, this.gameController, this.algorithm, this.evaluator);
			moveThread.start();
		} else if (state.equals("remove")) {
			BotRemoveThread removeThread = new BotRemoveThread(this, this.gameController, this.upcomingActions);
			removeThread.start();
			this.upcomingActions.clear();
		}
	}

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}

	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	public void setEvaluator(EvaluationFunction evaluator) {
		this.evaluator = evaluator;
	}

	public void setUpcomingActions(ArrayList<Action> actions) {
		this.upcomingActions = actions;
	}

	public BotLogic getLogic() {
		return this.botLogic;
	}

	public EvaluationFunction getEvaluator() {
		return this.evaluator;
	}
}
