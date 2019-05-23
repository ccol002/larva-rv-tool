package newPEA;

import java.util.ArrayList;

public class Invariant extends Bool{

	public Bool expression;

	public Invariant clone()
	{
		Invariant inv = new Invariant();
		inv.expression = expression.clone();
		if (unary != null)
			inv.unary = unary.clone();
		return inv;
	}
	
	public String toString ()
	{
		if (unary != null)
			return "not " + expression.toString();
		else
			return expression.toString();
	}
	
	public void simplify() throws Exception
	{
		expression.simplify();
		evaluation = expression.evaluation;
	}

	public String toLARVA()  throws Exception{
		if (unary != null)
			return "! (" + expression.toLARVA() + ")";
		else
			return expression.toLARVA();
	}
	
//	public void getEvents(ArrayList<String> events)
//	{
//		expression.getEvents(events);
//	}
	
	public void getVariables(ArrayList<String> vars)
	{
		expression.getVariables(vars);
	}
	
	public void getClocks(ArrayList<ClockInv> vars)
	{
		expression.getClocks(vars);
	}

	public void getCounters(ArrayList<CounterInv> vars)
	{
		expression.getCounters(vars);
	}

}
