package com.project.game.ai.node;

import java.util.ArrayList;

import com.gipf.client.player.bot.generator.GameState;

public class Node {

	private GameState gameState;
	private Node parent;
	
	private int value;

	private ArrayList<Node> children;

	public Node(int value){
		this.children = null;
		this.parent = null;
		this.value = value;
	}
	
	public Node(GameState gameState, Node parent) {
		this.children = new ArrayList<Node>();
		this.parent = parent;
		this.gameState = gameState;
	}
	
	public Node(GameState gameState, int value) {
		this.children = new ArrayList<Node>();
		this.value = value;
		this.gameState = gameState;
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

	public int element() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public boolean isRoot() {
		if (this.parent == null) return true;
		return false;
	}
	
	public GameState getGameState() {
		return this.gameState;
	}

	
}

