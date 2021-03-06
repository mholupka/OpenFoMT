package fomt.base.mob;

import fomt.base.tile.TileInfo;
import fomt.base.world.World;


public class BasicPhysics implements IPhysicsComponent {

	protected static final float MAX_ACCEL = .3f;
	protected static final float ONE_OVER_32 = 1f / 32f;
	
	private boolean isInvalidTileCoord(int row, int col, World w) {
		return row < 0 || col < 0 || row >= w.getHeight() || col >= w.getWidth();
	}
	
	private boolean checkCollisions(World w, Mob m) {
		
		boolean collision = false;
		
		int r, c, d, n;
		
		if (m.vx != 0) {
			n = (int)m.x + (m.vx >= 0 ? 16 : -16);
			if (n < 16) {
				n -= 32;
			}
			c = n / 32;
			d = m.vx >= 0 ? -1 : 1;
			
			r = (int)(m.y - 11) / 32;		
			if (isInvalidTileCoord(r, c, w) || TileInfo.isDense(w.getTileData(r, c))) {
				m.vx = 0;
				m.x = 16 + (c+d) * 32;
				collision = true;
			}

			r = (int)(m.y + 11) / 32;
			if (isInvalidTileCoord(r, c, w) || TileInfo.isDense(w.getTileData(r, c))) {			
				m.vx = 0;
				m.x = 16 + (c+d) * 32;
				collision = true;
			}	
		}
		
		if (m.vy != 0) {
			n = (int)m.y + (m.vy >= 0 ? 16 : -16);
			if (n < 16) {
				n -= 32;
			}
			r = n / 32;
			d = m.vy >= 0 ? -1 : 1;
			
			c = (int)(m.x - 11) / 32;		
			if (isInvalidTileCoord(r, c, w) || TileInfo.isDense(w.getTileData(r, c))) {
				m.vy = 0;
				m.y = 16 + (r+d) * 32;
				collision = true;
			}
			
			c = (int)(m.x + 11) / 32;	
			if (isInvalidTileCoord(r, c, w) || TileInfo.isDense(w.getTileData(r, c))) {			
				m.vy = 0;
				m.y = 16 + (r+d) * 32;
				collision = true;
			}
		}
	
		return collision;
		
	}
	
	@Override
	public void update(World w, Mob m) {
		
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
		
		checkCollisions(w, m);
		
		m.row = (int)m.y / 32;
		m.col = (int)m.x / 32;
			
	}
	
}
