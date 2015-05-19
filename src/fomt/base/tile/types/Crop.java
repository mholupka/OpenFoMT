package fomt.base.tile.types;

import fomt.base.mob.Mob;
import fomt.base.tile.ITileDayUpdate;
import fomt.base.tile.TileInfo;
import fomt.base.tile.TileType;
import fomt.base.world.World;

public abstract class Crop extends TileType implements ITileDayUpdate {

	// --- Instance Methods ---
	
	public abstract int getSpriteID();
	public abstract int getNumCycles();
	public abstract int getCropTypeID();
	public abstract int getDaysToGrow(int cycleID);
	public abstract int getSpriteForCycle(int cycleID);
	
	@Override
	public void onPutDown(World world, int row, int col) {
	
		long data = world.getTileData(row, col);
		
		data = TileInfo.setFGSpriteID(data, getSpriteForCycle(0));
		//data = TileInfo.setDensity(data, true);
		
		int metaData = TileInfo.getMetaData(data);
		metaData = (metaData & ~0x1) | 0;
		metaData = (metaData & ~0x1E) | (0 << 1);
		metaData = (metaData & ~0xE0) | (0 << 5);
		metaData = (metaData & ~0x3FF00) | (getCropTypeID() << 8);
		world.setTileData(row, col, TileInfo.setMetaData(data, metaData));
			
		world.addTileToUpdate(row, col, this);
		
	}
	
	@Override
	public void onInteract(World world, Mob mob, int row, int col) {
		
		long data = world.getTileData(row, col);
	
		int metadata = TileInfo.getMetaData(data);
		int currCycle = (metadata &0xE0) >> 5;
		
		// if crop has finished its cycles
		if (currCycle == getNumCycles()) {
			data = TileInfo.setFGSpriteID(data, 0);
			world.setTileData(row, col, data);
		}
		
	}
	
	@Override
	public void onDayUpdate(World world, int row, int col) {
		
		long data = world.getTileData(row, col);
		int metadata = TileInfo.getMetaData(data);
		
		int isWatered = metadata &0x1;
		//int cropType = (metadata &0x3FF00) >> 8;
		int currCycle = (metadata &0xE0) >> 5;
		
		//if (currCycle == getNumCycles()) {
		//	world.removeTileFromUpdate(row, col);
		//} else {
			if (isWatered == 1) {
				
				int daysInCycle = (metadata &0x1E) >> 1;
				++daysInCycle;
				
				if (daysInCycle == getDaysToGrow(currCycle)) {
					daysInCycle = 0;
					++currCycle;
					data = TileInfo.setFGSpriteID(data, getSpriteForCycle(currCycle));
				}
				
				isWatered = 0;
				data = TileInfo.setBGSpriteID(data, 13);
				metadata = (metadata & ~0x1) | isWatered;
				metadata = (metadata & ~0xE0) | (currCycle << 5);
				metadata = (metadata & ~0x1E) | (daysInCycle << 1);
				data = TileInfo.setMetaData(data, metadata);
				world.setTileData(row, col, data);
				
			}
		//}

	}
	
	// --- Static Methods ---

	public static void setWatered(TileInfo tile, boolean flag) {
		int metadata = TileInfo.getMetaData(tile.data);
		metadata = (metadata & ~0x1) | (flag ? 1 : 0);
		tile.data = TileInfo.setMetaData(tile.data, metadata);
	}
	
}
