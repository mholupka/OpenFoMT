package fomt.base.message;

import java.util.ArrayList;
import java.util.List;

public class MessageHandler {

	/* --- Instance Methods (Interface) --- */
	
	public <U extends IMessage> void register(Class<U> clazz) {
		listDist.add(MessageDistributer.get(clazz));
	}
	
	public void onTick() {
		for (MessageDistributer<?> d : listDist) {
			d.distribute();
		}
	}
	
	/* --- Instance Fields --- */
	
	private List<MessageDistributer<?>> listDist = new ArrayList<>();
	
}
