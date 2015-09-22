package com.project.logic;

public abstract class Board {

	public abstract void init();
	
	public abstract GridBoard copy();
	
	public abstract void print();
	
	public abstract void place(int stone, Point from, Point to);
	
	public abstract void push(Point from, Point to);

	public abstract void move(Point from, Point to);

	public abstract boolean isEmpty(Point point);

}
