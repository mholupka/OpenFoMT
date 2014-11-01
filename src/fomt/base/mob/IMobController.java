package fomt.base.mob;

import fomt.base.input.InputManager;
import fomt.base.world.World;

public interface IMobController {

	public void update(World w, Mob mob, InputManager input);
	
}
