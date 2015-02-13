package tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import plutocracy.*;

@RunWith(JUnit4.class)
public class CellTest 
{	
	/*
		There's not much to test in this class. You don't have to test every single
		function getter and setter like I did here. This file was mainly used to make
		sure the build.xml file, and j-unit where working.
	*/
	
	// Fixtures can be set up outside of tests, which can be used repeatedly.
	Cell cellA = new Cell(CellType.TAX, "Cell1");
	Cell cellB = new Cell(CellType.CENTRAL_BANK, "Central Bank");
	
	// This is a test. Simply make a "public void" method with the name of your test,
	// then place your test inside.
	@Test
	public void getType()
	{
		Assert.assertEquals(CellType.CENTRAL_BANK, cellB.getType());
	}
	
	@Test
	public void setName()
	{
		String name = "NameTest";
		cellA.setName(name);
		Assert.assertEquals(name, cellA.getName());
	}
	
	// You don't have to create the object your testing on, prior to your tests.
	// You can just create objects inside them like cell3 below, or the String name above.
	@Test
	public void Constructor()
	{
		Cell cell3 = new Cell(CellType.TAX, "Lullz");
		Assert.assertEquals(CellType.TAX, cell3.getType());
		Assert.assertEquals("Lullz", cell3.getName());
	}
	
	@Test
	public void setType()
	{
		cellB.setType(CellType.RANDOM_EVENT);
		Assert.assertEquals(CellType.RANDOM_EVENT, cellB.getType());
	}
	
}
