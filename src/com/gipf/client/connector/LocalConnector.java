package com.gipf.client.connector;

import com.gipf.client.game.GameController;
import com.gipf.client.localserver.LocalServer;

public class LocalConnector extends Connector {

	private LocalServer server;
	
	public LocalConnector(GameController gameController, LocalServer server) {
		super(gameController);
		this.server = server;
	}
	
	public void send(String string) {
		this.server.receive(string);
	}

	public void receive(String string) {
		super.receive(string);
	}
}
