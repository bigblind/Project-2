package com.gipf.client.game.player.bot;

import java.util.ArrayList;

import com.gipf.client.game.GameController;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;
import com.gipf.client.player.bot.algorithm.withouttreegeneration.ActionRetrievalMethod;
import com.gipf.client.utils.Point;

public class BotRemoveThread extends Thread {

	private ArrayList<Action> actions;
	private GameController gameController;
	private Bot bot;
	private ActionRetrievalMethod method;

	public BotRemoveThread(Bot bot, GameController gameController, ArrayList<Action> actions) {
		this.gameController = gameController;
		this.actions = actions;
		this.bot = bot;
		this.method = new ActionRetrievalMethod();
	}

	public void run() {
		if (this.actions.size() == 0) {
			Node root = new Node(null, this.gameController.getController().getGame().copy(), null, true);
			this.bot.getLogic().performLogic(this.bot, root);

			this.actions = this.method.calculateBestActions(new Tree(root), this.bot);
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
	}

	public Bot getBot() {
		return this.bot;
	}
}
