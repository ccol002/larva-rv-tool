package compiler;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
		Tokenizer tok = new Tokenizer(Tokenizer.VARIABLE_MODE);
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
		
		//declare channels
		for (Variable v:localVariables.values())
			if (v.getVariableType().equals("Channel"))
				cl.append("\r\npublic static " + v.getVariableType()+ " " + v.getVariableName()+";");
		
		//declare hashmap
		cl.append("\r\n\r\npublic static LinkedHashMap<_cls_"+name+id+",_cls_"+name+id+"> _cls_"+name+id
				+ "_instances;");
		
		

		
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
		
		//number of automata
		cl.append("\r\nint no_automata;");
		
		//automaton methods
		
		cl.append("\r\n");
		for (Property l:logics.values())
			cl.append("\r\nint _state_id_"+l.name+";");
		
		
		
		
		//clocks and other variable declarations		
		for (Variable v:local.keySet())
		{
			if (!v.getVariableType().equals("Clock") && !v.getVariableType().equals("Channel"))
				cl.append("\r\n public " + Tokenizer.showStats(local.get(v))+";");
			else if (v.getVariableType().equals("Clock"))
			{
				cl.append("\r\npublic " + v.getVariableType() + " " + 
						v.getVariableName() + ";");
			}
		}
		
		//invariants
		for (Invariant inv: invariants.invariants.values())
		{
			if (!inv.initialization)
			{
				cl.append("\r\npublic boolean " + inv.name + "_enb;");
				cl.append("\r\npublic " + inv.returnType + " " + inv.name + "_temp;" );
			}
			else {
				cl.append("\r\npublic boolean " + inv.name + "_enb;");
				cl.append("\r\npublic " + inv.returnType + " " + inv.name + "_temp = "+ Tokenizer.showStats(inv.call) +";" );
			}
		}
		
		/////////////////////////////////////////////////////
		//initialisation method
		cl.append("\r\n\r\npublic static void initialize(){");
		
		cl.append("\r\n//note that this initialisation does not include user-defined declarations in the Variables section\r\n");
		
		//initialise channels
		for (Variable v:localVariables.values())
			if (v.getVariableType().equals("Channel"))
				cl.append("\r\n" + v.getVariableName()+" = new Channel();");

		//initialise hashmap
		cl.append("\r\n\r\n_cls_"+name+id
				+ "_instances = new LinkedHashMap<_cls_"+name+id+",_cls_"+name+id+">();");
		
		
		//////////////////////////////////////////////////////////////////////////
		// initialization of root
		
		if (this.id == 0)
		{
			cl.append("\r\ntry{");

			if (!Compiler.console)//just output to console instead of file
				cl.append("\r\npw = new PrintWriter(\""+Compiler.outputDir.replace("\\", "\\\\")
					+"/output_"+name+".txt\");\r\n");

			cl.append("\r\nroot = new _cls_" + this.name + this.id + "();" +
					"\r\n_cls_" + this.name + this.id + "_instances.put(root, root);");

			cl.append("\r\n  root.initialisation();");
			
//			for (Variable v:localVariables.values())
//				if (v.getVariableType().equals("Clock"))
//					cl.append("\r\nroot." + v.getVariableName() + ".reset();");
			
			cl.append("\r\n}catch(Exception ex)\r\n{ex.printStackTrace();}");
		}

		//clocks and other variable declarations		
		for (Variable v:local.keySet())
			if (v.getVariableType().equals("Clock"))
				cl.append("\r\n" + 
						v.getVariableName() + " = new " + v.getVariableType() + "(this,\""+v.getVariableName()+"\");");

		//invariants
		for (Invariant inv: invariants.invariants.values())
		{
			if (!inv.initialization)
				cl.append("\r\n" + inv.name + "_enb = false;");
			else {
				cl.append("\r\n" + inv.name + "_enb = true;");
				cl.append("\r\n" + inv.name + "_temp = "+ Tokenizer.showStats(inv.call) +";" );
			}
		}
		
		cl.append("\r\n}");
		
		
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
		
		//number of automata
		cl.append("\r\nno_automata = "+logics.size()+";");
				
		
		cl.append("\r\n//initialise automata");
		for (Property p : logics.values())
		{

			cl.append("\r\n_state_id_"+p.name+" = "+p.states.starting.get(0).id+";");
			
			
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
				cl.append("\r\nif ( tmp." + inv + "_enb && !tmp."+inv+"_temp.equals(" + Tokenizer.showStats(inv.call) +")){");
				
				if (!Compiler.light)//no file output to keep it light
					cl.append("\r\n  _cls_"+name+"0.pw.println(\" Invariant Check: "+inv+" Failed: " + Tokenizer.showStats(inv.call) + "!!: \" + " +
						"new _BadStateException"+name+"().toString());" +
						"\r\n  _cls_" + name + "0.pw.flush();");
				else 
					cl.append("\r\n  System.out.println(\" Invariant Check: "+inv+" Failed: " + Tokenizer.showStats(inv.call) + "!!: \");");
				
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
		
		
		//hashcode
		cl.append("\r\n\r\npublic int hashCode() {");
		
		cl.append("\r\nreturn ");
		for (int i = 0; i < variables.size(); i++)
		{
			if (equateMethods.get(i) == null)
				cl.append("("+variables.get(i).name+"==null?1:"+variables.get(i).name+".hashCode()) *");
		}		
		if (parent != null)
			cl.append("(parent==null?1:parent.hashCode()) *");

		cl.append("1;\r\n}");
		
		
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
	
		
	public void createClasses()
	{
		createClass("_larva");
		createClass("_callable");
		createClass("Clock");
//		createClass("FixedClock");//these classes have been scrapped as they would introduce lots of complications
//		createClass("DynamicClock");
		createClass("Channel");
		createClass("IterableList");
		createClass("RunningClock");
		createSC();
	}
		
	public void createClass(String name)
	{
		try{			
			URL url = this.getClass().getResource("/resources/"+ name+".txt");
			String decoded = URLDecoder.decode(url.getPath(), "UTF-8");
			
			Path source = Paths.get(decoded);
			Path destination = Paths.get(Compiler.outputDir+"/larva/"+name+".java");

			Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
		}catch(Exception ex){
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
		if (!Compiler.console)//just output to console
			return stats.replace("System .out", "_cls_"+root.name+root.id+".pw");
		else
			return stats;
	}

//	public static String channelRedirect(String name, String stats) {
//		return stats.replace(".send (", ".send (\""+name+"\"");
//		
//	}
	
}
