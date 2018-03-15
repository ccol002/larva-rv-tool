package larva;

import java.util.ArrayList;

public class Clock implements Runnable{

public static boolean on = true;
public boolean thison = true;
ArrayList<Long> registered = new ArrayList<Long>();
ArrayList<Long> cycles = new ArrayList<Long>();
int count = 0;
Object o = new Object();
Object wait = new Object();	
long starting = System.currentTimeMillis();
boolean enabled = false;
boolean paused = false;
long whenPaused = 0;
boolean resetc = false;
public _callable _inst;

public Clock(_callable _inst)
{this._inst = _inst;}

public void off(){thison = false;
  synchronized(o){
    o.notify();
  }
}

public void restart()
{
   for (long l:cycles)
      register(l);
	try{
		enabled = false;
		synchronized (o) {
			o.notify();
		}
	starting = System.currentTimeMillis();

	Thread t = new Thread(this);
	t.setPriority(Thread.MAX_PRIORITY);
	t.setDaemon(true);
	while(count>0)	{
		synchronized (wait) {
			wait.wait(10);
		}
	}
	enabled = true;
	t.start();
	}catch(Exception ex){
		ex.printStackTrace();
	}
}

public void reset()
{
   resetc = true;
   synchronized (o) {
      o.notify();
   }
}

//pause
public void pause()
{
	paused = true;
	synchronized (o) {
	o.notify();
	}
}

//continue
public void resume()
{
	paused = false;
	synchronized(o){
		o.notify();
	}
}

public int compareTo(double seconds){
	return new Long(current_long()).compareTo((long)(seconds*1000));
}

public int compareToMillis(long milli){
	return new Long(current_long()).compareTo(milli);
}

public double current(){
  if (paused) return (whenPaused - starting)/(double)1000;
  else return (System.currentTimeMillis() - starting)/(double)1000;
}

public long current_long(){
  if (paused) return (whenPaused - starting);
  else return (System.currentTimeMillis() - starting);
}

public void register(long millis)	{
int i = 0;
while (i <registered.size() && millis > registered.get(i))
	i++;
if (i < registered.size() && millis != registered.get(i))
registered.add(i, millis);
else if (i >= registered.size())
	registered.add(millis);
}

public void registerCycle(long millis)	{
	cycles.add(millis);
}
public void event(long millis){}
public void run()	{
	count++;
	try{
		int i = -1;
		while (thison && on && enabled){

//reset
		if (resetc){
			i = -1;
			starting = System.currentTimeMillis();
			resetc = false;
		}

//pause
			whenPaused = System.currentTimeMillis();
			synchronized (o) {
				while (enabled && thison && on && paused) o.wait();
			}
			starting += System.currentTimeMillis() - whenPaused;

			if (++i < registered.size())
			{

			//normal execution
			long nextMilli = registered.get(i);
			synchronized (o) {
				long cur = System.currentTimeMillis();
				long tmp = nextMilli - (cur-starting);
				if (thison && on && enabled && tmp > 0) o.wait(tmp);
				if (thison && on && enabled && starting+tmp <= System.currentTimeMillis()){
					new ClockEvent(this,nextMilli);
					for (long l:cycles){
						long cyc = nextMilli - nextMilli%l + l;
						if (!registered.contains(cyc))
							register(cyc);
					}
				} else i--;
			}
		    }
			else
			{
			   synchronized (o) {o.wait();}
			}
		}
	}
	catch(Exception ex)		{
		ex.printStackTrace();
	}	
	try{//notify the restart that the clock has stopped
		synchronized (wait) {
			wait.notify();
		}	
	}catch(Exception ex){
		ex.printStackTrace();
	}
	count--;
}
}