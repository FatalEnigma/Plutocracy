package plutocracy;
import plutocracyGUI.*;
import plutocracyGUI.GameLog.logType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.ImageIcon;


/**
 * A Player of the game.
 */
public class Player extends Bank implements ActionListener
{
	// Store a reference to the game.
	private Plutocracy game;
	
	// Store the Player's current board position.
	private int boardPosition;
	
	// Store the bankBrand.
	private BankBrand bankBrand;
	
	// Store the Player's Karma.
	private int karma;
	
	// Store references to the sites owned by the Player.
	private ArrayList<Site> ownerSites;
	
	// Store a reference to the Player's bank
	private Bank playerBank;
	
	// Store a reference to the Central Bank
	private CentralBank centralBank;
	
	// Store the player symbol.
	private char playerSymbol;
	
	// Store the player's bank logo.
	ImageIcon bankLogo;
	
	// Stores if the player is bankrupt.
	private boolean isBankrupt;
	
	/**
	 * Constructs a Player for use in the game-logic and GUI.
	 * @param game A reference to the central game object.
	 */
	public Player(String name, BankBrand bankBrand, Plutocracy game) 
	{
		// Construct the base class.
		super(game);
	
		// Initialise the variables.
		this.game = game;
		this.boardPosition = 0;
		this.karma = 0;
		
		centralBank = game.getCentralBank();
		playerBank = this;
		
		// Get the player details.
		this.name = name;
		this.bankBrand = bankBrand;
		
		// Initialise the list of sites owned by the Player.
		ownerSites = new ArrayList<Site>();
		
		isBankrupt = false;
		
		// Set the Player Symbol.
		switch(game.getPlayers().size())
		{
			case 0:
				playerSymbol = '\u25A0'; // Square
				break;
			case 1:
				playerSymbol = '\u25B2'; // Triangle
				break;
			case 2:
				playerSymbol = '\u25CF'; // Circle
				break;
			case 3:
				playerSymbol = '\u25C6'; // Diamond
				break;
		}	
		
		// Set the starting cash.
		cash = 25000;
	}
	
	/**
	 * Return the list of ownerSites
	 */
	public ArrayList<Site> getOwnerSites()
	{
		return ownerSites;
	}
	
	/**
	 * Moves the player round the board n cells.
	 * @param n The number of positions the player is moved around the board.
	 */
	private void increaseBoardPosition(int n)
	{
		// Add n to the current board position and loop round if necessary.
		boardPosition = boardPosition + n;
		
		// Check to see has the player looped round the board.
		if(boardPosition >= game.getBoardCells().size())
		{
			boardPosition %= game.getBoardCells().size();
			
			// Check that player has not just landed on the Central Bank
			if (boardPosition>0)
				goPastCentralBank();
		}
	}
	
	/**
	 * The Player is rewarded with cash whenever they pass the "Central Bank" square
	 */
	public void goPastCentralBank()
	{
		// Store a set amount of money to be rewarded
		int cash = 200;
		
		// Check that the Central Bank has enough money and adjust the cash amount accordingly if not
		if (centralBank.cash < cash)
			cash = centralBank.cash;
		
		// Money is taken away from Central Bank as they are giving it
		centralBank.cashTransaction(-cash);
		
		// Money is added onto the Player's current funds
		playerBank.cashTransaction(cash);
		
		// Output to log box
		game.getBoard().getLog().outputMessageToLogbox(logType.GAME, name + " has received £" + cash + " for passing the central bank");

		
		Dialog.showPassCentralBankConfirmation(cash, getCash());
	}
	
	/**
	 * Sets the Player's position on the board to n.
	 * @param n
	 */
	public void setBoardPosition(int n)
	{
		// If n is not a position on the game-board, throw an exception.
		if(n < game.getBoardCells().size() - 1)
			boardPosition = n;
		else
			boardPosition = 0;
	}
	
	/**
	 * @return The Player's current position on the game-board.
	 */
	public int getBoardPosition()
	{
		return boardPosition;
	}
	
	/**
	 * @return The bankbrand selected by the player.
	 */
	public BankBrand getBankBrand()
	{
		return bankBrand;
	}

