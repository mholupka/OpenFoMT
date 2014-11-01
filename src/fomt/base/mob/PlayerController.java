package fomt.base.mob;

import org.lwjgl.input.Keyboard;

import fomt.base.input.GameInput;
import fomt.base.input.InputManager;
import fomt.base.item.Item;
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
		
	}
	
	private void handleMovement(Mob m, InputManager input) {
		
		int moveInput = input.peekMoveStack();
				
		switch (moveInput) {
		case GameInput.MOVE_UP:
			m.sy = -SPEED;
			m.sx = 0;
			break;
		case GameInput.MOVE_DOWN:
			m.sy = SPEED;
			m.sx = 0;
			break;
		case GameInput.MOVE_RIGHT:
			m.sx = SPEED;
			m.sy = 0;
			break;
		case GameInput.MOVE_LEFT:
			m.sx = -SPEED;
			m.sy = 0;
			break;	
		default:
			m.sx = 0;
			m.sy = 0;
		}
		
	}
	
	// --- Static Fields ---
	
	protected static final float SPEED = 1.5f;
	
}
