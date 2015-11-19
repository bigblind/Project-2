package com.project.game.ai.tree;

import java.util.ArrayList;

import com.project.game.ai.node.Node;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Node<Integer> root = new Node<Integer>(0,0);
		Node<Integer> one = new Node<Integer>(1,1);
		Node<Integer> two = new Node<Integer>(2,2);
		Node<Integer> three =new Node<Integer>(3,3);
		Node<Integer> four = new Node<Integer>(4,4);
		Node<Integer> five = new Node<Integer>(5,5);
		Node<Integer> six = new Node<Integer>(6,6);
		Node<Integer> seven = new Node<Integer>(7,7);
		Node<Integer> eight = new Node<Integer>(8,8);
		
		ArrayList<Node<Integer>> a = new ArrayList<Node<Integer>>();
		a.add(one);
		a.add(two);
		a.add(three);
		
		
		ArrayList<Node<Integer>> b = new ArrayList<Node<Integer>>();
		b.add(four);
		b.add(five);
		b.add(six);
		
		ArrayList<Node<Integer>> c = new ArrayList<Node<Integer>>();
		c.add(seven);
		c.add(eight);
		
		root.addChildren(a);
		one.addChildren(b);
		three.addChildren(c);
		
		Tree<Integer> tree = new Tree<Integer>(root);
		
		System.out.println(tree.preOrder(root));
		
		for(Node<Integer> node: tree.bfSearch(seven)){
			System.out.print(node.getEvalValue() + " | ");
		}
		
	}

}
