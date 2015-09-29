package com.project.base;

import java.io.IOException;

import com.project.logic.Game;
import com.project.logic.gamelogic.StandardGameLogic;
import com.project.visuals.GameFrame;
import com.project.visuals.ResourceLoader;

public class Base {

	public static void main(String[] args) {
		Game game = new Game();

		StandardGameLogic logic = new StandardGameLogic(game);
		game.setGameLogic(logic);

		ResourceLoader loader = new ResourceLoader();
		try {
			loader.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		game.getBoard().print();
		
		GameFrame frame = new GameFrame(game);
		frame.setVisible(true);
	}
}
