package fomt.base.world;

import java.util.function.Consumer;

//import fomt.base.mob.Mob;

public class World {

	// --- Constructors ---
	
	public World(String name, int width, int height) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.tileData = new long[width * height];
	}
	// --- Instance Methods ---
	
	public void forEachTile(Consumer<TileInfo> lambda) {
		
		TileInfo info = new TileInfo(0, 0, 0);
		
		int rowOff = 0;
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				info.row = i; info.col = j; info.data = tileData[rowOff + j];
				lambda.accept(info);
			}
			rowOff += width;
		}
		
	}
	
	public final long[] getTileData() { 
		return tileData;
	}
	
	public final long getTileData(int row, int col) { 
		if (row < 0 || row >= height || col < 0 || col >= width)
			throw new IllegalArgumentException();
		return this.tileData[row * width + col]; 	
	}
	
	public final void setTileData(int row, int col, long tileData) {
		if (row < 0 || row >= height || col < 0 || col >= width)
			throw new IllegalArgumentException();
		this.tileData[row * width + col] = tileData;
	}
	
	public final String getName() { return name; }
	public final int getWidth() { return width; }
	public final int getHeight() { return height; }
	
	// --- Instance Fields ---
	
	protected String name;
	
	protected long[] tileData;
	
	protected int width, height;
	
}
