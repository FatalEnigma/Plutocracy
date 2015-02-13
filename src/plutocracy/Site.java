package plutocracy;

import java.util.ArrayList;

/**
 * The site class - Sites are squares that hold objects which are buyable etc.
 * can be upgraded using properties
 */
public class Site extends Cell
{
	private boolean isUpgradeable;
	private Colour colour;
	private int basePrice;
	private int baseRent;
	private ArrayList<Property> properties;
	private Bank owner;
	
	// Getter and Setter Functions.
	/**
	 * @return - The colour of the Site.
	 */
	public Colour getColour(){return colour;}
	
	/**
	 * @return - The base price price of the Site in int form.
	 */
	public int getBasePrice(){return basePrice;}
	
	/**
	 * @return - The rent of the Site..
	 */
	public int getRent()
	{
		int rent = baseRent;
		
		for(Property p : properties)
		{
			rent += p.getRentContribution();
		}
		
		return rent;
	}	
	
	/**
	 * @param color - color sets the colour of the Site.
	 */
	public void setColour(Colour color){colour = color;}
	
	/**
	 * @param price - basePrice sets the base price of the Site.
	 */
	public void setBasePrice(int price){basePrice = price;}
	
	/**
	 * @param rent - baseRent sets the base rent of the Site.
	 */
	public void setBaseRent(int rent){baseRent = rent;}
	
	
	// Constructor.
	/**
	 * Constructs a Site for use by the game.
	 * @param name - Name of site
	 * @param price - The base price of the Site.
	 * @param rent - The base rent of the Site.
	 * @param color - States the colour of the Site.
	 * @param game - The game object
	 */
	public Site (String name, int price, int rent, Colour color, Plutocracy game) 
	{
		super(CellType.SITE, name);
		colour = color;
		basePrice = price;
		baseRent = rent;
		properties = new ArrayList<Property>();
		owner = game.getCentralBank();

		
	}

	/**
	 * @return The owner of the site.
	 */
	public Bank getOwner() 
	{
		return owner;
	}
	
	/**
	 * @return The properties on the site.
	 */
	public ArrayList<Property> getProperties()
	{
		return properties;
	}

	/**
	 * @param player The owner of the site.
	 */
	public void setOwner(Bank player) 
	{
		owner = player;
	}
	
	/**
	 * Whether the site is upgradeable or not
	 * @return the isUpgradeable boolean 
	 */
	public boolean isUpgradeable()
	{
		return isUpgradeable;
	}
	
	/**
	 * Updated the isUpgradeable boolean
	 */
	public void setIsUpgradeable()
	{
		int count = 0;
		
		if(!isUpgradeable)
		{
			//count number of color that player owns
			for(Site site : ((Player)owner).getOwnerSites())
			{
				if(site.colour == colour)
				{
					count++;
				}
			}

			//Check if they have all the properties of this color as set when the properties were loaded
			if(owner.game.getPropertyColorCount()[Colour.valueOf(colour.toString()).ordinal()] == count)
			{				
				for(Site site : ((Player)owner).getOwnerSites())
				{
					if(site.colour == colour)
					{
						site.isUpgradeable = true;
					}
				}
			}
				
		}
	}
}