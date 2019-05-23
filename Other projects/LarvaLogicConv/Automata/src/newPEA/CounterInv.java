package newPEA;

import java.util.ArrayList;

public class CounterInv extends Bool{

	public Counter c;
	public Operator operator;
	public Object bound;
	
	public boolean equals (Object obj)
	{
		if (obj instanceof CounterInv)
			return ((CounterInv)obj).c.equals(c)
				&& ((CounterInv)obj).operator.equals(operator)
				&& ((CounterInv)obj).bound.equals(bound);
		else
			return false;
	}
	
	public CounterInv clone()
	{
		CounterInv ci = new CounterInv();
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
			return "cnt_" + c + ")"+ operator + bound;
		else
			return "!(cnt_" + c + ")" + operator + bound + ")";
	}
	
//	public void getEvents(ArrayList<String> vars)
//	{
//		vars.add(c+"AT" + bound.toString().substring(0,bound.toString().indexOf('.')));
//	}
	
	public void getVariables(ArrayList<String> vars)
	{
		if (!vars.contains(c.predicate.name))
		{
			vars.add("cnt_" + c.predicate.name);
			vars.add(c.predicate.name);
		}
	}
}
