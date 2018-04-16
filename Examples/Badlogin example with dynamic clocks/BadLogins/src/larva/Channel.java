package larva;
public class Channel{
	static boolean on = true;

public Channel()
{
}

public void receive(String s){}

public void receive(Object s){}

public void receive(){}

public void send(String s)
{
   if (on)	receive(s);
}

public void send(Object s)
{
	if (on) receive(s);
}

public void send()
{
	if (on) receive();
}
}