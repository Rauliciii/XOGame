package src.com.main;
import src.com.controller.GameController;
import src.com.controller.GameControllerImpl;
import src.com.model.GameHandle;
import src.com.model.GameHandleImpl;



public class App {
	static int DIM = 3;
	static int K = 3;

	
	public static void main(String[] args) {
		GameHandle game = new GameHandleImpl();
		game.setDimension(DIM);
		GameController ctr = new GameControllerImpl(game);
		ctr.setK(K);
		
		Thread t = new Thread(new Server(ctr));
		t.start();
		
	}

}
