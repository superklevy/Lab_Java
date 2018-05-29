package lab2;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Collections {
	
	public static void main(String[] args) {
		List<String> l = new ArrayList<String>();
		List<String> x = new java.util.ArrayList<String>();
		l.add("first");
		l.add("second");
		l.add("third");
		l.add("first");
		
	
	    System.out.println(l.toString());
		
		l.remove(2);
	    System.out.println(l.toString());
	    
	    System.out.println(l.size());
	    System.out.println(l.get(0));
	    System.out.println(l.contains("first"));
	    System.out.println(l.contains("x"));
		
	}

}