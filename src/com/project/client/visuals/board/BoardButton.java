package com.project.client.visuals.board;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JButton;

public class BoardButton extends JButton {

	private static final long serialVersionUID = -3216722706956588440L;

	private boolean isOuterDot;
	private boolean draw;
	private Image image;

	public void paintComponent(Graphics g2) {
		Graphics2D g = (Graphics2D) g2;
		if (this.draw && this.image != null) g.drawImage(this.image, 0, 0, this.getWidth(), this.getHeight(), null);
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public void setDraw(boolean draw) {
		this.draw = draw;
	}
	
	public boolean getDraw() {
		return this.draw;
	}
	
	public void setIsOuterDot(boolean bool) {
		this.isOuterDot = bool;
	}
	
	public boolean getIsOuterDot() {
		return this.isOuterDot;
	}
	
	public Image getImage() {
		return this.image;
	}
}
