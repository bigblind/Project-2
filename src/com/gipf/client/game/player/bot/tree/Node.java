package com.gipf.client.game.player.bot.tree;

import java.util.ArrayList;
import java.util.Iterator;

import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.offline.logic.Game;

public class Node implements Comparable<Node> {

	private Action action;
	private Game game;

	private Node parent;
	private ArrayList<Node> children;
	private ArrayList<Action> possibleActions;

	private int value;
	private boolean endState; // the state at the end of 1 turn

	public Node(Node parent, Game game, Action action, boolean endState) {
		this.game = game;
		this.parent = parent;
		this.action = action;
		this.endState = endState;
		this.children = new ArrayList<Node>();
		this.possibleActions = new ArrayList<Action>();
	}

	public Node copy(boolean includeChildren) {
//		Node p = null;
//		if (this.parent != null) {
//			p = this.parent.copy(includeChildren);
//		}
		
		//I'm reusing the same action object, since it will never be changed.
		Node n = new Node(this.parent, this.game.copy(), this.action, this.endState); // this was 	Node n = new Node(p, this.game.copy(...
		if (includeChildren) {
			ArrayList<Node> copiedChildren = new ArrayList<Node>(this.children.size());
			for (Node child : this.children) {
				child.parent = null;
				Node childCopy = child.copy(true);
				childCopy.parent = n;
				copiedChildren.add(childCopy);
				child.parent = this;
			}
			n.children = copiedChildren;
		}
		return n;
	}

	public Node copy() {
		return this.copy(false);
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

	public boolean print() {
		return print("", true, 0);
	}

	private boolean print(String prefix, boolean isTail, int d) {
		if (d > 20) {
			System.out.println("Maximum depth.");
			return false;
		}
		System.out.println(prefix + (isTail ? "└── " : "├── ") + getEndState() + " " + children.size());
		for (int i = 0; i < children.size() - 1; i++) {
			if (!children.get(i).print(prefix + (isTail ? "    " : "│   "), false, d + 1)) return false;
		}
		if (children.size() > 0) {
			if (!children.get(children.size() - 1).print(prefix + (isTail ? "    " : "│   "), true, d + 1)) return false;
		}
		return true;
	}
	
	public ArrayList<Node> bfSearch(Node node) {
		ArrayList<Node> result = new ArrayList<Node>();
		result.add(node);
		if (!this.children.isEmpty()) {
			Iterator<Node> children = node.getChildren().iterator();
			while (children.hasNext())
				result.addAll(bfSearch(children.next()));
		}
		return result;
	}
	
	public ArrayList<Node> bottomChildren() {
		ArrayList<Node> result = new ArrayList<Node>();
		if (this.children.isEmpty()) {
			result.add(this);
			return result;
		}
		ArrayList<Node> search = this.bfSearch(this);
		for (Node node : search) {
			if (node.getEndState() == true) result.add(node);
		}
		return result;
	}

	public int compareTo(Node node) {
		if (this.value > node.getValue()) return 1;
		else if (this.value == node.getValue()) return 0;
		else return -1;
	}
	
	public ArrayList<Action> getPossibleActions() {
		return this.possibleActions;
	}
	
	public void setPossibleActions(ArrayList<Action> actions) {
		this.possibleActions = actions;
	}
	
	public void setAction(Action action) {
		this.action = action;
	}
	
	public void removeChild(Node child) {
		this.children.remove(child);
	}
}
