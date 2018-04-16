package larva;
public class ChannelEvent implements Runnable{
	Object o;
	String s;
	Channel c;
	public ChannelEvent(Channel c, Object o){
if (o instanceof String)
 	this.s = (String)o;
else
	this.o = o;
this.c = c;
 Thread t = new Thread(this);
 t.setDaemon(true);
 t.start();
}
public void run()	{
if (s!= null) c.receive(s);
else c.receive(o);
}
}