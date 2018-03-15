package benchmark;

public class User {

	public Transaction addTransaction()
	{
		return new Transaction(this);
	}

	public void removeTransaction(){}
}
