package fomt.base.message;

public interface IMessageListener<T extends IMessage>{
	public void onMessage(T msg);
}
