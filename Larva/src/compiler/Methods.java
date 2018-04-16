package compiler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Methods extends Compiler{

	StringBuilder scjava = new StringBuilder();
	
	StringBuilder methods = new StringBuilder();
	
	public Methods(ParsingString substring) {
		super(substring);
		parse();
	}

	public void parse()
	{		
		int lastIndex = 0;
		int prevIndex = 0;
		while ((lastIndex = getString().indexOf("import ", prevIndex)) != -1)
		{
			prevIndex = getString().indexOf(";",lastIndex);
			String filename = getString().substring(lastIndex+7, prevIndex).trim();
			
			if (filename.startsWith("\"") && filename.endsWith("\""))
				filename = filename.substring(1,filename.length()-1);
			
			BufferedReader br = null;
			try{
				br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
				String temp;
				while ((temp = br.readLine()) != null)
				{
					temp = temp.replace("%%", "//");					
					scjava.append(temp + "\r\n");
				}
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}	
			finally{
				try{
				br.close();
				} catch(Exception ex){}
			}					
		}		
		
		
		int lastIndexOf = getString().lastIndexOf("}");
		if (lastIndexOf != -1 && lastIndexOf >  prevIndex+1)
			methods.append(getString().substring(prevIndex+1, lastIndexOf+1));
	}
	
	public String toJava()
	{
		return Global.redirectOutput(scjava.toString());
	}
	
	public String getMethods()
	{
		return Global.redirectOutput(methods.toString());
	}
}
