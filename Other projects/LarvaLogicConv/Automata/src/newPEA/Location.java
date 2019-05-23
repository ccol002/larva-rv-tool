package newPEA;

import java.util.ArrayList;

public class Location {

	public PowerSet powerSet;
	public Invariant invariant;
	public Invariant clockInv;
	public Invariant counterInv;
	public ArrayList<Transition> incoming = new ArrayList<Transition>();
	public ArrayList<Transition> outgoing = new ArrayList<Transition>();
	public Invariant initial;
	
	public boolean reached = false;
	public String events;
	public String eventName;
	public static int unique = -1;
	
	public boolean equals(Object location)
	{
		if (!(location instanceof Location))
			return false;
		else
		{
			Location l = (Location) location;
			return l.powerSet.equals(powerSet);
		}
	}
	
	public String toString()
	{
		return powerSet.toString();
	}
	
//	public String getEvents()
//	{
//		if (eventName == null)
//		{
//			ArrayList<String> eventlist = new ArrayList<String>();
//			invariant.getEvents(eventlist);
//			clockInv.getEvents(eventlist);
//			
//			events = "";
//			for (String s: eventlist)
//			events +=  s + "|";
//			
//			if (events.length() > 0)
//			{
//				events = events.substring(0, events.length()-1);
//				eventName = "event" + (++unique);
//			}
//			else
//			{
//				eventName = "";
//			}
//		}
//		return eventName;
//	}
	
	public ArrayList<String> getVariables()
	{
		ArrayList<String> vars = new ArrayList<String>();
		invariant.getVariables(vars);
		counterInv.getVariables(vars);
		return vars;
	}
	
	public ArrayList<ClockInv> getClocks()
	{
		ArrayList<ClockInv> vars = new ArrayList<ClockInv>();
		invariant.getClocks(vars);
		clockInv.getClocks(vars);
		return vars;
	}
	
	public ArrayList<CounterInv> getCounters()
	{
		ArrayList<CounterInv> vars = new ArrayList<CounterInv>();
		invariant.getCounters(vars);
		counterInv.getCounters(vars);
		return vars;
	}
 	
	public Bool getCondition(Resets resets, Increments incs) throws Exception
	{
		Conjunction conj = new Conjunction();
		conj.conjunction.add(invariant);
		if (clockInv.expression instanceof ClockInv && (resets == null || !resets.clocks.contains(((ClockInv)(clockInv.expression)).c)))
			conj.conjunction.add(clockInv);
		if (counterInv.expression instanceof CounterInv && (incs == null || !incs.counters.contains(((CounterInv)(counterInv.expression)).c)))
			conj.conjunction.add(counterInv);
		conj.simplify();
		return conj;
	}
	
	public String toNode()
	{
		return powerSet.toString() + "\\n" + invariant + "\\n" + clockInv + "\\n" + counterInv;
	}
	
	public String toAutomatonString()
	{
		String string = powerSet.toString() + "\r\n";
		if (initial != null)
			string += "|--" + initial + "-->" + this + "\r\n"; 
		for (Transition t : incoming)
			if (t.source.reached)
				string += t.toString()+"\r\n";
		string += "invariant: " + invariant + "\r\n";
		string += "clockinvariant: " + clockInv + "\r\n";
		string += "counterinvariant: " + counterInv + "\r\n";
		return string;
	}
	
}

