package com.gipf.client.game.player.bot;

import com.gipf.client.game.GameController;
import com.gipf.client.player.bot.algorithm.Algorithm;
import com.gipf.client.player.bot.generator.GameState;
import com.gipf.client.utils.Point;

public class BotMoveThread extends Thread {

	private GameController gameController;
	private Algorithm algorithm;
	private Bot bot;

	public BotMoveThread(Bot bot, GameController gameController, Algorithm algorithm) {
		this.gameController = gameController;
		this.algorithm = algorithm;
		this.bot = bot;
	}

	public void run() {
		long start = System.currentTimeMillis();

		// computation for move
		Point[] move = this.algorithm.returnBestMove(new GameState(this.gameController.getController().getGame(), null, null), this.bot);
		
		long end = System.currentTimeMillis();

//		 used for making bots move visible
		if (end - start < 800) {
			try {
				Thread.sleep(800 - (end - start));
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		// do move
		this.gameController.getController().getGamePanel().getButtons()[move[0].getX()][move[0].getY()].doClick();
		this.gameController.getController().getGamePanel().getButtons()[move[1].getX()][move[1].getY()].doClick();

		// ending thread
		try {
			this.join();
			Thread.currentThread().interrupt();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
