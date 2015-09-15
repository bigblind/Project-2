package com.project.visuals;

import java.awt.Dimension;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = -3171344956959611349L;

	public GameFrame() {
		final int width = 1200;
		this.setPreferredSize(new Dimension(width, width / 16 * 9));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
