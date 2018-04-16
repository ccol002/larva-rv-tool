package larva;


public class RunningClock{

	public static boolean on = true;
	public static IterableList events = new IterableList();
	public static Object lock = new Object();
		
	public static long now = 0;
	
	public static void start()
	{
//	 	Thread t = new Thread(new RunningClock());
//		t.setPriority(Thread.MAX_PRIORITY);
//		t.setDaemon(true);
//		t.start();
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
	
	public static void updateNow(long timeNow)
	{
		synchronized (lock) {
			now = timeNow;
			run();
		}
	}
	
	public static void run() {
		try{			
			boolean completed = false;
			
			while (!completed && events.getNext() != null) {
					
				long next = events.current();
				
				synchronized (lock) {
//					long cur = now;
//					long tmp = next - cur;
//					if (on && tmp > 0) 
//						lock.wait(tmp);
					
					long cur = now;
					
					if (on && next <= cur)
					{
						synchronized (events){
						for (int i = 0; i < events.currentClocks().size(); i++)
						{
							Clock c = events.currentClocks().get(i);
							long d = events.currentDurations().get(i);
//							System.out.println("Next" + next);
//							System.out.println("Dur" + d);
							if (c.verified(next-d))
								c.event(d);
						}
						}
					} 
					else
					{
						completed = true;
						events.keep();//event has not been triggered (because of a notificaiton)...try again
					}
			    }
			}
		}
		catch(Exception ex)		{
			ex.printStackTrace();
		}	
	}

}
