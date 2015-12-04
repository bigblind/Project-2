package com.gipf.client.game.player.bot;

import java.util.ArrayList;

import com.gipf.client.game.GameController;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.utils.Point;

public class BotRemoveThread extends Thread {

	private ArrayList<Action> actions;
	private GameController gameController;
	private Bot bot;

	public BotRemoveThread(Bot bot, GameController gameController, ArrayList<Action> actions) {
		this.gameController = gameController;
		this.actions = actions;
		this.bot = bot;
	}

	public void run() {
		// visualising move
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		// remove
		for (Action a : this.actions) {
			for (Point p : a.getPoints()) {
				this.gameController.getController().getGamePanel().getButtons()[p.getX()][p.getY()].doClick();
			}
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
