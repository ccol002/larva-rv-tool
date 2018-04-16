package benchmark;

import java.util.ArrayList;

public class User {

ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	
/*@
offline
scope = class
logic = ERE
Transcount{
[int count = 0;]
event add : end(call(* *.addTransaction())){count++;}
formula : add add *
}
Validation handler{
if (@MONITOR.count < 5)
   ;//System.out.println("ok!")
else
   ;//System.out.println("qbadtek!");
}
@*/
	
	
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







