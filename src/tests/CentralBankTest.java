package tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import plutocracy.*;

@RunWith(JUnit4.class)
public class CentralBankTest
{
	Plutocracy p = new Plutocracy();
	CentralBank cb = new CentralBank(p);
	
	@Test
	public void setGetTaxRate()
	{
		cb.setTaxRate(42);
		Assert.assertEquals(42, cb.getTaxRate());
	}
}