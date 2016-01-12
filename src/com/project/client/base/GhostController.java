package com.project.client.base;

import com.project.client.visuals.state.State;

public class GhostController extends Controller {

	private Controller controller;

	public GhostController(Controller controller) {
		this.controller = controller;
	}

	public void gamePanelStateChange(State state) {
		this.controller.gamePanelStateChange(state);
	}
}
