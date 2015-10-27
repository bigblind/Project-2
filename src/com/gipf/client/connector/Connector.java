package com.gipf.client.connector;

import com.gipf.client.game.GameController;

public abstract class Connector {

	protected GameController gameController;
	
	public Connector(GameController gameController) {
		this.gameController = gameController;
	}
	
	public abstract void send(String string);
	
	public abstract void receive(String string);
}
