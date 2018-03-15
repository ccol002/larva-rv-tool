package larva;

import java.util.ArrayList;

public class Channel implements Runnable{

	static boolean on = true;
	ArrayList<String> queue = new ArrayList<String>();
	
	public Channel()
	{
		new Thread(this).start();
	}
	
	public void send(String s)
	{
		synchronized (queue) {
			queue.add(s);
			queue.notify();
		}
	}
	
	public void run()
	{
		try{
		while (on)
		{
			while (queue.isEmpty() && on)
				synchronized (queue) {
					queue.wait(100);
				}				
			if (on){				
				new ChannelEvent(queue.remove(0));
			}
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void event(String s){}
}
