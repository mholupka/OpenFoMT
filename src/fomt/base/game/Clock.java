package fomt.base.game;

public class Clock {
	
	// --- Constructors ---
	public Clock(int time)
	{
		this.time = time;
		this.ticks = 0;
	}
	
	// --- Class Methods ---
	public String timeToDisplay()
	{
		String displayTime;
		int hour = this.time/60;
		int minute = this.time - hour*60;
		if (minute == 0)
		{
			displayTime = String.valueOf(hour) + ":" + "00";
		}
		else
			displayTime = String.valueOf(hour) + ":" + String.valueOf(minute);
		
		return displayTime;
	}
	
	public void updateTime()
	{
		ticks += 1;
		if (ticks == 500)
		{
			if (time == 1440)
				time = 0;
			else
				time += 10;
			ticks = 0;
		}
		
	}
	
	// --- Instance Fields ---
	
		protected int time;
		protected int ticks;
}
