package com.gipf.client.game.player.bot;

import com.gipf.client.game.GameController;
import com.gipf.client.offline.logic.Row;
import com.gipf.client.player.bot.algorithm.Algorithm;
import com.project.client.visuals.state.RemoveState;

public class BotRemoveThread extends Thread {

	private GameController gameController;
	private Algorithm algorithm;
	private Bot bot;

<<<<<<< HEAD
	public BotRemoveThread(Bot bot, GameController gameController, Algorithm algorithm) {
=======
	public BotRemoveThread(Bot bot, GameController gameController, Algorithm algorithm, Evaluator evaluator, ArrayList<Action> actions) {
>>>>>>> BranchBranchBranches!!!
		this.gameController = gameController;
		this.algorithm = algorithm;
		this.bot = bot;
	}

	public void run() {
<<<<<<< HEAD
		long start = System.currentTimeMillis();
=======

		if (this.actions.size() == 0) {
			Node root = this.evaluator.evalToNode(this.gameController.getController().getGame().copy());
			this.bot.getLogic().performLogic(this.bot, root);
>>>>>>> BranchBranchBranches!!!

		// computation for remove
		RemoveState state = null;
		try {
			state = (RemoveState) this.gameController.getController().getGamePanel().getState();
		} catch(ClassCastException e) {
			e.printStackTrace();
			System.exit(0);
		}
<<<<<<< HEAD
		Row[] rows = state.getRows();
		
		long end = System.currentTimeMillis();

		// used for making bots move visible
		if (end - start < 800) {
			try {
				Thread.sleep(800 - (end - start));
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
=======

		// visualising move
		//		try {
		//			Thread.sleep(800);
		//		} catch (InterruptedException e) {
		//			Thread.currentThread().interrupt();
		//		}

		// remove
		for (Action a : this.actions) {
			for (Point p : a.getPoints()) {
				this.gameController.getController().getGamePanel().getButtons()[p.getX()][p.getY()].doClick();
>>>>>>> BranchBranchBranches!!!
			}
		}

		// remove

		// ending thread
		try {
			this.join();
			Thread.currentThread().interrupt();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
<<<<<<< HEAD
=======

	public Bot getBot() {
		return this.bot;
	}
>>>>>>> BranchBranchBranches!!!
}
