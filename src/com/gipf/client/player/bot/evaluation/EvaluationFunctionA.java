package com.gipf.client.player.bot.evaluation;

import com.gipf.client.offline.logic.Board;

public class EvaluationFunctionA implements EvaluationFunction {

	private Board board;
	private int[][] grid;
	public static int CENTER_WEIGHT = 100;
	public static int STONECOUNT_WEIGHT = 200;
	public static int DIAGONAL_WEIGHT = 80;
	public static int LINE_OF_3_WEIGHT = 150;

	public EvaluationFunctionA() {

	}

	public int evaluate(Board tmp, int whiteStoneCnt, int blackStoneCnt) {
		this.grid = tmp.getGrid();
		this.board = tmp;
		int boardValue = 0;

		boardValue = STONECOUNT_WEIGHT * (whiteStoneCnt - blackStoneCnt) + CENTER_WEIGHT * centerStones() + DIAGONAL_WEIGHT * diagonal() + LINE_OF_3_WEIGHT * lineOf3();
		return boardValue;

	}

	private int centerStones() {
		int extBonus = 30;
		int mediumBonus = 50;
		int centerBonus = 100;
		int centerValue = 0;
		int[][] externalCoor = new int[][] { { 2, 2 }, { 3, 2 }, { 4, 2 }, { 2, 3 }, { 2, 4 }, { 3, 5 }, { 4, 6 }, { 5, 6 }, { 6, 6 }, { 6, 5 }, { 6, 4 }, { 5, 3 } };
		int[][] mediumCoor = new int[][] { { 3, 3 }, { 3, 4 }, { 4, 5 }, { 5, 5 }, { 5, 4 }, { 4, 3 } };
		for (int i = 0; i < 12; i++) {
			int x = externalCoor[i][0];
			int y = externalCoor[i][1];

			if (board.getGrid()[x][y] == Board.WHITE_VALUE) centerValue += extBonus;
			else if (board.getGrid()[x][y] == Board.BLACK_VALUE) centerValue -= extBonus;

		}
		for (int i = 0; i < 6; i++) {
			int x = mediumCoor[i][0];
			int y = mediumCoor[i][1];
			if (board.getGrid()[x][y] == Board.WHITE_VALUE) centerValue += mediumBonus;
			else if (board.getGrid()[x][y] == Board.BLACK_VALUE) centerValue -= mediumBonus;

		}
		if (board.getGrid()[4][4] == Board.WHITE_VALUE) centerValue += centerBonus;
		else if (board.getGrid()[4][4] == Board.BLACK_VALUE) centerValue -= centerBonus;

		return centerValue;
	}

	private int diagonal() {
		int diagonalValue = 0;
		int diagonalBonus = 30;
		int cnt = 1;

		for (int i = 1; i < 8; i++) {
			if (board.getGrid()[i][4] == Board.WHITE_VALUE) diagonalValue += diagonalBonus;
			else if (board.getGrid()[i][4] == Board.BLACK_VALUE) diagonalValue -= diagonalBonus;
		}
		for (int i = 1; i < 7; i++) {
			if (board.getGrid()[4][i] == Board.WHITE_VALUE) diagonalValue += diagonalBonus;
			else if (board.getGrid()[4][i] == Board.BLACK_VALUE) diagonalValue -= diagonalBonus;
		}
		for (int i = 1; i < 7; i++) {
			if (board.getGrid()[i][cnt] == Board.WHITE_VALUE) diagonalValue += diagonalBonus;
			else if (board.getGrid()[i][cnt] == Board.BLACK_VALUE) diagonalValue -= diagonalBonus;
			cnt++;
		}
		return diagonalValue;
	}

