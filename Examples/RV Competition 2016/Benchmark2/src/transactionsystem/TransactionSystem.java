package transactionsystem;

import java.util.ArrayList;
import java.util.Iterator;

public class TransactionSystem {
	protected boolean initialised;
	protected ArrayList<UserInfo> users;
	protected Integer next_user_id;

	// Constructor
	public TransactionSystem() 
	{
		users = new ArrayList<UserInfo>();
		initialised = false;
		next_user_id = 1;
		users = new ArrayList<UserInfo> ();
	}
	
	public ArrayList<UserInfo> getUsers() 
	{
		return users;
	}
	
	// Initialise the transaction system
	public void initialise() 
	{
		initialised = !initialised;

		UserInfo admin = new UserInfo(0,"Clark Kent","Malta");
		admin.makeSilverUser();
		admin.makeActive();
		users.add(admin);
	}
	
	// Lookup a user by user-id
	public UserInfo getUserInfo(Integer uid) 
	{
		UserInfo u;
		
		Iterator<UserInfo> iterator = users.iterator();
		while (iterator.hasNext()) {
		    u = iterator.next();
		    if (u.getId()==uid) return u;
		}
		return null;
	}

	// Add a user to the system
	public Integer addUser(String name, String country) 
	{
		Integer uid = next_user_id;
		next_user_id++;
	
		users.add(new UserInfo(uid, name, country));
		return uid;
	}

	// Calculate the charges when a particular user makes a transfer
	public double charges(Integer uid, double amount) 
	{
		UserInfo u = getUserInfo(uid);
		if (u.isGoldUser()) {
			if (amount <= 100) return 0;				// no charges
			if (amount <= 1000) return (amount * 0.02); // 2% charges
			return (amount * 0.01);						// 1% charges
		}
		if (u.isSilverUser()) {
			if (amount <= 1000) return (amount * 0.03); // 3% charges
			return (amount * 0.02);						// 2% charges
		}
		if (u.isNormalUser()) {
			if (amount*0.05 > 2.0) {
				return (amount*0.05); 
			} else {
				return 2.0;
			}											// 5% charges, minimum of $2
		}
		return 0;
	}

}
