package plutocracy;

import javax.swing.JOptionPane;

/**
 * Main Class
 */
public class Main 
{
	/**
	 * Sets up then runs the game.
	 * @param args are not used.
	 */
	public static void main(String[] args) 
	{	
		// Create, setup, and run the game.
		Plutocracy game = new Plutocracy();
		game.setup();
		
		// Ask the user if they want to start the game.
		if(JOptionPane.showConfirmDialog(null, "Would you like to start the game?", "Start Game", JOptionPane.YES_NO_OPTION) == 0)
			game.run();
		else
			System.exit(0);
				
	}

}
