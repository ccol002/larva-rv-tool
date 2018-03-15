package larva;

public class Main {

	public static void main(String[] args) {
		
		RunningClock.start();
		
//		Clock c = new Clock(null);
//		c.register(1000l);
//		c.register(2000l);
//		System.out.println(System.currentTimeMillis());
//		c.reset();
//		try{Thread.sleep(500);}catch(Exception ex){}
//		c.pause();
//		try{Thread.sleep(500);}catch(Exception ex){}
//		c.resume();
//		try{Thread.sleep(500);}catch(Exception ex){}
//		c.pause();
//		try{Thread.sleep(500);}catch(Exception ex){}
//		c.resume();
//		c.reset();
////		try{Thread.sleep(1500);}catch(Exception ex){}
////		c.reset();		
//		try{Thread.sleep(1500);}catch(Exception ex){}
		
		Clock c = new Clock(null);
		c.register(1000l);
		c.register(2000l);
		Clock d = new Clock(null);
		d.register(1000l);
		d.register(2000l);
		System.out.println(System.currentTimeMillis());
		c.reset();d.reset();
		try{Thread.sleep(500);}catch(Exception ex){}
		c.pause();
		try{Thread.sleep(500);}catch(Exception ex){}
		c.resume();
		try{Thread.sleep(150000);}catch(Exception ex){}
	}

}
