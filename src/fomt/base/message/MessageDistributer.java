package fomt.base.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fomt.base.message.types.MsgTilePutDown;

public class MessageDistributer<T extends IMessage> {

	/* --- Instance Methods (Interface) --- */
	
	public void send(T msg, boolean immediate) {
		if (immediate) {
			for (IMessageListener<T> l : listListeners) {
				l.onMessage(msg);
			}
		} else {
			synchronized(listMsgQueue) {
				listMsgQueue.add(msg);		
			}
		}
	}
	
	public void distribute() {
		for (T msg : listMsgQueue) {
			for (IMessageListener<T> l : listListeners) {
				l.onMessage(msg);
			}
		}
		listMsgQueue.clear();
	}
	
	public void registerListener(IMessageListener<T> listener) {
		listListeners.add(listener);
	}

	/* --- Static Methods (Interface) --- */
	
	@SuppressWarnings("unchecked")
	public static <U extends IMessage> MessageDistributer<U> get(Class<U> clazz) {
		MessageDistributer<U> d = (MessageDistributer<U>)mapDist.get(clazz.getName());
		if (d == null) {
			d = new MessageDistributer<U>();
			mapDist.put(clazz.getName(), d);
		}
		return d;
	}
	
	/* --- Instance Fields --- */
	
	private List<T> listMsgQueue = new ArrayList<>();
	private List<IMessageListener<T>> listListeners = new LinkedList<>();

	/* --- Test --- */
	
	public static void main(String[] args) {
		
		MessageDistributer<MsgTilePutDown> d;
		
		d = MessageDistributer.get(MsgTilePutDown.class);
		d.send(null, false);
		
		d = MessageDistributer.get(MsgTilePutDown.class);
		d.registerListener(new IMessageListener<MsgTilePutDown>() {
			@Override
			public void onMessage(MsgTilePutDown msg) {
				System.out.println("Message Recieved!");
			}
		});
		
		d = MessageDistributer.get(MsgTilePutDown.class);
		d.distribute();
	
	}
	
	/* --- Static Fields --- */
	
	private static Map<String, MessageDistributer<?>> mapDist = new HashMap<>();;
	
}
