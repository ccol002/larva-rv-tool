package transactionsystem;

// The methods called by the user interface for (i) the ADMINistrator; and (ii) normal USERs
public class Interface {
	protected TransactionSystem ts;
	
	// Constructor of the interface
	public Interface()
	{
		ts = new TransactionSystem();
	}
	
	// ADMINistrator methods
	// * Initialise the transaction system
	public void ADMIN_initialise()
	{
		ts.initialise();
	}
	// * Reconcile the transaction system
	public void ADMIN_reconcile()
	{
	}
	// * Create a new user
	public Integer ADMIN_createUser(String name, String country)
	{
		Integer uid = ts.addUser(name,country);
		ts.getUserInfo(uid).makeDisabled();
		return uid;
	}

	// * Enable a user 
	public void ADMIN_activateUser(Integer uid)
	{
		ts.getUserInfo(uid).makeActive();
	}
	// * Disable a user (initially disabled)
	public void ADMIN_disableUser(Integer uid)
	{
		ts.getUserInfo(uid).makeDisabled();		
	}
	
	// * Black/grey or whitelist a user
	public void ADMIN_blacklistUser(Integer uid)
	{
		ts.getUserInfo(uid).blacklist();
	}
	public void ADMIN_greylistUser(Integer uid)
	{
		ts.getUserInfo(uid).greylist();
	}
	public void ADMIN_whitelistUser(Integer uid)
	{
		ts.getUserInfo(uid).whitelist();
	}
	// * Change user type (Gold, Silver or Normal User)
	public void ADMIN_makeGoldUser(Integer uid)
	{
		ts.getUserInfo(uid).makeGoldUser();
	}
	public void ADMIN_makeSilverUser(Integer uid)
	{
		ts.getUserInfo(uid).makeSilverUser();
	}
	public void ADMIN_makeNormalUser(Integer uid)
	{
		ts.getUserInfo(uid).makeNormalUser();
	}

	// * Approve the opening of an account
	public void ADMIN_approveOpenAccount(Integer uid, String account_number)
	{
		ts.getUserInfo(uid).getAccount(account_number).activateAccount();
	}
	
	// USER methods
	// * Login into the system (allows only ACTIVE users to login)
	public Integer USER_login(Integer uid) 
	{
		UserInfo u = ts.getUserInfo(uid);

		if (u.isActive()) {
			return (u.openSession());
		} else {
			return -1;
		}
	}
	// * Logout of the chosen session
	public void USER_logout(Integer uid, Integer sid)
	{
		ts.getUserInfo(uid).closeSession(sid);
	}
	// * Freeze his/her own user account
	public boolean USER_freezeUserAccount(Integer uid, Integer sid)
	{
		UserInfo u = ts.getUserInfo(uid);
		u.getSession(sid).log("Freeze account");
		u.makeFrozen();		
		return true;
	}
	// * Unfreeze his/her own user account
	public boolean USER_unfreezeUserAccount(Integer uid, Integer sid)
	{
		UserInfo u = ts.getUserInfo(uid);
		UserSession s = u.getSession(sid);
		if (u.isFrozen()) {
			s.log("Unfreeze account");
			u.makeUnfrozen();
			return true;
		} 
		s.log("FAILED (user account not frozen): Unfreeze account");
		return false;
	}
	// * Open a new money account
	public String USER_requestAccount(Integer uid, Integer sid)
	{
		UserInfo u = ts.getUserInfo(uid);
		UserSession s = u.getSession(sid);
		String account_number = u.createAccount(sid);
		s.log("Request new account with number <"+account_number+">");
		return (account_number);
	}
	// * Close an existing money account
	public void USER_closeAccount(Integer uid, Integer sid, String account_number)
	{
		UserInfo u = ts.getUserInfo(uid);
		UserSession s = u.getSession(sid);
		s.log("Close account number <"+account_number+">");
		u.deleteAccount(account_number);
	}
	// * Deposit money from an external source (e.g. from a credit card)
	public void USER_depositFromExternal(Integer uid, Integer sid, String to_account_number, double amount)
	{
		UserInfo u = ts.getUserInfo(uid);
		UserSession s = u.getSession(sid);
		s.log("Deposit $"+amount+"to account <"+to_account_number+">");
		u.depositTo(to_account_number,amount);
	}
	// * Pay a bill (i.e. an external money account) - charges apply
	public boolean USER_payToExternal(Integer uid, Integer sid, String from_account_number, double amount)
	{
		UserInfo u = ts.getUserInfo(uid);
		UserSession s = u.getSession(sid);
		
		if (s == null) return false;

		double total_amount = amount + ts.charges(uid, amount);
		if (u.getAccount(from_account_number).getBalance() >= amount) {
			s.log("Payment of $"+amount+" from account <"+from_account_number+">");
			u.withdrawFrom(from_account_number, total_amount);
			return true;
		}
		s.log("FAILED (not enough funds): Payment of $"+amount+" from account <"+from_account_number+">");
		return false;
	}
	// * Transfer money to another user's account - charges apply
	public boolean USER_transferToOtherAccount(Integer from_uid, Integer sid, String from_account_number, Integer to_uid, String to_account_number, double amount)
	{
		UserInfo from_u = ts.getUserInfo(from_uid);
		UserSession s = from_u.getSession(sid);

		if (s == null) return false;
		
		double total_amount = amount + ts.charges(from_uid, amount);
		
		if (from_u.getAccount(from_account_number).getBalance() >= total_amount) {
			from_u.withdrawFrom(from_account_number, total_amount);
			ts.getUserInfo(to_uid).depositTo(to_account_number, amount);
			s.log("Payment of $"+amount+" from account <"+from_account_number+"> to account "+
					"<"+to_account_number+" of user "+to_uid);
			return true;
		}
		s.log("FAILED (not enough funds): "+
				"Payment of $"+amount+" from account <"+from_account_number+"> to account "+
				"<"+to_account_number+" of user "+to_uid);
		return false;
	}
	// * Transfer money across own accounts - charges do not apply
	public boolean USER_transferOwnAccounts(Integer uid, Integer sid, String from_account_number, String to_account_number, double amount)
	{
		UserInfo u = ts.getUserInfo(uid);
		UserSession s = u.getSession(sid);
		UserAccount 
			from_a = ts.getUserInfo(uid).getAccount(from_account_number),
			to_a   = ts.getUserInfo(uid).getAccount(to_account_number);
		
		if (from_a.getBalance() >= amount) {
			from_a.withdraw(amount);
			to_a.deposit(amount);
			s.log("Transfer of $"+amount+" from account <"+from_account_number+"> to own account <"+to_account_number);
			return true;
		}
		s.log("FAILED (not enough funds)"+
				"Transfer of $"+amount+" from account <"+from_account_number+"> to own account <"+to_account_number);
		return false;
	}

}


