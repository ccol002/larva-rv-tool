package nesting;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Bank {

	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	ArrayList<User> users = new ArrayList<User>();
	
	public void addUser()
	{
		users.add(new User(this));
	}
	
	public void deleteUser(int id)
	{
		users.get(users.indexOf(new User(id))).delete();
		users.remove(new User(id));
	}
	
	public void processUser(int id)
	{
		users.get(users.indexOf(new User(id))).process();
	}
	
	public static int read()
	{
		try{
		return Integer.parseInt(br.readLine());
		}
		catch(Exception ex)
		{ex.printStackTrace();}
		return -1;
	}
	
	public static void write(String text)
	{
		System.out.println(text);
	}
	
	public void userMenu(int id)
	{
		users.get(users.indexOf(new User(id))).menu();
	}
	
	public String show()
	{
		String s = "";
		for (User a:users)
			s += a.id + ", ";
		return s;
	}
	
	public void menu()
	{
		boolean run = true;
		while (run)
		{
			System.out.println("****MAIN MENU****");
			System.out.println("Users: "+show());
			System.out.println("1. add user");
			System.out.println("2. delete user");
			System.out.println("3. nothing");
			System.out.println("4. edit user");
			System.out.println("5. exit");
			switch(read())
			{
			case 1:addUser();break;
			case 2:write("Id: ");deleteUser(read());break;
			case 3:break;
			case 4:write("Id: ");userMenu(read());break;
			case 5:run = false;break;
			}
		}
	}
	
	public static void main(String[] args) {
		try{
		//ClassLoader.getSystemClassLoader().loadClass("larva._cls_clock0");
		
			//_larva.initialize();
		System.out.println(System.currentTimeMillis());
		new Bank().menu();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

}
