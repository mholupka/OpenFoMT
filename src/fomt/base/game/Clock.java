package fomt.base.game;

public class Clock {
	
	// --- Constructors ---
	public Clock(int time)
	{
		this.time = time;
		this.day = 1;
		this.ticks = 1;
		this.season = 1;
		this.seasonName = "Spring";
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
	
	public void updateSeasonName()
	{
		if(season == 1)
			seasonName = "Spring";
		else if(season == 2)
			seasonName = "Summer";
		else if(season == 3) 
			seasonName = "Fall";
		else
			seasonName = "Winter";
	}
	
	public void updateTime()
	{
		ticks += 1;
		if (ticks == 500)
		{
			if (time == 1430)
			{
				time = 0;
				++day;
				if (day == 31)
				{
					++season;
					day = 1;
					if (season == 5)
						season = 1;
					updateSeasonName();
				}
			}
			else
				time += 10;
			ticks = 0;
		}
		
	}
	
	public Boolean dayChanged()
	{
		return ticks == 0 && time == 0;
	}
	
	// --- Instance Fields ---
	
		protected int time;
		protected int day;
		protected int ticks;
		protected int season;
		protected String seasonName;
}
