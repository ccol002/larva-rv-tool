package PEA;

import java.util.ArrayList;

public class BiImplication extends Bool{

	public Bool lhs;
	public Bool rhs;
	
//	public boolean equals(Object obj)
//	{
//		if (obj instanceof BiImplication)
//			return ((BiImplication)obj).lhs.equals(lhs) && ((BiImplication)obj).rhs.equals(rhs);
//		else
//			return false;
//	}
	
	public BiImplication clone()
	{
		BiImplication bi = new BiImplication();
		bi.lhs = lhs.clone();
		if (rhs != null)
			bi.rhs = rhs.clone();
		if (unary != null)
			bi.unary = unary.clone();
		return bi;
	}
	
	public String toString ()
	{
		if (evaluated())
			return evaluation.toString();
		else if (rhs == null)
		{
			if (unary == null)
				return lhs.toString();
			else 
				return "not (" + lhs.toString() + ")";
		}
		else
			return "("+lhs+ ") <=> (" + rhs +")";
	}
	
	public boolean tryRemoveOneUnary()
	{
		if (unary != null)
		{
			unary = null;
			return true;
		}
		else if (lhs != null && rhs==null)
			return lhs.tryRemoveOneUnary();
		else
			return false;
	}
	
	public void simplify()throws Exception 
	{
		
		
		lhs.simplify();
		if (rhs != null)
			rhs.simplify();
		
		if (lhs.equals(rhs))
		{
			evaluate(true);
			return;
		}
		
		if (lhs.evaluated() && !(lhs instanceof BoolValue))
			lhs = new BoolValue(lhs.evaluation);
		
		if (rhs != null && rhs.evaluated() && !(rhs instanceof BoolValue))
			rhs = new BoolValue(rhs.evaluation);
		
		if (rhs != null && lhs.evaluated() && rhs.evaluated())
		{
			if (lhs.evaluation.equals(rhs.evaluation))
				evaluate(true);	
			else
				evaluate(false);
			rhs = null;
			lhs = new BoolValue(evaluation);
		}
		
		if (rhs != null && lhs.evaluated() && lhs.evaluation)
		{
			lhs = rhs;
			rhs = null;
		}
		else if (rhs != null && rhs.evaluated() && rhs.evaluation)
		{
			rhs = null;
		}
		else if (rhs != null && lhs.evaluated() && !lhs.evaluation)
		{
			if (rhs.unary == null)
				unary = new Unary();
			else
				rhs.unary = null;
			lhs = rhs;
			rhs = null;
		}
		else if (rhs != null && rhs.evaluated() && !rhs.evaluation)
		{
			rhs = null;
			if (lhs.unary == null)
				unary = new Unary();
			else
				lhs.unary = null;
		}
		if (rhs == null && unary != null)
		{
			if (lhs.tryRemoveOneUnary())
				unary = null;
			
			if (lhs.evaluated())
				evaluate(lhs.evaluation);
		}
		
	}
	
	public String toLARVA() throws Exception{
		if (evaluated())
			return evaluation.toString();
		else if (rhs == null)
		{
			if (unary == null)
				return lhs.toLARVA();
			else 
				return "!(" + lhs.toLARVA() + ")";
		}
		else if (unary == null)
			return "(("+lhs.toLARVA()+ " && " + rhs.toLARVA() +") || (!("+lhs.toLARVA()+ ") && !(" + rhs.toLARVA() +")))";
		else
			return "!(("+lhs.toLARVA()+ " && " + rhs.toLARVA() +") || (!("+lhs.toLARVA()+ ") && !(" + rhs.toLARVA() +")))";
	}
	
	public void getEvents(ArrayList<String> events)
	{
		if (!evaluated() && !lhs.evaluated())
			lhs.getEvents(events);
		if (!evaluated() && !rhs.evaluated())
			rhs.getEvents(events);
	}
	
	public void getVariables(ArrayList<String> vars)
	{
		if (!evaluated() && !lhs.evaluated())
			lhs.getVariables(vars);
		if (!evaluated() && !rhs.evaluated())
			rhs.getVariables(vars);
	}
}
