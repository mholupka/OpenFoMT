package fomt.base.tile;

import fomt.base.tile.types.*;

public class TileTable {

	public static TileType getTileType(int id) {
		if (id < 0 || id >= table.length)
			return null;
		return table[id];
	}
	
	public static final TileType[] table = {
		null,					// 0
		null,					// 1
		null,					// 2
		null,					// 3
		null,					// 4			
		null,					// 5
		null,					// 6
		null,					// 7
		null,					// 8
		null,					// 9
		null,					// 10
		new Log(), 				// 11
		null,					// 12
		null,					// 13
		null,					// 14
		null,					// 15
		null,					// 16
		new PinkCatGrass(),		// 17
		new BlueCatGrass(),		// 18
		new YellowCatGrass(),	// 19
		null,					// 20
		null,					// 21
		null,					// 22
		null,					// 23
		null,					// 24
		null,					// 25
		null,					// 26
		new MultiCatGrass(),	// 27
		
	};

}
