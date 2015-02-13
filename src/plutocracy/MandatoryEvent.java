/**
 * 
 */
package plutocracy;

/**
 * A Mandatory Event - One that the user has no control over
 */
public class MandatoryEvent extends Event 
{
	/**
	 * Set up required variables
	 */
	private int cashChange;
	private int karmaChange;
	private String responseText;

	/**
	 * Sets up the mandatory event class
	 * @param text - test associated with event
	 * @param cash - cash change that happens when event happens
	 * @param karma - the karma change that happens when event happens
	 * @param karmaUp - the upper bound of what the users karma can be to get the event
	 * @param karmaLow - the lower bound of what the users karma can be to get the event
	 */
	public MandatoryEvent(String text, int cash, int karma, int karmaUp, int karmaLow, String response)
	{
		super(false, text, karmaUp, karmaLow);
		this.cashChange = cash;
		this.karmaChange = karma;
		this.responseText = response;
	}
	
	//Getters.
	/**
	 * @return Returns how much the karma changes by when the event happens
	 */
	public int getCashChange(){return cashChange;}
	/**
	 * @return Returns how much the karma changes by when the event happens
	 */
	public int getKarmaChange(){return karmaChange;}
	/**
	 * @return Returns text responding to the Event
	 */
	public String getTextResponse(){return responseText;}
	
	//Setters.
	/**
	 * @param cashChange - The cash change that happens when the event is called
	 */
	public void setCashChange(int cashChange){this.cashChange = cashChange;}
	/**
	 * @param karmaChange - The change in karma that happens when the event happens
	 */
	public void setKarmaChange(int karmaChange){this.karmaChange = karmaChange;}
	/**
	 * @param responseText - The response to an Event
	 */
	public void setResponse(String responseText){this.responseText = responseText;}

}
