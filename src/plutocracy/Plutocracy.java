
package plutocracy;

import plutocracyGUI.*;
import plutocracyGUI.GameLog.logType;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

/**
 * The core game code is here - including xml reading
 */
public class Plutocracy implements ActionListener
{
	//Declaring class variables.
	private Document doc;
	
/*	private NodeList nList;
	private Node nNode;
	private int numOfCells = 32;*/
	
	private int random;
	private ArrayList<Cell> boardCells;
	private ArrayList<Player> playerList;
	private ArrayList<Event> eventList;
	private GameBoard board;
	private CentralBank cb;
	private int currentPlayerIndex;
	private int[] propertyColorCount = new int[Colour.values().length];
	
	public Plutocracy()
	{	
		// Initialise the variousLists.
		playerList = new ArrayList<Player>();
		boardCells = new ArrayList<Cell>();
	}
	
	/**
	 * Sets up the game.
	 */
	public void setup()
	{	
		// Initialise the Central Bank
		cb = new CentralBank(this);
				
		// Get the number of players.
		int numPlayers = Dialog.getHowManyPlayers(2,4); //
		
		// Get the players.
		for(int i = 0; i < numPlayers; i++)
		{
			playerList.add(Dialog.getPlayer(this));
		}
		
		// Load in the XML Lists.
		boardCells = loadBoardSquares();
		eventList = loadEvents();
		
		// Set up the game-board.
		setBoard(new GameBoard(this));
		
		// Reset the currentPlayerIndex.
		currentPlayerIndex = -1;
		
		// Get the players.
		for (int i = 0; i < numPlayers; i++) {
			Player p = playerList.get(i);
			
			// Output to log box
			board.getLog().outputMessageToLogbox(logType.GAME, p.getName() + " has joined the game and is playing as " + p.getBankBrand().toString());
		}
	}
	
	/**
	 * Starts the game cycle.
	 */
	public void run()
	{
		// Start the Gameloop.
		actionPerformed(null);
		
	}

	/**
	 * @return the list of cells on the board.
	 */
	public ArrayList<Cell> getBoardCells() 
	{
		return boardCells;
	}

	/**
	 * Updates the game-board to match the game-logic.
	 */
	public void updateGameBoard() 
	{
		getBoard().update();
	}

	/**
	 * Calls a method to get a Random Event.
	 */
	  public Event getEvent(Player player)
	  {   
		  //returns an event based on the player's karma.
		  Event event;
		  int playerkarma = player.getKarma();
		  
		  // Get a random number generator.
		  Random rand = new Random();
		  do
		  {
		    random = rand.nextInt(20);
		    event = eventList.get(random);
	  	  }
		  while(!((playerkarma >= event.getKarmaLower()) && (playerkarma <= event.getKarmaUpper())));
		  
		  return event;
	  }
	
	public CentralBank getCentralBank()
	{
		return cb;
	}
	
	public void setCentralBank(CentralBank centralBank)
	{
		cb = centralBank;
	}
	
