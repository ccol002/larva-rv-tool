package transactionsystem;

import java.util.ArrayList;
import java.util.Iterator;

import transactionsystem.UserSession;

public class UserInfo {
	protected enum UserMode {
		ACTIVE, DISABLED, FROZEN;
	}

	public enum UserStatus {
		WHITELISTED, GREYLISTED, BLACKLISTED;
	}

	public enum UserType {
		GOLD, SILVER, NORMAL
	}
	
	protected Integer uid;
	protected String name;
	protected UserMode mode;
	protected UserStatus status;
	protected UserType type;
	protected ArrayList<UserSession> sessions;
	protected ArrayList<UserAccount> accounts;
	protected Integer next_session_id;
	protected Integer next_account;
	protected String country;
	
	public UserInfo(Integer uid, String name, String country) {
		this.uid = uid;
		this.name = name;
		
		makeDisabled();
		whitelist();
		makeNormalUser();
		
		sessions = new ArrayList<UserSession>();
		accounts = new ArrayList<UserAccount>();
		
		next_session_id = 0;
		next_account = 1;
		
		this.country = country;
	}
	
	// Basic information
	public Integer getId() 
	{
		return uid;
	}
	public String getName()
	{
		return name;
	}
	public String getCountry()
	{
		return country;
	}
	public ArrayList<UserAccount> getAccounts()
	{
		return accounts;
	}
	public ArrayList<UserSession> getSessions()
	{
		return sessions;
	}
	
	// User type
	public boolean isGoldUser() { return (type==UserType.GOLD); }
	public boolean isSilverUser() { return (type==UserType.SILVER); }
	public boolean isNormalUser() { return (type==UserType.NORMAL); }

	public void makeGoldUser() { 
		type = UserType.GOLD; 
	}
	public void makeSilverUser() { type = UserType.SILVER; }
	public void makeNormalUser() { type = UserType.NORMAL; }	
	
	// Status
	public boolean isWhitelisted() { return (status==UserStatus.WHITELISTED); }
	public boolean isGreylisted() { return (status==UserStatus.GREYLISTED); }
	public boolean isBlacklisted() { return (status==UserStatus.BLACKLISTED); }
	
	public void blacklist() 
	{ 
		status=UserStatus.BLACKLISTED; 
	}
	public void greylist() 
	{
		status=UserStatus.GREYLISTED; 
	}
	public void whitelist()
	{ 
		status=UserStatus.WHITELISTED; 
	}

	// Mode
	public boolean isActive() { return (mode==UserMode.ACTIVE); }
	public boolean isFrozen() { return (mode==UserMode.FROZEN); }
	public boolean isDisabled() { return (mode==UserMode.DISABLED); }

	public void makeActive() 
	{ 
		mode=UserMode.ACTIVE; 
	}
	public void makeFrozen() 
	{ 
		mode=UserMode.FROZEN; 
	}
	public void makeDisabled() 
	{ 
		mode=UserMode.DISABLED; 
	}
	public void makeUnfrozen() 
	{ 
		mode=UserMode.ACTIVE; 
	}

	// Sessions
	public UserSession getSession(Integer sid) 
	{
		UserSession s;
		
		Iterator<UserSession> iterator = sessions.iterator();
		while (iterator.hasNext()) {
		    s = iterator.next();
		    if (s.getId()==sid) return s;
		}
		return null;
	}
	public Integer openSession() 
	{
		Integer sid = next_session_id;
		
		UserSession session = new UserSession(uid, sid);
		session.openSession();
		sessions.add(session);

		next_session_id++;

		return(sid);
	}
	public void closeSession(Integer sid) 
	{
		UserSession s = getSession(sid);

		s.closeSession();
	}

	// Accounts
	public UserAccount getAccount(String account_number) 
	{
		UserAccount a;
		
		Iterator<UserAccount> iterator = accounts.iterator();
		while (iterator.hasNext()) {
		    a = iterator.next();
		    if (a.getAccountNumber() == account_number) return a;
		}
		return null;
	} 
	public String createAccount(Integer sid) 
	{
		String account_number = uid.toString() + next_account.toString();
		next_account++;
		UserAccount a = new UserAccount(uid, account_number);
		accounts.add(a);
		return account_number;
	}
	public void deleteAccount(String account_number) 
	{
		UserAccount a = getAccount(account_number);
		a.closeAccount();
	}

	public void withdrawFrom(String account_number, double amount)
	{
		getAccount(account_number).withdraw(amount);
	}
	public void depositTo(String account_number, double amount)
	{
		getAccount(account_number).deposit(amount);
	}
}
