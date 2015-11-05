package com.project.game.ai.tree;

import java.util.ListIterator;

import com.project.game.ai.node.Node;

public interface TreeADT<E> {
	
	Node<E> root();
	Node<E> parent(Node<E> node);
	ListIterator<Node<E>> children(Node<E> node);
	
	boolean isInternal(Node<E> node);
	boolean isExternal(Node<E> node);
	boolean isRoot(Node<E> node);
	
	void swapElements(Node<E> firstNode, Node<E> secondNode);
	E replaceElement(Node<E> oldNode, E newElement);
	
	ListIterator<E> elements();
	ListIterator<E> positions();
	
	int size();
	boolean isEmpty();
	
}

