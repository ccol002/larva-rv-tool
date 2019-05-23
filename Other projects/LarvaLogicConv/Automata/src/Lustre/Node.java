package Lustre;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

import javax.management.monitor.Monitor;

import parsing.Token;
import parsing.Tokenizer;



public class Node {

	public static Node beingParsed; 
	public ArrayList<Assignment> statements;
	public ArrayList<Assignment> preStatements;
	public Hashtable<String,Variable> variables;
	public ArrayList<Variable> inputVars;
	public ArrayList<Variable> outputVars;
	public ArrayList<Assignment> initializations = new ArrayList<Assignment>();
	
	public Hashtable<Variable,Hashtable<Variable,Object>> varDependencies = new Hashtable<Variable, Hashtable<Variable,Object>>();
	
	public Token name;
	public boolean mainNode = true;
	
	public Node()
	{}
	
	public Node flatten(Node parent, HashMap<Variable, Primitive> replacements)throws Exception
	{
		Node flat = this.clone();
		for (int i = 0; i < statements.size(); i++)
		{
			Assignment a = flat.statements.get(i);
			a = a.replaceAndFlatten(flat, replacements);
		}
		for (int i = 0; i < preStatements.size(); i++)
		{
			Assignment a = flat.preStatements.get(i);
			a = a.replaceAndFlatten(flat, replacements);
		}
		for (int i = 0; i < initializations.size(); i++)
		{
			Assignment a = flat.initializations.get(i);
			a = a.replaceAndFlatten(flat, replacements);
		}
				
		return flat;
	}
	
	public Node clone()
	{
		Node node = new Node();
		
		node.name = this.name.clone();
		
		node.variables = new Hashtable<String, Variable>();
		for (Variable v : variables.values())
		{
			Variable clone = v.clone();
			node.variables.put(clone.var.text,clone);
		}
		
		node.inputVars = new ArrayList<Variable>();
		for (Variable v : inputVars)
			node.inputVars.add(v.clone());
		
		node.outputVars = new ArrayList<Variable>();
		for (Variable v : outputVars)
			node.outputVars.add(v.clone());
		
		node.statements = new ArrayList<Assignment>();
		for (Assignment a: statements)
			node.statements.add(a.clone());
		
		node.preStatements = new ArrayList<Assignment>();
		for (Assignment a: preStatements)
			node.preStatements.add(a.clone());
		
		node.initializations = new ArrayList<Assignment>();
		for (Assignment a: initializations)
			node.initializations.add(a.clone());
		
		return node;
	}
		
	public String getParams()
	{
		String params = "";
		for (Variable v:inputVars)
			params += v.toString()+":" + E.string(v.type) +";";
		return params.substring(0, params.length()-1);
	}
	
	public String getJavaParams()
	{
		String params = "";
		for (Variable v:inputVars)
			if (!v.getName().equals("_rt_clock"))
				params += E.typeToJava(v.type) + " " + v.toString() + ",";
		return params.substring(0, params.length()-1);
	}
	
	public String getOuts()
	{
		String params = "";
		for (Variable v:outputVars)
			params += v.toString()+":" + E.string(v.type) +";";
		return params.substring(0, params.length()-1);
	}
	
//	public String getLocals()
//	{
//		String params = "";
//		for (Variable v:)
//			params += v.toString()+":" + E.string(v.type) +";";
//		return params.substring(0, params.length()-1);
//	}
	
	public String getParamsNoTypes()
	{
		String params = "";
		for (Variable v:inputVars)
			params += v.toString()+",";
		if (params.length()>0)
			return params.substring(0, params.length()-1);
		else
			return "";
	}
	
	public String getSignature()
	{
		return this.name + " (" + getParamsNoTypes()+")";
	}
	
	public String getHeader()
	{
		return this.name + "(" + getParams() + ")returns(" + getOuts()+");";
	//			"var " + getLocals();
	}
	
