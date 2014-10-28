package fomt.utils.gl;

import org.lwjgl.opengl.GL11;

public class TileCamera implements ICamera {

	// --- Constructors ---
	
	public TileCamera() {
		this(0, 0, 0, 0);
	}
	public TileCamera(int row, int col, int screenWidth, int screenHeight) {
		this.row = row;
		this.col = col;
		this.screenWidthHalf = screenWidth / 2;
		this.screenHeightHalf = screenHeight / 2;
	}
	
	// --- Instance Methods ---
	
	@Override
	public void apply() {
		GL11.glPushMatrix();
		GL11.glTranslatef(col * -32 + screenWidthHalf - 16, row * -32 + screenHeightHalf - 16, zoom);
	}
	
	@Override
	public void unapply() {
		GL11.glPopMatrix();
	}
	
	public void left() { ++col; }
	public void right() { --col; }
	public void up() { ++row; }
	public void down() { --row; }
	
	public int getRow() { return row; }
	public int getColumn() { return col; }
	
	// --- Instance Fields ---
	
	protected int row, col, screenWidthHalf, screenHeightHalf;
	protected float rot, zoom;
	
}
