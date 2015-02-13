/**
 * 
 */
package plutocracy;

/**
 * An optional event that the player has control over
 */
public class OptionalEvent extends Event
{
	/**
	 * Set up required variables
	 */
	private int cashChangeYes;
	private int karmaChangeYes;
	private String responseTextYes;
	
	private int cashChangeNo;
	private int karmaChangeNo;
	private String responseTextNo;

	
	/**
	 * Sets up the optional event class
	 * @param text - test associated with event
	 * @param cashYes - cash change that happens when event happens and they say yes
	 * @param karmaYes - the karma change that happens when event happens and they say yes
	 * @param karmaUp - the upper bound of what the users karma can be to get the event
	 * @param karmaLow - the lower bound of what the users karma can be to get the event
	 * @param cashNo - cash change that happens when event happens and they say no
	 * @param karmaNo - the karma change that happens when event happens and they say no
	 */
	public OptionalEvent(String text, int cashYes, int karmaYes, int karmaUp, int karmaLow, int cashNo, int karmaNo, String TextYes, String TextNo)
	{
		super(true, text, karmaUp, karmaLow);
		
		cashChangeYes = cashYes;
		karmaChangeYes = karmaYes;
		cashChangeNo = cashNo;
		karmaChangeNo = karmaNo;
		responseTextYes = TextYes;
		responseTextNo = TextNo;
	}
	
	//Getters.
	/**
	 * @return The cash change that happens when event happens and they say no
	 */
	public int getCashChangeNo(){return cashChangeNo;}
	/**
	 * @return The karma change that happens when event happens and they say no
	 */
	public int getKarmaChangeNo(){return karmaChangeNo;}
	/**
	 * @return The cash change that happens when event happens and they say yes
	 */
	public int getCashChangeYes(){return cashChangeYes;}
	/**
	 * @return The karma change that happens when event happens and they say yes
	 */
	public int getKarmaChangeYes(){return karmaChangeYes;}
	/**
	 * @return The text response if the use clicks Yes in response to the Event
	 */
	public String getTextResponseYes(){return responseTextYes;}
	/**
	 * @return The text response if the use clicks No in response to the Event
	 */
	public String getTextResponseNo(){return responseTextNo;}
	
	
	//Setters.
	/**
	 * @param cashChangeNo - The cash change that happens when event happens and they say no
	 */
	public void setCashChangeNo(int cashChangeNo){this.cashChangeNo = cashChangeNo;}
	/**
	 * @param karmaChangeNo - The karma change that happens when event happens and they say no
	 */
	public void setKarmaChangeNo(int karmaChangeNo){this.karmaChangeNo = karmaChangeNo;}
	/**
	 * @param cashChangeYes - The cash change that happens when event happens and they say yes
	 */
	public void setCashChangeYes(int cashChangeYes){this.cashChangeYes = cashChangeYes;}
	/**
	 * @param karmaChangeYes - The karma change that happens when event happens and they say yes
	 */
	public void setKarmaChangeYes(int karmaChangeYes){this.karmaChangeYes = karmaChangeYes;}
	/**
	 * @param responseTextYes - The response text that is displayed when the user clicks Yes to the Event
	 */
	public void setresponseTextYes(String responseTextYes){this.responseTextYes = responseTextYes;}
	/**
	 * @param responseTextNo - The response text that is displayed when the user clicks Yes to the Event
	 */
	public void setresponseTextNo(String responseTextNo){this.responseTextNo = responseTextNo;}

}
