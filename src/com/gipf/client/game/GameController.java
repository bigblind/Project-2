package com.gipf.client.game;

import com.gipf.client.connector.Connector;
import com.gipf.client.game.player.Player;
import com.project.client.base.Controller;
import com.project.client.visuals.board.GamePanel;

public class GameController {

	private Controller controller;
	private Connector connector;
	private GamePanel gamePanel;
	private Game game;

	private Player thisPlayer;
	
	public GameController(Controller controller, Player player) {
		this.thisPlayer = player;
		this.game = controller.getGamePanel().getGame();
		this.gamePanel = controller.getGamePanel();
		this.connector = controller.getConnector();
		this.controller = controller;
	}
	
	public void reinitConnector() {
		this.connector = this.controller.getConnector();
	}
	
	public void input(String text) {
		System.out.println(text);
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public Game getGame() {
		return this.game;
	}

	public Player getThisPlayer() {
		return this.thisPlayer;
	}
	
	public Connector getConnector() {
		return this.connector;
	}
}
