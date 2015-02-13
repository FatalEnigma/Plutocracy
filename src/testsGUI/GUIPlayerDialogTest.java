/**
 * 
 */
package testsGUI;

import org.junit.Assert;
import org.junit.Test;

import plutocracy.BankBrand;
import plutocracy.Player;
import plutocracy.Plutocracy;
import plutocracyGUI.Dialog;

/**
 * @author gbrow_000
 *
 */
public class GUIPlayerDialogTest {

    /**
     * Test method for {@link plutocracyGUI.Dialog#getPlayer(plutocracy.Plutocracy)}.
     */
    @Test
    public void testGetPlayer() 
    {
	// Get the game.
	Plutocracy p = new Plutocracy();

	// Set up the players.
	p.getPlayers().add(new Player("John", BankBrand.CB, p));
	p.getPlayers().add(new Player("Ron", BankBrand.BA, p));
	p.getPlayers().add(new Player("Don", BankBrand.JP, p));
	p.getPlayers().add(new Player("Nom", BankBrand.ST, p));
	Player p5 = Dialog.getPlayer(p);
	
	// Make sure the name is not null and that the bankbrand is the only one available.
	Assert.assertNotNull(p5.getName());
	Assert.assertEquals(BankBrand.WF, p5.getBankBrand());
    }
    
    @Test
    public void testgetHowManyPlayers()
    {
    	// Ensure the number of players is greater than 0
    	Assert.assertTrue(Dialog.getHowManyPlayers(1,5) > 0);
    }
    
}
