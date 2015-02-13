package testsGUI;

import java.lang.reflect.Field;
import plutocracy.Bank;
import plutocracy.BankBrand;
import plutocracy.Colour;
import plutocracy.MandatoryEvent;
import plutocracy.OptionalEvent;
import plutocracy.Player;
import plutocracy.Plutocracy;
import plutocracy.Property;
import plutocracy.PropertyType;
import plutocracy.Site;
import plutocracyGUI.Dialog;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class GUIDialogTest 
{
	Plutocracy p = new Plutocracy();
	
	@Test
	public void showTaxPaid()
	{
		Player testPlayer = new Player("Test Player", null, new Plutocracy());
		testPlayer.setCash(500);
		
		Dialog.showTaxPaid(300, testPlayer.getCash());
	}
	
	@Test
	public void showTaxPaidWithDescription()
	{
		Player testPlayer = new Player("Test Player", null, new Plutocracy());
		testPlayer.setCash(250);
		
		Dialog.showTaxPaid(300, "This is a description...", testPlayer.getCash());
	}
		
	@Test
	public void showTaxSkipOptionTrueWithDescription()
	{
		// Must select "Yes" for this test.
		Assert.assertEquals(true, Dialog.showTaxSkipOption(400, "This is a description... You must select 'Yes'"));
	}
	
	@Test
	public void showTaxSkipOptionFalseWithDescription()
	{
		// Must select "No" for this test.
		Assert.assertEquals(false, Dialog.showTaxSkipOption(400, "This is a description... You must select 'No'"));
	}
	
	@Test
	public void showRent()
	{
		Bank testBank = new Bank(null);
		testBank.setName("Test Player");
		testBank.setCash(1000);
		
		Dialog.showRent(500, testBank, testBank.getCash());
	}
	
	@Test
	public void showBuySiteConfirmation()
	{
		Site testSite = new Site("Test Site", 500, 250, Colour.RED, p);
		Bank testBank = new Bank(null);
		testBank.setName("Test Player");
		testBank.setCash(100);
		
		Dialog.showBuySiteConfirmation(testSite, testBank.getCash());
	}
	
	@Test
	public void showBuyPropertyConfirmation()
	{
		Site testSite = new Site("Test Site", 500, 250, Colour.RED, p);
		Bank testBank = new Bank(null);
		testBank.setName("Test Player");
		testBank.setCash(800);
		
		Dialog.showBuyPropertyConfirmation(testSite, testBank.getCash());
	}
	
	@Test
	public void showPassCentralBankConfirmation()
	{
		Bank testBank = new Bank(null);
		testBank.setName("Test Player");
		testBank.setCash(500);
		
		Dialog.showPassCentralBankConfirmation(200, testBank.getCash());
	}
	
	@Test
	public void showEventMandatory()
	{
		MandatoryEvent testEvent = new MandatoryEvent("Test Event", -200, 2, 5, -5, "Test Response");
		
		Dialog.showEvent(testEvent);
	}
	
	@Test
	public void SiteOptionBox() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Plutocracy p = new Plutocracy();
		
		Player testPlayer = new Player("Test Player", BankBrand.BA, p);
		
		testPlayer.setCash(10000);
		
		Site testSite = new Site("Test Site (TEST: Buy Property)", 500, 250, Colour.RED, p);
		
		//FIXME: Test fails here, exact point is showing the site info box, too tired right now to fix
		// This should ask player to buy a site
		Dialog.SiteOptionBox(p, testSite, testPlayer);
		
		// Add some more properties to this site
		testSite.getProperties().add(new Property(500));
		testSite.getProperties().add(new Property(750));
		
		// This should ask the user to buy the final office block for this site
		testSite.setName("Test Site (TEST: Buy One More Office Block)");
		Dialog.SiteOptionBox(p, testSite, testPlayer);
		
		// This should now allow the user to upgrade each conference centre on their site
		testSite.setName("Test Site (TEST: Upgrade first 2 ONLY)");
		Dialog.SiteOptionBox(p, testSite, testPlayer);
		
		// Now perform some checks to ensure values are as they should be
		Assert.assertEquals(PropertyType.CONFERENCE_CENTRE, testSite.getProperties().get(0).getPropertyType());

		Field field = Property.class.getDeclaredField("propertyValue");
		field.setAccessible(true);

		int fieldValue = (int) field.get(testSite.getProperties().get(0));
		Assert.assertEquals(PropertyType.CONFERENCE_CENTRE, testSite.getProperties().get(0).getPropertyType());
		Assert.assertEquals(750, fieldValue);
		
		fieldValue = (int) field.get(testSite.getProperties().get(2));
		Assert.assertEquals(PropertyType.OFFICE, testSite.getProperties().get(2).getPropertyType());
		Assert.assertEquals(250, fieldValue);
	}

	@Test
	public void showEventOptionalTrue()
	{ OptionalEvent testEvent = new OptionalEvent("This is a test optional event, please click the yes option!", -200, 2, 5, -5, 2000, -2, "Test Yes Reply", "Test No Reply");
		
		
		Assert.assertEquals(true, Dialog.showEvent(testEvent));
	}
	
	@Test
	public void showEventOptionalFalse()
	{
		OptionalEvent testEvent = new OptionalEvent("This is a test optional event, now click the no option!", -200, 2, 5, -5, 2000, -2, "Test Yes Reply", "Test No Reply");
		
		Assert.assertEquals(false, Dialog.showEvent(testEvent));
	}
	
}