	/**
	 * Moves the player around the board when it's their turn, and performs relevant actions.
	 * @throws Exception
	 */
	public void makeMove()
	{
		// Store the sum of the dice rolls.
		int diceSum = 0;
		
		// Roll two dice.
		//int[] diceRolls = rollDice(1);
		
		diceSum = rollDice();
		
		// Sum the results.
/*		for(int i : diceRolls)
			diceSum += i;*/
						
		//FIXME Remove
		// Show the result.
		//JOptionPane.showMessageDialog(null, "You rolled " + diceSum + ".", "Roll Dice", JOptionPane.INFORMATION_MESSAGE);
		
		// Move the player round the board.
		increaseBoardPosition(diceSum);
		
		// Update the gameboard.
		game.getBoard().update();
		
		// Get the current Cell the player is on.
		Cell currentCell = game.getBoardCells().get(boardPosition);
		
/*		// Output to log box
		game.getBoard()
				.getLog()
				.outputMessageToLogbox(
						logType.GAME,
						name + " has rolled " + diceRolls[0] + " and "
								+ diceRolls[1] + ", moved " + diceSum
								+ " squares and landed on a "
								+ currentCell.getType().toString() + " square");*/
		
		// Output to log box
		game.getBoard()
				.getLog()
				.outputMessageToLogbox(
						logType.GAME,
						name + " has moved " + diceSum
								+ " squares and landed on a "
								+ currentCell.getType().toString() + " square");
		
		if(!isBankrupt)
		{
			// Branch depending on the type of Cell landed on.
			switch(currentCell.getType())
			{
				case SITE:
					landedOnSite((Site)currentCell);
					break;
				case CENTRAL_BANK:
					landedOnCentralBank();
					break;
				case RANDOM_EVENT:
					landedOnEvent();
					break;
				case TAX:
					landedOnTax();
					break;
				case BLANK:
					break;
			}
		}
		// Kill the SiteInfoBox if there's one on screen.
		game.getBoard().killInfoBox();
		
		// Update the gameboard.
		game.updateGameBoard();
		
		// Re-enable the button.
		game.getBoard().getBtnEndTurn().setEnabled(true);
	}
	
	/**
	 * Called whenever the cell the player land's on is a Site.
	 * @param site The site landed on.
	 */
	private void landedOnSite(Site site)
	{
		// Get the current Owner.
		Bank owner = site.getOwner();
		
		// If the current owner is another player, pay rent.
		if(game.getPlayers().contains(owner) && !owner.equals(this))
		{
			payRent(site);	
		}
		else
		{
			// Else, show the player their various options.
			Dialog.SiteOptionBox(game, site, this);
		}
	}
	
	/**
	 * Called whenever the player lands on the Central Bank.
	 */
	private void landedOnCentralBank()
	{
		// Reward player with a larger cash sum than what if given to players who merely pass the Central Bank square
		
		// Store a set amount of money to be rewarded
		int cash = 500;
				
		// Check that the Central Bank has enough money and adjust the cash amount accordingly if not
		if (centralBank.cash < cash)
			cash = centralBank.cash;
				
		// Money is taken away from Central Bank as they are giving it
		centralBank.cashTransaction(-cash);
				
		// Money is added onto the Player's current funds
		playerBank.cashTransaction(cash);
				
		// Output to log box
		game.getBoard().getLog().outputMessageToLogbox(logType.GAME, name + " has received £" + cash + " for landing on the central bank");
		
		// Show confirmation
		Dialog.showLandedCentralBankConfirmation(cash, getCash());
		
	}
	
	/**
	 * Called whenever the player lands on an event cell.
	 */
	private void landedOnEvent()
	{
		// Get the random event.
		Event event = game.getEvent(this);
		
		// Act depending on if the event's optional or mandatory.
		if (event.isOptional())
		{
			// Cast the event to OptionalEvent.
			OptionalEvent opEvent = (OptionalEvent)event;
			
			// Get the answer to the OptionalEvent question.
			boolean choseYes = Dialog.showEvent((opEvent));
			
			// Perform the respective manipulations based on the user's choice.
			if(choseYes)
			{
				cashTransaction(opEvent.getCashChangeYes());
				addKarma(opEvent.getKarmaChangeYes());
				// Output to log box
				game.getBoard()
						.getLog()
						.outputMessageToLogbox(
								logType.EVENT,
								(opEvent.getCashChangeYes() < 0) ? name
										+ " selected yes on an optional event and lost £"
										+ Math.abs(opEvent.getCashChangeYes())
										+ " as a result!"
										: name
												+ " selected yes on an optional event and gained £"
												+ opEvent.getCashChangeYes()
												+ " as a result!");
			}
			else
			{
				cashTransaction(opEvent.getCashChangeNo());
				addKarma(opEvent.getKarmaChangeNo());
				// Output to log box
				game.getBoard()
						.getLog()
						.outputMessageToLogbox(
								logType.EVENT,
								(opEvent.getCashChangeNo() < 0) ? name
										+ " selected no on an optional event and lost £"
										+ Math.abs(opEvent.getCashChangeNo())
										+ " as a result!"
										: name
												+ " selected no on an optional event and gained £"
												+ opEvent.getCashChangeNo()
												+ " as a result!");
			}
			
		}
		else
		{
			// Cast the event to MandatoryEvent.
			MandatoryEvent manEvent = (MandatoryEvent)event;
			
			// Display the event.
			Dialog.showEvent(manEvent);
			
			// Perform the event manipulations.
			cashTransaction(manEvent.getCashChange());
			addKarma(manEvent.getKarmaChange());
			
			// Output to log box
			game.getBoard()
					.getLog()
					.outputMessageToLogbox(
							logType.EVENT,
							(manEvent.getCashChange() < 0) ? name
									+ " received a mandatory event and lost £"
									+ Math.abs(manEvent.getCashChange())
									+ " as a result!"
									: name
											+ " received a mandatory event and gained £"
											+ manEvent.getCashChange()
											+ " as a result!");
		}
	}
	
