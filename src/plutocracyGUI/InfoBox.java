/**
 * 
 */
package plutocracyGUI;


/**
 * This is a simple class used to make sure there is only 1 PlayerInfoBox or SiteInfoBox on Screen.
 */
public abstract class InfoBox extends javax.swing.JFrame 
{
	private static final long serialVersionUID = 1054469016661271352L;


	/**
	 * @param title The title of the frame.
	 */
	public InfoBox(String title)
	{
		super(title);
	}

}
