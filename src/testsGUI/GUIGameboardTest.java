/**
 * 
 */
package testsGUI;

import java.lang.reflect.Field;

import org.junit.Test;

import plutocracy.BankBrand;
import plutocracy.CentralBank;
import plutocracy.Player;
import plutocracy.Plutocracy;
import plutocracyGUI.GameBoard;

/**
 * @author gbrow_000
 *
 */
public class GUIGameboardTest {

	/**
	 * Test method for {@link plutocracyGUI.GameBoard#GameBoard(plutocracy.Plutocracy)}.
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Test
	public void testGameBoard() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException 
	{
		// Create a new game and lad in the XML files.
		Plutocracy p  = new Plutocracy();
		
		// Add players to the game.
		p.getPlayers().add(new Player("Ron", BankBrand.CB, p));
		p.getPlayers().add(new Player("Ron", BankBrand.BA, p));
		p.getPlayers().add(new Player("Ron", BankBrand.JP, p));
		//p.getPlayers().add(new Player("Ron", BankBrand.WF, p));
		
		// Use reflection to set the bank up to avoid null pointer exception
		Field field = Plutocracy.class.getDeclaredField("cb");
		field.setAccessible(true);
		
		field.set(p, new CentralBank(p));
		
		// Create the GUI.
		GameBoard g = new GameBoard(p);
		
		// Show the GUI and brick your PC for a bit.
		int i = 1;
		while(i>0)
		{
			i++;
		}
	}
}
