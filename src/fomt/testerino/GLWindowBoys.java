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
	float scale = 32f;
	
	//This comment should make changes
	@Override
	protected void render(float f) {
		
		glClear(GL_COLOR_BUFFER_BIT);
		
		glColor3f(1, 1, 1);
		
		texture.bind();
		
		int x = 0, y = 0;
		
		glPushMatrix();
		
		glTranslatef(this.x - scale * 5 + f * dx, this.y - scale * 5 + f * dy, 0f);
	
		for (int i = 0; i < 10; ++i) {
			for (int j = 0; j < 10; ++j) {
				drawQuad(x, y, x + scale, y + scale);			
				x += scale;
			}
			y += scale;
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
			
		int dw = Mouse.getDWheel();
		if (dw < 0){
			System.out.println(dw);
			if (scale > 10) {
				scale -= .8f;
			} else {
				scale = 10;
			}
		} else if (dw > 0) {
			if (scale < 64) {
				scale += .8f;
			} else {
				scale = 64;
			}
		}
	
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
