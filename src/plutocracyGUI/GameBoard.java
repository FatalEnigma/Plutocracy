
package plutocracyGUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;


import plutocracy.*;

/**
 *	The main GUI class that runs the gameboard
 */
@SuppressWarnings({ "unused", "serial" })
public class GameBoard extends JFrame 
{
	// The size of the cells.
	private final int cellWidth = 100;
	private final int cellHeight = 100;
	
	// Reference to the game.
	private Plutocracy game;
	
	// The dimensions of the grid layout.
	private final int dimX = 9;
	private final int dimY = 9;
	
	// The font of the player symbols.
	private Font playerFont;
	
	// Store references to the game-logic lists.
	private ArrayList<Cell> cellList;
	private ArrayList<Player> playerList;
	
	// Store the CellGUIs
	private ArrayList<CellGUI>cellGUIs;
	
	// Store the icons for buildings.
	private Image officeIcon;
	private Image conferenceIcon;
	
	// Store the icons for the Cells.
	private Image eventIcon;
	private Image taxIcon;
	private Image centralBankIcon;
	
	// Declare the container.
	private Container c;
	
	// Declare the central JPanel.
	private JPanel grid;
	
	// Declare the InfoBox that's displayed whenever a SIte is clicked.
	private InfoBox clickedCell;
	
	// Declare the Player Info Box
	private PlayerInfoCell[] playerInfoGUI;
	
	// Declare the "end current player's turn button".
	private JButton btnEndTurn;
	
	// Declare the "roll Dice" button.
	private JButton btnRollDice;
	
	// Declare the GameLog.
	private GameLog log;
	
	// Declare JPanel that stores Money held by central bank
	private JLabel centralBankMoney;
	
	/**
	 * Builds a game-board using the lists in the main game class. 
	 * @param game is a reference to the central game-logic object.
	 */
	public GameBoard(Plutocracy game)
	{
		// Set the title of the JFrame.
		super(new String("Plutocracy"));
		
		// Store the reference.
		this.game = game;
		
		// Get the imageIcons for use on the game-board and Site GUIs.
		officeIcon  = getImage("plutocracyGUI/resources/office.png");
		conferenceIcon = getImage("plutocracyGUI/resources/conference.png");
		eventIcon = getImage("plutocracyGUI/resources/event.png");
		taxIcon = getImage("plutocracyGUI/resources/tax.png");
		centralBankIcon = getImage("plutocracyGUI/resources/centralBank.png");
		
		// Store references to the game-logic lists.
		cellList = game.getBoardCells();
		playerList = game.getPlayers();
		
		// Get the container.
		c = this.getContentPane();
		
		// Initialise the grid.
		grid = new JPanel(new GridBagLayout());
		
		// Initialise the font.
		playerFont = new Font("Serif", Font.PLAIN, 30);
		
		// Set up the cells.
		setUpCells();
		
		// Set the player's up.
		setUpPlayers();
		
		// Add the gird to the frame.
		c.add(grid);
		
		// Set the default close operation.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set the siteInfoBox to null.
		setClickedCell(null);
		
		// Get the game-log.
		log = new GameLog();
		log.pack();
		log.setAlwaysOnTop(true);
		
		// Maximise and make visible.
		setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		grid.setBackground(Color.BLACK);
		setVisible(true);
		log.setVisible(true);
		
	}
	
