package nesting;

import java.util.ArrayList;

public class Account {

public static int sid = -1;
	
	public int id;
	
	ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	public User owner;
	
	public Account(User owner)
	{ 
		id = ++sid;
		this.owner = owner;
	}
	
	public Account(int id)
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
		if ((o instanceof Account) && (((Account)o).id == id))
			return true;
		else 
			return false;
	}
	
	public void addTransaction()
	{
		transactions.add(new Transaction(this));
	}
	
	public void deleteTransaction(int id)
	{
		transactions.remove(new Transaction(id));
	}
	
	public void transactionMenu(int id)
	{
		transactions.get(transactions.indexOf(new Transaction(id))).menu();
	}
	
	public String show()
	{
		String s = "";
		for (Transaction a:transactions)
			s += a.id + ", ";
		return s;
	}
	
	public void menu()
	{
		while (true)
		{
			System.out.println("****ACCOUNT MENU****");
			System.out.println("Transaction: "+show());
			System.out.println("1. add Transaction");
			System.out.println("2. delete Transaction");
			System.out.println("3. process all");
			System.out.println("4. edit Transaction");
			System.out.println("5. exit");
			switch(Bank.read())
			{
			case 1:addTransaction();break;
			case 2:Bank.write("Id: ");deleteTransaction(Bank.read());break;
			case 3:Bank.write("Id: ");process();break;
			case 4:Bank.write("Id: ");transactionMenu(Bank.read());break;
			case 5:owner.menu();break;
			}
		}
	}
	
	public String toString()
	{
		return "Account "+id;
	}
}
