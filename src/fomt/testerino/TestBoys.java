package fomt.testerino;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestBoys {
	
	public static void main(String[] argv)
	{
		Map<Long, Object> b = new HashMap<>();
		
		int row = 20;
		int col = 40;
		
		long key = (long)row | ((long)col << 32L);
		
		b.put(key, "ASD BOYS");
		
		long a = System.nanoTime();
		
		Object obj = b.get(key);
		
		System.out.println(System.nanoTime() - a);
	
		System.out.println(obj);
		
	}
}
