package fomt.base.ui;

public class UIElement {

	/* --- Constructors --- */
	
	protected UIElement() {
		
	}
	
	/* --- Instance Methods (Interface) --- */
	
	public int getX() { return x; }
	public int getY() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	/* --- Instance Fields --- */
	
	protected int x, y, width, height;
	
}
