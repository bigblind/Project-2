package com.project.game.ai.node;

import java.util.ArrayList;

import com.gipf.client.offline.logic.Board;

public class Node<E> {

	private Node<E> parent;
	private Board board;

	private int value;

	private ArrayList<Node<E>> children;

	public Node(Board board, Node<E> parent) {
		this.children = new ArrayList<Node<E>>();
		this.parent = parent;
		this.board = board;
	}

	public Node<E> getParent() {
		return this.parent;
	}

	public void setParent(Node<E> newNode) {
		this.parent = newNode;
	}

	public ArrayList<Node<E>> getChildren() {
		return this.children;
	}

	public void addChildren(ArrayList<Node<E>> children) {
		this.children = children;
		for (Node<E> child : children)
			child.setParent(this);
	}

	public void addChild(Node<E> child) {
		this.children.add(child);
		child.setParent(this);
	}

	public boolean removeChild(Node<E> remove) {
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
