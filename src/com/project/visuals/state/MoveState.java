package com.project.visuals.state;

import java.awt.event.MouseListener;

import com.project.logic.Game;
import com.project.visuals.GhostBoardButton;

public class MoveState extends State {

	public MoveState(GhostBoardButton[][] buttons, Game game) {
		super(buttons);
	}

	public void execute() {
		super.execute();
		
		for (int i = 0; i < 5; i++) {
			this.buttons[i][0].addMouseListener(new MouseListener() {
				
			});
		}
	}
}
