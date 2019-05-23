package code;

import java.util.Stack;

public class Account {

	private double balance;
	private int id;
	private static int cntr = 0;
	
	
	
	Account(double initial)
	{
		id = cntr++;
		balance = initial;
	}
	
	Account()
	{
		this(0.0);		
	}
	
	public void cancel()
	{}
	
	public void deposit(double sum)
	{
		balance += sum;
		System.out.println(toString()+" deposited " + sum);
	}
	
	public void withdraw(double sum)
	{
		balance -= sum;
		System.out.println(toString()+" withdrawn " + sum);
	}
	
	public String toString()
	{
		return "Account:"+id+" bal: $" + Double.toString(balance);
	}
	
	
}
