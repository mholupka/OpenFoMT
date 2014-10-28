package fomt.utils.gl;

public interface ICamera {

	public void apply();
	public void unapply();
	public void up();
	public void down();
	public void left();
	public void right();
	
	public int getX(); 
	public int getY();

}
