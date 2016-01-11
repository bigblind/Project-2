package com.gipf.client.player.bot.generator;

import java.util.ArrayList;
import java.util.Random;

import com.gipf.client.game.player.Player;
import com.gipf.client.game.player.bot.BotLogic;
import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.game.player.bot.tree.Tree;
import com.gipf.client.offline.logic.Board;
import com.gipf.client.offline.logic.Game;
import com.gipf.client.utils.Point;

public class TreeGenerator {

	private ArrayList<PointDuo> movePoints;
	private Random random;

	public TreeGenerator() {
		this.movePoints = this.getMovePoints();
		this.random = new Random();
	}

	private class PointDuo {

		public Point from;
		public Point to;

		public PointDuo(Point from, Point to) {
			this.from = from;
			this.to = to;
		}
	}

	private ArrayList<PointDuo> getMovePoints() {
		ArrayList<PointDuo> result = new ArrayList<PointDuo>();

		Point from;
		Point to1, to2;

		//correct, bottom right
		for (int i = 5; i < 8; i++) {
			from = new Point(i, 8);
			to1 = new Point(i - 1, 7);
			to2 = new Point(i, 7);
			result.add(new PointDuo(from, to1));
			result.add(new PointDuo(from, to2));
		}

		// correct, right
		for (int j = 7; j >= 5; j--) {
			from = new Point(8, j);
			to1 = new Point(7, j - 1);
			to2 = new Point(7, j);
			result.add(new PointDuo(from, to1));
			result.add(new PointDuo(from, to2));
		}
		// correct, top left
		for (int i = 1; i < 4; i++) {
			from = new Point(i, 0);
			to1 = new Point(i, 1);
			to2 = new Point(i + 1, 1);
			result.add(new PointDuo(from, to1));
			result.add(new PointDuo(from, to2));
		}
		// correct, left
		for (int j = 1; j < 4; j++) {
			from = new Point(0, j);
			to1 = new Point(1, j);
			to2 = new Point(1, j + 1);
			result.add(new PointDuo(from, to1));
			result.add(new PointDuo(from, to2));
		}

		// top right
		from = new Point(5, 1);
		to1 = new Point(4, 1);
		to2 = new Point(5, 2);
		result.add(new PointDuo(from, to1));
		result.add(new PointDuo(from, to2));

		from = new Point(6, 2);
		to1 = new Point(5, 2);
		to2 = new Point(6, 3);
		result.add(new PointDuo(from, to1));
		result.add(new PointDuo(from, to2));

		from = new Point(7, 3);
		to1 = new Point(6, 3);
		to2 = new Point(7, 4);
		result.add(new PointDuo(from, to1));
		result.add(new PointDuo(from, to2));

		// bottom left
		from = new Point(1, 5);
		to1 = new Point(1, 4);
		to2 = new Point(2, 5);
		result.add(new PointDuo(from, to1));
		result.add(new PointDuo(from, to2));

		from = new Point(2, 6);
		to1 = new Point(2, 5);
		to2 = new Point(3, 6);
		result.add(new PointDuo(from, to1));
		result.add(new PointDuo(from, to2));

		from = new Point(3, 7);
		to1 = new Point(3, 6);
		to2 = new Point(4, 7);
		result.add(new PointDuo(from, to1));
		result.add(new PointDuo(from, to2));

		from = new Point(8, 4);
		to1 = new Point(7, 4);
		result.add(new PointDuo(from, to1));

		from = new Point(8, 8);
		to1 = new Point(7, 7);
		result.add(new PointDuo(from, to1));

		from = new Point(4, 8);
		to1 = new Point(4, 7);
		result.add(new PointDuo(from, to1));

		from = new Point(0, 4);
		to1 = new Point(1, 4);
		result.add(new PointDuo(from, to1));

		from = new Point(0, 0);
		to1 = new Point(1, 1);
		result.add(new PointDuo(from, to1));

		from = new Point(4, 0);
		to1 = new Point(4, 1);
		result.add(new PointDuo(from, to1));

		return result;
	}

	public void generateTree(int ply, Node root, Player player, BotLogic logic) {
		if (ply == 0) return;
		Player opponent;
		if (player.equals(root.getGame().getPlayerOne())) opponent = root.getGame().getPlayerTwo();
		else opponent = root.getGame().getPlayerOne();
		
		this.generateTreeLayer(root, player, logic, false);
		Tree tree = new Tree(root);
		
		ArrayList<Node> search = tree.bfSearch(root);
		for (Node child : search) {
			if (child.getEndState() && tree.isExternal(child)) this.generateTree(ply - 1, child, opponent, logic);
		}
	}

