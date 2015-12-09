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
		this.game = game;
		this.parent = parent;
		this.action = action;
		this.endState = endState;
		this.children = new ArrayList<Node>();
	}
	
	public Node copy(boolean includeChildren){
		Node p = null;
		if(this.parent != null){
			p = this.parent.copy(includeChildren);
		}
		//I'm reusing the same action object, since it will never be changed.
		Node n = new Node(p, this.game.copy(), this.action, this.endState);
		if(includeChildren){
			ArrayList<Node> copiedChildren = new ArrayList<Node>(this.children.size());
			for(Node child: this.children){
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
	
	public Node copy(){
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
    	if(d > 20){
    		System.out.println("Maximum depth.");
    		return false;
    	}
        System.out.println(prefix + (isTail ? "└── " : "├── ") + getEndState() + " " + children.size());
        for (int i = 0; i < children.size() - 1; i++) {
            if(!children.get(i).print(prefix + (isTail ? "    " : "│   "), false, d+1))
            	return false;
        }
        if (children.size() > 0) {
            if(!children.get(children.size() - 1).print(prefix + (isTail ?"    " : "│   "), true, d+1))
            	return false;
        }
        return true;
    }
}
