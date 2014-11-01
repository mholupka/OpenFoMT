package fomt.base.mob;

import fomt.base.input.InputManager;
import fomt.base.item.Item;
import fomt.base.world.World;


public class Mob {

	// --- Constructor ---
	
	public Mob(IMobController control, IPhysicsComponent physics, IRenderComponent renderer) {
		this(0f, 0f, control, physics, renderer);
	}
	public Mob(float x, float y, IMobController control, IPhysicsComponent physics, IRenderComponent renderer) {
		this.x = x;
		this.y = y;
		this.control = control;
		this.physics = physics;
		this.renderer = renderer;
	}
	
	// --- Instance Methods ---
	
	public Item getHeldItem() {
		return heldItem;
	}
	
	public void setHeldItem(Item item) {
		heldItem = item;
	}
	
	public void onTick(World world, InputManager input) {
		control.update(world, this, input);
		physics.update(world, this);
	}
	
	public void render(float f) {
		renderer.render(this, f);
	}
	
	// --- Instance Fields ---
	
	public int row, col;
	public float x, y;
	public float vx, vy;
	public float sx, sy;
	
	protected IMobController control;
	protected IPhysicsComponent physics;
	protected IRenderComponent renderer;
	
	protected Item heldItem;
	
}
