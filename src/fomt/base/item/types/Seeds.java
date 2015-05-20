package fomt.base.item.types;

import java.util.Random;

import fomt.base.item.Item;
import fomt.base.mob.Direction;
import fomt.base.mob.Mob;
import fomt.base.tile.TileInfo;
import fomt.base.tile.TileTable;
import fomt.base.tile.TileType;
import fomt.base.tile.types.Crop;
import fomt.base.world.World;

public class Seeds implements Item {
	
	// --- Constructor ---
	
	public Seeds(int type) {
		this.range = 3;
	}
	
	// --- Instance Methods ---
	
	@Override
	public void onUse(World w, Mob m) {
		
		TileInfo tile = new TileInfo(0, 0, 0L);
		
		final int r0 = m.row - range, r1 = m.row + range;
		final int c0 = m.col - range, c1 = m.col + range;
		
		int fgSpriteID;
		int bgSpriteID;
		TileType tileType;
		
		for (int i = r0; i <= r1; ++i) {
			for (int j = c0; j <= c1; ++j) {
				
				if (!w.getTileData(i, j, tile))
					continue;
				
				fgSpriteID = tile.getFGSpriteID();

				bgSpriteID = tile.getBGSpriteID();
				tileType = TileTable.getTileType(fgSpriteID);
				TileType tTy;
				long data;
					
				// if a tilled earth tile
				if (bgSpriteID != 13)
					continue;
				
				if (fgSpriteID == 0) {
					//todo properly
					if(this.type == 0) {
						Random r = new Random();
						int a = r.nextInt(1000);
						if(a <= 500){
							data = TileInfo.setFGSpriteID(w.getTileData(i, j), 17);
							tTy = TileTable.getTileType(TileInfo.getFGSpriteID(data));
							tTy.onPutDown(w, i, j);
						}
						else if(a <= 800){
							data = TileInfo.setFGSpriteID(w.getTileData(i, j), 18);
							tTy = TileTable.getTileType(TileInfo.getFGSpriteID(data));
							tTy.onPutDown(w, i, j);
						}
						else if(a <= 998){
							data = TileInfo.setFGSpriteID(w.getTileData(i, j), 19);
							tTy = TileTable.getTileType(TileInfo.getFGSpriteID(data));
							tTy.onPutDown(w, i, j);
						}
						else{
							data = TileInfo.setFGSpriteID(w.getTileData(i, j), 27);
							tTy = TileTable.getTileType(TileInfo.getFGSpriteID(data));
							tTy.onPutDown(w, i, j);
						}
					}
				}
			}
		}
	}
	
	public String getType()
	{
		return "Seeds " + this.type;
	}

	// --- Instance Fields ---
	
	protected int range;
	protected int type;
	
}
