package fomt.testerino;

import static org.lwjgl.opengl.GL11.*;

import java.io.FileInputStream;
import java.io.IOException;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import fomt.utils.gl.GLWindow;

public class GLWindowBoys extends GLWindow {
	
	private Texture texture;
	
	public GLWindowBoys(String title, int width, int height) {
		super(title, width, height, 8);	
		
	}
	
	@Override
	protected void postSetup() {
	
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/shitty_grass.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	float x, y, dx, dy;
	
	@Override
	protected void render(float f) {
		
		glClear(GL_COLOR_BUFFER_BIT);
		
		glColor3f(1, 1, 1);
		
		texture.bind();
		
		int[][] tiles = new int[10][10];
		
		for (int i = 0; i < 10; ++i)
		{
			for (int j = 0; j < 10; ++j)
			{
				if (i==0 || i==9 || j==0 || j==9)
				{
					tiles[i][j] = 1;
				}
				else
				{
					tiles[i][j] = 0;
				}
			}
		}
		
		int x = 0, y = 0;
		
		glPushMatrix();
		
		glTranslatef(this.x - 160+ f * dx, this.y - 160 + f * dy, 0f);
	
		for (int i = 0; i < 10; ++i) {
			for (int j = 0; j < 10; ++j) {
				if (tiles[i][j] == 0)
				{
					try {
						texture = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/farmDirt.png"));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (tiles[i][j] == 1)
				{
					try {
						texture = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/shitty_grass.png"));
					} catch (IOException e) {
						e.printStackTrace();
					}
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
