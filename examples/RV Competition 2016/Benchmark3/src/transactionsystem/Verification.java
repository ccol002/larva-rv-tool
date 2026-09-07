package transactionsystem;

import java.util.HashMap;

public class Verification {
	
		static public void assertion(boolean condition, String message)
	{
		if (!condition) System.out.println("*** "+message+" ***");
	}

	private Verification() 
	{ 
		initialiseVerification(); 
	}
	
	static boolean transactionSystemInitialised = false;
	static HashMap<String,Boolean> accountNumberAccepted;
	static HashMap<Integer,Boolean> userIdDisabled;
	static HashMap<Integer,Integer> grayListedUser;
	static HashMap<Integer,Integer> newAccountsInOneSession;
	static HashMap<Integer,Integer> userLogins;
	
	
	static public void initialiseVerification()
	{
		transactionSystemInitialised = false;
		accountNumberAccepted = new HashMap<String,Boolean>();
		userIdDisabled = new HashMap<Integer,Boolean>();
		grayListedUser = new HashMap<Integer,Integer>();
		newAccountsInOneSession = new HashMap<Integer,Integer>();
		userLogins = new HashMap<Integer,Integer>();
	}

	static public void setTransationSystemInitialised(boolean value){
		transactionSystemInitialised = value;		
	}
	
	static public void addAccountNumber(String account){
			accountNumberAccepted.put(account, true);		
	}

	static public void addDisabledUser(Integer uid){		
		userIdDisabled.put(uid,true);		
	}
	static public void removeDisabledUser(Integer uid){		
		userIdDisabled.remove(uid);		
	}

	static public void addGrayListedUser(Integer uid){	
		grayListedUser.put(uid, 0);
	}
	static public void incrementTransactionForGrayListedUser(Integer uid){
		if (grayListedUser.containsKey(uid) ) grayListedUser.put(uid, grayListedUser.get(uid)+1);		
	}

	static public void addNewSession(Integer sid){
		newAccountsInOneSession.put(sid,0);		
	}
	static public void incrementNewAccountRequest(Integer sid){
		if (newAccountsInOneSession.containsKey(sid)) newAccountsInOneSession.put(sid, newAccountsInOneSession.get(sid)+1);		
	}
	
	
	static public void incrementLoginRequest(Integer uid){
		if (userLogins.containsKey(uid)){ 
			userLogins.put(uid, userLogins.get(uid)+1);
		}
		else{
			userLogins.put(uid,1);
		}	
	}
	static public void decrementLoginRequest(Integer uid){
		if (userLogins.containsKey(uid)){ 
			userLogins.put(uid, userLogins.get(uid)-1);
		}	
	}
}
