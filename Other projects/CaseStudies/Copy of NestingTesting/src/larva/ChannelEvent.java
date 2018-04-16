package larva;

public class ChannelEvent implements Runnable{

	String s;
	
	public ChannelEvent(String s)
	{
		this.s = s;
		new Thread(this).start();
	}
	
	public void event(String s)
	{}
	
	public void run()
	{
		event(s);
	}
	
}
