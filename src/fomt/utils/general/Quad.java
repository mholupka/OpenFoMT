package fomt.utils.general;

public class Quad {
	
	// --- Constructor ---
	
	public Quad() {
		this(0, 0, 0, 0);
	} 
	public Quad(int x0, int y0, int x1, int y1) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
	}
	
	// --- Instance Fields ---
	
	public int x0, y0, x1, y1;
	
}
