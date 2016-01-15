package com.gipf.client.player.bot.algorithm;

import java.util.ArrayList;
import java.util.Random;

import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.offline.logic.Game;
import com.gipf.client.player.bot.evaluation.EvaluationFunction;

public class MCTS extends Algorithm {

	private long timeLimit;
	private int simulationDepth;

	public MCTS() {
		this(5000, 10);
	}

	public MCTS(long timeLimit, int maxSimulationDepth) {
		super();
		this.timeLimit = timeLimit;
		this.simulationDepth = maxSimulationDepth;
		this.name = "Monte Carlo Tree Search";
	}

	public ArrayList<Action> calculateBestActions(Game game, Bot player, EvaluationFunction evaluator) {
		Node rootNode = new Node(null, game, null, true);
		MCTSNode root = new MCTSNode(game, player, evaluator, rootNode);
		long startTime = System.currentTimeMillis();
		long endTime = startTime + timeLimit;
		int i = 0;
		while (System.currentTimeMillis() < endTime) {
			i += 1;

			//Selection phase
			MCTSNode node = root;
			while (node.untriedMoves.size() == 0 && node.children.size() != 0) {
				node = node.selectChild();
			}

			ArrayList<MCTSNode> nodes;
			if (node.untriedMoves.size() != 0) {
				nodes = node.expand();
			} else {
				nodes = new ArrayList<MCTSNode>();
				nodes.add(node);
			}

			//Simulation phase, includes backpropagation
			for (MCTSNode nodeToSimulate : nodes) {
				nodeToSimulate.simulate();
			}

			System.out.println("Iteration " + i + " done with " + ((endTime - System.currentTimeMillis()) / 1000.0) + "s left");
		}

		MCTSNode best = root.getMostVisitedChild();

		System.out.println("Algorithm done, finding actions");

		return getActionsToNode(best.node);
	}
	
	public Node calculateBestNode(Game game, Bot player, EvaluationFunction evaluator) {
		Node rootNode = new Node(null, game, null, true);
		MCTSNode root = new MCTSNode(game, player, evaluator, rootNode);
		long startTime = System.currentTimeMillis();
		long endTime = startTime + timeLimit;
		int i = 0;
		while (System.currentTimeMillis() < endTime) {
			i += 1;

			//Selection phase
			MCTSNode node = root;
			while (node.untriedMoves.size() == 0 && node.children.size() != 0) {
				node = node.selectChild();
			}

			ArrayList<MCTSNode> nodes;
			if (node.untriedMoves.size() != 0) {
				nodes = node.expand();
			} else {
				nodes = new ArrayList<MCTSNode>();
				nodes.add(node);
			}

			//Simulation phase, includes backpropagation
			for (MCTSNode nodeToSimulate : nodes) {
				nodeToSimulate.simulate();
			}

			System.out.println("Iteration " + i + " done with " + ((endTime - System.currentTimeMillis()) / 1000.0) + "s left");
		}

		MCTSNode best = root.getMostVisitedChild();

		System.out.println("Algorithm done, finding actions");

		return best.node;
	}

	private class MCTSNode {

		protected Game state;
		protected Action action;
		protected EvaluationFunction evaluator;
		protected MCTSNode parent;
		protected ArrayList<Action> untriedMoves;
		protected Bot player;
		protected ArrayList<MCTSNode> children;
		protected Node node;
		protected int score;
		protected int visits;
		private Random random;

		public MCTSNode(Game state, Action action, MCTSNode parent, Bot player, EvaluationFunction evaluator, Node normalNode) {
			this.state = state;
			this.parent = parent;
			this.player = player;
			this.action = action;
			this.evaluator = evaluator;
			this.node = normalNode;

			this.untriedMoves = generator.getPossibleActions(state);
			this.children = new ArrayList<MCTSNode>();
			this.random = new Random();

			score = 0;
			visits = 0;
		}

		public MCTSNode(Game state, Bot player, EvaluationFunction evaluation, Node node) {
			this(state, null, null, player, evaluation, node);
		}

		public MCTSNode selectChild() {
			MCTSNode best = this.children.get(0);
			for (MCTSNode child : this.children) {
				if (child.getUCB1() > best.getUCB1()) {
					best = child;
				}
			}
			return best;
		}

		private double getUCB1() {
			//To understand this formula, see https://en.wikipedia.org/wiki/Monte_Carlo_tree_search#Exploration_and_exploitation
			return ((double) this.score / (double) this.visits) + Math.sqrt(2 * Math.log(this.parent.visits) / this.visits);
		}

		public ArrayList<MCTSNode> expand() {
			Action action = this.untriedMoves.get(this.random.nextInt(this.untriedMoves.size()));
			Node newState = performAction(action, state.copy(), player, evaluator);
			this.node.addChild(newState);
			newState.setParent(this.node);
			ArrayList<Node> bottomNodes = newState.bottomChildren();
			ArrayList<MCTSNode> result = new ArrayList<MCTSNode>();

			for (Node bottomNode : bottomNodes) {
				MCTSNode child = new MCTSNode(bottomNode.getGame(), bottomNode.getAction(), this, player, evaluator, bottomNode);
				this.children.add(child);
				result.add(child);
			}

			this.untriedMoves.remove(action);
			//If this node is fully expanded, there's no need to hold on to the game instance.
			if (this.untriedMoves.isEmpty()) {
				this.state = null;
			}
			return result;
		}

		public void simulate() {
			Game simState = state;
			Bot simPlayer = player;
			int depth = 0;

			while (simState.isFinished() && depth < simulationDepth) {
				depth += 1;
				Node node = performAction(generator.getRandomAction(simState), simState, simPlayer, evaluator);
				if (!node.getEndState()) {
					ArrayList<Node> endStates = node.bottomChildren();
					node = endStates.get(random.nextInt(endStates.size()));
				}
				simState = node.getGame();
				simPlayer = (Bot) getOpponent(simPlayer, simState);
			}
			score = evaluator.evaluate(simState, player);

			this.backPropagate(score);
		}

		private void backPropagate(int score) {
			this.score += score;
			this.visits += 1;
			if (this.parent != null) {
				this.parent.backPropagate(score);
			}
		}

		public MCTSNode getMostVisitedChild() {
			MCTSNode best = this.children.get(0);
			for (MCTSNode child : this.children) {
				if (child.visits > best.visits) {
					best = child;
				}
			}
			return best;
		}
	}

}