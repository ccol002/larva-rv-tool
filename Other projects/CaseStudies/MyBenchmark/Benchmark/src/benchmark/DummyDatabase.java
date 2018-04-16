package benchmark;

public class DummyDatabase {

	private static int succeed = 0;
	private static Transaction t;
	private static int current;
	private static int delay = 0;
	private static boolean exception = false;
	
	public static void setSucceed(int succeed)
	{
		DummyDatabase.succeed = succeed;
	}
	
	public static void setDelay(int delay)
	{
		DummyDatabase.delay = delay;
	}
	
	public static void setException(boolean exception)
	{
		DummyDatabase.exception = exception;
	}
	
	public void doDelay()
	{
		try{
		if (delay > 0)
			Thread.sleep(delay);
		}catch(Exception ex)
		{}
	}
	
	public boolean execute(Transaction t)throws Exception
	{
		if (DummyDatabase.t == null || !DummyDatabase.t.equals(t))
		{
			DummyDatabase.t = t;
			current = succeed;
		}
		if (--current < 0)
			return true;
		else if (exception)
			throw new Exception("Transaction Failed!");
		else
			return false;		
	}

}
