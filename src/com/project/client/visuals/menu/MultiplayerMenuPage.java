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
import com.project.client.visuals.frame.ConnectFrame;

public class MultiplayerMenuPage extends MenuPage {

	private static final long serialVersionUID = -8081059145854987079L;

	private JButton pvp, online, back;
	private JLabel title;

	public MultiplayerMenuPage(final Controller controller) {
		super(controller);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		final Font buttonFont = new Font("Segoe UI", 0, 32);
		final Dimension buttonDimension = new Dimension(400, 80);

		this.title = new JLabel("Project Gipf");
		this.title.setOpaque(false);
		this.title.setHorizontalAlignment(JLabel.CENTER);
		this.title.setFont(new Font("Segoe UI", 1, 60));
		this.title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		this.pvp = new JButton("Player vs Player");
		this.pvp.setContentAreaFilled(false);
		this.pvp.setAlignmentX(JButton.CENTER_ALIGNMENT);
		this.pvp.setFont(buttonFont);
		this.pvp.setFocusPainted(false);
		this.pvp.setPreferredSize(buttonDimension);
		this.pvp.setMinimumSize(buttonDimension);
		this.pvp.setMaximumSize(buttonDimension);
		this.online = new JButton("Online");
		this.online.setContentAreaFilled(false);
		this.online.setAlignmentX(JButton.CENTER_ALIGNMENT);
		this.online.setFont(buttonFont);
		this.online.setFocusPainted(false);
		this.online.setPreferredSize(buttonDimension);
		this.online.setMinimumSize(buttonDimension);
		this.online.setMaximumSize(buttonDimension);
		this.back = new JButton("Back");
		this.back.setContentAreaFilled(false);
		this.back.setAlignmentX(JButton.CENTER_ALIGNMENT);
		this.back.setFont(buttonFont);
		this.back.setFocusPainted(false);
		this.back.setPreferredSize(buttonDimension);
		this.back.setMinimumSize(buttonDimension);
		this.back.setMaximumSize(buttonDimension);
		
		this.pvp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showMenuPage(3);
			}
		});
		this.online.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConnectFrame frame = new ConnectFrame(controller);
				frame.setLocationRelativeTo(pvp);
				frame.setVisible(true);
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
		box.add(this.pvp);
		box.add(Box.createVerticalStrut(35));
		box.add(Box.createVerticalGlue());
		box.add(this.online);
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
