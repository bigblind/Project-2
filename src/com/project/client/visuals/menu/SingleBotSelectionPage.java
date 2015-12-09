package com.project.client.visuals.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RadialGradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gipf.client.offline.logic.LocalServer;
import com.project.client.base.Controller;

public class SingleBotSelectionPage extends MenuPage {

	private static final long serialVersionUID = 634389002387668782L;

	private final BotSelectionPanel bot1Panel;
	private JLabel title;
	private JButton back, start;
	
	private String mode;
	
	public SingleBotSelectionPage(final Controller controller) {
		super(controller);
		
		final Font buttonFont = new Font("Segoe UI", 0, 32);
		final Dimension buttonDimension = new Dimension(400, 80);

		this.setLayout(new BorderLayout());
		
		JPanel top = new JPanel();
		top.setPreferredSize(new Dimension(2, 100));
		top.setOpaque(false);
		
		JPanel center = new JPanel();
		center.setLayout(new GridLayout());
		center.setOpaque(false);
		
		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(2, 150));
		south.setOpaque(false);
		
		this.add(top, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);
		
		this.bot1Panel = new BotSelectionPanel(controller, "Bot");
		center.add(bot1Panel);
		
		this.title = new JLabel("Project Gipf");
		this.title.setOpaque(false);
		this.title.setHorizontalAlignment(JLabel.CENTER);
		this.title.setFont(new Font("Segoe UI", 1, 60));
		this.title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		this.back = new JButton("Back");
		this.back.setContentAreaFilled(false);
		this.back.setAlignmentX(JButton.CENTER_ALIGNMENT);
		this.back.setFont(buttonFont);
		this.back.setFocusPainted(false);
		this.back.setPreferredSize(buttonDimension);
		this.back.setMinimumSize(buttonDimension);
		this.back.setMaximumSize(buttonDimension);
		this.start = new JButton("Start");
		this.start.setContentAreaFilled(false);
		this.start.setAlignmentX(JButton.CENTER_ALIGNMENT);
		this.start.setFont(buttonFont);
		this.start.setFocusPainted(false);
		this.start.setPreferredSize(buttonDimension);
		this.start.setMinimumSize(buttonDimension);
		this.start.setMaximumSize(buttonDimension);
		
		this.back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showMenuPage(2);
			}
		});
		this.start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.createLocalBotGame(new LocalServer(null, null, mode), bot1Panel.getBot());
				controller.showPanel(controller.getGamePanel());
				controller.getFrame().pack();
			}
		});
		
		south.setLayout(new BoxLayout(south, BoxLayout.X_AXIS));
		Box southBox = Box.createHorizontalBox();
		southBox.add(Box.createHorizontalStrut(10));
		southBox.add(Box.createHorizontalGlue());
		southBox.add(Box.createHorizontalStrut(10));
		southBox.add(this.start);
		southBox.add(Box.createHorizontalStrut(50));
		southBox.add(this.back);
		southBox.add(Box.createHorizontalStrut(10));
		southBox.add(Box.createHorizontalGlue());
		southBox.add(Box.createHorizontalStrut(10));
		south.add(southBox);
		
		top.add(this.title);
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
	
	public void setMode(String string) {
		this.mode = string;
	}
}
