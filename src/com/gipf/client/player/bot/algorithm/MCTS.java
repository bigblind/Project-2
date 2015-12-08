package com.gipf.client.player.bot.algorithm;

import java.util.ArrayList;
import java.util.Random;

import com.gipf.client.game.player.Player;
import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;

public class MCTS extends Algorithm{
	private int iterations;
	private int simulationDepth;
	
	public MCTS(){
		this(1000, 100);
	}
	
	public MCTS(int maxIterations, int maxSimulationDepth){
		super();
		this.iterations = maxIterations;
		this.simulationDepth = maxSimulationDepth;
	}
	
	public ArrayList<Action> calculateBestActions(Tree tree, Bot player) {
		MCTSNode root = new MCTSNode(tree.root(), player, tree);
		
		
		for(int i=0; i < this.iterations; i++){
			//Selection phase
			MCTSNode node = root;
			while(node.untriedMoves.size() == 0 && node.children.size() != 0){
				node = node.selectChild();
			}
			
			//Expansion phase
			if(node.untriedMoves.size() != 0){
				node = node.expand();
			}
			
			//Simulation phase, includes backpropagation
			node.simulate(player);
		}
		
		Node best = root.getMostVisitedChild().state;
		return this.getActionsToNode(tree, best);
	}
	
	private class MCTSNode{
		protected Node state;
		protected MCTSNode parent;
		protected ArrayList<Node> untriedMoves;
		protected Bot player;
		protected ArrayList<MCTSNode> children;
		protected int score;
		protected int visits;
		private Random random;
		private Tree tree;
		
		public MCTSNode(Node state, MCTSNode parent, Bot player, Tree tree){
			this.state = state;
			this.parent = parent;
			this.player = player;
			this.tree = tree;
			this.untriedMoves = generateUntriedMoves();
			this.children = new ArrayList<MCTSNode>();
			random = new Random();
			
			score = 0;
			visits = 0;
		}
		
		public MCTSNode(Node state, Bot player, Tree tree){
			this(state, null, player, tree);
		}
		
		private ArrayList<Node> generateUntriedMoves(){
			return getNextLayers(state, tree, player);
		}
		
		public MCTSNode selectChild(){
			MCTSNode best = this.children.get(0);
			for(MCTSNode child: this.children){
				if(child.getUCB1() > best.getUCB1()){
					best = child;
				}
			}
			return best;
		}
		
		private double getUCB1(){
			//To understand this formula, see https://en.wikipedia.org/wiki/Monte_Carlo_tree_search#Exploration_and_exploitation
			return ((double)this.score / (double)this.visits) + Math.sqrt(2*Math.log(this.parent.visits)/this.visits);
		}
		
		public MCTSNode expand(){
			Node state = this.untriedMoves.get(this.random.nextInt(this.untriedMoves.size()));
			MCTSNode child = new MCTSNode(state, this, player, tree);
			this.children.add(child);
			this.untriedMoves.remove(state);
			return child;
		}
		
		public void simulate(Player activePlayer){
			int depth = 0;
			Node simState = this.state;
			while(!simState.getGame().isFinished() && depth < simulationDepth){
				depth += 1;
				ArrayList<Node> moves = getNextLayers(simState, tree, player);
				simState = moves.get(random.nextInt(moves.size()));
			}
			int score = simState.getValue();
			//Make sure the score is always calculated as if the current player is player 1 (positive=good.)
			if(activePlayer == simState.getGame().getPlayerTwo()){
				score = -score;
			}
			this.backPropagate(score);
		}
		
		private void backPropagate(int score){
			this.score += score;
			this.visits += 1;
			if(this.parent != null){
				this.parent.backPropagate(score);
			}
		}
		
		public MCTSNode getMostVisitedChild(){
			MCTSNode best = this.children.get(0);
			for(MCTSNode child: this.children){
				if(child.visits > best.visits){
					best = child;
				}
			}
			return best;
		}
	}
	
	private ArrayList<Node> getNextLayers(Node node, Tree tree, Bot bot){
		//Generate the next 2 layers of nodes if they aren't generated yet.
		if(tree.isExternal(node)){
			bot.getGenerator().generateTreeLayer(node, bot, bot.getLogic(), true);
		}
		return tree.getEndChildren(node, 2);
	}
}