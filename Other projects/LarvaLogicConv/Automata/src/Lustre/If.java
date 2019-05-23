package Lustre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import parsing.Token;



public class If extends Primitive{

	Primitive condition;
	Primitive ifStat;
	Primitive elseStat;
	
	public int parse(int cnt, ArrayList<Token> tokens)throws Exception
	{
		cnt++;//if
		condition = new Expression();
		cnt = condition.parse(cnt,tokens);
		cnt++;//then
		ifStat = new Expression();
		cnt = ifStat.parse(cnt,tokens);
		if (tokens.get(cnt).is("else"))//else
		{
			cnt++;//else
			elseStat = new Primitive();
			cnt = elseStat.parse(cnt,tokens);
		}
		return cnt;
	}
	
	public E.Type getType()throws Exception
	{
		return ifStat.getType();
	}
	
	public String toString()
	{
		String text = "if ("+condition.toString()+") then (" + ifStat.toString()+")";
		if (elseStat != null)
			text += " else (" + elseStat.toString() + ")";
		return text;
	}
	
	public If clone()
	{
		If i = new If();
		i.condition = condition.clone();
		i.ifStat = ifStat.clone();
		if (elseStat != null)
			i.elseStat = elseStat.clone();
		return i;
	}
	
	public If replaceAndFlatten(Node node, HashMap<Variable, Primitive> replacements)throws Exception
	{
		condition = condition.replaceAndFlatten(node, replacements);
		ifStat = ifStat.replaceAndFlatten(node, replacements);
		if (elseStat != null)
			elseStat = elseStat.replaceAndFlatten(node, replacements);
		return this;
	}
	
	public Hashtable<Variable, Object> getVarList(Hashtable<Variable, Object> hashtable) {
		condition.getVarList(hashtable);
		ifStat.getVarList(hashtable);
		if (elseStat != null)
			elseStat.getVarList(hashtable);
		return hashtable;
	}
	
	public String toJava()throws Exception
	{
		String java = "";
		java += "("+condition.toJava()+")?(";
		java += ifStat.toJava()+")";
		if (elseStat != null)
			java += ":("+elseStat.toJava()+")";
		return java;
	}
}
