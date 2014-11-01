package fomt.base.mob;

import org.lwjgl.input.Keyboard;

import fomt.base.input.GameInput;
import fomt.base.input.InputManager;
import fomt.base.item.Item;
import fomt.base.tile.TileInfo;
import fomt.base.tile.TileTable;
import fomt.base.tile.TileType;
import fomt.base.world.World;

public class PlayerController implements IMobController {

	// --- Constructor ---
	
	
	
	// --- Instance Methods ---
	
	public void update(World w, Mob m, InputManager input) {
		
		handleMovement(m, input);
		
		handleInteractions(w, m, input);
		
	}
	
	private void handleInteractions(World w, Mob m, InputManager input) {
		
		// todo properly...
		
		if (Keyboard.isKeyDown(Keyboard.KEY_J)) {
			Item item = m.getHeldItem();
			if (item != null) {
				item.onUse(w, m);		
			}
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
			
			int trgtRow, trgtCol;
			
			switch(m.facing) {
			case Direction.UP:
				trgtRow = m.row - 1;
				trgtCol = m.col;
				break;
			case Direction.DOWN:
				trgtRow = m.row + 1;
				trgtCol = m.col;
				break;
			case Direction.LEFT:
				trgtRow = m.row;
				trgtCol = m.col - 1;
				break;
			case Direction.RIGHT:
				trgtRow = m.row;
				trgtCol = m.col + 1;
				break;
			default:
				return;
			}
			
			int trgtFGSpriteID = TileInfo.getFGSpriteID(w.getTileData(trgtRow, trgtCol));
			
			TileType tile = TileTable.getTileType(trgtFGSpriteID);
			
			if (tile != null) {
				tile.onInteract(w, m, trgtRow, trgtCol);
			}
			
		}
		
	}
	
	private void handleMovement(Mob m, InputManager input) {
		
		int moveInput = input.peekMoveStack();
				
		switch (moveInput) {
		case GameInput.MOVE_UP:
			m.sy = -SPEED;
			m.sx = 0;
			m.facing = Direction.UP;
			break;
		case GameInput.MOVE_DOWN:
			m.sy = SPEED;
			m.sx = 0;
			m.facing = Direction.DOWN;
			break;
		case GameInput.MOVE_RIGHT:
			m.sx = SPEED;
			m.sy = 0;
			m.facing = Direction.RIGHT;
			break;
		case GameInput.MOVE_LEFT:
			m.sx = -SPEED;
			m.sy = 0;
			m.facing = Direction.LEFT;
			break;	
		default:
			m.sx = 0;
			m.sy = 0;
		}
		
	}
	
	// --- Static Fields ---
	
	protected static final float SPEED = 1.5f;
	
}
