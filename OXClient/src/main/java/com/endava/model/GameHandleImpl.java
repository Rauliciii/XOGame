package com.endava.model;

public class GameHandleImpl implements GameHandle {

	private String game[][];
	private int n;

	public String valueAtPos(int i, int j) {
		return game[i][j];
	}

	public void addValue(int i, int j, String value) {
		game[i][j] = value;
	}

	public void setDimension(int k) {
		game = new String[k][k];
		this.n = k;
		initializeGame();
	}

	private void initializeGame() {
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				game[i][j] = "-";
	}

	public boolean isEmpty(int i, int j) {
		if ("".equals(game[i][j]))
			return true;
		return false;
	}

	public int getDimension() {
		return this.n;
	}

	public String toString() {

		String s = "";
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				s += game[i][j] + " ";
			s += "\n";
		}
		return s;
	}

}