	/**
	 * Called whenever the player lands on a tax cell.
	 */
	private void landedOnTax()
	{
		// Initialize taxAdjustment to 1.5, for each karma a player has, take away 0.1 from the taxAdjustment.
		// Karma can range from -5 to 5, so the max adjustment is 1.5, minimum is 0.5
		double taxAdjustment = 1.5;
		for(int i = -5; i <= karma; i++)
			taxAdjustment -= 0.1;
		
		// Get 10% of current cash, multiply by taxAdjustment for final tax figure.
		double tax = cash / 10 * taxAdjustment;
		
		// Transfer that amount of tax to the Central Bank.
		playerBank.cashTransaction((int)-tax);
		centralBank.cashTransaction((int)tax);
		
		// Output to log box
		game.getBoard().getLog().outputMessageToLogbox(logType.TAX, name + " paid £" + (int)tax + " in tax for landing on a tax square");
		
		// Show the result to the player.
		Dialog.showTaxPaid((int)tax, getCash());
	}
	
	/**
	 * Rolls the number of specified dice and returns the result of each roll in an array.
	 * @param numberOfDice The number of 6-sided dice to be rolled.
	 * @return An array containing the result of each roll.
	 */
	private static int[] rollDice(int numberOfDice)
	{
		// Initialise the variables and random number generator.
		Random dice = new Random();
		int diceRolls[] = new int[numberOfDice];
		
		// Make the rolls and store them.
		for(int i = 0; i < numberOfDice; i++)
			diceRolls[i] = dice.nextInt(6) + 1;
			
		return diceRolls;
	}
	
	/**
	 * Rolls the number of specified dice and returns the result of each roll in an array.
	 * @param numberOfDice The number of 6-sided dice to be rolled.
	 * @return An array containing the resut of each roll.
	 */
	private static int rollDice()
	{
		// Initialise the variables and random number generator.
		Random dice = new Random(); 
			
		return dice.nextInt(6) + 1;
	}
	
	/**
	 * Pays rent when a player lands on another player's Site.
	 * @param site The Site the player lands on, that's owned by someone else.
	 */
	private void payRent(Site site)
	{
		// Get the owner of the site.
		Bank owner = site.getOwner();
		
		// Get how much is owed.
		int amount = site.getRent();
		
		// Transfer the money from the player to the landlord.
		playerBank.cashTransaction(-amount);
		owner.cashTransaction(amount);
		
		isPlayerBankrupt();
		
		// Output to the log box
		game.getBoard().getLog().outputMessageToLogbox(logType.RENT, name + " paid " + owner.getName() + " £" +site.getRent() + " in rent for landing on " + site.getName());
		
		// Show the result in a Dialog box.
		Dialog.showRent(amount, owner, getCash());
	}

	/**
	 * Buy the current Site the player is on, if it's owned by the central bank.
	 */
	public void buySite()
	{
		// Get the Current Site.
		Site site = (Site) game.getBoardCells().get(boardPosition);
		
		// Get the price of the property
		int amount = site.getBasePrice();
								
		// Initiate transaction between the player and the Central Bank
		playerBank.cashTransaction(-amount);
		centralBank.cashTransaction(amount);
		
		// Change the owner of the site to the player.
		site.setOwner(this);
		
		// Add the site to owner's list.
		ownerSites.add(site);
		
		// Output to the log box
		game.getBoard().getLog().outputMessageToLogbox(logType.SITE, name + " bought " + site.getName() + " for £" +site.getBasePrice());

		//FIXME Remove
		// Show a confirmation 
		// Dialog.showBuySiteConfirmation(site, getCash());
	}
	
	public void buySite(Site site)
	{
		// Get the price of the property
		int amount = site.getBasePrice();
								
		// Initiate transaction between the player and the Central Bank
		playerBank.cashTransaction(-amount);
		centralBank.cashTransaction(amount);
		
		// Change the owner of the site to the player.
		site.setOwner(this);
		
		// Add the site to owner's list.
		ownerSites.add(site);
		
		//Check to see the player owns all the sites in group
		site.setIsUpgradeable();
		
		// Output to the log box
		game.getBoard().getLog().outputMessageToLogbox(logType.SITE, name + " bought " + site.getName() + " for £" +site.getBasePrice());
				
		//FIXME Remove		// Show a confirmation 
		//Dialog.showBuySiteConfirmation(site, getCash());
	}
	
