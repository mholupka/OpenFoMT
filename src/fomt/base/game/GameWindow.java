package fomt.base.game;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import fomt.base.sprite.Sprite;
import fomt.base.sprite.SpriteTable;
import fomt.base.tile.TileInfo;
import fomt.base.world.World;
import fomt.utils.gl.GLWindow;
import fomt.utils.gl.ICamera;
import fomt.utils.gl.TileCamera;

public class GameWindow extends GLWindow {
	
	public GameWindow(String title, int width, int height) {
		super(title, width, height);
	
	}

	public void postSetup()
	{
		gameSprites = new SpriteTable();
		loadSprites();
		
		font = new TrueTypeFont(new Font("Times New Roman", Font.ITALIC, 40), true);
		
		clock = new Clock(0);
		camera = new TileCamera(10, 10, getWidth(), getHeight());
		world = new World("Test Farm", 20, 20);
		worldRenderer = new WorldRenderer(gameSprites);		 
				
		world.forEachTile(tile -> {
			
			int r = tile.row;
			int c = tile.col;
			long data = tile.data;
			
			if (r == 0 || r == 19 || c == 0 || c == 19) {
				world.setTileData(r, c, TileInfo.setBGSpriteID(data, 1));
			} 
			else if (r == 1 && c == 1) {
				world.setTileData(r, c, TileInfo.setBGSpriteID(data, 6));
			}
			else if (r == 1 && c > 1 && c < 18) {
				world.setTileData(r, c, TileInfo.setBGSpriteID(data, 10));
			}
			else if (r == 1 &&  c == 18) {
				world.setTileData(r, c, TileInfo.setBGSpriteID(data, 5));
			}
			else if (c == 1 && r > 1 && r < 18) {
				world.setTileData(r, c, TileInfo.setBGSpriteID(data, 7));
			}
			else if (c == 1 && r == 18) {
				world.setTileData(r, c, TileInfo.setBGSpriteID(data, 3));
			}
			else if (r == 18 && c > 1 && c < 18) {
				world.setTileData(r, c, TileInfo.setBGSpriteID(data, 8));
			}
			else if (r == 18 &&  c == 18) {
				world.setTileData(r, c, TileInfo.setBGSpriteID(data, 4));
			}
			else if (c == 18 && r > 1 && r < 18) {
				world.setTileData(r, c, TileInfo.setBGSpriteID(data, 9));
			}
			else
			{
				world.setTileData(r, c, TileInfo.setBGSpriteID(data, 2));
			}
			
			data = world.getTileData(r, c);
			
			if (r == 5 && c == 5)
			{
				data = TileInfo.setFGSpriteID(data, 11);
				world.setTileData(r, c, TileInfo.setMetaData(data, 3));
			}
			
		});
		
		world.addTileToUpdate(5, 5);
	}
	
	public void loadSprites()
	{
		Texture tex;
		try {
			gameSprites.addSprite(new Sprite(0, null));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/grass1.png"));
			gameSprites.addSprite(new Sprite(1, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/farmDirt.png"));
			gameSprites.addSprite(new Sprite(2, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/grassDirtCornerBottomLeft.png"));
			gameSprites.addSprite(new Sprite(3, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/grassDirtCornerBottomRight.png"));
			gameSprites.addSprite(new Sprite(4, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/grassDirtCornerTopRight.png"));
			gameSprites.addSprite(new Sprite(5, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/grassDirtCornerTopLeft.png"));
			gameSprites.addSprite(new Sprite(6, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/grassDirtEdgeLeft.png"));
			gameSprites.addSprite(new Sprite(7, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/grassDirtEdgeBottom.png"));
			gameSprites.addSprite(new Sprite(8, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/grassDirtEdgeRight.png"));
			gameSprites.addSprite(new Sprite(9, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/grassDirtEdgetop.png"));
			gameSprites.addSprite(new Sprite(10, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/log1.png"));
			gameSprites.addSprite(new Sprite(11, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/logDecayed1.png"));
			gameSprites.addSprite(new Sprite(12, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/tilledFarmDirt.png"));
			gameSprites.addSprite(new Sprite(13, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/seeds.png"));
			gameSprites.addSprite(new Sprite(14, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/flowerSprout.png"));
			gameSprites.addSprite(new Sprite(15, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/pinkCatGrassSprout2.png"));
			gameSprites.addSprite(new Sprite(16, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/pinkCatGrass.png"));
			gameSprites.addSprite(new Sprite(17, tex));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void onTick() 
	{
		clock.updateTime();
		
		if (clock.dayChanged())
		{
			world.dayUpdate();
		}
		
		while (Keyboard.next()) {
			
			if (Keyboard.getEventKeyState())
				continue;
			
			switch (Keyboard.getEventKey()) {
			case Keyboard.KEY_W:
				camera.down();
				break;
			case Keyboard.KEY_A:
				camera.right();
				break;
			case Keyboard.KEY_S:
				camera.up();
				break;
			case Keyboard.KEY_D:
				camera.left();
				break;
			}
		}
	}
	
	public void render(float f)
	{
		glClear(GL_COLOR_BUFFER_BIT);
		worldRenderer.render(camera, world);
		drawClock();
	}
	
	protected void drawClock()
	{
		String s = clock.timeToDisplay();
		s = clock.seasonName + " " + clock.day + " " + s;
		font.drawString(getWidth()-font.getWidth(s), getHeight()-font.getHeight(s), s);
	}
	
	SpriteTable gameSprites;
	Clock clock;
	ICamera camera;
	World world;
	WorldRenderer worldRenderer;
	TrueTypeFont font;
	
}
