package fomt.base.input;

import org.lwjgl.input.Keyboard;

public class InputManager {

	// --- Constructors ---
	
	public InputManager() {
		
		for (int i = 0; i < keyToGameInput.length; ++i) {
			keyToGameInput[i] = -1;
		}
		
		keyToGameInput[Keyboard.KEY_W] = GameInput.MOVE_UP;
		keyToGameInput[Keyboard.KEY_S] = GameInput.MOVE_DOWN;
		keyToGameInput[Keyboard.KEY_A] = GameInput.MOVE_LEFT;
		keyToGameInput[Keyboard.KEY_D] = GameInput.MOVE_RIGHT;
	
	}
	
	// --- Instance Methods ---
	
	protected void onMovementKey(int gameInput, boolean state) {
		if (state) {
			++moveStackLastIndex;
			moveStack[moveStackLastIndex] = gameInput;
			moveStack[4 + gameInput] = moveStackLastIndex;
		} else {
			--moveStackLastIndex;
			int index = moveStack[4 + gameInput];
			for (int i = index; i != 4; ++i) {
				moveStack[i] = moveStack[i + 1];
			}
			for (int i = 4; i < 8; ++i) {
				int oldIndex = moveStack[i];
				if (oldIndex >= index) {
					moveStack[i] = --oldIndex;
				}
			}
		}
	}
	
	protected void onDefaultKey(int gameInput, boolean state) {
		
	}
	
	public void update() {
		
		int key;
		int gameInput;
		while (Keyboard.next()) {
		
			key = Keyboard.getEventKey();
			gameInput = keyToGameInput[key];
			
			switch (gameInput) {
			case GameInput.MOVE_UP:
			case GameInput.MOVE_DOWN:
			case GameInput.MOVE_RIGHT:
			case GameInput.MOVE_LEFT:
				onMovementKey(gameInput, Keyboard.getEventKeyState());
				break;
			default:
				onDefaultKey(gameInput, Keyboard.getEventKeyState());	
			}
			
		}
		
	}
	
	public int peekMoveStack() {
		 return moveStackLastIndex >= 0 ? moveStack[moveStackLastIndex] : -1;
	}
	
	// --- Instance Fields ---
	
	protected int[] keyToGameInput = new int[512];
	protected int[] moveStack = new int[8];
	protected int moveStackLastIndex = -1;
	
}
