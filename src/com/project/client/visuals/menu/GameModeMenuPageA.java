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

import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.offline.logic.LocalServer;
import com.project.client.base.Controller;

public class GameModeMenuPageA extends MenuPage {

	private static final long serialVersionUID = -3991355653905765625L;

	private JButton basic, standard, back;
	private JLabel title;

	public GameModeMenuPageA(final Controller controller) {
		super(controller);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		final Font buttonFont = new Font("Segoe UI", 0, 32);
		final Dimension buttonDimension = new Dimension(400, 80);

		this.title = new JLabel("Project Gipf");
		this.title.setOpaque(false);
		this.title.setHorizontalAlignment(JLabel.CENTER);
		this.title.setFont(new Font("Segoe UI", 1, 60));
		this.title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		this.basic = new JButton("Basic mode");
		this.basic.setContentAreaFilled(false);
		this.basic.setAlignmentX(JButton.CENTER_ALIGNMENT);
		this.basic.setFont(buttonFont);
		this.basic.setFocusPainted(false);
		this.basic.setPreferredSize(buttonDimension);
		this.basic.setMinimumSize(buttonDimension);
		this.basic.setMaximumSize(buttonDimension);
		this.standard = new JButton("Standard mode");
		this.standard.setContentAreaFilled(false);
		this.standard.setAlignmentX(JButton.CENTER_ALIGNMENT);
		this.standard.setFont(buttonFont);
		this.standard.setFocusPainted(false);
		this.standard.setPreferredSize(buttonDimension);
		this.standard.setMinimumSize(buttonDimension);
		this.standard.setMaximumSize(buttonDimension);
		this.back = new JButton("Back");
		this.back.setContentAreaFilled(false);
		this.back.setAlignmentX(JButton.CENTER_ALIGNMENT);
		this.back.setFont(buttonFont);
		this.back.setFocusPainted(false);
		this.back.setPreferredSize(buttonDimension);
		this.back.setMinimumSize(buttonDimension);
		this.back.setMaximumSize(buttonDimension);
		
		this.basic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.createLocalBotGame(new LocalServer(null, null, "basic"), new Bot());
				controller.showPanel(controller.getGamePanel());
				controller.getFrame().pack();
			}
		});
		this.standard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.createLocalBotGame(new LocalServer(null, null, "standard"), new Bot());
				controller.showPanel(controller.getGamePanel());
				controller.getFrame().pack();
			}
		});
		this.back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showMenuPage(0);
			}
		});

		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalStrut(50));
		box.add(this.title);
		box.add(Box.createVerticalStrut(5));
		box.add(Box.createVerticalGlue());
		box.add(Box.createVerticalStrut(100));
		box.add(this.basic);
		box.add(Box.createVerticalStrut(35));
		box.add(Box.createVerticalGlue());
		box.add(this.standard);
		box.add(Box.createVerticalStrut(35));
		box.add(Box.createVerticalGlue());
		box.add(this.back);
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
