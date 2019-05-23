package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class MouseHandler extends MouseAdapter implements ActionListener, Runnable{

	public static GuiFrame gf;
	public static String runcommand;
	
	public MouseHandler(GuiFrame gf){
		this.gf = gf;
	}
	
	public void newMtd(){
		JFileChooser jf = new JFileChooser();
		jf.setFileFilter(new FileFilter(){
			public boolean accept(File f){
				if (f.isDirectory() || f.getName().endsWith("txt")) return true;
				else return false;
			}
			public String getDescription(){
				return "Text files";
			}
		});
		int ans = jf.showSaveDialog(gf);
		if (ans == JFileChooser.APPROVE_OPTION){
			File f = jf.getSelectedFile();
			if (!f.getName().endsWith(".txt"))
				f = new File(f.getPath()+".txt");
			try{
				if (!f.createNewFile())
					JOptionPane.showMessageDialog(gf, "A text file with this name already exist.", "Attention", JOptionPane.INFORMATION_MESSAGE);
			}catch(Exception e){
				System.out.println("new file error.");
				return;
			}
			String path = f.getParentFile().getPath();
			gf.urlTxt.setText(path);
			try{
			String[] files = fillCombo(f.getParentFile(), f.getName());
			gf.addTab(files, path, nameOnly(f.getName()));
			}catch(Exception ex)
			{ex.printStackTrace();}
			
		}
	}
	
	String nameOnly(String s){
		if (s.length() < 4) return "";
		return s.substring(0, s.length()-4);
	}
	
	public void loadMtd(){
		if (gf.urlTxt.getText().equals("") || gf.combo.getItemCount() == 0) return;
		String fileName = (String)gf.combo.getSelectedItem();
		String nameOnly = nameOnly(fileName);
		String s = gf.urlTxt.getText()+"\\"+fileName;
		File f;
		if (tabNumber(nameOnly) >= 0){
			int ans = JOptionPane.showConfirmDialog(gf, "This file is in use. Current data will be lost on proceeding.", 
					"Attention", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
			if (ans == JOptionPane.CANCEL_OPTION || ans == JOptionPane.CLOSED_OPTION)
				return;
			else{
				gf.jtp.removeTabAt(tabNumber(nameOnly));
			}
		}
		try{
			f = new File(s);
			String[] files = fillCombo(f.getParentFile(), nameOnly);
			TabPanel tp = gf.addTab(files, gf.urlTxt.getText(), nameOnly);
			
			tp.text1.getFromFile();
			
		}catch(Exception ioe){
            System.out.println("File error");
            ioe.printStackTrace();
            JOptionPane.showMessageDialog(gf, "Make sure you have administrator rights!", "Error", JOptionPane.ERROR_MESSAGE);
               
        }
	}
	
	int tabNumber(String name){
		for (int i = 0; i < gf.jtp.getTabCount(); i++)
			if (gf.jtp.getTitleAt(i).equals(name)) return i;
		return -1;
	}
	
	//settings button
	public void settingsMtd(){
		gf.jd = new JDialog(gf, "Settings");
		gf.jd.setSize(450, 350);
		gf.jd.setLocation(350, 150);
		
		gf.compilerTxt = new JTextField(30);
		gf.compilerTxt.setText(gf.compilerAddress);
		gf.brCompilerBtn = new JButton(new ImageIcon(gf.path +"icons\\open.gif"));
		gf.brCompilerBtn.addMouseListener(this);
		gf.applicTxt = new JTextField(30);
		gf.applicTxt.addFocusListener(new FocusAdapter(){
			public void focusLost(FocusEvent e){
				File file = new File(gf.applicTxt.getText());
				if (file.isDirectory())
						listCodeFiles(file);
			}
		});
		
		//application address
		gf.applicTxt.setText(gf.applicAddress);
		gf.applicTxt.addActionListener(this);
		
		gf.brApplicBtn = new JButton(new ImageIcon(gf.path+"icons\\open.gif"));
		gf.brApplicBtn.addMouseListener(this);
		
//		gf.mainCmb = new JComboBox();
//		if (!gf.applicAddress.equals("")) listCodeFiles(new File(gf.applicAddress));
//		else gf.mainCmb.addItem("       ");
//		if (!gf.mainClass.equals("")) gf.mainCmb.setSelectedItem(gf.mainClass);
		gf.mainCmb = new JTextField(20);
		gf.mainCmb.setText(gf.mainClass);
		gf.argTxt = new JTextField(20);
		gf.argTxt.setText(gf.arguments);
		gf.okBtn = new JButton("Ok");
		gf.okBtn.addMouseListener(this);
		
		JPanel compilerPnl = new JPanel(new FlowLayout());
		compilerPnl.add(gf.compilerTxt);
		compilerPnl.add(gf.brCompilerBtn);
		
		JPanel applicPnl = new JPanel(new FlowLayout());
		applicPnl.add(gf.applicTxt);
		applicPnl.add(gf.brApplicBtn);
		
		GridLayout gl = new GridLayout(2,2);
		gl.setHgap(20);
		JPanel classArgsPnl = new JPanel(gl);
		classArgsPnl.add(new JLabel("Main Class"));
		classArgsPnl.add(new JLabel("Arguments"));
		classArgsPnl.add(gf.mainCmb);
		classArgsPnl.add(gf.argTxt);
		
		JPanel aux = new JPanel(new GridLayout(5,1));
		aux.add(new JLabel("Compiler Address"));
		aux.add(compilerPnl);
		aux.add(new JLabel("Application Address"));
		aux.add(applicPnl);
		aux.add(classArgsPnl); 
		
		FlowLayout fl = new FlowLayout(FlowLayout.RIGHT);
		JPanel okPnl = new JPanel(fl);
		okPnl.add(gf.okBtn);
		
		gf.jd.setLayout(new BorderLayout());
		gf.jd.add(aux, BorderLayout.CENTER);
		gf.jd.add(okPnl, BorderLayout.SOUTH);
		gf.jd.setVisible(true);
	}
	
	public void listCodeFiles(File file){
		String[] files = file.list(new FilenameFilter(){
			public boolean accept(File dir, String name){
				if (name.endsWith(".class")) return true;
				else return false;
			}
		});
		//DefaultComboBoxModel cbm = new DefaultComboBoxModel(files);
		//gf.mainCmb.setModel(cbm);
	}
	
	public void runMtd()
	{				
		String compile1 = "C:\\WINDOWS\\system32\\cmd.exe /c"
			+ "ajc -1.5 -cp \"" + gf.compilerAddress + "\\aspectjrt.jar\";\"" + gf.applicAddress
			+ "\" \"" + gf.applicAddress + "\\larva\\*.java\"";
		
		String compile2 = "C:\\WINDOWS\\system32\\cmd.exe /c"
			+ "ajc -1.5 -cp \"" + gf.compilerAddress + "\\aspectjrt.jar\";\"" + gf.applicAddress + "\" "
			+ " -outxmlfile \""+ gf.applicAddress +"\\META-INF\\aop.xml\" \""+ gf.applicAddress + "\\aspects\\*.aj\"";
		
	//	String run = "aj5 -cp \"" + gf.applicAddress + "\" \"" + gf.mainClass + "\"";
		
		String run = "java -classpath \""+gf.aspectjAddress+"\\lib\\aspectjweaver.jar;"+gf.applicAddress+"\" \"-javaagent:"+gf.aspectjAddress+"\\lib\\aspectjweaver.jar\" " + gf.mainClass;
		//String run = "\"%JAVA_HOME%\\bin\\java\" -classpath \"%ASPECTJ_HOME%\\lib\\aspectjweaver.jar;%CLASSPATH%\" \"-javaagent:%ASPECTJ_HOME%\\lib\\aspectjweaver.jar\" %*";
		
//		Runner.execute(compile1, "compilation of automata", gf);
//		Runner.execute(compile2, "compilation of aspects", gf);
				
		gf.updateEnabled(false);
		
//		try{
//		Runtime.getRuntime().exec("C:\\WINDOWS\\system32\\cmd.exe /c if \"%JAVA_HOME%\" == \"\" set JAVA_HOME=\"C:\\Program Files (x86)\\Java\\jre1.6.0_10\"");
//		Runtime.getRuntime().exec("C:\\WINDOWS\\system32\\cmd.exe /c if \"%ASPECTJ_HOME%\" == \"\" set ASPECTJ_HOME=\"C:\\Program Files (x86)\\Java\\aspectj1.6\"");
//		Runtime.getRuntime().exec("C:\\WINDOWS\\system32\\cmd.exe /c set path JAVA_HOME = JAVA_HOME=\"C:\\Program Files (x86)\\Java\\jre1.6.0_10\"");
//		Runtime.getRuntime().exec("C:\\WINDOWS\\system32\\cmd.exe /c set path ASPECTJ_HOME = set ASPECTJ_HOME=\"C:\\Program Files (x86)\\Java\\aspectj1.6\"");
//		
//		}catch(Exception ex)
//		{ex.printStackTrace();}
		
		
		for (int i = 0; i < gf.jtp.getTabCount(); i ++)
		{			
			TabPanel tab = (TabPanel)gf.jtp.getComponentAt(i); //seems that there is a bug in getTabComponentAt
		
			tab.updateEnabled(false);
			
			File f = tab.text2.file;
			try{
				tab.text2.br.close();
				tab.text2.fr.close();
				f.delete();
				f.createNewFile();
				tab.text2.setText("");
			//tab.text2.invalidate();
			
//			Object o = new Object();
//			try{
//				synchronized(o) {o.wait(1000);}
//				//Thread.sleep(period);
//				}catch(Exception ex)
//				{ex.printStackTrace();}
				
			//tab.text2 = new JTextAreaReloaded(f);
			//jsp.add(scrl2, JSplitPane.RIGHT);
			}catch(Exception ex)
			{ex.printStackTrace();}
			
			tab.text2.periodicRefresh();			
		}
		
		
		
		runcommand = run;
		
//		String list = Runner.quickRun("tasklist");
//		Runner.map = Runner.parseList(list);
						
		new Thread(this).start();	
		
	}
		
	public void updateEnabled()
	{
		for (int i = 0; i < gf.jtp.getTabCount(); i ++){
			TabPanel tab = (TabPanel)gf.jtp.getComponentAt(i); 
			tab.updateEnabled(true);
		}
	}
		
	public void browseMtd(JTextField jtf){
		JFileChooser jf = new JFileChooser();
		if (!jtf.getText().equals("")) jf.setSelectedFile(new File(jtf.getText()));
		jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY );
		jf.setFileFilter(new FileFilter(){
			public boolean accept(File dir){
				if (dir.isDirectory()) return true;
				else return false;
			}
			public String getDescription(){
				return "All Folders";
			}
		});
		int i = jf.showOpenDialog(gf);
		if (i == JFileChooser.APPROVE_OPTION){
			File f = jf.getSelectedFile();
			jtf.setText(f.toString());
			gf.propertiesAddress = f.toString();
			try
			{
				fillCombo(f, jf.getSelectedFile().getName());
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				JOptionPane.showMessageDialog(gf, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
				
			}
		}
	}
	
	public static String[] fillCombo(File dir, String select)throws Exception{
		String[] files = dir.list(new FilenameFilter(){
			public boolean accept(File dir, String name){
				if (name.endsWith(".txt")) return true;
				else return false;
			}
		});
		if (files == null)
			throw new Exception();
//		{
//			files = new String[1];
//			files[0] = "           ";
//		}
		DefaultComboBoxModel cbm = new DefaultComboBoxModel(files);
		gf.combo.setModel(cbm);
		gf.combo.setSelectedItem(select);
		return files;
	}
	
	public void okMtd(){
		gf.jd.setVisible(false);
		gf.compilerAddress = gf.compilerTxt.getText();
		gf.applicAddress = gf.applicTxt.getText();
		gf.mainClass = gf.mainCmb.getText();//(String)gf.mainCmb.getSelectedItem();
		gf.arguments = gf.argTxt.getText();
		
		gf.compilerTxt = null;
		gf.applicTxt = null;
		gf.brCompilerBtn = null;
		gf.brApplicBtn = null;
		gf.mainCmb = null;
		gf.argTxt = null;
		gf.jd = null;
	}
		
	public void mousePressed(MouseEvent e){
		if (e.getSource().equals(gf.newBtn)) newMtd();
		
		if (e.getSource().equals(gf.loadBtn)) loadMtd();
		
		if (e.getSource().equals(gf.settingsBtn)) settingsMtd();
		
		if (e.getSource().equals(gf.runBtn)) runMtd();
		
		if (e.getSource().equals(gf.stopBtn)) stopMtd();
		
		if (e.getSource().equals(gf.browseBtn)) browseMtd(gf.urlTxt);
		
		if (e.getSource().equals(gf.brCompilerBtn)) browseMtd(gf.compilerTxt);
		
		
		//set application directory
		if (e.getSource().equals(gf.brApplicBtn)) {
			browseMtd(gf.applicTxt);
			File file = new File(gf.applicTxt.getText());
			if (file.isDirectory())
			{
				listCodeFiles(file);
				gf.applicAddress = gf.applicTxt.getText();
			}
		}
		
		if (e.getSource().equals(gf.okBtn)) okMtd();
	}

	public void stopMtd() 
	{		
//		try{
//			for(int i = 0; i < Runner.extras.size(); i++)
//				Runner.quickRun("taskkill /F /T /PID " + Runner.extras.get(i));
//			
//		}catch(Exception ex){ex.printStackTrace();}
		Runner.p.destroy();
	}

	public void actionPerformed(ActionEvent e) 
	{
		//properties address changed
		if (e.getSource().equals(gf.urlTxt))
		{
			try
			{
				fillCombo(new File(gf.urlTxt.getText()), "");
				gf.propertiesAddress = gf.urlTxt.getText();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				JOptionPane.showMessageDialog(gf, "Error in directory specified!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		else if (e.getSource().equals(gf.applicTxt))
		{
			try
			{
				listCodeFiles(new File(gf.applicTxt.getText()));
				gf.applicAddress = gf.applicTxt.getText();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				JOptionPane.showMessageDialog(gf, "Error in directory specified!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void run()
	{
		Runner.execute(runcommand, "run of application", gf);
	}
}
