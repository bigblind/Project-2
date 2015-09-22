package com.project.visuals;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.project.logic.Game;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 2230253311268206870L;

	private Game game;
	
	public GamePanel(Game game) {
		this.game = game;
		
		this.setLayout(new BorderLayout());
		this.add(new BoardPanel(game.getBoard()), BorderLayout.CENTER);
		this.add(new SidePanel(), BorderLayout.EAST);
	}
}
