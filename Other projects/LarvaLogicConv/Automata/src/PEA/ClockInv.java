package PEA;

import java.util.ArrayList;

public class ClockInv extends Bool{

	public Clock c;
	public Operator operator;
	public Object bound;
	
	public boolean equals (Object obj)
	{
		if (obj instanceof ClockInv)
			return ((ClockInv)obj).c.equals(c)
				&& ((ClockInv)obj).operator.equals(operator)
				&& ((ClockInv)obj).bound.equals(bound);
		else
			return false;
	}
	
	public ClockInv clone()
	{
		ClockInv ci = new ClockInv();
		ci.c = c.clone();
		ci.op = op.clone();
		ci.bound = bound;
		if (unary != null)
			ci.unary = unary.clone();
		return ci;
	}
	
	
	public String toString()
	{
		if (unary == null)
			return c + " " + operator + " " + bound;
		else
			return "not " + c + " " + operator + " " + bound;
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
		if (unary == null)
			return c + ".compareToMillis((long)" + bound + ")"+ operator.toString() + "0";
		else
			return "!(" + c + ".compareToMillis((long)" + bound + ")" + operator.toString() + "0)";
	}
	
	public void getEvents(ArrayList<String> vars)
	{
		vars.add(c+"AT" + bound.toString().substring(0,bound.toString().indexOf('.')));
	}
	
	public void getVariables(ArrayList<String> vars)
	{
		//vars.add(name);
	}
}
