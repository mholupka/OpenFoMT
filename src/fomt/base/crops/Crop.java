package fomt.base.crops;

public class Crop {
	
	// --- Constructors ---
	public Crop(int[] daysToGrow, int numCycles, int[] cycleIds, int id)
	{
		this.daysToGrow = daysToGrow;
		this.numCycles = numCycles;
		this.cycleIds = cycleIds;
		this.id = id;
	}
	
	// --- Instance Methods --- 
	
	// --- Get and Set Methods ---
	public int[] getDaysToGrow()
	{
		return this.daysToGrow;
	}
	
	public int getNumCycles()
	{
		return this.numCycles;
	}
	
	public int[] getCycleIds()
	{
		return this.cycleIds;
	}
	
	public int getID()
	{
		return this.id;
	}
	
	// --- Instance Fields ---
	
	protected int[] daysToGrow;
	protected int numCycles;
	protected int[] cycleIds;
	protected int id;
	
}
