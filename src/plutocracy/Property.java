package plutocracy;

/**
 * The property class - Upgrades that can be added to sites
 */
public class Property {
	
	// Private Fields
	private PropertyType propertyType;
	private int propertyValue;

	public static final double RENT_PERCENTAGE = 0.25;
	public static final double UPGRADE_PERCENTAGE = 0.75;
	
	// Getter & Setter for Property Type
	/**
	 * @return A PropertyType stating whether the property is an office or conference centre.
	 */
	public PropertyType getPropertyType(){ return propertyType; }
	/**
	 * @param propertyType sets whether the property is an office or conference centre.
	 */
	public void setPropertyType (PropertyType propertyType) { this.propertyType = propertyType; }
	/**
	 * @return An integer value which is a percentage of the propertyValue, defined by RENT_PERCENTAGE. Value is rounded to nearest integer.
	 */	
	public int getRentContribution() { return (int) (Math.round(propertyValue*RENT_PERCENTAGE)); }
	/**
	 * @return An integer value which is a percentage of the propertyValue, defined by UPGRADE_PERCENTAGE. Value is rounded to nearest integer.
	 */
	public int getUpgradeCost () { return (int) (Math.round(propertyValue*UPGRADE_PERCENTAGE)); }
	/**
	 * Sets this property to be of type conference centre and sets the property value equal to the original value multiplied by the UPGRADE_PERCENTAGE constant
	 */
	public void upgrade() {
		propertyType = PropertyType.CONFERENCE_CENTRE;
		propertyValue = getUpgradeCost();
	}
	
	// Constructor.
		/**
		 * Constructs a property of the game-board. propertyType is set to office as conference centres cannot be constructed directly
		 * @param value An integer which represents how much this building costs to build.
		 */
		public Property(int value)
		{
			this.propertyType = PropertyType.OFFICE;
			this.propertyValue = value;
		}
	
}
