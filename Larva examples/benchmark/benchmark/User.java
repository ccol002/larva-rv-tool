package benchmark;

import java.util.ArrayList;

public class User {

ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	
	public Transaction addTransaction()
	{
		transactions.add(new Transaction(this));
		return transactions.get(transactions.size()-1);
	}

	public boolean removeTransaction(Transaction t)
	{
		return transactions.remove(t);
	}
}
