package com.project.visuals;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.project.logic.Game;

public class GameFrame extends JFrame implements ComponentListener {

	private static final long serialVersionUID = -3171344956959611349L;

	private GamePanel gamePanel;
	private JPanel ghostGamePanel;
	private GhostBoardButtonPanel ghostBoardButtonPanel;
	
	public GameFrame(Game game) {
		final int width = 1200;
		this.setPreferredSize(new Dimension(width, width / 16 * 9));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addComponentListener(this);

		JLayeredPane layer = this.getLayeredPane();
		layer.removeAll();

		this.gamePanel = new GamePanel(game);
		this.gamePanel.setMinimumSize(new Dimension(50, 50));
		this.gamePanel.setSize(50, 50);

		this.ghostBoardButtonPanel = new GhostBoardButtonPanel(this.gamePanel.getBoardPanel());

		this.ghostGamePanel = new JPanel();
		this.ghostGamePanel.setBackground(new Color(0, 0, 0, 150));

		layer.add(this.gamePanel, new Integer(0));
		layer.add(this.ghostBoardButtonPanel, new Integer(1));
		layer.add(this.ghostGamePanel, new Integer(2));

		this.ghostGamePanel.setVisible(false);
		this.setIconImage(ResourceLoader.ICON);
		
		this.pack();
	}

	public void componentHidden(ComponentEvent e) {

	}

	public void componentMoved(ComponentEvent e) {

	}

	public void componentResized(ComponentEvent e) {
		int width = (int) (this.getSize().getWidth() - this.getInsets().left - this.getInsets().right);
		int height = (int) (this.getSize().getHeight() - this.getInsets().top - this.getInsets().bottom);

		this.gamePanel.setSize(width, height);
		this.ghostGamePanel.setSize(width, height-1); // TODO EXPLAIN WHAT THE FUCK. ... JAG KAN INTE JAG ORKAR INTE
		this.revalidate();
	}

	public void componentShown(ComponentEvent e) {

	}
}