	public boolean equals(Object obj)
	{
		if (obj instanceof Node)
			return name.text.equals(((Node)obj).name.text);
		else
			return false;
	}
	
	public int hashCode()
	{
		return name.text.hashCode();
	}
	
	public int parse(int cnt, ArrayList<Token> tokens)throws Exception
	{
		beingParsed = this;
		cnt = parseHeader(cnt,tokens);
		
		Logic.nodes.put(name.text, this);
		Logic.nodeDependencies.put(this, new Hashtable<Node, Object>());
		
		statements = new ArrayList<Assignment>();
		preStatements = new ArrayList<Assignment>();
		cnt = parseStatements(cnt,tokens);
		return cnt;
	}
	
	public int parseVariableList(int cnt, ArrayList<Token> tokens, E.Io io, String stopping)throws Exception
	{
		while (tokens.get(cnt).isNot(stopping))//")" or "let"
		{
			Hashtable<String,Variable> temp = new Hashtable<String,Variable>();
			while (tokens.get(cnt).isNot(":"))//:
			{
				Variable v = new Variable();
				v.var = tokens.get(cnt);
				temp.put(v.var.text, v);
				if (io == E.Io.IN)
					inputVars.add(v);
				if (io == E.Io.OUT)
					outputVars.add(v);
				cnt++;
				if (tokens.get(cnt).is(","))//,
					cnt++;//,
			}
			cnt++;//:
			E.Type variableType = null;
			if (tokens.get(cnt).is("int"))
				variableType = E.Type.INT;
			else if (tokens.get(cnt).is("bool"))
				variableType = E.Type.BOOL;
			else if (tokens.get(cnt).is("real"))
				variableType = E.Type.REAL;
			else throw new Exception("Wrong Type! " + Tokenizer.debugShow(tokens, cnt));
			
			cnt++;//type
			if (tokens.get(cnt).is(";"))//;
				cnt++;//;
			for (Enumeration<Variable> enrVars = temp.elements(); enrVars.hasMoreElements();)
			{
				Variable v = enrVars.nextElement();
				v.type = variableType;
				v.io = io;
			}
			
			variables.putAll(temp);
			
		}
		return cnt;
	}
	
	public int parseHeader(int cnt, ArrayList<Token> tokens)throws Exception
	{
		variables = new Hashtable<String, Variable>();
		inputVars = new ArrayList<Variable>();
		outputVars = new ArrayList<Variable>();
		if (tokens.get(cnt).is("node"))
			cnt++;//node
		else
			throw new Exception("node expected: " + Tokenizer.debugShow(tokens, cnt));
		
		if (tokens.get(cnt).isIdentifier())
		{
			name = tokens.get(cnt);
			cnt++;
		}
		else
			throw new Exception("Identifier expected: " + Tokenizer.debugShow(tokens, cnt));
		
		if (tokens.get(cnt).is("("))
		cnt++;//(
		else
			throw new Exception("( expected: " + Tokenizer.debugShow(tokens, cnt));
		
		cnt = parseVariableList(cnt, tokens, E.Io.IN, ")");//ends with )
		
		if (tokens.get(cnt).is(")"))
		cnt++;//)
		else
			throw new Exception(") expected: " + Tokenizer.debugShow(tokens, cnt));
		
		if (tokens.get(cnt).is("returns"))
		cnt++;//returns
		else
			throw new Exception("returns expected: " + Tokenizer.debugShow(tokens, cnt));
		
		if (tokens.get(cnt).is("("))
		cnt++;//(
		else
			throw new Exception("( expected: " + Tokenizer.debugShow(tokens, cnt));
		
		cnt = parseVariableList(cnt, tokens, E.Io.OUT, ")");//ends with )
		
		if (tokens.get(cnt).is(")"))
		cnt++;//)
		else
			throw new Exception(") expected: " + Tokenizer.debugShow(tokens, cnt));
		
		if (tokens.get(cnt).is(";"))
		cnt++;//;		
		else
			throw new Exception("; expected: " + Tokenizer.debugShow(tokens, cnt));
		
		
		if (tokens.get(cnt).is("var"))//var
		{
		cnt++;//var
		cnt = parseVariableList(cnt, tokens, E.Io.LOCAL, "let");//ends with let
		}
		return cnt;
	}
	
