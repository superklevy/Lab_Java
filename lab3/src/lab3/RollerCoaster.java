import java.math.*;

class RollerCoaster {
  public static int PASSENGER_NUM = 25;
  public static int CAR_NUM = 1; 
  public static int SEAT_AVAIL = 4; 
  public static void main(String[] args) {
    Monitor rcMon = new Monitor();

    Car theCar;
    Passenger aPassenger;

    Thread t1[] = new Thread[PASSENGER_NUM];
    Thread t2[] = new Thread[CAR_NUM];
   
    for (int i = 0; i < PASSENGER_NUM; i++) {
      aPassenger = new Passenger(i, rcMon);
      t1[i] = new Thread(aPassenger);
    }
    for (int i = 0; i < CAR_NUM; i++) {
      theCar = new Car(i, rcMon);
      t2[i] = new Thread(theCar);
    }

    for(int i = 0; i < PASSENGER_NUM; i++) {
      t1[i].start();
    }
    for(int i = 0; i < CAR_NUM; i++) {
      t2[i].start();
    }

    try {
      for (int i = 0; i < PASSENGER_NUM; i++) {
        t1[i].join();
      }
    } catch (InterruptedException e) {
    }

    try {
      for (int i = 0; i < CAR_NUM; i++) {
        t2[i].join();
      }
    } catch (InterruptedException e) {
    }
  }
} 

class Passenger implements Runnable {
  private int id;
  private Monitor passengerMon;
  public Passenger(int i, Monitor monitorIn) {
    id = i;
    this.passengerMon = monitorIn;
  }
  public void run() {
    for(int i=0; i<1; i++){ // Каждый пассажир ездит всего на одну поездку.
      try{
        Thread.sleep((int)( Math.random()*2000));
      }catch(InterruptedException e){
      }
      passengerMon.tryToGetOnCar(id);
    }
  }
} 

class Car implements Runnable {
  private int id;
  private Monitor carMon;
  public Car(int i, Monitor monitorIn) {
    id = i;
    this.carMon = monitorIn;
  }
  public void run() {
    while(true) {
      carMon.passengerGetOn(id);
      try{
        Thread.sleep((int)(Math.random()*2000));
      }catch(InterruptedException e){
      }
      carMon.passengerGetOff(id);
    }
  }
} 

class Monitor {
  private int i, line_length; // Количество пассажиров, ожидающих посадки
  private int seats_available = 0;
  boolean coaster_loading_passengers = false;
  boolean passengers_riding = true;

  private Object notifyPassenger = new Object(); // ввод / выход протокола обеспечивает взаимное исключение.
  private Object notifyCar = new Object(); 

  public void tryToGetOnCar(int i) {
    synchronized (notifyPassenger) {
      while (!seatAvailable()) {
        try {
          notifyPassenger.wait(); // Сообщите пассажиру, чтобы он подождал
          } catch (InterruptedException e){}
      }
    }
    System.out.println("Пассажир "+ i + " садится");
    synchronized (notifyCar) {notifyCar.notify();}
  }

  private synchronized boolean seatAvailable() {
    // Проверьте, доступно ли место для пассажира, который пытается попасть.
    if ((seats_available > 0)
        && (seats_available <= RollerCoaster.SEAT_AVAIL)
        && (!passengers_riding)) {
      seats_available--;
      return true;
    } else return false;
  }

  public void passengerGetOn(int i) {
    synchronized (notifyCar) {
      while (!carIsRunning()) {
        try {
          notifyCar.wait();
          } catch (InterruptedException e){}
      }
    }
    System.out.println("Тележка заполнена");
    synchronized(notifyPassenger) {notifyPassenger.notifyAll();}
  }

  private synchronized boolean carIsRunning() {
    // Проверьте, работает ли автомобиль
    if (seats_available == 0) {
      // Если нет места, автомобиль начинает работать и сбрасывает параметры.
      seats_available = RollerCoaster.SEAT_AVAIL;
      // Сбросить доступность места для следующей поездки
      coaster_loading_passengers = true; // Индикатор автомобиля работает.
      passengers_riding = true; //пассажиры едут в машине.
      return true;
    } else return false;
  }

  public void passengerGetOff(int i) {
    synchronized (this) {
      // Параметры сброса
      passengers_riding = false;
      coaster_loading_passengers = false;
    }
    synchronized(notifyPassenger) {notifyPassenger.notifyAll();}
  }
}