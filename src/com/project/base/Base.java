package com.project.base;

import com.project.logic.Game;
import com.project.logic.StandardGameLogic;

public class Base {

	public static void main(String[] args) {
		Game game = new Game();
		
		StandardGameLogic logic = new StandardGameLogic(game);
		game.setGameLogic(logic);
		
		logic.checkForLines();
		
		game.getBoard().print();
	}
}
