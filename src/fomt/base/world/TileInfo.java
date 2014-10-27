package fomt.base.world;

public class TileInfo {

	// --- Static Methods ---
	
	public static final int getFGSpriteID(long tileData) {
		return (int)tileData & FG_SPRITE_MASK_32;
	}
	
	public static final int getBGSpriteID(long tileData) {
		return ((int)tileData & BG_SPRITE_MASK_32) >> 16;
	}
	
	public static final boolean isDense(long tileData) {
		return ((int)(tileData >> 32L) & DENSITY_MASK_32) != 0;
	}
	
	public static final int getMetaData(long tileData) {
		return (int)(tileData >> 32L) & METADATA_MASK_32;
	}
	
	public static final long setFGSpriteID(long tileData, int id) {
		return (tileData & ~FG_SPRITE_MASK) | (id & 0xFFFF);
	}
	
	public static final long setBGSpriteID(long tileData, int id) {
		return (tileData & ~BG_SPRITE_MASK) | ((id & 0xFFFF) << 16);
	}
	
	public static final long setDensity(long tileData, boolean flag) {
		return (tileData & ~DENSITY_MASK) | (flag ? 0xE000000000000000L : 0L);
	}
	
	public static final long setMetaData(long tileData, int metadata) {
		return (tileData & ~METADATA_MASK) | ((long)(metadata & METADATA_MASK_32) << 32L);
	}
	
	// --- Static Fields ---
	
	public static final int FG_SPRITE_MASK_32 = 0xFFFF;
	public static final long FG_SPRITE_MASK = 0xFFFFL;
	
	public static final int BG_SPRITE_MASK_32 = 0xFFFF0000;
	public static final long BG_SPRITE_MASK = 0xFFFF0000L;
	
	protected static final int DENSITY_MASK_32 = 0xE0000000;
	public static final long DENSITY_MASK = 0xE000000000000000L;
	
	protected static final int METADATA_MASK_32 = 0x7FFFFFFF;
	public static final long METADATA_MASK = 0x7FFFFFFF00000000L;
	
}
