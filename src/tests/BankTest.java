package tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import plutocracy.*;

@RunWith(JUnit4.class)
public class BankTest
{	
	Plutocracy p = new Plutocracy();
	Bank bankA = new Bank(p);
	
	
	@Test
	public void setGetCash()
	{
		bankA.setCash(1000);
		Assert.assertEquals(1000, bankA.getCash());
	}
	
	@Test
	public void increaseCash()
	{
		bankA.setCash(5000);
		Assert.assertEquals(5000, bankA.getCash());
		bankA.cashTransaction(2000);
		Assert.assertEquals(7000, bankA.getCash());
	}
	
	@Test
	public void decreaseCash()
	{
		bankA.setCash(2000);
		Assert.assertEquals(2000, bankA.getCash());
		bankA.cashTransaction(-500);
		Assert.assertEquals(1500, bankA.getCash());
	}
	
	@Test
	public void setName()
	{
		bankA.setName("TestA");
		Assert.assertEquals("TestA", bankA.getName());
	}
}