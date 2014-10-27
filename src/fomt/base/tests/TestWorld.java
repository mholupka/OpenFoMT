package fomt.base.tests;

import org.junit.Test;

import fomt.base.world.TileInfo;
import fomt.base.world.World;

public class TestWorld {

	@Test
	public void testWorld() {
		
		World world = new World("test_world", 10, 10);
		
		world.forEachTile(tile -> {
			
			tile.data = TileInfo.setDensity(tile.data, true);
			tile.data = TileInfo.setMetaData(tile.data, 12);
			tile.data = TileInfo.setBGSpriteID(tile.data, 1);
			tile.data = TileInfo.setFGSpriteID(tile.data, 2);
			
			world.setTileData(tile.row, tile.col, tile.data);
		
		});
		
		world.forEachTile(tile -> {
			if (tile.row == 5 && tile.col == 5) {
				TileInfo.debugPrint(tile.data);
			}	
		});
		
	}
	
}
