package com.project.client.visuals.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.project.client.base.Controller;

public class MainMenuPage extends MenuPage {

	private static final long serialVersionUID = -8717369149028464745L;

	private JButton singlePlayer, multiPlayer, arena, exit;
	private JLabel title;

	public MainMenuPage(final Controller controller) {
		super(controller);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		final Font buttonFont = new Font("Segoe UI", 0, 32);
		final Dimension buttonDimension = new Dimension(400, 80);

		this.title = new JLabel("Project Gipf");
		this.title.setOpaque(false);
		this.title.setHorizontalAlignment(JLabel.CENTER);
		this.title.setFont(new Font("Segoe UI", 1, 60));
		this.title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		this.singlePlayer = new JButton("Singleplayer");
		this.singlePlayer.setContentAreaFilled(false);
		this.singlePlayer.setAlignmentX(JButton.CENTER_ALIGNMENT);
		this.singlePlayer.setFont(buttonFont);
		this.singlePlayer.setFocusPainted(false);
		this.singlePlayer.setPreferredSize(buttonDimension);
		this.singlePlayer.setMinimumSize(buttonDimension);
		this.singlePlayer.setMaximumSize(buttonDimension);
		this.multiPlayer = new JButton("Multiplayer");
		this.multiPlayer.setContentAreaFilled(false);
		this.multiPlayer.setAlignmentX(JButton.CENTER_ALIGNMENT);
		this.multiPlayer.setFont(buttonFont);
		this.multiPlayer.setFocusPainted(false);
		this.multiPlayer.setPreferredSize(buttonDimension);
		this.multiPlayer.setMinimumSize(buttonDimension);
		this.multiPlayer.setMaximumSize(buttonDimension);
		this.arena = new JButton("Arena");
		this.arena.setContentAreaFilled(false);
		this.arena.setAlignmentX(JButton.CENTER_ALIGNMENT);
		this.arena.setFont(buttonFont);
		this.arena.setFocusPainted(false);
		this.arena.setPreferredSize(buttonDimension);
		this.arena.setMinimumSize(buttonDimension);
		this.arena.setMaximumSize(buttonDimension);
		this.exit = new JButton("Exit");
		this.exit.setContentAreaFilled(false);
		this.exit.setAlignmentX(JButton.CENTER_ALIGNMENT);
		this.exit.setFont(buttonFont);
		this.exit.setFocusPainted(false);
		this.exit.setPreferredSize(buttonDimension);
		this.exit.setMinimumSize(buttonDimension);
		this.exit.setMaximumSize(buttonDimension);

		this.singlePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showMenuPage(2);
			}
		});
		this.multiPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showMenuPage(1);
			}
		});
		this.arena.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showMenuPage(4);
			}
		});
		this.exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalStrut(50));
		box.add(this.title);
		box.add(Box.createVerticalStrut(5));
		box.add(Box.createVerticalGlue());
		box.add(Box.createVerticalStrut(100));
		box.add(this.singlePlayer);
		box.add(Box.createVerticalStrut(35));
		box.add(Box.createVerticalGlue());
		box.add(this.multiPlayer);
		box.add(Box.createVerticalStrut(35));
		box.add(Box.createVerticalGlue());
		box.add(this.arena);
		box.add(Box.createVerticalStrut(35));
		box.add(Box.createVerticalGlue());
		box.add(this.exit);
		box.add(Box.createVerticalStrut(125));

		this.add(box);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		Point2D center = new Point2D.Float(this.getWidth() / 2, this.getHeight() / 2);
		float radius = this.getWidth() / 2;
		float[] dist = { 0.6f, 0.8f };
		Color[] colors = { new Color(25, 207, 67), new Color(23, 178, 67) };
		RadialGradientPaint gp = new RadialGradientPaint(center, radius, dist, colors);
		g2.setPaint(gp);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
}
