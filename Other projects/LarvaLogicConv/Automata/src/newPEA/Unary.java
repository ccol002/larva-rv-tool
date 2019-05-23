package newPEA;


public class Unary{

public enum Op {not};
	
	public Op op;
	
	public Unary()
	{
		op = Op.not;
	}
	
	public String toString ()
	{
		return "not";
	}
	
	public Unary clone()
	{
		return new Unary();
	}
}
