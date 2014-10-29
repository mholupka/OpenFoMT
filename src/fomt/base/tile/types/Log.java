package fomt.base.tile.types;

import fomt.base.tile.ITileDayUpdate;
import fomt.base.tile.TileInfo;
import fomt.base.tile.TileType;
import fomt.base.world.World;

public class Log extends TileType implements ITileDayUpdate {
	
	// --- Instance Methods ---
	
	@Override
	public int getSpriteID() { 
		return SPRITE_ID;
	}
	
	@Override
	public void onPutDown(World world, int row, int col) {
		
		long data = world.getTileData(row, col);
		data = TileInfo.setFGSpriteID(data, getSpriteID());
		
		world.setTileData(row, col, data);
		
		// set metadata (3 days to decay)
		world.setTileData(row, col, TileInfo.setMetaData(data, 3));
		
		// add this tile to the update list
		world.addTileToUpdate(row, col);
		
	}

	@Override
	public void onDayUpdate(World world, int row, int col) {
		
		long data = world.getTileData(row, col);
		
		int metadata = TileInfo.getMetaData(data);
		
		int timeToDecay = getTimeToDecay(metadata);
	
		--timeToDecay;
		
		if (timeToDecay == 0) {
			data = TileInfo.setFGSpriteID(data, DecayedLog.SPRITE_ID);
			data = TileInfo.setMetaData(data, 0);
			world.setTileData(row, col, data);
		} else {
			metadata = setTimeToDecay(metadata, timeToDecay);
			data = TileInfo.setMetaData(data, metadata);
			world.setTileData(row, col, data);
		}
		
	}

	// --- Static Methods ---
	
	public static int getTimeToDecay(int metadata) {
		return metadata & 0x7;
	}
	public static int setTimeToDecay(int metadata, int timeToDecay) {
		return (metadata & ~0x7) | (timeToDecay & 0x7);
	}
	
	// --- Static Fields ---
	
	public static final int SPRITE_ID = 11;
	
}
