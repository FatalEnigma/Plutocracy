package plutocracyGUI;

import java.awt.*;

import plutocracy.*;

import javax.swing.*;

/**
 * The player info box that appears when you click on a player in the player info area
 */
public class PlayerInfoBox extends InfoBox
{

	private static final long serialVersionUID = 5823470162047582290L;
	private JTabbedPane playerInfoTabbedPane;
	private JPanel mainPanel1, mainPanel2, imagePanel, playerInfoTextPanel, siteListPanel;
	private JLabel playerName, playerBank, playerCash, playerSymbol, siteListTitleLabel;
	private JScrollPane siteListScrollPane;
	private ImageIcon playerIcon;
	private int overallRent, overallOffices, overallConferenceCentres;
	
	/**
	 * @param player - the player you are getting info on
	 */
	public PlayerInfoBox(Player player)
	{
		super(player.getName());
		
		// Set up the logging box
		playerInfoTabbedPane = new JTabbedPane();
		
		// Set Up Tab 1
		mainPanel1 = new JPanel(new GridLayout(0,2));
		imagePanel = new JPanel(new GridLayout(1,1));
		playerIcon = new ImageIcon(GameBoard.getImage(player.getBankBrand().returnImage()));
		imagePanel.add(new JLabel(playerIcon));
		
		playerInfoTextPanel = new JPanel(new GridLayout(4,0,2,2));
		
		playerName = new JLabel("Name: " + player.getName());
		playerBank = new JLabel("Bank: " + player.getBankBrand());
		playerCash = new JLabel("Cash: £" + player.getCash());
		playerSymbol = new JLabel("Symbol: " + player.getSymbol());
		
		playerName.setFont(new Font("monospace", Font.BOLD, 18));
		playerBank.setFont(new Font("monospace", Font.BOLD, 18));
		playerCash.setFont(new Font("monospace", Font.BOLD, 18));
		playerSymbol.setFont(new Font("monospace", Font.BOLD, 18));
		
		// Add player details
		playerInfoTextPanel.add(playerName);
		playerInfoTextPanel.add(playerBank);
		playerInfoTextPanel.add(playerCash);
		playerInfoTextPanel.add(playerSymbol);
		
		// Add picture and details to main panel 1
		mainPanel1.add(imagePanel);
		mainPanel1.add(playerInfoTextPanel);
		
		// Add tab 1 to TabbedPane
		playerInfoTabbedPane.addTab("General Info", mainPanel1);
		
		// Set Up Tab 2
		mainPanel2 = new JPanel(new GridLayout(0,2));
		siteListPanel = new JPanel(new GridLayout(player.getOwnerSites().size() + 1, 0, 0, 20));
		
		// Initialise variables
		overallRent = 0;
		overallOffices = 0;
		overallConferenceCentres = 0;
		
		siteListTitleLabel = new JLabel();
		siteListTitleLabel.setFont(new Font("monospace", Font.BOLD, 25));
		
		// If they don't own any sites we can skip the loops and set the text underneath
		if(player.getOwnerSites().size() != 0){
			siteListTitleLabel.setText("             Site List");
			siteListPanel.add(siteListTitleLabel);
			
			// Get details on the sites the player owns creating a dynamically
			// sizable panel in the process
			for (int x = 0; x < player.getOwnerSites().size(); x++) {
				JPanel individualPropertyInfo = new JPanel(new GridLayout(4, 0));
				individualPropertyInfo.setBorder(BorderFactory
						.createLineBorder(Color.BLACK, 2));

				JLabel siteNameLabel = new JLabel("Site name: "
						+ player.getOwnerSites().get(x).getName());
				JLabel siteRentLabel = new JLabel("Rent Income: £"
						+ player.getOwnerSites().get(x).getRent());
				JLabel siteColourLabel = new JLabel("Site Colour: "
						+ player.getOwnerSites().get(x).getColour().toString()
								.toLowerCase());

				individualPropertyInfo.add(siteNameLabel);
				individualPropertyInfo.add(siteRentLabel);
				individualPropertyInfo.add(siteColourLabel);

				// Get details on the properties on each of the sites
				int siteOffices = 0, siteConferenceCentres = 0;
				for (int y = 0; y < player.getOwnerSites().get(x)
						.getProperties().size(); y++) {
					if (player.getOwnerSites().get(x).getProperties().get(y)
							.getPropertyType() == PropertyType.OFFICE)
						siteOffices++;
					else
						siteConferenceCentres++;
				}

				overallRent += player.getOwnerSites().get(x).getRent();
				overallOffices += siteOffices;
				overallConferenceCentres += siteConferenceCentres;

				JLabel sitePropertyLabel = new JLabel("Properties: "
						+ siteOffices + " office(s) & " + siteConferenceCentres
						+ " conference center(s)");
				individualPropertyInfo.add(sitePropertyLabel);
				siteListPanel.add(individualPropertyInfo);
			}
		
		}
		
		else
		{
		siteListTitleLabel.setText("         No Sites Owned");
		siteListPanel.add(siteListTitleLabel);
		}
		
		siteListScrollPane = new JScrollPane(siteListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		siteListScrollPane.setPreferredSize(new Dimension(300, 80));
		
		
		JLabel statisticTitle = new JLabel("         Site Statistics");
		statisticTitle.setFont(new Font("monospace", Font.BOLD, 25));
		
		JPanel propertyStats = new JPanel(new GridLayout(6,0));
		JLabel totalProperties = new JLabel("Total Sites: " +player.getOwnerSites().size());
		JLabel totalOfficesLabel = new JLabel("Total Offices: " +overallOffices);
		JLabel totalConferenceCentresLabel = new JLabel("Total Conference Centres: " +overallConferenceCentres);
		JLabel totalRentLabel = new JLabel("Total Rent Value From All Sites: £" +overallRent);
		JLabel averagePropertiesPerSite = new JLabel((player.getOwnerSites().size() != 0) ? "Average Properties Per Site: " +String.format("%3.2f",(double)((double)(overallOffices + overallConferenceCentres) / (double)player.getOwnerSites().size())) : "Average Properties Per Site:  0");
		
		// Add stat details
		propertyStats.add(statisticTitle);
		propertyStats.add(totalProperties);
		propertyStats.add(totalOfficesLabel);
		propertyStats.add(totalConferenceCentresLabel);
		propertyStats.add(totalRentLabel);
		propertyStats.add(averagePropertiesPerSite);
		
		// Add stats and scroll panel of properties to main panel 2
		mainPanel2.add(propertyStats);
		mainPanel2.add(siteListScrollPane);
		
		// Add tab 2 to TabbedPane
		playerInfoTabbedPane.addTab("Site/Property Information", mainPanel2);
		
		add(playerInfoTabbedPane);
		
		//Methods needed to display the frame
		imagePanel.revalidate();
		imagePanel.repaint();
		
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		
		// Set size and location
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int screenWidth = gd.getDisplayMode().getWidth();
		int screenHeight = gd.getDisplayMode().getHeight();

		this.setLocation(((screenWidth / 9) + 5), (screenHeight / 9) + 20);
	}
}
