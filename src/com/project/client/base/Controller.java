package com.project.client.base;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.gipf.client.connector.Connector;
import com.gipf.client.connector.LocalConnector;
import com.gipf.client.game.GameController;
import com.gipf.client.localserver.LocalServer;
import com.gipf.client.resource.ResourceLoader;
import com.project.client.visuals.frame.Frame;
import com.project.client.visuals.menu.GameModeMenuPageA;
import com.project.client.visuals.menu.GameModeMenuPageB;
import com.project.client.visuals.menu.MainMenuPage;
import com.project.client.visuals.menu.MenuPage;
import com.project.client.visuals.menu.MultiplayerMenuPage;

public class Controller {

	private ArrayList<MenuPage> menuPages;
	
	private JFrame frame;
	
	private Connector connector;
	
	private GameController gameController;
	
	public Controller() {
		this.menuPages = new ArrayList<MenuPage>();
		this.gameController = new GameController();
	}
	
	public void init() {
		this.frame = new Frame();

		this.initMenuPages();
		this.showMenuPage(0);
		
		ResourceLoader resourceLoader = new ResourceLoader();
		try {
			resourceLoader.init();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		this.frame.setVisible(true);
		this.frame.pack();
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
	
	private void showPanel(JPanel panel) {
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
	 * Used to make a local game.
	 */
	public void createLocalGame(LocalServer server) {
		Controller ghostController = new Controller();
		
		this.setConnector(new LocalConnector(this.gameController, server));
		ghostController.setConnector(new LocalConnector(this.gameController, server));
	}
	
	public GameController getGameController() {
		return this.gameController;
	}
}
