package compiler;

public class ParsingString{

	StringBuilder string;
	String parameter;
	
	public ParsingString(StringBuilder sb)
	{
		this.string = sb;
	}
	
	public ParsingString(StringBuilder sb, String p)
	{
		this.string = sb;
		this.parameter = p;
	}
	
	public ParsingString(String s)
	{
		this.string = new StringBuilder(s);
	}
	
	public ParsingString parseWrapper(String title) throws ParseException
	{
		return parseWrapper(title, true);

	}
	
	public ParsingString parseWrapper(String title,boolean strict) throws ParseException
	{
		title = String.valueOf(title.charAt(0)).toUpperCase() + title.substring(1).toLowerCase();
		String params;
		int start = string.indexOf("{");
		int tit;
		if (start == -1 && strict)
			throw new ParseException("Missing Delimeter: \"{\" for section: " + title);
		else if (start == -1)
			return new ParsingString("");
		else if (string.substring(0, start).trim().indexOf(title)!=0
				&& string.substring(0, start).trim().indexOf(title.toUpperCase())!=0)
		{
			if  (strict)
			throw new ParseException("Missing "+title);
			else
			{
				//System.out.println("Warning: " + title + " section was not found...(possibly entered in wrong sequence)");
				return new ParsingString("");
			}
		}
		else
		{
			if (string.substring(0, start).indexOf(title) != -1)
				tit = string.substring(0, start).indexOf(title);
			else
				tit = string.substring(0, start).indexOf(title.toUpperCase());
			
			params = string.substring(tit + title.length(), start);
			start++;			
		}
		
		int end = start;
		int opening = start;
		boolean found = false;
		
		while (!found)
		{
			//no { found or } found before
		if ((opening = string.indexOf("{",opening)) > (end = string.indexOf("}",end)) || opening == -1)
		{
			if (end == -1)
				throw new ParseException("Missing Delimiter \"}\" for section: " + title);
			else
				found = true;
		}
		opening++;
		end++;
		}	
		StringBuilder ret = new StringBuilder(string.substring(start, end-1));
			
		string.delete(0, end);
		return new ParsingString(ret,params);
	}
	
	public boolean hasMore(String title)
	{
		title = String.valueOf(title.charAt(0)).toUpperCase() + title.substring(1).toLowerCase();
		int start = string.indexOf("{");
		if (start==-1)
			return false;
		String test = string.substring(0, start).trim();
		return (test.indexOf(title)==0) || (test.indexOf(title.toUpperCase())==0) ;			
	}
	
	public String toString()
	{
		return string.toString();
	}
}
