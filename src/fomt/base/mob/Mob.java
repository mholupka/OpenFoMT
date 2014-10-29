package fomt.base.mob;

public class Mob {

	// --- Constructor ---
	
	protected Mob() {
		
	}
	
	// --- Instance Methods ---
	
	public float getX() { return x; }
	public float getY() { return y; }
	public float getVX() { return vx; }
	public float getVY() { return vy; }
	
	// --- Instance Fields ---
	
	protected int row, col;
	
	protected float x, y;
	
	protected float vx, vy;
	
}
