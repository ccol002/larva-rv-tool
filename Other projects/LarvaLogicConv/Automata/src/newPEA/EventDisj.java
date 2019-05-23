package newPEA;

import java.util.ArrayList;

import newPEA.Event;

public class EventDisj extends Bool {
	public ArrayList<Event> disjunction = new ArrayList<Event>();
	
	public EventDisj clone()
	{
		EventDisj d = new EventDisj();
		for (Event b : disjunction)
			d.disjunction.add(b.clone());
		if (unary != null)
			d.unary = unary.clone();
		return d;
	}
	
	public String toString()
	{
		String string = "(";
		if (disjunction.size()==0)
			return "true";
		
		for (Event b: disjunction)
			string += b + " or ";
		
		
		string = string.substring(0, string.length()-4) + ")";
		
		if (unary == null)
			return string;
		else
			return "not " + string;
	}
	
	public boolean tryRemoveOneUnary()
	{
		if (unary != null)
		{
			unary = null;
			return true;
		}
		else if (disjunction.size()==1)
			return disjunction.get(0).tryRemoveOneUnary();
		else
			return false;
	}
	
	public void simplify()  throws Exception
	{
		if (disjunction.size() == 0)
		{
			evaluate(true);
		}
	}
	
	public String toLARVA() throws Exception{
		if (unary != null)
			throw new Exception("I don't know how to handle not events");
		String string = "";
		for (Event e : disjunction)
			string += e.toLARVA() + " || ";
		return string.substring(0, string.length()-4);
	}
	
	public void getEvents(ArrayList<String> vars)
	{
		for (Event e : disjunction)
			vars.add("changeOf" + e.name);
	}
	
	public void getVariables(ArrayList<String> vars)
	{
		for (Event e : disjunction)
			vars.add(e.name);
	}
	
	public void getClocks(ArrayList<ClockInv> vars)
	{}
}
