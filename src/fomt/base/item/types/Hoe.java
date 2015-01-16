package fomt.base.item.types;

import fomt.base.item.Item;
import fomt.base.mob.Direction;
import fomt.base.mob.Mob;
import fomt.base.tile.TileInfo;
import fomt.base.tile.TileTable;
import fomt.base.tile.TileType;
import fomt.base.tile.types.Crop;
import fomt.base.world.World;

public class Hoe implements Item {
	
	// --- Constructor ---
	
	public Hoe(int range) {
		this.range = range;
	}
	
	// --- Instance Methods ---
	
	@Override
	public void onUse(World w, Mob m) {
		
		int trgtRow, trgtCol;
		
		switch(m.facing) {
		case Direction.UP:
			trgtRow = m.row - 1;
			trgtCol = m.col;
			break;
		case Direction.DOWN:
			trgtRow = m.row + 1;
			trgtCol = m.col;
			break;
		case Direction.LEFT:
			trgtRow = m.row;
			trgtCol = m.col - 1;
			break;
		case Direction.RIGHT:
			trgtRow = m.row;
			trgtCol = m.col + 1;
			break;
		default:
			return;
		}
		
		int trgtFGSpriteID = TileInfo.getFGSpriteID(w.getTileData(trgtRow, trgtCol));
		int trgtBGSpriteID = TileInfo.getBGSpriteID(w.getTileData(trgtRow, trgtCol));
		
		if (trgtBGSpriteID == 2 && trgtFGSpriteID == 0)
			w.setTileData(trgtRow, trgtCol, TileInfo.setBGSpriteID(w.getTileData(trgtRow, trgtCol), 13));
		
	}
	
	public String getType()
	{
		return "Hoe";
	}

	// --- Instance Fields ---
	
	protected int range;
	
}
