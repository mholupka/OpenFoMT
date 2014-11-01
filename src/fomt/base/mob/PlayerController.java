package fomt.base.mob;

import fomt.base.input.GameInput;
import fomt.base.input.InputManager;

public class PlayerController implements IMobController {

	// --- Constructor ---
	
	
	
	// --- Instance Methods ---
	
	public void update(Mob m, InputManager input) {
		
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
		
		if (m.sx == 0) {
			
		}
		
	}
	
	// --- Static Fields ---
	
	protected static final float SPEED = 1.5f;
	
}
