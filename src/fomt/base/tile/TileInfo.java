package fomt.base.tile;

public class TileInfo {
	
	// --- Constructors ---
	
	public TileInfo(int row, int col, long data) {
		this.row = row;
		this.col = col;
		this.data = data;
	}
	
	// --- Instance Methods ---
	
	public int getFGSpriteID() {
		return (int)data & FG_SPRITE_MASK_32;
	}
	
	public int getBGSpriteID() {
		return ((int)data & BG_SPRITE_MASK_32) >> 16;
	}
	
	public boolean isDense() {
		return ((int)(data >> 32L) & DENSITY_MASK_32) != 0;
	}
	
	public int getMetaData() {
		return (int)(data >> 32L) & METADATA_MASK_32;
	}
	
	public void setFGSpriteID(int id) {
		data = (data & ~FG_SPRITE_MASK) | (id & 0xFFFF);
	}
	
	public void setBGSpriteID(int id) {
		data = (data & ~BG_SPRITE_MASK) | ((id & 0xFFFF) << 16);
	}
	
	public void setDensity(boolean flag) {
		data = (data & ~DENSITY_MASK) | (flag ? 0x8000000000000000L : 0L);
	}
	
	public void setMetaData(int metadata) {
		data = (data & ~METADATA_MASK) | ((long)(metadata & METADATA_MASK_32) << 32L);
	}
	
	// --- Instance Fields ---
		
	public int row, col;
	public long data;

	// --- Static Methods ---
	
	public static int getFGSpriteID(long tileData) {
		return (int)tileData & FG_SPRITE_MASK_32;
	}
	
	public static int getBGSpriteID(long tileData) {
		return ((int)tileData & BG_SPRITE_MASK_32) >> 16;
	}
	
	public static boolean isDense(long tileData) {
		return ((int)(tileData >> 32L) & DENSITY_MASK_32) != 0;
	}
	
	public static int getMetaData(long tileData) {
		return (int)(tileData >> 32L) & METADATA_MASK_32;
	}
	
	public static long setFGSpriteID(long tileData, int id) {
		return (tileData & ~FG_SPRITE_MASK) | (id & 0xFFFF);
	}
	
	public static long setBGSpriteID(long tileData, int id) {
		return (tileData & ~BG_SPRITE_MASK) | ((id & 0xFFFF) << 16);
	}
	
	public static long setDensity(long tileData, boolean flag) {
		return (tileData & ~DENSITY_MASK) | (flag ? 0x8000000000000000L : 0L);
	}
	
	public static long setMetaData(long tileData, int metadata) {
		return (tileData & ~METADATA_MASK) | ((long)(metadata & METADATA_MASK_32) << 32L);
	}
	
	public static void debugPrint(long tileData) {
	
		System.out.println("TileData(" + tileData + ") {");
		
		System.out.println("\tBG Sprite ID: " + getBGSpriteID(tileData));
		System.out.println("\tFG Sprite ID: " + getFGSpriteID(tileData));
		System.out.println("\tDensity: " + isDense(tileData));
		System.out.println("\tMetaData: " + getMetaData(tileData));
		
		System.out.println("}");
		
	}
	
	// --- Static Fields ---
	
	public static final int FG_SPRITE_MASK_32 = 0xFFFF;
	public static final long FG_SPRITE_MASK = 0xFFFFL;
	
	public static final int BG_SPRITE_MASK_32 = 0xFFFF0000;
	public static final long BG_SPRITE_MASK = 0xFFFF0000L;
	
	protected static final int DENSITY_MASK_32 = 0x80000000;
	public static final long DENSITY_MASK = 0x8000000000000000L;
	
	protected static final int METADATA_MASK_32 = 0x7FFFFFFF;
	public static final long METADATA_MASK = 0x7FFFFFFF00000000L;
	
}
