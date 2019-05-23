package Lustre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import parsing.Token;
import parsing.Tokenizer;


public class Primitive {

	Expression initialization;
	Operator unary;
	Primitive expression;
	int pres;
	
	public int parse(int cnt, ArrayList<Token> tokens)throws Exception
	{
		if (Operator.isUnary(tokens.get(cnt)))
		{
			unary = new Operator();
			cnt = unary.parseUnary(cnt, tokens);
		}
		if (tokens.get(cnt).is("if"))//if
		{
			expression = new If();
			cnt = expression.parse(cnt, tokens);
		}
		else if (tokens.get(cnt).is("pre"))//pre
		{
			while (tokens.get(cnt).is("pre")){
				pres++;
				cnt++;
			}
		}
		else if (tokens.get(cnt).is("("))//(
		{
			cnt++;//(
			expression = new Expression();
			cnt = expression.parse(cnt, tokens);
			cnt++;//)
		}
		else if ((tokens.get(cnt).is("true")) || (tokens.get(cnt).is("false")) //true or false
				|| (Character.isDigit(tokens.get(cnt).text.charAt(0))) || (tokens.get(cnt).text.charAt(0)=='.'))//is a number
		{
			expression = new Value();
			cnt = expression.parse(cnt, tokens);
			
		}
		else if (Node.beingParsed.variables.containsKey(tokens.get(cnt).text))//is a variable
		{
			expression = new Variable();
			cnt = expression.parse(cnt, tokens);
		}
		else if (Logic.nodes.containsKey(tokens.get(cnt).text))//node call
		{
			expression = new NodeCall();
			cnt = expression.parse(cnt, tokens);
			Hashtable<Node, Object> deps = Logic.nodeDependencies.get(Node.beingParsed);
			Node n = Logic.nodes.get(((NodeCall)expression).name.text);
			deps.put(n, new Object());
		}
		else throw new Exception("Error in statement! "+Tokenizer.debugShow(tokens, cnt));
		
		//look for -> since this will be as a unary operation? on the next expression...
		//we should in fact search for another primitive 
			
		if (tokens.get(cnt).is("->"))//->
		{
			if (expression != null)
			{
				initialization = new Expression();
				initialization.expression = expression;
			}
			cnt++;//->
			while (tokens.get(cnt).is("pre"))//pre
			{
				pres++;
				cnt++;
			}
		}
		if (pres > 0)//->
		{
			boolean brack = false;
			if (tokens.get(cnt).is("("))//(
			{
				cnt++;
				brack = true;
			}
			expression = new Primitive();//FOR NOW THE INITIALIZATION CAN ONLY TAKE PLACE ON A VARIABLE!!!
			cnt = expression.parse(cnt, tokens);
			if (brack && tokens.get(cnt).is(")"))//)
				cnt++;
			else if (brack)
				throw new Exception(") Expected: " + Tokenizer.debugShow(tokens, cnt));

			
			//assignment for initialization
			
			//assignments for pre operators
			Variable preVar = null;
			Assignment ass;
			for (int i = 0; i < pres; i++)
			{
				preVar = new Variable(new Token("pre_"+(++Variable.unique)), expression.getType(), E.Io.LOCAL);
				Node.beingParsed.variables.put(preVar.var.text, preVar);
				ass = new Assignment(); 
				ass.LHS = preVar;
				ass.RHS = expression;
				Node.beingParsed.preStatements.add(ass);
			}
			expression = preVar;
			
			if (initialization != null)
			{
				ass = new Assignment();
				ass.LHS = preVar; 
				ass.RHS = initialization;
				Node.beingParsed.initializations.add(ass);
			}
		}
		return cnt;
	}

	public E.Type getType() throws Exception
	{
		return expression.getType();
	}
	
	public String toString()
	{
		String text = "";
		if (initialization != null)
			text += initialization.toString()+ "->";
		if (unary != null)
			 text += unary.toString() + " ";
		
		return text + expression.toString();
	}
	
	public Primitive clone()
	{
		Primitive p = new Primitive();
		if (initialization != null)
			p.initialization = initialization.clone();
		if (unary != null)
			p.unary = unary.clone();
		p.expression = expression.clone();
		p.pres = pres;
		return p;
	}
	
	public Primitive replaceAndFlatten(Node node, HashMap<Variable, Primitive> replacements)throws Exception
	{
		if (initialization != null)
			initialization = initialization.replaceAndFlatten(node, replacements);
		expression = expression.replaceAndFlatten(node, replacements);
		return this;
	}

	public Hashtable<Variable, Object> getVarList(Hashtable<Variable, Object> hashtable) {
		expression.getVarList(hashtable);
		return hashtable;
	}

	
	public String toJava() throws Exception
	{
		String java;
		if (unary != null)
			java = unary.toJava()+"("+expression.toJava()+")";
		else 
			java = expression.toJava();
		return java;
	}
}
