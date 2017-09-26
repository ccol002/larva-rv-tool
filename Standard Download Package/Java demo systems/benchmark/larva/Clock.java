package larva;

import java.util.ArrayList;

public class Clock {

public String name;
public boolean thison = true;

ArrayList<Long> registered = new ArrayList<Long>();
	//ArrayList<Long> cycles = new ArrayList<Long>();


long starting;
boolean enabled = false;
boolean paused = false;
long durationPaused = 0;
long whenPaused;
	
public _callable _inst;



public Clock(_callable _inst, String name)
{
this._inst = _inst;
this.name = name;
}

public String toString() {
  return name;
}

public void off(){
synchronized (this){
thison = false;
}}

public void reset()
{
synchronized (this){
paused = false;
durationPaused = 0;
starting = System.currentTimeMillis();
enabled = true;
for (int i = 0; i < registered.size(); i++)
						registerGlobally(registered.get(i),starting);
					//no need to un-register the existing events which belong to this clock
					//this will be automatically ignored
}
}

public boolean verified(long starting)
{
synchronized (this){
//		System.out.println("Starting" + this.starting);
//		System.out.println("paused " + durationPaused);
if (thison && enabled && !paused)
return (this.starting + durationPaused) == starting;
else 
return false;
}}
	
public void pause()
{
synchronized (this){
    paused = true;
//		System.out.println("Paused>>" + System.currentTimeMillis());
    whenPaused = System.currentTimeMillis();
}}
	
//continue
public void resume()
{			
		//avoids deadlock..."resume" may be waiting for the "register" to complete
		//while holding "this object" as a lock while "verified" is also holding
		//"this object" as a lock and its caller is holding "lock" which is required by "register"		
		//note the order of obtained locks!!!
		//this order of locking is crucial when the method registers with the global clock!!
				synchronized (this){
                  long now = System.currentTimeMillis();
					durationPaused += now - whenPaused;	
					paused = false;//unpause here because this will effect the current time of the clock
//					System.out.println("Resumed>>" + System.currentTimeMillis());
					for (int i = 0; i < registered.size(); i++)
						if (registered.get(i) > current_long(now))//filter those events which occurred before pause
							RunningClock.register(registered.get(i), starting, durationPaused, this);
				}
	}

	public int compareTo(double seconds) {
synchronized (this){
		return compareToMillis((long)(seconds*1000));
	}}

	public int compareToMillis(long milli) {
synchronized (this){
return new Long(current_long()).compareTo(milli);
	}}

	public double current() {
synchronized (this){
		return current_long()/(double)1000;
	}}

public long current_long(long now) {
synchronized (this){
if (paused) return (whenPaused - starting - durationPaused);
else return (now - starting - durationPaused);
}}

public long current_long() {
synchronized (this){
if (paused) return (whenPaused - starting - durationPaused);
else return (System.currentTimeMillis() - starting - durationPaused);
}}
	
public void register(Long millis) 
{
synchronized (this){
registered.add(millis);
	}}
	
	public void registerGlobally(Long millis, Long current)
	{
		RunningClock.register(millis,current, this);
	}

//	public void registerCycle(long millis) {
//		cycles.add(millis);
//	}

	public void event(long millis){}

}