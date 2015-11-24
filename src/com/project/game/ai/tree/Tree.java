package com.project.game.ai.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import com.project.game.ai.node.Node;

public class Tree<E> {
	
	private Node<E> root;
	
	public Tree() {
		this.root = null;
	}
	
	public Tree(Node<E> node){
		this.root = node;
	}
	
	public Node<E> root() {
		return this.root;
	}

	public void setRoot(Node<E> root){
		this.root = root;
	}
	
	public Node<E> parent(Node<E> node) {
		return node.getParent();
	}
	
	public void setParent(Node<E> node, Node<E> parent){
		node.setParent(parent);
	}
	
	public boolean isInternal(Node<E> node) {
		return !node.getChildren().isEmpty();
	}

	public boolean isExternal(Node<E> node) {
		return node.getChildren().isEmpty();
	}

	public boolean isRoot(Node<E> node) {
		return (node.getParent() == null);
	}
	
	public ListIterator<Node<E>> children(Node<E> node) {
		return node.getChildren().listIterator();
	}

	public ArrayList<Node<E>> positions(Node<E> node) {
		ArrayList<Node<E>> result = new ArrayList<Node<E>>();
		result.add(node);
		Iterator<Node<E>> children = children(node);
		while(children.hasNext())
			result.addAll(positions((Node<E>) children.next()));
		return result;
	}

	public int size() {
		return count(root);
	}

	public boolean isEmpty() {
		return count(root) == 1;
	}
	
	public String preOrder(Node<E> node){
		String result = "";
		result += node.getValue();
		if(isInternal(node))
			for(Node<E> child: node.getChildren())
				result += "(" + preOrder(child) + ")";
			
		return result;
	}
	
	public String postOrder(Node<E> node){
		String result = "";
		if(isInternal(node))
			for(Node<E> child: node.getChildren())
				result += ("(" + postOrder(child) + ")");
				
		return result += node.getValue();
	}
	
	public ArrayList<Node<E>> bfSearch(Node<E> node){
		return bfSearch(node, new ArrayList<Node<E>>());
	}

	private ArrayList<Node<E>> bfSearch(Node<E> node, ArrayList<Node<E>> result){
		result.add(node);
		if(isInternal(node)){
			Iterator<Node<E>> children = this.children(node);
			while(children.hasNext())
				result.addAll(bfSearch(children.next()));
		}
		return result;
	}
	
	private int count(Node<E> root){
		int n = 0;
		if(isInternal(root)){
			for(Node<E> child: root.getChildren())
				n += count(child);
		}
		return n+1;
	}
}
