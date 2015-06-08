package src.com.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;


public class GameHandleImplTest {

	@InjectMocks
	private GameHandleImpl gameHandle;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getDimensionTest() {
		assertEquals(0, gameHandle.getDimension());
	}

	@Test
	public void isEmptyTest() {
		gameHandle.setDimension(1);
		assertEquals(false, gameHandle.isEmpty(0, 0));
	}

	@Test
	public void dimensionTest() {
		gameHandle.setDimension(1);
		assertEquals(1, gameHandle.getDimension());
	}

	@Test
	public void gameHandleTest() {
		gameHandle.setDimension(2);
		assertEquals(2, gameHandle.getDimension());

		gameHandle.addValue(0, 0, "X");
		assertEquals("X", gameHandle.valueAtPos(0, 0));

		gameHandle.addValue(1, 0, "O");
		assertEquals("O", gameHandle.valueAtPos(1, 0));

	}

}
