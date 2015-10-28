package com.gipf.client.localserver;

import com.gipf.client.connector.LocalConnector;
import com.gipf.client.game.Game;

/**
 * @author simon
 * This class is meant to serve as the replacement of the online server.
 * It is connected with 2 local connectors to fake the connection.
 * Implementation of LocalServer and online server are identical except for the way of sending and retrieving.
 * The use of both classes is identical.
 */
public class LocalServer {

	private LocalConnector c1, c2;
	
//	private GameLogic gameLogic;
	private Game game;
	
	public LocalServer(LocalConnector c1, LocalConnector c2) {
		this.c1 = c1;
		this.c2 = c2;
	}
	
	public void init() {
		this.game = new Game();
		
		this.game.getBoard().basicInit();
	}
	
	public void sendToAll(String string) {
		this.send(string, c1);
		this.send(string, c2);
	}
	
	public void send(String string, LocalConnector connector) {
		connector.receive(string);
	}

	public void receive(String string) {
		
	}
}
