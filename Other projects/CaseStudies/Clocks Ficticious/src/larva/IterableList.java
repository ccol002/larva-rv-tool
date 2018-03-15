package larva;

import java.util.ArrayList;

public class IterableList {

	ArrayList<Long> actual = new ArrayList<Long>();
	ArrayList<ArrayList<Long>> drs = new ArrayList<ArrayList<Long>>();
	ArrayList<ArrayList<Clock>> clks = new ArrayList<ArrayList<Clock>>();
	
	int iterator = 0;
	boolean keeping = false;
	
	ArrayList<Clock> clocks = null;
	Long l = null;
	ArrayList<Long> durations = null;
	
	public synchronized void add(Long l, Long d, Clock c)
	{			
			if (!actual.contains(l))
			{
				int i = 0;
				while (i < actual.size() && l > actual.get(i)) i++;
				
				actual.add(i,l);
				
				ArrayList<Long> ds = new ArrayList<Long>();
				ArrayList<Clock> cs = new ArrayList<Clock>();
				ds.add(d);
				cs.add(c);
				drs.add(i,ds);
				clks.add(i,cs);
			}
			else
			{
				clks.get(actual.indexOf(l)).add(c);
				drs.get(actual.indexOf(l)).add(d);
			}
	}
	
	//skip the next getNext() by returning the current values again
	public synchronized void keep()
	{
		synchronized (RunningClock.lock) 
		{
			keeping = true;
		}
	}
	
	public synchronized ArrayList<Clock> currentClocks()
	{
		return clocks;
	}
	
	public synchronized ArrayList<Long> currentDurations()
	{
		return durations;
	}
	
	public synchronized Long current()
	{
		return l;
	}
	
	public synchronized Long getNext()
	{
			if (keeping)
			{
				keeping = false;
				return l;
			}
			else if (actual.size() == 0)
				return null;
			else
			{
				l = actual.get(0);
				clocks = clks.get(0);
				durations = drs.get(0);
				actual.remove(0);
				clks.remove(0);
				drs.remove(0);
				return l;
			}
	}
	
	
	
}
