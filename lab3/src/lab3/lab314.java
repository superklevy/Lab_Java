package lab3;

import java.util.LinkedList;
import java.util.List;

public class lab314 {

	static final int PLACES = 10;
	 static In in = new In();

	 static class In {
		int i = 1;
		int wait = 0;
		

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}

		public int getWait() {
			return wait;
		}

		public void setWait(int wait) {
			this.wait = wait;
		}
	}

	static class Out implements Runnable {

		int people = 100;

		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				
				if (0 == people) {
					return;
				}
				synchronized (in) {
					people -= 1;
					in.setWait(in.getWait() + 1);
					if (PLACES == in.getWait()) {
						System.err.println("Тележка " + in.getI() + " ушла");
						in.setI(in.getI() + 1);
						in.setWait(0);
					}
				}
			}
		}

	}

	public static void main(String[] args) throws InterruptedException {
		
		Out x = new Out();
		
		x.run();
		
	}
}