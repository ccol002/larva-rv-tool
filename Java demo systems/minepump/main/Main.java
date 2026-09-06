package main;


public class Main {

	public static int TIME_UNIT = 100;
	
	public void DH2O()
	{
		new Thread(new DH2O()).start();
	}
	
	public void initialize(){}
	
	public void start(){}
	
	public void Alarm()
	{
		new Thread(new ALARM()).start();
	}	
	
	public static void main(String[] args)throws Exception
	{
		int delta = 11;
		Main m = new Main();
		m.initialize();
		m.start();
		m.DH2O();
		Thread.sleep(TIME_UNIT * delta);
		m.Alarm();
		
//		Thread.sleep(TIME_UNIT * 20);
//		m.DH2O();
//		Thread.sleep(TIME_UNIT * delta);
//		m.Alarm();
		
	}
	
//	public static void main(String[] args)throws Exception
//	{
//		Clock c = new Clock(null);
//		c.restart();
//		Thread.sleep(100);
//		c.pause();
//		Thread.sleep(100);
//		c.resume();
//		Thread.sleep(100);
//		c.reset();
//		Thread.sleep(100);
//		c.pause();
//		Thread.sleep(100);
//		System.out.println(c.current_long());
//	}
}
