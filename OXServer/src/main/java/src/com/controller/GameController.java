package src.com.controller;

public interface GameController {

	void setK(int k);
	
	int getK();
		
	String valueAtPos(int i, int j);
	
	void addValue(int i, int j, String value);
		
	int getDimension();
	
	boolean isEmpty(int i, int j);
	
	boolean gameWon(String value);
	
}
