package PEA;

import java.util.ArrayList;

import PEA.BoolOp.Op;

public class Conjunction extends Bool{

	public ArrayList<Bool> conjunction = new ArrayList<Bool>();
	
	public String toString()
	{
		if (evaluated())
			return evaluation.toString();
		if (conjunction.size()==0)
			return "true";
		String string = "";
		for (Bool b: conjunction)
			string += b + " and ";
		if (unary == null && conjunction.size() == 1)
			return string.substring(0, string.length()-5);
		else if (unary == null)
			return "(" + string.substring(0, string.length()-5) + ")"; 
		else
			return "not (" + string.substring(0, string.length()-5) + ")";
	}
	
	public Conjunction clone()
	{
		Conjunction d = new Conjunction();
		for (Bool b : conjunction)
			d.conjunction.add(b.clone());
		if (unary != null)
			d.unary = unary.clone();
		return d;
	}
	
	public boolean tryRemoveOneUnary()
	{
		if (unary != null)
		{
			unary = null;
			return true;
		}
		else if (conjunction.size()==1)
			return conjunction.get(0).tryRemoveOneUnary();
		else
			return false;
	}
	
	public void simplify()throws Exception
	{
		
		for (int i = 0; i < conjunction.size(); i++)
		{
			Bool b = conjunction.get(i);
			if (b.op != null && b.op.op.equals(Op.and))
			{
				conjunction.add(b.rhs);
				conjunction.set(i, b.lhs);
				b = b.lhs;
			}
			b.simplify();
			if (b.evaluated() && b.evaluation)
			{
				conjunction.remove(i);
				i--;
			}
			else if (b.evaluated())// && !b.evaluation)
			{
				evaluate(false);
				conjunction = new ArrayList<Bool>();
				conjunction.add(new BoolValue(false));
				break;
			} 
		}


			outer:for (int i = 0; i < conjunction.size(); i++)
				for (int j = i+1; j < conjunction.size(); j++)
			{
				if (conjunction.get(i).equals(conjunction.get(j)))
				{
					conjunction.remove(j);
					j--;
				}
				else if (conjunction.get(i).inverse(conjunction.get(j))){
					evaluate(false);
					conjunction = new ArrayList<Bool>();
					conjunction.add(new BoolValue(false));
					break outer;
				}
			}
		
		if (conjunction.size() == 1 && unary != null)
		{
			if (conjunction.get(0).tryRemoveOneUnary())
				unary = null;
		}
		else if (conjunction.size() == 0)
		{
			conjunction.add(new BoolValue(true));
			evaluate(true);
		}
	}
	
	public String toLARVA()  throws Exception{
		if (evaluated())
			return evaluation.toString();
		if (conjunction.size()==0)
			return "true";
		String string = "";
		for (Bool b: conjunction)
			string += b.toLARVA() + " && ";
		if (unary == null && conjunction.size() == 1)
			return string.substring(0, string.length()-4);
		else if (unary == null)
			return "(" + string.substring(0, string.length()-4) + ")"; 
		else
			return "!(" + string.substring(0, string.length()-4) + ")";
	}
	
	public void getEvents(ArrayList<String> events)
	{
		for (Bool b:conjunction)
			b.getEvents(events);
	}
	
	public void getVariables(ArrayList<String> vars)
	{
		for (Bool b: conjunction)
			b.getVariables(vars);
	}
	
	public void getClocks(ArrayList<ClockInv> vars)
	{
		for (Bool b: conjunction)
			if (b instanceof ClockInv)
				vars.add((ClockInv)b);
			else 
				b.getClocks(vars);
	}

}
	