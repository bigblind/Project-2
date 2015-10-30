package com.project.ai.tree;

import java.util.ListIterator;

public interface TreeADT<E> {
	
	E root();
	E parent(E node);
	ListIterator<E> children(E element);
	
	boolean isInternal(E node);
	boolean isExternal(E node);
	boolean isRoot(E node);
	
	void swapElements(E firstNode, E secondNode);
	E replaceElement(E oldNode, E newElement);
	
	ListIterator<E> elements();
	ListIterator<E> positions();
	
	Integer size();
	boolean isEmpty();
	
}

