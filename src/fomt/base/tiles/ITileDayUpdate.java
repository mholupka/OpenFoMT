package fomt.base.tiles;

import fomt.base.world.World;

public interface ITileDayUpdate {

	public void onDayUpdate(World world, int row, int col);
	
}
