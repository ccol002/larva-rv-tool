package benchmark;

public class Tester {

	public void allPropertiesSucceed()
	{
		Bank b = new Bank();
		for (int i = 0; i < 1000; i++)
		{
			try{
			b.perform(b.addUser().addTransaction());
			}catch(Exception ex)
			{}
		}
	}
	
	/*
	 * The transaction amount should not be modified
	 *  after being set the first time
	 */
	public void transactionAmountIsChanged()
	{
		Bank b = new Bank();
		for (int i = 0; i < 100; i++)
		{
			try{
			Transaction t = b.addUser().addTransaction();
			t.setAmount(100);
			t.setAmount(200);			
			}catch(Exception ex)
			{}
		}
	}
	
	/*
	 * A transaction which fails should be retried within 
	 * a maximum of 2 seconds for a maximum of 5 times (then it is abandoned)
	 */
	public void transactionRetriesTimeout()
	{
		Bank b = new Bank();
		DummyDatabase.setSucceed(2);
		DummyDatabase.setDelay(2100);
		for (int i = 0; i < 1; i++)
		{
			try{
				//Thread.sleep(1500);
			//Executer.perform(b.addUser().addTransaction());
				b.perform(b.addUser().addTransaction());
			}catch(Exception ex)
			{}
		}
	}
	
	/*
	 * A User cannot add more than 5 transactions
	 */
	public void userAddsMoreThan5Transactions()
	{
		Bank b = new Bank();
		for (int i = 0; i < 100; i++)
		{
			try{
				User u = b.addUser();
				for (int j = 0; j < 20; j++)
					u.addTransaction();
			}catch(Exception ex)
			{}
		}
	}
	
	/*
	 * A transaction which returned an exception should not 
	 * be retried
	 */
	public void transactionWithExceptionRetried()
	{
		Bank b = new Bank();
		DummyDatabase.setException(true);
		DummyDatabase.setSucceed(3);
		for (int i = 0; i < 100; i++)
		{
			try{
			b.perform(b.addUser().addTransaction());
			}catch(Exception ex)
			{}
		}
	}
	
	public static void main(String[] args)
	{
		Runtime.getRuntime().gc();
		long current = System.nanoTime();
		long memory = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		if (args.length > 0)
		{			
			if (args[0].equals("0"))
				new Tester().allPropertiesSucceed();
			else if (args[0].equals("1"))
				new Tester().transactionAmountIsChanged();
			else if (args[0].equals("2"))
				new Tester().transactionRetriesTimeout();
			else if (args[0].equals("3"))
				new Tester().userAddsMoreThan5Transactions();
			else if (args[0].equals("4"))
				new Tester().transactionWithExceptionRetried();	
			else
				System.out.println("Please specify a test number (0-4)");
			
			long memory2 = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
						
			System.out.println("Test took " + ((System.nanoTime()-current)/(double)1000000) + " milliseconds ");
			System.out.println("Test used " + ((memory2 - memory)/(double)1000) + " Kb ");
			
		}
		else
			System.out.println("Please specify a test number (0-4)");
	}
	
	
}
