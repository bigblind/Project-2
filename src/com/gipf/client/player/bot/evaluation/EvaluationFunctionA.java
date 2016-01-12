package com.gipf.client.player.bot.evaluation;

import com.gipf.client.offline.logic.Board;

public class EvaluationFunctionA implements EvaluationFunction {

	public static final EvaluationFunctionA EQUAL_WEIGHTS = new EvaluationFunctionA("Equal Weights", 1, 1, 1, 1);
	public static final EvaluationFunctionA GREEDY_WEIGHTS_STONECOUNT = new EvaluationFunctionA("Stonecount", 0.1, 1.0, 0.1, 0.1);
	public static final EvaluationFunctionA GREEDY_WEIGHTS_CENTER = new EvaluationFunctionA("Center Location", 1.0, 0.1, 0.1, 0.1);
	public static final EvaluationFunctionA GREEDY_WEIGHTS_DIAGONAL = new EvaluationFunctionA("Diagonal Location", 0.1, 0.1, 1.0, 0.1);
	public static final EvaluationFunctionA GREEDY_WEIGHTS_LINEOFTHREE = new EvaluationFunctionA("Line Of Three", 0.1, 0.1, 0.1, 0.5);

	public double centerWeight = 0.28093113378083556;
	public double stoneCountWeight = 0.5360165855488239;
	public double diagonalWeight = 0.6612279213601735;
	public double lineOfThreeWeight = 0.5161235621909996;

	private double fitness;
	private int whiteBoardPieces = 0;
	private int blackBoardPieces = 0;
	private Board board;

	private String name;

	public EvaluationFunctionA(String name) {
		this.name = name;
	}

	public EvaluationFunctionA(String name, double centerWeight, double stoneCountWeight, double diagonalWeight, double lineOfThreeWeight) {
		this.centerWeight = centerWeight;
		this.stoneCountWeight = stoneCountWeight;
		this.diagonalWeight = diagonalWeight;
		this.lineOfThreeWeight = lineOfThreeWeight;
		this.name = name;
	}

	public int evaluate(Board board, int whiteStoneCnt, int blackStoneCnt, boolean isStandard) {
		this.board = board;

		if (whiteStoneCnt == 0) {
			return -100000;
		}
		if (blackStoneCnt == 0) {
			return 100000;
		}

		int boardValue = 0;

		int center = (int) (centerWeight * centerStones());
		int count = (int) (stoneCountWeight * ((whiteBoardPieces + whiteStoneCnt) - (blackBoardPieces + blackStoneCnt)));
		int diagonal = (int) (diagonalWeight * diagonal());
		int lineOf3 = (int) (lineOfThreeWeight * lineOf3(board.getGrid()));

		boardValue = count + center + diagonal + lineOf3;
		return boardValue;

	}

	private int centerStones() {
		int extBonus = 30;
		int mediumBonus = 50;
		int centerBonus = 100;
		int centerValue = 0;

		int extGipfBonus = 300;
		int mediumGipfBonus = 600;
		int centerGipfBonus = 1200;

		int[][] edgeCoor = new int[][] { { 1, 1 }, { 1, 2 }, { 1, 3 }, { 1, 4 }, { 2, 5 }, { 3, 6 }, { 4, 7 }, { 5, 7 }, { 6, 7 }, { 7, 7 }, { 7, 6 }, { 7, 5 }, { 7, 4 }, { 6, 3 }, { 5, 2 }, { 4, 1 }, { 3, 1 }, { 2, 1 } };
		int[][] externalCoor = new int[][] { { 2, 2 }, { 3, 2 }, { 4, 2 }, { 2, 3 }, { 2, 4 }, { 3, 5 }, { 4, 6 }, { 5, 6 }, { 6, 6 }, { 6, 5 }, { 6, 4 }, { 5, 3 } };
		int[][] mediumCoor = new int[][] { { 3, 3 }, { 3, 4 }, { 4, 5 }, { 5, 5 }, { 5, 4 }, { 4, 3 } };

		for (int i = 0; i < edgeCoor.length; i++) {
			int x = edgeCoor[i][0];
			int y = edgeCoor[i][1];
			if (board.getGrid()[x][y] == Board.WHITE_VALUE) {
				whiteBoardPieces++;
			} else if (board.getGrid()[x][y] == Board.BLACK_VALUE) {
				blackBoardPieces++;
			} else if (board.getGrid()[x][y] == Board.GIPF_WHITE_VALUE) {
				whiteBoardPieces += 2;
			} else if (board.getGrid()[x][y] == Board.GIPF_BLACK_VALUE) {
				blackBoardPieces += 2;
			}

		}

		for (int i = 0; i < externalCoor.length; i++) {
			int x = externalCoor[i][0];
			int y = externalCoor[i][1];

			if (board.getGrid()[x][y] == Board.WHITE_VALUE) {
				centerValue += extBonus;
				whiteBoardPieces++;

			} else if (board.getGrid()[x][y] == Board.BLACK_VALUE) {
				centerValue -= extBonus;
				blackBoardPieces++;
			} else if (board.getGrid()[x][y] == Board.GIPF_WHITE_VALUE) {
				centerValue -= extGipfBonus;
				whiteBoardPieces += 2;
			} else if (board.getGrid()[x][y] == Board.GIPF_BLACK_VALUE) {
				centerValue += extGipfBonus;
				blackBoardPieces += 2;
			}

		}
		for (int i = 0; i < mediumCoor.length; i++) {
			int x = mediumCoor[i][0];
			int y = mediumCoor[i][1];
			if (board.getGrid()[x][y] == Board.WHITE_VALUE) centerValue += mediumBonus;
			else if (board.getGrid()[x][y] == Board.BLACK_VALUE) centerValue -= mediumBonus;
			else if (board.getGrid()[x][y] == Board.GIPF_WHITE_VALUE) centerValue -= mediumGipfBonus;
			else if (board.getGrid()[x][y] == Board.GIPF_BLACK_VALUE) centerValue += mediumGipfBonus;

		}
		if (board.getGrid()[4][4] == Board.WHITE_VALUE) centerValue += centerBonus;
		else if (board.getGrid()[4][4] == Board.BLACK_VALUE) centerValue -= centerBonus;
		else if (board.getGrid()[4][4] == Board.GIPF_WHITE_VALUE) centerValue -= centerGipfBonus;
		else if (board.getGrid()[4][4] == Board.GIPF_BLACK_VALUE) centerValue += centerGipfBonus;

		return centerValue;
	}

