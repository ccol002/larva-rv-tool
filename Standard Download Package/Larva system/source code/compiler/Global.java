package compiler;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;



public class Global extends Compiler{

	public static int sid = -1;
	
	public int id;
	
	public static String name;
	
	Events events;
	
	LinkedHashMap<Variable, ArrayList<Token>> local = new LinkedHashMap<Variable, ArrayList<Token>>(); 
	LinkedHashMap<String, Variable> localVariables = new LinkedHashMap<String, Variable>();
	
	HashMap<String, Variable> contextVariables = new HashMap<String, Variable>();
	ArrayList<Variable> variables = new ArrayList<Variable>();//these are the variables for the FOREACH	
	ArrayList<String> equateMethods = new ArrayList<String>();//these are the equate methods for the FOREACH
	ArrayList<String> stringMethods = new ArrayList<String>();//these are the equate methods for the FOREACH
	ArrayList<Token> context = new ArrayList<Token>();
	
	LinkedHashMap<String, Property> logics = new LinkedHashMap<String, Property>(); 
	
	ArrayList<Foreach> foreaches = new ArrayList<Foreach>();	
	
	public Invariants invariants;
	
	public static Global root; 
	
	Global parent;//we need access to the parent because of nesting
	//when we nest FOREACH'es we need to keep track of the whole context starting from the Global
	
	public Global(ParsingString substring) throws ParseException{
		
		super(substring);
		id  = ++sid;
		if (id==0)
			root = this;
	}
		
	public boolean equals(Object o)
	{
		if (o instanceof Global && ((Global)o).id == id)
			return true;
		else
			return false;
	}
	
	public void parse() throws ParseException
	{
		commonParse();		
	}
		
	public void commonParse() throws ParseException
	{
		//System.out.println(string);
		invariants = new Invariants(parseWrapper("INVARIANTS",false), this);
		
		parseLocalVariables(parseWrapper("VARIABLES",false));
		
		events = new Events(parseWrapper("EVENTS",false), this);
		
		while (ps.hasMore("PROPERTY"))
		{
			Property l = new Property(parseWrapper("PROPERTY",false));
			l.parse(allParentsEvents(new HashMap<String, Trigger>()), this);
			logics.put(l.name, l);
		}
		while (ps.hasMore("FOREACH"))
		{
			foreaches.add(new Foreach(this,parseWrapper("FOREACH")));
		}
		if (ps.string.toString().trim().length() > 0)
			System.out.println("Warning: Not all script was successfully parsed!!...(possible wrong order of sections)");
	}
		
	public void parseLocalVariables(ParsingString string) throws ParseException
	{		
		Tokenizer tok = new Tokenizer(Tokenizer.EVENT_MODE);
		ArrayList<Token> tokens = tok.scan(string.toString());
		int cnt = 0;
		while (cnt < tokens.size())
		{
			Variable v = new Variable();
			v.type = new Token(tokens.get(cnt).text);
						
			int tempcnt = cnt;
			
			int angledBracketCount = 0;
			
			if (tokens.get(cnt+1).is("<"))
			{
				angledBracketCount = 1; 
				v.type.text += tokens.get(++cnt).text;
			}
			
			while (angledBracketCount > 0 && cnt+1 < tokens.size())
			{
				if (tokens.get(cnt+1).is("<"))
					angledBracketCount++;

				if (tokens.get(cnt+1).is(">"))
					angledBracketCount--;
				
			   v.type.text += tokens.get(++cnt).text;
			}
			if (cnt+1 == tokens.size())
				throw new ParseException("> expected: " + Tokenizer.debugShow(tokens,0));
			
			v.name = tokens.get(cnt+1);
			ArrayList<Token> init = Tokenizer.ending(tempcnt, ";", tokens);
			cnt = tempcnt + init.size()+1;//;
			if (!local.containsKey(v))
			{
				local.put(v,init);
				localVariables.put(v.getVariableName(), v);
			}
		}
	}
	
	public HashMap<String, Variable> allParentsVarsHash(HashMap<String, Variable> vars)
	{
		for (Variable v:variables)
			vars.put(v.name.text, v);
		
		if (parent != null) 
			return parent.allParentsVarsHash(vars);
		else
			return vars;
	}
	
	public ArrayList<Variable> allParentsVars(ArrayList<Variable> vars)
	{
		vars.addAll(variables);
		
		if (parent != null) 
			return parent.allParentsVars(vars);
		else
			return vars;
	}
	
	public HashMap<String, Trigger> allParentsEvents(HashMap<String, Trigger> events)
	{
		events.putAll(this.events.events);
		
		if (parent != null) 
			return parent.allParentsEvents(events);
		else
			return events;
	}
	
	public ArrayList<Global> getRecursiveForeaches(ArrayList<Global> arr)
	{
		arr.add(this);
		for (Global g:foreaches)
			g.getRecursiveForeaches(arr);
		return arr;
	}
	
	public static boolean search(Collection<Variable> c, String string)
	{
		for (Variable v: c)
			if (v.name.text.equals(string))
				return true;
		return false;
	}
	
	public static int searchRecEvents(String string, Global g)
	{
		if (search(g.events.variables.values(), string))
			return 0;
		else if (g.parent != null)
			return searchRecEvents(string, g.parent)+1;
		else
			return Integer.MIN_VALUE;
	}
	
	public static int searchRecLocals(String string, Global g)
	{
		if (search(g.local.keySet(), string))
			return 0;
		else if (g.parent != null)
			return searchRecLocals(string, g.parent)+1;
		else
			return Integer.MIN_VALUE;
	}
	
	public static int searchRecVars(String string, Global g)
	{
		if (search(g.variables, string))
			return 0;
		else if (g.parent != null)
			return searchRecVars(string, g.parent)+1;
		else
			return Integer.MIN_VALUE;
	}
	
	public static String searchVariable(Token t, Global g)
	{
		int level = searchRecEvents(t.text, g);
		if (level < 0)//check in the other lists!
			level = searchRecVars(t.text, g);
		if (level < 0)
			level = searchRecLocals(t.text, g);
		
		if (level > 0)
		{
			String s = "";
			for (int i = 0; i < level; i++)
				s+= "parent.";
			return s+t.text;
		}
		else 
		{
			if (!g.equals(root) && serachVariableInOneContext(root,t))//this search does not include context variables...these can never be ambiguous!!
				System.out.println("Warning: ambigious reference to variable: \""+ t + "\" (matching the innermost context...use \"::"+t+"\" to refer to the variable in global)");
			return t.text;
		}
		//else check if it is a method!!!!
		//throw new ParseException("Unknown variable in the current context: "+string);
	}
	
