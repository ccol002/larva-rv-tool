package compiler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Methods extends Compiler{

	StringBuilder sb = new StringBuilder();
	
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
			String filename = getString().substring(lastIndex+7, prevIndex);
			
			try{
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
				String temp;
				while ((temp = br.readLine()) != null)
				{
					temp = temp.replace("%%", "//");					
					sb.append(temp + "\r\n");
				}
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}						
		}		
		int lastIndexOf = getString().lastIndexOf("}");
		if (lastIndexOf != -1 && lastIndexOf >  prevIndex+1)
			sb.append(getString().substring(prevIndex+1, lastIndexOf+1));
	}
	
	public String toJava()
	{
		return Global.redirectOutput(sb.toString());
	}
}
