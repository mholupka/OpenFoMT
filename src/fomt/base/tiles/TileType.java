package fomt.base.tiles;

import fomt.base.tile.TileInfo;
import fomt.base.world.World;

public abstract class TileType {

	public abstract int getSpriteID();
	
	public void onPutDown(World world, TileInfo tile) {
		world.setTileData(tile.row, tile.col, TileInfo.setFGSpriteID(tile.data, getSpriteID()));
	}
	
}
