package fomt.base.sprite;

import java.util.ArrayList;

public class SpriteTable {
	
	// ---Constructor---
	public SpriteTable()
	{
		this.sprites = new ArrayList<Sprite>();
	}
	
	// --- Instance Methods ---
	
	public void addSprite(Sprite s)
	{
		sprites.add(s);
	}
	
	public Sprite getSprite(int id) 
	{
		return sprites.get(id);
	}
	
	public int size() {
		 return sprites.size();
	}
	
	// --- Instance Fields ---
	protected ArrayList<Sprite> sprites;
	
}
