package regularExpressions;

//RE+RE

public class REOr extends RE{
	public RE parse()
	{
		return new REOr();
	}
}
