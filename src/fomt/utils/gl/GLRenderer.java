package fomt.utils.gl;

import static org.lwjgl.opengl.GL11.*;

public class GLRenderer {

	public static void setColor(float r, float g, float b, float a) {
		glColor4f(r, g, b, a);
	}
	
	public static void drawTexturedQuad(float x0, float y0, float x1, float y1) {
		
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
	
}
