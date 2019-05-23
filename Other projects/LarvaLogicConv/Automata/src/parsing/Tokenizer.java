package parsing;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class Tokenizer {

	public ArrayList<Token> tokens;
	Hashtable<String, Token> identifiers;
	
	public static Hashtable<Integer, Hashtable<String, Integer>> keyWords = new Hashtable<Integer, Hashtable<String,Integer>>();
	
	public static Hashtable<String, Integer> allKeyWords = new Hashtable<String, Integer>();
	
	public String stopChars;
	public String whiteSpace = "\t\n\r ";
	public String starting;//the possible starts of symbols with 2 characters
	
	public static final int EVENT_MODE = 0;
	public static final int AUTOMATON_MODE = 1;
	public static final int ACTION_MODE = 2;
	public static final int QDDC_MODE = 3;
	public static final int LUSTRE_MODE = 4;
	public static final int CE_MODE = 5;
	public static final int CEform_MODE = 6;
	int mode;
	
	public Tokenizer(int mode)
	{
		this.mode = mode;
		switch(mode){
		case EVENT_MODE    : initializeEvents();break;
		case AUTOMATON_MODE: initializeAutomaton();break;
		case ACTION_MODE: initializeActions();break;
		case QDDC_MODE: initializeQDDC();break;
		case LUSTRE_MODE:initializeLustre();break;
		case CE_MODE:initializeCE();break;
		case CEform_MODE:initializeCEform();break;
		}
	}
	
	private void initializeQDDC() {
		stopChars = "()<>=![],";
		starting = "<>![]=";//the possible starts of symbols with 2 characters or 3 characters
		
		if (!keyWords.containsKey(mode))
		{
		keyWords.put(mode, new Hashtable<String, Integer>());
				
		putKeyWord("begin");
		putKeyWord("end");
		putKeyWord("age");
		putKeyWord("agef");
		putKeyWord("leadsto");
		putKeyWord("persist");
		putKeyWord("repeat");
		putKeyWord("stable");
		putKeyWord("always");
		putKeyWord("eventually");
		putKeyWord("ithen");
		putKeyWord("count");
		putKeyWord("then");
		putKeyWord("thenstar");
		putKeyWord("len");
		putKeyWord("sigma");
		putKeyWord("true");
		putKeyWord("false");
		putKeyWord("bounded");
		putKeyWord("rising");
		putKeyWord("[[");
		putKeyWord("[");
		putKeyWord("]");
		putKeyWord("]0");
		putKeyWord("]+");
		putKeyWord("]]");
		putKeyWord("]]+");
		putKeyWord("<");
		putKeyWord(">");
		putKeyWord("<=");
		putKeyWord(">=");
		putKeyWord("==");
		putKeyWord("!=");
		putKeyWord("and");
		putKeyWord("or");
		putKeyWord("not");
		putKeyWord("(");
		putKeyWord(")");
		putKeyWord(",");
		}
	}

	private void initializeCE() {
		stopChars = "(),";
		starting = "";//the possible starts of symbols with 2 characters or 3 characters
		
		if (!keyWords.containsKey(mode))
		{
		keyWords.put(mode, new Hashtable<String, Integer>());
				
		putKeyWord("init");
		putKeyWord("seq");
		putKeyWord("prog");
		putKeyWord("stab");
		putKeyWord("synch");
		putKeyWord("rising");
		putKeyWord("true");
		putKeyWord("false");
		putKeyWord("and");
		putKeyWord("or");
		putKeyWord("not");
		putKeyWord("(");
		putKeyWord(")");
		putKeyWord(",");
		}
	}

	private void initializeCEform() {
		stopChars = "()<>=!,;[]";
		starting = "<>!=";//the possible starts of symbols with 2 characters or 3 characters
		
		if (!keyWords.containsKey(mode))
		{
		keyWords.put(mode, new Hashtable<String, Integer>());
				
		putKeyWord("change");
		putKeyWord("invchange");
		putKeyWord("nochange");
		putKeyWord("len");
		putKeyWord("cnt");
		putKeyWord("true");
		putKeyWord("false");
		putKeyWord("rising");
		putKeyWord("<");
		putKeyWord(">");
		putKeyWord("<=");
		putKeyWord(">=");
		putKeyWord("==");
		putKeyWord("!=");
		putKeyWord("and");
		putKeyWord("or");
		putKeyWord("not");
		putKeyWord("(");
		putKeyWord(")");
		putKeyWord(",");
		putKeyWord(";");
		putKeyWord("[");
		putKeyWord("]");
		}
	}

	
	public void putKeyWord(String text)
	{
		int id;
		if (allKeyWords.containsKey(text))
			id = allKeyWords.get(text);
		else
		{
			id = allKeyWords.size();
			allKeyWords.put(text, id);
		}
		if (!keyWords.get(mode).containsKey(text))
		{
			keyWords.get(mode).put(text, id);
		}
	}
	
	public boolean isKeyword(String keyword)
	{
		return keyWords.get(mode).containsKey(keyword);
	}
	
	public void initializeEvents()
	{
		stopChars = "()=;.{}*,|";
		starting = "";//the possible starts of symbols with 2 characters
		
		if (!keyWords.containsKey(mode))
		{
		keyWords.put(mode, new Hashtable<String, Integer>());
				
		putKeyWord(".");
		putKeyWord("=");
		putKeyWord(";");
		putKeyWord("(");
		putKeyWord(")");
		putKeyWord("where");
		putKeyWord("{");
		putKeyWord("}");
		putKeyWord("*");
		putKeyWord(",");
		putKeyWord("|");
		putKeyWord("uponThrowing");
		putKeyWord("uponHandling");
		putKeyWord("uponReturning");
		putKeyWord("equateUsing");
		putKeyWord("stringUsing");
		}
	}
	
	public void initializeAutomaton()
	{
		stopChars = ",[]:->\\()!=<+-*&|{}%\";";
		starting = "-|&><=+";//the possible starts of symbols with 2 characters
		
		if (!keyWords.containsKey(mode))
		{
		keyWords.put(mode, new Hashtable<String, Integer>());
		
		putKeyWord("[");
		putKeyWord("]");
		putKeyWord(":");
		putKeyWord("(");
		putKeyWord(")");
		putKeyWord("->");
		putKeyWord("\\");
		putKeyWord("+");
		putKeyWord("-");
		putKeyWord("/");
		putKeyWord("*");
		putKeyWord("<");
		putKeyWord(">");
		putKeyWord(">=");
		putKeyWord("<=");
		putKeyWord("==");
		putKeyWord("!=");
		putKeyWord("%");
		putKeyWord("&&");
		putKeyWord("||");
		putKeyWord("=");
		putKeyWord("+=");
		putKeyWord("-=");
		putKeyWord("++");
		putKeyWord("--");
		putKeyWord("\"");
		putKeyWord(";");
		putKeyWord("!");
		putKeyWord(",");
		putKeyWord("enableCheck");
		putKeyWord("disableCheck");
		putKeyWord("on");
		}
	}
	
	public void initializeLustre()
	{
			stopChars = ":;()=<>-+*/,%!";
			starting = "<>-!=";//the possible starts of symbols with 2 characters or 3 characters
			
			if (!keyWords.containsKey(mode))
			{
			keyWords.put(mode, new Hashtable<String, Integer>());
			
			putKeyWord("node");
			putKeyWord(":");
			putKeyWord("%");
			putKeyWord(";");
			putKeyWord("(");
			putKeyWord(")");
			putKeyWord("let");
			putKeyWord("tel");
			putKeyWord("and");
			putKeyWord("or");
			putKeyWord("not");
			putKeyWord("<");
			putKeyWord(">");
			putKeyWord("<=");
			putKeyWord(">=");
			putKeyWord("!=");
			putKeyWord("==");
			putKeyWord("int");
			putKeyWord("bool");
			putKeyWord("real");		
			putKeyWord("returns");		
			putKeyWord("->");
			putKeyWord("pre");		
			putKeyWord("if");
			putKeyWord("then");
			putKeyWord("true");		
			putKeyWord("false");
			putKeyWord("var");
			putKeyWord("+");		
			putKeyWord("-");		
			putKeyWord("*");		
			putKeyWord("/");
			putKeyWord(",");
			putKeyWord("else");
			putKeyWord("=");
			}
	}
	
	public void initializeActions()
	{
		stopChars = "(){}\"";
		starting = "";//the possible starts of symbols with 2 characters
		
		if (!keyWords.containsKey(mode))
		{
		keyWords.put(mode, new Hashtable<String, Integer>());

		putKeyWord("{");
		putKeyWord("}");
		putKeyWord("(");
		putKeyWord(")");
		putKeyWord("\"");
		putKeyWord("if");
		}
	}
	
	public static boolean is(String string, int id)throws ParseException
	{
		if (allKeyWords.containsKey(string))
			return allKeyWords.get(string).equals(id);
		else
			throw new ParseException("KeyWord Not Found: " + string);
	}
	
	public static boolean isNot(String string, int id)
	{
		if (allKeyWords.containsKey(string))
			return !allKeyWords.get(string).equals(id);
		else
			return false;
	}
	
	public ArrayList<Token> scan(String text)throws ParseException
	{		
		tokens = new ArrayList<Token>();
		identifiers = new Hashtable<String, Token>();
		int indx = 0;
		
		while (indx < text.length())
		{
			int indx2 = indx;
			while (indx2 < text.length() && stopChars.indexOf(text.charAt(indx2))==-1 && whiteSpace.indexOf(text.charAt(indx2))==-1)
				indx2++;
			
			String cut = text.substring(indx,indx2);

			if (!identifiers.containsKey(cut) && !cut.equals("") && !isKeyword(cut))
			{
				Token t = new Token(cut);//automatically given an id >100
				identifiers.put(cut, t);
				tokens.add(t);
			}
			else if (identifiers.containsKey(cut))
			{
				tokens.add(identifiers.get(cut));
			}
			else if (!cut.equals("") && isKeyword(cut))
			{
				tokens.add(new Token(get(cut),cut));
			}
				
			//pick the symbol which caused the string to split..
			//it may be a two character symbol! BUT NOT 3!
			String symbol1 = null;
			String symbol2 = null;
			String symbol3 = null;
			if (indx2<text.length())
			{
				symbol1 = String.valueOf(text.charAt(indx2));
			
				if (indx2<text.length()-1 && starting.contains(symbol1))//keyWords.containsKey(symbol1))
					symbol2 = symbol1+String.valueOf(text.charAt(indx2+1));
				if (indx2<text.length()-2 && starting.contains(symbol1))//keyWords.containsKey(symbol1))
					symbol3 = symbol2+String.valueOf(text.charAt(indx2+2));
				
				if (symbol3 != null && isKeyword(symbol3))
				{
					tokens.add(new Token(get(symbol3),symbol3));
					indx2++;indx2++;
				}	
				else if (symbol2 != null && isKeyword(symbol2))
				{
					tokens.add(new Token(get(symbol2),symbol2));
					indx2++;
				}			
				else if (isKeyword(symbol1))
					tokens.add(new Token(get(symbol1),symbol1));
			}
			indx = indx2+1;
			
			//gather a string
			if (tokens.size()>0 && isKeyword("\"") && tokens.get(tokens.size()-1).is("\""))
			{
				int temp;
				if ((temp = text.indexOf("\"",indx)) != -1)
				{
					indx2 = temp;
					tokens.add(new Token(text.substring(indx,indx2)));
					tokens.add(new Token(get("\""), "\""));
					indx = indx2 + 1;					
				}
			}
			
			//remove white space
			while (indx < text.length() && whiteSpace.indexOf(text.charAt(indx))!=-1)
				indx++;
		}
		//System.out.println(Show(tokens));
		return tokens;
	}

	public static int get(String string)throws ParseException
	{
		if (allKeyWords.containsKey(string))
			return allKeyWords.get(string);
		else
			throw new ParseException("KeyWord Not Found!!");
	}
	
	public static ArrayList<Token> startingEnding(int cnt, String starting, String ending, List<Token> tokens) throws ParseException
	{
		return startingEnding(cnt, get(starting), get(ending), tokens);
	}
	
	public static ArrayList<Token> ending(int cnt, String ending, ArrayList<Token> tokens) throws ParseException
	{
		return ending(cnt, get(ending), tokens);
	}
	
	public static ArrayList<Token> endingPoss(int cnt, String ending, ArrayList<Token> tokens) throws ParseException
	{
		return endingPoss(cnt, get(ending), tokens);
	}
	
	public static ArrayList<Token> twoEnding(int cnt, String ending1, String ending2, ArrayList<Token> tokens) throws ParseException
	{
		return twoEnding(cnt, get(ending1), get(ending2), tokens);
	}
		
	private static ArrayList<Token> startingEnding(int cnt, int starting, int ending, List<Token> tokens) throws ParseException
	{
		int opens = 0;
		ArrayList<Token> list = new ArrayList<Token>();
		
		if (cnt < tokens.size() && tokens.get(cnt).is(starting)){//(
			while ((cnt < tokens.size()-1 && tokens.get(++cnt).isNot(ending))
					|| (opens != 0))//)
			{
				if (tokens.get(cnt).is(starting))
					opens++;
				if (tokens.get(cnt).is(ending))
					opens--;
				list.add(tokens.get(cnt));
			}
			if (cnt >= tokens.size() || tokens.get(cnt).isNot(ending))
				throw new ParseException("Missing Closing Delimiter : " + debugShow(tokens, cnt));
		}
		else
			throw new ParseException("Missing Opening Delimiter : " + debugShow(tokens, cnt));
		
		return list;
	}
	
	private static ArrayList<Token> ending(int cnt, int ending, ArrayList<Token> tokens) throws ParseException
	{
		ArrayList<Token> list = new ArrayList<Token>();
		
			while (cnt < tokens.size() && tokens.get(cnt).isNot(ending))
			{
				list.add(tokens.get(cnt++));
			}
			if (cnt >= tokens.size() || tokens.get(cnt).isNot(ending))
				throw new ParseException("Missing Closing Delimiter : " + debugShow(tokens, cnt));
		
		
		return list;
	}
	
	private static ArrayList<Token> endingPoss(int cnt, int ending, ArrayList<Token> tokens) throws ParseException
	{
		ArrayList<Token> list = new ArrayList<Token>();
		
			while (cnt < tokens.size() && tokens.get(cnt).isNot(ending))
			{
				list.add(tokens.get(cnt++));
			}
			if (cnt < tokens.size() && tokens.get(cnt).isNot(ending))
				throw new ParseException("Missing Closing Delimiter : " + debugShow(tokens, cnt));
		
		
		return list;
	}
	
	private static ArrayList<Token> twoEnding(int cnt, int ending1, int ending2, ArrayList<Token> tokens) throws ParseException
	{
		ArrayList<Token> list = new ArrayList<Token>();
		
			while (cnt < tokens.size() && tokens.get(cnt).isNot(ending1) && tokens.get(cnt).isNot(ending2))
			{
				list.add(tokens.get(cnt++));
			}
			if (cnt >= tokens.size() || (tokens.get(cnt).isNot(ending1)  && tokens.get(cnt).isNot(ending2)))
				throw new ParseException("Missing Closing Delimeter : " + debugShow(tokens, cnt));
		
		
		return list;
	}
	
	public static String Show(ArrayList<Token> tokens)
	{
		String show = "";
		for (int i = 0; i < tokens.size(); i++)
		{
			show += tokens.get(i).toString() + " ";
		}
		return show;
	}
	
	public static String debugShow(List<Token> tokens, int cnt)
	{
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (Token t : tokens)
		{
			if (i++ == cnt)
				sb.append(" >>>>>"+t.toString()+ "<<<<< ");
			else
				sb.append(t.toString()+ " ");
		}
		if (i == cnt)
			sb.append(" >>>>><<<<<");
		return sb.toString();
	}
	
	public static String show(List<Token> tokens)
	{
		StringBuilder sb = new StringBuilder();
		for (Token t : tokens)
			sb.append(t.toString()+ " ");
		return sb.toString();
	}
	
	public static String showStats(List<Token> tokens)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tokens.size(); i++)
		{
			Token t= tokens.get(i);
			if (t.text.equals(";"))
				sb.append(t.toString()+ "\r\n");
			else if (t.isIdentifier() && (i == tokens.size()-1 || tokens.get(i+1).isNot("\"")))
			{
				sb.append(t.toString() + " ");
			}
			else
				sb.append(t.toString());
		}
		return sb.toString();
	}
	
//	public static String showNoSpace(List<Token> tokens)
//	{
//		StringBuilder sb = new StringBuilder();
//		for (Token t : tokens)
//			sb.append(t.toString());
//		return sb.toString();
//	}
	
	public static String show2(List<Token> tokens)
	{
		StringBuilder sb = new StringBuilder();
		for (Token t : tokens)
			sb.append(t.toString2()+ " ");
		return sb.toString();
	}

}
