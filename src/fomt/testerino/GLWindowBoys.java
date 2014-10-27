package fomt.testerino;

import static org.lwjgl.opengl.GL11.*;

import java.io.FileInputStream;
import java.io.IOException;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import fomt.base.world.World;
import fomt.utils.gl.GLWindow;
import fomt.base.world.TileInfo;

public class GLWindowBoys extends GLWindow {
	
	private Texture grass, dirt;
	private World world;
	
	public GLWindowBoys(String title, int width, int height) {
		super(title, width, height, 8);	
	
	}
	
	@Override
	protected void postSetup() {
	
		world = new World("test_world", 10, 10);
		
		world.forEachTile(tile -> {
			
			int r = tile.row;
			int c = tile.col;
			long data = tile.data;
			
			if (r == 0 || r == 9 || c == 0 || c == 9) {
				world.setTileData(r, c, TileInfo.setBGSpriteID(data, 0));
			} else {
				world.setTileData(r, c, TileInfo.setBGSpriteID(data, 1));
			}
			
		});
		
		try {
			grass = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/grass1.png"));
			dirt = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/farmDirt.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	float x, y, dx, dy;
	
	//This comment should make changes
	@Override
	protected void render(float f) {
		
		glClear(GL_COLOR_BUFFER_BIT);
		
		glColor3f(1, 1, 1);
	
		int x = 0, y = 0;
		
		glPushMatrix();
		
		glTranslatef(this.x - 160+ f * dx, this.y - 160 + f * dy, 0f);
	
		for (int i = 0; i < 10; ++i) {
			for (int j = 0; j < 10; ++j) {
				
				int bgSpriteID = TileInfo.getBGSpriteID(world.getTileData(i, j));
		
				if (bgSpriteID == 0) {
					grass.bind();
				} else {
					dirt.bind();
				}
				
				drawQuad(x, y, x + 32, y + 32);			
			
				x += 32;
			
			}
			
			y += 32;
			x = 0;
		
		}
		
		glPopMatrix();
		
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
		
	@Override
	protected void onTick() {
				
		if (Mouse.isButtonDown(0)) {
		
			int xOff = Mouse.getX() - (int)x;
			int yOff = (getHeight() - Mouse.getY()) - (int)y;
		
			if (xOff == 0 && yOff == 0) 
				return;
			
			int lenOff = xOff * xOff + yOff * yOff;
		
			float sqrt = (float)Math.sqrt(lenOff);
			
			dx = (float)xOff * Math.abs(xOff) / (100 * sqrt);
			x += dx;
			
			dy = (float)yOff * Math.abs(yOff) / (100 * sqrt);
			y += dy;
				
		} else {
		
			if (Math.abs(dx) > .002f) {
				dx *= .98f;
				x += dx;
			}
			
			if (Math.abs(dy) > .002f) {
				dy *= .98f;		
				y += dy;
			}
			
		}
		
	}
	
}
