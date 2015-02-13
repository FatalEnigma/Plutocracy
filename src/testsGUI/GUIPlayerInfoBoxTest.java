package testsGUI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import plutocracy.*;
import plutocracyGUI.*;

@RunWith(JUnit4.class)
public class GUIPlayerInfoBoxTest
{	
	Plutocracy p = new Plutocracy();
	Player playerA = new Player("Sandee", BankBrand.CB, p);
	
	
	@Test
	public void playerInfoBox()
	{
		// Set playerA as owner to some sites
		
		PlayerInfoBox pib = new PlayerInfoBox(playerA);
		
		// Keep on screen for short while
		int i = 1;
		while(i>0)
		{
			i++;
		}
	}
}