package fomt.base.game;

import fomt.base.game.Clock;

public class ClockTest {

	public static void main(String[] argv)
	{
		Clock c = new Clock(450);
		System.out.println(c.timeToDisplay());
	}
	
}
