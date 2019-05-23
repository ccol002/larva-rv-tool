package PEA;

public class Operator {

	public enum Op {gt,gteq,ls,lseq,none};
	
	public Op op;
	
	public Operator(Op op)
	{
		this.op = op;
	}
	
	public String toString()
	{
		if (op.equals(Op.gt))
			return ">";
		else if (op.equals(Op.gteq))
			return ">=";
		else if (op.equals(Op.ls))
			return "<";
		else if (op.equals(Op.lseq))
			return "<=";
		else return "INVALID OPERATOR!!!!";
	}
	
	public Operator clone()
	{
		return new Operator(op);
	}
}