	public void generateTreeLayer(Node node, Player player, BotLogic logic, boolean initialRun) {
		Board board = node.getGame().getBoard();

		for (PointDuo pd : this.movePoints) {
			if (board.isValidMove(pd.from, pd.to)) {
				this.attachNode(node, player, pd.from, pd.to);
			}
		}

		this.checkForEndState(node, player, logic, true);
		if (initialRun) generateEnemyLayer(node, player, logic);
	}
	
	public void generateTreeLayer(Node node, Player player, BotLogic logic, boolean initialRun, int nonodes) {
		Board board = node.getGame().getBoard();

		int counter = 0;
		while (node.getChildren().size() < nonodes && counter < (nonodes * 5)) {
			counter++;
			PointDuo pd = this.movePoints.get((int) (Math.random() * this.movePoints.size()));
			if (board.isValidMove(pd.from, pd.to)) {
				this.attachNode(node, player, pd.from, pd.to);
			}
		}

		this.checkForEndState(node, player, logic, true);
		if (initialRun) generateEnemyLayer(node, player, logic);
	}

	private void generateEnemyLayer(Node rootNode, Player player, BotLogic logic) { // DUMBNESS SEARCH 
		Player opponent;
		System.out.println("you are a cunt and we are using it simon you are stupid you should have listened to F");
		for (Node child : rootNode.getChildren()) {
			if (child.getChildren().size() != 0) {
				Tree tree = new Tree(child);
				ArrayList<Node> search = tree.bfSearch(child);
				for (Node grandChild : search) {
					if (grandChild.getEndState()) {
						if (player.equals(rootNode.getGame().getPlayerOne())) opponent = grandChild.getGame().getPlayerTwo();
						else opponent = grandChild.getGame().getPlayerOne();
						this.generateTreeLayer(grandChild, opponent, logic, false);
					}
				}
			} else {
				if (player.equals(rootNode.getGame().getPlayerOne())) opponent = child.getGame().getPlayerTwo();
				else opponent = child.getGame().getPlayerOne();
				this.generateTreeLayer(child, opponent, logic, false);
			}
		}
	}

	public Node getRandomMove(Node node, Player player, BotLogic logic, boolean initialRun) {
		node = node.copy();
		Board board = node.getGame().getBoard();

		@SuppressWarnings("unchecked")
		ArrayList<PointDuo> pds = (ArrayList<PointDuo>) this.movePoints.clone();

		Node returnNode = null;
		while (!pds.isEmpty()) {
			PointDuo pd = pds.get(random.nextInt(pds.size()));
			pds.remove(pd);
			if (board.isValidMove(pd.from, pd.to)) {
				Node newNode = this.attachNode(node, player, pd.from, pd.to);
				returnNode = newNode;
				this.checkForEndState(newNode, player, logic, false);
				if (initialRun) {
					returnNode = getRandomEnemyMove(newNode, player, logic);
				}
				break;
			}
		}
		return returnNode;
	}

	private Node getRandomEnemyMove(Node node, Player player, BotLogic logic) {
		Player opponent;
		if (player.equals(node.getGame().getPlayerOne())) opponent = node.getGame().getPlayerTwo();
		else opponent = node.getGame().getPlayerOne();
		return getRandomMove(node, opponent, logic, false);
	}

	private Node attachNode(Node node, Player player, Point from, Point to) {
		Node newNode = nodeFromPoints(node, player, from, to);
		node.addChild(newNode);
		return newNode;
	}

	private Node nodeFromPoints(Node node, Player player, Point from, Point to) {
		Game tmp = node.getGame().copy();
		tmp.getBoard().place(player.getStoneColor(), from, to);
		if (player.equals(tmp.getPlayerOne())) tmp.getPlayerOne().addStones(-1);
		else tmp.getPlayerTwo().addStones(-1);
		return new Node(node, tmp, new Action(from, to), false);
	}

	private void checkForEndState(Node node, Player player, BotLogic logic, boolean forChildren) {
		if (forChildren) {
			for (Node child : node.getChildren()) {
				if (player.equals(child.getGame().getPlayerOne())) logic.performLogic(child.getGame().getPlayerOne(), child);
				else logic.performLogic(child.getGame().getPlayerTwo(), child);
			}
		} else {
			if (player.equals(node.getGame().getPlayerOne())) logic.performLogic(node.getGame().getPlayerOne(), node);
			else logic.performLogic(node.getGame().getPlayerTwo(), node);
		}
	}
}
