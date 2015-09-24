package com.project.visuals;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.project.logic.Game;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 2230253311268206870L;

	private BoardPanel boardPanel;
	
	public GamePanel(Game game) {
		this.boardPanel = new BoardPanel(game);
		
		this.setLayout(new BorderLayout());
		this.add(this.boardPanel, BorderLayout.CENTER);
		this.add(new SidePanel(), BorderLayout.EAST);
	}
	
	public BoardPanel getBoardPanel() {
		return this.boardPanel;
	}
}
