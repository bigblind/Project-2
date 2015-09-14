package com.project.base;

import com.project.base.logic.Board;
import com.project.base.logic.Point;

public class Base {

	public static void main(String[] args) {
		Board board = new Board();
		
		board.push(new Point(0, 0), new Point(1, 1));
		board.print();
	}
}
