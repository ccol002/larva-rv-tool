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
	
	public String getSQLType()
	{
		if (type.text.equals("String")) return "varchar(255)";
		else if (type.text.equals("int")) return "int(10)";
		else if (type.text.equals("Integer")) return "int(10)";
		else if (type.text.equals("long")) return "bigint(20)";
		else if (type.text.equals("Long")) return "bigint(20)";
		else if (type.text.equals("double")) return "double(12,2)";
		else if (type.text.equals("Double")) return "doouble(12,2)";
		else if (type.text.equals("boolean")) return "bool";
		else if (type.text.equals("Boolean")) return "bool";
		else return null;
	}
	
	public String getResultSetType()
	{
		if (type.text.equals("String")) return "String";
		else if (type.text.equals("int")) return "Int";
		else if (type.text.equals("Integer")) return "Int";
		else if (type.text.equals("long")) return "Long";
		else if (type.text.equals("Long")) return "Long";
		else if (type.text.equals("double")) return "Double";
		else if (type.text.equals("Double")) return "Double";
		else if (type.text.equals("boolean")) return "Boolean";
		else if (type.text.equals("Boolean")) return "Boolean";
		else return null;
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
