package larva;

public class ClockEvent implements Runnable{

	long millis;
	
	public ClockEvent(long millis)
	{
		this.millis = millis;
		new Thread(this).start();
	}
	
	public void event(long millis)
	{}
	
	public void run()
	{
		event(millis);
	}
	
}
