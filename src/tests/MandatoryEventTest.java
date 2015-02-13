package tests;
/**
 * @author Ryan McCarter
 */

import junit.framework.Assert;
import plutocracy.*;

import org.junit.Test;

public class MandatoryEventTest {

	MandatoryEvent event = new MandatoryEvent("Test Event", -200, 2, 5, -5, "testing");
	
	@Test
	public void testGetCashChange() {
		Assert.assertEquals(-200, event.getCashChange());
	}

	@Test
	public void testGetKarmaChange() {
		Assert.assertEquals(2, event.getKarmaChange());
	}

	@Test
	public void testSetCashChange() {
		event.setCashChange(5000);
		Assert.assertEquals(5000, event.getCashChange());
	}

	@Test
	public void testSetKarmaChange() {
		event.setKarmaChange(4);
		Assert.assertEquals(4, event.getKarmaChange());
	}

	@Test
	public void testIsOptional() {
		Assert.assertEquals(false, event.isOptional());
	}
	
	@Test
	public void getResponceText()
	{
		Assert.assertEquals("testing", event.getTextResponse());
	}
	
	@Test
	public void setResponceText()
	{
		event.setResponse("moreTesting");
		Assert.assertEquals("moreTesting", event.getTextResponse());
	}
}
