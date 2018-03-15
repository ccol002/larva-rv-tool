package larva;

import java.util.Iterator;
import java.util.TreeSet;

public class Clock implements Runnable{

	public static boolean on = true;
	TreeSet<Long> registered = new TreeSet<Long>();
	int count = 0;
	Object o = new Object();
	Object wait = new Object();
	long starting = System.currentTimeMillis();
	boolean enabled = false;
	
	public Clock()
	{}
	
	public static void initialize()
	{
		_cls_clock0.initialize();
	}
	
	public void reset()
	{
		try{
			enabled = false;
			synchronized (o) {
				o.notify();
			}
			starting = System.currentTimeMillis();
			//System.out.println("Reset: " + starting);

			Thread t = new Thread(this);
			t.setPriority(Thread.MAX_PRIORITY);
			while(count>0)
			{
				synchronized (wait) {
					wait.wait(10);
				}
			}
			enabled = true;
			t.start();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void register(long millis)
	{
		registered.add(millis);
	}
	
	public void unRegister(long millis)
	{
		registered.remove(millis);
	}
	
	public void unRegisterAll(long millis)
	{
		registered = new TreeSet<Long>();
	}

	public void run()
	{
		count++;
		try{
			Iterator<Long> iter = registered.iterator();
			while (on && enabled && iter.hasNext())
			{
				long nextMilli = iter.next();
				synchronized (o) {
					long cur = System.currentTimeMillis();
					long tmp = nextMilli - (cur-starting);
					if (on && enabled && tmp > 0) o.wait(tmp);
					if (on && enabled)
						new ClockEvent(nextMilli);
				}			
			}		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}	
		
		try{
			synchronized (wait) {
				wait.notify();
			}	
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		count--;
	}
}
