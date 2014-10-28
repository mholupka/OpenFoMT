package fomt.base.world;

import java.util.ArrayList;
import java.util.function.Consumer;

import fomt.base.tile.TileInfo;

public class World {

	// --- Constructors ---
	
	public World(String name, int width, int height) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.tileData = new long[width * height];
		this.updateList = new ArrayList<int[]>();
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
	
	public void addTileToUpdate(int x, int y)
	{
		int[] a = new int[2];
		a[0] = x;
		a[1] = y;
		updateList.add(a);
	}
	
	public void removeTileFromUpdate(int x, int y)
	{
		int[] a = new int[2];
		a[0] = x;
		a[1] = y;
		updateList.remove(a);
	}
	
	public void dayUpdate()
	{
		for(int i = 0; i < updateList.size(); i++)
		{
			int row = updateList.get(i)[0];
			int col = updateList.get(i)[1];
			long data = getTileData(row, col);
			if(TileInfo.getFGSpriteID(data) == 11)
			{
				long metaData = TileInfo.getMetaData(data);
				long timeToDecay = metaData &0x7;
				--timeToDecay;
				if (timeToDecay == 0)
				{
					data = TileInfo.setFGSpriteID(data, 12);
					data = TileInfo.setMetaData(data, 0);
					setTileData(row, col, data);
				}
				else
				{
					metaData = (metaData &~0x7)|timeToDecay;
					data = TileInfo.setMetaData(data, (int)metaData);
					setTileData(row, col, data);
				}
			}
			if(TileInfo.getFGSpriteID(data) == 14)
			{
				
			}
		}
	}
	
	// --- Get and Set Methods ---
	
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
	
	ArrayList<int[]> updateList;
	
}
