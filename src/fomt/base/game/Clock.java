package fomt.base.game;

public class Clock {
	
	// --- Constructors ---
	public Clock(int time)
	{
		this.time = time;
	}
	
	// --- Class Methods ---
	public String timeToDisplay()
	{
		String displayTime;
		int hour = this.time/60;
		int minute = this.time - hour*60;
		
		displayTime = String.valueOf(hour) + ":" + String.valueOf(minute);
		
		return displayTime;
	}
	
	// --- Instance Fields ---
	
		protected int time;
}
