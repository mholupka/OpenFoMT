package fomt.base.crops;

import java.util.ArrayList;

import fomt.base.tile.types.Crop;

public class CropTable {

	// --- Constructors ---
	
	public CropTable()
	{
		this.crops = new ArrayList<>();
	}
	
	// --- Instance Methods ---
	
	public void addCrop(Crop crop)
	{
		crops.add(crop);
	}
	
	public Crop getCrop(int id)
	{
		return crops.get(id);
	}
	
	public int size()
	{
		return crops.size();
	}
	
	// --- Instance Fields ---
	
	protected ArrayList<Crop> crops;

}
