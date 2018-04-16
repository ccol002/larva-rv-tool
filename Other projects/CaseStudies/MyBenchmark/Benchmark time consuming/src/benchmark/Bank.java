package benchmark;

public class Bank {

	private static DummyDatabase db = new DummyDatabase();
	
	public User addUser()
	{
		return new User();
	}
	
	public void removeUser(User user){}
	
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
