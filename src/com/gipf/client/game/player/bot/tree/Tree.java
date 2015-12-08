package com.gipf.client.game.player.bot.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class Tree {
	
	private Node root;
	
	public Tree() {
		this.root = null;
	}
	
	public Tree(Node node){
		this.root = node;
	}
	
	public Node root() {
		return this.root;
	}

	public void setRoot(Node root){
		this.root = root;
	}
	
	public Node parent(Node node) {
		return node.getParent();
	}
	
	public void setParent(Node node, Node parent){
		node.setParent(parent);
	}
	
	public boolean isInternal(Node node) {
		return !node.getChildren().isEmpty();
	}

	public boolean isExternal(Node node) {
		return node.getChildren().isEmpty();
	}

	public boolean isRoot(Node node) {
		return (node.getParent() == null);
	}
	
	public ListIterator<Node> children(Node node) {
		return node.getChildren().listIterator();
	}

	public ArrayList<Node> positions(Node node) {
		ArrayList<Node> result = new ArrayList<Node>();
		result.add(node);
		Iterator<Node> children = children(node);
		while(children.hasNext())
			result.addAll(positions((Node) children.next()));
		return result;
	}

	public int size() {
		return count(root);
	}

	public boolean isEmpty() {
		return count(root) == 1;
	}
	
	public String preOrder(Node node){
		String result = "";
		result += node.getValue();
		if(isInternal(node))
			for(Node child: node.getChildren())
				result += "(" + preOrder(child) + ")";
			
		return result;
	}
	
	public String postOrder(Node node){
		String result = "";
		if(isInternal(node))
			for(Node child: node.getChildren())
				result += ("(" + postOrder(child) + ")");
				
		return result += node.getValue();
	}
	
	public ArrayList<Node> bfSearch(Node node){
		return bfSearch(node, new ArrayList<Node>());
	}

	private ArrayList<Node> bfSearch(Node node, ArrayList<Node> result){
		result.add(node);
		if(isInternal(node)){
			Iterator<Node> children = this.children(node);
			while(children.hasNext())
				result.addAll(bfSearch(children.next()));
		}
		return result;
	}
	
	public ArrayList<Node> dfSearch(Node node, ArrayList<Node> result) {
		result.add(node);
		if (isInternal(node)) {
			Iterator<Node> children = this.children(node);
			while(children.hasNext()) {
				Node child = children.next();
				result.add(child);
				if (this.isInternal(child)) dfSearch(child, result);
			}
		}
		return result;
	}
	
	public ArrayList<Node> getExternalNodes(Node node){
		ArrayList<Node> result = new ArrayList<Node>();
		for(Node resultNode: bfSearch(node)){
			if(isExternal(resultNode)){
				result.add(resultNode);
			}
		}
		return result;
	}
	
	public ArrayList<Node> getEndChildren(Node node){
		ArrayList<Node> toSearch = node.getChildren();
		ArrayList<Node> toSearchNext = new ArrayList<Node>();
		ArrayList<Node> result = new ArrayList<Node>();
		while(!toSearch.isEmpty()){
			for(Node candidate: toSearch){
				if(candidate.getEndState()){
					result.add(candidate);
				}else{
					toSearchNext.addAll(candidate.getChildren());
				}
			}
			toSearch = toSearchNext;
		}
		return result;
	}
	
	public ArrayList<Node> getEndChildren(Node node, int turns){
		// Get the end states n turns down.
		// So passing in turns=1 will return the current player's possible moves,
		// passing in turns=2 will return the enemy's moves.
		ArrayList<Node> toSearch = new ArrayList<Node>();
		toSearch.add(node);
		while(turns > 0){
			turns -= 1;
			ArrayList<Node> toSearchNext = new ArrayList<Node>();
			for(Node currentNode: toSearch){
				toSearchNext.addAll(getEndChildren(currentNode));
			}
			toSearch = toSearchNext;
		}
		return toSearch;
	}
	
	private int count(Node root){
		int n = 0;
		if(isInternal(root)){
			for(Node child: root.getChildren())
				n += count(child);
		}
		return n+1;
	}
}