	/**
	 * Set up the cells around the board,
	 * Add all buttons, and panels
	 */
	private void setUpCells() 
	{
		// Initailise the list.
		cellGUIs = new ArrayList<CellGUI>();

		// Create the grid layout.
		grid.setLayout(new GridLayout(9,9, 5, 5));
		
		// Flood the list with Blanks.
		for(int i = 0; i < 81; i++)
		{
			cellGUIs.add(new BlankCellGUI());
		}
		
		// Loop through the cellGUIs list, adding the Cells from CellList.
		for (int i = 0; i < cellList.size(); i++)
		{
			// Declare a CellGUI.
			CellGUI temp = null;
			
			// Get the cell from the cell list.
			Cell cell = cellList.get(i);
				
			// Create a GUI component based on the type of Cell.
			switch(cell.getType())
			{
				case CENTRAL_BANK:
					temp = new CentralBankGUI();
					break;
				case RANDOM_EVENT:
					temp = new EventGUI();
					break;
				case SITE:
					temp = new SiteGUI((Site)cell);
					break;
				case TAX:
					temp = new TaxGUI();
					break;
				case BLANK:
					temp = new BlankCellGUI();
					break;
			}
				
			// Get the position of the cell in the grid.
			int gridPosition = translateBoardPosition(i);	
			
			// Add the cellGUI to the grid.
			cellGUIs.remove(gridPosition);
			cellGUIs.add(gridPosition, temp);
		}
		
		// Put in the PlayerInfoCells
		for(int i = 0; i < game.getPlayers().size(); i++)
		{
			// Start at grid index 17 and move downwards.
			cellGUIs.remove(19 + (i * 9) + 1);
			cellGUIs.add(19 + (i * 9) + 1, new PlayerInfoCell(game.getPlayers().get(i)));
		}
				
		// Fill the grid with references to the GUI
		for(CellGUI cellGUI : cellGUIs)
			grid.add(cellGUI);
		
		// Add the roll dice button.
		setBtnRollDice(new JButton("Roll Dice"));
		getBtnRollDice().setEnabled(false);
		
		// Add all the players to the roll dice button.
		for(Player p : game.getPlayers())
			getBtnRollDice().addActionListener(p);
		
		// Add the roll dice button to the grid.
		((BlankCellGUI)grid.getComponent(60)).add(getBtnRollDice());
		
		// Add the end turn button.
		setBtnEndTurn(new JButton("End Turn"));
		getBtnEndTurn().setEnabled(false);
		getBtnEndTurn().addActionListener(game);
		((BlankCellGUI)grid.getComponent(69)).add(getBtnEndTurn());	
		
		centralBankMoney = new JLabel("Central Bank Cash: £" + game.getCentralBank().getCash());
		centralBankMoney.setForeground(Color.white);
		((BlankCellGUI)grid.getComponent(60)).add(centralBankMoney);
	}

	
	/**
	 * Loop through player list and add their symbol onto the boar
	 */
	private void setUpPlayers() 
	{
		// Place all of the player symbols onto the board position.
		for(Player p : playerList)
		{
			// Get the cell the player is on.
			CellGUI temp = (CellGUI) grid.getComponent(translateBoardPosition(p.getBoardPosition()));
			
			// Add a JLabel to the cells player panel.
			JLabel lblTemp = new JLabel("" + p.getSymbol());

			lblTemp.setForeground(Color.RED);
			lblTemp.setFont(playerFont);
			temp.pnlPlayers.add(lblTemp);
		}
	}
	
	/**
	 * Translates the Player's board position into a grid position.
	 * @param boardPosition The player's board position.
	 */
	private static int translateBoardPosition(int boardPosition)
	{
		// Depending on the side, manipulate the board position differently.
		if(boardPosition >= 0 && boardPosition <= 8)
			return boardPosition;
		else if(boardPosition > 8 && boardPosition <= 16)
			return ((boardPosition - (8 - 1)) * 9) - 1;
		else if(boardPosition > 16 && boardPosition <= 24)
			return(80 - (boardPosition - 16));
		else
			return(72 - (boardPosition - 24) * 9);
	}

