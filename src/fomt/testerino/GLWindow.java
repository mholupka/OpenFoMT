package fomt.testerino;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public abstract class GLWindow {
	
	// Window Info
	private String title;
	private int width, height, samples;
	
	// FPS Info
	private int frameCount;
	private long lastFPSReport;
	
	// Timer Data
	private long lastTick, period;
	
	// State Information
	private boolean doLoop;
	
	public GLWindow(String title, int width, int height) {
		this(title, width, height, 1);
	}
	public GLWindow(String title, int width, int height, int samples) {
		
		if (title == null || width < 1 || height < 1)
			throw new IllegalArgumentException();
	
		this.title = title;
		this.width = width;
		this.height = height;
		this.samples = samples;
		
		this.period = 8L;
		
		this.doLoop = true;
	
	}
	
	public final String getTitle() {
		return title;
	}
	public final int getWidth() {
		return width;
	}
	public final int getHeight() {
		return height;
	}

	public void open() throws LWJGLException {
		
		setupDisplay();
		setupGL();
		
		postSetup();
		
	}
	
	protected void postSetup() { }
	
	protected abstract void onTick();
	protected abstract void render(float f);
	
	public void loop() {
		
		long t, t2;
		float f;
		int i;
		
		lastTick = Sys.getTime() * 1000L / Sys.getTimerResolution();
		while (doLoop && !Display.isCloseRequested()) {

			i = 0;
			while ((t2 = (t = Sys.getTime() * 1000L / Sys.getTimerResolution()) - lastTick) > period && i < 5) {
				
				onTick();
				lastTick = t;
				++i;
				
			}
			
			f = (float)t2 / (float)period;
			render(f);
			
			++frameCount;
			if (t - lastFPSReport >= 1000L) {
				Display.setTitle(title + " FPS(" + frameCount + ")");
				frameCount = 0;
				lastFPSReport = t;
			}
			
			Display.update();
			
		}
		
	}
	
	public void destroy() {
		doLoop = false;
		if (Display.isCreated()) {
			Display.destroy();		
		}
	}
	
	protected void setupDisplay() throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(width, height));
		//Display.setVSyncEnabled(true);
		Display.setTitle(title);
		Display.create(new PixelFormat(0, 8, 0, samples));
	}
	
	protected void setupGL() {
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
	//	glShadeModel(GL_SMOOTH);
		glEnable(GL_DEPTH_TEST);
             
		//glEnable(GL_CULL_FACE);
		glCullFace(GL11.GL_NONE);
		
		//glEnable(GL_COLOR_MATERIAL);
      //  glColorMaterial(GL_FRONT, GL_DIFFUSE);
    //    org.lwjgl.BufferUtils.createFloatBuffer(4);
        //glMaterial(GL_FRONT_AND_BACK, GL_DIFFUSE, asFlippedFloatBuffer(new float[] { 1f, 0f, 0f, 1f } ));
        
		//glMaterialf(GL_FRONT, GL_SHININESS, 10f);
        //if (GLContext.getCapabilities().GL_ARB_depth_clamp)
        //	glEnable(ARBDepthClamp.GL_DEPTH_CLAMP);
       
        glClearColor(0f, 0f, 0f, 1f); 
        
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    	
    	glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		
		glOrtho(0, width, height, 0, -1, 1);
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
        
	}
	
}
