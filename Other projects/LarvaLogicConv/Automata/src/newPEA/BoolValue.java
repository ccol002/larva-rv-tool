package newPEA;

import java.util.ArrayList;

public class BoolValue extends Bool {

	
	public Boolean value;
	
	public BoolValue()
	{}
	
	public BoolValue(Boolean b)
	{
		value = b;
		simplify();
	}
	
	public BoolValue clone()
	{
		BoolValue bv = new BoolValue(value);
		if (unary != null)
			bv.unary = unary.clone();
		return bv;
	}
	
	public boolean equals (Object obj)
	{
		if (obj instanceof BoolValue)
			return ((BoolValue)obj).value.equals(value);
		else
			return false;
	}
	
	public boolean tryRemoveOneUnary()
	{
		if (unary != null)
		{
			unary = null;
			return true;
		}
		else
		{
			value = !value;
			return true;
		}
	}
	
	public String toString()
	{
		return value.toString();
	}
	
	public void simplify()
	{
		if (unary != null)
			value = !value;
		evaluation = value;
	}
	
	public String toLARVA() {
		return toString();
	}
	
	public void getEvents(ArrayList<String> events)
	{}
	
	public void getVariables(ArrayList<String> vars)
	{}
	
	public void getClocks(ArrayList<ClockInv> vars)
	{}
}
