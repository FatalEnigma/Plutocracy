package tests;
/**
 * @author Ryan McCarter
 */

import junit.framework.Assert;

import plutocracy.*;

import org.junit.Test;

public class OptionalEventTest {

	OptionalEvent event = new OptionalEvent("Test Event", -200, 2, 5, -5, 2000, -2, null, null);
	OptionalEvent e = new OptionalEvent("getterTest", -200, 2, 5, -5, 2000, -2, "testingYes", "testingNo");
	
	@Test
	public void testGetCashChangeNo() {
		Assert.assertEquals(2000, event.getCashChangeNo());
	}

	@Test
	public void testGetKarmaChangeNo() {
		Assert.assertEquals(-2, event.getKarmaChangeNo());
	}

	@Test
	public void testGetCashChangeYes() {
		Assert.assertEquals(-200, event.getCashChangeYes());
	}

	@Test
	public void testGetKarmaChangeYes() {
		Assert.assertEquals(2, event.getKarmaChangeYes());
	}

	@Test
	public void testIsOptional() {
		Assert.assertEquals(true, event.isOptional());
	}

	@Test
	public void testGetText() {
		Assert.assertEquals("Test Event", event.getText());
	}

	@Test
	public void testGetKarmaLower() {
		Assert.assertEquals(-5, event.getKarmaLower());
	}

	@Test
	public void testGetKarmaUpper() {
		Assert.assertEquals(5, event.getKarmaUpper());
	}
	
	@Test
	public void testSetCashChangeNo() {
		event.setCashChangeNo(5000);
		Assert.assertEquals(5000, event.getCashChangeNo());
	}

	@Test
	public void testSetKarmaChangeNo() {
		event.setKarmaChangeNo(-5);
		Assert.assertEquals(-5, event.getKarmaChangeNo());
	}

	@Test
	public void testSetCashChangeYes() {
		event.setCashChangeYes(-1);
		Assert.assertEquals(-1, event.getCashChangeYes());
	}

	@Test
	public void testSetKarmaChangeYes() {
		event.setKarmaChangeYes(3);
		Assert.assertEquals(3, event.getKarmaChangeYes());
	}

	@Test
	public void testSetText() {
		event.setText("Test Event 2");
		Assert.assertEquals("Test Event 2", event.getText());
	}

	@Test
	public void testSetKarmaLower() {
		event.setKarmaLower(0);
		Assert.assertEquals(0, event.getKarmaLower());
	}

	@Test
	public void testSetKarmaUpper() {
		event.setKarmaUpper(0);
		Assert.assertEquals(0, event.getKarmaUpper());
	}
	
	@Test
	public void getTextResponceYes()
	{
		Assert.assertEquals("testingYes", e.getTextResponseYes());
	}
	
	@Test
	public void getTextResponceNo()
	{
		Assert.assertEquals("testingNo", e.getTextResponseNo());
	}
	
	@Test
	public void setTextResponceYes()
	{
		e.setresponseTextYes("moreTestingYes");
		Assert.assertEquals("moreTestingYes", e.getTextResponseYes());
	}
	
	@Test
	public void setTextResponceNo()
	{
		e.setresponseTextNo("moreTestingNo");
		Assert.assertEquals("moreTestingNo", e.getTextResponseNo());
	}
}
