package main;

public class Editor {

	StringBuilder sb = new StringBuilder();
	
	public String insert(String s, Integer p)
	{
		return sb.insert(p, s).toString();
	}
	
	public String insert(String s)
	{
		return sb.append(s).toString();
	}
	
	public String delete(Integer p)
	{
		return sb.deleteCharAt(p).toString();
	}
	
	public String delete()
	{
		return sb.deleteCharAt(sb.length()-1).toString();
	}
	
	public String save()
	{ 
		return sb.toString();
	}
	
	public String undo()
	{
		return sb.toString();
	}
	
	public String undoOne()
	{
		return sb.toString();
	}
	
	public String get(Integer start, Integer end)
	{
		return sb.substring(start, end);
	}
	
	public Integer length()
	{
		return sb.length();
	}
}
