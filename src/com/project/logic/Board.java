package com.project.logic;

public abstract class Board {

	public abstract void init();
	
	public abstract GridBoard copy();
	
	public abstract void print();
	
	public void place(int stone, Point from, Point to);
	
	public void push(Point from, Point to) {

	public void move(Point from, Point to) {

	public boolean isEmpty(Point point) {

}
