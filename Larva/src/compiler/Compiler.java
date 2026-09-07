package compiler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class Compiler {

	public static String inputDir = null;
	public static String outputDir = ".";

	public static String graphvizDir = "dot"; //resolved via PATH; override with -g
	
	static Global global;
	static Methods methods;
	ParsingString ps;
	static StringBuilder imports;
	
	static boolean verbose = false;     //-v (adds more verbose output)
	static boolean console = false;     //-c (leave output to system.out)
	static boolean light = false;       //-l (for competition)
	static boolean synchronous = false; //-s or --synchronous (auto-flush the output file after every write, so "tail -f" shows it live)
	
	public Compiler(){}
	
	public Compiler(ParsingString sb)
	{
		ps = sb;
	}
	
	public StringBuilder getString()
	{
		return ps.string;
	}
	
	public void parse() throws ParseException
	{
		imports = parseWrapper("IMPORTS",false).string;
		
		global = new Global(parseWrapper("GLOBAL"));
		int slash = Math.max(inputDir.lastIndexOf("\\"),inputDir.lastIndexOf("/"));
		int space = inputDir.indexOf(" ",slash);
		int dot = 	inputDir.lastIndexOf(".");
		if (dot <= slash) dot = inputDir.length();//remember that there may be no slashes and no dots...
		global.name = inputDir.substring(slash+1,(space < dot && space > 0)?space:dot);
		global.parse();
		methods = new Methods(parseWrapper("METHODS", false));
//		
//		if (ps.getString().length() > 0)
//			System.out.println("Warning: Unreached end of file!!");
		//System.out.println(global);
		//System.out.println(methods.toJava());
		
	}
	
	public ParsingString parseWrapper(String title,boolean strict) throws ParseException
	{
		return ps.parseWrapper(title,strict);
	}
	
	public ParsingString parseWrapper(String title) throws ParseException
	{
		return ps.parseWrapper(title);
	}
	
	public String toJava()throws ParseException
	{
		return "";
	}

	/**
	 * Resets all of the compiler's cross-compile static state back to defaults.
	 * Several classes (Global, Event, Events, State, Token, EventCollection) use
	 * static counters/maps to generate unique names across a single compile; left
	 * untouched, a second compile in the same JVM inherits the first one's counters
	 * and accumulated data, producing non-deterministic/wrong output (e.g. Global's
	 * "id==0 becomes root" check never firing again once a first Global exists).
	 * Call this before every compile that isn't the first in a fresh JVM - a JUnit
	 * {@code @BeforeEach} in a shared test base class is the intended caller.
	 */
	public static void resetState()
	{
		inputDir = null;
		outputDir = ".";
		graphvizDir = "dot";

		global = null;
		methods = null;
		imports = null;
		verbose = false;
		console = false;
		light = false;
		synchronous = false;

		Global.sid = -1;
		Global.root = null;
		Global.name = null;

		Event.sid = -1;
		Events.sid = -1;
		State.sid = -1;
		Token.guid = Token.GUID_START;

		EventCollection.reverse.clear();
	}

	public static void main(String[] args) {
		try{
			if (args.length == 0)
			{
				System.out.println("You should specify a script file!! ");
				System.out.println("-o [] to specify output directory");
				System.out.println("-g [] to specify Graphviz directory");
				System.out.println("-s or --synchronous to auto-flush the output file after every write, so \"tail -f\" shows it live");
			}
			else
			{
				inputDir = args[0];
				for (int i = 1; i < args.length; i++)
				{
					if (args[i].equals("-o") && i < args.length-1)
						outputDir = args[i+1]+"/";
				
					if (args[i].equals("-g") && i < args.length-1)
						graphvizDir = args[i+1];
					
					if (args[i].equals("-v"))
						verbose = true;
					
					if (args[i].equals("-c"))
						console = true;
					
					if (args[i].equals("-l"))
						light = true;

					if (args[i].equals("-s") || args[i].equals("--synchronous"))
						synchronous = true;
				}

				compile();
				System.out.println("Compiled Successfully!!!");
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * Runs a compile using whatever inputDir/outputDir/graphvizDir/verbose/
	 * console/light are currently set to, letting exceptions (in particular
	 * ParseException) propagate to the caller instead of being swallowed -
	 * unlike main(), which prints and discards them. This is the entry point
	 * tests should call directly.
	 */
	public static void compile() throws ParseException, java.io.IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputDir)));
		StringBuilder text = new StringBuilder();
		String temp;
		while ((temp = br.readLine()) != null)   {
			if (temp.indexOf("%%") != -1)//remove comments
				temp=temp.substring(0,temp.indexOf("%%"));
			text.append(temp.trim() + "\r\n");
		}
		br.close();
		Compiler p = new Compiler(new ParsingString(text));
		p.parse();
		p.outputFiles();
		p.outputLogics();
	}

	private void outputLogics() {
		global.outputLogicsDiagrams(outputDir);		
	}

	private void outputFiles()throws ParseException {
		global.toJava();		
	}

	
}
