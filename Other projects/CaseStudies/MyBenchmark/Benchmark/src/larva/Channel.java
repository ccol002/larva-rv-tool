package larva;
import java.util.ArrayList;
public class Channel implements Runnable{
	static boolean on = true;
	ArrayList<Object> queue = new ArrayList<Object>();

public Channel()
{
	Thread t = new Thread(this);
	t.setDaemon(true);
	t.start();
}

public void receive(String s){}

public void receive(Object s){}

public void receive(){}

public void send(String s)
{
	synchronized (queue) {
		queue.add(s);
		queue.notify();
	}
}

public void send(Object s)
{
	synchronized (queue) {
		queue.add(s);
		queue.notify();
	}
}

public void send()
{
	synchronized (queue) {
		queue.add(new Object());
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
		new ChannelEvent(this,queue.remove(0));
	}
	}
}
catch(Exception ex){
	ex.printStackTrace();
}
}
}