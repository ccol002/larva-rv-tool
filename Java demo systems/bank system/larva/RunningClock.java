package larva;


public class RunningClock implements Runnable {

public static boolean on = false;
public static IterableList events;
public static Object lock;

public static void start()
{
 if (!on) {
on = true;
events = new IterableList();
lock = new Object();
 	Thread t = new Thread(new RunningClock());
	t.setPriority(Thread.MAX_PRIORITY);
	t.setDaemon(true);
	t.start();
}
}

	public static void register(Long l, Long current, Clock c)
	{
		RunningClock.events.add(l + current,l,c);
		synchronized (lock) {
			lock.notify();//in case the clock is idle
		}
	}
	
	public static void register(Long l, Long current, Long paused, Clock c)
	{
		RunningClock.events.add(l + current + paused,l,c);
		synchronized (lock) {
			lock.notify();//in case the clock is idle
			}
		}
	
	public void run() {
		try{ 	
			while (on)
          if (events.getNext() != null) {
					
				long next = events.current();
				
				long cur = System.currentTimeMillis();
				long tmp = next - cur;
				if (on && tmp > 0) 
			    	synchronized (lock) {
				    	lock.wait(tmp);
					}
				
				cur = System.currentTimeMillis();
				if (on && next <= cur)
					{
						events.remove();
						for (int i = 0; i < events.currentClocks().size(); i++)
						{
							Clock c = events.currentClocks().get(i);
							long d = events.currentDurations().get(i);
//									System.out.println("Next" + next);
//									System.out.println("Dur" + d);
							if (c.verified(next-d))
								c.event(d);
						}
					} 
				}
				else
				{
				   synchronized (lock) {lock.wait();}
				}
		}
		catch(Exception ex)		{
			ex.printStackTrace();
		}	
	}
}