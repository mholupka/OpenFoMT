package fomt.base.tile;

import java.io.Serializable;

import fomt.base.mob.Mob;
import fomt.base.world.World;

public abstract class TileType implements Serializable {

	private static final long serialVersionUID = 1212534511554619179L;

	public abstract int getSpriteID();
	
	public void onPutDown(World world, int row, int col) {
		long data = world.getTileData(row, col);
		world.setTileData(row, col, TileInfo.setFGSpriteID(data, getSpriteID()));
		world.removeTileFromUpdate(row, col);
	}
	
	public void onInteract(World world, Mob mob, int row, int col) {
		// do nothing...
	}
	
}
