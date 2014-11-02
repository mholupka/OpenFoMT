package fomt.base.mob;

import fomt.base.sprite.SpriteTable;
import fomt.utils.gl.GLRenderer;

public class RenderComponent implements IRenderComponent {

	private static float[] offsets = new float[] { 0f, .125f, .250f, .375f, .500f, .625f, .750f, .875f, 1f };
	private static int index = 1;
	private static int state = 0;
	private long l;
	
	@Override
	public void render(SpriteTable sprites, Mob m, float f) {
			
		float lx = m.x - m.vx - 32f;
		float ly = m.y - m.vy - 48f;
		
		lx += f * m.vx;
		ly += f * m.vy;
		
		switch (m.facing) {
		case Direction.DOWN:
			state = 0;
			break;
		case Direction.UP:
			state = 3;
			break;
		case Direction.LEFT:
			state = 2;
			break;
		case Direction.RIGHT:
			state = 1;
			break;
		}
		
		if (m.sx != 0 || m.sy != 0) {
			long l1 = System.currentTimeMillis();
			if (l1 - l > 190) {
				index = (index + 1) % 4;
				l = l1;
			}		
		} else {
			index = 0;
		}
		
		GLRenderer.setColor(1f, 1f, 1f, 1f);
		sprites.getSprite(25).getTexture().bind();
		GLRenderer.drawTexturedQuad(lx, ly, lx + 64, ly + 64, offsets[index], offsets[state], offsets[index + 1], offsets[state + 1]);
			
	}

}
