package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class main {

	
	public static void M1()
	{
		try{
		File f = new File("C:\\Users\\University User\\Desktop\\projects\\Resource Management\\src\\hello2.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		System.out.println(br.readLine());


		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
public static void main(String[] args) {
		
	try{
	
		File f = new File("C:\\Users\\University User\\Desktop\\projects\\Resource Management\\src\\hello.txt");
		FileReader fr = new FileReader(f);
		
		BufferedReader br = new BufferedReader(fr);
		
		if (0==0) throw(new Exception());
		
		System.out.println(br.readLine());
		
		M1();
		
			
	}catch(Exception e)
	{
			e.printStackTrace();
	}
	}

}
