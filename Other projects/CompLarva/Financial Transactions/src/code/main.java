package code;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try{
			
			Account sender = new Account();
			Account receiver = new Account();
			
			sender.withdraw(100);
			receiver.deposit(100);
			
			sender.cancel();
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
