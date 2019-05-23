package QDDC.G;

import java.util.ArrayList;

import Lustre.Lustre;
import QDDC.QDDC;
import QDDC.F.F;
import QDDC.P.P;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;

public class Bounded extends G{
	P p;
	Object value;
	Object len;
		
	public Bounded()
	{}
	
	//starting from the operator
	//bounded(P,c,l)
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("bounded"))
			cnt++;
		else throw new ParseException("bounded Expected: " + Tokenizer.debugShow(tokens, cnt));

		int brackets = 0;
		while (tokens.get(cnt).is("("))
		{
			brackets++;
			cnt++;
		}
		
		p = new P();
		cnt = p.parse(tokens,cnt);
		
		if (tokens.get(cnt).is(","))
			cnt++;
		else throw new ParseException(", Expected: " + Tokenizer.debugShow(tokens, cnt));
		
		value = tokens.get(cnt).getNumber();
		cnt++;

		if (tokens.get(cnt).is(","))
			cnt++;
		else throw new ParseException(", Expected: " + Tokenizer.debugShow(tokens, cnt));
		
		len = tokens.get(cnt).getNumber();
		cnt++;
		
		
		while (brackets > 0 && tokens.get(cnt).is(")"))
		{
			brackets--;
			cnt++;
		}
		if (brackets != 0) throw new ParseException(") Expected: " + Tokenizer.debugShow(tokens, cnt));
		
		return cnt;
	}
	
	public String toString()
	{
		return "bounded (" + p + ", " + value + ", " + len + ")";
	}
	
	public void createAcceptor()throws Exception
	{
		if (acceptor == null){
			p.createAcceptor();
			
			
			acceptor = "bounded_"+(++QDDC.unique);
			StringBuilder sb = new StringBuilder("node " + acceptor + "("+ getParams("_b:bool; _rt_clock:int;",p) +")returns(_p:bool);" +
					" var now:bool;count:int; ");
			for (int i = 0; i < (Double)value; i++)
				sb.append(" temp_"+i+":int;");
			
			sb.append(" let " +
					"  now = " + p.getSignature() + " and after(_b);" +
							" count = if (now) then (0->pre count)+1 else (0->pre count);");
						
			sb.append(" temp_0 = if (now) then _rt_clock else (0->pre temp_0);");
			
			int size = 0;
			for (int i = 1; i < (Double)value; i++)
			{
				size = i;
				sb.append(" temp_"+i+" = if (now) then temp_" + (i-1) + " else (0->pre temp_"+i+");");
			}
			
			sb.append("  _p = if (count > "+ value +" and now and (_rt_clock-(0-> pre temp_"+size+")<="+len+")) then false else (true->pre _p);" +
					" tel");
			
			Lustre.logic.addParse(sb.toString());
		}
	}
	
}
