package com.endava.gui;

public interface GameFrame {

	void displayMessage(String message);

	void setResources(int dim, int k, String type);

	void ClickAtPosition(String msgRecieved);

	void gameLost();
	
	void gameWon();

}
