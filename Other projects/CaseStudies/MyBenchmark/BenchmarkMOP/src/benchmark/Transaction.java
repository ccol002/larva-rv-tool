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
	
	/*
	scope = class
	logic = ERE
	TransExc{
	event exec1 : end(call(* *.execute()));
	event exec2 : end(call(* *.execute()))throwing (Exception e);//NOT SUPPORTED!!!!!!!!!!

	formula : (exec2  exec1)
	}
	Validation handler{
	   System.out.println("Exception! Cannot retry!!!");
	}
	*/
	
	/*@
	scope = class
	logic = ERE
	TransExc{
	[double amount1 = 0; double oldamount = 0;]
	event exec1 : end(call(* *.setAmount(double d))){amount1 = d;};
	event upd   : update (Transaction.amount){amount1 = amount;};

	formula : (exec1 + upd)
	}
	Validation handler{
	   if (amount1 != oldamount && oldamount != 0)
			System.out.println("Cannot change amount!!!");
		else 
			oldamount = amount1;
	}
	@*/
	
	

	public boolean execute()throws Exception
	{
		return Bank.getConnection().execute(this);
	}
}




