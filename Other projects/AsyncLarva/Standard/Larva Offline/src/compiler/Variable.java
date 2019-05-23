package compiler;

import java.util.ArrayList;

public class Variable extends Compiler{

	Token type;
	Token name;
	public boolean set = false;//needed to decide whether this variable is "other" or a bound variable in an event collection

	public boolean subclasses = false;
	
	public ArrayList<Long> clockEvents = new ArrayList<Long>();
	public ArrayList<Long> clockCycleEvents = new ArrayList<Long>();
	
	public Variable(){}
	
	
	
	public boolean equals(Object o)
	{
		if (o instanceof Variable && ((Variable)o).name.equals(name))
			return true;
		else
			return false;
	}
	
	public int hashCode()
	{
		return name.hashCode();
	}
	
	public String toString()
	{
		return type + " " + name;
	}
	
	public String toJava()
	{
		return type + " " + name;
	}
	
	public String getVariableName()
	{
		if (type == null)
			return "*";
		else
			return name.toString();
	}
	
	public String getVariableType()
	{
		if (type == null)
			return "*";
		else if (subclasses)
			return type.toString()+"+";
		else
			return type.toString();
	}
}
