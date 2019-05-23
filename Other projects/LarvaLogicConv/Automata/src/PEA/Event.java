package PEA;

import java.util.ArrayList;

public class Event extends Bool{

	public String name;
	
	public String toString()
	{
		String string = "";
		if (unary != null)
			string += unary.toString() + " ";
		return string + "event_" + name;
	}
	
	public Event clone()
	{
		Event ev = new Event();
		ev.name= name;
		ev.unary = unary.clone();
		return ev;
	}
	
	public boolean tryRemoveOneUnary()
	{
		if (unary != null)
		{
			unary = null;
			return true;
		}
		else
			return false;
	}
	
	public void simplify()
	{}
	
	public String toLARVA() {
		if (unary != null)
			return "!" + name;
		else
			return name;
	}
	
	public void getEvents(ArrayList<String> vars)
	{
		vars.add("changeOf" + name);
	}
	
	public void getVariables(ArrayList<String> vars)
	{
		vars.add(name);
	}
	
	public void getClocks(ArrayList<ClockInv> vars)
	{}
}
