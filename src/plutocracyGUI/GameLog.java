package plutocracyGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JWindow;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 * The log area on the gameboard
 */
public class GameLog extends JWindow
{
	private static final long serialVersionUID = -5123225663595326268L;
	
	// Add the tabbed pane for monitoring all game activities
	private JTabbedPane logTabbedPane;
		
	// Add the Textpanes for outputting the events
	private JTextPane allTextpane, gameTextpane, rentTextpane, taxTextpane, siteTextpane, eventTextpane;
		
	// Add the scrollpanes to enable scrollbars
	private JScrollPane allTextpaneScroll, gameTextpaneScroll, rentTextpaneScroll, taxTextpaneScroll, siteTextpaneScroll, eventTextpaneScroll;
	
	// Used to always make the logs go to the bottom
	JScrollBar vertical;
	
	// Add style related items
	private StyleContext context;
	private StyledDocument documentAll, documentGame, documentRent, documentTax, documentSite, documentEvent;
	private javax.swing.text.Style style;
		
	// Enumeration for the different types of log messages
	public enum logType {GAME, RENT, TAX, SITE, EVENT};
	
	/**
	 * Set up the gameLog
	 */
	public GameLog()
	{
		// Set up the logging box
		logTabbedPane = new JTabbedPane();
				
		// Needed for styling
		context = new StyleContext();
		documentAll = new DefaultStyledDocument(context);
		documentGame = new DefaultStyledDocument(context);
		documentRent = new DefaultStyledDocument(context);
		documentTax = new DefaultStyledDocument(context);
		documentSite = new DefaultStyledDocument(context);
		documentEvent = new DefaultStyledDocument(context);
				
		style = context.getStyle(StyleContext.DEFAULT_STYLE);
		
		// Set up the TabbedPane by placing TextPane inside ScrollPane and then adding as a tab
		allTextpane = new JTextPane(documentAll);
		allTextpane.setEditable(false);
		allTextpane.setBackground(Color.BLACK);
		JPanel allTextpanePanel = new JPanel();
		allTextpanePanel.setBackground(Color.BLACK);
		allTextpanePanel.add(allTextpane, BorderLayout.WEST);
		
		gameTextpane = new JTextPane(documentGame);
		gameTextpane.setEditable(false);
		gameTextpane.setBackground(Color.BLACK);
		JPanel gameTextpanePanel = new JPanel();
		gameTextpanePanel.setBackground(Color.BLACK);
		gameTextpanePanel.add(gameTextpane, BorderLayout.WEST);
						
		rentTextpane =  new JTextPane(documentRent);
		rentTextpane.setEditable(false);
		rentTextpane.setBackground(Color.BLACK);
		JPanel rentTextpanePanel = new JPanel();
		rentTextpanePanel.setBackground(Color.BLACK);
		rentTextpanePanel.add(rentTextpane, BorderLayout.WEST);
				
		taxTextpane = new JTextPane(documentTax);
		taxTextpane.setEditable(false);
		taxTextpane.setBackground(Color.BLACK);
		JPanel taxTextpanePanel = new JPanel();
		taxTextpanePanel.setBackground(Color.BLACK);
		taxTextpanePanel.add(taxTextpane, BorderLayout.WEST);
				
		siteTextpane = new JTextPane(documentSite);
		siteTextpane.setEditable(false);
		siteTextpane.setBackground(Color.BLACK);
		JPanel siteTextpanePanel = new JPanel();
		siteTextpanePanel.setBackground(Color.BLACK);
		siteTextpanePanel.add(siteTextpane, BorderLayout.WEST);
		
		eventTextpane = new JTextPane(documentEvent);
		eventTextpane.setEditable(false);
		eventTextpane.setBackground(Color.BLACK);
		JPanel eventTextpanePanel = new JPanel();
		eventTextpanePanel.setBackground(Color.BLACK);
		eventTextpanePanel.add(eventTextpane, BorderLayout.WEST);
				
		allTextpaneScroll = new JScrollPane(allTextpanePanel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		gameTextpaneScroll = new JScrollPane(gameTextpanePanel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		rentTextpaneScroll = new JScrollPane(rentTextpanePanel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		taxTextpaneScroll = new JScrollPane(taxTextpanePanel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		siteTextpaneScroll = new JScrollPane(siteTextpanePanel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		eventTextpaneScroll = new JScrollPane(eventTextpanePanel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		logTabbedPane.addTab("All", allTextpaneScroll);
		logTabbedPane.addTab("Game", gameTextpaneScroll);
		logTabbedPane.addTab("Rent", rentTextpaneScroll);
		logTabbedPane.addTab("Tax", taxTextpaneScroll);
		logTabbedPane.addTab("Site", siteTextpaneScroll);
		logTabbedPane.addTab("Event", eventTextpaneScroll);
			
		StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
		StyleConstants.setFontSize(style, 11);
		StyleConstants.setBold(style, true);
		StyleConstants.setSpaceAbove(style, 4);
		StyleConstants.setSpaceBelow(style, 4);
		StyleConstants.setFontFamily(style, "monospace");

		setLocationRelativeTo(null);
		
		// TODO: Fix the stupid annoying GRRR URGH centering of JTextPane
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int screenWidth = gd.getDisplayMode().getWidth();
		int screenHeight = gd.getDisplayMode().getHeight();
		
		this.setPreferredSize(new Dimension((int)(Math.round((screenWidth / 9) * 2.5)), (int)(Math.round((screenHeight / 9) * 2.5))));
		this.setLocation((int)(Math.round((screenWidth / 9) * 5.5)), (screenHeight / 9) + 20);
		
		add(logTabbedPane);
		
		logTabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// Always display the bottom of the log when changing tab
			    switch(logTabbedPane.getSelectedIndex())
			    {
			    case 0 : vertical = allTextpaneScroll.getVerticalScrollBar();
				vertical.setValue(vertical.getMaximum());
				break;
				
			    case 1 : vertical = gameTextpaneScroll.getVerticalScrollBar();
				vertical.setValue(vertical.getMaximum());
				break;
				
			    case 2 : vertical = rentTextpaneScroll.getVerticalScrollBar();
				vertical.setValue(vertical.getMaximum());
				break;
				
			    case 3 : vertical = taxTextpaneScroll.getVerticalScrollBar();
				vertical.setValue(vertical.getMaximum());
				break;
				
			    case 4 : vertical = siteTextpaneScroll.getVerticalScrollBar();
				vertical.setValue(vertical.getMaximum());
				break;
				
			    case 5 : vertical = eventTextpaneScroll.getVerticalScrollBar();
				vertical.setValue(vertical.getMaximum());
				break;
			    }
			}
		});
	}

	/**
	 * Sends a message to the appropriate tabs on the log box
	 * @throws BadLocationException 
	 * @param logType the type of message to display
	 * @param message the message to display
	 */
	public void outputMessageToLogbox(logType logType, String message) {
		// Get the current time and format
		String timeStamp = new SimpleDateFormat("(HH:mm): ").format(Calendar
				.getInstance().getTime());
		
		// Write to the appropriate document depending on the logType, setting scrollbar to maximum
		try {
			switch (logType) {
			case GAME:
				StyleConstants.setForeground(style, Color.RED);
				documentGame.insertString(documentGame.getLength(), timeStamp
						+ message + "\n", style);
				documentAll.insertString(documentAll.getLength(), timeStamp + "["
						+ logType.toString() + "] ", style);
				vertical = gameTextpaneScroll.getVerticalScrollBar();
				vertical.setValue(vertical.getMaximum());
				break;

			case RENT:
				StyleConstants.setForeground(style, Color.CYAN);
				documentRent.insertString(documentRent.getLength(), timeStamp
						+ message + "\n", style);
				documentAll.insertString(documentAll.getLength(), timeStamp + "["
						+ logType.toString() + "] ", style);
				vertical = rentTextpaneScroll.getVerticalScrollBar();
				vertical.setValue(vertical.getMaximum());
				break;

			case TAX:
				StyleConstants.setForeground(style, new Color(0, 184, 0));
				documentTax.insertString(documentTax.getLength(), timeStamp
						+ message + "\n", style);
				documentAll.insertString(documentAll.getLength(), timeStamp + "["
						+ logType.toString() + "] ", style);
				vertical = taxTextpaneScroll.getVerticalScrollBar();
				vertical.setValue(vertical.getMaximum());
				break;

			case SITE:
				StyleConstants.setForeground(style, new Color(255, 153, 51));
				documentSite.insertString(documentSite.getLength(), timeStamp
						+ message + "\n", style);
				documentAll.insertString(documentAll.getLength(), timeStamp + "["
						+ logType.toString() + "] ", style);
				vertical = siteTextpaneScroll.getVerticalScrollBar();
				vertical.setValue(vertical.getMaximum());
				break;
				
			case EVENT:
				StyleConstants.setForeground(style, new Color(143, 0, 255));
				documentEvent.insertString(documentEvent.getLength(), timeStamp
						+ message + "\n", style);
				documentAll.insertString(documentAll.getLength(), timeStamp + "["
						+ logType.toString() + "] ", style);
				vertical = eventTextpaneScroll.getVerticalScrollBar();
				vertical.setValue(vertical.getMaximum());
				break;
				
			default:
				System.out.println("Error in log box, invalid log type specified!");
				break;
			}
		
		documentAll.insertString(documentAll.getLength(), message + "\n", style);
		vertical = allTextpaneScroll.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());
		
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
}
