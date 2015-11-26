package com.gipf.client.game.player.bot;

import com.gipf.client.game.GameController;
import com.gipf.client.player.bot.algorithm.Algorithm;
import com.gipf.client.utils.Point;

public class BotThread extends Thread {

	private GameController controller;
	private Algorithm algorithm;
	private Bot bot;

	public BotThread(Bot bot, GameController controller, Algorithm algorithm) {
		this.controller = controller;
		this.algorithm = algorithm;
		this.bot = bot;
	}

	public void run() {
		long start = System.currentTimeMillis();

		// computation for move
		Point[] move = this.algorithm.returnBestMove(this.controller.getController().getGame().getBoard(), this.bot);
		
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
		this.controller.getController().getGamePanel().getButtons()[move[0].getX()][move[0].getY()].doClick();
		this.controller.getController().getGamePanel().getButtons()[move[1].getX()][move[1].getY()].doClick();

		// ending thread
		try {
			this.join();
			Thread.currentThread().interrupt();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
