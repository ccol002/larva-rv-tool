package nesting;

import java.util.ArrayList;

public class User {

	public static int sid = -1;
	
	public int id;
	
	public ArrayList<Account> accounts = new ArrayList<Account>();
	
	public Bank location;
	
	
	public User(Bank location)
	{ 
		id = ++sid;
		this.location = location;
	}
	
	public User(int id)
	{
		this.id = id;
	}
	
	public boolean equals(Object o)
	{
		if ((o instanceof User) && (((User)o).id == id))
			return true;
		else 
			return false;
	}
	
	public void addAccount()
	{
		accounts.add(new Account(this));
	}
	
	public void deleteAccount(int id)
	{
		try{
		accounts.remove(new Account(id));
		}catch(Exception ex)
		{}
	}
	
	public void initialize()
	{}
	
	public void process()
	{}
	
	public void delete()
	{}
	
	public void processAccount(int id)
	{
		accounts.get(accounts.indexOf(new Account(id))).process();
	}
	
	public void accountMenu(int id)
	{
		accounts.get(accounts.indexOf(new Account(id))).menu();
	}
	
	public String show()
	{
		String s = "";
		for (Account a:accounts)
			s += a.id + ", ";
		return s;
	}
	
	public void menu()
	{
		while (true)
		{
			System.out.println("****USER MENU****");
			System.out.println("Accounts: "+show());
			System.out.println("1. add Account");
			System.out.println("2. delete Account");
			System.out.println("3. process all");
			System.out.println("4. edit Account");
			System.out.println("5. exit");
			switch(Bank.read())
			{
			case 1:addAccount();break;
			case 2:Bank.write("Id: ");deleteAccount(Bank.read());break;
			case 3:process();break;
			case 4:Bank.write("Id: ");accountMenu(Bank.read());break;
			case 5:location.menu();break;
			}
		}
	}
	
	public String toString()
	{
		return "User "+id;
	}
}
