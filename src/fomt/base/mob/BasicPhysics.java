package fomt.base.mob;

import fomt.base.tile.TileInfo;
import fomt.base.world.World;


public class BasicPhysics implements IPhysicsComponent {

	protected static final float MAX_ACCEL = .09f;
	protected static final float ONE_OVER_32 = 1f / 32f;
	
	private boolean isInvalidTileCoord(int row, int col, World w) {
		return row < 0 || col < 0 || row >= w.getHeight() || col >= w.getWidth();
	}
	
	private boolean checkCollisions(World w, Mob m) {
		
		boolean collision = false;
		
		int oldRow = m.row, oldCol = m.col;
		float nextX = m.x + m.vx, nextY = m.y + m.vy;
		float tx0, ty0, tx1, ty1;
		int r, c;
		
		c = (int)nextX + m.vx >= 0 ? 16 : -16;
	
		r = (int)m.y - 15;
		if (isInvalidTileCoord(r, c, w) || TileInfo.isDense(w.getTileData(r, c))) {
			System.out.println("OH NO");
			
			
		}
		
		r = (int)m.y + 15;
		if (isInvalidTileCoord(r, c, w) || TileInfo.isDense(w.getTileData(r, c))) {			
			System.out.println("OH NO");
			
		}
		

		
		
		// TODO - Y
		
		return collision;
		
	}
	
	@Override
	public void update(World w, Mob m) {
		
		if (checkCollisions(w, m)) {
			return;
		}
		
		float ax = m.sx - m.vx;
		float ay = m.sy - m.vy;
	
		if (ax >= MAX_ACCEL) ax = MAX_ACCEL;
		if (ax <= -MAX_ACCEL) ax = -MAX_ACCEL;
		if (ay >= MAX_ACCEL) ay = MAX_ACCEL;
		if (ay <= -MAX_ACCEL) ay = -MAX_ACCEL;
		
		m.vx += ax;
		m.vy += ay;
		
		m.x += m.vx;
		m.y += m.vy;
		
		m.row = (int)m.y / 32;
		m.col = (int)m.x / 32;
			
	}
	
}
