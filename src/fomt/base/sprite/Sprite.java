package fomt.base.sprite;

import org.newdawn.slick.opengl.Texture;

public class Sprite {
	
	// --- Constructor ---
	
	public Sprite(int id, Texture texture)
	{
		this.id = id;
		this.texture = texture;
	}
	
	// --- Class Methods ---
	
	public Texture getTexture()
	{
		return this.texture;
	}
	
	public int getID()
	{
		return this.id;
	}
	
	// --- Instance Fields ---
	
	protected Texture texture;
	protected int id;

}
