package com.gipf.client.game.player.bot;

import com.gipf.client.game.GameController;
import com.gipf.client.player.bot.algorithm.Algorithm;
import com.gipf.client.utils.Point;

public class BotRemoveThread extends Thread {

	private GameController controller;
	private Algorithm algorithm;
	private Bot bot;

	public BotRemoveThread(Bot bot, GameController controller, Algorithm algorithm) {
		this.controller = controller;
		this.algorithm = algorithm;
		this.bot = bot;
	}

	public void run() {
		long start = System.currentTimeMillis();

		// computation for remove
		
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
