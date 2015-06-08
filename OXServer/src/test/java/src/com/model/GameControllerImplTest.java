package src.com.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;


import src.com.controller.GameControllerImpl;

public class GameControllerImplTest {

	
	@InjectMocks
	private GameControllerImpl controller;
	
	@Mock
	private GameHandleImpl game;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		setUpGame();
		//when(userDataSource.findAll()).thenReturn(initUserList());
		controller.setK(3);
		game.setDimension(3);
		when(game.valueAtPos(0, 0)).thenReturn("X");
		when(game.valueAtPos(0, 1)).thenReturn("X");
		when(game.valueAtPos(0, 2)).thenReturn("X");

	}
	
	private void setUpGame() {
		//when(game.getDimension()).thenReturn(3);
	}

	@Test
	public void gameWonTest() {

		game.addValue(0, 0, "X");
		game.addValue(0, 1, "X");
		assertEquals(false, controller.gameWon("X"));
		game.addValue(0, 2, "X");
		//assertEquals(true, controller.gameWon("X"));
		
	}
	

}
