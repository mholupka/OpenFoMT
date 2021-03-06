package fomt.base.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import fomt.base.tile.ITileDayUpdate;
import fomt.base.tile.TileInfo;

public class World implements Serializable {
	
	private static final long serialVersionUID = -7658444781209201667L;
	
	// --- Constructors ---
	
	public World(String name, int width, int height) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.tileData = new long[width * height];
		this.fgDayUpdateList = new HashMap<>();
		this.bgDayUpdateList = new HashMap<>();
	}
	
	// --- Instance Methods ---
	
	public void setID(int id) { this.id = id; }
	public int getID() { return id; }
	
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
	
	public void addTileToUpdate(int x, int y, ITileDayUpdate fn)
	{	
		long key = (long)x | ((long)y << 32L);
		fgDayUpdateList.put(key, fn);
	}
	
	public void removeTileFromUpdate(int x, int y)
	{
		long key = (long)x | ((long)y << 32L);
		removeList.add(key);
	}
	
	public void onDayUpdate()
	{
		long key;
		int row, col;
		
		removeFromList(bgDayUpdateList);
		for (Entry<Long, ITileDayUpdate> e : bgDayUpdateList.entrySet()) {
			key = e.getKey();
			row = (int)key;
			col = (int)(key >> 32);
			e.getValue().onDayUpdate(this, row, col);
		}
		removeFromList(bgDayUpdateList); 
		
		removeFromList(fgDayUpdateList);
		for (Entry<Long, ITileDayUpdate> e : fgDayUpdateList.entrySet()) {
			key = e.getKey();
			row = (int)key;
			col = (int)(key >> 32);
			e.getValue().onDayUpdate(this, row, col);
		}
		removeFromList(fgDayUpdateList);
	}
	
	@SuppressWarnings("rawtypes")
	public void removeFromList(Map hm)
	{
		for(long l: removeList)
		{
			hm.remove(l);
		}
		removeList.clear();
	}
	
	// --- Get and Set Methods ---
	
	public final long[] getTileData() { 
		return tileData;
	}
	
	public final boolean isValidTileCoord(int row, int col) {
		return row >= 0 && col >= 0 && row < height && col < width;
	}
	public final long getTileData(int row, int col) { 
		if (row < 0 || row >= height || col < 0 || col >= width)
			throw new IllegalArgumentException();
		return this.tileData[row * width + col]; 	
	}
	public final boolean getTileData(int row, int col, TileInfo t) {
		
		if (row < 0 || row >= height || col < 0 || col >= width)
			return false;
		
		t.row = row;
		t.col = col;
		t.data = this.tileData[row * width + col];
		
		return true;
	
	}
	
	public final void setTileData(int row, int col, long tileData) {
		if (row < 0 || row >= height || col < 0 || col >= width)
			throw new IllegalArgumentException();
		this.tileData[row * width + col] = tileData;
	}
	public final void setTileData(TileInfo tile) {
		if (tile.row < 0 || tile.row >= height || tile.col < 0 || tile.col >= width)
			throw new IllegalArgumentException();
		this.tileData[tile.row * width + tile.col] = tile.data;
	}
	
	public final String getName() { return name; }
	public final int getWidth() { return width; }
	public final int getHeight() { return height; }
	
	// --- Instance Fields ---
	
	protected int id;
	protected String name;
	protected long[] tileData;
	protected int width, height;
	
	Map<Long, ITileDayUpdate> bgDayUpdateList;
	Map<Long, ITileDayUpdate> fgDayUpdateList;
	ArrayList<Long> removeList = new ArrayList<Long>();
	
}
