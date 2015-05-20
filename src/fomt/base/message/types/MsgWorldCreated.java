package fomt.base.message.types;

import fomt.base.message.IMessage;
import fomt.base.world.World;

public class MsgWorldCreated implements IMessage {

	/* --- Constructors --- */
	
	public MsgWorldCreated(World world) {
		this.world = world;
	}
	
	/* --- Instance Methods (Interface) --- */
	
	public World getWorld() { return world; }

	/* --- Instance Fields --- */
	
	private World world;
	
}
