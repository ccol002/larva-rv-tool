package newPEA;

import java.util.ArrayList;

public class Variable extends Bool {

	public String name;
	
	public Variable()
	{}
	
	public Variable(String name)
	{
		this.name= name;
	}
	
	public String toString()
	{
		if (unary != null)
			return "not " + name;
		else
			return name;
	}
	
	public Variable clone()
	{
		Variable v = new Variable();
		v.name = this.name;
		if (unary != null)
			v.unary = this.unary.clone();
		return v;
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
	
	public void simplify(){}
	
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
		if (!vars.contains(name))
			vars.add(name);
	}
	
	public void getClocks(ArrayList<ClockInv> vars)
	{}
}
