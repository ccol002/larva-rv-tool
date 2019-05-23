package PEA;

public class BoolOp {

	//public String op;//and or or

public enum Op {and,or};
	
	public Op op;
	
	public BoolOp(Op op){
		this.op = op;
	}
	
	public BoolOp clone()
	{
		return new BoolOp(op);
	}
	
	public String toString()
	{
		if (op.equals(Op.and))
			return "and";
		else
			return "or";
	}
}
