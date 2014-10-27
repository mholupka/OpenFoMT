package fomt.testerino;

import static org.lwjgl.opengl.GL11.*;

import java.nio.IntBuffer;

import org.lwjgl.input.Mouse;

public class GLWindowBoys extends GLWindow {
	
	public GLWindowBoys(String title, int width, int height) {
		super(title, width, height, 8);	
		
	}
	
	@Override
	protected void postSetup() {
		
	}
	
	int x, y, dx, dy;
	
	@Override
	protected void render(float f) {
		
		glClear(GL_COLOR_BUFFER_BIT);
		
		glColor3f(1, 1, 1);
		
		drawQuad(x + dx * f, y + dy * f, x + dx * f + 10, y + dy * f + 10);
		
	}
	
	protected void drawQuad(float x0, float y0, float x1, float y1) {
		glBegin(GL_QUADS);
		glVertex2f(x0, y0);
		glVertex2f(x1, y0);
		glVertex2f(x1, y1);
		glVertex2f(x0, y1);
		glEnd();
	}
	
	@Override
	protected void onTick() {
		
		x = Mouse.getX() - 5;
		dx = Mouse.getDX();
		
		y = getHeight() - Mouse.getY() - 5;
		dy = -Mouse.getDY();
		
	}
	
}
