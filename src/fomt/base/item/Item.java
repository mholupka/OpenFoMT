package fomt.base.item;

import fomt.base.mob.Mob;
import fomt.base.world.World;

public interface Item {

	public void onUse(World w, Mob m);
	
}
