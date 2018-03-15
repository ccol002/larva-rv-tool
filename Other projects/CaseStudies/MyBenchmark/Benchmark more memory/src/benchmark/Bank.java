package benchmark;

import java.util.ArrayList;

public class Bank {

	private static DummyDatabase db = new DummyDatabase();
	
ArrayList<User> users = new ArrayList<User>();
	
	public User addUser()
	{
		users.add(new User());
		return users.get(users.size()-1);
	}
	
	public boolean removeUser(User user)
	{
		return users.remove(user);
	}
	
	public boolean perform(Transaction t){
		
		int retries = 5;
		boolean succeed = false;
		while (retries-- > 0 && !(succeed))
		{
			db.doDelay();
			try{
			succeed = t.execute();
			}catch(Exception ex)
			{}
		}
		return succeed;
	}

	public static DummyDatabase getConnection()
	{
		return db;
	}
}
