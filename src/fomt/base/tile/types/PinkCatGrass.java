package fomt.base.tile.types;

public class PinkCatGrass extends Crop {

	// --- Instance Methods ---
	
	@Override
	public int getSpriteID() {
		return SPRITE_ID;
	}

	@Override
	public int getNumCycles() {
		return cycleLengths.length;
	}

	@Override
	public int getCropTypeID() {
		return CROP_ID;
	}

	@Override
	public int getDaysToGrow(int cycleID) {
		return cycleLengths[cycleID];
	}

	@Override
	public int getSpriteForCycle(int cycleID) {
		return cycleSpriteIDs[cycleID];
	}

	// --- Static Fields ---

	private static final int[] cycleLengths = {1, 2, 3, 4};
	protected static final int[] cycleSpriteIDs = {14, 15, 16, 17};
	
	public static final int SPRITE_ID = 14;
	public static final int CROP_ID = 0;
	
}
