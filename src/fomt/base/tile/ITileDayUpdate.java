package fomt.base.tile;

import fomt.base.world.World;

public interface ITileDayUpdate {

	public void onDayUpdate(World world, int row, int col);
	
}
