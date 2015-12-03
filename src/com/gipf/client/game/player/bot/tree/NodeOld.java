package com.gipf.client.game.player.bot.tree;

import java.util.ArrayList;

import com.gipf.client.offline.logic.Board;

public class NodeOld {

	private Board board;
	private NodeOld parent;
	
	private int value;

	private ArrayList<NodeOld> children;

	public NodeOld(Board board, NodeOld parent) {
		this.children = new ArrayList<NodeOld>();
		this.parent = parent;
		this.board = board;
	}
	
	public NodeOld(Board board, int value) {
		this.children = new ArrayList<NodeOld>();
		this.value = value;
		this.board = board;
	}

	public NodeOld getParent() {
		return this.parent;
	}

	public void setParent(NodeOld newNode) {
		this.parent = newNode;
	}

	public ArrayList<NodeOld> getChildren() {
		return this.children;
	}

	public void addChildren(ArrayList<NodeOld> children) {
		this.children = children;
		for (NodeOld child : children)
			child.setParent(this);
	}

	public void addChild(NodeOld child) {
		this.children.add(child);
		child.setParent(this);
	}

	public boolean removeChild(NodeOld remove) {
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
