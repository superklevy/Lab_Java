package lab3;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Lab314 {

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
		
		int people = ThreadLocalRandom.current().nextInt(1, 50 + 1);
		
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
						System.out.println("Тележка №" + in.getI() + " ушла");
						in.setI(in.getI() + 1);
						in.setWait(0);
						try {
						Thread.sleep(100);
						} catch (InterruptedException e) {
						e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		List<Thread> t = new LinkedList<>();
		List<Out> b = new LinkedList<>();
		for (int i = 0; i < 10; i++) {
			Out b1 = new Out();
			Thread t1 = new Thread(b1);
			t1.start();
			t.add(t1);
			b.add(b1);
		}
	}
}