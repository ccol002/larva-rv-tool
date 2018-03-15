package benchmark;

public class Transaction {

	double amount = 0;
	User owner;
	
	public Transaction(User u)
	{
		owner = u;
	}
	
	public void setAmount(double amount)
	{
		this.amount = amount;
	}
	
	public double getAmount()
	{
		return amount;
	}

	public User getOwner()
	{return owner;}

	public boolean execute()throws Exception
	{
		return Bank.getConnection().execute(this);
	}
}
