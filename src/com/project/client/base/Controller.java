package com.project.client.base;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.gipf.client.connector.Connector;
import com.gipf.client.connector.LocalConnector;
import com.gipf.client.game.Game;
import com.gipf.client.game.GameController;
import com.gipf.client.game.player.Player;
import com.gipf.client.offline.logic.LocalServer;
import com.gipf.client.resource.ResourceLoader;
import com.project.client.board.Board;
import com.project.client.visuals.board.GamePanel;
import com.project.client.visuals.frame.Frame;
import com.project.client.visuals.menu.GameModeMenuPageA;
import com.project.client.visuals.menu.GameModeMenuPageB;
import com.project.client.visuals.menu.MainMenuPage;
import com.project.client.visuals.menu.MenuPage;
import com.project.client.visuals.menu.MultiplayerMenuPage;
import com.project.client.visuals.state.State;
import com.project.client.visuals.state.WaitState;

public class Controller {

	private ArrayList<MenuPage> menuPages;

	private JFrame frame;

	private Connector connector;

	private GameController gameController;
	private GamePanel gamePanel;
	private Game game;

	private boolean runningLocalGame = false;
	private Controller ghostController;

	public Controller() {

	}

	public void init() {
		this.frame = new Frame();
		this.gameController = new GameController(this);

		ResourceLoader resourceLoader = new ResourceLoader();
		try {
			resourceLoader.init();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		this.gamePanel = new GamePanel(new Game(new Player("", 0, Board.WHITE_VALUE), new Player("", 0, Board.BLACK_VALUE)), this);

		this.menuPages = new ArrayList<MenuPage>();

		this.initMenuPages();
		this.showMenuPage(0);

		this.frame.pack();
		this.frame.setVisible(true);
	}

	public void ghostInit() {
		this.gameController = new GameController(this);
		this.gamePanel = new GamePanel(new Game(new Player("", 0, Board.WHITE_VALUE), new Player("", 0, Board.BLACK_VALUE)), this);
	}

	private void initMenuPages() {
		this.menuPages.add(new MainMenuPage(this));
		this.menuPages.add(new MultiplayerMenuPage(this));
		this.menuPages.add(new GameModeMenuPageA(this));
		this.menuPages.add(new GameModeMenuPageB(this));
	}

	public void showMenuPage(int index) {
		this.showPanel(this.menuPages.get(index));
	}

	public void showPanel(JPanel panel) {
		this.frame.setContentPane(panel);
		this.frame.pack();
	}

	public void setConnector(Connector connector) {
		this.connector = connector;
	}

	public void disconnect() {
		this.connector.receive("quit");
		this.connector = null;
	}

	/**
	 * @param server
	 *            Used to make a local game.
	 */
	public void createLocalGame(LocalServer server) {
		this.ghostController = new Controller();
		this.ghostController.ghostInit();

		LocalConnector ghostConnector = new LocalConnector(this.ghostController.getGameController(), server);

		this.setConnector(new LocalConnector(this.gameController, server));
		this.ghostController.setConnector(ghostConnector);

		server.setConnectors(this.connector, ghostConnector);
		server.start();

		this.runningLocalGame = true;
	}

	public void gamePanelStateChange(State state) {
		if (this.runningLocalGame) {
			if (state instanceof WaitState) this.showPanel(this.ghostController.getGamePanel());
			else this.showPanel(this.gamePanel);
		}
	}

	public GameController getGameController() {
		return this.gameController;
	}

	public GamePanel getGamePanel() {
		return this.gamePanel;
	}

	public Connector getConnector() {
		return this.connector;
	}

	public JFrame getFrame() {
		return this.frame;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return this.game;
	}
}