	public int parseStatements(int cnt, ArrayList<Token> tokens)throws Exception
	{
		if (tokens.get(cnt).is("let"))
			cnt++;//let
		else
			throw new Exception("let expected: " + Tokenizer.debugShow(tokens, cnt));
		while (tokens.get(cnt).isNot("tel"))
		{
			Assignment ass = new Assignment();
			cnt = ass.parse(cnt,tokens);
			statements.add(ass);
			if (tokens.get(cnt).is(";"))//;
				cnt++;
			//else throw new Exception("; Expected");
		}
		cnt++;//tel
		return cnt;
	}
	
	public String variableDeclarations()
	{
		String java = "";
		String inputs = "";
		String outputs = "";
		String locals = "";
		for (Enumeration<Variable> enrVars = variables.elements(); enrVars.hasMoreElements();)
		{
			Variable var = enrVars.nextElement();
			if (var.io == E.Io.IN)
				inputs += "\r\npublic "+E.typeToJava(var.type)+" "+var.var.text+";";
			else if (var.io == E.Io.LOCAL)
				locals += "\r\npublic "+E.typeToJava(var.type)+" "+var.var.text+";";
			else //if (var.io == E.Io.OUT)
				outputs += "\r\npublic "+E.typeToJava(var.type)+" "+var.var.text+";";
		}
		java += "\r\n\r\nclass InputsOf"+name.text+" { \r\n"+inputs+"\r\n}";
		java += "\r\n\r\nclass LocalsOf"+name.text+" { \r\n"+locals+"\r\n}";
		java += "\r\n\r\nclass OutputsOf"+name.text+" { \r\n"+outputs+"\r\n}";
	
		return java;
	}
	
	public E.Type getType()throws Exception
	{
		if (outputVars.size() == 1)
			return outputVars.get(0).getType();
		else
			throw new Exception("Unknown Type");
	}
	
	//also refreshes the variable list of the node...this has been lost during replacement of variables
	public void populateVarDeps()throws Exception
	{
		variables= new Hashtable<String, Variable>();
		for (Assignment a : statements)
		{
			if (varDependencies.containsKey(a.LHS))
				a.RHS.getVarList(varDependencies.get(a.LHS));
			else 
			{
				variables.put(a.LHS.getName(),a.LHS);
				varDependencies.put(a.LHS, new Hashtable<Variable, Object>());
				a.RHS.getVarList(varDependencies.get(a.LHS));
			}
			for (Variable v: varDependencies.get(a.LHS).keySet())
				if (!variables.containsKey(v.getName()))
				{
					variables.put(v.getName(),v);
					varDependencies.put(v, new Hashtable<Variable, Object>());
				}
		}
		for (Assignment a : preStatements)
			if (!variables.containsKey(((Variable)a.LHS).getName()))
				variables.put(((Variable)a.LHS).getName(),((Variable)a.LHS));
	}
	
	public void sort()throws Exception
	{		
		ArrayList<Variable> sorted = new ArrayList<Variable>();
		//Hashtable<Variable,Hashtable<Variable,Object>> copy = (Hashtable<Variable,Hashtable<Variable,Object>>)varDependencies.clone();
		
		HashSet<Variable> ready = new HashSet<Variable>();
		
		int varDepSize = varDependencies.size();
		//sort variables
		while (sorted.size() < varDepSize)
		{			
			for (Variable v : varDependencies.keySet())
				if (varDependencies.get(v).size() == 0)
					ready.add(v);
			
			if (ready.size() == 0)
				throw new Exception("Cycle Detected while sorting statements: Node " + this);
			else
			{//remove ready variables from varDependencies
				for (Variable v: ready)
				{
					sorted.add(v);
					varDependencies.remove(v);
					for (Hashtable<Variable,Object> h : varDependencies.values())
						if (h.containsKey(v))
							h.remove(v);
				}	
				ready.clear();
			}
		}
		
		ArrayList<Assignment> sortedStatements = new ArrayList<Assignment>();
		
		for (Variable v : sorted)
			for (int i = 0; i < statements.size(); i++)
			{
				Assignment a = statements.get(i);
				if (a.LHS.equals(v))
				{
					sortedStatements.add(a);
					statements.remove(i--);
				}
			}
		
		statements = sortedStatements;
	}

