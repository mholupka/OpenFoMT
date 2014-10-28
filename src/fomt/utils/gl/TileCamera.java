package fomt.utils.gl;

import org.lwjgl.opengl.GL11;

public class TileCamera implements ICamera {

	// --- Constructors ---
	
	public TileCamera() {
		this(0, 0);
	}
	public TileCamera(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	// --- Instance Methods ---
	
	@Override
	public void apply() {
		GL11.glTranslatef(col * 32, row * 32, zoom);
	}
	
	public void left() { --col; }
	public void right() { ++col; }
	public void up() { --row; }
	public void down() { ++row; }
	
	public int getRow() { return row; }
	public int getColumn() { return col; }
	
	// --- Instance Fields ---
	
	protected int row, col;
	protected float rot, zoom;
	
}
