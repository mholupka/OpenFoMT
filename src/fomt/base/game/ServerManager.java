package fomt.base.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import fomt.base.message.*;
import fomt.base.message.types.*;
import fomt.base.world.World;

public class ServerManager implements IGameManager {

	/* --- Instance Methods (Interface) --- */
	
	@Override
	public void setup() {
		MessageDistributer.get(MsgWorldCreated.class).registerListener(msgWorldCreated);
		MessageDistributer.get(MsgTilePutDown.class).registerListener(msgTilePutDownHandler);
		MessageDistributer.get(MsgPlayerUseItem.class).registerListener(msgPlayerInputHandler);
	}

	@Override
	public void onTick(long tick) {
		
	}
	
	/* --- Instance Fields --- */

	private IMessageListener<MsgWorldCreated> msgWorldCreated = new IMessageListener<MsgWorldCreated>() {
		@Override
		public void onMessage(MsgWorldCreated msg) {
			World world = msg.getWorld();
			
			int id;
			while (mapWorlds.get((id = random.nextInt())) != null) { }
			
			world.setID(id);
			mapWorlds.put(id, world);
		}
	};
	
	private IMessageListener<MsgTilePutDown> msgTilePutDownHandler = new IMessageListener<MsgTilePutDown>() {
		@Override
		public void onMessage(MsgTilePutDown msg) {
			int worldID = msg.getWorldID();
			World world = mapWorlds.get(worldID);
			
		}
	};
	
	private IMessageListener<MsgPlayerUseItem> msgPlayerInputHandler = new IMessageListener<MsgPlayerUseItem>() {
		@Override
		public void onMessage(MsgPlayerUseItem msg) {
			
		}
	};
	
	private Map<Integer, World> mapWorlds = new HashMap<>();
	private Random random = new Random();
	
}