	/**
	 * Returns an image resource as an ImageIcon.
	 * @param path The path of the image, relative to root.
	 * @return The icon at the path.
	 */
	public static Image getImage(String path)
	{
		//return new ImageIcon(getClass().getResource(path));
		//return new ImageIcon(path).getImage();
		Image img = null;
		try
		{
			InputStream imgStream = Plutocracy.class.getClassLoader().getResourceAsStream(path);
			img = ImageIO.read(imgStream);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return img;
	}
	
	/**
	 * Updates all the cells on the game board.
	 */
	public void update()
	{
		// Update every cell on the game-board.
		for(CellGUI cellGUI : cellGUIs)
		{
			cellGUI.update();
			cellGUI.revalidate();
			cellGUI.repaint();
		}
		
		// Place all of the player symbols onto the board position.
		for(Player p : playerList)
		{
			// Get the cell the player is on.
			CellGUI temp = (CellGUI) grid.getComponent(translateBoardPosition(p.getBoardPosition()));
						
			// Add a JLabel to the cells player panel.
			JLabel lblTemp = new JLabel("" + p.getSymbol());

			lblTemp.setForeground(Color.RED);
			lblTemp.setFont(playerFont);
			temp.pnlPlayers.add(lblTemp);
			
			temp.revalidate();
			temp.repaint();
		}
		
		centralBankMoney.setText("Central Bank Cash: £" + game.getCentralBank().getCash());
		grid.revalidate();
		grid.repaint();
	}
	

	/**
	 * The abstract class that represents a cell on the game-board.
	 */
	private abstract class CellGUI extends JPanel
	{
		// Declare the panel used to display info on the Cell.
		protected JPanel pnlCellInfo;
		
		// Declare the panel showing the number of players on the Cell.
		private JPanel pnlPlayers;
		
		
		/**
		 * Base constructor for the CellGUI.
		 */
		public CellGUI()
		{
			// Initialise the JPanel to have two rows and 1 column.
			// The top part contains the cell-specific GUI, and the bottom part
			// contains the players currently on that square.
			super(new GridLayout(2,1));
			
			// Set the size of the cell on screen.
			setPreferredSize(new Dimension(cellWidth, cellHeight));
			
			// Construct and add an inner Panel to display the Cell info in the top half.
			pnlCellInfo = new JPanel(new GridLayout(1,1));
			pnlCellInfo.setPreferredSize(pnlCellInfo.getMaximumSize());
			pnlCellInfo.setOpaque(false);
			add(pnlCellInfo);
			
			// Construct and add the panel showing the current players on the bottom half.
			pnlPlayers = new JPanel(new FlowLayout());
			pnlPlayers.setOpaque(false);
			add(pnlPlayers);
			
			// Draw a border around the entire cell.
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
		}
		
		/**
		 * Updates the CellGUI to match the game-logic.
		 */
		public void update()
		{
			// Clean out the players.
			pnlPlayers.removeAll();
		}
	}
	
	/**
	 * Create a cell on the board to show player info
	 */
	private class PlayerInfoCell extends BlankCellGUI implements MouseListener
	{
		// Declare a reference to the player. 
		private Player player;
		
		// Declare the various JLabels.
		private JLabel lblPlayerSymbolName;
		private JLabel lblPlayerBankBrand;
		private JLabel lblPlayerCash;
		
		// Store the Labels in a list.
		private ArrayList<JLabel> lblList;
		
		// Declare the ActionListener that shows the PlayerInfoBox.
		private ActionListener listener;
		
		// Declare the font.
		private Font lblFont;
		
		/**
		 * Constructor
		 * @param player the player whos info you want to show
		 */
		public PlayerInfoCell(final Player player)
		{
			// Store the reference to player.
			this.player = player;
			
			// Set the layout of the Panel.
			setLayout(new GridLayout(3,1,5,5));
			
			// Initialise the list.
			lblList = new ArrayList<JLabel>();
			
			// Construct the font.
			lblFont = new Font("Serif",Font.PLAIN, 15);
			
			// Construct the JLabels.
			lblPlayerSymbolName = new JLabel();
			lblPlayerBankBrand = new JLabel();
			lblPlayerCash = new JLabel();
			
			// Add them to the list.
			lblList.add(lblPlayerSymbolName);
			lblList.add(lblPlayerBankBrand);
			lblList.add(lblPlayerCash);
			
			// Update the information in the JLabels.
			this.update();
			
			// Set the font and add the JLabels to the stackPanel.
			for(JLabel label : lblList)
			{
				label.setFont(lblFont);
				label.setHorizontalTextPosition(SwingConstants.CENTER);
				label.setForeground(Color.WHITE);
				add(label);
			}
				
			
			// Create the ActionListener that shows the SiteInfoBox of a site on Click.
			addMouseListener(this);
			
			listener =
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						// Kill the current InfoBox;
						killInfoBox();
						
						// Display the PlayerInfoBox.
						setClickedCell(new PlayerInfoBox(player));
					}
				};
		}
	

		/**
		 *	Catches when the mouse is clicked and passes the event to the listener.  		
		 */
		@Override
		public void mouseClicked(MouseEvent e) 
		{	
			ActionEvent event = new ActionEvent(this,0, player.getName());
			listener.actionPerformed(event);
		}
		
		// These modify the border to show selection.
		@Override
		public void mouseEntered(MouseEvent arg0)
		{
			setBorder(BorderFactory.createLineBorder(Color.WHITE));
		}
		@Override
		public void mouseExited(MouseEvent arg0)
		{
			setBorder(null);
		}	
		
		/**
		 * Causes the PlayerInfoCell to be repainted on mouse press.
		 */
		@Override
		public void mousePressed(MouseEvent arg0) 
		{
			setBorder(BorderFactory.createLineBorder(Color.WHITE, 7));
			repaint();
		}

		/**
		 * Causes the PlayerInfoCell to be repainted on mouse release.
		 */
		@Override
		public void mouseReleased(MouseEvent arg0) 
		{
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
			repaint();
		}
	