	private int lineOf3() {
		int counter = 0;
		int prevValue = -1;
		int lineOf3Bonus = 50;
		int lineOf3Value = 0;

		for (int i = 1; i < this.grid.length - 1; i++) {
			for (int j = 1; j < this.grid[i].length; j++) {
				if (((prevValue == Board.WHITE_VALUE) || (prevValue == Board.GIPF_WHITE_VALUE)) && ((this.grid[i][j] == Board.WHITE_VALUE) || (this.grid[i][j] == Board.GIPF_WHITE_VALUE))) {
					counter++;
				} else if (((prevValue == Board.BLACK_VALUE) || (prevValue == Board.GIPF_BLACK_VALUE)) && ((this.grid[i][j] == Board.BLACK_VALUE) || (this.grid[i][j] == Board.GIPF_BLACK_VALUE))) {
					counter++;
				} else {
					if (counter == 3) {
						if (prevValue == Board.WHITE_VALUE) lineOf3Value += lineOf3Bonus;
						else if (prevValue == Board.BLACK_VALUE) lineOf3Value -= lineOf3Bonus;
					}

					prevValue = this.grid[i][j];
					if (prevValue > 0) counter = 1;
					else counter = 0;
				}
			}
			counter = 0;
			prevValue = -1;
		}

		for (int j = 1; j < this.grid[0].length - 1; j++) {
			for (int i = 1; i < this.grid.length; i++) {
				if (((prevValue == Board.WHITE_VALUE) || (prevValue == Board.GIPF_WHITE_VALUE)) && ((this.grid[i][j] == Board.WHITE_VALUE) || (this.grid[i][j] == Board.GIPF_WHITE_VALUE))) {
					counter++;
				} else if (((prevValue == Board.BLACK_VALUE) || (prevValue == Board.GIPF_BLACK_VALUE)) && ((this.grid[i][j] == Board.BLACK_VALUE) || (this.grid[i][j] == Board.GIPF_BLACK_VALUE))) {
					counter++;
				} else {
					if (counter == 3) {
						if (prevValue == Board.WHITE_VALUE) lineOf3Value += lineOf3Bonus;
						else if (prevValue == Board.BLACK_VALUE) lineOf3Value -= Board.WHITE_VALUE;
					}

					prevValue = this.grid[i][j];
					if (prevValue > 0) counter = 1;
					else counter = 0;
				}
			}
			counter = 0;
			prevValue = -1;
		}

		for (int k = 0; k < 4; k++) {
			for (int l = 0; l < 5 + k; l++) {
				if (((prevValue == Board.WHITE_VALUE) || (prevValue == Board.GIPF_WHITE_VALUE)) && ((this.grid[l + 1][4 - k + l] == Board.WHITE_VALUE) || (this.grid[l + 1][4 - k + l] == Board.GIPF_WHITE_VALUE))) {
					counter++;
				} else if (((prevValue == Board.BLACK_VALUE) || (prevValue == Board.GIPF_BLACK_VALUE)) && ((this.grid[l + 1][4 - k + l] == Board.BLACK_VALUE) || (this.grid[l + 1][4 - k + l] == Board.GIPF_BLACK_VALUE))) {
					counter++;
				} else {
					if (counter == 3) {
						if (prevValue == Board.WHITE_VALUE) lineOf3Value += lineOf3Bonus;
						else if (prevValue == Board.BLACK_VALUE) lineOf3Value -= Board.WHITE_VALUE;
					}

					prevValue = this.grid[l + 1][4 - k + l];
					if (prevValue > 0) counter = 1;
					else counter = 0;
				}
			}
			counter = 0;
			prevValue = -1;
		}

		for (int k = 0; k < 3; k++) {
			for (int l = 0; l < 7 - k; l++) {
				if (((prevValue == Board.WHITE_VALUE) || (prevValue == Board.GIPF_WHITE_VALUE)) && ((this.grid[2 + k + l][1 + l] == Board.WHITE_VALUE) || (this.grid[2 + k + l][1 + l] == Board.GIPF_WHITE_VALUE))) {
					counter++;
				} else if (((prevValue == Board.BLACK_VALUE) || (prevValue == Board.GIPF_BLACK_VALUE)) && ((this.grid[2 + k + l][1 + l] == Board.BLACK_VALUE) || (this.grid[2 + k + l][1 + l] == Board.GIPF_BLACK_VALUE))) {
					counter++;
				} else {
					if (counter == 3) {
						if (prevValue == Board.WHITE_VALUE) lineOf3Value += lineOf3Bonus;
						else if (prevValue == Board.BLACK_VALUE) lineOf3Value -= Board.WHITE_VALUE;
					}

					prevValue = this.grid[2 + k + l][1 + l];
					if (prevValue > 0) counter = 1;
					else counter = 0;
				}
			}
			counter = 0;
			prevValue = -1;
		}
		return lineOf3Value;
	}
}