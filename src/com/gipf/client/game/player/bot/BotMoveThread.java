package com.gipf.client.game.player.bot;

import java.util.ArrayList;

import com.gipf.client.game.GameController;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;
import com.gipf.client.player.bot.algorithm.Algorithm;
import com.gipf.client.player.bot.evaluation.Evaluator;

public class BotMoveThread extends Thread {

	public static long runTime = 0;
	public static int runs = 0;

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
		long start = System.nanoTime();

		// computation for move
		Node root = this.evaluator.evalToNode(this.gameController.getController().getGame());
		this.bot.getGenerator().generateTreeLayer(root, this.bot, this.bot.getLogic(), false);
		ArrayList<Action> actions = this.algorithm.calculateBestActions(new Tree(root), this.bot);

		ArrayList<Node> search = new Tree(root).bfSearch(root);
		System.out.println(search.size() + " nodes");
		
		if (actions.size() > 1) {
			ArrayList<Action> upcomingActions = new ArrayList<Action>();
			for (int i = 1; i < actions.size(); i++)
				upcomingActions.add(actions.get(i));
			this.bot.setUpcomingActions(upcomingActions);
		}

		long end = System.nanoTime();

		runTime += end - start;
		runs++;
		System.out.println("average runtime: " + runTime / (runs * 1000000.0) + "ms at " + runs + " runs, current run time: " + (end - start) + " nanoseconds = " + ((end - start) / 1000000.0) + " ms.");

		// used for making bots move visible
		if (end - start < 250000000) {
			try {
				Thread.sleep((250000000 - (end - start)) / 1000000);
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