		/**
		 * Updates the Player's information in the PlayerInfoCell
		 */
		@Override
		public void update()
		{
			this.lblPlayerSymbolName.setText("" + player.getSymbol() + " " + player.getName());
			this.lblPlayerBankBrand.setText(player.getBankBrand().toString());
			this.lblPlayerCash.setText("£" + player.getCash());
			
			// Removes this Cell if the player has been made bankrupt.
			if(player.isBankrupt())
				this.setVisible(false);
		}
	}
	
	/**
	 * The layout for each cell containing a site.
	 */
	private class SiteGUI extends CellGUI implements MouseListener
	{
		// Store a reference to the site and it's system colour.
		private Site siteRef;
		private Color color;
		
		// Declare the inner JPanels to display the Site colour and buildings.
		private JPanel pnlColorAndBuildings;
		
		// Declare the JLabel that shows the name and Owner.
		private JLabel lblInfo;
		
		// Declare the font used for building icons.
		Font buildingFont;
		
		// Store the ActionListener. 
		private ActionListener listener;
		
		/**
		 * Constructs a GUI for a Site.
		 * @param site The site which the SiteGUI references.
		 */
		public SiteGUI(Site site)
		{
			// Store a reference to the Site.
			this.siteRef = site;
			
			// Change the layout of the cellInfo panel.
			pnlCellInfo.setLayout(new GridLayout(2,1));
			
			// Store the colour of the site.
			color = translateColour(siteRef.getColour());
			
			// Initialise the JPanel that displays the colour of the site, and it's buildings.
			pnlColorAndBuildings = new JPanel(new FlowLayout());
			
			// Set the dimensions and colour of the panel.
			pnlColorAndBuildings.setPreferredSize(new Dimension(20, cellWidth));
			pnlColorAndBuildings.setBackground(this.color);
			
			// Construct the building font.
			buildingFont = new Font("SansSerif", Font.BOLD, 16);
			
			// Initialise the labels.
			lblInfo = new JLabel(siteRef.getName());
			
			// Add the components to the SiteCellGUI.
			pnlCellInfo.add(pnlColorAndBuildings);
			pnlCellInfo.add(lblInfo);
			
			// Add a mouseListener to this SiteCellGUI.
			addMouseListener(this);
			
			// Create the ActionListener that shows the SiteInfoBox of a site on Click.
			listener =
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						// If there's already a siteInfoBox shown, destroy it.
						killInfoBox();
						
						// Show the site's SiteInfoBox
						setClickedCell(new SiteInfoBox(siteRef));
					}
				};
		}

		/**
		 * Translates a colour from the Colour enumeration, to a system color.
		 * @param colour The colour to be translated.
		 * @return The system color that matches the given colour.
		 */
		private Color translateColour(Colour colour) 
		{	
			switch(colour)
			{
				case PURPLE:
					return new Color(142, 56, 142);
				case BLUE:
					return new Color(0,0,255);
				case GREEN:
					return new Color(152,251,152);
				case IRISHGREEN:
					return new Color(50, 205, 50);
				case ORANGE:
					return new Color(255,165,0);
				case LIGHTBLUE:
					return new Color(173, 216, 230);
				case YELLOW:
					return new Color(255,255,0);
				case RED:
					return new Color(255, 0, 0);				
			}
			
			// If none of the Colours match, return black.
			return Color.BLACK;
		}

		/**
		 *	Catches when the mouse is clicked and passes the event to the listener.  		
		 */
		@Override
		public void mouseClicked(MouseEvent e) 
		{	
			ActionEvent event = new ActionEvent(this,0,siteRef.getName());
			listener.actionPerformed(event);
		}
		
		// These do nothing and are present to satisfy the MouseListener implementation.
		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		
		/*
		 * The followin two functions mouePressed(...) and mouseReleased(...) are used to
		 * "repaint" the Site GUI. Their inclusion allows a visual effect to be displayed
		 * during the selection of the SiteGUI.
		 * 
		 * This is done by overloading the paint(Graphics g) method, and having a branch
		 * where hit==true paints an effect for "mousePressed()", and   
		 */
		
		/**
		 * Causes the Site GUI to be repainted on mouse press.
		 */
		@Override
		public void mousePressed(MouseEvent arg0) 
		{
			setBorder(BorderFactory.createLineBorder(Color.BLACK, 7));
			repaint();
		}

		/**
		 * Causes the Site GUI to be repainted on mouse release.
		 */
		@Override
		public void mouseReleased(MouseEvent arg0) 
		{
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
			repaint();
		}
		
		/**
		 * Updates the SiteGUI with information from the Site.
		 */
		@Override
		public void update() 
		{	
			super.update();
			
			// Clean out the Buildings JPanel.
			pnlColorAndBuildings.removeAll();
			
			// Update the ownerLabel.
			if(!siteRef.getOwner().equals(game.getCentralBank()))
				lblInfo.setText(siteRef.getName() + " - " + ((Player)(siteRef.getOwner())).getSymbol() + " " + siteRef.getOwner().getName());
			
			// Add the appropriate Icons as Labels into the now empty panel.
			for(Property property : siteRef.getProperties())
			{
				if(property.getPropertyType()==PropertyType.OFFICE)
					pnlColorAndBuildings.add(new JLabel(new ImageIcon(getImage("plutocracyGUI/resources/office.png"))));
				else
					pnlColorAndBuildings.add(new JLabel(new ImageIcon(getImage("plutocracyGUI/resources/conference.png"))));
			}
			
			// Modify the JLabels.
			for(Component lbl : pnlColorAndBuildings.getComponents())
			{
				lbl.setFont(buildingFont);
				lbl.setForeground(Color.MAGENTA);
			}
		}	
	}
	
	/**
	 * Creates a cell for the Tax Square and adds the image
	 */
	private class TaxGUI extends CellGUI
	{
		// Declare the labels used to denote a Tax cell.
		JLabel lblTax;
	
		/**
		 * Constructs cell with a tax symbol on it.
		 */
		public TaxGUI()
		{
		}
		
		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(taxIcon, (this.getWidth() / 2) - (taxIcon.getWidth(this) / 2), 0, null);
		}
	}
	
	/**
	 * Creates a cell for events and adds the image
	 */
	private class EventGUI extends CellGUI
	{
		// Declare the label used to denote an Event Cell.
		private JLabel lblEvent;
		
		/**
		 * Constructs a cell with an event symbol on it.
		 */
		public EventGUI()
		{
		}
		
		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(eventIcon, (this.getWidth() / 2) - (eventIcon.getWidth(this) / 2), 0, null);
		}
	}
	
	/**
	 * Creates a cell for the Central Bank and adds the image
	 */
	private class CentralBankGUI extends CellGUI
	{
		// Declare the label used to denote an Event Cell.
		private JLabel lblCB;
				
		/**
		 * Constructs a cell with a central bank symbol on it.
		 */
		public CentralBankGUI()
		{
		}
		
		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(centralBankIcon, (this.getWidth() / 2) - (centralBankIcon.getWidth(this) / 2), 0, null);
		}
	}
	
	/**
	 * This constructs a blank cell, and therefore does nothing except remove the border.
	 */
	private class BlankCellGUI extends CellGUI
	{
		/**
		 * Constructs a blank cell.
		 */
		public BlankCellGUI()
		{
			//setBorder(BorderFactory.createLineBorder(Color.GREEN));
			setOpaque(false);
			this.removeAll();
		}
	}
	
	/**
	 * Kills the siteInfoBox on the screen if present.
	 */
	public void killInfoBox()
	{
		if(getClickedCell() != null)
			getClickedCell().dispose();
	}
	
	/**
	 * Store the cell the user clicks
	 * @param clickedSite
	 */
	public void setClickedCell(InfoBox clickedSite) {
		this.clickedCell = clickedSite;
	}

	/**
	 * @return the btnEndTurn
	 */
	public JButton getBtnEndTurn() {
		return btnEndTurn;
	}

	/**
	 * @param btnEndTurn the btnEndTurn to set
	 */
	public void setBtnEndTurn(JButton btnEndTurn) {
		this.btnEndTurn = btnEndTurn;
	}

	/**
	 * @return the btnRollDice
	 */
	public JButton getBtnRollDice() {
		return btnRollDice;
	}

	/**
	 * @param btnRollDice the btnRollDice to set
	 */
	public void setBtnRollDice(JButton btnRollDice) {
		this.btnRollDice = btnRollDice;
	}
	
	/**
	 * @return The Gamelog.
	 */
	public GameLog getLog()
	{
		return log;
	}
	
	public void setLog(GameLog log)
	{
		this.log = log;
	}

	/**
	 * @return the cell that the user has clicked
	 */
	public InfoBox getClickedCell() {
		return clickedCell;
	}
	
}