package transactionsystem;

import java.io.IOException;

public class Scenarios {

	private static Integer SCENARIO_COUNT = 20;
	
	static Interface tr = new Interface();
	
	private static void scenario(Integer n) 
	{
		String account_number, account_number_receiver;
		Integer uid, uid_receiver;
		Integer sid, sid_receiver;
		
		if (n>0 && n <= SCENARIO_COUNT) {
			Integer prop = (n+1)/2;
			System.out.println("\nRunning scenario ["+prop+((n%2==1)?"a":"b")+"]");
			System.out.println("Should "+((n%2==1)?"":"not ")+"violate property "+prop);
		}
		switch (n) {
		// PROPERTY 1: Only users based in Argentina can be Gold users.
		case 1:
			// violation
			tr.ADMIN_initialise();
			uid = tr.ADMIN_createUser("Fred","France");
			tr.ADMIN_activateUser(uid);
			tr.ADMIN_makeGoldUser(uid);
			break;
		case 2: 
			// non-violation
			tr.ADMIN_initialise();
			uid = tr.ADMIN_createUser("Fred","France");
			tr.ADMIN_activateUser(uid);
			tr.ADMIN_makeSilverUser(uid);
			uid = tr.ADMIN_createUser("Marge","Argentina");
			tr.ADMIN_activateUser(uid);
			tr.ADMIN_makeGoldUser(uid);
			break;
		// PROPERTY 2: The transaction system must be initialised before any user logs in.
		case 3:
			// P2 violation
			uid = tr.ADMIN_createUser("Fred","France");
			tr.ADMIN_activateUser(uid);
			tr.USER_login(uid);
			break;
		case 4:
			// P2 non-violation
			tr.ADMIN_initialise();
			uid = tr.ADMIN_createUser("Fred","France");
			tr.ADMIN_activateUser(uid);
			sid = tr.USER_login(uid);
			tr.USER_logout(uid, sid);
			break;
		// PROPERTY 3: No account may end up with a negative balance after being accessed.
		case 5:
			// P3 violation
			tr.ADMIN_initialise();
			uid = tr.ADMIN_createUser("Fred","France");
			tr.ADMIN_activateUser(uid);
			sid = tr.USER_login(uid);
			account_number = tr.USER_requestAccount(uid,sid);
			tr.ADMIN_approveOpenAccount(uid, account_number);
			tr.USER_depositFromExternal(uid, sid, account_number, 500);
			tr.USER_payToExternal(uid,sid,account_number, 495);
			break;
		case 6:
			// P3 non-violation
			tr.ADMIN_initialise();
			uid = tr.ADMIN_createUser("Fred","France");
			tr.ADMIN_activateUser(uid);
			sid = tr.USER_login(uid);
			account_number = tr.USER_requestAccount(uid,sid);
			tr.ADMIN_approveOpenAccount(uid, account_number);
			tr.USER_depositFromExternal(uid, sid, account_number, 500);
			tr.USER_payToExternal(uid,sid,account_number, 100);
			tr.USER_payToExternal(uid,sid,account_number, 100);
			break;
		// PROPERTY 4: An account approved by the administrator may not have the same 
		// account number as any other already existing account in the system.
		case 7:
			// P4 violation
			tr.ADMIN_initialise();
			for (Integer i=0; i<15; i++) {
				uid = tr.ADMIN_createUser("Fred("+i+")","France");
				tr.ADMIN_activateUser(uid);
				for (Integer j=0; j<11; j++) {
					sid = tr.USER_login(uid);
					account_number = tr.USER_requestAccount(uid,sid);
					tr.ADMIN_approveOpenAccount(uid, account_number);					
					tr.USER_logout(uid,sid);
				}
			
			}
			// Account 1 of user 11 and account 11 of user 1 will both be named "111"
			break;
		case 8:
			// P4 non-violation
			tr.ADMIN_initialise();
			for (Integer i=0; i<10; i++) {
				uid = tr.ADMIN_createUser("Fred("+i+")","France");
				tr.ADMIN_activateUser(uid);
				sid = tr.USER_login(uid);
				for (Integer j=0; j<10; j++) {
					account_number = tr.USER_requestAccount(uid,sid);
					tr.ADMIN_approveOpenAccount(uid, account_number);					
				}
				tr.USER_logout(uid,sid);
			}
			break;	
		// PROPERTY 5: Once a user is disabled by the administrator, he or she may not
		// withdraw from an account until being activated again by the administrator.
		case 9:
			// P5 violation
			tr.ADMIN_initialise();
			uid = tr.ADMIN_createUser("Fred","France");
			tr.ADMIN_activateUser(uid);
			sid = tr.USER_login(uid);
			account_number = tr.USER_requestAccount(uid,sid);
			tr.ADMIN_approveOpenAccount(uid, account_number);
			tr.USER_depositFromExternal(uid, sid, account_number, 500);
			tr.USER_payToExternal(uid, sid, account_number, 1000);
			tr.ADMIN_disableUser(uid);
			tr.USER_freezeUserAccount(uid,sid);
			tr.USER_unfreezeUserAccount(uid,sid);
			tr.USER_payToExternal(uid,sid,account_number,200);
			break;
		case 10:
			// P5 non-violation
			tr.ADMIN_initialise();
			uid = tr.ADMIN_createUser("Fred","France");
			tr.ADMIN_activateUser(uid);
			sid = tr.USER_login(uid);
			account_number = tr.USER_requestAccount(uid,sid);
			tr.ADMIN_approveOpenAccount(uid, account_number);
			tr.USER_depositFromExternal(uid, sid, account_number, 500);
			tr.USER_payToExternal(uid,sid,account_number, 1000);
			tr.ADMIN_disableUser(uid);
			tr.ADMIN_activateUser(uid);
			tr.USER_payToExternal(uid,sid,account_number,200);
			break;
		// PROPERTY 6: Once greylisted, a user must perform at least three external
		// transfers before being whitelisted.
		case 11:
			// P6 violation
			
			tr.ADMIN_initialise();

			for(int i = 0; i < 10000; i++){
				uid_receiver = tr.ADMIN_createUser("Roger" + i,"Romania");
				tr.ADMIN_activateUser(uid_receiver);
				sid_receiver = tr.USER_login(uid_receiver); 
				account_number_receiver = tr.USER_requestAccount(uid_receiver, sid_receiver);
				tr.ADMIN_approveOpenAccount(uid_receiver, account_number_receiver);
				tr.USER_logout(uid_receiver, sid_receiver);

				uid = tr.ADMIN_createUser("Sandy" + i,"Senegal");
				tr.ADMIN_activateUser(uid);
				sid = tr.USER_login(uid);
				account_number = tr.USER_requestAccount(uid,sid);
				tr.ADMIN_approveOpenAccount(uid, account_number);
				tr.USER_logout(uid,sid);
				
				tr.ADMIN_greylistUser(uid);

				for (Integer j=0; j<2; j++) {
					sid = tr.USER_login(uid);
					tr.USER_depositFromExternal(uid,sid,account_number,1000);
					tr.USER_depositFromExternal(uid,sid,account_number,100);
					tr.USER_logout(uid,sid);
				}

				tr.ADMIN_whitelistUser(uid);
			}
			
			
			uid_receiver = tr.ADMIN_createUser("Vlad","Romania");
			tr.ADMIN_activateUser(uid_receiver);
			sid_receiver = tr.USER_login(uid_receiver); 
			account_number_receiver = tr.USER_requestAccount(uid_receiver, sid_receiver);
			tr.ADMIN_approveOpenAccount(uid_receiver, account_number_receiver);
			tr.USER_logout(uid_receiver, sid_receiver);

			uid = tr.ADMIN_createUser("Sarah","Senegal");
			tr.ADMIN_activateUser(uid);
			sid = tr.USER_login(uid);
			account_number = tr.USER_requestAccount(uid,sid);
			tr.ADMIN_approveOpenAccount(uid, account_number);
			tr.USER_logout(uid,sid);
			
			tr.ADMIN_greylistUser(uid);

			for (Integer i=0; i<2; i++) {
				sid = tr.USER_login(uid);
				tr.USER_depositFromExternal(uid,sid,account_number,1000);
				tr.USER_logout(uid,sid);
			}

			tr.ADMIN_whitelistUser(uid);

			break;
		case 12:
			// P6 non-violation
			tr.ADMIN_initialise();
			uid_receiver = tr.ADMIN_createUser("Roger","Romania");
			tr.ADMIN_activateUser(uid_receiver);
			sid_receiver = tr.USER_login(uid_receiver); 
			account_number_receiver = tr.USER_requestAccount(uid_receiver, sid_receiver);
			tr.ADMIN_approveOpenAccount(uid_receiver, account_number_receiver);
			tr.USER_logout(uid_receiver, sid_receiver);

			uid = tr.ADMIN_createUser("Sandy","Senegal");
			tr.ADMIN_activateUser(uid);
			sid = tr.USER_login(uid);
			account_number = tr.USER_requestAccount(uid,sid);
			tr.ADMIN_approveOpenAccount(uid, account_number);
			tr.USER_logout(uid,sid);
			
			tr.ADMIN_greylistUser(uid);

			for (Integer i=0; i<2; i++) {
				sid = tr.USER_login(uid);
				tr.USER_depositFromExternal(uid,sid,account_number,1000);
				tr.USER_depositFromExternal(uid,sid,account_number,100);
				tr.USER_logout(uid,sid);
			}

			tr.ADMIN_whitelistUser(uid);
			break;	
		// PROPERTY 7: No user may request more than 10 new accounts in a single session.
		case 13:
			// P7 violation
			tr.ADMIN_initialise();
			uid = tr.ADMIN_createUser("Fred","France");
			tr.ADMIN_activateUser(uid);
			sid = tr.USER_login(uid);
			for (Integer i=0; i<11; i++) {
				account_number = tr.USER_requestAccount(uid,sid);
				if (i%2==0) tr.ADMIN_approveOpenAccount(uid, account_number);					
			}
			tr.USER_logout(uid,sid);
			break;
		case 14:
			// P7 non-violation
			tr.ADMIN_initialise();
			uid = tr.ADMIN_createUser("Fred","France");
			tr.ADMIN_activateUser(uid);
			sid = tr.USER_login(uid);
			for (Integer i=0; i<30; i++) {
				account_number = tr.USER_requestAccount(uid,sid);
				if (i%2==0) tr.ADMIN_approveOpenAccount(uid, account_number);					
				if (i%9==5) {
					tr.USER_logout(uid,sid);
					sid = tr.USER_login(uid);
				}
			}
			tr.USER_logout(uid,sid);
			break;
		// PROPERTY 8: The administrator must reconcile accounts every 1000 external money
		// transfers or an aggregate total of one million dollars in external transfers.
		case 15:
			// P8 violation
			tr.ADMIN_initialise();
			for (Integer i=0; i<501; i++) {
				uid = tr.ADMIN_createUser("Fred("+i+")","France");
				tr.ADMIN_activateUser(uid);
				sid = tr.USER_login(uid);
				account_number = tr.USER_requestAccount(uid,sid);
				tr.ADMIN_approveOpenAccount(uid, account_number);
				tr.USER_depositFromExternal(uid,sid,account_number,250);
				tr.USER_payToExternal(uid,sid,account_number,200);
				tr.USER_logout(uid,sid);
			}
			break;
		case 16:
			// P8 non-violation
			Integer total = 0;
			
			int countuserDepositFromExternal = 0;
			int userpayto = 0;
			int reconcile = 0;
			
			tr.ADMIN_initialise();
			for (Integer i=0; i<10000; i++) {
				uid = tr.ADMIN_createUser("Fred("+i+")","France");
				tr.ADMIN_activateUser(uid);
				sid = tr.USER_login(uid);
				account_number = tr.USER_requestAccount(uid,sid);
				tr.ADMIN_approveOpenAccount(uid, account_number);
				tr.USER_depositFromExternal(uid,sid,account_number,1000);
				countuserDepositFromExternal++;
				tr.USER_payToExternal(uid,sid,account_number,500);
				userpayto++;
				total += 1525;
				if (total > 500000) {tr.ADMIN_reconcile(); reconcile++;}
				tr.USER_logout(uid,sid);
			}
			
			System.out.println("countuserDepositFromExternal " + countuserDepositFromExternal);
			System.out.println("userpayto " + userpayto);
			System.out.println("reconcile " + reconcile);
			break;
		// PROPERTY 9: A user may not have more than 3 active sessions at once.
		case 17:
			// P9 violation
			tr.ADMIN_initialise();
			for (Integer i=0; i<4; i++) {
				uid = tr.ADMIN_createUser("Fred("+i+")","France");
				tr.ADMIN_activateUser(uid);
				for (Integer j=0; j<=i; j++) {
					sid = tr.USER_login(uid);
				}
			}
			break;
		case 18:
			// P9 non-violation
			tr.ADMIN_initialise();
			for (Integer i=0; i<4; i++) {
				uid = tr.ADMIN_createUser("Fred("+i+")","France");
				tr.ADMIN_activateUser(uid);
				for (Integer j=0; j<5; j++) {
					sid = tr.USER_login(uid);
					tr.USER_logout(uid,sid);
				}
				for (Integer j=0; j<2; j++) {
					sid = tr.USER_login(uid);
				}
			}
			break;	
		// PROPERTY 10: Transfers may only be made during an active session (i.e. between a login and a logout)
		case 19:
			// P10 violation
			tr.ADMIN_initialise();
			for(int j = 0; j < 10000; j++){
				uid_receiver = tr.ADMIN_createUser("Roger" + j,"Romania");
				tr.ADMIN_activateUser(uid_receiver);
				sid_receiver = tr.USER_login(uid_receiver); 
				account_number_receiver = tr.USER_requestAccount(uid_receiver, sid_receiver);
				tr.ADMIN_approveOpenAccount(uid_receiver, account_number_receiver);
				tr.USER_logout(uid_receiver, sid_receiver);

				for (Integer i=0; i<5; i++) {
					uid = tr.ADMIN_createUser("Sandy"+ j + "." + i,"Senegal");
					tr.ADMIN_activateUser(uid);
					sid = tr.USER_login(uid);
					account_number = tr.USER_requestAccount(uid,sid);
					tr.ADMIN_approveOpenAccount(uid, account_number);
					tr.USER_depositFromExternal(uid,sid,account_number,1000);
					tr.USER_payToExternal(uid,sid,account_number,100);
					tr.USER_transferToOtherAccount(uid,sid,account_number,uid_receiver, account_number_receiver,100);
					tr.USER_logout(uid,sid);
				}
			}
			
			uid_receiver = tr.ADMIN_createUser("Vlad","Romania");
			tr.ADMIN_activateUser(uid_receiver);
			sid_receiver = tr.USER_login(uid_receiver); 
			account_number_receiver = tr.USER_requestAccount(uid_receiver, sid_receiver);
			tr.ADMIN_approveOpenAccount(uid_receiver, account_number_receiver);
			tr.USER_logout(uid_receiver, sid_receiver);

			for (Integer i=0; i<5; i++) {
				uid = tr.ADMIN_createUser("Sarah"+i,"Senegal");
				tr.ADMIN_activateUser(uid);
				sid = tr.USER_login(uid);
				account_number = tr.USER_requestAccount(uid,sid);
				tr.ADMIN_approveOpenAccount(uid, account_number);
				tr.USER_depositFromExternal(uid,sid,account_number,1000);
				tr.USER_logout(uid,sid);
				if (i==3) tr.USER_payToExternal(uid,sid,account_number,100);
			}
			
			break;
		case 20:
			// P10 non-violation
			tr.ADMIN_initialise();
			uid_receiver = tr.ADMIN_createUser("Roger","Romania");
			tr.ADMIN_activateUser(uid_receiver);
			sid_receiver = tr.USER_login(uid_receiver); 
			account_number_receiver = tr.USER_requestAccount(uid_receiver, sid_receiver);
			tr.ADMIN_approveOpenAccount(uid_receiver, account_number_receiver);
			tr.USER_logout(uid_receiver, sid_receiver);

			for (Integer i=0; i<5; i++) {
				uid = tr.ADMIN_createUser("Sandy("+i+")","Senegal");
				tr.ADMIN_activateUser(uid);
				sid = tr.USER_login(uid);
				account_number = tr.USER_requestAccount(uid,sid);
				tr.ADMIN_approveOpenAccount(uid, account_number);
				tr.USER_depositFromExternal(uid,sid,account_number,1000);
				tr.USER_payToExternal(uid,sid,account_number,100);
				tr.USER_transferToOtherAccount(uid,sid,account_number,uid_receiver, account_number_receiver,100);
				tr.USER_logout(uid,sid);
			}
			break;	
		default:
			System.out.println("Requested scenario "+n.toString()+" which is not defined.");
		}
	}
	public static void runViolatingScenarioForProperty(Integer n) 
	{
		scenario(2*n-1);
	}
	public static void runNonViolatingScenarioForProperty(Integer n) 
	{
		scenario(2*n);
	}
	private static void restartTransactionSystem()
	{
		tr = new Interface();		
	}
	public static void runAllScenarios()
	{
		for (Integer n=1; n<=SCENARIO_COUNT; n++) {
			restartTransactionSystem();
			Verification.initialiseVerification();
			scenario(n);
		}					
	}
}