	/**
	 * Buy a property on the current site, provided the player already owns the site.
	 */
	public void buyProperty()
	{
		// Get the Current Site.
		Site site = (Site) game.getBoardCells().get(boardPosition);
		
		// Buy the propetry.
		buyProperty(site);
	}
	
	public void buyProperty(Site site)
	{
		// Get the price of the property
		int amount = (int)(0.5 * site.getBasePrice());
						
		// Initiate transaction between the player and the Central Bank
		playerBank.cashTransaction(-amount);
		centralBank.cashTransaction(amount);
		
		// Add a new property to the site.
		site.getProperties().add(new Property((amount)));
		
		// Output to the log box
		game.getBoard().getLog().outputMessageToLogbox(logType.SITE, name + " bought an office block on " + site.getName() + " for £" +amount);

		//FIXME Remove
		//Dialog.showBuyPropertyConfirmation(site, getCash());
	}
	
	/**
	 * Upgrade a property on the current site to a conference centre, provided 3 offices are built on it.
	 */
	public void upgradeProperty()
	{
		// Get the current Site.
		Site site = (Site) game.getBoardCells().get(boardPosition);
		
		upgradeProperty(site);
	}
	
	public void upgradeProperty(Site site)
	{
		// Search for an office.
		for(int i = 0; i < site.getProperties().size(); i++)
		{
			// If an office is found, upgrade it.
			if(site.getProperties().get(i).getPropertyType() == PropertyType.OFFICE)
			{
				// Pay for upgrade
				int amount = site.getProperties().get(i).getUpgradeCost();
				playerBank.cashTransaction(-amount);
				centralBank.cashTransaction(amount);
				
				site.getProperties().get(i).upgrade();
				game.getBoard().getLog().outputMessageToLogbox(logType.SITE, name + " bought a conference centre on " + site.getName() + " for £" +amount);
				break;
			}
		}
	}
	
	/**
	 * 
	 * @return The amount of Karma the player has.
	 */
	public int getKarma()
	{
		return karma;
	}
	
	/**
	 * Adds the given amount to the player's karma.
	 * @param n The amount to add to the player's karma.
	 */
	public void addKarma(int n)
	{
		karma += n;
		
		// Ensure that karma doesn't go above 5 or below -5
		if(karma > 5)
			karma = 5;
		if(karma < -5)
			karma = -5;
	}

	/**
	 * Checks whether a site is owned by player
	 * @param site the site to check
	 */
	public boolean isSiteOwnedBy(Site site)
	{
		if(ownerSites.contains(site))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the player has lost all their funds
	 * Returns their properties to the Central Bank if bankrupt
	 */
	public void isPlayerBankrupt()
	{
		if (playerBank.cash <= 0)
		{
			// Output to log box
			game.getBoard().getLog().outputMessageToLogbox(logType.GAME, name + " is bankrupt and out of the game!");
			
			// Show confirmation
			Dialog.showBankruptConfirmation();
			
			// Return each of the player's properties to the Central Bank
			int size = ownerSites.size();
			for (int i = 0; i < size; i++)
			{
				getOwnerSites().get(i).setOwner(centralBank);
			}
			
			// Set the boolean.
			isBankrupt = true;
			
			// Checks for victory.
			int healthyPlayers = 0;
			int playerIndex = 0;
			
			for(int i = 0; i < game.getPlayers().size(); i++)
			{
				if(!game.getPlayers().get(i).isBankrupt())
				{
					healthyPlayers++;
					playerIndex = i;
				}
			}
			
			if(healthyPlayers == 1)
			{
				Dialog.showVictory(game.getPlayers().get(playerIndex));
			}
		}
	}
	
	/**
	 * Overrides the base cashTransaction() to check if the player is now bankrupt.
	 */
	@Override
	public void cashTransaction(int amount)
	{
		super.cashTransaction(amount);
		isPlayerBankrupt();
	}
	
	/**
	 * Gets the player's symbol.
	 * @return The symbol that represents the player on the gameboard.
	 */
	public char getSymbol()
	{
		return playerSymbol;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		// Disable the roll button if active.
		if(game.getBoard().getBtnRollDice().isEnabled())
			game.getBoard().getBtnRollDice().setEnabled(false);
		
		// If this is the current player, start their move.
		if(game.getPlayers().get(game.getCurrentPlayerIndex()).equals(this))
			makeMove();
	}
	
	/**
	 * @return If the player has been made bankrupt.
	 */
	public boolean isBankrupt()
	{
		return isBankrupt;
	}
	
}
