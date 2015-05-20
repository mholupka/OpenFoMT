package fomt.base.game;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import fomt.base.crops.CropTable;
import fomt.base.input.GameInput;
import fomt.base.input.InputManager;
import fomt.base.item.types.Hoe;
import fomt.base.item.types.Seeds;
import fomt.base.item.types.WateringCan;
import fomt.base.mob.BasicPhysics;
import fomt.base.mob.Mob;
import fomt.base.mob.PlayerController;
import fomt.base.mob.RenderComponent;
import fomt.base.sprite.Sprite;
import fomt.base.sprite.SpriteTable;
import fomt.base.tile.TileInfo;
import fomt.base.tile.TileTable;
import fomt.base.tile.TileType;
import fomt.base.tile.types.*;
import fomt.base.world.World;
import fomt.base.world.WorldIO;
import fomt.utils.gl.GLRenderer;
import fomt.utils.gl.GLWindow;
import fomt.utils.gl.ICamera;
import fomt.utils.gl.TileCamera;

public class GameWindow extends GLWindow {
	
	public GameWindow(String title, int width, int height) {
		super(title, width, height);
	
	}

	Mob mob;
	
	public void postSetup()
	{
		// testing...
		mob = new Mob(16, 16, new PlayerController(), new BasicPhysics(), new RenderComponent());
		mob.setHeldItem(new WateringCan(5, 1));
		mob.setPrimaryItem(new WateringCan(5, 1));
		mob.setSecondaryItem(new Hoe(1));
		mob.setThirdItem(new Seeds(0));
		
		gameSprites = new SpriteTable();
		crops = new CropTable();
	
		loadSprites();
		loadCrops();
		
		font = new TrueTypeFont(new Font("Times New Roman", Font.ITALIC, 40), true);
		font16 = new TrueTypeFont(new Font("Times New Roman", Font.ITALIC, 16), true);
		
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
				data = TileInfo.setBGSpriteID(data, 2);
				world.setTileData(r, c, TileInfo.setFGSpriteID(data, 6));
			}
			else if (r == 1 && c > 1 && c < 18) {
				data = TileInfo.setBGSpriteID(data, 2);
				world.setTileData(r, c, TileInfo.setFGSpriteID(data, 10));
			}
			else if (r == 1 &&  c == 18) {
				data = TileInfo.setBGSpriteID(data, 2);
				world.setTileData(r, c, TileInfo.setFGSpriteID(data, 5));
			}
			else if (c == 1 && r > 1 && r < 18) {
				data = TileInfo.setBGSpriteID(data, 2);
				world.setTileData(r, c, TileInfo.setFGSpriteID(data, 7));
			}
			else if (c == 1 && r == 18) {
				data = TileInfo.setBGSpriteID(data, 2);
				world.setTileData(r, c, TileInfo.setFGSpriteID(data, 3));
			}
			else if (r == 18 && c > 1 && c < 18) {
				data = TileInfo.setBGSpriteID(data, 2);
				world.setTileData(r, c, TileInfo.setFGSpriteID(data, 8));
			}
			else if (r == 18 &&  c == 18) {
				data = TileInfo.setBGSpriteID(data, 2);
				world.setTileData(r, c, TileInfo.setFGSpriteID(data, 4));
			}
			else if (c == 18 && r > 1 && r < 18) {
				data = TileInfo.setBGSpriteID(data, 2);
				world.setTileData(r, c, TileInfo.setFGSpriteID(data, 9));
			}
			else
			{
				/*
				//THIS IS FOR TESTING FLOWER GARDENS. I DON'T THINK I NEED IT BUT I'LL LEAVE IT FOR NOW
				TileType tTy;
				Random rand = new Random();
				int a = rand.nextInt(1000);
				if(a <= 500){
					data = TileInfo.setFGSpriteID(world.getTileData(r, c), 17);
					tTy = TileTable.getTileType(TileInfo.getFGSpriteID(data));
					tTy.onPutDown(world, r, c);
				}
				else if(a <= 800){
					data = TileInfo.setFGSpriteID(world.getTileData(r, c), 18);
					tTy = TileTable.getTileType(TileInfo.getFGSpriteID(data));
					tTy.onPutDown(world, r, c);
				}
				else if(a <= 998){
					data = TileInfo.setFGSpriteID(world.getTileData(r, c), 19);
					tTy = TileTable.getTileType(TileInfo.getFGSpriteID(data));
					tTy.onPutDown(world, r, c);
				}
				else{
					data = TileInfo.setFGSpriteID(world.getTileData(r, c), 27);
					tTy = TileTable.getTileType(TileInfo.getFGSpriteID(data));
					tTy.onPutDown(world, r, c);
				}
				*/
				world.setTileData(r, c, TileInfo.setBGSpriteID(data, 13));
			}
			
