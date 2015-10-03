package com.project.logic.board;

import java.util.ArrayList;

import com.project.logic.Point;
import com.project.logic.Row;
import com.project.logic.gamelogic.GameLogic;

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
	private GameLogic logic;

	public Board() {
		this.init();
	}
	
	public void setLogic(GameLogic l){
		logic = l;
	}

	public Board(int[][] grid) {
		this.grid = grid;
	}

	public void init() {
		this.grid = new int[9][9];

		for (int j = 8; j > 4; j--)
			for (int i = 0; i < 4 - (8 - j); i++)
				this.grid[i][j] = VOID_TILE;

		for (int j = 0; j < 4; j++)
			for (int i = 8; i > 4 + j; i--)
				this.grid[i][j] = VOID_TILE;

		for (int i = 4; i < 9; i++)
			this.grid[i][8] = BOARD_EDGE;

		for (int j = 8; j >= 4; j--)
			this.grid[8][j] = BOARD_EDGE;

		for (int i = 0; i < 5; i++)
			this.grid[i][0] = BOARD_EDGE;

		for (int j = 0; j < 5; j++)
			this.grid[0][j] = BOARD_EDGE;

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

		for (int i = 0; i < gridCopy.length; i++)
			for (int j = 0; j < gridCopy[0].length; j++)
				gridCopy[i][j] = this.grid[i][j];

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

	public void place(int stone, Point from, Point to) {
		System.out.println("Placing stone " + stone + " at point ("+to.getX()+","+to.getY()+")");
		this.push(from, to);
		this.grid[to.getX()][to.getY()] = stone;
	}

	public void push(Point from, Point to) {
		int x1 = from.getX();
		int x2 = to.getX();
		int y1 = from.getY();
		int y2 = to.getY();
		int xx = (int) (x2 - x1);
		int yy = (int) (y2 - y1);

		if (!isEmpty(new Point(x2, y2))) push(to, new Point(x2 + xx, y2 + yy));
		if (grid[x1][y1] > 0) move(from, to);
	}

	public void move(Point from, Point to) {
		int x1 = from.getX();
		int x2 = to.getX();
		int y1 = from.getY();
		int y2 = to.getY();
		this.grid[x2][y2] = this.grid[x1][y1];
		this.grid[x1][y1] = 0;
	}

	public boolean isEmpty(Point point) {
		return this.grid[(int) point.getX()][(int) point.getY()] <= 0;
	}

	public int[][] getGrid() {
		return this.grid;
	}
	
	public ArrayList<Row> checkForLines() {
		ArrayList<Row> lines = new ArrayList<Row>();
		System.out.println("Checking for rows");
		print();
		int lineStartX = -1, lineStartY = -1, lineEndX = -1, lineEndY = -1;

		int counter = -1;
		int prevValue = -1;

		// checks for vertical lines
		for (int i = 1; i < grid.length - 1; i++) {
			for (int j = 1; j < grid[i].length - 1; j++) {
				if (prevValue == grid[i][j] && prevValue > 0) counter++;
				else {
					prevValue = grid[i][j];
					if (counter >= 4) {
						lineEndX = i;
						lineEndY = j-1; //the current stone's color is different, so it doesn't count towards the row.
						lineStartX = i;
						lineStartY = j - counter;
						int[] extensionStones = getExtensionStones(new Point(lineStartX, lineStartY), new Point(lineEndX, lineEndY));
						lines.add(
								new Row(new Point(lineStartX, lineStartY),
										new Point(lineEndX, lineEndY),
										logic.checkPlayer(prevValue),
										counter,
										extensionStones[0],
										extensionStones[1]
									)
								);
						counter = 1;
					}
				}
			}
			counter = 0;
			prevValue = -1;
		}

		// checks for left down right up lines
		for (int j = 0; j < grid[0].length; j++) {
			for (int i = 0; i < grid.length; i++) {
				if (prevValue == grid[i][j] && prevValue > 0) counter++;
				else {
					prevValue = grid[i][j];
					if (counter >= 4) {
						lineEndX = i-1;
						lineEndY = j;
						lineStartX = i - counter;
						lineStartY = j;
				
						int[] extensionStones = getExtensionStones(new Point(lineStartX, lineStartY), new Point(lineEndX, lineEndY));
						lines.add(
								new Row(new Point(lineStartX, lineStartY),
										new Point(lineEndX, lineEndY),
										logic.checkPlayer(prevValue),
										counter,
										extensionStones[0],
										extensionStones[1]
									)
								);
						counter = 1;
					}
				}
			}
			counter = 1;
			prevValue = -1;
		}

		// checks for lines left top right bottom
		for (int j = 3; j >= 0; j--) {
			for (int i = 1; i < (9 - j) - 1; i++) {
				if (prevValue == grid[i][j + i] && prevValue > 0) counter++;
				else {
					prevValue = grid[i][j + i];
					if (counter >= 4) {
						lineEndX = i-1;
						lineEndY = (j+1) + (i-1);
						lineStartX = i - counter;
						lineStartY = j - counter + i;
						
						int[] extensionStones = getExtensionStones(new Point(lineStartX, lineStartY), new Point(lineEndX, lineEndY));
						lines.add(
								new Row(new Point(lineStartX, lineStartY),
										new Point(lineEndX, lineEndY),
										logic.checkPlayer(prevValue),
										counter,
										extensionStones[0],
										extensionStones[1]
									)
								);
						counter = 1;
					}
				}
			}
			counter = 0;
			prevValue = -1;
		}
		
		//The maximum row length you can create is 7, by pushing a stone between 2 rows of 3.
		for (int i = 1; i < 7; i++) {
			for (int j = 1; j < (9 - i) - 1; j++) {
				if (prevValue == grid[i + j][j] && prevValue > 0) counter++;
				else {
					prevValue = grid[i + j][j];
					if (counter == 4) {
						lineEndX = i + (j-1);
						lineEndY = j-1;
					lineStartX = i - counter + j;
					lineStartY = j - counter;
					
					int[] extensionStones = getExtensionStones(new Point(lineStartX, lineStartY), new Point(lineEndX, lineEndY));
					lines.add(
							new Row(new Point(lineStartX, lineStartY),
									new Point(lineEndX, lineEndY),
									logic.checkPlayer(prevValue),
									counter,
									extensionStones[0],
									extensionStones[1]
								)
							);
					}
				}
			}
			counter = 0;
			prevValue = -1;
		}
		
		System.out.println("Result:");
		System.out.println(lines);
		return lines;
	}
	
	private int[] getExtensionStones(Point start, Point end){
		int white = 0;
		int black = 0;
		int deltaX = end.getX() - start.getX();
		int deltaY = end.getY() - start.getY();
		//normalize these deltas to 1 if it goes up, -1 if it goes down, 0 if it remains the same.
		if(deltaX > 0 ) deltaX = 1;
		else if(deltaX < 0) deltaX = -1;
		else deltaX = 0;
		if(deltaY > 0 ) deltaY = 1;
		else if(deltaY < 0) deltaY = -1;
		else deltaY = 0;
		
		Point connectedStart = findConnectionEnd(start, -deltaX, -deltaY);
		Point connectedEnd = findConnectionEnd(end, deltaX, deltaY);
		Point p = connectedStart;
		while(!(p.getX() == start.getX() && p.getY() == start.getY())){
			if(grid[p.getX()][p.getY()] == WHITE_VALUE){
				white += 1;
			}else{
				black += 1;
			}
			p = new Point(p.getX() + deltaX, p.getY() + deltaY);
		}
		p = end;
		while(!(p.getX() == connectedEnd.getX() && p.getY() == connectedEnd.getY())){
			p = new Point(p.getX() + deltaX, p.getY() + deltaY);
			if(grid[p.getX()][p.getY()] == WHITE_VALUE){
				white += 1;
			}else{
				black += 1;
			}
		}
		return new int[]{white, black};
	}
	
	public void removeRowAndExtensions(Row row){
		Point start = row.getFromPoint();
		Point end = row.getToPoint();
		int deltaX = end.getX() - start.getX();
		int deltaY = end.getY() - start.getY();
		//normalize these deltas to 1 if it goes up, -1 if it goes down, 0 if it remains the same.
		if(deltaX > 0 ) deltaX = 1;
		else if(deltaX < 0) deltaX = -1;
		else deltaX = 0;
		if(deltaY > 0 ) deltaY = 1;
		else if(deltaY < 0) deltaY = -1;
		else deltaY = 0;
		
		Point connectedStart = findConnectionEnd(start, -deltaX, -deltaY);
		Point connectedEnd = findConnectionEnd(end, deltaX, deltaY);
		removeLine(connectedStart, connectedEnd, deltaX, deltaY);
	}
	
	private Point findConnectionEnd(Point from, int deltaX, int deltaY){
		int i=1;
		Point 	p = from;
		while(!isEmpty(p)){
			int x = from.getX() + deltaX * i;
			int y = from.getY() + deltaY * i;
			p = new Point(x, y);
			i++;
		}
		return p;
	}
	
	private void removeLine(Point start, Point end, int deltaX, int deltaY){
		int x = start.getX();
		int y = start.getY();
		while(!(x == end.getX() && y == end.getY())){
			this.grid[x][y] = EMPTY_TILE;
			x += deltaX;
			y += deltaY;
		}
	}
}