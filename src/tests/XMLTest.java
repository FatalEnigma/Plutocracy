package tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import plutocracy.*;

/**
 * @author Adam Smith
 *
 */
@RunWith(JUnit4.class)
public class XMLTest
{	
	Plutocracy p = new Plutocracy();
	
	// Random Event Tests
	ArrayList<Event> eventList = p.loadEvents();
	
	@Test
	public void isEventListNotNull()
	{
		Assert.assertNotNull(eventList);
	}
	
	@Test
	public void isEventListLargerThanZero()
	{
		Assert.assertTrue(eventList.size() > 0);
	}
	
	@Test
	public void printList() {
		int eventNum = 0;

		for (Event e : eventList) {
			System.out.println("ID: " + eventNum++ + "\nKarma Bounds: "
					+ e.getKarmaLower() + " | " + e.getKarmaUpper()
					+ "\nText: " + e.getText());

			if (e.isOptional()) {
				OptionalEvent o = (OptionalEvent) e;

				System.out.println("Cash Changes (Y/N): "
						+ o.getCashChangeYes() + " | " + o.getCashChangeNo()
						+ "\nKarma Changes (Y/N): " + o.getKarmaChangeYes()
						+ " | " + o.getKarmaChangeNo());
			}

			else {
				MandatoryEvent m = (MandatoryEvent) e;

				System.out.println("Cash Change: " + m.getCashChange()
						+ "\nKarma Change: " + m.getKarmaChange());
			}

			System.out.println("\n");

		}
	}
	
	@Test
	public void checkRandomEventValues()
	{
		MandatoryEvent m = (MandatoryEvent) eventList.get(0);
		
		Assert.assertEquals("You have been given a sum of money by the rich entrepreneur Mista Sora, to do with as you please.", m.getText());
		Assert.assertEquals(10000, m.getCashChange());
		Assert.assertEquals(-4, m.getKarmaLower());
		
		m = (MandatoryEvent) eventList.get(10);
		Assert.assertEquals("ALIENS ATTACK EARTH! Receive 5000 pounds for helping them.", m.getText());
		Assert.assertEquals(5000, m.getCashChange());
		Assert.assertEquals(4, m.getKarmaUpper());
		
		OptionalEvent o = (OptionalEvent) eventList.get(15);
		Assert.assertEquals("The evil dictator Kane attacks the UK. Do you donate 10000 pounds to the war effort(Yes), or join forces with the evil Kane; your actions having perhaps untold consequences?(No)", o.getText());
		Assert.assertEquals(5, o.getKarmaUpper());
		Assert.assertEquals(4, o.getKarmaChangeYes());
		Assert.assertEquals(1000, o.getCashChangeNo());
		
		o = (OptionalEvent) eventList.get(9);
		Assert.assertEquals("It is the end of the financial year; you can either pay dividends(Yes) or re-invest(No).", o.getText());
		Assert.assertEquals(-5, o.getKarmaLower());
		Assert.assertEquals(-3000, o.getCashChangeYes());
		Assert.assertEquals(-1, o.getKarmaChangeNo());
	}
	
	// Board Cell Tests
	
	ArrayList<Cell> boardList = p.loadBoardSquares();
	
	@Test
	public void isBoardcellListNotNull()
	{
		Assert.assertNotNull(boardList);
	}
	
	@Test
	public void isBoardcellListLargerThanZero()
	{
		Assert.assertTrue(boardList.size() > 0);
	}
	
	@Test
	public void printBoardcellList()
	{
		int cellNum = 0;
		
		for (Cell e : boardList) {
			if (e.getType() == CellType.SITE) {
				Site testSite = (Site) e;

				System.out.println("Id: " + cellNum++ + " | Name: "
						+ testSite.getName() + " | CellType: "
						+ testSite.getType() + " | Colour: "
						+ testSite.getColour() + " | Base Rent: "
						+ testSite.getRent() + " | Base Price: "
						+ testSite.getBasePrice() + "\n");
			}
			else
			System.out.println("Id: " + cellNum++ + " | Name: " + e.getName()
					+ " | CellType: " + e.getType() + "\n");
		}
	}
}