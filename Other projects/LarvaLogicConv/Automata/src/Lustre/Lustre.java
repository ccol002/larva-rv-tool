package Lustre;

import parsing.ParsingString;


public class Lustre {

	public static Logic logic = new Logic();
	
	public static void afterNode()
	{
		try{
		logic.addParse("node after(p:bool)returns(after_p:bool);" +
				" let" +
				" after_p = if (p) then true" +
				" else (false -> pre(after(p)));" +
				" tel");
		}
		catch(Exception ex)
		{ex.printStackTrace();}
	}
	
	public static void strictAfterNode()
	{
		try{
		logic.addParse("node strict_after(p:bool)returns(strict_after_p:bool); " +
				" let" +
				" strict_after_p = if (p) then false" +
				" else if (false->pre(p)) then true" +
				" else (false -> pre(strict_after(p)));" +
				" tel");
		
		}
		catch(Exception ex)
		{ex.printStackTrace();}
	}
	
	public static void starterNode()
	{
		try{
		logic.addParse("node starter(b:bool)returns(starter_b:bool); " +
				" let" +
				" starter_b = b and (not (strict_after(b)));" +
				" tel");
		
		}
		catch(Exception ex)
		{ex.printStackTrace();}
	}
	
	public static void alwaysSinceNode()
	{
		try{
		logic.addParse("node always_since(b,p:bool)returns(always_p_since_b:bool); " +
				" let" +
				" always_p_since_b = if (b) then p" +
				" else if (after(b)) then (p and pre(always_since(b,p)))" +
				" else true;" +
				" tel");
		
		}
		catch(Exception ex)
		{ex.printStackTrace();}
	}
	
	public static void ageNode()
	{
		try{
		logic.addParse("node age(b,p:bool)returns(age_p_b:int); " +
				" let" +
				" age_p_b = if (after(b) and p) then (0->pre(age(b,p)))+1" +
				" else 0;" +
				" tel");
		
		}
		catch(Exception ex)
		{ex.printStackTrace();}
	}
	
	public static void countNode()
	{
		try{
		logic.addParse("node count(b,p:bool)returns(count_p_b:int); " +
				" let" +
				" count_p_b = if (b and p) then 1" +
				" else if (b) then 0" +
				" else if (after(b) and p) then (0->pre(count(b,p)))+1" +
				" else if (after(b)) then (0->pre(count(b,p)))" +
				" else 0;" +
				" tel");
		
		}
		catch(Exception ex)
		{ex.printStackTrace();}
	}
	
	public static void ageNodeRT()
	{
		try{
		logic.addParse("node age(b:bool; rt_clock:int; p:bool)returns(age_p_b:int); " +
				" var " +
				"   temptime:int;" +
				" let" +
				"   temptime = if (not (after(b) and (false->pre p)) or b) then rt_clock " +
//				"              else if (b and (p)) then (0->pre(rt_clock))" +
//				"              else if (b) then (rt_clock)" +
				"	           else (0-> pre(temptime));" +
				"   age_p_b = rt_clock - temptime; " +
				" tel");
		
		}
		catch(Exception ex)
		{ex.printStackTrace();}
	}
	
	public static void firstNode()
	{
		try{
		logic.addParse("node first(b,p:bool)returns(first_p_b:bool); " +
				" var never_p:bool;" +
				" let" +
				" first_p_b = p and never_p;" +
				" never_p = if b then true" +
				"			else if (false -> pre(p)) then false" +
				"			else (false -> pre(never_p));" +
				" tel");
		
		}
		catch(Exception ex)
		{ex.printStackTrace();}
	}
	
	public static void sinceNodeRT()
	{
		try{
		logic.addParse("node since(b:bool; rt_clock:int; p:bool;)returns(p_since_b:int); " +
				" let" +
				"   p_since_b = if (b) then 0" +
				"   else if (strict_after(b) and false -> pre (p)) " +//we use strict after because of using pre(P)
				"     then (0-> pre(p_since_b)) + rt_clock - (0-> pre(rt_clock)) " +
				"     else (0-> pre (p_since_b));" +
				" tel");
		
		}
		catch(Exception ex)
		{ex.printStackTrace();}
	}

	public static void sinceNode()
	{
		try{
		logic.addParse("node since(b,p:bool)returns(p_since_b:int); " +
				" let" +
				" p_since_b = if (b) then (if (p) then 1 else 0)" +
				" else if (after(b)) then (pre(p_since_b))+" +
				"						(if (p) then 1 else 0)" +
				" else 0;" +
				" tel");
		
		}
		catch(Exception ex)
		{ex.printStackTrace();}
	}
	
	public String toString()
	{
		String s= "";
		for (Node n:Logic.nodes.values())
			s += n.toString()+"\r\n";
		return s;
	}
	
	public String original()
	{
		return Logic.inputText;
	}
	
	public static void parseFile(String filename)throws Exception
	{
		ParsingString ps = new ParsingString(filename);		
		logic.addParse(ps.toString());
	}
	
	public static String toLARVA()throws Exception
	{
		logic.flattenAll();
		
		//System.out.println(logic.toString());
		
		logic.sortAllFlattened();
		return logic.toLARVA();
	}
	
	public static void main(String[] args)throws Exception
	{
//		if (args.length == 1)
//			Lustre.parseFile(args[0]);
//		else
//			throw new Exception("Unknown argument list!!");
		afterNode();
		alwaysSinceNode();
		System.out.println(Lustre.toLARVA());
//		for (Node n: logic.flattenedNodes.values())
//			System.out.println(n+"\r\n\r\n");
	}
}
