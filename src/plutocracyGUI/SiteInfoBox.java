package plutocracyGUI;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import plutocracy.PropertyType;
import plutocracy.Site;

/**
 * JFrame that is used to display the Site Info Box
 */
public class SiteInfoBox extends InfoBox 
{
	private static final long serialVersionUID = -968542736583759444L;
	//JPanel
	private JPanel pnlInfo;
	//JLabels used to display site properties 
	private JLabel siteName;
	private JLabel siteColour;
	private JLabel sitePrice;
	private JLabel siteOwner;
	private JLabel siteRent;
	private JLabel siteProperty;
	
	Site site;
	
	/**
	 * The site info box that shows when a site is clicked
	 * @param site - The current site clicked on or landed on
	 */
	public SiteInfoBox(Site site)
	{
		super(site.getName());
		
		this.site = site;
		
		pnlInfo = new JPanel(new GridLayout(6, 0, 2, 2));

		//Create labels to store all info
		siteName = new JLabel("Name: " + site.getName());
		siteColour = new JLabel("Colour: " + site.getColour().toString().toLowerCase());
		sitePrice = new JLabel("Buying Price: £" + site.getBasePrice());
		siteOwner = new JLabel("Owner: " + site.getOwner().getName());
		siteRent = new JLabel("Rent: £" + site.getRent());
		
		//Count number of offices and conference
		int offices = 0, conferences = 0;
		
		for(int i = 0; i < site.getProperties().size(); i++)
		{
			if(site.getProperties().get(i).getPropertyType() == PropertyType.OFFICE)
				offices++;
			else
				conferences++;
		}
		
		siteProperty = new JLabel("Properties: " + offices + " office(s) & " + conferences + " conference center(s)");
	
		// Add all to Panel
		pnlInfo.add(siteName);
		pnlInfo.add(siteColour);
		pnlInfo.add(sitePrice);
		pnlInfo.add(siteOwner);
		pnlInfo.add(siteRent);
		pnlInfo.add(siteProperty);
		
		add(pnlInfo);
		
		//Methods needed to display the frame
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		// Set size and location
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int screenWidth = gd.getDisplayMode().getWidth();
		int screenHeight = gd.getDisplayMode().getHeight();

		this.setPreferredSize(new Dimension(((screenWidth / 9) * 2), ((screenHeight / 9) * 2)));
		this.setLocation((int) ((screenWidth / 9) * 3.5) + 10, ((screenHeight / 9)) + 15);
		
		//TODO Write and update method for this that can be called after an upgrade
	}
	
	public void update()
	{	
		//Count number of offices and conference
		int offices = 0, conferences = 0;
		
		for(int i = 0; i < site.getProperties().size(); i++)
		{
			if(site.getProperties().get(i).getPropertyType() == PropertyType.OFFICE)
				offices++;
			else
				conferences++;
		}
		
		siteProperty.setText("Properties: " + offices + " office(s) & " + conferences + " conference center(s)");
		pnlInfo.revalidate();
		pnlInfo.repaint();
	}
}
