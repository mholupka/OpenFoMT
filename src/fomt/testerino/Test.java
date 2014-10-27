package fomt.testerino;

import org.lwjgl.LWJGLException;

public class Test {

	public static void main(String[] args) {
	
		GLWindow win = new GLWindowBoys("GL WINDOW TEST BOYS", 1280, 720);
		
		try {
			win.open();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		win.loop();
		
		win.destroy();		
		
	}
	
}
