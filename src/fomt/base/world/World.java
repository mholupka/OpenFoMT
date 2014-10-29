package fomt.base.world;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import fomt.base.crops.Crop;
import fomt.base.crops.CropTable;
import fomt.base.tile.TileInfo;
import fomt.base.tiles.ITileDayUpdate;

public class World {

	// --- Constructors ---
	
	public World(String name, int width, int height) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.tileData = new long[width * height];
		this.updateList = new LinkedList<int[]>();
		this.fgDayUpdateList = new HashMap<>();
		this.bgDayUpdateList = new HashMap<>();
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
	
		// new version
		
		long key = (long)x | ((long)y << 32L);
		fgDayUpdateList.put(key, null);
	}
	
	public void removeTileFromUpdate(int x, int y)
	{
		int[] a = new int[2];
		a[0] = x;
		a[1] = y;
		updateList.remove(a);
		
		// new
		long key = (long)x | ((long)y << 32L);
		fgDayUpdateList.remove(key);
	}
	
	public void dayUpdate(CropTable crops)
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
			if(TileInfo.getFGSpriteID(data) == 14 || TileInfo.getFGSpriteID(data) == 15 || TileInfo.getFGSpriteID(data) == 16 || TileInfo.getFGSpriteID(data) == 17)
			{
				int metaData = TileInfo.getMetaData(data);
				int isWatered = metaData &0x1;
				int cropType = (metaData &0x3FF00) >> 8;
				Crop crop = crops.getCrop(cropType);
				int currCycle = (metaData &0xE0) >> 5;
				if (currCycle == crop.getNumCycles()-1)
				{
					updateList.remove(i);
				}
				else
				{
					if (isWatered == 1)
					{
						int daysInCycle = (metaData &0x1E) >> 1;
						++daysInCycle;
						if (daysInCycle == crop.getDaysToGrow()[currCycle])
						{
							daysInCycle = 0;
							++currCycle;
							data = TileInfo.setFGSpriteID(data, crop.getCycleIds()[currCycle]);
						}
						//isWatered = 0;
						metaData = (metaData &~0x1)|isWatered;
						metaData = (metaData &~0xE0)|(currCycle << 5);
						metaData = (metaData &~0x1E)|(daysInCycle << 1);
						data = TileInfo.setMetaData(data, metaData);
						setTileData(row, col, data);
					}
				}
			}
		}
		
		// new
		
		for (Entry<Long, ITileDayUpdate> e : bgDayUpdateList.entrySet()) {
			
			long key = e.getKey();
			
			int row = (int)key;
			int col = (int)(key >> 32);
			
			e.getValue().onDayUpdate(this, row, col);
			
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
	
	List<int[]> updateList;
	
	Map<Long, ITileDayUpdate> bgDayUpdateList;
	Map<Long, ITileDayUpdate> fgDayUpdateList;
	
}
