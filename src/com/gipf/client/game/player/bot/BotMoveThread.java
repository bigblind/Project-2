package com.gipf.client.game.player.bot;

import java.util.ArrayList;

import com.gipf.client.game.GameController;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.player.bot.algorithm.withouttreegeneration.Algorithm;
import com.gipf.client.player.bot.evaluation.EvaluationFunction;

public class BotMoveThread extends Thread {

	public static long runTime = 0;
	public static int runs = 0;

	private GameController gameController;
	private EvaluationFunction evaluator;
	private Algorithm algorithm;
	private Bot bot;

	public BotMoveThread(Bot bot, GameController gameController, Algorithm algorithm, EvaluationFunction evaluator) {
		this.gameController = gameController;
		this.algorithm = algorithm;
		this.evaluator = evaluator;
		this.bot = bot;
	}

	public void run() {
		long start = System.nanoTime();

		// computation for move
//		Node root = new Node(null, this.gameController.getController().getGame().copy(), null, true);
//		System.out.println("evaluated root");
//		this.bot.getGenerator().generateTree(2, root, this.bot, this.bot.getLogic());
//		System.out.println(new Tree(root).bfSearch(root).size());
		
		ArrayList<Action> actions = this.algorithm.calculateBestActions(this.gameController.getController().getGame().copy(), this.bot, this.evaluator);
//		System.out.println("calculated acions");
		
		// Complicated row? Bot remove thread gets information
		if (actions.size() > 1) {
			ArrayList<Action> upcomingActions = new ArrayList<Action>();
			for (int i = 1; i < actions.size(); i++)
				upcomingActions.add(actions.get(i));
			this.bot.setUpcomingActions(upcomingActions);
		}

		long end = System.nanoTime();

		runTime += end - start;
		runs++;
//		System.out.println("average runtime: " + runTime / (runs * 1000000.0) + "ms at " + runs + " runs, current run time: " + (end - start) + " miliseconds = " + ((end - start) / 1000000.0) + " ms with " + " algo: " + this.algorithm.getClass().getName() +".");
//		System.out.println();
		// used for making bots move visible
		if (end - start < 166700000) {
			try {
				Thread.sleep((166700000 - (end - start)) / 1000000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		// do move
		this.gameController.getController().getGamePanel().getButtons()[actions.get(0).getPoints()[0].getX()][actions.get(0).getPoints()[0].getY()].doClick();

		// visualise direction choice
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		this.gameController.getController().getGamePanel().getButtons()[actions.get(0).getPoints()[1].getX()][actions.get(0).getPoints()[1].getY()].doClick();
	}
}
