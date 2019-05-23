package larvaOutput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Parser {

	public static String inputDir;

	public static String outputDir;

	public static int id = 0;//the component id

	public static String exception = "Larva";//lines with this label are put in the header!
	
	public static String name;//the name of the file being processed
	
	public static String excapeSequence = "{}";//if this symbol is found exactly after the label, the labels are ignored!!
		
	public static String compHeader1()
	{
		 return "<div style=\"background: PeachPuff; margin: 2px; padding: 1px;\"><a href=\"javascript:doMenu('main"+
		 		id+"');\" id=xmain"+id+">[-]</a>";
	}
	
	public static String compHeader2()
	{
		 return "<div id=main"+id+" style=\"margin: 2px; margin-left:2em; background: Moccasin; padding: 1px; \">";
	}
	
	public static String compFoot = "</div></div>";
	
	public static void parseAndOutput()throws Exception
	{

		HashMap<String,StringBuilder> list = new HashMap<String, StringBuilder>();

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputDir)));
		StringBuilder text = new StringBuilder();
		String temp;
		Set<String> oldlabels = new HashSet<String>();
		
		while ((temp = br.readLine()) != null)   {
			
			Set<String> labels = new HashSet<String>();
			int index = 0;
			int startindex = 0;
			
			//extract labels
			while ((startindex = temp.trim().indexOf("[",index)) == 0)
			{
				int endindex = temp.indexOf("]",index);
				if (endindex == -1)
					throw new Exception("] Expected! >> " + temp);
				
				//extract label
				String label = temp.substring(startindex + 1, endindex).toLowerCase();
				label = Character.toUpperCase(label.charAt(0)) + label.substring(1,label.length());
				labels.add(label);
				
				//create string with label removed
				String temp2 = temp.substring(0,startindex);
				if (endindex+1 < temp.length())
					temp2 += temp.substring(endindex+1,temp.length());
				temp = temp2.trim();//trim
				startindex = 0;							
			}
			
			if (labels.size() > 0 && temp.indexOf(excapeSequence) != 0)
			{
				//close previous divs
				for (String l : oldlabels)
					if (!l.equals(exception))
					{
						//remove div if unused
						if (list.get(l).lastIndexOf("padding: 1px; \">\r\n\r\n") == list.get(l).length()-20)
						{
							list.get(l).replace(list.get(l).lastIndexOf("<div id=main"), list.get(l).length(), "");
							list.get(l).append("\r\n\r\n </div> \r\n\r\n");
						}
						else
							list.get(l).append(compFoot + "\r\n\r\n");
					}
				
				//create new labels
				for (String l : labels)
					if (!list.containsKey(l))
						list.put(l, new StringBuilder());
				
				//create new divs
				for (String l : labels)
					if (!l.equals(exception))
					{					
						list.get(l).append(compHeader1() + "\r\n\r\n" + temp + "\r\n\r\n" + compHeader2() + "\r\n\r\n");
						id++;
					}
					else
						list.get(l).append(temp + "\r\n <br> \r\n");
					
				//replace old labels
				oldlabels = labels;
			}			
			else //continue divs of old labels
			{
				if (temp.indexOf(excapeSequence) == 0)
					temp = temp.substring(2,temp.length());
				for (String l : oldlabels)
					list.get(l).append("\r\n\r\n" + temp + "\r\n <br> \r\n");
			}
			
		}
		
		for (String s : list.keySet())
			if (!s.equals(exception))
				createPageForLabel(s, list.get(s));
		
		if (list.containsKey("Larva"))
			createHeader(list.get("Larva"));
		else
			createHeader(new StringBuilder());
		
		createMenu(new TreeSet<String>(list.keySet()));
		createCorner();
		createIndex();
		
	}
	
	public static void createPageForLabel(String label, StringBuilder sb)throws Exception
	{
		PrintWriter lb = new PrintWriter(outputDir + label + ".html");
		lb.write("\r\n<html>" +
				"\r\n<body bgcolor=\"white\">" +
				"\r\n<h2>" + label + "</h2>"+
				"\r\n<a href=\"javascript:doHideAll('main');\" >Hide All</a>" +
				"\r\n<a href=\"javascript:doShowAll('main');\" >Show All</a>" +
				sb.toString()+
				"\r\n</body>" +
				"\r\n</html>" +
				"\r\n<script language=\"JavaScript\"" +
				"\r\ntype=\"text/javascript\">" +
				"\r\nfunction doMenu(item)" +
				"\r\n{" +
				"\r\n	obj=document.getElementById(item);" +
				"\r\n	col=document.getElementById (\"x\" + item);" +
				"\r\n	if (obj.style.display==\"none\") {" +
				"\r\n		obj.style.display=\"block\";" +
				"\r\n		col.innerHTML=\"[-]\";" +
				"\r\n	}" +
				"\r\n	else {" +
				"\r\n		obj.style.display=\"none\";" +
				"\r\n		col.innerHTML=\"[+]\";" +
				"\r\n	}" +
				"\r\n}" +
				"\r\nfunction doHideAll(item)" +
				"\r\n{" +
				"\r\n	objs=document.getElementsByTagName('div');" +
				"\r\n	for (var i = 0; i < objs.length; i++)" +
				"\r\n	{" +
				"\r\n		if (objs[i].id.search(\"main\")!=-1" +
				"\r\n	   		&& objs[i].style.display!=\"none\")" +
				"\r\n		{" +
				"\r\n			objs[i].style.display=\"none\";" +
				"\r\n			col=document.getElementById (\"x\" + objs[i].id);" +
				"\r\n			col.innerHTML=\"[+]\";" +
				"\r\n		}" +
				"\r\n	}" +
				"\r\n}" +
				"\r\nfunction doShowAll(item)" +
				"\r\n{" +
				"\r\n	objs=document.getElementsByTagName('div');" +
				"\r\n	for (var i = 0; i < objs.length; i++)" +
				"\r\n	{" +
				"\r\n		if (objs[i].id.search(\"main\")!=-1" +
				"\r\n			&& objs[i].style.display!=\"block\")" +
				"\r\n		 {" +
				"\r\n			objs[i].style.display=\"block\";" +
				"\r\n			col=document.getElementById (\"x\" + objs[i].id);" +
				"\r\n			col.innerHTML=\"[-]\";" +
				"\r\n		}" +
				"\r\n	}" +
				"\r\n}" +
				"\r\n</script>");	
		lb.close();
	}
	
	public static void createHeader(StringBuilder sb)throws Exception
	{
		PrintWriter header = new PrintWriter(outputDir + "header.html");
		header.write("<html>" +
				"\r\n\r\n<body bgcolor=\"LightBlue\">" +
				"\r\n\r\n" +
				sb.toString() +
				"\r\n\r\n</body>" +
				"\r\n\r\n</html>");
		header.close();
	}
	
	public static void createIndex()throws Exception
	{
		PrintWriter index = new PrintWriter(outputDir + "index.html");
		index.write("<html>" +
				"\r\n\r\n<head>" +
				"\r\n\r\n<title>Larva compiler output</title>" +
				"\r\n<frameset cols=\"160,*\" rows=\"105,*\" frameborder=\"NO\" border=\"4\"" +
				"\r\nframespacing=\"0\" bordercolor=\"#999900\">" +
				"\r\n<frame src=\"larva.html\"  name=\"larva\"   noresize >" +
				"\r\n<frame src=\"header.html\" name=\"header\"  noresize >" +
				"\r\n<frame src=\"menu.html\"   name=\"leftbar\" noresize >" +
				"\r\n<frame  name=\"main\" noresize >" +
				"\r\n</frameset>" +
				"\r\n<noframes>" +
				"\r\n</noframes>" +
				"\r\n</html>");
		index.close();
	}
	
	public static void createCorner()throws Exception
	{
		PrintWriter corner = new PrintWriter(outputDir + "larva.html");
		corner.write("<html>" +
				"\r\n\r\n<body bgcolor=\"Bisque\">" +
				"\r\n<strong><font size=\"4\"> LARVA </font><br> </strong> " +
			//	"<font size=\"2\"> output for: </font> <br> " +
				"<strong> <font size=\"3\">*"+ name + "*</font></strong>" +
				"\r\n</body>" +
				"\r\n</html>");
		corner.close();
	}
	
	public static void createMenu(Set<String> labels)throws Exception
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<html>" +
				"\r\n\r\n<body bgcolor=\"LightBlue\">");
		
		for (String l : labels)
			if (!l.equals(exception))
				sb.append("<a href=\"" + l + ".html\" target=main>" + l + "</a><br>");

		sb.append("\r\n\r\n</body>" +
				"\r\n\r\n</html>");
		
		PrintWriter menu = new PrintWriter(outputDir + "menu.html");
		menu.write(sb.toString());
		menu.close();
	}
	
	public static void main(String[] args) 
	{
		try{
			if (args.length == 0)
			{
				System.out.println("You must specify the path of a LARVA output file!!");
				System.out.println("Optional switches you can use:");
				System.out.println("  -o <dir> to specify the output directory");		
			}
			else
			{
				inputDir = args[0];
				for (int i = 1; i < args.length; i++)
				{
					if (args[i].equals("-o") && i < args.length-1)
					{
						outputDir = args[i+1];
						if (!outputDir.endsWith("\\"))
							outputDir += "\\";
					}
				}
				
				name = inputDir.substring(inputDir.lastIndexOf("\\") + 1, inputDir.length());
				
				if (outputDir == null)
				{
					if (inputDir.lastIndexOf(".") > 0)
						outputDir = inputDir.substring(0, inputDir.lastIndexOf(".")) + "\\\\";
					else
						outputDir = inputDir.substring(0, inputDir.length()) + "\\\\";
				}
				
				File f = new File(outputDir);
				f.mkdirs();
				
				parseAndOutput();	
						
				System.out.println("Completed Successfully!!!");
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}

	}

}
