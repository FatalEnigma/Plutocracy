package plutocracy;

/**
 * A Bank of the game
 */
public class Bank {

	protected int cash;
	protected Plutocracy game;
	protected String name;
	
	/**
	 * Constructs a bank
	 * @param game refers to central game object
	 */
	public Bank(Plutocracy game)
	{
		this.game = game;
		cash = 0;
	}
	
	/**
	 * @return name of bank
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @param name - name of bank
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * @return amount of cash currently held by bank
	 */
	public int getCash()
	{
		return cash;
	}
	
	/**
	 * @param amount - amount of cash held by bank
	 */
	public void setCash(int amount)
	{
		cash = amount;
	}
	
	/**
	 * Modify amount of cash held by bank
	 * Bank can lose or gain any amount of cash depending on the event/transaction
	 * @param amount the amount the cash is edited by
	 */
	public void cashTransaction(int amount)
	{
		cash += amount;
	}
	
}