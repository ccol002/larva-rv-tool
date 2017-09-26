package compiler;

import java.io.PrintWriter;
import java.util.HashMap;

public class Property extends Compiler {

	
	String name;	
	States states;
	Transitions transitions;
	
	public Property(ParsingString ps)throws ParseException
	{
		super(ps);
	}

	public void parse(HashMap<String, Trigger> events, Global g) throws ParseException
	{
		name = ps.parameter.trim();
		
		states = new States();
		states.ps= parseWrapper("STATES");
		states.parse();
		
		transitions = new Transitions();
		transitions.ps = parseWrapper("TRANSITIONS");
		transitions.parse(events,states,g);
	}
	
	public void appendJava(StringBuilder sb, Global g, Global root)throws ParseException
	{
		sb.append("\r\nint _state_id_"+name+" = "+states.starting.get(0).id+";");
		//translate logics
		sb.append("\r\n\r\npublic void _performLogic_"+name+"(String _info, int... _event) {");
		
		transitions.appendJava(sb, this, g, root);
		
		sb.append("\r\n}");
		
		sb.append("\r\n\r\npublic void _goto_"+name+"(String _info){");
		if (Compiler.verbose){
			sb.append("\r\n_cls_"+root.name+root.id+".pw.println(\"[" + name + "]MOVED ON METHODCALL: \"+ _info +\" TO STATE::> \" + _string_"+name+"(_state_id_"+name+", 1));");
			sb.append("\r\n_cls_"+root.name+root.id+".pw.flush();");
		}
		sb.append("\r\n}");
		
		sb.append("\r\n\r\npublic String _string_"+name+"(int _state_id, int _mode){");
		sb.append("\r\nswitch(_state_id){");
		
		for (State s: states.all.values())//no break needed because of return!
			sb.append("\r\ncase " + s.id+ ": if (_mode == 0) return \""+s.name+"\"; else return \""+s.format()+"\";");
		sb.append("\r\ndefault: return \"!!!SYSTEM REACHED AN UNKNOWN STATE!!!\";");
		sb.append("\r\n}\r\n}");
	}

	public void outputDiagram(String outputDir) 
	{
		StringBuilder sb = new StringBuilder();

		sb.append("digraph _logic_"+name+" {"+
			"\r\nrankdir=LR;");// +
			//"\r\nsize=\""+(8+states.all.size())+","+(5+states.all.size())+"\"");
	
		
		if (states.accepting.size() > 0)
		{
			sb.append("\r\nnode [shape = doublecircle];");
			for (State s:states.accepting)
				sb.append(" " + s.toString());
			sb.append(";");
		}
		
		if (states.normal.size()+states.starting.size() > 0)
		{
		sb.append("\r\nnode [shape = circle];");
		for (State s:states.normal)
			sb.append(" " + s.toString());
		for (State s:states.starting)
			sb.append(" " + s.toString());
		sb.append(";");
		}
		
		if (states.bad.size() > 0)
		{
		sb.append("\r\nnode [shape = octagon];");
		for (State s:states.bad)
			sb.append(" " + s.toString());
		sb.append(";");
		}
		
		sb.append("\r\nnode [shape = point]; _s_s;");
			
		for (State s:states.starting)
			sb.append("\r\n_s_s -> " + s + ";");

		
		for (Transition t:transitions.transitions.values())
		{
			int cnt = 0;
			for (Arrow a:t.arrows)
			{
				cnt++;
				sb.append("\r\n"+t.source + " -> " + a.destination);
				sb.append(" [ label = \"("+cnt+") "+ a.trigger);
				if (a.condition.size() > 0)
					sb.append("\\\\" + Tokenizer.show2(a.condition)); //+ "\\"
				if (a.action.size() > 0)
				{
					if (a.condition.size()==0)
						sb.append("\\\\");
					sb.append("\\\\" + Tokenizer.show2(a.action));
				}
				sb.append("\"];");
			}			
		}		
		
		sb.append("\r\n}");
		
		try{
			PrintWriter pw1 = new PrintWriter(Compiler.outputDir+"_diag_"+name+".txt");
			pw1.write(sb.toString());
			pw1.close();
			Runtime.getRuntime().exec("\""+Compiler.graphvizDir + "\" -Tgif -o\""+outputDir
					+"_logic_"+name+"_diag.gif\" -Kdot \""+Compiler.outputDir+"_diag_"+name+".txt\"");
			
		}
		catch(Exception ex)
		{
			System.out.println("Diagram was not successfully generated! " +
					"Make sure Graphviz is installed in the default location!" +
					"...or else provide a \"-g\" commandline argument");
		}
	}
}