	public String toLARVA()throws Exception
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("IMPORTS {import main.*;} \r\n\r\nGLOBAL {");
		
		boolean realtime = false;
		if (variables.containsKey("_rt_clock"))
			realtime = true;
		
		sb.append("\r\n\r\nVARIABLES {");
		if (realtime)
			sb.append("\r\nClock _clock;");
		for (Variable v : variables.values())
			if ((!v.io.equals(E.Io.IN)) || v.getName().equals("_rt_clock"))
				sb.append("\r\n" + E.typeToJava(v.getType()) + " " + v.getName() + ";");
		sb.append("\r\n}");
		
		String params = getJavaParams();
		
				
			
//		sb.append("\r\n\r\nEVENTS {");
//				
//		for (Variable v:inputVars)
//			if (!v.getName().equals("_rt_clock"))
//		{
//			sb.append("\r\n" + v+ "_event(" + params + ") = {***.***()} where {");
//			for (Variable w:inputVars)
//			{
//				if (w.getName().equals("_rt_clock")) 
//					;//sb.append("_rt_clock = _clock.current();");
//				else if (w.getName().equals(v.getName()))
//					sb.append(" " + w.getName() + " = true;");
//				else
//					sb.append(" " + w.getName() + " = false;");
//			}
//			sb.append("}");
//		}
//		
//		sb.append("\r\ninitializationEvent() = {***.***()}" +
//				"\r\nperiodicEvent(" + params + ") = {");
//		
//		for (Variable v:inputVars)
//			if (!v.getName().equals("_rt_clock"))
//				sb.append(v + "_event|");
//		
//		if (sb.charAt(sb.length()-1) == '|')
//			sb.deleteCharAt(sb.length()-1);
//		
//		sb.append("}" +
//				"\r\n}\r\n}");
//		
		
		sb.append("\r\n\r\n EVENTS {"+
			"_b_event(boolean _b,boolean D,boolean Alarm) = {Main *.start()} "+ 
			"where { _b = true; D = true; Alarm = false;} "+

			"D_event(boolean _b,boolean D,boolean Alarm) = {DH2O *.on()} "+
			"where { _b = false; D = DH2O.status; Alarm = ALARM.status;} "+

			"Alarm_event(boolean _b,boolean D,boolean Alarm) = {ALARM *.on()} "+
			"where { _b = false; D = DH2O.status; Alarm = ALARM.status;} "+

			"initializationEvent() = {Main *.initialize()} "+

			"periodicEvent(boolean _b,boolean D,boolean Alarm) = {_b_event|D_event|Alarm_event} "+
			"}");
		
		
		
		sb.append("\r\n\r\nPROPERTY " + name.text + " {");
		//STATES
		sb.append("\r\n\r\nSTATES { " +
				"\r\n NORMAL { lustre }" +
				"\r\n STARTING { initialization }" +
				"\r\n}");
		
		//TRANSITIONS
		sb.append("\r\n\r\nTRANSITIONS { ");
		
		sb.append("\r\n initialization -> lustre [initializationEvent\\ \\");
		
		for (Assignment a : initializations)
			sb.append(a.toJava());
		sb.append("]");
		
		sb.append("\r\n lustre -> lustre [periodicEvent\\ \\");
		
