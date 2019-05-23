package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class Runner implements Runnable{

	
//	static String command;
//	static String description;
//	static GuiFrame gf;
	static Process p;
	static ArrayList<Integer> extras;
	static HashMap<String,String> map;
	
	public static void execute(String command, final String description, final GuiFrame gf)
	{	
//		command = command1;
//		description = description1;
//		gf = gf1;
		
		try
		{			
			try
		      {
				//p = Runtime.getRuntime().exec("C:\\WINDOWS\\system32\\cmd.exe /c" + command);
				p = Runtime.getRuntime().exec(command);
		      //  ProcessBuilder pb = new ProcessBuilder(command.split(" "));
		      //  pb.environment().put("Path",pb.environment().get("Path") + "C:\\Program Files (x86)\\Java\\aspectj1.6\\bin");
		       // pb.directory(new File("C:\\Program Files (x86)\\Java\\aspectj1.6\\bin"));
		     //   final Process p = pb.start();	          
		         			
				
		          final StringBuilder s = new StringBuilder("");
		          final StringBuilder e = new StringBuilder("");
		          
		          final BufferedReader errStream = new BufferedReader(new InputStreamReader(p.getErrorStream()));    
			      final BufferedReader inStream = new BufferedReader(new InputStreamReader(p.getInputStream()));  
		          		          
		          new Thread(){
			            public void run(){
					        try{				        	
					        	 String line;
					        	 while ((line = errStream.readLine()) != null){
					        		 System.out.println(line);
						        	  e.append(line + "\r\n");
					        	 }
					         }catch (Exception ex)
					         {
						         ex.printStackTrace();  
						         JOptionPane.showMessageDialog(gf,"Error in " + description + "!","Error!",JOptionPane.ERROR_MESSAGE);
					         }
			                }
			            }.start();
		          
		          new Thread(){
		            public void run(){
				        try{				        	
				        	 String line;
				        	while ((line = inStream.readLine()) != null){
				        		System.out.println(line);
				        		s.append(line + "\r\n");
				        	}
				        	
				         }catch (Exception ex)
				         {
					         ex.printStackTrace();  
					         JOptionPane.showMessageDialog(gf,"Error in " + description + "!","Error!",JOptionPane.ERROR_MESSAGE);
				         }
		                }
		            }.start();
		          
		            
		            
		       //     extras = findExtra(Runner.quickRun("tasklist"));
		            gf.stopBtn.setEnabled(true);
		            
		            
		            
		            p.waitFor(); 
		         //	System.out.println("!!!!@!" + p.exitValue());	         
   
		            
		          
		         String all = e.toString()+s.toString();
		        // System.out.println(all);
			     
		         if (all.length() > 0)
		        	 JOptionPane.showMessageDialog(gf,all.substring(Math.max(all.length()-1000,0)));
		         else
		        	 JOptionPane.showMessageDialog(gf,description + " terminated normally (or stopped by user)!");
		      }
		      catch(IOException e)
		      {
		         e.printStackTrace();  
		         JOptionPane.showMessageDialog(gf,"Error in " + description + "!","Error!",JOptionPane.ERROR_MESSAGE);
		      }
			
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(gf, "Problem executing " + description + "!", "Error!" , JOptionPane.ERROR_MESSAGE);	
		}
		
		gf.stopBtn.setEnabled(false);
		gf.updateEnabled(true);
		for (int i = 0; i < gf.jtp.getTabCount(); i ++)
		{
			TabPanel tab = (TabPanel)gf.jtp.getComponentAt(i); //seems that there is a bug in getTabComponentAt			
			tab.text2.autoRefresh = false;			
		}
		
		//new Thread(new Runner()).start();
	}
	
	
	public static String quickRun(String command)
	{
		String all = "";
	try
	{			
		Process p;
		
		try
	      {
			p = Runtime.getRuntime().exec(command);

	          final StringBuilder s = new StringBuilder("");
	          final StringBuilder e = new StringBuilder("");
	          
	          final BufferedReader errStream = new BufferedReader(new InputStreamReader(p.getErrorStream()));    
		      final BufferedReader inStream = new BufferedReader(new InputStreamReader(p.getInputStream()));  
	          		          
	          new Thread(){
		            public void run(){
				        try{				        	
				        	 String line;
				        	 while ((line = errStream.readLine()) != null){
				        		 System.out.println(line);
					        	  e.append(line + "\r\n");
				        	 }
				         }catch (Exception ex)
				         {
					         ex.printStackTrace();  
					       //  JOptionPane.showMessageDialog(gf,"Error in " + description + "!","Error!",JOptionPane.ERROR_MESSAGE);
				         }
		                }
		            }.start();
	          
	          new Thread(){
	            public void run(){
			        try{				        	
			        	 String line;
			        	while ((line = inStream.readLine()) != null){
			        		System.out.println(line);
			        		s.append(line + "\r\n");
			        	}
			        	
			         }catch (Exception ex)
			         {
				         ex.printStackTrace();  
				       //  JOptionPane.showMessageDialog(gf,"Error in " + description + "!","Error!",JOptionPane.ERROR_MESSAGE);
			         }
	                }
	            }.start();
	          
	            
	            p.waitFor(); 
	        
	            
	          
	         all= e.toString()+s.toString();
	        // System.out.println(all);
		     return all;
	          }
	      catch(IOException e)
	      {
	         e.printStackTrace();  
	      //   JOptionPane.showMessageDialog(gf,"Error in " + description + "!","Error!",JOptionPane.ERROR_MESSAGE);
	      }
		
		
	}catch(Exception ex)
	{
		ex.printStackTrace();
	//	JOptionPane.showMessageDialog(gf, "Problem executing " + description + "!", "Error!" , JOptionPane.ERROR_MESSAGE);	
	}
return all;
}
	
	
	public void run()
	{}


	public static HashMap<String,String> parseList(String list) {
		HashMap<String, String> map = new HashMap<String, String>();
		
		String[] split = list.trim().split("[ \r\n]+");
		
		for (int i = 21; i <split.length; i+=6)
			map.put(split[i+1], split[i]);
				
		return map;
		
	}
	
	public static ArrayList<Integer> findExtra(String list) {
		
		ArrayList<Integer> extras = new ArrayList<Integer>();
		
		String[] split = list.trim().split("[ \r\n]+");
		
		for (int i = 21; i <split.length; i+=6)
			if (!map.containsKey(split[i+1]))
					extras.add(Integer.parseInt(split[i+1]));
				
		return extras;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
