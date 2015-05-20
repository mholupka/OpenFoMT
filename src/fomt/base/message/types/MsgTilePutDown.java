package fomt.base.message.types;

import fomt.base.message.IMessage;

public class MsgTilePutDown implements IMessage {

	/* --- Constructors --- */
	
	public MsgTilePutDown(int worldID, int row, int col, int type) {
		this.worldID = worldID;
		this.row = row;
		this.col = col;
		this.type = type;
	}
	
	/* --- Instance Methods (Interface) --- */
	
	public int getWorldID() { return worldID; }
	public int getRow() { return row; }
	public int getColumn() { return col; }
	public int getType() { return type; }

	/* --- Instance Fields --- */
	
	private int worldID;
	private int row, col;
	private int type;
	
}
