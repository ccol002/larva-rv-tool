package code;

public class Transaction {

	//this transaction is cancelled in time before packing
	public void doTx(Order o, User u)
	{
		//get stock
		Store.decreaseStock();
		o.stockReceivedFromStore();
		
		//payment
		Bank.receiveMoney();
		u.payMoney();
		
		u.cancelOrder();
		
	}
	
	//this transaction is after packing
	public void doTx2(Order o, User u)
	{
		//get stock
		Store.decreaseStock();
		o.stockReceivedFromStore();
		
		//payment
		Bank.receiveMoney();
		u.payMoney();
		
		//pack
		o.packOrder();
		
		u.cancelOrder();

	}
	
	//this transaction executes a number of activities in parallel
	public void doTx3(Order o, User u)
	{
		//get stock
		Store.decreaseStock();
		o.stockReceivedFromStore();
		
		//payment
		Bank.receiveMoney();
		new Thread(u).start();
		
		//pack
		new Thread(o).start();
		
	    try{
	    	Thread.sleep(300);
	    }catch(Exception e){e.printStackTrace();}
	    
	    u.cancelOrder();
		
		
//		//delivery
//		Courier c1 = new Courier();
//		Courier c2 = new Courier();
//		
//		c1.priceQuotation();
//		c2.priceQuotation();
//		
//		c1.acceptOrder();
//		c1.shipOrder();
		
	}
	
}
