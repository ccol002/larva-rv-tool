package larva;

import java.util.ArrayList;
import java.sql.*;
import java.lang.reflect.Method;

public class Clock {

public String name;
public String className;
public static final int limit = 5;
public boolean thison = true;

ArrayList<Long> registered = new ArrayList<Long>();
	//ArrayList<Long> cycles = new ArrayList<Long>();


long starting;
boolean enabled = false;
boolean paused = false;
long durationPaused = 0;
long whenPaused;
public static long pks = 0;	
public long pk;	
	
public _callable _inst;


public Clock(){}

public Clock(_callable _inst, String name)
{
	pk = pks++;
  this._inst = _inst;
  this.name = name;
  this.className = _inst.getClass().getName();
}
public void updateInDB(boolean inst) { 
  Statement stat = null;
	try {
   if (inst) {
     Class c = Class.forName(className);
      Method m = c.getMethod("updateInDB",new Class[0]);
      m.invoke(c.cast(_inst));
   }
	 else {
     stat = _cls_script0._conn.createStatement();
	   String s = "update _clocks set enabled = \"" + ((enabled)?(1):(0)) + "\", name = \"" + name + "\", class_name = \"" + className + "\", inst = \"" + _inst.getPk() + "\", paused = \"" + ((paused)?(1):(0)) + "\", thison = \"" + ((thison)?(1):(0)) + "\", _starting = \"" + starting + "\", durationPaused = \"" + durationPaused + "\",whenPaused = \"" + whenPaused + "\",";
for (int i = 0; i < limit; i++)
		if (i < registered.size())
			s += "ev" + i + "= \"" + registered.get(i) + "\",";
		else 
			s += "ev" + i + "= null,";
		s = s.substring(0, s.length()-1);
 s += " where id = \"" + pk + "\";";
 stat.execute(s);
	 }
	} catch (Exception ex) {
	 ex.printStackTrace();
	}
	finally {
	    if (stat != null) {
	        try {
	            stat.close();
	        } catch (SQLException sqlEx) { } // ignore
	        stat = null;
	    }
	}
	}
public void insertInDB() { 
	Statement stat = null;
	try {
	 stat = _cls_script0._conn.createStatement();
	 String s = "insert into _clocks values ( \"" + pk + "\" , \"" + name + "\" , \"" + className + "\" , \"" + ((enabled)?(1):(0)) + "\", \"" + _inst.getPk() + "\", \"" + ((paused)?(1):(0)) + "\", \"" + ((thison)?(1):(0)) + "\", \"" + starting + "\", \"" + durationPaused + "\", \"" + whenPaused + "\",";
for (int i = 0; i < limit; i++)
		if (i < registered.size())
			s += "\"" + registered.get(i) + "\",";
		else 
			s += "null,";
	s = s.substring(0, s.length()-1);
  s += ");";
stat.execute(s);
	 } catch (Exception ex) {
	 ex.printStackTrace();
	}
	finally {
	    if (stat != null) {
	        try {
	            stat.close();
	        } catch (SQLException sqlEx) { } // ignore
	        stat = null;
	    }
	}
	}
 public static Clock loadFromDB( long pk, boolean inst ) { 
 	 Statement stat = null;
 	 ResultSet rs = null;
 	try {
 	  stat = _cls_script0._conn.createStatement();
 	  rs = stat.executeQuery("select * from _clocks where id = \"" + pk + "\" ;");
 	 if (rs.next()) {
 		 Clock temp = new Clock();
 		 temp.pk = pk;
 		 temp.name = rs.getString("name");
 		 temp.className = rs.getString("class_name");
 		 temp.enabled = rs.getBoolean("enabled");
       if (inst) {
 	       Class[] par = new Class[1];
 		   par[0] = long.class;
 		   Class c = Class.forName(temp.className);
 		   Method m = c.getMethod("loadByPkFromDB",par);
 		   temp._inst = (_callable)m.invoke(c,rs.getLong("inst"));
        }
 		 temp.paused = rs.getBoolean("paused");
 		 temp.thison = rs.getBoolean("thison");
 		 temp.starting = rs.getLong("_starting");
 		 temp.durationPaused = rs.getLong("durationPaused");
 		 temp.whenPaused = rs.getLong("whenPaused");	
 		for (int i = 0; i < limit; i++)
 		 {
 			 Long l = rs.getLong("ev" + i);
 			 if (l==null) break;
 			 temp.registered.add(l);
 		 }				
 		 return temp;
 	  } else return null;
  } catch (Exception ex) {
 	ex.printStackTrace();
	}
	finally {
		if (rs != null) {
	        try {
	            rs.close();
	        } catch (SQLException sqlEx) { } // ignore
	        rs = null;
	    }
	    if (stat != null) {
	        try {
	            stat.close();
	        } catch (SQLException sqlEx) { } // ignore
	        stat = null;
	    }	
	}
	return null;
	}

public synchronized void off(){
thison = false;
}

public void reset(long now)
{	   
synchronized (RunningClock.lock){
synchronized (RunningClock.events){
synchronized (this){
enabled = false;
paused = false;
durationPaused = 0;

starting = now;
enabled = true;
for (int i = 0; i < registered.size(); i++)
						registerGlobally(registered.get(i),starting);
					//no need to un-register the existing events which belong to this clock
					//this will be automatically ignored
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
	
public synchronized void register(Long millis) throws Exception 
{ if (registered.size()+1 > limit) throw new Exception("Limit of registered events exceeded!!");
registered.add(millis);
	}
	
	public void registerGlobally(Long millis, Long current)
	{
		RunningClock.register(millis,current, this);
	}

//	public void registerCycle(long millis) {
//		cycles.add(millis);
//	}

	public void event(long millis){}

}