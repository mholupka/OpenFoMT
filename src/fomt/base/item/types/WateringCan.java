package fomt.base.item.types;

import fomt.base.item.Item;
import fomt.base.mob.Mob;
import fomt.base.tile.TileInfo;
import fomt.base.tile.TileTable;
import fomt.base.tile.TileType;
import fomt.base.tile.types.Crop;
import fomt.base.world.World;

public class WateringCan implements Item {
	
	// --- Constructor ---
	
	public WateringCan(int capacity, int range) {
		this.water = 5;
		this.range = range;
	}
	
	// --- Instance Methods ---
	
	@Override
	public void onUse(World w, Mob m) {
	
		if (water == 0)
			return;
		
		TileInfo tile = new TileInfo(0, 0, 0L);
		
		final int r0 = m.row - range, r1 = m.row + range;
		final int c0 = m.col - range, c1 = m.col + range;
		
		int fgSpriteID;
		TileType tileType;
		
		for (int i = r0; i <= r1; ++i) {
			for (int j = c0; j <= c1; ++j) {
				
				if (!w.getTileData(i, j, tile))
					continue;
				
				fgSpriteID = tile.getFGSpriteID();
				tileType = TileTable.getTileType(fgSpriteID);
				
				// if not a crop, skip (todo clean this shit up)
				if (fgSpriteID != 14 && fgSpriteID != 15 && fgSpriteID != 16 && !(tileType instanceof Crop))
					continue;
				
				Crop.setWatered(tile, true);
				
				w.setTileData(tile);
				
			}
		}
		
		--water;
		
	}

	// --- Instance Fields ---
	
	protected int water;
	protected int range;
	
}
