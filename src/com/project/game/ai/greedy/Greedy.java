package com.project.game.ai.greedy;

import java.util.ArrayList;

import com.project.game.ai.node.Node;

public class Greedy<E> {
	public Node<E> run(ArrayList<Node<E>> nodes, boolean turnOfPlayer){
		if(turnOfPlayer){
			Node<E> best = nodes.get(0);
			for(Node<E> node: nodes){
				if(node.getEvalValue() > best.getEvalValue()){
					best = node;
				}
			}
			return best;
		}else{
			Node<E> best = nodes.get(0);
			for(Node<E> node: nodes){
				if(node.getEvalValue() < best.getEvalValue()){
					best = node;
				}
			}
			return best;
		}
	}
}
