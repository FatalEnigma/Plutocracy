package tests;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import plutocracy.BankBrand;
import plutocracy.CentralBank;
import plutocracy.Colour;
import plutocracy.Player;
import plutocracy.Plutocracy;
import plutocracy.PropertyType;
import plutocracy.Site;
import plutocracyGUI.GameBoard;
import plutocracyGUI.GameLog;

@RunWith(JUnit4.class)
public class PlayerTest 
{
	// Test Fixtures
	static Plutocracy game;
	
	@BeforeClass
	public static void setUp()
	{
		// Get the game.
		game = new Plutocracy();
				
		// Manually add players.
		game.getPlayers().add(new Player("Foo", BankBrand.BA, game));
		game.getPlayers().add(new Player("Bar", BankBrand.CB, game));
		game.getPlayers().add(new Player("Nom", BankBrand.JP, game));
		game.getPlayers().add(new Player("Sweet", BankBrand.ST, game));
		game.getPlayers().add(new Player("Troll", BankBrand.WF, game));
		
	}
	
	@Test
	public void symbolTest()
	{
		// Check to make sure the player symbols are not the same.
		for(Player p1 : game.getPlayers())
		{
			for(Player p2 : game.getPlayers())
			{
				if(!p1.equals(p2))
					Assert.assertNotSame(p1.getSymbol(), p2.getSymbol());
			}
		}
	}
	
	@Test
	public void bankBrand()
	{
		// Check to make sure the player symbols are not the same.
		for(Player p1 : game.getPlayers())
		{
			for(Player p2 : game.getPlayers())
			{
				if(!p1.equals(p2))
					Assert.assertNotSame(p1.getBankBrand(), p2.getBankBrand());
			}
		}
	}
	
	@Test
	public void getSites()
	{
		// Get a player.
		Player p = game.getPlayers().get(0);
		
		// Make sure the site list is initalised.
		Assert.assertNotNull(p.getOwnerSites());
		
		// Give the player 2 sites.
		p.getOwnerSites().add(new Site("TestSite1", 1000, 100, Colour.BLUE, game));
		p.getOwnerSites().add(new Site("TestSite2", 1000, 100, Colour.BLUE, game));
		
		Assert.assertEquals(2, p.getOwnerSites().size());
	}
	
	@Test
	public void moveAroundBoard() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException
	{
		//**** Test of the player can safely make it round the board. ****\\
		
		// Get access to the private method.
		Method increaseBoardPosition = Player.class.getDeclaredMethod("increaseBoardPosition", int.class);
		increaseBoardPosition.setAccessible(true);
		
		// Get the game.
		Plutocracy game = new Plutocracy();
		
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
		
		// Add the board to the game.
		Player p = new Player("lulzybar", BankBrand.CB, game);
		
		// Move the player round the board.
		for(int i = 0; i < 200; i++)
		{
			increaseBoardPosition.invoke(p, 1);
			Assert.assertTrue(p.getBoardPosition() < game.getBoardCells().size());
		}
		
		//**** Test if the player always receives money when they pass the Central Bank. ****\\
		// Remove all money from the central bank.
		game.getCentralBank().cashTransaction(199 - game.getCentralBank().getCash());
		
		game.getBoard().setLog(new GameLog());
		
		// Store the player's cash.
		int cash = p.getCash();
		
		// Move the player round the board and check if the player get's more cash when they pass round.
		for(int i = 0; i < 200; i++)
		{
			increaseBoardPosition.invoke(p, 1);
			Assert.assertTrue(p.getBoardPosition() < game.getBoardCells().size());
		}
		
		// FIXME: This fails here, apparently the player has the same cash?
		Assert.assertTrue(p.getCash() > cash);
	}
	
	@Test
	public void setBoardPosition()
	{
		// Get the game.
		Plutocracy game = new Plutocracy();
				
		// Get the Central bank.
		game.setCentralBank(new plutocracy.CentralBank(game));
				
		// Flood the board with blankCells.
		for(int i = 0; i < 32; i++)
			game.getBoardCells().add(new Site("Test"+i, 1000, 100, Colour.GREEN, game));
				
		// Add the board to the game.
		Player p = new Player("lulzybar", BankBrand.CB, game);	
		
		//Set the player's position.
		p.setBoardPosition(10);
		Assert.assertEquals(10, p.getBoardPosition());
		
		// Set it beyond the number of cells.
		p.setBoardPosition(2000);
		Assert.assertEquals(0, p.getBoardPosition());
	}
	
