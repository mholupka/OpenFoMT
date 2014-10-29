package fomt.base.game;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import fomt.base.crops.Crop;
import fomt.base.crops.CropTable;
import fomt.base.sprite.Sprite;
import fomt.base.sprite.SpriteTable;
import fomt.base.tile.TileInfo;
import fomt.base.tile.TileTable;
import fomt.base.tile.TileType;
import fomt.base.world.World;
import fomt.utils.gl.GLRenderer;
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
		crops = new CropTable();
		loadSprites();
		loadCrops();
		
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
				data = TileInfo.setBGSpriteID(data, 13);
				data = TileInfo.setFGSpriteID(data, 14);
				int metaData = TileInfo.getMetaData(data);
				metaData = (metaData &~0x1)|1;
				metaData = (metaData &~0x1E)|0 << 1;
				metaData = (metaData &~0xE0)|0 << 5;
				metaData = (metaData &~0x3FF00)|0 << 8;
				world.setTileData(r, c, TileInfo.setMetaData(data, metaData));
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
	
	public void loadCrops()
	{
		crops.addCrop(new Crop(new int[] {1,2,3,4}, 4, new int[] {14, 15, 16, 17}, 0));
	}
	
	public void onTick() 
	{
		clock.updateTime();
		
		if (clock.dayChanged())
		{
			world.dayUpdate(crops);
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
		
		updateMouseTile();
		
		int wheel;
		while (Mouse.next()) {
			
			wheel = Mouse.getEventDWheel();
			
			if (wheel > 0) {
				--gameSpriteSelection;
				if (gameSpriteSelection == -1)
					gameSpriteSelection = gameSprites.size() - 1;
			} else if (wheel < 0) {
				gameSpriteSelection = (gameSpriteSelection + 1) % gameSprites.size();
			}
			
			if (Mouse.getEventButtonState())
				continue;
			
			if (Mouse.getEventButton() == 0) {
				TileType type = TileTable.table[gameSpriteSelection];
				if (type != null) {
					type.onPutDown(world, mouseRow, mouseCol);
				}
			} else if (Mouse.getEventButton() == 1) {
				long data = world.getTileData(mouseRow, mouseCol);
				data = TileInfo.setBGSpriteID(data, gameSpriteSelection);
				world.setTileData(mouseRow, mouseCol, data);
			}
			
		}
		
		
		
	}
	
	private void updateMouseTile() {
		
		int cx = getWidth() / 2 - camera.getX();
		int nx = Mouse.getX() - cx + 16;
		mouseCol = nx / 32;
		if (mouseCol < 0) 
			mouseCol = 0;
		else if (mouseCol >= world.getWidth())
			mouseCol = world.getWidth() - 1;
		
		int cy = getHeight() / 2 - camera.getY();
		int ny = (getHeight() - Mouse.getY()) - cy + 16;
		mouseRow = ny / 32;
		if (mouseRow < 0)
			mouseRow = 0;
		else if (mouseRow >= world.getHeight())
			mouseRow = world.getHeight() - 1;
		
	}
	public void render(float f)
	{
		glClear(GL_COLOR_BUFFER_BIT);
		worldRenderer.render(camera, world);
		drawMouseTile();
		drawClock();
		drawSpriteTable();
	}
	
	protected void drawSpriteTable() {
		
		float alpha = .2f;
		
		int tableSize = gameSprites.size();
		int fullWidth = tableSize * 35;
		
		int x = (getWidth() - fullWidth) / 2;
		int y = getHeight() - 35;
		int y2 = y + 32;
		
		GLRenderer.setColor(1f, 1f, 1f, alpha);
		
		for (int i = 0; i < tableSize; ++i) {
			
			Sprite s = gameSprites.getSprite(i);
			Texture t = s.getTexture();
			if (t == null)
				continue;
				
			t.bind();
			if (i == gameSpriteSelection) {
				GLRenderer.setColor(1f, 1f, 1f, 1f);
				GLRenderer.drawTexturedQuad(x, y, x + 32, y2);
				GLRenderer.setColor(1f, 1f, 1f, alpha);
			} else {
				GLRenderer.drawTexturedQuad(x, y, x + 32, y2);
			}
			
			x += 35;
			
		}
		
		GLRenderer.setColor(1f, 1f, 1f, 1f);
		
	}
	
	protected void drawMouseTile() {
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(1f, 1f, 1f);
		
		camera.apply();
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex2f(mouseCol * 32, mouseRow * 32);
		GL11.glVertex2f(mouseCol * 32 + 32, mouseRow * 32);
		GL11.glVertex2f(mouseCol * 32 + 32, mouseRow * 32 + 32);
		GL11.glVertex2f(mouseCol * 32, mouseRow * 32 + 32);
		GL11.glEnd();
		camera.unapply();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
	}
	
	protected void drawClock()
	{
		String time = clock.timeToDisplay();
		String date = clock.seasonName + " " + clock.day;
		font.drawString(getWidth()-font.getWidth(time), getHeight()-font.getHeight(time), time);
		font.drawString(getWidth()-font.getWidth(date) - 120, getHeight()-font.getHeight(date), date);
	}
	
	SpriteTable gameSprites;
	CropTable crops;
	Clock clock;
	ICamera camera;
	World world;
	WorldRenderer worldRenderer;
	TrueTypeFont font;
	private int mouseRow, mouseCol;
	private int gameSpriteSelection;
	
}
