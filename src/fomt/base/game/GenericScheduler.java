package fomt.base.game;

public class GenericScheduler {

	/* --- Instance Methods (Interface) --- */
	
	public void onTick(long tick) {
		curTick = tick;
		
		Entry it = head.next;
		while (it != null) {
			int delta = (int)(tick - it.spawn);
			// if we have hit a section that is not ready, immediately break
			if (delta < it.remaining)
				break;
			it.fn.run();
			it = it.next;
		}
		
		// remove old entries
		head.next = it;
		if (it != null) {
			it.prev = head;		
		}
	}
	
	public void schedule(Runnable fn, int ticks) {
		Entry e = new Entry(curTick, ticks, fn);
		
		Entry old = head.next;
		if (old == null) {
			head.next = e;
			e.prev = head;
		} else {
			// find place to insert
			Entry it = head.next;
			while (it != null) {
				int r = (int)(curTick - it.spawn);
				if (r >= ticks)
					break;
			}
			
			it.prev.next = e;
			e.prev = it.prev;
			e.next = it;
			it.prev = e;
		}
	}
	
	/* --- Inner Types --- */
	
	private class Entry {
		public Entry(long spawn, int remaining, Runnable fn) {
			this.spawn = spawn;
			this.remaining = remaining;
			this.fn = fn;
		}
		
		public long spawn;
		public int remaining;
		public Runnable fn;
		public Entry prev, next;
	}
	
	/* --- Instance Fields --- */
	
	private long curTick;
	private Entry head = new Entry(0, 0, null);
	
	/* --- Test --- */
	
	public static void main(String[] args) {
		
		GenericScheduler gen = new GenericScheduler();
		
		gen.schedule(new Runnable() { public void run() { System.out.println("A"); } }, 5);
		
		gen.onTick(2);

		gen.schedule(new Runnable() { public void run() { System.out.println("B"); } }, 2);
		
		gen.onTick(4);
		gen.onTick(5);
		
	}
	
}