	@Test
	public void rollDice() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		// Get access to the private method.
		Method rollDice = Player.class.getDeclaredMethod("rollDice", int.class);
		rollDice.setAccessible(true);
		
		// Make sure each roll only returns between 1 and 6.
		for(int i = 1; i < 100; i++)
		{
			// Get the dice rolls.
			int[] results = (int[])rollDice.invoke(null, i);
		
			// Check each roll is between 1 and 6.
			for(int n : results)
			{
				Assert.assertTrue(n <= 6 && n >= 1);
			}
		}
	}
	
	@Test
	public void buySite()
	{
		// Get the game.
		Plutocracy game = new Plutocracy();
				
		// Get the Central bank.
		game.setCentralBank(new plutocracy.CentralBank(game));
				
		// Flood the board with blankCells.
		for(int i = 0; i < 32; i++)
			game.getBoardCells().add(new Site("Test"+i, 1000, 100, Colour.GREEN, game));
		
		game.setBoard(new GameBoard(game));
		game.getBoard().setLog(new GameLog());
		
		// Add a player.
		Player p = new Player("lulzybar", BankBrand.CB, game);	
		
		// Buy site number 10.
		p.setBoardPosition(10);
		p.buySite();
		
		Assert.assertEquals(p, ((Site)game.getBoardCells().get(10)).getOwner());
		Assert.assertTrue(p.getOwnerSites().contains(game.getBoardCells().get(10)));
	}
	
	@Test
	public void buyProperty()
	{
		// Get the game.
		Plutocracy game = new Plutocracy();
						
		// Get the Central bank.
		game.setCentralBank(new plutocracy.CentralBank(game));
						
		// Flood the board with blankCells.
		for(int i = 0; i < 32; i++)
			game.getBoardCells().add(new Site("Test"+i, 1000, 100, Colour.GREEN, game));
		
		game.setBoard(new GameBoard(game));
		game.getBoard().setLog(new GameLog());
		
		// Add a player.
		Player p = new Player("lulzybar", BankBrand.CB, game);
		
		// Buy site number 10.
		p.buySite((Site)game.getBoardCells().get(10));
		
		// Buy a property on the site.
		p.setBoardPosition(10);
		p.buyProperty();
		
		Assert.assertTrue(p.getOwnerSites().get(0).getProperties().size() > 0);	
	}
	
	@Test
	public void upgradeProperty()
	{
		// Get the game.
		Plutocracy game = new Plutocracy();
						
		// Get the Central bank.
		game.setCentralBank(new plutocracy.CentralBank(game));
						
		// Flood the board with blankCells.
		for(int i = 0; i < 32; i++)
			game.getBoardCells().add(new Site("Test"+i, 1000, 100, Colour.GREEN, game));
		
		game.setBoard(new GameBoard(game));
		game.getBoard().setLog(new GameLog());
		
		// Add a player.
		Player p = new Player("lulzybar", BankBrand.CB, game);
		
		// Buy site number 10.
		p.buySite((Site)game.getBoardCells().get(10));
		
		// Buy a property on the site.
		p.setBoardPosition(10);
		p.buyProperty();
		
		// Upgrade The Property.
		p.upgradeProperty();
		
		Assert.assertEquals(PropertyType.CONFERENCE_CENTRE, p.getOwnerSites().get(0).getProperties().get(0).getPropertyType());	
	}
	
	@Test
	public void payRent() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		// Get access to the private method.
		Method payRent = Player.class.getDeclaredMethod("payRent", Site.class);
		payRent.setAccessible(true);
				
		// Get the game.
		Plutocracy game = new Plutocracy();
								
		// Get the Central bank.
		game.setCentralBank(new plutocracy.CentralBank(game));
								
		// Flood the board with blankCells.
		for(int i = 0; i < 32; i++)
			game.getBoardCells().add(new Site("Test"+i, 1000, 100, Colour.GREEN, game));
		
		game.setBoard(new GameBoard(game));
		
		// Add a player.
		Player p = new Player("lulzybar", BankBrand.CB, game);
		Player p2 = new Player("lulzybar2", BankBrand.JP, game);
				
		// Buy site number 10.
		p.buySite((Site)game.getBoardCells().get(10));
		
		// Store both players cash.
		int cashP1 = p.getCash();
		int cashP2 = p2.getCash();
		int rent = ((Site)game.getBoardCells().get(10)).getRent();
		
		// Pay the rent.
		payRent.invoke(p2, (Site)game.getBoardCells().get(10));
		
		Assert.assertEquals(cashP2 - rent, p2.getCash());
		Assert.assertEquals(cashP1 + rent, p.getCash());
		
	}

}
