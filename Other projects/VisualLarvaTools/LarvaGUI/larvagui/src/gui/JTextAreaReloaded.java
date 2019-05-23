package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JTextArea;

public class JTextAreaReloaded extends JTextArea implements Runnable {

	public static final int maxshown = 100000;
	public static final int oneread = 10000;

	FileReader fr;
	BufferedReader br;
	StringBuilder text;
	char[] cbuf;
	
	public File file;
	public int period = 10000;//1 second
	public boolean autoRefresh = false;
	
	public int offset;
	
	
	public JTextAreaReloaded(String s)throws Exception
	{		
		this(new File(s));
		//this.setDocument(new Fixe) 
	}
	
	public JTextAreaReloaded(File f)throws Exception
	{
		super();
		this.setEditable(false);
		this.file = f;
		refresh(); 
	}
		
	public void refresh()throws Exception
	{		
		reset();
		getFromFile();
	}
	
	public void reset()throws Exception
	{
		offset = 0;
		fr = new FileReader(file);
		br = new BufferedReader(fr);
		text = new StringBuilder(getText());
		cbuf = new char[JTextAreaReloaded.oneread];
	}
	
	public void periodicRefresh()
	{
		try{
		autoRefresh = true;
		reset();		
		Thread t= new Thread(this);
		t.start();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
//	public void setText(String s)
//	{
//		try{
//		throw new Exception();
//		}catch(Exception ex)
//		{
//			ex.printStackTrace();
//		}
//	}
	
	public void getFromFile()throws Exception
	{			
		int actual=JTextAreaReloaded.oneread;		
		try{	
				
		while ((actual = br.read(cbuf, 0, JTextAreaReloaded.oneread))>0)
		{
			offset += actual;
			text.append(String.valueOf(cbuf).substring(0,actual));
			int extra = text.length() - JTextAreaReloaded.maxshown;
			if (extra > 0)
				text.delete(0, extra);
		}
//		String x;
//		while((x = br.readLine()) != null)			 
//			allText += x+"\r\n";
		}
		catch(Exception ex)
		{
			throw ex;
			//ex.printStackTrace();
		}
		setText(text.toString());
	
	}

	public void run() {
		
		while (autoRefresh)
		{
			try{
				getFromFile();
				Thread.sleep(period);
			}catch(Exception ex)
			{ex.printStackTrace();}
		}
		
	}
	
}
