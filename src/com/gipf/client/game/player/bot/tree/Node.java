package com.gipf.client.game.player.bot.tree;

import java.util.ArrayList;

import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.offline.logic.Game;

public class Node {

	private Action action;
	private Game game;
	
	private Node parent;
	private ArrayList<Node> children;

	private int value;
	private boolean endState; // the state at the end of 1 turn

	public Node(Node parent, Game game, Action action, boolean endState) {
		this.parent = parent;
		this.action = action;
		this.endState = endState;
		this.children = new ArrayList<Node>();
	}

	public void addChild(Node node) {
		this.children.add(node);
	}

	public Node getParent() {
		return this.parent;
	}

	public void setParent(Node node) {
		this.parent = node;
	}

	public ArrayList<Node> getChildren() {
		return this.children;
	}

	public int getValue() {
		return this.value;
	}

	public Action getAction() {
		return this.action;
	}

	public Game getGame() {
		return this.game;
	}

	public boolean getEndState() {
		return this.endState;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public void setEndState(boolean bool) {
		this.endState = bool;
	}
}
