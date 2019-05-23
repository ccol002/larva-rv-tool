package CEform.P;

import java.util.ArrayList;

import Lustre.Logic;
import Lustre.Lustre;
import PEA.BoolOp;
import QDDC.QDDC;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;

public class Bool extends P{

	P lhs;
	Token operator;
	P rhs;
		
	public Bool(P p)
	{
		lhs = p;
	}
	
	//starting from the operator
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		if (tokens.get(cnt).is("and")||tokens.get(cnt).is("or"))
			operator = tokens.get(cnt);
		else throw new ParseException("Operator Expected: " + Tokenizer.debugShow(tokens, cnt));
		cnt++;
		rhs = new P();
		cnt = rhs.parse(tokens, cnt);
		return cnt;
	}
	
	public String toString()
	{
		return "(" + lhs + ") " + operator + " (" + rhs + ")";
	}
	
	public PEA.Bool toPEABool()
	{
		PEA.Bool bool = new PEA.Bool();
		try{
		bool.lhs = lhs.toPEABool();
		if (operator != null)
		{
			
			if (operator.is("and"))
				bool.op = new BoolOp(BoolOp.Op.and); 
			else if (operator.is("or"))
				bool.op = new BoolOp(BoolOp.Op.or);
			bool.rhs = rhs.toPEABool();
		}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return bool;
	}
}
