package com.gipf.client.game.player.bot;

import com.gipf.client.game.GameController;
import com.gipf.client.game.player.Player;

public class Bot extends Player {

	private GameController controller;

	public void update(String state) {
		System.out.println(state);
		Thread bot = new Thread() {
			public void run() {
				long start = System.currentTimeMillis();

				//				controller.getController().getGamePanel().getButtons()[0][0].doClick();
				//				controller.getController().getGamePanel().getButtons()[1][1].doClick();

				long end = System.currentTimeMillis();

				if (end - start < 1000) {
					try {
						Thread.sleep(end - start);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}

				// do move

				try {
					this.join();
					Thread.currentThread().interrupt();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		};
		bot.start();
	}

	public void setController(GameController controller) {
		this.controller = controller;
	}
}
