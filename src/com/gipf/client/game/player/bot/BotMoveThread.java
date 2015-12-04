package com.gipf.client.game.player.bot;

import java.util.ArrayList;

import com.gipf.client.game.GameController;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;
import com.gipf.client.player.bot.algorithm.Algorithm;
import com.gipf.client.player.bot.evaluation.Evaluator;

public class BotMoveThread extends Thread {

	private GameController gameController;
	private Evaluator evaluator;
	private Algorithm algorithm;
	private Bot bot;

	public BotMoveThread(Bot bot, GameController gameController, Algorithm algorithm, Evaluator evaluator) {
		this.gameController = gameController;
		this.algorithm = algorithm;
		this.evaluator = evaluator;
		this.bot = bot;
	}

	public void run() {
		long start = System.currentTimeMillis();

		// computation for move
		Node root = this.evaluator.evalToNode(this.gameController.getController().getGame());
		this.bot.getGenerator().generateTreeLayer(root, this.bot, this.bot.getLogic());
		ArrayList<Action> actions = this.algorithm.calculateBestActions(new Tree(root), this.bot);

		if (actions.size() > 1) {
			ArrayList<Action> upcomingActions = new ArrayList<Action>();
			for (int i = 1; i < actions.size(); i++) upcomingActions.add(actions.get(i));
			this.bot.setUpcomingActions(upcomingActions);
		}

		long end = System.currentTimeMillis();

		// used for making bots move visible
		if (end - start < 500) {
			try {
				Thread.sleep(500 - (end - start));
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		// do move
		this.gameController.getController().getGamePanel().getButtons()[actions.get(0).getPoints()[0].getX()][actions.get(0).getPoints()[0].getY()].doClick();
		this.gameController.getController().getGamePanel().getButtons()[actions.get(0).getPoints()[1].getX()][actions.get(0).getPoints()[1].getY()].doClick();

		// ending thread
		try {
			this.join();
			Thread.currentThread().interrupt();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
