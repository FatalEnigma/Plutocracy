package testsGUI;

import org.junit.Test;

import plutocracy.*;
import plutocracyGUI.SiteInfoBox;

public class GUISiteInfoBoxTest {

	@Test
	public void testSiteInfoBox()
	{
		Site testSite = new Site("Test Site", 500, 250, Colour.RED, new Plutocracy());
		testSite.getProperties().add(new Property(500));
		testSite.getProperties().add(new Property(250));
		testSite.getProperties().add(new Property(100));
		testSite.getProperties().get(2).upgrade();
		
		Bank testBank = new Bank(null);
		testBank.setName("Test Player");
		testSite.setOwner(testBank);
		
		SiteInfoBox infoBox = new SiteInfoBox(testSite);
		int i = 1;
		while(i>0)
		{
			i++;
		}
	}
}
