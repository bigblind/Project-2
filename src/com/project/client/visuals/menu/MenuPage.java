package com.project.client.visuals.menu;

import javax.swing.JPanel;

import com.project.client.base.Controller;

public abstract class MenuPage extends JPanel {

	private static final long serialVersionUID = -7444068164252064611L;
	
	private final Controller controller;
	
	public MenuPage(final Controller controller) {
		this.controller = controller;
	}
	
	public Controller getController() {
		return this.controller;
	}
}
