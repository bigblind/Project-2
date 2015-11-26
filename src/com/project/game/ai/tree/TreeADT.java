package com.project.game.ai.tree;

import java.util.ListIterator;

import com.project.game.ai.node.Node;

public interface TreeADT {
	
	Node root();
	Node parent(Node node);
	ListIterator<Node> children(Node node);
	
	boolean isInternal(Node node);
	boolean isExternal(Node node);
	boolean isRoot(Node node);
	
	void swapElements(Node firstNode, Node secondNode);
	int replaceElement(Node oldNode, int newElement);
	
	ListIterator<Integer> elements();
	ListIterator<Node> positions();
	
	int size();
	boolean isEmpty();
	
}

