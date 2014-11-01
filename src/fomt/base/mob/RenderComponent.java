package fomt.base.mob;

import fomt.utils.gl.GLRenderer;

public class RenderComponent implements IRenderComponent {

	@Override
	public void render(Mob m, float f) {
		
		float lx = m.x - m.vx - 16f;
		float ly = m.y - m.vy - 16f;
		
		lx += f * m.vx;
		ly += f * m.vy;
		
		if (m.facing == Direction.DOWN)
			GLRenderer.setColor(0, 0, 0, 1);
		if (m.facing == Direction.UP)
			GLRenderer.setColor(255, 0, 0, 1);
		if (m.facing == Direction.LEFT)
			GLRenderer.setColor(0, 255, 0, 1);
		if (m.facing == Direction.RIGHT)
			GLRenderer.setColor(0, 0, 255, 1);
		
		GLRenderer.drawQuad(lx, ly - 32, lx + 32, ly + 32);
		
	}

}
