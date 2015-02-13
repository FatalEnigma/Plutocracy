package tests;

import plutocracy.Property;
import plutocracy.PropertyType;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Adam Smith
 *
 */

@RunWith(JUnit4.class)
public class PropertyTest {

	// Fixtures created
	Property property1, property2;
	
	// Run this before each test to prevent any method from altering the value
	@Before
	public void initialiseFixture() { property1 = new Property(500); property2 = new Property(250); }
	
	@Test
	public void setGetType()
	{
		Assert.assertEquals("Property should start as type office.", PropertyType.OFFICE, property1.getPropertyType());
		property1.setPropertyType(PropertyType.CONFERENCE_CENTRE);
		Assert.assertEquals("Property should now be of type conference centre.", PropertyType.CONFERENCE_CENTRE, property1.getPropertyType());
	}
	
	@Test
	public void getRentContribution()
	{
		Assert.assertEquals("Property 1 should have 125 for rent contribution", 125, property1.getRentContribution());
		Assert.assertEquals("Property 2 should have 63 for rent contribution", 63, property2.getRentContribution());
	}
	
	@Test
	public void getUpgradeCost()
	{
		Assert.assertEquals("Property 1 should have 375 for upgrade cost", 375, property1.getUpgradeCost());
		Assert.assertEquals("Property 2 should now have 375 for upgrade cost", 188, property2.getUpgradeCost());
	}
	
	@Test
	public void upgrade()
	{
		property1.upgrade();
		property2.upgrade();
		Assert.assertEquals("Property 1 should now be of type conference centre", PropertyType.CONFERENCE_CENTRE, property1.getPropertyType());
		Assert.assertEquals("Property 2 should now be of type conference centre", PropertyType.CONFERENCE_CENTRE, property2.getPropertyType());
		Assert.assertEquals("Property 1 should now have 94 for rent contribution", 94, property1.getRentContribution());
		Assert.assertEquals("Property 2 should now have 94 for rent contribution", 47, property2.getRentContribution());
	}
	
}
