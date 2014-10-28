package fomt.base.game;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.newdawn.slick.opengl.Texture;

import fomt.base.sprite.Sprite;
import fomt.base.sprite.SpriteTable;
import fomt.base.tile.TileInfo;
import fomt.base.world.World;
import fomt.utils.gl.ICamera;

public class WorldRenderer {

	// --- Constructor ---
	
	public WorldRenderer(SpriteTable sprites) {
		this.sprites = sprites;
	}
	
	// --- Instance Methods ---
	
	public void render(ICamera camera, World world) {
		
		camera.apply();
		
		world.forEachTile(tile -> {
			
			int r = tile.row;
			int c = tile.col;
			long data = tile.data;
			
			int bgSpriteID = TileInfo.getBGSpriteID(data);
			int fgSpriteID = TileInfo.getFGSpriteID(data);
			
			Sprite sprite = sprites.getSprite(bgSpriteID);
			Texture texture = sprite.getTexture();
			
			if (texture != null) {
				texture.bind();
				drawQuad(c * 32, r * 32, c * 32 + 32, r * 32 + 32);	
			}
			
			sprite = sprites.getSprite(fgSpriteID);
			texture = sprite.getTexture();
			
			if (texture != null) {
				texture.bind();
				drawQuad(c * 32, r * 32, c * 32 + 32, r * 32 + 32);	
			}
			
			
		});
		
		camera.unapply();
		
	}

	protected void drawQuad(float x0, float y0, float x1, float y1) {
		
		glBegin(GL_QUADS);
		
		glTexCoord2f(0f, 0f);
		glVertex2f(x0, y0);
		
		glTexCoord2f(1f, 0f);
		glVertex2f(x1, y0);
		
		glTexCoord2f(1f, 1f);
		glVertex2f(x1, y1);

		glTexCoord2f(0f, 1f);
		glVertex2f(x0, y1);
		
		glEnd();
	
	}
	
	// --- Instance Fields ---
	
	protected SpriteTable sprites;
	
}
