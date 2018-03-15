package larva;

import java.util.ArrayList;

public class Clock {


	public boolean thison = true;

	ArrayList<Long> registered = new ArrayList<Long>();
	//ArrayList<Long> cycles = new ArrayList<Long>();


	long starting;
	boolean enabled = false;
	boolean paused = false;
	long durationPaused = 0;
	long whenPaused;
	
	public _callable _inst;



	public Clock(_callable _inst)
	{
		this._inst = _inst;
	}

	public synchronized void off(){
		thison = false;
	}

	//the reset should have an absolute quantity (not relative)
	//in other words the time of the reset should be given depending 
	//on the event which triggerred it
	public void reset(long now)
	{	   
		synchronized (RunningClock.lock){
			synchronized (RunningClock.events){
				synchronized (this){
					enabled = false;
					paused = false;
					durationPaused = 0;

					starting = now;
					for (int i = 0; i < registered.size(); i++)
						registerGlobally(registered.get(i),starting);
					//no need to un-register the existing events which belong to this clock
					//this will be automatically ignored
					enabled = true;
				}}}
	}

	public synchronized boolean verified(long starting)
	{
//		System.out.println("Starting" + this.starting);
//		System.out.println("paused " + durationPaused);
		if (thison && enabled && !paused)
			return (this.starting + durationPaused) == starting;
		else 
			return false;
	}
	
	public synchronized void pause(long now)
	{
		paused = true;
//		System.out.println("Paused>>" + System.currentTimeMillis());
		whenPaused = now;
	}
	
	//continue
	public void resume(long now)
	{			
		//avoids deadlock..."resume" may be waiting for the "register" to complete
		//while holding "this object" as a lock while "verified" is also holding
		//"this object" as a lock and its caller is holding "lock" which is required by "register"		
		//note the order of obtained locks!!!
		//this order of locking is crucial when the method registers with the global clock!!
		synchronized (RunningClock.lock){
			synchronized (RunningClock.events){
				synchronized (this){
					durationPaused += now - whenPaused;	
					paused = false;//unpause here because this will effect the current time of the clock
//					System.out.println("Resumed>>" + System.currentTimeMillis());
					for (int i = 0; i < registered.size(); i++)
						if (registered.get(i) > current_long(now))//filter those events which occurred before pause
							RunningClock.register(registered.get(i), starting, durationPaused, this);
				}}}
	}

	public synchronized int compareTo(long now, double seconds) {
		return compareToMillis(now, (long)(seconds*1000));
	}

	public synchronized int compareToMillis(long now, long milli) {
		return new Long(current_long(now)).compareTo(milli);
	}

	public synchronized double current(long now) {
		return current_long(now)/(double)1000;
	}

	public synchronized long current_long(long now) {
		if (paused) return (whenPaused - starting - durationPaused);
		else return (now - starting - durationPaused);
	}
	
	public synchronized void register(Long millis) 
	{
		registered.add(millis);
	}
	
	public void registerGlobally(Long millis, Long current)
	{
		RunningClock.register(millis,current, this);
	}

//	public void registerCycle(long millis) {
//		cycles.add(millis);
//	}

	public void event(long millis){System.out.println(this.toString()+" "+ System.currentTimeMillis());}

}