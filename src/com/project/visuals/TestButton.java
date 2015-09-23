package com.project.visuals;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

import com.project.logic.Player;

public class TestButton extends JButton {

	private static final long serialVersionUID = -3216722706956588440L;

	private Player player;
	
	public void paintComponent(Graphics g2) {
		Graphics2D g = (Graphics2D) g2;

		g.drawImage(ResourceLoader.BLACK_STONE, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}