		if (variables.containsKey("_rt_clock"))
				sb.append("_rt_clock = _clock.current_long();");
		for (Assignment a : statements)
			sb.append(a.toJava());
		for (Assignment a : preStatements)
			sb.append(a.toJava());
		
		sb.append("System.out.println(");
		for (Variable v: inputVars)
			sb.append("\" " + v.getName() + ": \" + " + v.getName() + " + \" \" + ");
		sb.append("\" " + /*outputVars.get(0).getName()*/"output" + ": \" + " + outputVars.get(0).getName() + " + \" \"");
		sb.append(");");
		
		sb.append("]");
		
		sb.append("\r\n}\r\n}\r\n}");
		
		return sb.toString();
	}
	
//	public String generateIf(Monitor m)
//	{
//		String java = "";
//		long encodedInps = 0;
//		//WE are assuming that all the inputs of the node are declared events (in the monitor's definitions)
//		//later on this has to change because of flattening and hence variable renaming...
//		//another intermediary stage will probably be required
//		for (int i = 0; i < inputVars.size(); i++)			
//			encodedInps = encodedInps | Packet.encode(m.defs.get(inputVars.get(i).var.text).id);
//		
//		java += "if ((encodedVars & "+encodedInps+") != 0){\r\n";
//		java += "updateInputsOf"+name.text+"(encodedVars);";
//		java += "\r\ntestingOf"+name.text+"(inputsOf"+name.text+", outputsOf"+name.text+", localsOf"+name.text+");\r\n}\r\n";
//		return java;
//	}
//	
//	public String generateUpdateOfInputs(Monitor m)
//	{
//		String java ="\r\npublic void updateInputsOf"+name.text+"(long encodedInputs){\r\n";
//		for (int i = 0; i < inputVars.size(); i++)
//			java += "inputsOf"+name.text+"."+ inputVars.get(i).var.text +" = false;\r\n"; 
//		for (int i = 0; i < inputVars.size(); i++)
//		{
//			java += "if ((encodedInputs & "+Packet.encode(m.defs.get(inputVars.get(i).var.text).id);
//			java += ") != 0) inputsOf"+name.text+"."+ inputVars.get(i).var.text +" = true;\r\n"; 
//		}
//		java += "}\r\n";
//		return java;
//	}
//	
	public String toString()
	{
		
		String text = getHeader() + "\r\nlet";
		
		for (Assignment a:statements)
			text += "\r\n" + a.toString();
		
		for (Assignment a:preStatements)
			text += "\r\n" + a.toString();
		
		return text+"\r\ntel\r\n";
	}
	
	public String toJava()throws Exception
	{
		
		String text = getHeader() + "\r\nlet";
		
		for (Assignment a:statements)
			text += "\r\n" + a.toJava();
		
		for (Assignment a:preStatements)
			text += "\r\n" + a.toJava();
		
		return text+"\r\ntel\r\n";
	}
	
	
//	public String toJava()throws Exception
//	{
//		String java = "";
//			
//		if (initializations.size()>0)
//		{
//			java+="\r\npublic void intializationOf"+name.text+"(InputsOf"+name.text+" in, OutputsOf"+name.text;
//			java += " out, LocalsOf"+name.text+" loc){\r\n";
//			for (int i =0; i < initializations.size(); i++)
//				java += initializations.get(i).toJava()+"\r\n";
//			java+="}\r\n";
//		}
//		
//		java += "\r\npublic void testingOf"+name.text+"(InputsOf"+name.text+" in, OutputsOf"+name.text;
//		java += " out, LocalsOf"+name.text+" loc){\r\n";
//		for (int i = 0; i < statements.size(); i++)
//		{
//			java += "\r\n"+statements.get(i).toJava();
//		}
//		for (int i = 0; i < preStatements.size(); i++)
//		{
//			java += "\r\n"+preStatements.get(i).toJava();
//		}
//		java += "\r\n}\r\n";
//		return java;
//	}
}
