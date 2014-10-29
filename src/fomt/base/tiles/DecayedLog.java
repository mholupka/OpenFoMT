package fomt.base.tiles;

import fomt.base.world.World;

public class DecayedLog extends TileType implements ITilePutDown {

	// --- Instance Fields ---
	
	@Override
	public void onPutDown(World world, int row, int col) {
		
	}

	@Override
	public int getSpriteID() {
		return SPRITE_ID;
	}
	
	// --- Static Fields ---
	
	public static final int SPRITE_ID = 12;

}
