package com.gipf.client.player.bot.algorithm.withouttreegeneration;

import java.util.ArrayList;

import com.gipf.client.game.player.bot.action.Action;
import com.gipf.client.game.player.bot.tree.Node;
import com.gipf.client.utils.Point;

public class ActionGenerator {

	private ArrayList<PointDuo> movePoints;

	public ActionGenerator() {
		this.movePoints = this.getMovePoints();
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
	
	public ArrayList<Action> getPossibleActions(Node node) {
		ArrayList<Action> result = new ArrayList<Action>();
		for (PointDuo pd : this.movePoints) {
			if (node.getGame().getBoard().isValidMove(pd.from, pd.to)) result.add(new Action(pd.from, pd.to));
		}
		return result;
	}
	
	public ArrayList<Action> getPossibleActions(Node node, int number) {
		ArrayList<Action> result = new ArrayList<Action>();
		for (int i = 0; i < number; i++) {
			PointDuo pd = this.movePoints.get((int) (Math.random() * this.movePoints.size()));
			if (node.getGame().getBoard().isValidMove(pd.from, pd.to)) result.add(new Action(pd.from, pd.to));
		}
		return result;
	}
	
	private class PointDuo {

		public Point from;
		public Point to;

		public PointDuo(Point from, Point to) {
			this.from = from;
			this.to = to;
		}
	}
}
