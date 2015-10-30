package com.project.ai.tree;

import java.util.ArrayList;

import com.project.server.logic.board.Board;


public class Node<E> implements PositionADT<E>{

		private Node parent;
		private E pathCost;
		private E estimateCost;
		private ArrayList<Node> children;
		private Board board;
		
		
		
		public Node(E pathCost, Board board){
			this.parent = null;
			this.pathCost = pathCost;
			this.estimateCost = null;
			this.board = board;
			this.children = null;
		}
		
		
		
		public Node(Node parent, E pathCost, Board board){
			this.parent = parent;
			this.pathCost = pathCost;
			this.estimateCost = null;
			this.board = board;
			this.children = null;
		}
		
		
		
		public Node(Node parent, E pathCost, E estimateCost, Board board, ArrayList<Node> children){
			this.parent = parent;
			this.pathCost = pathCost;
			this.estimateCost = estimateCost;
			this.board = board;
			this.children = children;
		}
		
	
		public Node getParent(){
			return this.parent;
		} 
		
		public void setParent(Node newNode){
			this.parent = newNode;
		}
		
		public ArrayList<Node> getChildren(){
			return this.children;
		}
		
		public void setChildren(ArrayList<Node> children){
			this.children = children;
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
		

		
	
		
}
	

