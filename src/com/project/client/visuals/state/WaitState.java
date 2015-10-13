package com.project.client.visuals.state;

import com.project.client.connection.ClientInterface;
import com.project.client.visuals.BoardPanel;

public class WaitState extends State {

	public WaitState(final BoardPanel boardPanel, final ClientInterface clientInterface) {
		super(boardPanel, clientInterface);
	}
	
	public void execute() {
		super.execute();
	}
}
