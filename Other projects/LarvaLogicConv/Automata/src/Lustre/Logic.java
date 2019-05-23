package Lustre;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import QDDC.QDDC;

import parsing.Token;
import parsing.Tokenizer;



public class Logic {
	//public String thinks;//the lustre code
	//public Hashtable<String, String> events = new Hashtable<String, String>();
	public static Hashtable<String, Node> nodes = new Hashtable<String, Node>();
	public static Hashtable<Node,Hashtable<Node,Object>> nodeDependencies = new Hashtable<Node, Hashtable<Node,Object>>();
	public static Tokenizer tokenizer = new Tokenizer(Tokenizer.LUSTRE_MODE);
	public static Hashtable<String, Node> flattenedNodes = new Hashtable<String, Node>();

	public static String inputText = ""; 
	
	public Logic()
	{}
	
	public static String getParams(QDDC... qddc )
	{
		return getParams("",qddc);
	}
	
	public static String getParams(String s, QDDC... qddc )
	{
		HashMap<Variable, Object> vars = new HashMap<Variable, Object>();
		for (QDDC q:qddc)
		{
			for (Variable v : Logic.nodes.get(q.acceptor).inputVars)
				if (!vars.containsKey(v))
					vars.put(v, null);
		}
		
		String params = "";
		for (Variable v:vars.keySet())
		{
			String sub = v + ":" + E.string(v.type);
			if (s.indexOf(sub) == -1)
				params += sub + ";";
		}
		
		if (params.length() == 0)
			return s.substring(0, s.length() - 1);
		else
			return s+params.substring(0, params.length()-1);
	}
	

	public static String getOrderedParamsNoTypes(String s, QDDC qddc )
	{		
		String params = "";
		for (Variable v:Logic.nodes.get(qddc.acceptor).inputVars)
		{
			if (s.indexOf(v.getName() + ",") == -1)
				params += v.getName() + ",";
		}
		
		if (params.length() == 0)
			return s.substring(0, s.length() - 1);
		else
			return s+params.substring(0, params.length()-1);
	}
	
	public void addParse(String thinks)throws Exception
	{
		inputText += "\r\n\r\n" + thinks;
		parse(thinks);
	}
	
	public void parse(String thinks)throws Exception
	{		
		int indx = 0;
		if (thinks.indexOf("tel",indx) == -1)
			throw new Exception("Missing keyword tel: " + thinks);
		while (thinks.indexOf("tel",indx) != -1)
		{
			int indx2 = thinks.indexOf("tel",indx)+3;
			String think = thinks.substring(indx,indx2);
			indx = indx2;			
			//try{
				ArrayList<Token> tokens = tokenizer.scan(think);
				
				Node node = new Node();
				int cnt = node.parse(0,tokens);
				//if (cnt != tokens.size()) throw new Exception("Unreached end of statement!");
				//nodes.put(node.name.text, node);this was put within the node code because in recursion this would not work!!!!!!
			//}catch(Exception ex)
			//{System.out.println("Error in node: "+think);}
		}
	}
	
	public void flattenAll()throws Exception
	{
		Node temp = nodes.get("boolg_4");
		//identify main node (from reference hashmap)
		for (Node h : nodeDependencies.keySet())
			for (Node n : nodeDependencies.get(h).keySet())
			{
//				if (n.name.text.contains("boolg"))
//				{
//					System.out.println("SDFSDF");
//				}
				if (!n.equals(h))
					n.mainNode = false;
			}
				
		
		//flatten each main node
		for (Node n: nodes.values())
			if (n.mainNode)
			{
				Node m = n.flatten(null,null);
				flattenedNodes.put(m.name.text, m);
			}
	}
	
	public void sortAllFlattened()throws Exception
	{
		for (Node n: flattenedNodes.values())
		{
			n.populateVarDeps();
			n.sort();
		}
	}
	
	public String toString()
	{
		String s = "";
		for (Node n : flattenedNodes.values())
			s += n.toString()+"\r\n\r\n";
		return s;
		
	}
	
	public String toLARVA()throws Exception
	{
		String s = "";
		for (Node n : flattenedNodes.values())
		{
			//int under = n.name.text.lastIndexOf("_");
			//if (under < n.name.text.length()-1 && Character.isDigit(n.name.text.charAt(under+1)))
				s += n.toLARVA()+"\r\n\r\n";
		}
		return s;
	}
	
////	public String toJava(Monitor m)throws Exception
//	{
//		String java = "";
//		Enumeration<Node> enrNodes = nodes.elements();
//		while (enrNodes.hasMoreElements())
//		{
//			java += enrNodes.nextElement().variableDeclarations()+"\r\n\r\n";
//		}
//		
//		java+="\r\npublic class GeneratedVerifier {\r\n\r\n";
//		//attribues decalarations
//		enrNodes = nodes.elements();
//		while (enrNodes.hasMoreElements())
//		{
//			String n =enrNodes.nextElement().name.text;
//			java += "InputsOf"+n+" inputsOf"+n+" = new InputsOf"+n+"();\r\n";
//			java += "OutputsOf"+n+" outputsOf"+n+" = new OutputsOf"+n+"();\r\n";
//			java += "LocalsOf"+n+" localsOf"+n+" = new LocalsOf"+n+"();\r\n";
//		}
//		//constructor
//		java += "\r\npublic GeneratedVerifier{\r\n";
//		enrNodes = nodes.elements();
//		while (enrNodes.hasMoreElements())
//		{
//			String n =enrNodes.nextElement().name.text;
//			java += "\r\nintializationOf"+n+"(inputsOf"+n+","+"outputsOf"+n+","+"localsOf"+n+");\r\n";
//		}
//		java += "}\r\n";
//		//run method
//		java += "\r\npublic void run(){\r\n";
//		java += "  while(true){\r\n";
//		java += "    if(queue.size()>0){\r\n";
//		java += "      Packet p = queue.get(0);\r\n";
//		java += "      queue.remove(0);\r\n";
//		java += "      long time = p.time;\r\n";
//		java += "      long encodedVars = Packet.encode(p.var);\r\n";
//		java += "      while(queue.size()>0 && queue.get(0).time == time){\r\n";
//		java += "        encodedVars = encodedVars | Packet.encode(queue.get(0).var);\r\n";
//		java += "        queue.remove(0);\r\n";
//		java += "      }\r\n";
//		java += "      checkStatus(encodedVars);\r\n";
//		java += "    }else{Thread.sleep(10);}\r\n}\r\n}\r\n";
//		//check status
//		java += "\r\npublic void checkStatus(long encodedVars){\r\n";
//		enrNodes = nodes.elements();
//		boolean first = true;
//		while (enrNodes.hasMoreElements())
//		{
//			if (first)
//				first = false;
//			else 
//				java += "else ";
//			java += enrNodes.nextElement().generateIf(m);
//			
//		}
//		java += "}\r\n";
//		enrNodes = nodes.elements();
//		while (enrNodes.hasMoreElements())
//			java += enrNodes.nextElement().generateUpdateOfInputs(m)+"\r\n";
//		enrNodes = nodes.elements();
//		while (enrNodes.hasMoreElements())
//			java += enrNodes.nextElement().toJava()+"\r\n";
//		java += "}";
//		return java;
//	}
}
