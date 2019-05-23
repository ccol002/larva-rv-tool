package newPEA;

import java.util.ArrayList;

import PEA.BoolOp.Op;

public class Disjunction extends Bool{

	public ArrayList<Bool> disjunction = new ArrayList<Bool>();
	
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
		for (int i = 0; i < disjunction.size(); i++)
		{
			Bool b = disjunction.get(i);
			if (b.op != null && b.op.op.equals(Op.or))
			{
				disjunction.add(b.rhs);
				disjunction.set(i, b.lhs);
				b = b.lhs;
			}
			b.simplify();
			if (b.evaluated() && b.evaluation)
			{
				evaluate(true);
				disjunction = new ArrayList<Bool>();
				disjunction.add(new BoolValue(true));
				break;
			}
			else if (b.evaluated())// && !b.evaluation)
			{
				disjunction.remove(i);
				i--;
			} 
		}


			outer:for (int i = 0; i < disjunction.size(); i++)
				for (int j = i+1; j < disjunction.size(); j++)
			{
				if (disjunction.get(i).equals(disjunction.get(j)))
				{
					disjunction.remove(j);
					j--;
				}
				else if (disjunction.get(i).inverse(disjunction.get(j))){
					evaluate(true);
					disjunction = new ArrayList<Bool>();
					disjunction.add(new BoolValue(true));
					break outer;
				}
			}

		if (disjunction.size() == 1 && unary != null)
		{
			if (disjunction.get(0).tryRemoveOneUnary())
				unary = null;
		}
		else if (disjunction.size() == 0)
		{
			disjunction.add(new BoolValue(false));
			evaluate(false);
		}

	}
	
	public Disjunction clone()
	{
		Disjunction d = new Disjunction();
		for (Bool b : disjunction)
			d.disjunction.add(b.clone());
		if (unary != null)
			d.unary = unary.clone();
		return d;
	}
	
	public String toString()
	{
		if (evaluated())
			return evaluation.toString();
		if (disjunction.size()==0)
			return "true";
		String string = "";
		for (Bool b: disjunction)
			string += b + " or ";
		if (unary == null && disjunction.size() == 1)
			return string.substring(0, string.length()-4);
		else if (unary == null)
			return "(" + string.substring(0, string.length()-4) + ")"; 
		else
			return "not " + string.substring(0, string.length()-4) + ")";
	}
	
	public String toLARVA()  throws Exception{
		if (evaluated())
			return evaluation.toString();
		if (disjunction.size()==0)
			return "true";
		String string = "";
		for (Bool b: disjunction)
			string += b.toLARVA() + " || ";
		if (unary == null && disjunction.size()==1)
			return string.substring(0, string.length()-4);
		else if (unary == null)
			return "(" + string.substring(0, string.length()-4) + ")"; 
		else
			return "!(" + string.substring(0, string.length()-4) + ")";
	}
	
//	public void getEvents(ArrayList<String> events)
//	{
//		for (Bool b: disjunction)
//			b.getEvents(events);
//	}
	
	public void getVariables(ArrayList<String> vars)
	{
		for (Bool b: disjunction)
			b.getVariables(vars);
	}
	
	public void getClocks(ArrayList<ClockInv> vars)
	{
		for (Bool b: disjunction)
			if (b instanceof ClockInv)
				vars.add((ClockInv)b);
			else 
				b.getClocks(vars);
	}

	public void getCounters(ArrayList<CounterInv> vars)
	{
		for (Bool b: disjunction)
			if (b instanceof CounterInv)
				vars.add((CounterInv)b);
			else 
				b.getCounters(vars);
	}
}
