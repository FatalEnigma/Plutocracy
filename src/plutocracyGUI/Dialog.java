package plutocracyGUI;

import javax.swing.*;


import plutocracy.*;
import plutocracyGUI.GameLog.logType;


/**
 * All of the dialogs required for the game
 */
public class Dialog
{
	/**
	 * Displays a dialog box, showing the user how much tax they've paid.
	 * @param amount states the amount the user paid in taxes.
	 * @param playerCash the amount of cash the player has.
	 */
	public static void showTaxPaid(int amount, int playerCash)
	{
		String text = "Paid £" + amount + " in taxes. \nYou have £" + playerCash + " remaining."; 
		JOptionPane.showMessageDialog(null, text, "Tax", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	/**
	 * Displays a dialog box, showing the user how much tax they've paid,
	 * with a short description above.
	 * @param amount states the amount the user has paid in taxes.
	 * @param description is a short story.
	 * @param playerCash the amount of cash the player has.
	 */
	public static void showTaxPaid(int amount, String description, int playerCash)
	{
		String text = description + "\n\nPaid £" + amount + " in taxes.\nYou have £" + playerCash + " remaining.";
		JOptionPane.showMessageDialog(null, text, "Tax", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Displays a dialog box, asking the user if they want to try
	 * and evade the tax they are obliged to pay.
	 * @param amount is the amount of money the player will pay in taxes
	 * if they choose not to skip.
	 * @return the users answer where true is yes, and false is no.
	 */
	public static boolean showTaxSkipOption(int amount)
	{
		String text = "You are obliged to pay £" + amount
				+ " in taxes, but you may be able to evade the taxman.\n\n"
				+ "Try to evade the tax?";
		
		if(JOptionPane.showConfirmDialog(null, text, "Evade Tax?", JOptionPane.YES_NO_OPTION) == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Displays a dialog box, asking the user if they want to try
	 * and evade the tax they are obliged to pay, with a short description above.
	 * @param amount is the amount of money the player will pay in taxes if they
	 * choose to not skip.
	 * @param description is a short story explaining why they are paying extra taxes
	 * or why they are able to skip paying etc.
	 * @return the users answer where true is yes, and false is no.
	 */
	public static boolean showTaxSkipOption(int amount, String description)
	{
		String text = description + "\n\nYou are obliged to pay £" + amount
				+ " in taxes, but you may be able to evade the taxman.\n"
				+ "Try to evade the tax?";
		
		if(JOptionPane.showConfirmDialog(null, text, "Evade Tax?", JOptionPane.YES_NO_OPTION) == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Ask how many players are going to be playing
	 * @param max - the max number of players
	 * @return number of players
	 */
	public static int getHowManyPlayers(int min, int max)
	{	
		// Declare the variables.
		String numberOfPlayers;
		int numPlayers;
		
		// While the number of players is null, loop round asking the user for input.
		do
		{
			// Reset the variables.
			numberOfPlayers = "";
			numPlayers = -1;
			
			// Get the number of players.
			numberOfPlayers = JOptionPane.showInputDialog(null, "How many players would you like?:", "Enter Number of Players", JOptionPane.PLAIN_MESSAGE);
			
			// Try and parse the number.
			try
			{
				// If null, the user clicked "Cancel" so exit.
				if(numberOfPlayers == null)
					System.exit(0);
				
				// Parse the input.
				numPlayers = Integer.parseInt(numberOfPlayers);
			}
			catch(NumberFormatException e) // Thrown if the string does not represent an integer. 
			{
				JOptionPane.showMessageDialog(null, "The text you entered is not a whole number!", "Error", JOptionPane.ERROR_MESSAGE);
				continue;
			}
			
			// Display a message asking them to enter a number within the allowed range.
			if(numPlayers < min || numPlayers > max)
				JOptionPane.showMessageDialog(null, "Please enter a number between " + min + " and " + max + " inclusive", "Enter Number of Players", JOptionPane.WARNING_MESSAGE);
			
		}while (numPlayers < min || numPlayers > max);
		
		// Return the number
		return numPlayers;
	}
	
	/**
	 * Creates a new player object with information given by the user.
	 * @param game The main game class.
	 * @return A Player object based on the user input.
	 */
	public static Player getPlayer(Plutocracy game)
	{
		// Get The player's name and bank brand.
		String name = getPlayerName(game);
		BankBrand bankBrand = getPlayerBank(game);
		
		// Return the new player.
		return new Player(name, bankBrand, game);
	}
	
	/**
	 * 
	 * @return the string entered by the user when prompted for a name.
	 */
	public static String getPlayerName(Plutocracy game)
	{
		// Get a string pointer but set it to null.
		String name = null;
		
		// While the name is null, loop round asking the user for input.
		do
		{
			int playerNum = game.getPlayers().size() + 1;
			name = JOptionPane.showInputDialog(null, "Enter your name:", "Player " + playerNum + " Details", JOptionPane.PLAIN_MESSAGE);
			
			if(name.isEmpty())
			{
				JOptionPane.showMessageDialog(null, "Please enter a name!", "Error", JOptionPane.ERROR_MESSAGE);
				name = null;
				continue;
			}
			
			if(name == null)
				System.exit(0);
			
			//Ensuring that the same name is not picked twice.
		    for(Player p : game.getPlayers())
		    {
				if(p.getName().contains(name))
				{
				    JOptionPane.showMessageDialog(null, name + " has already been picked!");
				    name = null;
				    continue;
				}
		    }
			
		}while (name==null);
		
		// Return the string.
		return name;
	}
	
	/**
	 * 
	 * @return the BankBrand selected by the user when prompted.
	 */
	public static BankBrand getPlayerBank(Plutocracy game)
	{
		// Get a string pointer but set it to null.
		BankBrand brand = null;
		boolean alreadyPicked;
		
		// While the bank is null or already picked, loop round asking the user for input.
		do
		{
		    // Get the BankBrand selected by the player.
		    brand = (BankBrand)JOptionPane.showInputDialog(
			    		null, 
					"Select a bank:", 
					"Player " + (game.getPlayers().size() + 1) + " Details", 
					JOptionPane.PLAIN_MESSAGE,
					null,
					BankBrand.values(),
					null
					);
			
		    // Set already picked to false.
		    alreadyPicked = false;
		    
		    // Check to see if the brand has already been picked.
		    for(Player p : game.getPlayers())
		    {
				if(p.getBankBrand() == brand)
				{
				    alreadyPicked = true;
				    JOptionPane.showMessageDialog(null, brand.toString() + " has already been picked!");
				}
		    }
		    
		    //If the player clicks 'Cancel'.
		    if(brand == null)
			    System.exit(0);
		    
		}while (brand == null || alreadyPicked);
		
		// Return the string.
		return brand;
	}
	
	
	/**
	 * Shows a dialog box displaying the amount of rent paid to a player.
	 * @param amount The amount paid in rent.
	 * @param owner The player who is getting paid rent.
	 * @param playerCash the amount of cash the player has.
	 */
	public static void showRent(int amount, Bank owner, int playerCash)
	{
		JOptionPane.showMessageDialog(null, "You paid £" + amount + " to "
				+ owner.getName() + " in rent. \nYou have £" + playerCash
				+ " remaining.");
	}
	
	/**
	 * Shows the player a confirmation saying they bought the site.
	 * @param site The site that was bought.
	 * @param playerCash the amount of cash the player has.
	 */
	public static void showBuySiteConfirmation(Site site, int playerCash)
	{
		JOptionPane.showMessageDialog(null, "You bought " + site.getName() + " for £" + site.getBasePrice() + ".\nYou have £" + playerCash + " remaining.");
	}
	
	/**
	 * Shows the player a confirmation saying they bought the site.
	 * @param site the site the property was purchased on.
	 * @param playerCash the amount of cash the player has.
	 */
	public static void showBuyPropertyConfirmation(Site site, int playerCash)
	{
		JOptionPane.showMessageDialog(null, "You bought a property on the " + site.getName() + " site for £" + site.getBasePrice() + ".\nYou have £" + playerCash + " remaining.");
	}
	
	/**
	 * Shows the player a confirmation that they have received money for passing the Central Bank square
	 * 
	 * @param cash the amount the player has received
	 * @param playerCash the amount of cash the player now has
	 */
	public static void showPassCentralBankConfirmation(int cash, int playerCash)
	{
		JOptionPane.showMessageDialog(null, "You passed the Central Bank and received £" + cash + "!\nYou now have £" + playerCash + ".");
	}
	
	/**
	 * Shows the player a confirmation that they have received money for landing on the Central Bank square
	 * @param cash - the amount the player has received
	 * @param playerCash - the amount of cash the player now has
	 */
	public static void showLandedCentralBankConfirmation(int cash, int playerCash)
	{
		JOptionPane.showMessageDialog(null, "You landed on the Central Bank and received an extra £" + cash + "!\nYou now have £" + playerCash + ".");
	}
	
	/**
	 * Shows the player a confirmation that they are bankrupt and are out of the game
	 */
	public static void showBankruptConfirmation()
	{
		JOptionPane.showMessageDialog(null, "You have lost all of your funds and are now out of the game!");
	}
	
	
	/**
	 * Displays site info for buying and upgrading property
	 * @param game What site the player currently is on.
	 * @param site What site the player currently is on.
	 * @param player The current player.
	 */
	public static void SiteOptionBox(Plutocracy game, Site site, Player player)
	{
		SiteInfoBox siteInfoBox;
		
		//Check if site is owned by Central Bank
		if(site.getOwner() == game.getCentralBank())
		{	
			//Check they can afford
			if(site.getBasePrice() <= player.getCash())
			{
				// Show the SiteInfoBox
				siteInfoBox = new SiteInfoBox(site);
				game.getBoard().setClickedCell(siteInfoBox);
				
				String description = "You have landed on " + site.getName() + "!" +
						"\nIt is currently up for sale for £" + site.getBasePrice() +
						".\nWould you like to buy it?";
				//If so let them try to buy it
				if(JOptionPane.showConfirmDialog(null, description, "Buy a Site", JOptionPane.YES_NO_OPTION) == 0)
				{
					player.buySite(site);
				}
			}
			else
				game.getBoard().getLog().outputMessageToLogbox(logType.GAME, player.getName() + " landed on a site but didn't have enough money to do anything.");
		}
		//Check if the current player owns the site
		else if(player.isSiteOwnedBy(site))
		{
			//Check whether they own all the sites in the group
			if(site.isUpgradeable())
			{
				// Show the SiteInfoBox
				siteInfoBox = new SiteInfoBox(site);
				game.getBoard().setClickedCell(siteInfoBox);
				
				//If they have less than three properties on the site offer them the chance to buy another office
				while(site.getProperties().size() < 3)
				{
					//Check player can afford it
					if(site.getBasePrice()*Property.UPGRADE_PERCENTAGE <= player.getCash())
					{
						
						String description = "You have landed on " + site.getName() + "!" +
								"\nYou currently have " + site.getProperties().size() + " properties on it." +
								"\nWould you like to buy another office block?";
						//Do you want to buy another office dialog
						if(JOptionPane.showConfirmDialog(null, description, "Buy Office", JOptionPane.YES_NO_OPTION) == 0)
						{
							player.buyProperty(site); 
							game.getBoard().update();
							siteInfoBox.update();
						}
						else
						{
							break;
						}
					}
				}
				//If the player has 3 properties on the site
				if(site.getProperties().size() >= 3)
				{
					//Find out how many conference centres the player has on the property
					int numOfCC=0;
					for(Property property : site.getProperties())
					{
						if(property.getPropertyType() == PropertyType.CONFERENCE_CENTRE)
						{
							numOfCC++;
						}
					}
					
					//For each property
					for(Property property : site.getProperties())
					{
						//Check player can afford it
						if(site.getBasePrice()*Property.UPGRADE_PERCENTAGE <= player.getCash())
						{
							//For each office give them the chance to upgrade it.
							if(property.getPropertyType() == PropertyType.OFFICE)
							{
								
								String description = "You have landed on " + site.getName() + "!" +
										"\nThere are currently " + numOfCC + " Conference Centres." +
										"\nWould you like to upgrade to another Conference Centre for £" + property.getUpgradeCost() + "?";
								
								if(JOptionPane.showConfirmDialog(null, description, "Upgrade Office", JOptionPane.YES_NO_OPTION) == 0)
								{
									player.upgradeProperty(site);
									numOfCC++; 
									game.getBoard().update();
									siteInfoBox.update();
									
								}
								else
								{
									break;
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Displays a message to the user showing a random event has occurred.
	 * @param event The event whose text is shown to the user.
	 */
	public static void showEvent(MandatoryEvent event)
	{
		JOptionPane.showMessageDialog(null, event.getText(), "Event", JOptionPane.INFORMATION_MESSAGE);
		JOptionPane.showMessageDialog(null,  event.getTextResponse(), "Event", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Displays a message to the user, and asks them a simple Yes/No question.
	 * @param event The event that's just happened to the player. 
	 * @return If the user selected Yes (True) or No (False).
	 */
	public static boolean showEvent(OptionalEvent event)
	{
		if(JOptionPane.showConfirmDialog(null, event.getText(), "Event", JOptionPane.YES_NO_OPTION) == 0)
		{
			JOptionPane.showMessageDialog(null, event.getTextResponseYes(), "Event", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		else
		{
			JOptionPane.showMessageDialog(null, event.getTextResponseNo(), "Event", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
	
	/**
	 * Displayed when a player wins the game, before exiting the game completely.
	 * @param player The winning player.
	 */
	public static void showVictory(Player player)
	{
		JOptionPane.showMessageDialog(null, player.getName() + " wins!\n\nThe game will now close.\nThanks for playing! ^^", "Victory", JOptionPane.PLAIN_MESSAGE);
		System.exit(0);
	}
}
