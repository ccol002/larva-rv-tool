package benchmark;

public class Executer implements Runnable{

	Transaction t;
	
public static void perform(Transaction t){
		
		new Executer(t);
	}

public Executer(Transaction t)
{
	this.t = t;
	new Thread(this).start();
}

public void run()
{
	int retries = 5;
	boolean succeed = false;
	while (retries-- > 0 && !(succeed))
	{
		Bank.getConnection().doDelay();
		try{
			succeed = t.execute();
		}catch(Exception ex){}
	}
}

}
