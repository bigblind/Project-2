package com.project.visuals;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public abstract class MenuComponent extends JPanel {

	private static final long serialVersionUID = -4387322078788704466L;

	protected Dimension smallDimension, bigDimension;
	
	public abstract void goHome();
	
	public abstract void goBig();
	
	public abstract void addActionListener(ActionListener al);
}
