package com.gipf.client.player.bot.algorithm;

import java.util.ArrayList;
import java.util.Random;

import com.gipf.client.game.player.Player;
import com.gipf.client.player.bot.evaluation.EvaluationFunction;
import com.gipf.client.player.bot.generator.GameState;
import com.gipf.client.utils.Point;

public class MCTS extends Algorithm{
	private int iterations;
	private int simulationDepth;
	public MCTS(EvaluationFunction function, int maxIterations, int maxSimulationDepth){
		super(function);
		this.iterations = maxIterations;
		this.simulationDepth = maxSimulationDepth;
	}
	
	public Point[] returnBestMove(GameState gameState, Player player) {
		MCTSNode root = new MCTSNode(gameState, player);
		
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
			node.simulate();
		}
		GameState best = root.getMostVisitedChild().state;
		return new Point[]{best.getFromPoint(), best.getToPoint()};
	}
	
	private class MCTSNode{
		protected GameState state;
		protected MCTSNode parent;
		protected ArrayList<GameState> untriedMoves;
		protected Player player;
		protected ArrayList<MCTSNode> children;
		protected int score;
		protected int visits;
		private Random random;
		
		public MCTSNode(GameState state, MCTSNode parent, Player player){
			this.state = state;
			this.parent = parent;
			this.player = player;
			this.untriedMoves = generator.generateStates(state, player, game.getGameLogic());
			this.children = new ArrayList<MCTSNode>();
			random = new Random();
			
			score = 0;
			visits = 0;
		}
		
		public MCTSNode(GameState state, Player player){
			this(state, null, player);
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
			GameState state = this.untriedMoves.get(this.random.nextInt(this.untriedMoves.size()));
			MCTSNode child = new MCTSNode(state, this, getOtherPlayer(this.player));
			this.children.add(child);
			this.untriedMoves.remove(state);
			return child;
		}
		
		public void simulate(Player activePlayer){
			int depth = 0;
			GameState simState = this.state;
			Player simPlayer = this.player;
			while(!simState.getGame().isFinished() && depth < simulationDepth){
				depth += 1;
				ArrayList<GameState> moves = generator.generateStates(simState, simPlayer, simState.getGame().getGameLogic());
				simState = moves.get(random.nextInt(moves.size()));
				simPlayer = getOtherPlayer(simPlayer);
			}
			int score = evaluate(simState);
			//Make sure the score is always positive for the active player (the player for which we're running mcts).
			if(activePlayer == game.getPlayerTwo()){
				score = -score;
			}
			//TODO: normalize the score to a double between -1 and 1.
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
	
	private Player getOtherPlayer(Player player){
		Player otherPlayer = game.getPlayerOne();
		if(player.equals(otherPlayer)){
			otherPlayer = game.getPlayerTwo();
		}
		return otherPlayer;
	}
}
