package newPEA;

public class Clock {

	public String name;
	
	public boolean equals(Object obj)
	{
		if (obj instanceof Clock)
			return ((Clock)obj).name.equals(name);
		else 
			return false;
	}
	
	public String toString ()
	{
		return name;
	}
	
	public Clock clone()
	{
		return this;
	}
}
