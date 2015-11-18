package com.project.game.ai.node;

import java.util.ArrayList;

import com.project.client.board.Board;



public class Node<E> implements PositionADT<E>{

		private Node<E> parent;
		private E pathCost;
		private E estimateCost;
		private int evalValue;
		private ArrayList<Node<E>> children;
		private Board board;
		
		public Node(E pathCost){
			this.parent = null;
			this.pathCost = pathCost;
			this.estimateCost = null;
			this.board = null;
			this.children = new ArrayList<Node<E>>();
		}
		
		public Node(E pathCost, Node<E> parent){
			this.parent = parent;
			this.pathCost = pathCost;
			this.estimateCost = null;
			this.board = null;
			this.children = new ArrayList<Node<E>>();
		}
		
		public Node(E pathCost, Board board){
			this.parent = null;
			this.pathCost = pathCost;
			this.estimateCost = null;
			this.board = board;
			this.children = new ArrayList<Node<E>>();
		}
		
		
		
		public Node(E pathCost, Board board, Node<E> parent){
			this.parent = parent;
			this.pathCost = pathCost;
			this.estimateCost = null;
			this.board = board;
			this.children = new ArrayList<Node<E>>();
		}
		
		
		
		public Node(Node<E> parent, E pathCost, E estimateCost, Board board, ArrayList<Node<E>> children){
			this.parent = parent;
			this.pathCost = pathCost;
			this.estimateCost = estimateCost;
			this.board = board;
			this.children = new ArrayList<Node<E>>();
		}
		
	
		public Node<E> getParent(){
			return this.parent;
		} 
		
		public void setParent(Node<E> newNode){
			this.parent = newNode;
		}
		
		public ArrayList<Node<E>> getChildren(){
			return this.children;
		}
		
		public void addChildren(ArrayList<Node<E>> children){
			this.children = children;
			for(Node<E> child: children)
				child.setParent(this);
		}
		
		
		public void addChild(Node<E> child){
			this.children.add(child);
			child.setParent(this);
		}
		

		public boolean removeChild(Node<E> remove){
			return children.remove(remove);
		}
		
		public E element() {
			return this.pathCost;
		}
		
		public void setPathCost(E pathCost){
			this.pathCost = pathCost;
		}
		
		public E getEstimateCost(){
			return this.estimateCost;
		}
		
		public void setEstimateCost(E estimateCost){
			this.estimateCost = estimateCost;
		}
		
		public void setBoard(Board board){
			this.board = board;
		}
		
		public Board getBoard(){
			return this.board;
		}
		
		
		public int getEvalValue(){
			return this.evalValue;
		}
		
		public void setEvalValue(int evalValue){
			this.evalValue = evalValue;
		}
	
		
}
	

