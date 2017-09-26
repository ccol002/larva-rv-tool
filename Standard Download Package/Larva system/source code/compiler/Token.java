package compiler;

public class Token {

	public static int GUID_START = 100;
	
	public static int guid = GUID_START;
	private int id;
	public String text;
	
	public Token(int id, String text)
	{
		this.id = id;
		this.text = text; 
	}
	
//	public Token(int id)
//	{
//		this.id = id;
//		this.text = null; 
//	}
	
	public Double getNumber()throws ParseException
	{
		try{
			return Double.parseDouble(text);
		}
		catch(Exception ex)
		{
			throw new ParseException("Number Expected! Found: " + text);
		}
	}
	
	public Boolean getBoolean()throws ParseException
	{
		try{
			return Boolean.parseBoolean(text);
		}
		catch(Exception ex)
		{
			throw new ParseException("Boolean Expected! Found: " + text);
		}
	}
	
	
	public boolean is(int id)throws ParseException
	{
		return id == this.id;
	}
	
	public boolean isNot(int id)throws ParseException
	{
		return id != this.id;
	}
	
	public boolean isIdentifier()
	{
		return id >= GUID_START;
	}
	
	public boolean is(String token)throws ParseException
	{
		return Tokenizer.is(token,id);
	}
	
	public boolean isNot(String token)
	{
		return Tokenizer.isNot(token,id);
	}
	
	public boolean equals(Object o)
	{
		if ((o instanceof Token) && (((Token)o).id == id) && (((Token)o).text.equals(text)))
		{
			return true;
		}
		else 
			return false;
	}
	
	public int hashCode()
	{
		return text.hashCode();
	}
	
	public Token(String text)
	{
		this.id = guid;
		guid++;
		this.text = text; 
	}
	
	public String toString()
	{
//		if (id<100)
//			return String.valueOf(id);
//		else
			return text;
	}

	public String toString2()
	{
		return text.replace("\"", "\\"+"\"");
	}
}
