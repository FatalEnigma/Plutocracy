package tests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import plutocracyGUI.GameBoard;

@RunWith(JUnit4.class)
public class GameBoardTest 
{
	@Test
	public void translateBoardPosition() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		// Get access to the private method translateBoardPosition().
		Method method = GameBoard.class.getDeclaredMethod("translateBoardPosition", int.class);
		method.setAccessible(true);
		
		// 
		Assert.assertEquals(0, method.invoke(null,0));
		Assert.assertEquals(1, method.invoke(null,1));
		Assert.assertEquals(2, method.invoke(null,2));
		Assert.assertEquals(3, method.invoke(null,3));
		Assert.assertEquals(4, method.invoke(null,4));
		Assert.assertEquals(5, method.invoke(null,5));
		Assert.assertEquals(6, method.invoke(null,6));
		Assert.assertEquals(7, method.invoke(null,7));
		Assert.assertEquals(8, method.invoke(null,8));
		Assert.assertEquals(17, method.invoke(null,9));
		Assert.assertEquals(26, method.invoke(null,10));
		Assert.assertEquals(35, method.invoke(null,11));
		Assert.assertEquals(44, method.invoke(null,12));
		Assert.assertEquals(53, method.invoke(null,13));
		Assert.assertEquals(62, method.invoke(null,14));
		Assert.assertEquals(71, method.invoke(null,15));
		Assert.assertEquals(80, method.invoke(null,16));
		Assert.assertEquals(79, method.invoke(null,17));
		Assert.assertEquals(78, method.invoke(null,18));
		Assert.assertEquals(77, method.invoke(null,19));
		Assert.assertEquals(76, method.invoke(null,20));
		Assert.assertEquals(75, method.invoke(null,21));
		Assert.assertEquals(74, method.invoke(null,22));
		Assert.assertEquals(73, method.invoke(null,23));
		Assert.assertEquals(72, method.invoke(null,24));
		Assert.assertEquals(63, method.invoke(null,25));
		Assert.assertEquals(54, method.invoke(null,26));
		Assert.assertEquals(45, method.invoke(null,27));
		Assert.assertEquals(36, method.invoke(null,28));
		Assert.assertEquals(27, method.invoke(null,29));
		Assert.assertEquals(18, method.invoke(null,30));
		Assert.assertEquals(9, method.invoke(null,31));
	}
}
