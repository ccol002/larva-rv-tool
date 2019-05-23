package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TabPanelHandler extends MouseAdapter{

	public TabPanel tp;
	
	public TabPanelHandler(TabPanel tp){
		this.tp = tp;
	}
	
	public void saveMtd(){
		String pathName = tp.path+"/"+tp.name;
		File f;
		try{
			f = new File(pathName);
			PrintWriter pw = new PrintWriter(f);
			pw.print(tp.text1.getText());
			pw.flush();
			JOptionPane.showMessageDialog(tp.gf, "Text file saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
		}catch(Exception e){
			System.out.println("Write file error.");
		}
	}
	
	public void closeMtd(){
		int ans = JOptionPane.showConfirmDialog(tp.gf, "<html>Are you sure you want to close this text file?<br>Changes will <u>not</u> be automatically saved.</html>",
				"Attention", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (ans == JOptionPane.YES_OPTION){
			tp.gf.jtp.remove(tp.gf.jtp.getSelectedComponent());
		}
	}
	
	public void compileMtd(){
		String command = "java -cp \"" + tp.gf.compilerAddress + "\" compiler.Compiler \"" + tp.gf.propertiesAddress + "\\" + tp.name + "\""
		+ " -o \"" + tp.gf.applicAddress + "\""
		+ " -g \"" + tp.gf.graphvizAddress + "\"";
		
		//String command = "java -cp \"" + tp.gf.compilerAddress + "\" compiler.Comler \"" + tp.gf.propertiesAddress + "" + tp.name + "\"" + " -o \"" + tp.gf.applicAddress + "\"";

		Runner.execute(command, "compiler call", tp.gf);
		
	}
	
	public void refreshMtd(){
		try{
			tp.text2.refresh();
		}catch(Exception ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(tp.gf, "Error while refreshing!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void diagramMtd()
	{
		String path = tp.gf.applicAddress+"\\larva";
		File dir = new File(path);
		String[] files = null;
		if (dir.exists())
		{
			files = dir.list(new FilenameFilter(){
			public boolean accept(File dir, String name){
				if (name.endsWith(".gif")) return true;
				else return false;
			}
			});
		}		
		if (files == null)
			JOptionPane.showMessageDialog(tp.gf, "No diagram files found! " + path, "Error!", JOptionPane.ERROR_MESSAGE);
		else
		{
			ArrayList<String> diags = new ArrayList<String>();
			
			try{
			int cnt = 0;
			String s = tp.text1.getText();
			while (cnt != -1)
			{
				cnt = s.indexOf("PROPERTY",cnt);
				if (cnt > -1)
				{
					cnt += 9;
					int indx = s.indexOf("{",cnt);
					if (indx > cnt)
					{
						String name = s.substring(cnt, indx).trim();
						diags.add(name);
					}
				}
				
			}
			}catch(Exception ex){ex.printStackTrace();}
			
			
			
			JFrame fr = new JFrame("Automaton Diagrams");
			fr.setSize(800, 400);
			JPanel p = new JPanel(new FlowLayout());
			//p.setSize(1200, 1000);
			
			int cnt = 0;
			
			for (String s : files)
			{
				String name = s.substring(7, s.length()-9);
				if (diags.contains(name))
				{
					cnt++;
					JPanel q = new JPanel(new BorderLayout());					
					q.add(new JLabel("Property: " + name),BorderLayout.NORTH);
					q.add(new JLabel(new ImageIcon(tp.gf.applicAddress+"\\larva\\"+s)),BorderLayout.CENTER);
					p.add(q);
				}
			}
			
			JScrollPane sp = new JScrollPane(p);
		//	sp.setPreferredSize(new Dimension(1200,1000));
			fr.getContentPane().add(sp);
			if (cnt > 0)
				fr.setVisible(true);
			else 
				JOptionPane.showMessageDialog(tp.gf, "No diagram files found! " + path, "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void mousePressed(MouseEvent e){
		if (e.getSource().equals(tp.saveBtn)) saveMtd();
		
		if (e.getSource().equals(tp.closeBtn)) closeMtd();
		
		if (e.getSource().equals(tp.compileBtn)) compileMtd();
		
		if (e.getSource().equals(tp.diagramBtn)) diagramMtd();
		
		if (e.getSource().equals(tp.refreshBtn)) refreshMtd();
	}
}
