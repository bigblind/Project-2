package com.project.game.ai;

import java.util.ListIterator;

public class Tree<E> implements TreeADT {
	
	private Node root;
	private int size = 0;
	
	public Tree(Node node){
		this.root = node;
		this.size = 1;
	}
	
	public Node root() {
		return this.root;
	}

	public Node parent(Node node) {
		return node.getParent();
	}

	public ListIterator children(Object element) {
		// TODO Auto-generated method stub
		return null;
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

	public void swapElements(Node firstNode, Node secondNode) {
		E firstElement = (E) firstNode.element();
		E secondElement = (E) secondNode.element();
		firstNode.setElement(secondElement);
		secondNode.setElement(firstElement);
	}

	public Object replaceElement(Node oldNode, Object newElement) {
		E replaceable = (E) oldNode.element();
		oldNode.setElement(newElement);
		return replaceable;
	}

	public ListIterator elements() {
		// TODO Auto-generated method stub
		return null;
	}

	public ListIterator positions() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer size() {
		return size;
	}

	public boolean isEmpty() {
		return (root.getChildren().isEmpty());
	}



}
