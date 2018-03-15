package nesting;

public class Transaction {

public static int sid = -1;
	
	public int id;
	
	public Account account;
	
	public Transaction(Account account)
	{ 
		id = ++sid;
		this.account = account;
	}
	
	public Transaction(int id)
	{
		this.id = id;
	}
	
	public void initialize()
	{}
	
	public void process()
	{}
	
	public void delete()
	{}
	
	public boolean equals(Object o)
	{
		if ((o instanceof Transaction) && (((Transaction)o).id == id))
			return true;
		else 
			return false;
	}
	
	public void menu()
	{
		while (true)
		{
			System.out.println("****TRANSACTION MENU****");
			//System.out.println("Transaction: "+show());
			//System.out.println("1. add Transaction");
			//System.out.println("2. delete Transaction");
			System.out.println("1. process");
			//System.out.println("4. edit Transaction");
			System.out.println("2. exit");
			switch(Bank.read())
			{
			case 1:process();break;
			//case 2:Bank.write("Id: ");deleteTransaction(Bank.read());break;
			//case 3:Bank.write("Id: ");process();break;
			//case 4:Bank.write("Id: ");transactionMenu(Bank.read());break;
			case 2:account.menu();break;
			}
		}
	}
	
	public String toString()
	{
		return "Transaction "+id;
	}
}