			data = world.getTileData(r, c);
			
		});
		
		//world.addTileToUpdate(5, 5);
		
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
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/grassCornerBottomLeft.png"));
			gameSprites.addSprite(new Sprite(3, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/grassCornerBottomRight.png"));
			gameSprites.addSprite(new Sprite(4, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/grassCornerTopRight.png"));
			gameSprites.addSprite(new Sprite(5, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/grassCornerTopLeft.png"));
			gameSprites.addSprite(new Sprite(6, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/grassEdgeLeft.png"));
			gameSprites.addSprite(new Sprite(7, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/grassEdgeBottom.png"));
			gameSprites.addSprite(new Sprite(8, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/grassEdgeRight.png"));
			gameSprites.addSprite(new Sprite(9, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/grassEdgetop.png"));
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
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/catGrassSprout2.png"));
			gameSprites.addSprite(new Sprite(16, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/pinkCatGrass.png"));
			gameSprites.addSprite(new Sprite(17, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/blueCatGrass.png"));
			gameSprites.addSprite(new Sprite(18, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/yellowCatGrass.png"));
			gameSprites.addSprite(new Sprite(19, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/water.png"));
			gameSprites.addSprite(new Sprite(20, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/grassCornerBottomLeftOuter.png"));
			gameSprites.addSprite(new Sprite(21, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/grassCornerBottomRightOuter.png"));
			gameSprites.addSprite(new Sprite(22, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/grassCornerTopRightOuter.png"));
			gameSprites.addSprite(new Sprite(23, tex));
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/grassCornerTopLeftOuter.png"));
			gameSprites.addSprite(new Sprite(24, tex));
			
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/player.png"));
			gameSprites.addSprite(new Sprite(25, tex));
			
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/tilledFarmDirtWatered.png"));
			gameSprites.addSprite(new Sprite(26, tex));
			
			tex = TextureLoader.getTexture("PNG", new FileInputStream("res/sprites/test/multiCatGrass.png"));
			gameSprites.addSprite(new Sprite(27, tex));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	InputManager input = new InputManager();
	
	public void loadCrops()
	{
		crops.addCrop((Crop)TileTable.table[PinkCatGrass.SPRITE_ID]);
		crops.addCrop((Crop)TileTable.table[BlueCatGrass.SPRITE_ID]);
		crops.addCrop((Crop)TileTable.table[YellowCatGrass.SPRITE_ID]);
		crops.addCrop((Crop)TileTable.table[MultiCatGrass.SPRITE_ID]);
	}
	
	public void onTick() 
	{
		clock.updateTime();
		worldRenderer.onTick();
		
		input.update();
		// test
		mob.onTick(world, input);
		
		if (clock.dayChanged())
		{
			world.onDayUpdate();
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
		
		if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
			try {
				WorldIO.save(world);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
			try {
				world = WorldIO.load("Test Farm");
			} catch (IOException e) {
				e.printStackTrace();
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
			
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				if (Mouse.getEventButton() == 0) {
					long data = world.getTileData(mouseRow, mouseCol);
					data = TileInfo.setDensity(data, !TileInfo.isDense(data));
					System.out.println("ASD");
					world.setTileData(mouseRow, mouseCol, data);
				} 
				continue;
			}
			
			if (Mouse.getEventButton() == 0) {
				TileType type = TileTable.table[gameSpriteSelection];
				if (type == null) {
					long data = world.getTileData(mouseRow, mouseCol);
					data = TileInfo.setMetaData(data,  0);
					world.removeTileFromUpdate(mouseRow, mouseCol);
					data = TileInfo.setFGSpriteID(data, gameSpriteSelection);
					world.setTileData(mouseRow, mouseCol, data);
				} else {
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
		GLRenderer.setColor(1f, 1f, 1f, 1f);
		
		worldRenderer.render(camera, world);
		
		camera.apply();
		mob.render(gameSprites, f);
		camera.unapply();
		
		drawMouseTile();
		drawClock();
		drawSpriteTable();
		drawFilter();
	}
	
	protected void drawFilter()
	{
		if(clock.time - 1080 >= 0 && clock.time - 1080 < 60 )
		{
			float alpha = (clock.time - 1080)*.0025f;
			GLRenderer.setColor(1, 204/255f, 0, alpha);
			GLRenderer.drawQuad(getWidth() / 2 - camera.getX() - 16, getHeight() / 2 - camera.getY() -16, (getWidth() / 2 - camera.getX() - 16) + 32 * world.getWidth(), (getHeight() / 2 - camera.getY() - 16) + 32 * world.getHeight());
		}
		if(clock.time - 1080 >= 60 && clock.time - 1080 < 180 )
		{
			float r = 255-(((clock.time - 1140f)/10)*21.25f);
			float g = 204-(((clock.time - 1140f)/10)*12.75f);
			float b = ((clock.time - 1140f)/10)*8.5f;
			GLRenderer.setColor(r/255f, g/255f, b/255f, .15f);
			GLRenderer.drawQuad(getWidth() / 2 - camera.getX() - 16, getHeight() / 2 - camera.getY() -16, (getWidth() / 2 - camera.getX() - 16) + 32 * world.getWidth(), (getHeight() / 2 - camera.getY() - 16) + 32 * world.getHeight());
		}
		if(clock.time >= 1200 && clock.time < 1260)
		{
			float alpha = .15f + (((clock.time - 1200)/10)*.035f);
			GLRenderer.setColor(0, 51/255f, 102/255f, alpha);
			GLRenderer.drawQuad(getWidth() / 2 - camera.getX() - 16, getHeight() / 2 - camera.getY() -16, (getWidth() / 2 - camera.getX() - 16) + 32 * world.getWidth(), (getHeight() / 2 - camera.getY() - 16) + 32 * world.getHeight());
		}
		if(clock.time >= 1260 || clock.time < 300)
		{
			GLRenderer.setColor(0, 51/255f, 102/255f, .36f);
			GLRenderer.drawQuad(getWidth() / 2 - camera.getX() - 16, getHeight() / 2 - camera.getY() -16, (getWidth() / 2 - camera.getX() - 16) + 32 * world.getWidth(), (getHeight() / 2 - camera.getY() - 16) + 32 * world.getHeight());
		}
		if(clock.time >= 300 && clock.time < 360)
		{
			float alpha = .36f - (((clock.time - 300)/10)*.035f);
			GLRenderer.setColor(0, 51/255f, 102/255f, alpha);
			GLRenderer.drawQuad(getWidth() / 2 - camera.getX() - 16, getHeight() / 2 - camera.getY() -16, (getWidth() / 2 - camera.getX() - 16) + 32 * world.getWidth(), (getHeight() / 2 - camera.getY() - 16) + 32 * world.getHeight());
		}
		if(clock.time >= 360 && clock.time < 480 )
		{
			float r = (((clock.time - 360f)/10)*21.25f);
			float g = 51+(((clock.time - 360f)/10)*12.75f);
			float b = 102-((clock.time - 360f)/10)*8.5f;
			GLRenderer.setColor(r/255f, g/255f, b/255f, .15f);
			GLRenderer.drawQuad(getWidth() / 2 - camera.getX() - 16, getHeight() / 2 - camera.getY() -16, (getWidth() / 2 - camera.getX() - 16) + 32 * world.getWidth(), (getHeight() / 2 - camera.getY() - 16) + 32 * world.getHeight());
		}
		if(clock.time >= 480 && clock.time < 540 )
		{
			float alpha = (540 - clock.time)*.0025f;
			GLRenderer.setColor(1, 204/255f, 0, alpha);
			GLRenderer.drawQuad(getWidth() / 2 - camera.getX() - 16, getHeight() / 2 - camera.getY() -16, (getWidth() / 2 - camera.getX() - 16) + 32 * world.getWidth(), (getHeight() / 2 - camera.getY() - 16) + 32 * world.getHeight());
		}
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
		
		int y = 5;
		long tile = world.getTileData(mouseRow, mouseCol);
		TrueTypeFont font = this.font;
		
		font.drawString(5f, y, "BG Sprite ID: " + TileInfo.getBGSpriteID(tile));
		y += 5 + font.getHeight();
		font.drawString(5f, y, "FG Sprite ID: " + TileInfo.getFGSpriteID(tile));
		y += 5 + font.getHeight();
		font.drawString(5f, y, "Density: " + TileInfo.isDense(tile));
		y += 5 + font.getHeight();
		font.drawString(5f, y, "Metadata: 0x" + Integer.toHexString(TileInfo.getMetaData(tile)));
		y += 10 + font.getHeight();
		font.drawString(5f, y, "HeldItem: " + mob.getHeldItem().getType());
		
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
	TrueTypeFont font, font16;
	private int mouseRow, mouseCol;
	private int gameSpriteSelection;
	
}
