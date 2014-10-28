package fomt.testerino;

import org.lwjgl.LWJGLException;

import fomt.base.game.GameWindow;
import fomt.utils.gl.GLWindow;

public class Test {

	public static void main(String[] args) {
	
		GLWindow win = new GameWindow("OpenFoMT", 1280, 720);
		
		try {
			win.open();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		win.loop();
		
		win.destroy();		
		
	}
	
}
