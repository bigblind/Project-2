package com.project.game.ai.tree;

import java.util.ListIterator;

import com.project.game.ai.node.Node;

public interface TreeADT<E> {
	
	Node root();
	Node parent(Node node);
	ListIterator<E> children(E element);
	
	boolean isInternal(Node node);
	boolean isExternal(Node node);
	boolean isRoot(Node node);
	
	void swapElements(Node firstNode, Node secondNode);
	E replaceElement(Node oldNode, E newElement);
	
	ListIterator<E> elements();
	ListIterator<E> positions();
	
	Integer size();
	boolean isEmpty();
	
}

