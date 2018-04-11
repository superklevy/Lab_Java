package test;

import java.io.*;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Madzhuga {
	
	static String[] m = new String[2];
	static Scanner scn;
	
	private static void openFile() {
	    try {
	         scn = new Scanner(new File("/Users/petr/eclipse-workspace/test/input.txt"));
	    }catch(Exception e){JOptionPane.showMessageDialog(null, "Файл не найден");}
	}
	
	private static void readFile() {
	    while(scn.hasNext()){	
	    	for(int i=0; i<m.length; i++){
	    		m[i] = scn.next();
	    		}
	   }
	}
	
	public static double dotProduct(double[] a, double[] b) {
		double sum = 0;
		
		for (int i = 0; i < a.length; i++) {
			sum += a[i] * b[i];
		}
		return sum;
	}
	
	public static void main (String args[]) {
		
		openFile();
		readFile();
		
		double[] a = {1,2};
		double[] b = {0,0};
		for(int i=0; i<m.length; i++){
			b[i] = Double.parseDouble(m[i]);
		}
		
		System.out.println(dotProduct(a, b));
		}

}