package main;
import java.util.Random;

public class Main implements Runnable{

	public void run()
	{
		try{
		    User[] users = new User[10];
		    for(int i = 0; i < users.length; i++) {
			users[i] = new User();
		    }
		    User selectedUser = users[(new Random()).nextInt(10)];
		    int cnt = 0;
		    while (true)
			{
				cnt++;
				Thread.sleep(100);
				selectedUser.badlogin();
				if (cnt%2==0)
					Thread.sleep(12000);
			}
		}catch(Exception ex)
		{ex.printStackTrace();}
	}
	
	
	public void initialize()
	{}
	
	public static void main(String [] args)
	{
		Main m = new Main();
		m.initialize();
		new Thread(m).start();
	}
}