	private int diagonal() {
		int diagonalValue = 0;
		int diagonalBonus = 30;
		int diagonalGipfBonus = 3000;

		for (int i = 1; i < 8; i++) {
			if (board.getGrid()[i][4] == Board.WHITE_VALUE) diagonalValue += diagonalBonus;
			else if (board.getGrid()[i][4] == Board.BLACK_VALUE) diagonalValue -= diagonalBonus;
			else if (board.getGrid()[i][4] == Board.WHITE_VALUE) diagonalValue -= diagonalGipfBonus;
			else if (board.getGrid()[i][4] == Board.BLACK_VALUE) diagonalValue += diagonalGipfBonus;
		}
		for (int i = 1; i < 8; i++) {
			if (board.getGrid()[4][i] == Board.WHITE_VALUE) diagonalValue += diagonalBonus;
			else if (board.getGrid()[4][i] == Board.BLACK_VALUE) diagonalValue -= diagonalBonus;
			else if (board.getGrid()[4][i] == Board.GIPF_WHITE_VALUE) diagonalValue -= diagonalGipfBonus;
			else if (board.getGrid()[4][i] == Board.GIPF_BLACK_VALUE) diagonalValue += diagonalGipfBonus;
		}
		for (int i = 1; i < 8; i++) {
			if (board.getGrid()[i][i] == Board.WHITE_VALUE) diagonalValue += diagonalBonus;
			else if (board.getGrid()[i][i] == Board.BLACK_VALUE) diagonalValue -= diagonalBonus;
			else if (board.getGrid()[i][i] == Board.GIPF_WHITE_VALUE) diagonalValue -= diagonalGipfBonus;
			else if (board.getGrid()[i][i] == Board.GIPF_BLACK_VALUE) diagonalValue += diagonalGipfBonus;
		}
		return diagonalValue;
	}

