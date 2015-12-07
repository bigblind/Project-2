package com.gipf.client.game.player.bot.tree;

import java.util.ArrayList;

import com.gipf.client.offline.logic.Board;

public class Node {

	private Board board;
	private Node parent;
	
	private int value;

	private ArrayList<Node> children;

	public Node(Board board, Node parent) {
		this.children = new ArrayList<Node>();
		this.parent = parent;
		this.board = board;
	}
	
	public Node(Board board, int value) {
		this.children = new ArrayList<Node>();
		this.value = value;
		this.board = board;
	}

	public Node getParent() {
		return this.parent;
	}

	public void setParent(Node newNode) {
		this.parent = newNode;
	}

	public ArrayList<Node> getChildren() {
		return this.children;
	}

	public void addChildren(ArrayList<Node> children) {
		this.children = children;
		for (Node child : children)
			child.setParent(this);
	}

	public void addChild(Node child) {
		this.children.add(child);
		child.setParent(this);
	}

	public boolean removeChild(Node remove) {
		return children.remove(remove);
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Board getBoard() {
		return this.board;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public boolean isRoot() {
		if (this.parent == null) return true;
		return false;
	}
}
