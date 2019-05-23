package code;

public class User implements Runnable{

	public void payMoney()
	{
		System.out.println("User: pay money");
	}
	
	public void receiveMoney()
	{
		System.out.println("User: receive money");
	}
		
	public void cancelOrder()
	{
		System.out.println("User: cancel order");
	}
	
	public void run()
	{
		try{
		Thread.sleep(100);
		System.out.println("User: pay money");
		}catch(Exception e){e.printStackTrace();}
	}
}
