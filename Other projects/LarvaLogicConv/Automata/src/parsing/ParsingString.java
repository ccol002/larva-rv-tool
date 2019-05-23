package parsing;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

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
	
	public ParsingString(String filename)
	{
		try{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		StringBuilder text = new StringBuilder();
		String temp;
		while ((temp = br.readLine()) != null)   {
			if (temp.indexOf("%%") != -1)//remove comments
				temp=temp.substring(0,temp.indexOf("%%"));
			text.append(temp.trim() + "\r\n");
		}
		string = text;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public boolean hasMore()
	{
		return string != null && string.length()>0;
	}
	
	public ParsingString parseWrapper(String title) throws ParseException
	{
		return parseWrapper(title, true);

	}
	
	public ParsingString parseWrapper(String title,boolean strict) throws ParseException
	{
		String params;
		int start = string.indexOf("{");
		int tit;
		if (start == -1 && strict)
			throw new ParseException("Missing Delimeter: \"{\"");
		else if (start == -1)
			return new ParsingString("");
		else if (string.substring(0, start).trim().indexOf(title)!=0)
		{
			if  (strict)
			throw new ParseException("Missing "+title);
			else
				return new ParsingString("");
		}
		else
		{
			tit = string.substring(0, start).indexOf(title);
			params = string.substring(tit+title.length(), start);
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
				throw new ParseException("Missing Delimiter \"}\"");
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
		int start = string.indexOf("{");
		if (start==-1)
			return false;
		return (string.substring(0, start).trim().indexOf(title)==0);			
	}
	
	public String toString()
	{
		return string.toString();
	}
}
