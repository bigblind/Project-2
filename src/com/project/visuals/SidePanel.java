package com.project.visuals;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SidePanel extends JPanel implements FocusListener {

	private static final long serialVersionUID = 4738543826353094709L;

	private Dimension small = new Dimension(50, 50), big = new Dimension(300, 50);
	private Color background;

	private JButton newGame;

	public SidePanel() {
		this.background = new Color(180, 0, 0);
		this.setBackground(this.background);
		this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.DARK_GRAY));

		this.newGame = new JButton("New Game");
		this.newGame.setFocusPainted(false);
		this.newGame.setBorder(BorderFactory.createEmptyBorder());
		this.newGame.setForeground(Color.BLACK);
		this.newGame.setFont(new Font("Segoe MP", 0, 20));
		this.newGame.setPreferredSize(new Dimension(200, 40));
		this.newGame.setHorizontalAlignment(JButton.CENTER);
		this.newGame.setAlignmentX(JButton.CENTER_ALIGNMENT);
		this.newGame.setBackground(this.background.darker());

		this.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setBackground(background);

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalStrut(20));
		box.add(newGame);
		box.add(Box.createVerticalStrut(20));
		box.add(Box.createVerticalGlue());
		panel.add(box);

		this.add(panel, BorderLayout.CENTER);

		BottomButtonPanel bottomButtonPanel = new BottomButtonPanel();
		bottomButtonPanel.setBackground(background);

		this.add(bottomButtonPanel, BorderLayout.SOUTH);

		this.goBig();
	}

	private void goBig() {
		this.setPreferredSize(big);
		this.revalidate();
	}

	private void goHome() {
		this.setPreferredSize(small);
		this.revalidate();
	}

	public void focusGained(FocusEvent e) {
		this.goBig();
	}

	public void focusLost(FocusEvent e) {
		this.goHome();
	}
}
