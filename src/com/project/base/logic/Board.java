package com.project.base.logic;

public class Board {

//	if (yy > 0) 
//		if (xx == 0) vertically down
//		if (xx > 0) diagonal left top to right down
//		if (xx < 0) diagonal right top to left down
//		
//	if (yy < 0) 
//		if (xx == 0) vertically up
//		if (xx > 0) diagonal left bottom to right top
//		if (xx < 0) diagonal right bottom to left top

	public static int GIPF_WHITE_VALUE = 11;
	public static int GIPF_BLACK_VALUE = 22;
	public static int WHITE_VALUE = 1;
	public static int BLACK_VALUE = 2;
	public static int BOARD_EDGE = -1;
	public static int EMPTY_TILE = 0;
	public static int VOID_TILE = -2;

	private int[][] grid;

	public Board() {
		this.init();
	}

	public Board(int[][] grid) {
		this.grid = grid;
	}

	public void init() {
		this.grid = new int[9][9];
		
		for (int j = 8; j > 4; j--) {
			for (int i = 0; i < 4 - (8 - j); i++) {
				this.grid[i][j] = VOID_TILE;
			}
		}

		for (int j = 0; j < 4; j++) {
			for (int i = 8; i > 4 + j; i--) {
				this.grid[i][j] = VOID_TILE;
			}
		}

		for (int i = 4; i < 9; i++) {
			this.grid[i][8] = BOARD_EDGE;
		}
		
		for (int j = 8; j >= 4; j--) {
			this.grid[8][j] = BOARD_EDGE;
		}
		
		for (int i = 0; i < 5; i++) {
			this.grid[i][0] = BOARD_EDGE;
		}
		
		for (int j = 0; j < 5; j++) {
			this.grid[0][j] = BOARD_EDGE;
		}
		
		this.grid[5][1] = BOARD_EDGE;
		this.grid[6][2] = BOARD_EDGE;
		this.grid[7][3] = BOARD_EDGE;
		
		this.grid[1][5] = BOARD_EDGE;
		this.grid[2][6] = BOARD_EDGE;
		this.grid[3][7] = BOARD_EDGE;
		
		// placing the stones
		this.grid[1][1] = WHITE_VALUE;
		this.grid[4][1] = BLACK_VALUE;
		this.grid[7][4] = WHITE_VALUE;
		this.grid[7][7] = BLACK_VALUE;
		this.grid[4][7] = WHITE_VALUE;
		this.grid[1][4] = BLACK_VALUE;
	}

	public Board copy() {
		int[][] gridCopy = new int[9][9];
		for (int i = 0; i < gridCopy.length; i++) {
			for (int j = 0; j < gridCopy[0].length; j++) {
				gridCopy[i][j] = this.grid[i][j];
			}
		}
		return new Board(gridCopy);
	}

	public void print() {
		for (int j = 0; j < this.grid[0].length; j++) {
			for (int i = 0; i < this.grid.length; i++) {
				if (this.grid[i][j] > -1) System.out.print(" ");
				System.out.print(this.grid[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void push(Point from, Point to) {
		int x1 = from.getX();
		int x2 = to.getX();
		int y1 = from.getY();
		int y2 = to.getY();
		int xx = (int) (x2 - x1);
		int yy = (int) (y2 - y1);

		if (!isEmpty(new Point(x2, y2))) push(to, new Point(x2 + xx, y2 + yy));
		else move(from, to);
	}

	public void move(Point from, Point to) {
		int x1 = from.getX();
		int x2 = to.getX();
		int y1 = from.getY();
		int y2 = to.getY();
		this.grid[x2][y2] = this.grid[x1][y1];
		this.grid[x1][y1] = 10;
	}

	public boolean isEmpty(Point point) {
		return this.grid[(int) point.getX()][(int) point.getY()] <= 0;
	}

	public int[][] getGrid() {
		return this.grid;
	}
}