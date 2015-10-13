package com.project.client.visuals.state;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.project.client.connection.ClientInterface;
import com.project.client.visuals.BoardPanel;
import com.project.common.utils.Point;

public class StandardGameRemoveState extends State {

	private MouseListener listener;
	private Point[][] rowPoints;
	private Point[] rows;

	
	public StandardGameRemoveState(BoardPanel boardPanel, ClientInterface clientInterface, Point[] row) {
		super(boardPanel, clientInterface);
		
		this.listener = new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				
			}

			public void mouseEntered(MouseEvent e) {
				
			}

			public void mouseExited(MouseEvent e) {
				
			}

			public void mousePressed(MouseEvent e) {
				
			}

			public void mouseReleased(MouseEvent e) {
				
			}
		};
	}
}
