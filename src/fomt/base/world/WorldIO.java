package fomt.base.world;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WorldIO {

	public static void save(World world) throws IOException {
	
		ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(world.getName() + ".world"));
		
		stream.writeObject(world);
	
		stream.close();
		
	}
	
	public static World load(String name) throws FileNotFoundException, IOException {
		
		ObjectInputStream stream = new ObjectInputStream(new FileInputStream(name + ".world"));
		
		World world = null;
		try {
			world = (World)stream.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return world;
		
	}
	
}
