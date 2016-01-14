package com.project.client.base;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.gipf.client.connector.Connector;
import com.gipf.client.connector.LocalConnector;
import com.gipf.client.game.GameController;
import com.gipf.client.game.player.Player;
import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.offline.logic.Board;
import com.gipf.client.offline.logic.Game;
import com.gipf.client.offline.logic.LocalServer;
import com.gipf.client.resource.ResourceLoader;
import com.project.client.visuals.board.GamePanel;
import com.project.client.visuals.frame.Frame;
import com.project.client.visuals.menu.ArenaBotSelectionPage;
import com.project.client.visuals.menu.GameModeMenuPageArena;
import com.project.client.visuals.menu.GameModeMenuPageMulti;
import com.project.client.visuals.menu.GameModeMenuPageSingle;
import com.project.client.visuals.menu.MainMenuPage;
import com.project.client.visuals.menu.MenuPage;
import com.project.client.visuals.menu.MultiplayerMenuPage;
import com.project.client.visuals.menu.SingleBotSelectionPage;
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
	private boolean runningBotGame = false;

	private Controller ghostController;
	private GhostController ghostController2;

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

		this.gamePanel = new GamePanel(new Game(new Player(0, Board.WHITE_VALUE), new Player(0, Board.BLACK_VALUE), false), this, true);

		this.menuPages = new ArrayList<MenuPage>();

		this.initMenuPages();
		this.showMenuPage(0);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.frame.pack();
		this.frame.setLocation((int) ((screenSize.getWidth() / 2) - (frame.getSize().getWidth() / 2)), (int) ((screenSize.getHeight() / 2) - (frame.getSize().getHeight() / 2)));
		this.frame.setVisible(true);
	}

	public void ghostInit(boolean showCheckedButton) {
		this.gameController = new GameController(this);
		if (showCheckedButton) this.gamePanel = new GamePanel(new Game(new Player(0, Board.WHITE_VALUE), new Player(0, Board.BLACK_VALUE), false), this, true);
		else this.gamePanel = new GamePanel(new Game(new Player(0, Board.WHITE_VALUE), new Player(0, Board.BLACK_VALUE), false), this, false);
	}

	private void initMenuPages() {
		this.menuPages.add(new MainMenuPage(this));
		this.menuPages.add(new MultiplayerMenuPage(this));
		this.menuPages.add(new GameModeMenuPageSingle(this));
		this.menuPages.add(new GameModeMenuPageMulti(this));
		this.menuPages.add(new GameModeMenuPageArena(this));
		this.menuPages.add(new ArenaBotSelectionPage(this));
		this.menuPages.add(new SingleBotSelectionPage(this));
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
		this.ghostController.ghostInit(true);

		LocalConnector ghostConnector = new LocalConnector(this.ghostController.getGameController(), server);

		this.setConnector(new LocalConnector(this.gameController, server));
		this.ghostController.setConnector(ghostConnector);

		server.setConnectors(this.connector, ghostConnector);
		server.start();

		this.runningLocalGame = true;
	}

	public void createArenaGame(LocalServer server, Bot one, Bot two) {
		this.ghostController = new Controller();
		this.ghostController2 = new GhostController(this);
		this.ghostController.ghostInit(false);

		ghostController2.ghostInit(false);

		LocalConnector ghostConnector1 = new LocalConnector(this.ghostController.getGameController(), server);
		LocalConnector ghostConnector2 = new LocalConnector(ghostController2.getGameController(), server);

		this.ghostController.setConnector(ghostConnector1);
		ghostController2.setConnector(ghostConnector2);

		this.ghostController.getGameController().setPlayer(one, true);
		ghostController2.getGameController().setPlayer(two, true);

		server.setConnectors(ghostConnector1, ghostConnector2);
		server.start();

		ghostController.getGame().setGameLogic(server.getGameLogic());
		ghostController.getGame().setIsStandard(server.getGameLogic().isStandard());
		ghostController2.getGame().setGameLogic(server.getGameLogic());
		ghostController2.getGame().setIsStandard(server.getGameLogic().isStandard());

		one.setGameController(this.ghostController.getGameController());
		two.setGameController(ghostController2.getGameController());

		server.start();

		this.runningBotGame = true;

		this.showPanel(ghostController.getGamePanel());
		this.frame.pack();

		this.ghostController.getGameController().input("/s move");
	}

	public void createLocalBotGame(LocalServer server, Bot opponent) {
		this.ghostController = new Controller();
		this.ghostController.ghostInit(false);

		LocalConnector ghostConnector = new LocalConnector(this.ghostController.getGameController(), server);

		this.setConnector(new LocalConnector(this.gameController, server));
		this.ghostController.setConnector(ghostConnector);

		this.ghostController.getGameController().setPlayer(opponent, true);

		server.setConnectors(this.connector, ghostConnector);
		server.start();

		ghostController.getGame().setGameLogic(server.getGameLogic());
		opponent.setGameController(this.ghostController.getGameController());

		this.runningLocalGame = true;
		this.runningBotGame = true;
	}

	public void gamePanelStateChange(State state) {
		if (this.runningLocalGame && !this.runningBotGame) {
			if (state instanceof WaitState) this.showPanel(this.ghostController.getGamePanel());
			else this.showPanel(this.gamePanel);
		} else if (!this.runningLocalGame && this.runningBotGame) {
			if (state instanceof WaitState) this.showPanel(this.ghostController.getGamePanel());
			else this.showPanel(this.ghostController2.getGamePanel());
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

	public Controller getGhostController() {
		return this.ghostController;
	}

	public ArrayList<MenuPage> getMenuPages() {
		return this.menuPages;
	}
}
