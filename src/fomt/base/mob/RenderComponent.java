package fomt.base.mob;

import fomt.utils.gl.GLRenderer;

public class RenderComponent implements IRenderComponent {

	@Override
	public void render(Mob m, float f) {
		
		float lx = m.x - m.vx - 16f;
		float ly = m.y - m.vy - 16f;
		
		lx += f * m.vx;
		ly += f * m.vy;
		
		GLRenderer.drawQuad(lx, ly - 32, lx + 32, ly + 32);
		
	}

}
