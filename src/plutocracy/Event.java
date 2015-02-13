/**
 * 
 */
package plutocracy;

/**
 *	A generic event, this is then expanded upon in OptionalEvent.java and MandatoryEvent.java
 */
abstract public class Event 
{
	/**
	 * Set up variables associated with all events
	 */
	protected boolean isOptional;
	protected String text;
	protected int karmaLowerBound;
	protected int karmaUpperBound;
	
	/**
	 * @param isOptional - Set whether an event with optional or not
	 */
	public Event(boolean isOptional, String text, int karmaUpperBound, int karmaLowerBound)
	{
		this.isOptional = isOptional;
		this.text = text;
		this.karmaUpperBound = karmaUpperBound;
		this.karmaLowerBound = karmaLowerBound;
	}
	
	//Getters.
	/**
	 * @return whether the event is optional or not
	 */
	public boolean isOptional()
	{
		return isOptional;
	}
	/**
	 * @return Returns the text description associated with the event
	 */
	public String getText(){return text;}
	/**
	 * @return Returns the lower karma limit that the user must have to receive the event
	 */
	public int getKarmaLower(){return karmaLowerBound;}
	/**
	 * @return Returns the upper karma limit that the user must have to receive the event
	 */
	public int getKarmaUpper(){return karmaUpperBound;}
	
	//Setters.
	/**
	 * @param text - The text description for the event
	 */
	public void setText(String text){this.text = text;}
	/**
	 * @param karmaLowerBound - The lower karma limit that the user must have to receive the event
	 */
	public void setKarmaLower(int karmaLowerBound){this.karmaLowerBound = karmaLowerBound;}
	/**
	 * @param karmaUpperBound - The upper karma limit that the user must have to receive the event
	 */
	public void setKarmaUpper(int karmaUpperBound){this.karmaUpperBound = karmaUpperBound;}
}


