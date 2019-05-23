package code;

public class Order implements Runnable{

	public void stockReceivedFromStore()
	{
		System.out.println("Order: stock received from store");
	}
	
	public void stockSentBackToStore()
	{
		System.out.println("Order: stock sent back to store");
	}
	
	public void packOrder()
	{
		System.out.println("Order: pack order");
	}
	
	public void unpackOrder()
	{
		System.out.println("Order: unpack order");
	}
	
	
	
	public void run()
	{
		System.out.println("Order: pack order");
	}
}