	public ArrayList<Player> getPlayers()
	{
		return playerList;
	}
	
	  
	  /**
	   * Generates a random number and accesses the random event at that position in nList.
	   */
	public ArrayList<Event> loadEvents() {
		// variables used throughout the method.
		NodeList n = null;
		String optional = "";
		boolean optionalEvent = false;
		NamedNodeMap attributesMap = null;
		ArrayList<Event> events = new ArrayList<Event>();

		// a try-catch ladder parsing in the XML file and building a document
		// out of it that can be read.
		try {
			// Parsing the XML file.
			InputStream xmlStream = Plutocracy.class.getClassLoader()
					.getResourceAsStream("xmlFiles/RandomEvents.xml");
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			doc = dBuilder.parse(xmlStream);

			// Normalising the XML so it can be read properly.
			doc.getDocumentElement().normalize();

			// Creates a list of 'events' from the 'event' tags in the XML.
			n = doc.getElementsByTagName("event");

			// Take each 'event' Node and process it.
			for (int i = 0; i < n.getLength(); i++) {
				// set the current Node.
				Node currentNode = n.item(i);

				// Ensure it is an ELEMENT NODE.
				if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
					// Gets the child nodes of the current event node.
					NodeList childNodes = currentNode.getChildNodes();

					/*
					 * If the current node has attributes, map them to a Named
					 * Node Map, and parse the item 'optional' to a string. If
					 * it is 'true', then this method will return an
					 * OptionalEvent.
					 */
					if (currentNode.hasAttributes()) {
						attributesMap = currentNode.getAttributes();
						optional = attributesMap.getNamedItem("optional")
								.toString();

						if (optional.contains("true")) {
							optionalEvent = true;
						} else {
							optionalEvent = false;
						}
					}

					/*
					 * If the 'optional' attribute was equal to true, there will
					 * be two additional nodes; 'yes' and 'no'; look at their
					 * child nodes and assign the values in them to variables
					 * declared for this method.
					 */
					if (optionalEvent) { // Store the variables for an
											// OptionalEvent
						String text = "";
						String responseTextYes = "";
						String responseTextNo = "";
						int karmaLowerBound = 0;
						int karmaUpperBound = 0;
						int cashchange = 0;
						int karmachange = 0;
						int cashchange1 = 0;
						int karmachange1 = 0;

						// Step through the child nodes.
						for (int j = 0; j < childNodes.getLength(); j++) {
							NodeList yesNodes;
							NodeList noNodes;

							// assign values to the child nodes, depending on
							// their tag name.
							switch (childNodes.item(j).getNodeName()) {
							case ("text"):
								text = childNodes.item(j).getTextContent();
								break;
							case ("karmaLowerBound"):
								karmaLowerBound = Integer.parseInt(childNodes
										.item(j).getTextContent());
								break;
							case ("karmaUpperBound"):
								karmaUpperBound = Integer.parseInt(childNodes
										.item(j).getTextContent());
								break;
							case ("yes"): {
								yesNodes = childNodes.item(j).getChildNodes();

								for (int x = 0; x < yesNodes.getLength(); x++) {
									switch (yesNodes.item(x).getNodeName()) {
									case ("karmachange"):
										karmachange = Integer.parseInt(yesNodes
												.item(x).getTextContent());
										break;

									case ("cashChange"):
										cashchange = Integer.parseInt(yesNodes
												.item(x).getTextContent());
										break;

									case ("textResponseYes"):
										responseTextYes = yesNodes.item(x)
												.getTextContent();
										break;

									}

								}

							}
								break;
							case ("no"): {
								noNodes = childNodes.item(j).getChildNodes();

								for (int x = 0; x < noNodes.getLength(); x++) {
									switch (noNodes.item(x).getNodeName()) {
									case ("karmachange1"):
										karmachange1 = Integer.parseInt(noNodes
												.item(x).getTextContent());
										break;

									case ("cashChange1"):
										cashchange1 = Integer.parseInt(noNodes
												.item(x).getTextContent());
										break;

									case ("textResponseNo"):
										responseTextNo = noNodes.item(x)
												.getTextContent();
										break;
									}
								}
							}
								break;
							}
						}

						// add an OptionalEvent to the arrayList.
						OptionalEvent temp = new OptionalEvent(text,
								cashchange, karmachange, karmaUpperBound,
								karmaLowerBound, cashchange1, karmachange1,
								responseTextYes, responseTextNo);
						events.add(temp);
					} else {
						// Store the variables for a MandatoryEvent
						String text = "";
						String responseText = "";
						int karmaLowerBound = 0;
						int karmaUpperBound = 0;
						int cashchange = 0;
						int karmachange = 0;

						for (int j = 0; j < childNodes.getLength(); j++) {

							// assign values to the child nodes, depending on
							// their tag name.
							switch (childNodes.item(j).getNodeName()) {
							case ("text"):
								text = childNodes.item(j).getTextContent();
								break;
							case ("karmaLowerBound"):
								karmaLowerBound = Integer.parseInt(childNodes
										.item(j).getTextContent());
								break;
							case ("karmaUpperBound"):
								karmaUpperBound = Integer.parseInt(childNodes
										.item(j).getTextContent());
								break;
							case ("cashChange"):
								cashchange = Integer.parseInt(childNodes
										.item(j).getTextContent());
								break;
							case ("karmachange"):
								karmachange = Integer.parseInt(childNodes.item(
										j).getTextContent());
								break;
							case ("textResponse"):
								responseText = childNodes.item(j)
										.getTextContent();
								break;
							}

						}

						// add a MandatoryEvent to the arrayList.
						MandatoryEvent temp = new MandatoryEvent(text,
								cashchange, karmachange, karmaUpperBound,
								karmaLowerBound, responseText);
						events.add(temp);
					}
				}
			}
		}
		// Catch any exceptions that may be thrown.
		catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return events;
	}
 
	
	/**
	 * Returns an Arraylist of Cells generated from the GameBoard XML file
	 * 
	 * @return The ArrayList of type cell containing all the board cells in the XML file
	 */
	public ArrayList<Cell> loadBoardSquares() {

		// Declare variables
		ArrayList<Cell> boardCells = new ArrayList<Cell>();
		NodeList squareNodeList = null;
		NamedNodeMap attributeNodeMap;
		
		// Try-catch ladder parsing in the XML file and building a document out of it that can be read.
		
		try {
			InputStream xmlStream = Plutocracy.class.getClassLoader().getResourceAsStream("xmlFiles/GameBoard.xml");
		 
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		 
			Document doc = dBuilder.parse(xmlStream);
			
			//Normalising the XML so it can be read properly.
    		doc.getDocumentElement().normalize();
    		
			// Create a node list with all the square elements
			squareNodeList = doc.getElementsByTagName("Square");
		
		for (int count = 0; count < squareNodeList.getLength(); count++) {
			// Cycle through the nodeList
				Node currentNode = squareNodeList.item(count);

				// Make sure it's an element node.
				if (currentNode.getNodeType() == Node.ELEMENT_NODE) {

					if (currentNode.hasAttributes()) {
						// Get attributes and add to nodeMap
						attributeNodeMap = currentNode.getAttributes();

						for (int i = 0; i < attributeNodeMap.getLength(); i++) {
							// Cycle through nodeMap and check attribute values
							Node node = attributeNodeMap.item(i);
							
							if(node.getNodeName() == "type")
							{
								// Convert the current node to an element to enable various methods to be called
								Element nodeElement = (Element) currentNode;
							
								// Either create a cell or a site instance based on value of type attribute
								if (!(node.getTextContent().equals("SITE"))) {
									Cell newCell = new Cell(
											CellType.valueOf(node
													.getTextContent()),
											nodeElement.getElementsByTagName(
													"name").item(0)
													.getTextContent());
									boardCells.add(newCell);
								}
								
								else if(node.getTextContent().equals("SITE"))
								{
									Site newSite = new Site(nodeElement
											.getElementsByTagName("name").item(0)
											.getTextContent(),
											Integer.parseInt(nodeElement
													.getElementsByTagName(
															"basePrice").item(0)
													.getTextContent()),
											Integer.parseInt(nodeElement
													.getElementsByTagName(
															"baseRent").item(0)
													.getTextContent()),
											Colour.valueOf(nodeElement
													.getElementsByTagName("colour")
													.item(0).getTextContent()), this);
									
									boardCells.add(newSite);
									
									propertyColorCount[Colour.valueOf(nodeElement.getElementsByTagName("colour").item(0).getTextContent()).ordinal()] += 1;
								}
							}
							// if the attribute is not id or type then there must be an error in the XML file
							else if (node.getNodeName() != "id")
								System.out.println("Invalid attribute, please check XML file!");					}
					}
				}
		}
				// Catch any exceptions which may be thrown
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (DOMException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return boardCells;

	  }
	
	/**
	 * @return an array holding the number of properties of each color
	 */
	public int[] getPropertyColorCount()
	{
		return propertyColorCount;
	}
	
	/**
	 * @return the player number of the current turn
	 */
	public int getCurrentPlayerIndex()
	{
		return currentPlayerIndex;
	}

	@Override
	/**
	 * Plutocracy picks up the end game button from here
	 */
	public void actionPerformed(ActionEvent arg0) 
	{
		do
		{
			// Disable the button.
			board.getBtnEndTurn().setEnabled(false);
		
			// Sort out the currentPlayerIndex.
			if(currentPlayerIndex == -1)
				currentPlayerIndex = 0;
			else
			{
				// If the current player is the last player in the list, loop back.
				if(currentPlayerIndex == playerList.size() - 1)
					currentPlayerIndex = 0;
				else
					currentPlayerIndex++;
			}
		}while(playerList.get(currentPlayerIndex).isBankrupt());
		
		// Output to log box
		board.getLog().outputMessageToLogbox(logType.GAME, "Turn begins for: " + playerList.get(currentPlayerIndex).getName());
		
		// Display the current player.
		JOptionPane.showMessageDialog(null, playerList.get(currentPlayerIndex).getSymbol() + " " + playerList.get(currentPlayerIndex).getName() + "'s turn!", "Next Player", JOptionPane.INFORMATION_MESSAGE);
		
		// Enable the roll dice button.
		board.getBtnRollDice().setEnabled(true);
	}

	/**
	 * @return the board
	 */
	public GameBoard getBoard() {
		return board;
	}

	/**
	 * @param board the board to set
	 */
	public void setBoard(GameBoard board) {
		this.board = board;
	}
}

