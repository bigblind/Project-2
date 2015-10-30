package com.project.ai.tree;

import java.util.ArrayList;


public class Node<E> implements PositionADT<E>{

		private Node parent;
		private E pathCost;
		private E estimateCost;
		private ArrayList<Node> children;
		
		
		
		public Node(E pathCost){
			this.parent = null;
			this.pathCost = pathCost;
			this.estimateCost = null;
			this.children = null;
		}
		
		
		
		public Node(Node parent, E pathCost, E estimateCost, ArrayList<Node> children){
			this.parent = parent;
			this.pathCost = pathCost;
			this.estimateCost = estimateCost;
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
	

