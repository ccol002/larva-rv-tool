package compiler;

import java.io.File;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class Global extends Compiler{

	public static int sid = -1;

	public int id;

	public static String name;

	Events events;

	//initializations
	LinkedHashMap<Variable, ArrayList<Token>> local = new LinkedHashMap<Variable, ArrayList<Token>>(); 
	LinkedHashMap<String, Variable> localVariables = new LinkedHashMap<String, Variable>();

	HashMap<String, Variable> contextVariables = new HashMap<String, Variable>();
	ArrayList<Variable> variables = new ArrayList<Variable>();//these are the (context)variables for the FOREACH	
	ArrayList<String> equateMethods = new ArrayList<String>();//these are the equate methods for the FOREACH
	ArrayList<String> stringMethods = new ArrayList<String>();//these are the equate methods for the FOREACH
	ArrayList<Token> context = new ArrayList<Token>();

	LinkedHashMap<String, Property> logics = new LinkedHashMap<String, Property>(); 

	ArrayList<Foreach> foreaches = new ArrayList<Foreach>();	

	public Invariants invariants;

	public static Global root; 

	Global parent;//we need access to the parent because of nesting
	//when we nest FOREACH'es we need to keep track of the whole context starting from the Global

	//the code which initializes the variables of a context
	ArrayList<Token> initializationCode = new ArrayList<Token>();//for special initialization
	ArrayList<Token> initializationCondition = new ArrayList<Token>();//for special initialization

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
		if (ps.string.toString().trim().length() > 0)
			System.out.println("Warning: Not all script was successfully parsed!!..." +
					"(possible wrong order of sections) Error found at: [" + 
					ps.string.substring(0, Math.min(ps.string.length(), 20)) + "...]");
	}

	public void commonParse() throws ParseException
	{
		parseInitialization(parseWrapper("INITIALIZEIF",false));

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
	}

	public void parseInitialization(ParsingString ps) throws ParseException
	{
		Tokenizer tok = new Tokenizer(Tokenizer.ACTION_MODE);
		if (ps.parameter != null)
			initializationCondition = tok.scan(ps.parameter);
		initializationCode = tok.scan(ps.toString());
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

			if (tokens.get(cnt+1).is("<"))
			{
				while (!tokens.get(cnt+1).is(">"))
				{
					v.type.text += tokens.get(++cnt).text;
					// tokens.remove(cnt+1);
				}
				v.type.text += tokens.get(++cnt).text;
				//tokens.remove(cnt+1);
			}

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

	public HashMap<String, Variable> allParentsLocalVarsHash(HashMap<String, Variable> vars)
	{

		vars.putAll(localVariables);

		if (parent != null) 
			return parent.allParentsLocalVarsHash(vars);
		else
			return vars;
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
			arr = g.getRecursiveForeaches(arr);
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
				System.out.println("Warning: ambigious reference to variable: \""+ t + "\" (matching the innermost context...use \":"+t+"\" to refer to the variable in global)");
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

	public void createFileForEach() throws ParseException, Exception
	{
		////////////////////////////////////////////
		////////////////////////////

		ArrayList<Variable> allVars = allParentsVars(new ArrayList<Variable>());
		ArrayList<Variable> parVars = null;
		if (parent != null)
			parVars = parent.allParentsVars(new ArrayList<Variable>());


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

		StringBuilder cl = new StringBuilder("package larva;\r\n\r\n"
				+imports+"\r\nimport java.util.LinkedHashMap;" +
				"\r\nimport java.util.HashMap;" +
				"\r\nimport java.lang.reflect.Field;" +
				"\r\nimport java.lang.reflect.Method;" +
				"\r\nimport java.io.PrintWriter;" +
		"\r\nimport java.sql.*;");
		cl.append("\r\n\r\npublic class _cls_"+name+id+" implements _callable{");


		cl.append("\r\npublic long pk = 0;");

		//static variable declarations
		if (parent == null)
		{
			cl.append("\r\n\r\npublic static PrintWriter _pw; " +
					"\r\npublic static _cls_" + this.name + this.id + " root;");

			cl.append("\r\npublic static String _url;" +
			"\r\n public static Connection _conn;");
		}

		cl.append("\r\npublic static long pks = 0;");
		
		Integer foreach_limit = null;
		if (parent != null)
		{
			foreach_limit = ((Foreach)this).LimitSize();
			if (foreach_limit != null)
			{
				cl.append("\r\n\r\npublic static final Integer LIMIT_OBJECTS = " + foreach_limit + ";");
				cl.append("\r\npublic static int livingObjects = 0;");
			}
		}

		for (Variable v:localVariables.values())
			if (v.getVariableType().equals("Channel"))
			{
				cl.append("\r\npublic static " + v.getVariableType()+ " " + v.getVariableName()+" = new Channel();");
			}



		//		cl.append("\r\n\r\npublic static LinkedHashMap<_cls_"+name+id+",_cls_"+name+id+"> _cls_"+name+id
		//				+ "_instances = new LinkedHashMap<_cls_"+name+id+",_cls_"+name+id+">();");


		//////////////////////////////////////////////////////////////////////////
		// static initialization 
		//*************************now initialization is handled by the global context

		if (parent == null)
		{
			cl.append("\r\nstatic{");
			//	cl.append("\r\n  initialize();");
			for (Global g : this.getRecursiveForeaches(new ArrayList<Global>()))
				cl.append("\r\n  _cls_"+name+g.id+".initialize();");

			cl.append(" \r\n}");
			//end of static initialization
		}



		/////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////
		//variable declarations
		//////////////////////////////////////////

		if (parent != null)
			cl.append("\r\n\r\n_cls_" + name + parent.id + " parent;");
		else 
			cl.append("\r\n\r\n_cls_" + name + id + " parent; //to remain null - this class does not have a parent!");



		///////////////////////////////////////////////////////////////////////////////
		//variables for all the events...

		for (Variable v: events.variables.values())
			if (v.type != null)
				cl.append("\r\npublic static " + v.type + " " + v.name+ ";");

		//these are the variables which constitute the context E.G. transaction
		for (Variable v: variables)
			cl.append("\r\npublic " + v.type + " " + v.name+ ";");

		cl.append("\r\nint no_automata = " + logics.size() + ";");

		for (Variable v:local.keySet())
		{
			if (!v.getVariableType().equals("Clock") && !v.getVariableType().equals("Channel"))
				cl.append("\r\npublic " + Tokenizer.showStats(local.get(v))+";");
			else if (v.getVariableType().equals("Clock"))
			{
				cl.append("\r\npublic " + v.getVariableType() + " " + 
						v.getVariableName() + ";");
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



		////////////////////////////////////////////////////////
		// list of context variables
		StringBuilder variablesSB = new StringBuilder();
		for (Variable v: allParentsVars(new ArrayList<Variable>()))
			variablesSB.append(v.type + " " + v.name+ ",");
		if (variablesSB.length() > 0)
			variablesSB.delete(variablesSB.length()-1, variablesSB.length());




		cl.append("\r\n\r\npublic long getPk(){return pk;}");


		cl.append("\r\n\r\npublic static void forceClassLoad(){}");



		///////////////////////////////////////////////////////////////
		///////// initialize
		///////////////////////////////////////////////////////////////
		
		cl.append("\r\n\r\npublic static void initialize(){ " +
			      "\r\n Statement stat = null;" +
			      "\r\n Statement stat1 = null;" +
				  "\r\n ResultSet rs = null;" + 
				  "\r\n try {");
		if (parent == null)
		{
			cl.append("\r\njava.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(\" (yyyy-MM-dd) [HH mm ss S]\");" +
					  "\r\njava.util.Date date = new java.util.Date();" +
					  "\r\nString datetime = dateFormat.format(date);" +
					  "dateFormat = new java.text.SimpleDateFormat(\" yyyy-MM-dd HH:mm:ss:S\");");
			
			cl.append("\r\n_pw = new PrintWriter(\"" + Compiler.reportDir.replace("\\", "\\\\")
					+ "_output_" + name + "\" + datetime + \".txt\");\r\n");
			
			cl.append("\r\n_pw.println(\"[LARVA] Monitoring started at \" + dateFormat.format(date));" +
					"\r\n_pw.flush();");
			
			cl.append("\r\n _url = \"" + Compiler.dbConnection + "\";");

			//setting database connection
			cl.append("\r\n Class.forName(\"com.mysql.jdbc.Driver\");"); //sun.jdbc.odbc.JdbcOdbcDriver
			if (Compiler.username == null)	
				cl.append("\r\n _conn = DriverManager.getConnection(_url);");
			else 
				cl.append("\r\n _conn = DriverManager.getConnection(_url, \""+Compiler.username+"\",\""+Compiler.password+"\");");
			
			/*
			 * the modification that would have to work under Linux
			 * 
			// _url = "jdbc:odbc:monitor";
			// Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			 * 
			 * to be replaced by
			 * 
			_url = "jdbc:mysql://localhost:3306/monitor"; 
			Class.forName("com.mysql.jdbc.Driver");  
			 _conn = DriverManager.getConnection(_url, "root", "abcdefgh");
			*
			*/
		}
		
		if (initializationCondition == null || initializationCondition.size()==0)
			cl.append("\r\n if (false) {");
		else
			cl.append("\r\n if (" + Tokenizer.showStats(initializationCondition) + ") {");

		
		if (parent == null)
		{
			cl.append("\r\n stat = _cls_"+name+"0._conn.createStatement();" +
			"\r\n  stat.execute(\"drop table if exists _clocks;\");");

			cl.append("\r\nstat.execute(\"create table _clocks");

			cl.append(" (id bigint(20) primary key,"
					+ "name varchar(255),"
					+ "class_name varchar(255),"
					+ "enabled bool,"					
					+ "inst bigint(20),"
					+ "paused bool,"
					+ "thison bool,"
					+ "_starting bigint(20),"
					+ "durationPaused bigint(20),"
					+ "whenPaused bigint(20),");

			for (int i = 0; i < Compiler.clockLimit; i++ )
				cl.append("ev"+ i + " bigint(20),");
			cl.deleteCharAt(cl.length()-1);
			cl.append(");\");");

			

		}//end of particular case of global		
		

		////////////////////////////////////////////////////////////////
		////////////// initialize continued... for the general case
		//////////////////////////////////////////////////////////////////

		cl.append("\r\n  stat1 = _cls_" + name + "0._conn.createStatement();");
		cl.append("\r\n  stat1.execute(\"drop table if exists _cls_"+name+id + ";\");");

		cl.append("\r\nstat1.execute(\"create table _cls_"+name+id);

		cl.append(" (_id bigint(20) primary key,");

		if (id >1)//parent exists and its not global
		{
			cl.append("_pid bigint(20),");
		}

		//context vars
		for (Variable v: contextVariables.values())
		{
			String s = v.getSQLType();
			if (s != null)
				cl.append("_" + v.name.text + " " + s + ",");
			else 
				throw new Exception("Don't know how to store variable " + v);
		}
		//local vars
		for (Variable v: localVariables.values())
		{
			String s = v.getSQLType();
			if (s != null)
				cl.append("_" + v.name.text + " " + s + ",");
			else if (v.getVariableType().equals("Clock"))
			{
				cl.append("_" + v.name.text + " bigint(20),");
			}
			else if (!v.getVariableType().equals("Channel"))
				throw new Exception("Don't know how to store variable " + v);
		}
		
		for (Property l:logics.values())
		{
			cl.append("_state_id_" + l.name + " int(10),");
		}
		cl.append("_no_automata int(10),");
		if (cl.charAt(cl.length()-1) == ',')
			cl.deleteCharAt(cl.length()-1);

		cl.append(");\");");

		//creating index
		for (Variable v: contextVariables.values())
		{
			cl.append("\r\n\r\ntry { " +
					"\r\n stat1.execute(\"CREATE INDEX _" + v.name.text + "_index ON _cls_" + name + id + "(_" + v.name.text + ");\");" +
			"\r\n } catch(Exception ex) {}");

		}
		
		//////////////////////////////////////////////////
		//initially code within initialize method
		/////////////////////////////////////////////////
		
		if (parent != null)
		{
			if (((Foreach)this).initially != null && ((Foreach)this).initially.size() > 0)
			{
				cl.append("\r\n\r\n ArrayList al = " + Tokenizer.showStats(((Foreach)this).initially));
				String temp = cl.toString().trim();
				if (temp.charAt(temp.length()-1) != ';')
					cl.append(";");
				if (variables.size()>1)
					throw new Exception("Dont know how to handle more than 1 variable in this special initialization!");

				cl.append("\r\n if (al != null && al.size() > 0) {\r\n");
				if (initializationCode == null || initializationCode.size() == 0)
				{
					cl.append("\r\n for (int _i =0; _i < ((ArrayList)al.get(0)).size(); _i++)" +
							  "\r\n   _get_cls_"+name+id+"_inst(true");
					for (int i = allVars.size()-1; i >=0; i--)
						cl.append(",(" + allVars.get(i).getVariableType()+")((ArrayList)al.get("+i+")).get(_i)");
					cl.append(");");
				}
				else
				{

					cl.append("\r\n for (int _i =0; _i < ((ArrayList)al.get(0)).size(); _i++)" +
							"\r\n  _get_cls_"+name+id+"_inst(true, ");

					for (int i = allVars.size()-1; i >=0; i--)
						cl.append("(" + allVars.get(i).getVariableType()+")((ArrayList)al.get("+i+")).get(_i),");

					if (allVars.size()>0)
						cl.deleteCharAt(cl.length()-1);

					cl.append(");");
				}
				cl.append("\r\n}");
			}
		}
		
		////////////////////////////////////////////
		//initializing root
		////////////////////////////
		if (parent == null)
		{
			cl.append("\r\nroot = new _cls_" + this.name + this.id + "();");
			//+	"\r\n_cls_" + this.name + this.id + "_instances.put(root, root);");

			cl.append("\r\n  root.initialisation();");
			
			if (initializationCode != null && initializationCode.size() > 0)
			{
				cl.append("\r\nroot.specialInitialization(" + initializationCode.get(0) + "());");
			}
			
			cl.append("\r\n  root.insertInDB();");
		}

		/////////////////////////////////////////////////
		/// "else" of initialization condition
		/////////////////////////////////////////////
		
		cl.append("\r\n}//of initialization condition " +
				"\r\n else {" +
				"\r\n   stat1 = _cls_"+name+"0._conn.createStatement();" +
				"\r\n   rs = stat1.executeQuery(\"select max(_id) as cnt from _cls_" + name + id + ";\");" +
				"\r\n   if (rs.next())" +
				"\r\n      pks = rs.getInt(\"cnt\") + 1;" +
				"\r\n   rs = stat1.executeQuery(\"select max(id) as cnt from _clocks;\");" +
				"\r\n   if (rs.next())" +
				"\r\n      Clock.pks = rs.getInt(\"cnt\") + 1;" +
				"\r\n  }" +
				"\r\n } catch (Exception ex) { ex.printStackTrace();} " +
				"\r\n finally {" +
				"\r\n 	if (rs != null) {" +
				"\r\n         try {" +
				"\r\n             rs.close();" +
				"\r\n         } catch (SQLException sqlEx) { } // ignore" +
				"\r\n         rs = null;" +
				"\r\n     }" +
				"\r\n     if (stat != null) {" +
				"\r\n         try {" +
				"\r\n             stat.close();" +
				"\r\n         } catch (SQLException sqlEx) { } // ignore" +
				"\r\n         stat = null;" +
				"\r\n     }" +
				"\r\n    if (stat1 != null) {" +
					"\r\n         try {" +
					"\r\n             stat1.close();" +
					"\r\n         } catch (SQLException sqlEx) { } // ignore" +
					"\r\n         stat1 = null;" +
					"\r\n     }" +
				"\r\n }" +
				"\r\n}");
		
		
		///////////////////////////////////////////////////////////////////////
		// update in DB
		/////////////////////////////////////////////////////////////////////////
		
		cl.append("\r\n\r\npublic void updateInDB() { " +
				"\r\nStatement stat = null;" +
				"\r\ntry {" +
				"\r\n  stat = _cls_" + name + "0._conn.createStatement();"
				+ "\r\n stat.execute(\"update _cls_" + name + id + " set ");

		for (Variable v: localVariables.values())
		{
			String s = v.getSQLType();
			if (s != null && s.equals("bool"))
				cl.append("_" + v.name.text + " = \\\"\" + ((" + v.name.text + ")?(1):(0)) + \"\\\",");
			else if (s != null)
				cl.append("_" + v.name.text + " = \\\"\" + " + v.name.text + " + \"\\\",");
			else if (v.getVariableType().equals("Clock"))
			{
				//remains the same but update clock table (below)
			}
			else if (!v.getVariableType().equals("Channel"))
				throw new Exception("Don't know how to store variable " + v);
		}
		for (Property l:logics.values())
		{
			cl.append(" _state_id_" + l.name + " = \\\"\" + _state_id_" + l.name + " + \"\\\",");
		}
		cl.append(" _no_automata = \\\"\" + no_automata + \"\\\"");
		//			if (cl.charAt(cl.length()-1) == ',')
		//				cl.deleteCharAt(cl.length()-1);


		if (contextVariables.size() > 0)
		{
			cl.append(" where ");

			for (Variable v: contextVariables.values())
			{
				cl.append("_" + v.name.text + " = \\\"\" + " + v.name.text + " + \"\\\" and ");
			}

			cl.delete(cl.length()-4, cl.length());
		}
		else
		{
			cl.append(" where _id = \" + pk + \"");
		}

		cl.append(";\"); " +
				"\r\n if (stat != null) {" +
				"\r\n     try {" +
				"\r\n         stat.close();" +
				"\r\n     } catch (SQLException sqlEx) { } // ignore" +
				"\r\n     stat = null;" +
				"\r\n }");

		
		

		//update clocks
		for (Variable v: localVariables.values())
			if (v.getVariableType().equals("Clock"))
			{					
				cl.append("\r\n" + v.getVariableName() + ".updateInDB(false);");
			}

		if (parent != null)
			cl.append("\r\n  parent.updateInDB();");

		cl.append("\r\n } catch (Exception ex) {" +
				"\r\n ex.printStackTrace();" +
				"\r\n}" +
				"\r\n finally {			" +
				"\r\n if (stat != null) {" +
				"\r\n try {" +
				"\r\n     stat.close();" +
				"\r\n } catch (SQLException sqlEx) { } // ignore" +
				"\r\n stat = null;" +
				"\r\n }" +
				"\r\n }" +
		"\r\n}");



		///////////////////////////////////////////////////////////////////////
		// insert new in DB
		//////////////////////////////////////////////////////////////////////
		
		cl.append("\r\n\r\npublic void insertInDB() { " +
				"Statement stat = null;" +
				"\r\ntry {" +
				"\r\n  stat = _cls_" + name + "0._conn.createStatement();"
				+ "\r\n stat.execute(\"insert into _cls_" + name + id + " values (");

		cl.append("\\\"\" + pk + \"\\\",");

		//parent is not global
		if (id > 1)
			cl.append("\\\"\" + parent.pk + \"\\\",");

		for (Variable v: contextVariables.values())
		{
			String s = v.getSQLType();
			if (s != null && s.equals("bool"))
			{
				cl.append("\\\"\" + (" + v.name.text + ")?(1):(0) + \"\\\",");
			}
			else if (s != null)
			{
				cl.append("\\\"\" + " + v.name.text + " + \"\\\",");
			}
			else 
				throw new Exception("Don't know how to store variable " + v);
		}			

		for (Variable v: localVariables.values())
		{
			String s = v.getSQLType();
			if (s != null && s.equals("bool"))
			{
				cl.append("\\\"\" + ((" + v.name.text + ")?(1):(0)) + \"\\\",");
			}
			else if (s != null)
				cl.append("\\\"\" + " + v.name.text + " + \"\\\",");
			else if (v.getVariableType().equals("Clock"))
			{
				cl.append("\\\"\" + " + v.name.text + ".pk + \"\\\",");
				//clocks are inserted into their respective table below
			}
			else if (!v.getVariableType().equals("Channel"))
				throw new Exception("Don't know how to store variable " + v);
		}
		for (Property l:logics.values())
		{
			cl.append("\\\"\" + _state_id_" + l.name + " + \"\\\",");
		}

		//			for (Variable v: contextVariables.values())
		//			{
		//				cl.append("_" + v.name.text + " = \\\"\" + " + v.name.text + " + \"\\\",");
		//			}
		cl.append("\\\"\" + no_automata + \"\\\"");
		//			if (cl.charAt(cl.length()-1) == ',')
		//				cl.deleteCharAt(cl.length()-1);


		cl.append(");\"); " +
				"\r\n if (stat != null) {" +
				"\r\n    try {" +
				"\r\n        stat.close();" +
				"\r\n    } catch (SQLException sqlEx) { } // ignore" +
				"\r\n    stat = null;" +
				"\r\n }");

		//update clocks
		for (Variable v: localVariables.values())
			if (v.getVariableType().equals("Clock"))
			{					
				cl.append("\r\n" + v.getVariableName() + ".insertInDB();");
			}


		cl.append(//"\r\nSystem.out.println(\"user: \" + s + \" pk: \" + pk + \" INSERTED in db\");" +
				"\r\n } catch (Exception ex) {" +
				//"\r\n System.out.println(\"Error occurred while inserting user: \" + s + \" pk: \" + pk + \" pks: \" + pks);" +
				"\r\n ex.printStackTrace();" +
				"\r\n}" +
				"\r\nfinally {	" +		
				"\r\nif (stat != null) {" +
				"\r\ntry {" +
				"\r\n    stat.close();" +
				"\r\n} catch (SQLException sqlEx) { } // ignore" +
				"\r\nstat = null;" +
				"\r\n}" +
				"\r\n}" +
		"\r\n}");



		///////////////////////////////////////////////////////////////////////
		// load from DB 
		//////////////////////////////////////////////////////////////////////
		
		cl.append("\r\n\r\npublic static _cls_"+name+id+" loadFromDB( " + variablesSB + " ) { " +
				"\r\nStatement stat = null;" +
				"\r\nResultSet rs = null;" +
				"\r\ntry {" +
				"\r\n  stat = _cls_" + name + "0._conn.createStatement();" +
		        "\r\n  rs = stat.executeQuery(\"select * from _cls_" + name + id + " where ");

		for (Variable v: contextVariables.values())
		{
			cl.append("_" + v.name.text + " = \\\"\" + " + v.name.text + " + \"\\\" and ");
		}
		if (contextVariables.size() > 0)
			cl.delete(cl.length()-4, cl.length());
		else
			cl.delete(cl.length()-7, cl.length());

		cl.append(";\");");

		cl.append("\r\n if (rs.next()) {" +
				"\r\n _cls_" + name + id + " temp = new _cls_" + name + id + "();");

		for (Variable v: contextVariables.values())
		{
			cl.append("\r\n temp." + v.name.text + " = " + v.name.text + ";");
		}

		cl.append("\r\n temp.pk = rs.getLong(\"_id\");");
		for (Variable v: localVariables.values())
		{
			String s = v.getSQLType();
			if (s != null)
				cl.append("\r\n temp." + v.name.text + " = rs.get" + v.getResultSetType() + "(\"_"+v.name.text+"\");");
			else if (v.getVariableType().equals("Clock"))
			{
				cl.append("\r\n long " + v.getVariableName() + " = rs.getLong(\"_"+v.getVariableName()+"\");");
				//these are further handled below
			}
			else if (!v.getVariableType().equals("Channel"))
				throw new Exception("Don't know how to store variable " + v);
		}
		for (Property l:logics.values())
		{
			cl.append("\r\n temp._state_id_" + l.name + " = rs.getInt(\"_state_id_" + l.name + "\");");
		}
		cl.append("\r\n temp.no_automata = rs.getInt(\"_no_automata\");");
		
		
		
		cl.append("\r\n if (rs != null) {" +
				"\r\n try {" +
				"\r\n     rs.close();" +
				"\r\n } catch (SQLException sqlEx) { } // ignore" +
				"\r\n  rs = null;" +
				"\r\n }" +
				"\r\n if (stat != null) {" +
				"\r\n      try {" +
				"\r\n          stat.close();" +
				"\r\n      } catch (SQLException sqlEx) { } // ignore" +
				"\r\n       stat = null;" +
				"\r\n  }");
		
		
		
		for (Variable v: localVariables.values())
		{
			if (v.getVariableType().equals("Clock"))
			{
				cl.append("\r\n temp." + v.name.text + " = Clock.loadFromDB("+v.name.text+", false );");
				cl.append("\r\n temp." + v.name.text + "._inst = temp;");
			}
		}
		
		
		cl.append("\r\n return temp;" +
		"\r\n} else return null;");



		cl.append("\r\n } catch (Exception ex) {" +
				//"\r\n System.out.println(\"Error while loading user: \" + s + \" pk \" + temp.pk);" +
				"\r\n ex.printStackTrace();" +
				"\r\n}" +
				"\r\nfinally {" +
				"\r\nif (rs != null) {" +
				"\r\ntry {" +
				"\r\n    rs.close();" +
				"\r\n} catch (SQLException sqlEx) { } // ignore" +
				"\r\nrs = null;" +
				"\r\n}" +
				"\r\nif (stat != null) {" +
				"\r\ntry {" +
				"\r\n    stat.close();" +
				"\r\n} catch (SQLException sqlEx) { } // ignore" +
				"\r\nstat = null;" +
				"\r\n}" +
				"\r\n}" +
				"\r\nreturn null;" +
		"\r\n}");


		///////////////////////////////////////////////////////////////////////
		// load from DB by PK
		//////////////////////////////////////////////////////////////////////
		
		cl.append("\r\n\r\npublic static _cls_"+name+id+" loadByPkFromDB(long pk) { " +
				"\r\nStatement stat = null;" +
				"\r\nResultSet rs = null;" +
				"\r\ntry {" +
				"\r\n  stat = _cls_" + name + "0._conn.createStatement();"
				+ "\r\n  rs = stat.executeQuery(\"select * from _cls_"
				+ name + id + " where _id=\" + pk + \";\");");



		cl.append("\r\n if (rs.next()) {" +
				"\r\n _cls_" + name + id + " temp = new _cls_" + name + id + "();");

		if (parent != null)
		{
			if (id == 1) //parent is global
				cl.append("\r\n temp.parent = _cls_" + name + "0._get_cls_" + name + "0_inst(false);");
			else
				cl.append("\r\n temp.parent = _cls_" + name + (id-1) + ".loadByPkFromDB(rs.getLong(\"_pik\"));");
		}
		
		for (Variable v: contextVariables.values())
			cl.append("\r\n temp." + v.name.text + " = rs.get" + v.getResultSetType() + "(\"_"+v.name.text+"\");");

		cl.append("\r\n temp.pk = rs.getLong(\"_id\");");
		for (Variable v: localVariables.values())
		{
			String s = v.getSQLType();
			if (s != null)
				cl.append("\r\n temp." + v.name.text + " = rs.get" + v.getResultSetType() + "(\"_"+v.name.text+"\");");
			else if (v.getVariableType().equals("Clock"))
			{
				cl.append("\r\n long " + v.getVariableName() + " = rs.getLong(\"_"+v.getVariableName()+"\");");
				//these are further handled below
			}
			else if (!v.getVariableType().equals("Channel"))
				throw new Exception("Don't know how to store variable " + v);
		}
		for (Property l:logics.values())
			cl.append("\r\n temp._state_id_" + l.name + " = rs.getInt(\"_state_id_" + l.name + "\");");

		cl.append("\r\n temp.no_automata = rs.getInt(\"_no_automata\");");
		
		
				
		cl.append("\r\n if (rs != null) {" +
				"\r\n try {" +
				"\r\n     rs.close();" +
				"\r\n } catch (SQLException sqlEx) { } // ignore" +
				"\r\n  rs = null;" +
				"\r\n }" +
				"\r\n if (stat != null) {" +
				"\r\n      try {" +
				"\r\n          stat.close();" +
				"\r\n      } catch (SQLException sqlEx) { } // ignore" +
				"\r\n       stat = null;" +
				"\r\n  }");
		
		
		
		for (Variable v: localVariables.values())
		{
			if (v.getVariableType().equals("Clock"))
			{
				cl.append("\r\n temp." + v.name.text + " = Clock.loadFromDB("+v.name.text+", false );");
				cl.append("\r\n temp." + v.name.text + "._inst = temp;");
			}
		}
		
				
		
		
		
		cl.append("\r\n return temp;" +
		"\r\n} else return null;");



		cl.append("\r\n } catch (Exception ex) {" +
				"\r\n ex.printStackTrace();" +
				"\r\n}" +
				"\r\nfinally {	" +		
				"\r\nif (rs != null) {" +
				"\r\n  try {" +
				"\r\n       rs.close();" +
				"\r\n    } catch (SQLException sqlEx) { } // ignore" +
				"\r\n      rs = null;" +
				"\r\n}" +
				"\r\nif (stat != null) {" +
				"\r\n     try {" +
				"\r\n        stat.close();" +
				"\r\n     } catch (SQLException sqlEx) { } // ignore" +
				"\r\n     stat = null;" +
				"\r\n   }" +
				"\r\n}" +
				"\r\nreturn null;" +
		"\r\n}");
		
		
		

		////////////////////////////////////////////////////
		//special initialization
		/////////////////////////////////////////////////////
		
		cl.append("\r\n\r\npublic void specialInitialization(HashMap<String,Object> m)" +
				"\r\n{" +
				"\r\nif (m == null) return;" +
				"\r\ntry {" +
				"\r\n  Class c = this.getClass();" +
				"\r\n  for (String s:m.keySet())" +
				"\r\n  {" +
				"\r\n    if (s.indexOf(\"STATE>\")==0){" +
				"\r\n      String logic = s.substring(6,s.length());" +
				"\r\n      Field f = c.getField(\"_state_id_\" + logic);" +
		"\r\n      String stateName = (String)m.get(s);");

		for (String s : logics.keySet())
		{
			cl.append("\r\n     if (logic.equals(\"" + s + "\")){");

			cl.append("\r\n          if (1==0){}"); 
			for (State state : logics.get(s).states.all.values())
			{
				cl.append("\r\n             else if (stateName.equals(\"" + state.name.text 
						+ "\")) f.set(this, " + state.id + ");");
			}

			cl.append("\r\n          }");
		}

		cl.append("\r\n    }" +
				"\r\n    else {" +
				"\r\n	   Field f = c.getField(s);" +
				"\r\n	   if (f.getType().equals(Clock.class))" +
				"\r\n	   {" +
				"\r\n        Method method = f.getType().getMethod(\"reset\", long.class);" +
				"\r\n	     method.invoke(f.get(this), m.get(s));" +
				"\r\n	   }" +
				"\r\n	   else" +
				"\r\n	 	 f.set(this, m.get(s));" +


				"\r\n      }" +
				"\r\n   }" +
				"\r\n} catch(Exception ex)" +
				"\r\n{ex.printStackTrace();}" +
		"\r\n}");






		////////////////////////////////////////////////////////////////////////////
		//CONSTRUCTOR
		///////////////////////////////////////////////////////////////////////////

		cl.append("\r\n//inheritance could not be used because of the automatic call to super()");
		cl.append("\r\n//when the constructor is called...we need to keep the SAME parent if this exists!");

		cl.append("\r\n\r\npublic _cls_" + name + id + "(){}");

		if (parent != null)
		{
			cl.append("\r\n\r\npublic _cls_" + name + id + "( ");

			for (Variable v: allParentsVars(new ArrayList<Variable>()))
				cl.append(v.type + " " + v.name + ",");
			cl.delete(cl.length()-1, cl.length());//remove last comma
			cl.append(") {" +
			"\r\ntry {");

			cl.append("\r\n pk = pks++; ");

			for (Variable v:localVariables.values())
				if (v.getVariableType().equals("Clock"))
				{
					cl.append("\r\n" + v.getVariableName() + " = new Clock(this,\""+v.getVariableName()+"\");");
					for (Long l:v.clockEvents)
						cl.append("\r\n" + v.getVariableName() + ".register(" + l + "l);");
					//					for (Long l:v.clockCycleEvents)
					//						cl.append("\r\n" + v.getVariableName() + ".registerCycle(" + l + ");");

				}


			for (Variable v: variables)
				cl.append("\r\nthis."+v.name+" = "+v.name+";");


			cl.append("\r\n  initialisation();");

			cl.append("\r\n} catch (Exception ex) {ex.printStackTrace();} \r\n}");

		}


		///////////////////////////////////////////////////
		///// initialisation
		/////////////////////////////////////////////////////
		
		cl.append("\r\n\r\npublic void initialisation() {");

		for (Property p : logics.values())
		{
			ArrayList<Token> code = p.states.starting.get(0).code;
			if (code != null)
				cl.append("\r\n\r\n" + Global.redirectOutput(Global.handleVariableReplacement(code,this), "[" + p.name +"]"));
		}

		for (Variable v:localVariables.values())
			if (v.getVariableType().equals("Clock"))
				cl.append("\r\n   " + v.getVariableName() + ".reset(RunningClock.now);");

		cl.append("\r\n}");

		///////////////////////////////////////////////////////////////////////////
		// static printStackTrace method
		//////////////////////////////////////////////////////////////////////////
		
		cl.append("\r\n\r\npublic static void printStackTrace(String prefix) {");
		cl.append("\r\n  Exception ex = new Exception();");
		cl.append("\r\n  String sEx = prefix == null ? \"\" : prefix;");
		cl.append("\r\n  for (int i = 0; i < ex.getStackTrace().length; i++)");
		cl.append("\r\n    sEx += ex.getStackTrace()[i];");
		cl.append("\r\n  _cls_"+name+"0._pw.println(sEx);");
		cl.append("\r\n  _cls_"+name+"0._pw.flush();");
		cl.append("\r\n}");

		///////////////////////////////////////////////////////////////////////////
		// static call to constructor / creation of new objects
		//////////////////////////////////////////////////////////////////////////
		
		cl.append("\r\n\r\npublic static _cls_"+name+id+" _get_cls_"+name+id+"_inst(boolean _init");

		if (parent == null)
			cl.append(") {" +
					"\r\n if (root == null) " +
					"\r\n   root = loadFromDB();" +
					"\r\n return root; \r\n}");
		else {

			for (Variable v: allVars)
				cl.append("," + v.getVariableType() + " " + v.getVariableName());
			//	cl.deleteCharAt(cl.length()-1);//remove last comma

			//cl.append(variablesSB);
			cl.append(") {");


			cl.append("\r\n_cls_"+name+id+" _inst = loadFromDB( ");
			for (Variable v: allVars)
				cl.append(v.getVariableName() + ",");
			cl.delete(cl.length()-1, cl.length());//remove last comma
			cl.append(");");
			cl.append("\r\nif (_inst != null)");
			cl.append("\r\n{//object loaded from DB");
			if (parent != null)
			{
				cl.append("\r\n  _inst.parent = _cls_" + name + parent.id
						+ "._get_cls_" + name + parent.id + "_inst(_init,");
				for (Variable v: parVars)
					cl.append(v.name + ",");
				cl.deleteCharAt(cl.length()-1);//remove last comma
				cl.append(");");

			}

			for (Invariant inv : invariants.invariants.values())
			{
				cl.append("\r\nif ( _inst." + inv + "_enb && !_inst."+inv+"_temp.equals(" + Tokenizer.showStats(inv.call) +")){"+ 
						"\r\n  _cls_"+name+"0._pw.println(\"[invariant check] Invariant Check: "+inv+" Failed: " + Tokenizer.showStats(inv.call) + "!!: \" + " +
						"new _BadStateException"+name+"().toString());" +
						"\r\n  _cls_" + name + "0._pw.flush();");

				cl.append("\r\n_inst." + inv + "_temp = " + Tokenizer.showStats(inv.call) + ";\r\n}");
			}

			cl.append("\r\n  if (_inst.no_automata == 0)");
			cl.append("\r\n  { printStackTrace(\"No more running automata.\\r\\n\"); }");

			cl.append("\r\n}");

			cl.append("\r\nelse\r\n{");

			cl.append("\r\n  _inst = new _cls_" + name + id + "(");
			for (Variable v: allVars)
				cl.append(v.getVariableName() + ",");
			cl.delete(cl.length()-1, cl.length());//remove last comma

			cl.append(");");

			for (Variable v:localVariables.values())
				if (v.getVariableType().equals("Clock"))
					cl.append("\r\n  _inst." + v.getVariableName() + ".reset(RunningClock.now);");

			if (initializationCode != null && initializationCode.size()>0)
			{
				cl.append("\r\n  if (_init) _inst.specialInitialization(" + initializationCode.get(0).text + "( ");

				for (Variable v: allVars)
					cl.append(v.getVariableName() + ",");
				cl.deleteCharAt(cl.length()-1);//remove last comma+

				cl.append( "));");
			}		

			if (parent != null)
			{
				cl.append("\r\n  _inst.parent = _cls_" + name + parent.id
						+ "._get_cls_" + name + parent.id + "_inst(_init,");
				for (Variable v: parVars)
					cl.append(v.name + ",");
				cl.deleteCharAt(cl.length()-1);//remove last comma
				cl.append(");");

			}

			cl.append("\r\n  _inst.insertInDB();");
			
			if (foreach_limit != null)
			{
				cl.append("\r\n\r\n  livingObjects++;");
				cl.append("\r\n  if (livingObjects > LIMIT_OBJECTS)");
				cl.append("\r\n  { printStackTrace(\"Foreach Limit (\" + LIMIT_OBJECTS + \") Exceeded (\" + livingObjects + \")\\r\\n\"); }");
			}
			

			cl.append("\r\n}");
			//		else
			//		{
			//			for (Invariant inv : invariants.invariants.values())
			//			{
			//				cl.append("\r\nif ( root." + inv + "_enb && !root."+inv+"_temp.equals(" + Tokenizer.showStats(inv.call) +")){"+ 
			//								"\r\n  _cls_"+name+"0._pw.println(\" Invariant Check: "+inv+" Failed: " + Tokenizer.showStats(inv.call) + "!!: \" + " +
			//								"new _BadStateException"+name+"().toString());" +
			//								"\r\n  _cls_" + name + "0._pw.flush();");
			//				
			//				cl.append("\r\nroot." + inv + "_temp = " + Tokenizer.showStats(inv.call) + ";\r\n}");
			//			}
			//			
			//			cl.append("\r\n return root;");
			//		}





			cl.append("\r\n return _inst;\r\n}");
		}


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

		cl.append("\r\n\r\npublic void _call(String _info, int... _event){");
		for (Property l:logics.values())
			cl.append("\r\n_performLogic_"+l.name+"(_info, _event);");
		cl.append("\r\n}");



		//_call_all_filtered method

		//this method is NOT static and it automatically calls all the necessary instances
		//which fall under the current context...if the event belongs to "user1" then it calls all the
		//instances in the contexts below which belong to "user1"

		cl.append("\r\n\r\npublic void _call_all_filtered(String _info, int... _event){");
		//		
		//		for (Global h: this.foreaches)
		//		{
		//				cl.append("\r\n\r\n_cls_" + h.name + h.id + "[] a = new _cls_" + h.name + h.id + "[1];" +
		//						"\r\na = _cls_" + h.name + h.id + "._cls_"	+ h.name + h.id + "_instances.keySet().toArray(a);" +
		//							
		//						"\r\nfor (_cls_" + h.name + h.id + " _inst : a)" +
		//						"\r\nif (_inst != null");
		//				
		//				for (int i = 0; i < variables.size(); i++)
		//				{
		//					if (equateMethods.get(i) == null)
		//						cl.append("\r\n && ("+variables.get(i).name+" == null || "+variables.get(i).name+".equals(_inst.parent."+variables.get(i).name + "))");
		//					else
		//						cl.append("\r\n && ( " + equateMethods.get(i) + "("+variables.get(i).name+", _inst.parent."+variables.get(i).name + "))");
		//				}
		////				for (Variable v: variables)
		////					cl.append(" && _inst.parent." + v.getVariableName() + ".equals(" + v.getVariableName()+")");
		//		
		//		cl.append("){\r\n_inst._call(_info, _event); " +
		//				"\r\n_inst._call_all_filtered(_info, _event);\r\n}");
		//		}
		cl.append("\r\n}");
		//		
		//		
		//_call_all method

		//this method invokes all the instances in a particular context
		//it IS static and is mainly used for channels

		cl.append("\r\n\r\npublic static void _call_all(String _info, int... _event){");// +
		//				"\r\n\r\n_cls_" + this.name + this.id + "[] a = new _cls_" + this.name + this.id + "[1];" +
		//						"\r\na = _cls_"	+ this.name + this.id + "_instances.keySet().toArray(a);" +
		//							
		//				"\r\nfor (_cls_" + this.name + this.id + " _inst : a)");
		//		cl.append("\r\n\r\nif (_inst != null) _inst._call(_info, _event);");
		//		
		cl.append("\r\n}");
		//		
		//		
		//		
		//		//_killThis
		//		cl.append("\r\n\r\npublic void _killThis(){");
		//		
		//		cl.append("\r\ntry{" +
		//				"\r\nif (--no_automata == 0){" +
		//						"\r\n_cls_"+name+id+"_instances.remove(this);");
		//						
		//		
		//		for (Variable v:local.keySet())
		//			if (v.getVariableType().equals("Clock"))
		//			{
		//				cl.append("\r\nsynchronized("+v.getVariableName() + "){");
		//				cl.append("\r\n"+v.getVariableName() + ".off();");
		//				cl.append("\r\n"+v.getVariableName() + "._inst = null;");
		//				cl.append("\r\n"+v.getVariableName() + " = null;}");
		//			}
		//		
		//		cl.append("\r\n}" +
		//				"\r\nelse if (no_automata < 0)" +
		//				"\r\n{throw new Exception(\"no_automata < 0!!\");}" +
		//				"\r\n}catch(Exception ex){ex.printStackTrace();}" +
		//				"\r\n}");
		//		



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
		//cl.append(methods.toJava());

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

	public void createSC()
	{
		try{
			PrintWriter pw4 = new PrintWriter(Compiler.outputDir+"/larva/SC.java");
			pw4.write("package larva; \r\n" + imports + methods.toJava());
			pw4.close();
		}catch(Exception ex)
		{ex.printStackTrace();}
	}

	public void createClasses()
	{
		createLarva();
		createInterfaceCallable();
		createRunningClock();
		createIterableList();
		createClockClass();
		createChannelClass();
		//	createClockEventClass();
		//	createChannelEventClass();
		createSC();
	}

	public void createRunningClock()
	{StringBuilder as1 = new StringBuilder();
	as1.append("package larva;"
			+"\r\n"
			+"\r\n"
			+"\r\npublic class RunningClock {"
			+"\r\n"
			+"\r\n	public static boolean on = true;"
			+"\r\n	public static IterableList events = new IterableList();"
			+"\r\n	public static Object lock = new Object();"
			+"\r\n		"
			+"\r\n	public static long now = 0;"
			+"\r\n	public static long eventTime = 0;"
			+"\r\n	"
			+"\r\n	public static void start()"
			+"\r\n	{}"
			+"\r\n"
			+"\r\n	public static void register(Long l, Long current, Clock c)"
			+"\r\n	{"
			+"\r\n		RunningClock.events.add(l + current,l,c.pk);"
			+"\r\n		synchronized (lock) {"
			+"\r\n			lock.notify();//in case the clock is idle"
			+"\r\n		}"
			+"\r\n	}"
			+"\r\n	"
			+"\r\n	public static void register(Long l, Long current, Long paused, Clock c)"
			+"\r\n	{"
			+"\r\n		RunningClock.events.add(l + current + paused,l,c.pk);"
			+"\r\n		synchronized (lock) {"
			+"\r\n			lock.notify();//in case the clock is idle"
			+"\r\n			}"
			+"\r\n		}"
			+"\r\n	"
			+"\r\n	public static void updateNow(long timeNow)"
			+"\r\n	{"
			+"\r\n		synchronized (lock) {"
			+"\r\n			now = timeNow;" 
			+"\r\n          run();"
			+"\r\n		}"
			+"\r\n	}"
			+"\r\n	"
			+"\r\n	public static void run() {"
			+"\r\n		try{boolean completed = false;	"
			+"\r\n			while (!completed && events.getNext() != null) {"
			+"\r\n					"
			+"\r\n				long next = events.current();"
			+"\r\n				"
			//			+"\r\n				synchronized (lock) {"
			+"\r\n					"
			+"\r\n					long cur = now;"
			+"\r\n					"
			+"\r\n					if (on && next <= cur)"
			+"\r\n					{"
			//	+"\r\n						synchronized (events){"
			+"\r\n						events.remove();"
			+"\r\n						for (int i = 0; i < events.currentClocks().size(); i++)"
			+"\r\n						{"
			+"\r\n							Clock c = Clock.loadFromDB(events.currentClocks().get(i),true);"
			+"\r\n							long d = events.currentDurations().get(i);"
			+"\r\n//									System.out.println(\"Next\" + next);"
			+"\r\n//									System.out.println(\"Dur\" + d);"
			+"\r\n							if (c.verified(next-d))"
			+"\r\n                           {  "
			+"\r\n                              eventTime = next;"
			+"\r\n								c.event(d);"
			+"\r\n		                     }"
			+"\r\n						}"
			+"\r\n					} else " 
			+"\r\n                  {" 
			//	+"\r\n                      events.keep();//event has not been triggered (because of a notificaiton)...try again"
			+"\r\n                      completed = true;"
			+"\r\n                  }"
			//			+"\r\n				}"
			+"\r\n			}"
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
			+"\r\n	ArrayList<ArrayList<Long>> clks = new ArrayList<ArrayList<Long>>();"
			+"\r\n	"
			+"\r\n	int iterator = 0;"
			+"\r\n	boolean keeping = false;"
			+"\r\n	"
			+"\r\n	ArrayList<Long> clocks = null;"
			+"\r\n	Long l = null;"
			+"\r\n	ArrayList<Long> durations = null;"
			+"\r\n	"
			+"\r\n	public void add(Long l, Long d, Long c)"
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
			+"\r\n				ArrayList<Long> cs = new ArrayList<Long>();"
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
			+"\r\n	public ArrayList<Long> currentClocks()"
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

	public void createClockClass()
	{

		StringBuilder as1 = new StringBuilder();
		as1.append("package larva;"
				+"\r\n"
				+"\r\nimport java.util.ArrayList;"
				+"\r\nimport java.sql.*;"
				+"\r\nimport java.lang.reflect.Method;"
				+"\r\n"
				+"\r\npublic class Clock {"
				+"\r\n"
				+"\r\npublic String name;"
				+"\r\npublic String className;"
				+"\r\npublic static final int limit = "+Compiler.clockLimit+";"
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
				+"\r\npublic static long pks = 0;	"
				+"\r\npublic long pk;	"
				+"\r\n	"
				+"\r\npublic _callable _inst;"
				+"\r\n"
				+"\r\n"
				+"\r\npublic Clock(){}"
				+"\r\n"
				+"\r\npublic Clock(_callable _inst, String name)"
				+"\r\n{"
				+"\r\n	pk = pks++;"
				+"\r\n  this._inst = _inst;"
				+"\r\n  this.name = name;"
				+"\r\n  this.className = _inst.getClass().getName();"
				+"\r\n}"


				+"\r\npublic void updateInDB(boolean inst) { "
				+"\r\n  Statement stat = null;"
				+"\r\n	try {"
				+"\r\n   if (inst) {"
				+"\r\n     Class c = Class.forName(className);"
				+"\r\n      Method m = c.getMethod(\"updateInDB\",new Class[0]);"
				+"\r\n      m.invoke(c.cast(_inst));"
				+"\r\n   }"
				+"\r\n	 else {" 
				+"\r\n     stat = _cls_" + name + "0._conn.createStatement();"
				+"\r\n	   String s = \"update _clocks set enabled = \\\"\" + ((enabled)?(1):(0)) + \"\\\"," +
				" name = \\\"\" + name + \"\\\"," +
				" class_name = \\\"\" + className + \"\\\"," +
				" inst = \\\"\" + _inst.getPk() + \"\\\"," +
				" paused = \\\"\" + ((paused)?(1):(0)) + \"\\\"," +
				" thison = \\\"\" + ((thison)?(1):(0)) + \"\\\"," +
				" _starting = \\\"\" + starting + \"\\\"," +
				" durationPaused = \\\"\" + durationPaused + \"\\\"," +
				"whenPaused = \\\"\" + whenPaused + \"\\\",\";"

				+"\r\nfor (int i = 0; i < limit; i++)"
				+"\r\n		if (i < registered.size())"
				+"\r\n			s += \"ev\" + i + \"= \\\"\" + registered.get(i) + \"\\\",\";"
				+"\r\n		else "
				+"\r\n			s += \"ev\" + i + \"= null,\";"

				+"\r\n		s = s.substring(0, s.length()-1);"
				//				+"\r\n		s+=\");\";"

				//				+"\r\nfor (int i = 0; i < registered.size(); i++)" +
				//				"\r\n  s += \"ev\" + i + \" = \\\"\" + registered.get(i) + \"\\\", \";" +

				+"\r\n s += \" where id = \\\"\" + pk + \"\\\";\";" 
				+"\r\n stat.execute(s);"
				+"\r\n	 }" 
				+"\r\n	} catch (Exception ex) {"
				+"\r\n	 ex.printStackTrace();"
				+"\r\n	}" 
				+"\r\n	finally {"
				+"\r\n	    if (stat != null) {"
				+"\r\n	        try {"
				+"\r\n	            stat.close();"
				+"\r\n	        } catch (SQLException sqlEx) { } // ignore"
				+"\r\n	        stat = null;"
				+"\r\n	    }"
				+"\r\n	}"
				+"\r\n	}"


				+"\r\npublic void insertInDB() { "
				+"\r\n	Statement stat = null;"
				+"\r\n	try {"
				+"\r\n	 stat = _cls_" + name + "0._conn.createStatement();"
				+"\r\n	 String s = \"insert into _clocks values (" +
				" \\\"\" + pk + \"\\\" ," +
				" \\\"\" + name + \"\\\" ," +
				" \\\"\" + className + \"\\\" ," +
				" \\\"\" + ((enabled)?(1):(0)) + \"\\\"," +
				" \\\"\" + _inst.getPk() + \"\\\"," +
				" \\\"\" + ((paused)?(1):(0)) + \"\\\"," +
				" \\\"\" + ((thison)?(1):(0)) + \"\\\"," +
				" \\\"\" + starting + \"\\\"," +
				" \\\"\" + durationPaused + \"\\\"," +
				" \\\"\" + whenPaused + \"\\\",\";"

				+"\r\nfor (int i = 0; i < limit; i++)"
				+"\r\n		if (i < registered.size())"
				+"\r\n			s += \"\\\"\" + registered.get(i) + \"\\\",\";"
				+"\r\n		else "
				+"\r\n			s += \"null,\";"

				+"\r\n	s = s.substring(0, s.length()-1);" 							
				+"\r\n  s += \");\";"				
				+"\r\nstat.execute(s);"

				+"\r\n	 } catch (Exception ex) {"
				+"\r\n	 ex.printStackTrace();"
				+"\r\n	}"
				+"\r\n	finally {"
				+"\r\n	    if (stat != null) {"
				+"\r\n	        try {"
				+"\r\n	            stat.close();"
				+"\r\n	        } catch (SQLException sqlEx) { } // ignore"
				+"\r\n	        stat = null;"
				+"\r\n	    }"
				+"\r\n	}"
				+"\r\n	}"

				+"\r\n public static Clock loadFromDB( long pk, boolean inst ) { "
				+"\r\n 	 Statement stat = null;"
				+"\r\n 	 ResultSet rs = null;"
				+"\r\n 	try {"
				+"\r\n 	  stat = _cls_" + name + "0._conn.createStatement();"
				+"\r\n 	  rs = stat.executeQuery(\"select * from _clocks where id = \\\"\" + pk + \"\\\" ;\");"
				+"\r\n 	 if (rs.next()) {"
				+"\r\n 		 Clock temp = new Clock();"
				+"\r\n 		 temp.pk = pk;"
				+"\r\n 		 temp.name = rs.getString(\"name\");"
				+"\r\n 		 temp.className = rs.getString(\"class_name\");"
				+"\r\n 		 temp.enabled = rs.getBoolean(\"enabled\");"
				+"\r\n       if (inst) {"
				+"\r\n 	       Class[] par = new Class[1];"
				+"\r\n 		   par[0] = long.class;"
				+"\r\n 		   Class c = Class.forName(temp.className);"
				+"\r\n 		   Method m = c.getMethod(\"loadByPkFromDB\",par);"
				+"\r\n 		   temp._inst = (_callable)m.invoke(c,rs.getLong(\"inst\"));"
				+"\r\n        }" 
				+"\r\n 		 temp.paused = rs.getBoolean(\"paused\");"
				+"\r\n 		 temp.thison = rs.getBoolean(\"thison\");"
				+"\r\n 		 temp.starting = rs.getLong(\"_starting\");"
				+"\r\n 		 temp.durationPaused = rs.getLong(\"durationPaused\");"
				+"\r\n 		 temp.whenPaused = rs.getLong(\"whenPaused\");	"	

				+"\r\n 		for (int i = 0; i < limit; i++)"
				+"\r\n 		 {"
				+"\r\n 			 Long l = rs.getLong(\"ev\" + i);"
				+"\r\n 			 if (l==null) break;"
				+"\r\n 			 temp.registered.add(l);"
				+"\r\n 		 }				"

				+"\r\n 		 return temp;"
				+"\r\n 	  } else return null;"
				+"\r\n  } catch (Exception ex) {"
				+"\r\n 	ex.printStackTrace();"
				+"\r\n	}"
				+"\r\n	finally {"
				+"\r\n		if (rs != null) {"
				+"\r\n	        try {"
				+"\r\n	            rs.close();"
				+"\r\n	        } catch (SQLException sqlEx) { } // ignore"
				+"\r\n	        rs = null;"
				+"\r\n	    }"
				+"\r\n	    if (stat != null) {"
				+"\r\n	        try {"
				+"\r\n	            stat.close();"
				+"\r\n	        } catch (SQLException sqlEx) { } // ignore"
				+"\r\n	        stat = null;"
				+"\r\n	    }	"	    
				+"\r\n	}"
				+"\r\n	return null;"
				+"\r\n	}"



				+"\r\n"
				+"\r\npublic synchronized void off(){"
				+"\r\nthison = false;"
				+"\r\n}"
				+"\r\n"
				+"\r\npublic void reset(long now)"
				+"\r\n{	   "
				+"\r\nsynchronized (RunningClock.lock){"
				+"\r\nsynchronized (RunningClock.events){"
				+"\r\nsynchronized (this){"
				+"\r\nenabled = false;"
				+"\r\npaused = false;"
				+"\r\ndurationPaused = 0;"
				+"\r\n"
				+"\r\nstarting = now;"
				+"\r\nenabled = true;"
				+"\r\nfor (int i = 0; i < registered.size(); i++)"
				+"\r\n						registerGlobally(registered.get(i),starting);"
				+"\r\n					//no need to un-register the existing events which belong to this clock"
				+"\r\n					//this will be automatically ignored"
				+"\r\n}}}"
				+"\r\n}"
				+"\r\n"
				+"\r\npublic synchronized boolean verified(long starting)"
				+"\r\n{"
				+"\r\n//		System.out.println(\"Starting\" + this.starting);"
				+"\r\n//		System.out.println(\"paused \" + durationPaused);"
				+"\r\nif (thison && enabled && !paused)"
				+"\r\nreturn (this.starting + durationPaused) == starting;"
				+"\r\nelse "
				+"\r\nreturn false;"
				+"\r\n}"
				+"\r\n	"
				+"\r\npublic synchronized void pause(long now)"
				+"\r\n{"
				+"\r\n    paused = true;"
				+"\r\n//		System.out.println(\"Paused>>\" + System.currentTimeMillis());"
				+"\r\n    whenPaused = now;"
				+"\r\n}"
				+"\r\n	"
				+"\r\n//continue"
				+"\r\npublic void resume(long now)"
				+"\r\n{			"
				+"\r\n		//avoids deadlock...\"resume\" may be waiting for the \"register\" to complete"
				+"\r\n		//while holding \"this object\" as a lock while \"verified\" is also holding"
				+"\r\n		//\"this object\" as a lock and its caller is holding \"lock\" which is required by \"register\"		"
				+"\r\n		//note the order of obtained locks!!!"
				+"\r\n		//this order of locking is crucial when the method registers with the global clock!!"
				+"\r\n		synchronized (RunningClock.lock){"
				+"\r\n			synchronized (RunningClock.events){"
				+"\r\n				synchronized (this){"
				+"\r\n					durationPaused += now - whenPaused;	"
				+"\r\n					paused = false;//unpause here because this will effect the current time of the clock"
				+"\r\n//					System.out.println(\"Resumed>>\" + System.currentTimeMillis());"
				+"\r\n					for (int i = 0; i < registered.size(); i++)"
				+"\r\n						if (registered.get(i) > current_long(now))//filter those events which occurred before pause"
				+"\r\n							RunningClock.register(registered.get(i), starting, durationPaused, this);"
				+"\r\n				}}}"
				+"\r\n	}"
				+"\r\n"
				+"\r\n	public synchronized int compareTo(long now, double seconds) {"
				+"\r\n		return compareToMillis(now, (long)(seconds*1000));"
				+"\r\n	}"
				+"\r\n"
				+"\r\n	public synchronized int compareToMillis(long now, long milli) {"
				+"\r\nreturn new Long(current_long(now)).compareTo(milli);"
				+"\r\n	}"
				+"\r\n"
				+"\r\n	public synchronized double current(long now) {"
				+"\r\n		return current_long(now)/(double)1000;"
				+"\r\n	}"
				+"\r\n"
				+"\r\npublic synchronized long current_long(long now) {"
				+"\r\nif (paused) return (whenPaused - starting - durationPaused);"
				+"\r\nelse return (now - starting - durationPaused);"
				+"\r\n}"
				+"\r\n	"
				+"\r\npublic synchronized void register(Long millis) throws Exception "
				+"\r\n{ if (registered.size()+1 > limit) throw new Exception(\"Limit of registered events exceeded!!\");"
				+"\r\nregistered.add(millis);"
				+"\r\n	}"
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
			PrintWriter pw1 = new PrintWriter(Compiler.outputDir+"/larva/Channel.java");
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
				"\r\n\r\npublic interface _callable {" +
				"public long getPk();"+
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


	public void createFiles()throws ParseException, Exception
	{
		if (id == 0)//root
		{
			File f = new File(outputDir + "/larva");
			f.mkdirs();
			f = new File(outputDir + "/larvaOutput");
			f.mkdirs();
			f = new File(outputDir + "/aspects");
			f.mkdirs();
			f = new File(outputDir + "/diags");
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
				PrintWriter pw1 = new PrintWriter(Compiler.outputDir + "/larva/_BadStateException" + name + ".java");
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

	public String toJava()throws ParseException, Exception
	{
		createFiles();		
		return "Files READY!!!";
	}

	public void outputLogicsDiagrams(String outputDir) {
		for (Property l:logics.values())
			l.outputDiagram(outputDir+"/diags/");
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

	public static String redirectOutput(String stats, String label) {	
		
		stats = stats.replace("System .out .println (", "_cls_"+root.name+root.id+"._pw.println(\"" + label + "\" + ");
		stats = stats.replace("System.out.println (", "_cls_"+root.name+root.id+"._pw.println(\"" + label + "\" + ");
		stats = stats.replace("System.out.println(", "_cls_"+root.name+root.id+"._pw.println(\"" + label + "\" + ");
		return stats;
	}

	//	public static String channelRedirect(String name, String stats) {
	//		return stats.replace(".send (", ".send (\""+name+"\"");
	//		
	//	}

}
