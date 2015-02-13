package plutocracy;
/**
 * The types of bank the players can choose from
 */
public enum BankBrand {
	//Set up Enumerated types shortName("Full Name")
	CB("Citibank", "plutocracyGUI/resources/banks/cb.png"),
	JP("JPMorgan", "plutocracyGUI/resources/banks/jp.png"),
	ST("Santander", "plutocracyGUI/resources/banks/st.png"),
	WF("Wells Fargo", "plutocracyGUI/resources/banks/wf.png"),
	BA("Bank of America", "plutocracyGUI/resources/banks/ba.png");
	
	private final String bankName;
	private final String path;
	private boolean isSelected = false;
	
	/**
	 * Constructor to set up Full Names
	 * @param inBank - Bank Name
	 * @param inPath - Path image
	 */
	private BankBrand(String inBank, String inPath) {
		bankName = inBank;
		path = inPath;
	}

	/**
	 * Used for comparisons
	 * @param inBank - Bank in
	 * @return - returns true or false
	 */
	public boolean equalsBank(String inBank){
		return (inBank == null)? false:bankName.equals(inBank);
	}

	/**
	 * Returns Full Name
	 * @return - Bank Name as a string
	 */
	public String toString(){
		return bankName;
	}
	
	/**
	 * Returns the path to the bank's logo. 
	 * @return returns path to image
	 */
	public String returnImage(){
		return path;
	}

	/**
	 * Sets whether the bank brand has already been selected this game.
	 */
	public void setSelected()
	{
		isSelected = true;
	}
	
	/**
	 * Returns whether the bank has already been selected this game
	 * @return - returns true or false
	 */
	public boolean getSelected()
	{
		return isSelected;
	}
	
};