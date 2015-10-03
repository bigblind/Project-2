package com.project.client.board;

import java.util.ArrayList;

import com.project.common.utils.Point;

public class Board {

	public static int GIPF_WHITE_VALUE = 11;
	public static int GIPF_BLACK_VALUE = 22;
	public static int WHITE_VALUE = 1;
	public static int BLACK_VALUE = 2;
	public static int BOARD_EDGE = -1;
	public static int EMPTY_TILE = 0;
	public static int VOID_TILE = -2;

	private ArrayList<BoardChangeListener> listeners;
	
	private int[][] grid;

	public Board() {
		this.grid = new int[9][9];
		this.listeners = new ArrayList<BoardChangeListener>();
	}

	public Board(int[][] grid) {
		this.grid = grid;
		this.listeners = new ArrayList<BoardChangeListener>();
	}

	public Board copy() {
		int[][] gridCopy = new int[9][9];

		for (int i = 0; i < gridCopy.length; i++)
			for (int j = 0; j < gridCopy[0].length; j++)
				gridCopy[i][j] = this.grid[i][j];

		return new Board(gridCopy);
	}

	public void print() {
		for (int j = 0; j < this.grid[0].length; j++) {
			for (int i = 0; i < this.grid.length; i++) {
				if (this.grid[i][j] > -1)
					System.out.print(" ");
				System.out.print(this.grid[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public boolean isEmpty(Point point) {
		return this.grid[(int) point.getX()][(int) point.getY()] <= 0;
	}

	public int[][] getGrid() {
		return this.grid;
	}

	public void setGrid(int[][] grid) {
		this.grid = grid;
		this.notifyListeners();
	}
	
	public void addBoardChangeListener(BoardChangeListener listener) {
		this.listeners.add(listener);
	}

	public void removeBoardChangeListener(BoardChangeListener listener) {
		this.listeners.remove(listener);
	}
	
	public void notifyListeners() {
		for (BoardChangeListener l : this.listeners)
			l.boardChangeEventPerformed(new BoardChangeEvent(this));
	}

	public String toString() {
		String result = "";
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				result += this.grid[i][j] + " ";
			}
		}
		return result;
	}
}
