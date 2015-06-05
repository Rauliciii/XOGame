package src.com.controller;

import src.com.model.GameHandle;

public class GameControllerImpl implements GameController {

	private GameHandle game;
	private int k;

	public GameControllerImpl(GameHandle gh) {
		this.game = gh;
	}

	public String valueAtPos(int i, int j) {
		return game.valueAtPos(i, j);
	}

	public void addValue(int i, int j, String value) {
		game.addValue(i, j, value);
	}

	public void setK(int k) {
		this.k = k;
	}

	public boolean isEmpty(int i, int j) {
		return game.isEmpty(i, j);
	}

	public boolean gameWon(String value) {
		if (straightWinn(value) == true)
			return true;
		if (diagonalWinnRight(value) == true) {
			return true;
		}
		return diagonalWinnLeft(value);
	}

	private boolean straightWinn(String value) {
		for (int i = 0; i < game.getDimension(); i++) {
			int hCount = k;
			int vCount = k;
			for (int j = 0; j < game.getDimension(); j++) {
				if (game.valueAtPos(i, j).equals(value)) {
					hCount--;
				} else {
					hCount = k;
				}
				if (game.valueAtPos(j, i).equals(value))
					vCount--;
				else
					vCount = k;
				if (hCount == 0 || vCount == 0)
					return true;
			}
		}
		return false;
	}

	private boolean diagonalWinnRight(String value) {
		int n = game.getDimension();
		for (int i = 0; i < n; i++) {
			int count = k;
			for (int j = n - 1; j >= 0; j--) {
				if (j - i == -1 || n - 1 - j == -1)
					break;
				// System.out.print(a[j - i][n - 1 - j] + " ");
				if (game.valueAtPos(j - i, n - 1 - j).equals(value)) {
					count--;
					if (count == 0)
						return true;
				} else {
					count = k;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("unused")
	private boolean diagonalWinnRightDown(String value) {
		int n = game.getDimension();
		for (int i = 0; i < n; i++) {
			int count = k;
			for (int j = n - 1; j >= 0; j--) {
				if (n - 1 + i - j == 6 || j == -1)
					break;
				// System.out.print(a[n-1+i-j][j] + " ");
				if (game.valueAtPos(n - 1 + i - j, j).equals(value)) {
					count--;
					if (count == 0)
						return true;
				} else
					count = k;
			}
		}
		return false;
	}

	private boolean diagonalWinnLeft(String value) {
		int n = game.getDimension();
		for (int i = n - 1; i >= 0; i--) {
			int dCountRightUp = k;
			int dCountLeftDown = k;
			for (int j = 0; j < n; j++) {
				if ((i + j) < n) {
					// System.out.print(a[k][j + k] + " ");
					if (game.valueAtPos(j, i + j).equals(value)) {
						dCountRightUp--;
						if (dCountRightUp == 0)
							return true;
					} else {
						dCountRightUp = k;
					}

					if (game.valueAtPos(j + i, j).equals(value)) {
						dCountLeftDown--;
						if (dCountLeftDown == 0)
							return true;
					} else {
						dCountLeftDown = k;
					}
				} else {
					break;
				}
			}
		}
		return false;
	}

	public int getDimension() {
		return game.getDimension();
	}

	@Override
	public String toString() {
		return "GameControllerImpl: \n" + game;
	}

	public int getK() {
		return this.k;
	}

}
