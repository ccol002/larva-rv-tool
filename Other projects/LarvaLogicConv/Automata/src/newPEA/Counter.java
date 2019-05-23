package newPEA;


public class Counter {

	public String name;
	
	public Variable predicate;
	
	public boolean equals(Object obj)
	{
		if (obj instanceof Counter)
			return ((Counter)obj).name.equals(name);
		else 
			return false;
	}
	
	public String toString ()
	{
		return name + "(" + predicate + ")";
	}
	
	public Counter clone()
	{
		return this;
	}

	
	
}
