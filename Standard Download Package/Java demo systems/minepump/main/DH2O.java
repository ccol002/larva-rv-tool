package main;

public class DH2O implements Runnable{

	public long time = Main.TIME_UNIT;
	public long events = 11; 
	public static boolean status = false;
	
	public void on()
	{
		try{
		Thread.sleep(time);//*events);
		}
		catch(Exception ex){}
	}
	
	public void run()
	{
		status = true;
		System.out.println("DH2O on at: " + System.currentTimeMillis());
		for (int i = 0; i < events; i++)
			on();
		status = false;
		System.out.println("DH2O off at: " + System.currentTimeMillis());
		for (int i = 0; i < events/2; i++)
			on();
	}
}
