package larva;
public class ClockEvent implements Runnable{
long millis;
Clock c;
public ClockEvent(Clock c, long millis){
this.millis = millis;
this.c = c;
Thread t = new Thread(this);
 t.setDaemon(true);
 t.start();
}
public void run()	{
if (c._inst != null) c.event(millis);
}
}