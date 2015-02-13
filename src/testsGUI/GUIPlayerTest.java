package testsGUI;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import plutocracy.*;

@RunWith(JUnit4.class)
public class GUIPlayerTest
{

	Plutocracy game = new Plutocracy();
	Player playerA = new Player("Ryan", BankBrand.BA, game);
	Player playerB = new Player("Glen", BankBrand.CB, game);
	
	@Test
	public void setGetName()
	{
		playerA.setName("Ryan");
		Assert.assertEquals("Ryan", playerA.getName());
	}	
	
	@Test
	public void setGetBoardPosition()
	{
		//FIXME: Seems to fail here, not sure why
		playerA.setBoardPosition(5);
		Assert.assertEquals(5, playerA.getBoardPosition());
	}		
	
	@Test
	public void incBoardPosition() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		// Get access to the private method translateBoardPosition().
		Method method = Player.class.getDeclaredMethod("increaseBoardPosition", int.class);
		method.setAccessible(true);
		
		//FIXME: This test fails, not sure why
		playerA.setBoardPosition(5);
		Assert.assertEquals(5, playerA.getBoardPosition());
		method.invoke(null,5);
		Assert.assertEquals(10, playerA.getBoardPosition());
		

		playerA.setBoardPosition(5);
		Assert.assertEquals(5, playerA.getBoardPosition());
		method.invoke(null,-5);
		Assert.assertEquals(0, playerA.getBoardPosition());
		

		playerA.setBoardPosition(2);
		Assert.assertEquals(2, playerA.getBoardPosition());
		method.invoke(null,-3);
		Assert.assertEquals(31, playerA.getBoardPosition());
		

		playerA.setBoardPosition(31);
		Assert.assertEquals(31, playerA.getBoardPosition());
		method.invoke(null,3);
		Assert.assertEquals(2, playerA.getBoardPosition());
	}
}
