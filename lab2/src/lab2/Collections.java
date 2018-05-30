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
		
		x.add("first");
		x.add("second");
		x.add("third");
		x.add("first");
		
	    System.out.println(l.toString());
	    System.out.println(x.toString());
	    
		l.remove(2);
		x.remove(2);
		Iterator it = l.iterator();
		
		while (it.hasNext()) {
			System.err.println(it.next());
		}
		
	    System.out.println(l.toString());  
	    System.out.println(l.size());
	    System.out.println(l.get(0));
	    System.out.println(l.contains("first"));
	    System.out.println(l.contains("x"));
	    
	    System.out.println(x.toString());  
	    System.out.println(x.size());
	    System.out.println(x.get(0));
	    System.out.println(x.contains("first"));
	    System.out.println(x.contains("x"));
	}
	
	

}