package compiler;

import java.util.ArrayList;

public class Foreach extends Global{


	
	public Foreach(Global parent, ParsingString string) throws ParseException
	{
		super(string);
		this.parent = parent;
		headerParse();
		parse();
		
	}
	
	public void headerParse()throws ParseException
	{
		Tokenizer tok = new Tokenizer(Tokenizer.EVENT_MODE);
		ArrayList<Token> tokens = tok.scan(ps.parameter);
		
		//how much "," does tokens contains - Variables count
		int tempCommaCount = 0;
		ArrayList<Integer> commaList = new ArrayList<Integer>();
		for (int i = 0; i < tokens.size(); i++)
		{
			if (tokens.get(i).text.equals(","))
			{
				commaList.add(i);
				tempCommaCount++;
			}
		}
		
		
		int tokenPosition = 0;
		
		for (int k = 0; k <= tempCommaCount; k++)//For every variable
		{
			Variable var = new Variable();
			
			//how much "." does variable contains
			int tempDotCount = 0;
			ArrayList<Integer> dotList = new ArrayList<Integer>();
			int startIndex = (k == 0) ? 0 : commaList.get(k-1);
			int endIndex = (k == tempCommaCount) ? tokens.size() : commaList.get(k);
			for (int j = startIndex; j < endIndex; j++)
			{
				if (tokens.get(j).text.equals("."))
				{
					dotList.add(j);
					tempDotCount++;
				}
			}
			//Nested type
			if (tempDotCount > 0)
			{
				String concatenatedTokens = "";
				for (int j = ((dotList.get(dotList.size() - 1)).intValue()) + 1; j >= 1; j--)
				{
					concatenatedTokens += tokens.get(1).text;
					tokens.remove(1);
				}
				Token token = new Token(concatenatedTokens);
				
				var.type = token;
				var.name = tokens.get(1);
				
			}
			//Normal type
			else
			{
				var.type = tokens.get(++tokenPosition);
				var.name = tokens.get(++tokenPosition);
			}

			
			if (tokenPosition+1 < tokens.size() && tokens.get(tokenPosition+1).is("equateUsing"))
			{
				tokenPosition++;
				if (tokenPosition+1 < tokens.size() && tokens.get(tokenPosition+1).isIdentifier())
				{
					equateMethods.add(tokens.get(tokenPosition+1).text);
					tokenPosition++;
				}
				else
					throw new ParseException("Identifier Expected: "+Tokenizer.debugShow(tokens, tokenPosition+1));
					
			}
			else
				equateMethods.add(null);
			
			if (tokenPosition+1 < tokens.size() && tokens.get(tokenPosition+1).is("stringUsing"))
			{
				tokenPosition++;
				if (tokenPosition+1 < tokens.size() && tokens.get(tokenPosition+1).isIdentifier())
				{
					stringMethods.add(tokens.get(tokenPosition+1).text);
					tokenPosition++;
				}
				else
					throw new ParseException("Identifier Expected: "+Tokenizer.debugShow(tokens, tokenPosition+1));
					
			}
			else
				stringMethods.add(null);
			
			if (context.size() > 0)
				context.add(new Token(Tokenizer.get(","),","));
			context.add(var.name);
			variables.add(var);
			contextVariables.put(var.name.text, var);
			
			tokenPosition++;//to account for comma
		}
		
//		for (int i = 1; i < tokens.size()-1; i++)//brackets (or comma) are included
//		{
//			Variable var = new Variable();
//			
//			//how much "." does tokens contains
//			int tempDotCount = 0;
//			ArrayList<Integer> dotList = new ArrayList<Integer>();
//			for (int j = 0; j < tokens.size(); j++)
//			{
//				if (tokens.get(j).text.equals("."))
//				{
//					dotList.add(j);
//					tempDotCount++;
//				}
//			}
//
//			ArrayList<Token> list = null;
//			if (tempDotCount > 1)
//			{
//				list = new ArrayList<Token>();
//				String concatenatedTokens = "";
//				for (int j = ((dotList.get(dotList.size() - 2)).intValue()) + 1; j >= 0; j--)
//				{
//					concatenatedTokens += tokens.get(0).text;
//					tokens.remove(0);
//				}
//				Token token = new Token(concatenatedTokens);
//				list = Tokenizer.ending(cnt, ".", tokens);//.
//				list.add(0, token);
//				cnt += list.size();//.
//			}
//			else
//			{
//				var.type = tokens.get(i++);
//				var.name = tokens.get(i++);
//			}
//			
//			
//			
//			
//			if (i < tokens.size() && tokens.get(i).is("equateUsing"))
//			{
//				if (i+1 < tokens.size() && tokens.get(i+1).isIdentifier())
//				{
//					equateMethods.add(tokens.get(i+1).text);
//					i+= 2;
//				}
//				else
//					throw new ParseException("Identifier Expected: "+Tokenizer.debugShow(tokens, i+1));
//					
//			}
//			else
//				equateMethods.add(null);
//			
//			if (i < tokens.size() && tokens.get(i).is("stringUsing"))
//			{
//				if (i+1 < tokens.size() && tokens.get(i+1).isIdentifier())
//				{
//					stringMethods.add(tokens.get(i+1).text);
//					i+= 2;
//				}
//				else
//					throw new ParseException("Identifier Expected: "+Tokenizer.debugShow(tokens, i+1));
//					
//			}
//			else
//				stringMethods.add(null);
//			
//			if (context.size() > 0)
//				context.add(new Token(Tokenizer.get(","),","));
//			context.add(var.name);
//			variables.add(var);
//			contextVariables.put(var.name.text, var);
//		}
	}
	
	
}
