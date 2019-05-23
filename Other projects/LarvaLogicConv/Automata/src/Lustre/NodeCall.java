package Lustre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import parsing.Token;
import parsing.Tokenizer;

public class NodeCall extends Primitive{

	public ArrayList<Primitive> pars = new ArrayList<Primitive>();
	public Token name;
	
	public int parse(int cnt, ArrayList<Token> tokens) throws Exception
	{
		name = tokens.get(cnt);
		cnt++;
		if (tokens.get(cnt).is("(")) cnt++;
		else throw new Exception("( Expected" + Tokenizer.debugShow(tokens, cnt));
		
		while (tokens.get(cnt).isNot(")"))//)
		{
			Primitive prim = new Expression();
			cnt = prim.parse(cnt, tokens);
			pars.add(prim);
			if (tokens.get(cnt).is(",")) cnt++;
			else if (!tokens.get(cnt).is(")"))
				throw new  Exception(", or ) Expected" + Tokenizer.debugShow(tokens, cnt));
		}
		cnt++;//)
		return cnt;
	}
	
	public E.Type getType() throws Exception
	{
		Node n = Logic.nodes.get(this.name.text);
		if (n.outputVars.size() > 1)
			throw new Exception("Type called on node call which returns more than one variable!!");
		else if (n.outputVars.size() < 1)
			throw new Exception("Type called on node call which returns no variables!!");
		else
		{
			for (Variable v : n.outputVars)
				return v.getType();
		}
		return null;//this will never be reached
	}
	
	public String toJava() throws Exception
	{
		throw new Exception("to Java called on node call");
	}
	
	public String toString()
	{
		String text = name.toString()+"(";
		for (Primitive p:pars)
		{
			text+=p.toString()+",";
		}
		if (pars.size() > 0)
			text = text.substring(0, text.length()-1);
		return text+")";
	}
	
	public NodeCall clone()
	{
		NodeCall n = new NodeCall();
		n.pars = new ArrayList<Primitive>();
		for (Primitive p : pars)
			n.pars.add(p.clone());
		n.name = name.clone();
		return n;
	}

	public Hashtable<Variable, Object> getVarList(Hashtable<Variable, Object> hashtable) {
	    for (Primitive p : pars)
	    	p.getVarList(hashtable);
		return hashtable;
	}
	
	public boolean equals(Object o)
	{
		if (o instanceof Node)
			return this.name.text.equals(((Node)o).name.text);
		else
			return false;
	}
	
	public Primitive replaceAndFlatten(Node node, HashMap<Variable, Primitive> replacements)throws Exception
	{
		Node toBeFlattened = Logic.nodes.get(name.text);
		
		//remove recursion
		if (toBeFlattened.equals(node))
			if (node.outputVars.size() == 1)
				return node.outputVars.get(0).replaceAndFlatten(node, replacements);
			else 
				throw new Exception("One return variable is allowed: Node " + node);
		
		
		//replace variables in parameters
		ArrayList<Primitive> newpars = new ArrayList<Primitive>();
		for (int i = 0; i < pars.size(); i++)
			newpars.add(pars.get(i).replaceAndFlatten(node, replacements));
		
		
		//only one output variable is assumed!! (this call will throw an exception if otherwise)
		Variable returned = new Variable(new Token(name.text + "_" + (++Variable.unique)), Logic.nodes.get(name.text).getType() ,E.Io.LOCAL);
		node.variables.put(returned.getName(), returned);
		
		
		//flatten the node (we need to pass parameters!!)
		//if (replacements == null)
			replacements = new HashMap<Variable, Primitive>();
		
		
		for (Variable v: toBeFlattened.variables.values())
		{
			if (v.io.equals(E.Io.LOCAL))
			{
				Variable newVar = new Variable(new Token(v.var.text+"_"+(++v.unique)),v.type,v.io);
				node.variables.put(newVar.getName(), newVar);
				replacements.put(v, newVar);
			}
			//input vars will be treated separately because their order is important!!!
			else if (v.io.equals(E.Io.OUT))
				replacements.put(v, returned);	//connect nodes (by returned object)
		}
		
		
		int p = -1;
		for (Variable v: toBeFlattened.inputVars)
		{
			if (++p < pars.size())
			{
				Primitive prim = newpars.get(p);
				if (v.type.equals(prim.getType()))
					replacements.put(v, prim);
				else
					throw new Exception("Type mismatch!! Variable: " + v + " Replacement: " + prim + " in " + node.name);
			}
			else
				throw new Exception("Parameter List Mismatch!! Node: " + toBeFlattened.getSignature() + " Params: " + pars);
		}
		
		if (p != pars.size()-1)
			throw new Exception("Parameter List Mismatch!! Node: " + toBeFlattened.getSignature()  + " Params: " + pars);
		
		//actual flattening
		Node flattened = toBeFlattened.flatten(node, replacements);
		
		
		//update statements
		for (Assignment a : flattened.statements)
			node.statements.add(a);
		
		for (Assignment a : flattened.preStatements)
			node.preStatements.add(a);
		
		for (Assignment a : flattened.initializations)
			node.initializations.add(a);
		
		return returned;
	}
}
