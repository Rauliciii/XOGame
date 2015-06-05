package src.com.model;

public interface GameHandle {

	String valueAtPos(int i, int j);
	
	void addValue(int i, int j, String value);
	
	void setDimension(int k);
	
	int getDimension();
	
	boolean isEmpty(int i, int j);
		
}
