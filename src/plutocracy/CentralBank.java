package plutocracy;

import java.util.Random;

/** 
 * The central bank of the game
 */
public class CentralBank extends Bank{
	
	private int globalTaxRate;
	private String name;
	private int totalCash;
	
	/**
	 * Constructs the central bank's parameters.
	 * @param game - The main game object
	 */
	public CentralBank(Plutocracy game)
	{
		super(game);
		
		name = "Central Bank";
		setName(name);
		
		Random percent = new Random();
		//totalCash will currently range between 100k and 1000k
		totalCash = 100000 * (percent.nextInt(10)+1);
		setCash(totalCash);
		
		//taxRate = percent.nextInt(25); use to randomise tax rate
		setTaxRate(12);
	}
	
	/**
	 * @return the global tax rate applied to all transactions
	 */
	public int getTaxRate()
	{
		return globalTaxRate;
	}
	
	/**
	 * @param amount - the global tax rate
	 */
	public void setTaxRate(int amount)
	{
		globalTaxRate = amount;
	}

}