	public static boolean serachVariableInOneContext(Global current, Token t/*var name*/)
	{
		boolean found = false;
		for (Variable v:current.local.keySet())
			if (t.text.equals(v.name.text))
				found = true;
		if (!found)
		{
			for (Variable v:current.events.variables.values())
				if (t.text.equals(v.name.text))
					found = true;
		}
//		if (!found)
//		{//check if it is in the context
//			ArrayList<Token> list = new ArrayList<Token>();
//			list.add(t);
//			current = current.searchContext(list);
//			if (current != null)
//				for (Variable v:current.variables)
//					if (t.text.equals(v.name.text))
//						found = true;
//		}
		return found;
	}
	
	//a variable which need to be replaced can be in one of the following lists:
	//global.variables ...the variables which distinguish objects (available in the class instance)
	//global.local     ...local variables (available in the aspect instance)
	//global.events.variables ...variables available through events (available in the class instance)
	//however we also need to search the parents...
	//furthermore, we need to find the variables which need replacement
	public static String handleVariableReplacement(ArrayList<Token> tokens, Global g)throws ParseException
	{
		if (tokens.size() == 0)
			return "";
		else
		{
			//dissect into pieces
			int cnt= 0;
			ArrayList<ArrayList<Token>> strings = new ArrayList<ArrayList<Token>>();
			while (cnt < tokens.size())
			{
				ArrayList<Token> string = new ArrayList<Token>();
				
				string.add(tokens.get(cnt++));//this should be an identifier
				//or a ":" to indicate global
				//in this case we need to consume another token
				if (string.get(0).is("::"))
					string.add(tokens.get(cnt++));
				
				boolean start = true;
				while (cnt<tokens.size() && ((!tokens.get(cnt).isIdentifier() && tokens.get(cnt).isNot("::"))
						|| start))//group "strings"
				{
					
					string.add(tokens.get(cnt));
					//start remains true will consuming the sequence representing the context
					if (start && tokens.get(cnt).isNot("::") && tokens.get(cnt).isNot(",")
							&& !tokens.get(cnt).isIdentifier())
						start = false;
					//the main problem is that , can be used to represent contexts with more than one variable
					//the following handles correctly the case where automatic replacement is required
					//however this will not work correctly in case :: is used 
					//the problem occurs only in method call (variables separated by commas
					if (!string.contains(new Token(Tokenizer.get("::"),"::")) && tokens.get(cnt).is(","))
					{
						start = false;
						cnt++;
						break;
					}
					cnt++;
				}
				strings.add(string);
			}
			//handle colons...
			
			for (ArrayList<Token> al:strings)
			{
				int cnt2 = 0;
				if (al.size()>1 && al.contains(new Token(Tokenizer.get("::"),"::")))//:
				{
					if (al.get(0).is("::"))
						al.remove(0);
					
					//trying to find the context
					Global current = root;
					ArrayList<Token> context;
					while (cnt2 < al.lastIndexOf(new Token(Tokenizer.get("::"),"::")))
					{
						context = Tokenizer.ending(cnt2, "::", al);
						cnt2 += context.size()+1;
						current = current.searchContext(context);
						if (current == null) 
							throw new ParseException("Invalid Context : " + al);
					}
					
					//the context exists! but check that the variable exists in that context!!
					Token t = al.get(cnt2);
					if (t.isIdentifier())//identifier
					{											
						if (!serachVariableInOneContext(current, t))
							throw new ParseException("Invalid Context for the Specified Variable " + Tokenizer.debugShow(al, cnt2));
						Global parent = g;
						int depth = 0;
						while (!current.equals(parent))
						{
							parent = parent.parent;
							if (parent == null)
								throw new ParseException("Invalid Context: " + al);
							depth++;
						}

						al.set(cnt2, new Token(t.text));
						t = al.get(cnt2);
						for (int j =0; j < depth; j++)
							t.text = "parent." + t.text;
						for (int i=0; i < cnt2; i++)
							al.remove(0);
					}
					else
						throw new ParseException("Identifier Expected: " + Tokenizer.debugShow(al, cnt2));
				}
				else 
				{
					for (int i = 0; i < al.size(); i++)
						if (al.get(i).isIdentifier())//identifier//no colons...search automatically!
						{
							Token t = al.get(i);
							al.set(i, new Token(searchVariable(t, g)));
						}
					
				}
//				else 
//				{
//					//do nothing...we are trying to replace identifiers....
//				}
			}
			StringBuilder sb = new StringBuilder();
			for (ArrayList<Token> al:strings)
				sb.append(Tokenizer.showStats(al));
			
			return sb.toString();
		}
	}
	
