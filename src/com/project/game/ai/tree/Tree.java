package com.project.game.ai.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import com.project.game.ai.node.Node;



public class Tree implements TreeADT {
	
	private Node root;
	
	public Tree(){
		this.root = null;
	}
	
	public Tree(Node node){
		this.root = node;
	}
	
	public Node root() {
		return this.root;
	}

	public void setNode(Node root){
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


	public void swapElements(Node firstNode, Node secondNode) {
		int firstElement = firstNode.element();
		int secondElement = secondNode.element();
		firstNode.setValue(secondElement);
		secondNode.setValue(firstElement);
	}

	public int replaceElement(Node oldNode, int newElement) {
		int replaceable = oldNode.element();
		oldNode.setValue(newElement);
		return replaceable;
	}

	public ListIterator<Integer> elements() {
		return elements(root()).listIterator();
	}
	
	private ArrayList<Integer> elements(Node node){
		ArrayList<Integer> result = new ArrayList<Integer>();
		result.add(node.element());
		Iterator<Node> children = children(node);
		while(children.hasNext())
			result.addAll(elements((Node) children.next()));
		return result;
	}
	
	public ListIterator<Node> positions(){
		return positions(root()).listIterator();
	}

	private ArrayList<Node> positions(Node node) {
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
		result += node.element();
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
				
		return result += node.element();
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
	

	
	private int count(Node root){
		int n = 0;
		if(isInternal(root)){
			for(Node child: root.getChildren())
				n += count(child);
		}
		return n+1;
	}




}
