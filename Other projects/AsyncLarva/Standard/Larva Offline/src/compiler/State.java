package compiler;

import java.util.ArrayList;


public class State {

	public enum Type {ACCEPTING, BAD, NORMAL, STARTING};
	
	
	public static int sid = -1;
	public int id;
	
	public Token name;	
	public ArrayList<Token> code;
	public Type type;
	
	public State()
	{
		id = ++sid;
	}
	
	public boolean equals(Object o)
	{
		if ((o instanceof State) && ((State)o).name.equals(name))
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
		return name.text;
	}
	
	public String format()
	{
		
		String formatted = "";
		if (type == Type.ACCEPTING)
			formatted += "(((SYSTEM REACHED AN ACCEPTED STATE)))  " + name;
		else if (type == Type.BAD)
			formatted += "!!!SYSTEM REACHED BAD STATE!!! "+name+" \"+new _BadStateException"+Global.name+"().toString()+\" ";
		else
			formatted += name;
		return formatted;
	}
}