	public void createFileForEach()throws ParseException
	{
		//generate 2 aspects...one with the pertarget
		//and an object with the FOREACH
		
		
		//******************************************************creating first aspect
		//this interacts directly with the system to be monitored
		StringBuilder as1 = new StringBuilder("package aspects;\r\n"+imports
				+"\r\nimport larva.*;"
				+"\r\npublic aspect _asp_"+name+id+" {");
		
		if (parent == null)
			as1.append("\r\n\r\npublic static Object lock = new Object();");
		
		events.appendJava(as1,this);
		as1.append("\r\n}");
		
		
		//***********************************************Creating the class
		
		StringBuilder cl = new StringBuilder("package larva;\r\n\r\n"+imports+"\r\nimport java.util.LinkedHashMap;\r\nimport java.io.PrintWriter;");
		cl.append("\r\n\r\npublic class _cls_"+name+id+" implements _callable{");
		
		if (this == root)
		{
			cl.append("\r\n\r\npublic static PrintWriter pw; " +
					"\r\npublic static _cls_" + this.name + this.id + " root;");
		}
		
		for (Variable v:localVariables.values())
			if (v.getVariableType().equals("Channel"))
			{
				cl.append("\r\npublic static " + v.getVariableType()+ " " + v.getVariableName()+" = new Channel();");
			}
		
		cl.append("\r\n\r\npublic static LinkedHashMap<_cls_"+name+id+",_cls_"+name+id+"> _cls_"+name+id
				+ "_instances = new LinkedHashMap<_cls_"+name+id+",_cls_"+name+id+">();");
		
		
		//////////////////////////////////////////////////////////////////////////
		// initialization of root
		
		if (this.id == 0)
		{
			cl.append("\r\nstatic{\r\ntry{");

			cl.append("\r\nRunningClock.start();");
			
			cl.append("\r\npw = new PrintWriter(\""+Compiler.outputDir.replace("\\", "\\\\")
					+"/output_"+name+".txt\");\r\n");

			cl.append("\r\nroot = new _cls_" + this.name + this.id + "();" +
					"\r\n_cls_" + this.name + this.id + "_instances.put(root, root);");

			cl.append("\r\n  root.initialisation();");
			
//			for (Variable v:localVariables.values())
//				if (v.getVariableType().equals("Clock"))
//					cl.append("\r\nroot." + v.getVariableName() + ".reset();");
			
			cl.append("\r\n}catch(Exception ex)\r\n{ex.printStackTrace();}\r\n}");
		}
		
		if (parent != null)
			cl.append("\r\n\r\n_cls_"+name + parent.id + " parent;");
		else 
			cl.append("\r\n\r\n_cls_"+name + id + " parent; //to remain null - this class does not have a parent!");
		
	
		
		
		///////////////////////////////////////////////////////////////////////////////
		//variables for all the events...
		
		for (Variable v: events.variables.values())
			if (v.type != null)
				cl.append("\r\npublic static " + v.type + " " + v.name+ ";");
	
		
		
		
		//these are the variables which constitute the context E.G. transaction
		for (Variable v: variables)
			cl.append("\r\npublic " + v.type + " " + v.name+ ";");
		
		
		cl.append("\r\nint no_automata = "+logics.size()+";");
		
				
		for (Variable v:local.keySet())
		{
			if (!v.getVariableType().equals("Clock") && !v.getVariableType().equals("Channel"))
				cl.append("\r\n public " + Tokenizer.showStats(local.get(v))+";");
			else if (v.getVariableType().equals("Clock"))
			{
				cl.append("\r\npublic " + v.getVariableType() + " " + 
						v.getVariableName() + " = new " + v.getVariableType() + "(this,\""+v.getVariableName()+"\");");
			}
		}
		
		for (Invariant inv: invariants.invariants.values())
		{
			if (!inv.initialization)
			{
				cl.append("\r\npublic boolean " + inv.name + "_enb = false;");
				cl.append("\r\npublic " + inv.returnType + " " + inv.name + "_temp;" );
			}
			else {
				cl.append("\r\npublic boolean " + inv.name + "_enb = true;");
				cl.append("\r\npublic " + inv.returnType + " " + inv.name + "_temp = "+ Tokenizer.showStats(inv.call) +";" );
			}
		}
		
		cl.append("\r\n\r\npublic static void initialize(){}");
		
		
		////////////////////////////////////////////////////////////////////////////
		//CONSTRUCTOR
		
		cl.append("\r\n//inheritance could not be used because of the automatic call to super()");
		cl.append("\r\n//when the constructor is called...we need to keep the SAME parent if this exists!");
		cl.append("\r\n\r\npublic _cls_"+name+id+"( ");
		for (Variable v: allParentsVars(new ArrayList<Variable>()))
			cl.append(v.type + " " + v.name+ ",");
		cl.delete(cl.length()-1, cl.length());//remove last comma
		cl.append(") {");
		if (parent != null)
		{
			cl.append("\r\nparent = _cls_"+name+parent.id+"._get_cls_"+name+parent.id+"_inst( ");
			for (Variable v: parent.allParentsVars(new ArrayList<Variable>()))
				cl.append(v.name+ ",");
			cl.delete(cl.length()-1, cl.length());//remove last comma
			cl.append(");");
		}
		for (Variable v:localVariables.values())
			if (v.getVariableType().equals("Clock"))
				{
					for (Long l:v.clockEvents)
						cl.append("\r\n" + v.getVariableName() + ".register(" + l + "l);");
//					for (Long l:v.clockCycleEvents)
//						cl.append("\r\n" + v.getVariableName() + ".registerCycle(" + l + ");");

				}
		
		for (Variable v: variables)
			cl.append("\r\nthis."+v.name+" = "+v.name+";");
		
		cl.append("\r\n}");
		
		
		
		///////////////////////// initialisation
		
		cl.append("\r\n\r\npublic void initialisation() {");
		
		for (Property p : logics.values())
		{
			ArrayList<Token> code = p.states.starting.get(0).code;
			if (code != null)
				cl.append("\r\n\r\n" + Global.redirectOutput(Global.handleVariableReplacement(code,this)));
		}
		
		for (Variable v:localVariables.values())
			if (v.getVariableType().equals("Clock"))
				cl.append("\r\n   " + v.getVariableName() + ".reset();");
		
		cl.append("\r\n}");

		
		
		///////////////////////////////////////////////////////////////////////////
		//call to constructor
		
		cl.append("\r\n\r\npublic static _cls_"+name+id+" _get_cls_"+name+id+"_inst( ");
		for (Variable v: allParentsVars(new ArrayList<Variable>()))
			cl.append(v.type + " " + v.name+ ",");
		cl.delete(cl.length()-1, cl.length());//remove last comma
		cl.append(") { synchronized(_cls_" + this.name + this.id + "_instances){");
		

		if (id != 0)
		{
			cl.append("\r\n_cls_"+name+id+" _inst = new _cls_"+name+id+"( ");
			for (Variable v: allParentsVars(new ArrayList<Variable>()))
				cl.append(v.name+ ",");
			cl.delete(cl.length()-1, cl.length());//remove last comma
			cl.append(");");
			cl.append("\r\nif (_cls_"+name+id+"_instances.containsKey(_inst))");
			cl.append("\r\n{" +
					"\r\n_cls_"+name+id+" tmp = _cls_"+name+id+"_instances.get(_inst);");

			for (Invariant inv : invariants.invariants.values())
			{
				cl.append("\r\nif ( tmp." + inv + "_enb && !tmp."+inv+"_temp.equals(" + Tokenizer.showStats(inv.call) +")){"+ 
						"\r\n  _cls_"+name+"0.pw.println(\" Invariant Check: "+inv+" Failed: " + Tokenizer.showStats(inv.call) + "!!: \" + " +
						"new _BadStateException"+name+"().toString());" +
						"\r\n  _cls_" + name + "0.pw.flush();");

				cl.append("\r\ntmp." + inv + "_temp = " + Tokenizer.showStats(inv.call) + ";\r\n}");
			}

			cl.append("\r\n return _cls_"+name+id+"_instances.get(_inst);" +
			"\r\n}");
			cl.append("\r\nelse\r\n{");

			cl.append("\r\n _inst.initialisation();");

			cl.append("\r\n _cls_"+name+id+"_instances.put(_inst,_inst);" +
					"\r\n return _inst;" +
			"\r\n}");
		}
		else
		{
			for (Invariant inv : invariants.invariants.values())
			{
				cl.append("\r\nif ( root." + inv + "_enb && !root."+inv+"_temp.equals(" + Tokenizer.showStats(inv.call) +")){"+ 
								"\r\n  _cls_"+name+"0.pw.println(\" Invariant Check: "+inv+" Failed: " + Tokenizer.showStats(inv.call) + "!!: \" + " +
								"new _BadStateException"+name+"().toString());" +
								"\r\n  _cls_" + name + "0.pw.flush();");
				
				cl.append("\r\nroot." + inv + "_temp = " + Tokenizer.showStats(inv.call) + ";\r\n}");
			}
			
			cl.append("\r\n return root;");
		}
		
		cl.append("\r\n}" +
				"\r\n}");
		
		
		
		///////////////////////////////////////////////////////////////
		//EQUALS method
		
		cl.append("\r\n\r\npublic boolean equals(Object o) {");
		cl.append("\r\n if ((o instanceof _cls_"+name+id+")");
		
		for (int i = 0; i < variables.size(); i++)
		{
			if (equateMethods.get(i) == null)
				cl.append("\r\n && ("+variables.get(i).name+" == null || "+variables.get(i).name+".equals(((_cls_"+name+id+")o)."+variables.get(i).name + "))");
			else
				cl.append("\r\n && ( " + equateMethods.get(i) + "("+variables.get(i).name+", ((_cls_"+name+id+")o)."+variables.get(i).name + "))");
		}
			
		
		if (parent != null)
			cl.append("\r\n && (parent == null || parent.equals(((_cls_"+name+id+")o).parent))");
		cl.append(")\r\n{return true;}\r\nelse\r\n{return false;}\r\n}");
		
		cl.append("\r\n\r\npublic int hashCode() {");
		cl.append("\r\nreturn 0;\r\n}");
		
		
		//_call method
		
		cl.append("\r\n\r\npublic void _call(String _info, int... _event){" +
				"\r\nsynchronized(_cls_" + this.name + this.id + "_instances){");
		for (Property l:logics.values())
			cl.append("\r\n_performLogic_"+l.name+"(_info, _event);");
		cl.append("\r\n}\r\n}");


//_call_all_filtered method
		
		//this method is NOT static and it automatically calls all the necessary instances
		//which fall under the current context...if the event belongs to "user1" then it calls all the
		//instances in the contexts below which belong to "user1"
		
		cl.append("\r\n\r\npublic void _call_all_filtered(String _info, int... _event){");
		
		for (Global h: this.foreaches)
		{
				cl.append("\r\n\r\n_cls_" + h.name + h.id + "[] a"+h.id+" = new _cls_" + h.name + h.id + "[1];" +
						"\r\nsynchronized(_cls_" + h.name + h.id + "._cls_" + h.name + h.id + "_instances){" +
						"\r\na"+h.id+" = _cls_" + h.name + h.id + "._cls_"	+ h.name + h.id + "_instances.keySet().toArray(a"+h.id+");" +
								"}" +
						"\r\nfor (_cls_" + h.name + h.id + " _inst : a"+h.id+")" +
						"\r\nif (_inst != null");
				
				for (int i = 0; i < variables.size(); i++)
				{
					if (equateMethods.get(i) == null)
						cl.append("\r\n && ("+variables.get(i).name+" == null || "+variables.get(i).name+".equals(_inst.parent."+variables.get(i).name + "))");
					else
						cl.append("\r\n && ( " + equateMethods.get(i) + "("+variables.get(i).name+", _inst.parent."+variables.get(i).name + "))");
				}
//				for (Variable v: variables)
//					cl.append(" && _inst.parent." + v.getVariableName() + ".equals(" + v.getVariableName()+")");
		
		cl.append("){\r\n_inst._call(_info, _event); " +
				"\r\n_inst._call_all_filtered(_info, _event);\r\n}");
		}
		cl.append("\r\n}");
		
		
//_call_all method
		
		//this method invokes all the instances in a particular context
		//it IS static and is mainly used for channels
		
		cl.append("\r\n\r\npublic static void _call_all(String _info, int... _event){" +
				"\r\n\r\n_cls_" + this.name + this.id + "[] a = new _cls_" + this.name + this.id + "[1];" +
						"\r\nsynchronized(_cls_" + this.name + this.id + "_instances){" +
						"\r\na = _cls_"	+ this.name + this.id + "_instances.keySet().toArray(a);" +
								"}" +
				"\r\nfor (_cls_" + this.name + this.id + " _inst : a)");
		cl.append("\r\n\r\nif (_inst != null) _inst._call(_info, _event);");
		
		cl.append("\r\n}");
		
		
		
		//_killThis
		cl.append("\r\n\r\npublic void _killThis(){");
		
		cl.append("\r\ntry{" +
				"\r\nif (--no_automata == 0){" +
				"\r\nsynchronized(_cls_" + this.name + this.id + "_instances){" +
						"\r\n_cls_"+name+id+"_instances.remove(this);" +
						"}");
		
		for (Variable v:local.keySet())
			if (v.getVariableType().equals("Clock"))
			{
				cl.append("\r\nsynchronized("+v.getVariableName() + "){");
				cl.append("\r\n"+v.getVariableName() + ".off();");
				cl.append("\r\n"+v.getVariableName() + "._inst = null;");
				cl.append("\r\n"+v.getVariableName() + " = null;}");
			}
		
		cl.append("\r\n}" +
				"\r\nelse if (no_automata < 0)" +
				"\r\n{throw new Exception(\"no_automata < 0!!\");}" +
				"\r\n}catch(Exception ex){ex.printStackTrace();}" +
				"\r\n}");
		
		
		//automaton methods
		
		cl.append("\r\n");
		for (Property l:logics.values())
			l.appendJava(cl, this,root);
		
		
		
		
		
		//_occurredEvent method
		
		cl.append("\r\n\r\npublic boolean _occurredEvent(int[] _events, int event){");
		cl.append("\r\nfor (int i:_events) if (i == event) return true;");
		cl.append("\r\nreturn false;");
		cl.append("\r\n}");
		
		//methods
		cl.append(methods.getMethods());
		
		cl.append("\r\n}");
		
		//end of class
		
		
		
		try{
		PrintWriter pw1 = new PrintWriter(Compiler.outputDir+"/aspects/_asp_"+name+id+".aj");
		pw1.write(as1.toString());
		pw1.close();
		PrintWriter pw3 = new PrintWriter(Compiler.outputDir+"/larva/_cls_"+name+id+".java");
		pw3.write(cl.toString());
		pw3.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
//	public void initializeAllClasses(StringBuilder sb, Global context)
//	{
//		sb.append("\r\n_cls_"+name+id + ".initialize();");
//		for (Foreach f:context.foreaches)
//			initializeAllClasses(sb, f);
//	}
		
	public void createClasses()
	{
		createLarva();
		createInterfaceCallable();
		createClockClass();
		createChannelClass();
	//	createClockEventClass();
	//	createChannelEventClass();
		createIterableList();
		createRunningClock();
		createSC();
		
	}
		
	public void createClockClass()
	{
		StringBuilder as1 = new StringBuilder();
		as1.append("package larva;"
				+"\r\n"
				+"\r\nimport java.util.ArrayList;"
				+"\r\n"
				+"\r\npublic class Clock {"
				+"\r\n"
				+"\r\npublic String name;"
				+"\r\npublic boolean thison = true;"
				+"\r\n"
				+"\r\nArrayList<Long> registered = new ArrayList<Long>();"
				+"\r\n	//ArrayList<Long> cycles = new ArrayList<Long>();"
				+"\r\n"
				+"\r\n"
				+"\r\nlong starting;"
				+"\r\nboolean enabled = false;"
				+"\r\nboolean paused = false;"
				+"\r\nlong durationPaused = 0;"
				+"\r\nlong whenPaused;"
				+"\r\n	"
				+"\r\npublic _callable _inst;"
				+"\r\n"
				+"\r\n"
				+"\r\n"
				+"\r\npublic Clock(_callable _inst, String name)"
				+"\r\n{"
				+"\r\nthis._inst = _inst;"
				+"\r\nthis.name = name;"
				+"\r\n}"
				+"\r\n"
				+"\r\npublic String toString() {"
				+"\r\n  return name;"
				+"\r\n}"
				+"\r\n"
				+"\r\npublic void off(){"
				+"\r\nsynchronized (this){"
				+"\r\nthison = false;"
				+"\r\n}}"
				+"\r\n"
				+"\r\npublic void reset()"
				+"\r\n{"
		//		+"\r\nsynchronized (RunningClock.lock){"
		//		+"\r\nsynchronized (RunningClock.events){"
				+"\r\nsynchronized (this){"
				+"\r\npaused = false;"
				+"\r\ndurationPaused = 0;"
				+"\r\nstarting = System.currentTimeMillis();"
				+"\r\nenabled = true;"
				+"\r\nfor (int i = 0; i < registered.size(); i++)"
				+"\r\n						registerGlobally(registered.get(i),starting);"
				+"\r\n					//no need to un-register the existing events which belong to this clock"
				+"\r\n					//this will be automatically ignored"
				+"\r\n}"//}}"
				+"\r\n}"
				+"\r\n"
				+"\r\npublic boolean verified(long starting)"
				+"\r\n{"
				+"\r\nsynchronized (this){"
				+"\r\n//		System.out.println(\"Starting\" + this.starting);"
				+"\r\n//		System.out.println(\"paused \" + durationPaused);"
				+"\r\nif (thison && enabled && !paused)"
				+"\r\nreturn (this.starting + durationPaused) == starting;"
				+"\r\nelse "
				+"\r\nreturn false;"
				+"\r\n}}"
				+"\r\n	"
				+"\r\npublic void pause()"
				+"\r\n{"
				+"\r\nsynchronized (this){"
				+"\r\n    paused = true;"
				+"\r\n//		System.out.println(\"Paused>>\" + System.currentTimeMillis());"
				+"\r\n    whenPaused = System.currentTimeMillis();"
				+"\r\n}}"
				+"\r\n	"
				+"\r\n//continue"
				+"\r\npublic void resume()"
				+"\r\n{			"
				+"\r\n		//avoids deadlock...\"resume\" may be waiting for the \"register\" to complete"
				+"\r\n		//while holding \"this object\" as a lock while \"verified\" is also holding"
				+"\r\n		//\"this object\" as a lock and its caller is holding \"lock\" which is required by \"register\"		"
				+"\r\n		//note the order of obtained locks!!!"
				+"\r\n		//this order of locking is crucial when the method registers with the global clock!!"
			//	+"\r\n		synchronized (RunningClock.lock){"
			//	+"\r\n			synchronized (RunningClock.events){"
				+"\r\n				synchronized (this){"
				+"\r\n                  long now = System.currentTimeMillis();"
				+"\r\n					durationPaused += now - whenPaused;	"
				+"\r\n					paused = false;//unpause here because this will effect the current time of the clock"
				+"\r\n//					System.out.println(\"Resumed>>\" + System.currentTimeMillis());"
						+"\r\n					for (int i = 0; i < registered.size(); i++)"
						+"\r\n						if (registered.get(i) > current_long(now))//filter those events which occurred before pause"
						+"\r\n							RunningClock.register(registered.get(i), starting, durationPaused, this);"
						+"\r\n				}"//}}"
						+"\r\n	}"
						+"\r\n"
						+"\r\n	public int compareTo(double seconds) {"
						+"\r\nsynchronized (this){"
						+"\r\n		return compareToMillis((long)(seconds*1000));"
						+"\r\n	}}"
						+"\r\n"
						+"\r\n	public int compareToMillis(long milli) {"
						+"\r\nsynchronized (this){"
						+"\r\nreturn new Long(current_long()).compareTo(milli);"
						+"\r\n	}}"
						+"\r\n"
						+"\r\n	public double current() {"
						+"\r\nsynchronized (this){"
						+"\r\n		return current_long()/(double)1000;"
						+"\r\n	}}"
						+"\r\n"
						+"\r\npublic long current_long(long now) {"
						+"\r\nsynchronized (this){"
						+"\r\nif (paused) return (whenPaused - starting - durationPaused);"
						+"\r\nelse return (now - starting - durationPaused);"
						+"\r\n}}"
						+"\r\n"
						+"\r\npublic long current_long() {"
						+"\r\nsynchronized (this){"
						+"\r\nif (paused) return (whenPaused - starting - durationPaused);"
						+"\r\nelse return (System.currentTimeMillis() - starting - durationPaused);"
						+"\r\n}}"
						+"\r\n	"
						+"\r\npublic void register(Long millis) "
						+"\r\n{"
						+"\r\nsynchronized (this){"
						+"\r\nregistered.add(millis);"
						+"\r\n	}}"
						+"\r\n	"
						+"\r\n	public void registerGlobally(Long millis, Long current)"
						+"\r\n	{"
						+"\r\n		RunningClock.register(millis,current, this);"
						+"\r\n	}"
						+"\r\n"
						+"\r\n//	public void registerCycle(long millis) {"
						+"\r\n//		cycles.add(millis);"
						+"\r\n//	}"
						+"\r\n"
						+"\r\n	public void event(long millis){}"
						+"\r\n"
						+"\r\n}");
	
		try{
		PrintWriter pw1 = new PrintWriter(Compiler.outputDir+"/larva/Clock.java");
		pw1.write(as1.toString());
		pw1.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void createSC()
	{
		try{
		PrintWriter pw4 = new PrintWriter(Compiler.outputDir+"/larva/SC.java");
		pw4.write("package larva; \r\n" + imports + methods.toJava());
		pw4.close();
		}catch(Exception ex)
		{ex.printStackTrace();}
	}
	
	public void _createChannelClass()
	{
		StringBuilder as1 = new StringBuilder();
		as1.append("package larva;"+

				"\r\nimport java.util.ArrayList;"+

				"\r\npublic class Channel implements Runnable{"+

				"\r\n	static boolean on = true;"+
				"\r\n	ArrayList<Object> queue = new ArrayList<Object>();"+
	
				"\r\n\r\npublic Channel()"+
				"\r\n{"+
				"\r\n	Thread t = new Thread(this);" +
				"\r\n	t.setDaemon(true);" +
				"\r\n	t.start();"+
				"\r\n}" +

				"\r\n\r\npublic void receive(String s){}"+
				"\r\n\r\npublic void receive(Object s){}"+
				"\r\n\r\npublic void receive(){}"+
				
				"\r\n\r\npublic void send(String s)"+
				"\r\n{"+
				"\r\n	synchronized (queue) {"+
				"\r\n		queue.add(s);"+
				"\r\n		queue.notify();"+
				"\r\n	}"+
				"\r\n}"+
				
				"\r\n\r\npublic void send(Object s)"+
				"\r\n{"+
				"\r\n	synchronized (queue) {"+
				"\r\n		queue.add(s);"+
				"\r\n		queue.notify();"+
				"\r\n	}"+
				"\r\n}"+
	
				"\r\n\r\npublic void send()"+
				"\r\n{"+
				"\r\n	synchronized (queue) {"+
				"\r\n		queue.add(new Object());"+
				"\r\n		queue.notify();"+
				"\r\n	}"+
				"\r\n}"+
				
				"\r\n\r\npublic void run()"+
				"\r\n{"+
				"\r\ntry{"+
				"\r\n	while (on)"+
				"\r\n	{"+
				"\r\n	while (queue.isEmpty() && on)"+
				"\r\n		synchronized (queue) {"+
				"\r\n			queue.wait(100);"+
				"\r\n	}"+
				"\r\n	if (on){"+
				"\r\n		new ChannelEvent(this,queue.remove(0));"+
				"\r\n	}"+
				"\r\n	}"+
				"\r\n}"+
				"\r\ncatch(Exception ex){"+
				"\r\n	ex.printStackTrace();"+
				"\r\n}"+
				"\r\n}\r\n}");
	
		try{
		PrintWriter pw1 = new PrintWriter(Compiler.outputDir+ "/larva/Channel.java");
		pw1.write(as1.toString());
		pw1.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void createChannelClass()
	{
		StringBuilder as1 = new StringBuilder();
		as1.append("package larva;"+

				"\r\npublic class Channel{"+
				"\r\n	static boolean on = true;"+
			
				"\r\n\r\npublic Channel()"+
				"\r\n{"+
				"\r\n}" +

				"\r\n\r\npublic void receive(String s){}"+
				"\r\n\r\npublic void receive(Object s){}"+
				"\r\n\r\npublic void receive(){}"+
				
				"\r\n\r\npublic void send(String s)"+
				"\r\n{"+
				"\r\n   if (on)	receive(s);"+
				"\r\n}"+
				
				"\r\n\r\npublic void send(Object s)"+
				"\r\n{"+
				"\r\n	if (on) receive(s);"+
				"\r\n}"+
	
				"\r\n\r\npublic void send()"+
				"\r\n{"+
				"\r\n	if (on) receive();"+
				"\r\n}"+
				
				"\r\n}");
	
		try{
		PrintWriter pw1 = new PrintWriter(Compiler.outputDir+"/larva/Channel.java");
		pw1.write(as1.toString());
		pw1.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void createClockEventClass()
	{
		StringBuilder as1 = new StringBuilder();
		as1.append("package larva;"+

"\r\npublic class ClockEvent implements Runnable{"+

				"\r\nlong millis;" +
				"\r\nClock c;"+
	
				"\r\npublic ClockEvent(Clock c, long millis){"+
				"\r\nthis.millis = millis;" +
				"\r\nthis.c = c;"+
				"\r\nThread t = new Thread(this);" +
				"\r\n t.setDaemon(true);" +
				"\r\n t.start();"+
				"\r\n}"+
	
				"\r\npublic void run()	{" +
				"\r\nsynchronized(c){"+
				"\r\nif (c != null && c._inst != null) c.event(millis);" +
				"\r\n}"+
				"\r\n}"+
	
				"\r\n}");
	
		try{
		PrintWriter pw1 = new PrintWriter(Compiler.outputDir+"/larva/ClockEvent.java");
		pw1.write(as1.toString());
		pw1.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void createInterfaceCallable()
	{
		StringBuilder as1 = new StringBuilder();
		as1.append("\r\npackage larva;"+
				"\r\n\r\npublic interface _callable {"+
				"\r\n\r\npublic void _call(String _info, int... _event);" +
				"\r\n\r\npublic void _call_all_filtered(String _info, int... _event);"+
				"\r\n}");
	
		try{
		PrintWriter pw1 = new PrintWriter(Compiler.outputDir+"/larva/_callable.java");
		pw1.write(as1.toString());
		pw1.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void createChannelEventClass()
	{
		StringBuilder as1 = new StringBuilder();
		as1.append("package larva;"+

"\r\npublic class ChannelEvent implements Runnable{"+

				"\r\n	Object o;" +
				"\r\n	String s;" +
				"\r\n	Channel c;"+
	
				"\r\n	public ChannelEvent(Channel c, Object o){"+
				"\r\nif (o instanceof String)" +
				"\r\n 	this.s = (String)o;" +
				"\r\nelse" +
				"\r\n	this.o = o;" +
				"\r\nthis.c = c;"+
				"\r\n Thread t = new Thread(this);" +
				"\r\n t.setDaemon(true);" +
				"\r\n t.start();"+
				"\r\n}"+
				
				"\r\npublic void run()	{"+
				"\r\nif (s!= null) c.receive(s);" +
				"\r\nelse c.receive(o);"+
				"\r\n}"+
	
				"\r\n}");
	
		try{
		PrintWriter pw1 = new PrintWriter(Compiler.outputDir+"/larva/ChannelEvent.java");
		pw1.write(as1.toString());
		pw1.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void createRunningClock()
	{StringBuilder as1 = new StringBuilder();
	as1.append("package larva;"
			+"\r\n"
			+"\r\n"
			+"\r\npublic class RunningClock implements Runnable {"
			+"\r\n"
			+"\r\npublic static boolean on = false;"
			+"\r\npublic static IterableList events;"
			+"\r\npublic static Object lock;"
			+"\r\n"
			+"\r\npublic static void start()"
			+"\r\n{"
			+"\r\n if (!on) {"
			+"\r\non = true;"
			+"\r\nevents = new IterableList();"
			+"\r\nlock = new Object();"
			+"\r\n 	Thread t = new Thread(new RunningClock());"
			+"\r\n	t.setPriority(Thread.MAX_PRIORITY);"
			+"\r\n	t.setDaemon(true);"
			+"\r\n	t.start();"
			+"\r\n}" +
					"\r\n}"
			+"\r\n"
			+"\r\n	public static void register(Long l, Long current, Clock c)"
			+"\r\n	{"
			+"\r\n		RunningClock.events.add(l + current,l,c);"
			+"\r\n		synchronized (lock) {"
			+"\r\n			lock.notify();//in case the clock is idle"
			+"\r\n		}"
			+"\r\n	}"
			+"\r\n	"
			+"\r\n	public static void register(Long l, Long current, Long paused, Clock c)"
			+"\r\n	{"
			+"\r\n		RunningClock.events.add(l + current + paused,l,c);"
			+"\r\n		synchronized (lock) {"
			+"\r\n			lock.notify();//in case the clock is idle"
			+"\r\n			}"
			+"\r\n		}"
			+"\r\n	"
			+"\r\n	public void run() {"
			+"\r\n		try{ 	"
			+"\r\n			while (on)"
			+"\r\n          if (events.getNext() != null) {"
			+"\r\n					"
			+"\r\n				long next = events.current();"
			+"\r\n				"
			+"\r\n				long cur = System.currentTimeMillis();"
			+"\r\n				long tmp = next - cur;"
			+"\r\n				if (on && tmp > 0) "
			+"\r\n			    	synchronized (lock) {"
			+"\r\n				    	lock.wait(tmp);"
			+"\r\n					}"	
			+"\r\n				"
			+"\r\n				cur = System.currentTimeMillis();"
			+"\r\n				if (on && next <= cur)"
			+"\r\n					{"
			+"\r\n						events.remove();"
//			+"\r\n						synchronized (events){"
			+"\r\n						for (int i = 0; i < events.currentClocks().size(); i++)"
			+"\r\n						{"
			+"\r\n							Clock c = events.currentClocks().get(i);"
			+"\r\n							long d = events.currentDurations().get(i);"
			+"\r\n//									System.out.println(\"Next\" + next);"
			+"\r\n//									System.out.println(\"Dur\" + d);"
			+"\r\n							if (c.verified(next-d))"
			+"\r\n								c.event(d);"
//			+"\r\n						}"
			+"\r\n						}"
			+"\r\n					} "
			+"\r\n				}"
			+"\r\n				else"
			+"\r\n				{"
			+"\r\n				   synchronized (lock) {lock.wait();}"
			+"\r\n				}"
			+"\r\n		}"
			+"\r\n		catch(Exception ex)		{"
			+"\r\n			ex.printStackTrace();"
			+"\r\n		}	"
			+"\r\n	}"
			+"\r\n}");
	try{
		PrintWriter pw1 = new PrintWriter(Compiler.outputDir+"/larva/RunningClock.java");
		pw1.write(as1.toString());
		pw1.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void createIterableList()
	{StringBuilder as1 = new StringBuilder();
	as1.append("package larva;"
			+"\r\n"
			+"\r\nimport java.util.ArrayList;"
			+"\r\n"
			+"\r\npublic class IterableList {"
			+"\r\n"
			+"\r\n	ArrayList<Long> actual = new ArrayList<Long>();"
			+"\r\n	ArrayList<ArrayList<Long>> drs = new ArrayList<ArrayList<Long>>();"
			+"\r\n	ArrayList<ArrayList<Clock>> clks = new ArrayList<ArrayList<Clock>>();"
			+"\r\n	"
			+"\r\n	int iterator = 0;"
			+"\r\n	boolean keeping = false;"
			+"\r\n	"
			+"\r\n	ArrayList<Clock> clocks = null;"
			+"\r\n	Long l = null;"
			+"\r\n	ArrayList<Long> durations = null;"
			+"\r\n	"
			+"\r\n	public void add(Long l, Long d, Clock c)"
			+"\r\n	{			"
			+"\r\nsynchronized (this){"
			+"\r\n			if (!actual.contains(l))"
			+"\r\n			{"
			+"\r\n				int i = 0;"
			+"\r\n				while (i < actual.size() && l > actual.get(i)) i++;"
			+"\r\n				"
			+"\r\n				actual.add(i,l);"
			+"\r\n				"
			+"\r\n				ArrayList<Long> ds = new ArrayList<Long>();"
			+"\r\n				ArrayList<Clock> cs = new ArrayList<Clock>();"
			+"\r\n				ds.add(d);"
			+"\r\n				cs.add(c);"
			+"\r\n				drs.add(i,ds);"
			+"\r\n				clks.add(i,cs);"
			+"\r\n			}"
			+"\r\n			else //if (!clks.get(actual.indexOf(l)).contains(c))"
			+"\r\n			{"
				+"\r\n				clks.get(actual.indexOf(l)).add(c);"
				+"\r\n				drs.get(actual.indexOf(l)).add(d);"
				+"\r\n			}"
				+"\r\n	}}"
				+"\r\n	"
				+"\r\n	//skip the next getNext() by returning the current values again"
				+"\r\n	public void keep()"
				+"\r\n	{"
				+"\r\nsynchronized (this){"
			//	+"\r\n		synchronized (RunningClock.lock) "
			//	+"\r\n		{"
				+"\r\n			keeping = true;"
				//+"\r\n		}"
				+"\r\n	}}"
				+"\r\n	"
				+"\r\n	public ArrayList<Clock> currentClocks()"
				+"\r\n	{"
				+"\r\nsynchronized (this){"
				+"\r\n		return clocks;"
				+"\r\n	}}"
				+"\r\n	"
				+"\r\n	public ArrayList<Long> currentDurations()"
				+"\r\n	{"
				+"\r\nsynchronized (this){"
				+"\r\n		return durations;"
				+"\r\n	}}"
				+"\r\n	"
				+"\r\n	public Long current()"
				+"\r\n	{"
				+"\r\nsynchronized (this){"
				+"\r\n		return l;"
				+"\r\n	}}"
				+"\r\n	"
				+"\r\n	public void remove()"
				+"\r\n	{"
				+"\r\nsynchronized (this){"
				+"\r\n			if (actual.size() > 0)"
				+"\r\n			{"
				+"\r\n				actual.remove(0);"
				+"\r\n				clks.remove(0);"
				+"\r\n				drs.remove(0);"
				+"\r\n			}"
				+"\r\n	}}"
				+"\r\n	"	
				+"\r\n	public Long getNext()"
				+"\r\n	{"
				+"\r\nsynchronized (this){"
				+"\r\n			if (actual.size() == 0)"
				+"\r\n				return null;"
				+"\r\n			else"
				+"\r\n			{"
				+"\r\n				l = actual.get(0);"
				+"\r\n				clocks = clks.get(0);"
				+"\r\n				durations = drs.get(0);"
				+"\r\n				return l;"
				+"\r\n			}"
				+"\r\n	}}"
				+"\r\n	"	
				+"\r\n	"	
				+"\r\n	}");
	try{
		PrintWriter pw1 = new PrintWriter(Compiler.outputDir+"/larva/IterableList.java");
		pw1.write(as1.toString());
		pw1.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void createLarva()
	{
		StringBuilder as1 = new StringBuilder();
		as1.append("\r\npackage larva;"+
				"\r\n\r\npublic class _larva {"+
				"\r\n\r\npublic static void _finalize(){" +
				"\r\n RunningClock.on = false;" +
				"\r\n Channel.on = false;"+
				"\r\n}" +
				"\r\n}");
	
		try{
		PrintWriter pw1 = new PrintWriter(Compiler.outputDir+"/larva/_larva.java");
		pw1.write(as1.toString());
		pw1.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
		
	public void createFiles()throws ParseException
	{
		if (id == 0)//root
		{
			File f = new File(outputDir+"/larva");
			f.mkdirs();
			f = new File(outputDir + "/aspects");
			f.mkdirs();
			createClasses();

			StringBuilder as1 = new StringBuilder();
			as1.append("package larva;");
			as1.append("\r\npublic class _BadStateException"+name+" extends Exception {");
			as1.append("\r\npublic String toString(){");
			as1.append("\r\nString temp = \"\";");
			as1.append("\r\nfor (int i = 4; i < getStackTrace().length; i++) temp += \"\\r\\n\" + getStackTrace()[i];");
			as1.append("\r\nreturn temp;");
			as1.append("\r\n}");
			as1.append("\r\n}");

			try{
				PrintWriter pw1 = new PrintWriter(Compiler.outputDir+"/larva/_BadStateException"+name+".java");
				pw1.write(as1.toString());
				pw1.close();
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		this.createFileForEach();
		
		for (Foreach f:foreaches)
			f.createFiles();
	}
	
	public String toJava()throws ParseException
	{
		createFiles();		
		return "Files READY!!!";
	}

	public void outputLogicsDiagrams(String outputDir) {
		for (Property l:logics.values())
			l.outputDiagram(outputDir+"/larva/");
		for (Global g:foreaches)
			g.outputLogicsDiagrams(outputDir);
	}

	public Global searchContext(ArrayList<Token> tokens) {
		if (tokens == null)
			return null;
		outer:
		for (Foreach f:foreaches)
		{
			if (f.context.size()!=tokens.size())
				return null;
			else
			{
				for (int i =0; i < f.context.size(); i++)
				{
					if (!f.context.get(i).text.equals(tokens.get(i).text))
						break outer;
				}
				return f;
			}
		}
		return null;
	}

	public Global containsContext(Token token) {
		if (token == null)
			return null;
		for (Foreach f:foreaches)
		{
			for (int i =0; i < f.context.size(); i++)
				{
					if (f.context.get(i).text.equals(token.text))
						return f;
				}				
		}
		return null;
	}

	public static String redirectOutput(String stats) {	
		
		return stats.replace("System .out", "_cls_"+root.name+root.id+".pw");
	}

//	public static String channelRedirect(String name, String stats) {
//		return stats.replace(".send (", ".send (\""+name+"\"");
//		
//	}
	
}
