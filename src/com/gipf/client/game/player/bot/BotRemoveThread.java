package com.gipf.client.game.player.bot;

import com.gipf.client.game.GameController;
import com.gipf.client.offline.logic.Row;
import com.gipf.client.player.bot.algorithm.Algorithm;
import com.project.client.visuals.state.RemoveState;

public class BotRemoveThread extends Thread {

	private GameController gameController;
	private Algorithm algorithm;
	private Bot bot;

	public BotRemoveThread(Bot bot, GameController gameController, Algorithm algorithm) {
		this.gameController = gameController;
		this.algorithm = algorithm;
		this.bot = bot;
	}

	public void run() {
		long start = System.currentTimeMillis();

		// computation for remove
		RemoveState state = null;
		try {
			state = (RemoveState) this.gameController.getController().getGamePanel().getState();
		} catch(ClassCastException e) {
			e.printStackTrace();
			System.exit(0);
		}
		Row[] rows = state.getRows();
		
		long end = System.currentTimeMillis();

		// used for making bots move visible
		if (end - start < 800) {
			try {
				Thread.sleep(800 - (end - start));
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
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
}
