package fomt.base.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import fomt.base.world.TileInfo;

public class TestTileInfo {

	@Test
	public void testTileInfo() {
		
		long tile = 0L;
		
		tile = TileInfo.setFGSpriteID(tile, 12);
		tile = TileInfo.setDensity(tile, true);
		tile = TileInfo.setBGSpriteID(tile, 24);		
		tile = TileInfo.setMetaData(tile, 80085);
				
		assertEquals(true, TileInfo.isDense(tile));
		assertEquals(12, TileInfo.getFGSpriteID(tile));
		assertEquals(24, TileInfo.getBGSpriteID(tile));
		assertEquals(80085, TileInfo.getMetaData(tile));
		
	}
	
}
