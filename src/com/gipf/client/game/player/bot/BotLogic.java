package com.gipf.client.game.player.bot;

import java.util.ArrayList;

import com.gipf.client.game.GameController;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;
import com.gipf.client.player.bot.algorithm.Algorithm;
import com.gipf.client.player.bot.evaluation.Evaluator;
import com.gipf.client.utils.Point;

public class BotRemoveThread extends Thread {

	private ArrayList<Action> actions;
	private GameController gameController;
	private Bot bot;
	private Algorithm algorithm;
	private Evaluator evaluator;

	public BotRemoveThread(Bot bot, GameController gameController, Algorithm algorithm, Evaluator evaluator, ArrayList<Action> actions) {
		this.gameController = gameController;
		this.actions = actions;
		this.bot = bot;
		this.evaluator = evaluator;
		this.algorithm = algorithm;
	}

	public void run() {

		if (this.actions.size() == 0) {
			Node root = this.evaluator.evalToNode(this.gameController.getController().getGame().copy());
			this.bot.getLogic().performLogic(this.bot, root);

			this.actions = algorithm.calculateBestActions(new Tree(root), this.bot);
		}

		// visualising removal
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		// remove
		for (Action a : this.actions) {
			for (Point p : a.getPoints()) {
				this.gameController.getController().getGamePanel().getButtons()[p.getX()][p.getY()].doClick();
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}

			}
			this.gameController.getController().getGamePanel().getCheckButton().doClick();
		}

		// ending thread
		try {
			this.join();
			Thread.currentThread().interrupt();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public Bot getBot() {
		return this.bot;
	}
}