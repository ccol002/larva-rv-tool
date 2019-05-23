package larva;


public class RunningClock {

	public static boolean on = true;
	public static IterableList events = new IterableList();
	public static Object lock = new Object();
		
	public static long now = 0;
	public static long eventTime = 0;
	
	public static void start()
	{}

	public static void register(Long l, Long current, Clock c)
	{
		RunningClock.events.add(l + current,l,c.pk);
		synchronized (lock) {
			lock.notify();//in case the clock is idle
		}
	}
	
	public static void register(Long l, Long current, Long paused, Clock c)
	{
		RunningClock.events.add(l + current + paused,l,c.pk);
		synchronized (lock) {
			lock.notify();//in case the clock is idle
			}
		}
	
	public static void updateNow(long timeNow)
	{
		synchronized (lock) {
			now = timeNow;
          run();
		}
	}
	
	public static void run() {
		try{boolean completed = false;	
			while (!completed && events.getNext() != null) {
					
				long next = events.current();
				
					
					long cur = now;
					
					if (on && next <= cur)
					{
						events.remove();
						for (int i = 0; i < events.currentClocks().size(); i++)
						{
							Clock c = Clock.loadFromDB(events.currentClocks().get(i),true);
							long d = events.currentDurations().get(i);
//									System.out.println("Next" + next);
//									System.out.println("Dur" + d);
							if (c.verified(next-d))
                           {  
                              eventTime = next;
								c.event(d);
		                     }
						}
					} else 
                  {
                      completed = true;
                  }
			}
		}
		catch(Exception ex)		{
			ex.printStackTrace();
		}	
	}
}