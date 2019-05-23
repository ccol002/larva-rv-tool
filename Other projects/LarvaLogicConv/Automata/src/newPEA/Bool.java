package newPEA;

import java.util.ArrayList;

import newPEA.BoolOp.Op;

public class Bool {

	public Unary unary;
	public Bool lhs;
	public BoolOp op;
	public Bool rhs;
	
	public Boolean evaluation;
	
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		else
			return toString().equals(obj.toString());
	}
	
	public boolean inverse(Object obj)
	{
		if (obj == null)
			return false;
		else
			return toString().equals("not (" + obj.toString() + ")")
				|| ("not (" + toString() + ")").equals(obj.toString());
	}
	
	public Bool clone()
	{
		Bool b = new Bool();
		b.lhs = lhs.clone();
		if (op != null)
			b.op = op.clone();
		if (rhs != null)
			b.rhs = rhs.clone();
		if (unary != null)
			b.unary = unary.clone();
		return b;
	}
	
//	public boolean equals(Object obj)
//	{
//		if (obj instanceof Bool)
//			if (((Bool)obj).lhs.equals(lhs)
//				&& ((rhs == null && ((Bool)obj).rhs == null)
//					|| ((Bool)obj).rhs.equals(rhs))
//				&&  ((rhs == null && ((Bool)obj).op == null)
//					|| ((Bool)obj).op.equals(op))
//				&& ((unary == null && ((Bool)obj).unary == null)
//						|| ((Bool)obj).unary.equals(unary)))
//			return true;
//			else
//				return false;
//		else
//			return false;
//	}
	
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
	
	public void simplify() throws Exception
	{
		if(op != null && op.op.equals(Op.and) && lhs instanceof Conjunction)
		{
			((Conjunction)lhs).conjunction.add(rhs);
			rhs = null;
			op = null;
		}
		else if(op != null && op.op.equals(Op.and) && rhs instanceof Conjunction)
		{
			((Conjunction)rhs).conjunction.add(lhs);
			lhs = rhs;
			rhs = null;
			op = null;
		}
		else if(op != null && op.op.equals(Op.or) && lhs instanceof Disjunction)
		{
			((Disjunction)lhs).disjunction.add(rhs);
			rhs = null;
			op = null;
		}
		else if(op != null && op.op.equals(Op.or) && lhs instanceof Disjunction)
		{
			((Disjunction)rhs).disjunction.add(lhs);
			lhs = rhs;
			rhs = null;
			op = null;
		}
		
		lhs.simplify();
		if (op != null)
		{
			if (lhs.evaluated() && lhs.evaluation && op.op.equals(Op.and))
			{
				lhs = rhs;
				rhs = null;
				op = null;
				lhs.simplify();
			}
			else if (lhs.evaluated() && lhs.evaluation && op.op.equals(Op.or))
			{
				evaluate(true);
				lhs = new BoolValue(true);
				rhs = null;
				op = null;
			}
			else if (lhs.evaluated() && !lhs.evaluation && op.op.equals(Op.or))
			{
				lhs = rhs;
				rhs = null;
				op = null;
				lhs.simplify();
			}
			else if (lhs.evaluated() && !lhs.evaluation && op.op.equals(Op.and))
			{
				evaluate(false);
				lhs = new BoolValue(false);
				rhs = null;
				op = null;
			}
			
			if (rhs != null)
			{
				rhs.simplify();
				if (rhs.evaluated() && rhs.evaluation && op.op.equals(Op.and))
				{
					rhs = null;
					op = null;
				}
				else if (rhs.evaluated() && rhs.evaluation && op.op.equals(Op.or))
				{
					evaluate(true);
					lhs = new BoolValue(true);
					rhs = null;
					op = null;
					
				}
				else if (rhs.evaluated() && !rhs.evaluation && op.op.equals(Op.or))
				{
					rhs = null;
					op = null;
				}
				else if (rhs.evaluated() && !rhs.evaluation && op.op.equals(Op.and))
				{
					evaluate(false);
					lhs = new BoolValue(false);
					rhs = null;
					op = null;
					
				}
			}
		}
		
				
		if (op == null && unary != null)
		{
			if (lhs.tryRemoveOneUnary())
				unary = null;
		}
			
		if (lhs.evaluated() && op == null)
			evaluate(lhs.evaluation);

		if (lhs.equals(rhs))
			rhs = null;
		else if (lhs.inverse(rhs))
			if (op.op.equals(Op.and))
			{
				lhs = new BoolValue(false);
				rhs= null;
				evaluate(false);
			}
			else {
				lhs = new BoolValue(true);
				rhs= null;
				evaluate(true);
			}
	}
	
	public String toString()
	{
		String string = "";
		if (unary != null)
			string += "not (";
		string += lhs.toString();
		if (op != null)
			string += " " + op + " ";
		if (rhs != null)
			string  += rhs.toString();
		if (unary != null)
			string += ")";
		return string;
	}
	
	public boolean evaluated()
	{
		return evaluation != null;
	}
	
	public void evaluate(boolean bool) throws Exception
	{
//		if (evaluation != null)
//			throw new Exception("Already Evaluated!!");
		if (unary == null)
			evaluation = bool;
		else
		{
//			unary = null;
			evaluation = !bool;
		}
	}
	
	public String toLARVA() throws Exception {
		if (evaluated())
			return evaluation.toString();
		else if (rhs == null)
		{
			if (unary == null)
				return lhs.toLARVA();
			else
				return "!("+lhs.toLARVA()+ ")";
		}
		else
		{
			String string = "";
			string += lhs.toLARVA();
			if (op.op.equals(BoolOp.Op.or))
				string += " || ";
			else if (op.op.equals(BoolOp.Op.and))
				string += " && ";
			
			string += rhs.toLARVA();
			
			if (unary == null)
				return string;
			else
				return "!(" + string + ")";

		}
	}
	
//	public void getEvents(ArrayList<String> events)
//	{
//		lhs.getEvents(events);
//		if (rhs != null)
//			rhs.getEvents(events);
//	}
	
	public void getVariables(ArrayList<String> vars)
	{
		lhs.getVariables(vars);
		if (rhs != null)
			rhs.getVariables(vars);
	}
	
	public void getClocks(ArrayList<ClockInv> vars)
	{
		if (lhs instanceof ClockInv)
			vars.add((ClockInv)lhs);
		else
			lhs.getClocks(vars);
		if (rhs != null)
			if (rhs instanceof ClockInv)
				vars.add((ClockInv)rhs);
			else
				rhs.getClocks(vars);
	}
	
	public void getCounters(ArrayList<CounterInv> vars)
	{
		if (lhs instanceof CounterInv)
			vars.add((CounterInv)lhs);
		else
			lhs.getCounters(vars);
		if (rhs != null)
			if (rhs instanceof CounterInv)
				vars.add((CounterInv)rhs);
			else
				rhs.getCounters(vars);
	}

}
