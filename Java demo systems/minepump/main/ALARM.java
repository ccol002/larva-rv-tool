package main;

public class ALARM implements Runnable{

	public long time = Main.TIME_UNIT;
	public long events = 5; 
	public static boolean status = false;
	
	public void on()
	{
		try{
		Thread.sleep(time);
		}
		catch(Exception ex){}
	}
	
	public void run()
	{
		status = true;
		System.out.println("Alarm on at: " + System.currentTimeMillis());
		for (int i = 0; i < events; i++)
			on();
		status = false;
		System.out.println("Alarm off at: " + System.currentTimeMillis());
		for (int i = 0; i < events/2; i++)
			on();
	}
}
