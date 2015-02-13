package tests;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import plutocracy.*;
import plutocracyGUI.GameBoard;
import plutocracyGUI.GameLog;
@RunWith(JUnit4.class)
public class SiteTest
{
	Site siteA;
	Site siteB;
	Plutocracy game;
	
	@Before
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		// Get the game.
		game = new Plutocracy();
		
		// Use reflection to set the bank up to avoid null pointer exception
		Field field = Plutocracy.class.getDeclaredField("cb");
		field.setAccessible(true);
		
		field.set(game, new CentralBank(game));
				
		game.setBoard(new GameBoard(game));
		game.getBoard().setLog(new GameLog());
				
		// Get the Central bank.
		game.setCentralBank(new plutocracy.CentralBank(game));
				
		// Flood the board with blankCells.
		for(int i = 0; i < 32; i++)
			game.getBoardCells().add(new Site("Test"+i, 1000, 100, Colour.GREEN, game));
		
		//Declaring objects to test on
		siteA = new Site("SiteA", 241000, 500, Colour.RED, game);
		siteB = new Site("SiteB", 352000, 1000, Colour.ORANGE, game);
	}
	
	//Test to ensure colour of siteB is equal to the value we have set there
	@Test
	public void setColour()
	{
		Colour colour = Colour.BLUE;
		siteB.setColour(colour);
		Assert.assertEquals(colour, siteB.getColour());
	}
	
	//Test to ensure basePrice of siteA is equal to the value we have set there
	@Test
	public void setBasePrice()
	{
		int price = 134000;
		siteA.setBasePrice(price);
		Assert.assertEquals(price, siteA.getBasePrice());
	}
	
	//Test to ensure baseRent of siteB is equal to the value we have set there
	@Test
	public void setBaseRent()
	{
		int rent = 4000;
		siteB.setBaseRent(rent);
		Assert.assertEquals(rent, siteB.getRent());
	}
	
	//Tests constructor is assigning the correct values
	@Test
	public void Constructor()
	{
		Site siteC = new Site("SiteC", 425000, 2000, Colour.BLUE, game);
		Assert.assertEquals(425000, siteC.getBasePrice());
		Assert.assertEquals(2000, siteC.getRent());
		Assert.assertEquals(Colour.BLUE, siteC.getColour());
	}
}