	private int lineOf3(int[][] grid) {
		int counter = 0;
		int prevValue = -1;
		int lineOf3Bonus = 50;
		int lineOf3GipfBonus = 500;
		int lineOf3Value = 0;

		for (int i = 1; i < grid.length - 1; i++) {
			for (int j = 1; j < grid[i].length; j++) {
				if (((prevValue == Board.WHITE_VALUE) || (prevValue == Board.GIPF_WHITE_VALUE)) && ((grid[i][j] == Board.WHITE_VALUE) || (grid[i][j] == Board.GIPF_WHITE_VALUE))) {
					counter++;
				} else if (((prevValue == Board.BLACK_VALUE) || (prevValue == Board.GIPF_BLACK_VALUE)) && ((grid[i][j] == Board.BLACK_VALUE) || (grid[i][j] == Board.GIPF_BLACK_VALUE))) {
					counter++;
				} else {
					if (counter >= 3) {
						if (prevValue == Board.WHITE_VALUE) lineOf3Value += lineOf3Bonus;
						else if (prevValue == Board.BLACK_VALUE) lineOf3Value -= lineOf3Bonus;
						else if (prevValue == Board.GIPF_WHITE_VALUE) lineOf3Value -= lineOf3GipfBonus;
						else if (prevValue == Board.GIPF_BLACK_VALUE) lineOf3Value += lineOf3GipfBonus;

					}

					prevValue = grid[i][j];
					if (prevValue > 0) counter = 1;
					else counter = 0;
				}
			}
			counter = 0;
			prevValue = -1;
		}

		for (int j = 1; j < grid[0].length - 1; j++) {
			for (int i = 1; i < grid.length; i++) {
				if (((prevValue == Board.WHITE_VALUE) || (prevValue == Board.GIPF_WHITE_VALUE)) && ((grid[i][j] == Board.WHITE_VALUE) || (grid[i][j] == Board.GIPF_WHITE_VALUE))) {
					counter++;
				} else if (((prevValue == Board.BLACK_VALUE) || (prevValue == Board.GIPF_BLACK_VALUE)) && ((grid[i][j] == Board.BLACK_VALUE) || (grid[i][j] == Board.GIPF_BLACK_VALUE))) {
					counter++;
				} else {
					if (counter >= 3) {
						if (prevValue == Board.WHITE_VALUE) lineOf3Value += lineOf3Bonus;
						else if (prevValue == Board.BLACK_VALUE) lineOf3Value -= lineOf3Bonus;
						else if (prevValue == Board.GIPF_WHITE_VALUE) lineOf3Value -= lineOf3GipfBonus;
						else if (prevValue == Board.GIPF_BLACK_VALUE) lineOf3Value += lineOf3GipfBonus;
					}

					prevValue = grid[i][j];
					if (prevValue > 0) counter = 1;
					else counter = 0;
				}
			}
			counter = 0;
			prevValue = -1;
		}

		for (int k = 0; k < 4; k++) {
			for (int l = 0; l < 5 + k; l++) {
				if (((prevValue == Board.WHITE_VALUE) || (prevValue == Board.GIPF_WHITE_VALUE)) && ((grid[l + 1][4 - k + l] == Board.WHITE_VALUE) || (grid[l + 1][4 - k + l] == Board.GIPF_WHITE_VALUE))) {
					counter++;
				} else if (((prevValue == Board.BLACK_VALUE) || (prevValue == Board.GIPF_BLACK_VALUE)) && ((grid[l + 1][4 - k + l] == Board.BLACK_VALUE) || (grid[l + 1][4 - k + l] == Board.GIPF_BLACK_VALUE))) {
					counter++;
				} else {
					if (counter >= 3) {
						if (prevValue == Board.WHITE_VALUE) lineOf3Value += lineOf3Bonus;
						else if (prevValue == Board.BLACK_VALUE) lineOf3Value -= lineOf3Bonus;
						else if (prevValue == Board.GIPF_WHITE_VALUE) lineOf3Value -= lineOf3GipfBonus;
						else if (prevValue == Board.GIPF_BLACK_VALUE) lineOf3Value += lineOf3GipfBonus;
					}

					prevValue = grid[l + 1][4 - k + l];
					if (prevValue > 0) counter = 1;
					else counter = 0;
				}
			}
			counter = 0;
			prevValue = -1;
		}

		for (int k = 0; k < 3; k++) {
			for (int l = 0; l < 7 - k; l++) {
				if (((prevValue == Board.WHITE_VALUE) || (prevValue == Board.GIPF_WHITE_VALUE)) && ((grid[2 + k + l][1 + l] == Board.WHITE_VALUE) || (grid[2 + k + l][1 + l] == Board.GIPF_WHITE_VALUE))) {
					counter++;
				} else if (((prevValue == Board.BLACK_VALUE) || (prevValue == Board.GIPF_BLACK_VALUE)) && ((grid[2 + k + l][1 + l] == Board.BLACK_VALUE) || (grid[2 + k + l][1 + l] == Board.GIPF_BLACK_VALUE))) {
					counter++;
				} else {
					if (counter >= 3) {
						if (prevValue == Board.WHITE_VALUE) lineOf3Value += lineOf3Bonus;
						else if (prevValue == Board.BLACK_VALUE) lineOf3Value -= lineOf3Bonus;
						else if (prevValue == Board.GIPF_WHITE_VALUE) lineOf3Value -= lineOf3GipfBonus;
						else if (prevValue == Board.GIPF_BLACK_VALUE) lineOf3Value += lineOf3GipfBonus;
					}

					prevValue = grid[2 + k + l][1 + l];
					if (prevValue > 0) counter = 1;
					else counter = 0;
				}
			}
			counter = 0;
			prevValue = -1;
		}
		return lineOf3Value;

	}

	public int getWhiteBoardStones() {
		return this.whiteBoardPieces;
	}

	public int getBlackBoardStones() {
		return this.blackBoardPieces;
	}

	public double getFitness() {
		return this.fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public String toString() {
		return this.name;
	}
}