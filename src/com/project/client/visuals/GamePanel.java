package com.project.client.visuals;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.project.client.connection.ClientInterface;
import com.project.client.visuals.sidepanel.SidePanel;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 2230253311268206870L;

	private BoardPanel boardPanel;
	
	private SidePanel sidePanel;
	
	public GamePanel(ClientInterface clientInterface) {
		this.boardPanel = new BoardPanel(clientInterface);
		
		this.sidePanel = new SidePanel();
		this.sidePanel.setVisible(false);
		
		this.setLayout(new BorderLayout());
		this.add(this.boardPanel, BorderLayout.CENTER);
		this.add(this.sidePanel, BorderLayout.WEST);
	}
	
	public BoardPanel getBoardPanel() {
		return this.boardPanel;
	}
}
