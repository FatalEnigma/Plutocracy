package plutocracy;
/**
 * Cell Class - Every square is a cell
 */
public class Cell 
{
	// Protected Fields.
	protected CellType cellType;
	protected String cellName;
	
	// Getter and Setter Functions.
	/**
	 * @return A CellType stating whether the cell is a Site, Tax Charge, Random Event, or the Central Bank.
	 */
	public CellType getType(){return cellType;}
	/**
	 * @return The name of the cell.
	 */
	public String getName(){return cellName;}
	/**
	 * @param type sets whether the cell is a Site, Tax Charge, Random Event, or the Central Bank.
	 */
	public void setType(CellType type){this.cellType = type;} 
	/**
	 * @param name sets the name of the cell, which is displayed on the game-board.
	 */
	public void setName(String name){this.cellName = name;}
	
	// Constructor.
	/**
	 * Constructs a cell (square) of the game-board.
	 * @param type States whether the cell is a Site, Tax Charge, Random Event, or the Central Bank.
	 * @param name The name of the cell, which is displayed on the game-board.
	 */
	public Cell(CellType type, String name)
	{
		this.cellType = type;
		this.cellName = name;
	}
}
