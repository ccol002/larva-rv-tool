package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class GuiFrame extends JFrame{

	
	public String compilerAddress = "C:\\Documents and Settings\\ixaris\\Desktop\\projects3\\Larva\\bin";
	public String applicAddress = "C:\\Documents and Settings\\ixaris\\Desktop\\projects3\\DBConnection\\bin";
	public String propertiesAddress = "C:\\Documents and Settings\\ixaris\\Desktop\\projects3\\properties"; 
//	public String compilerAddress = "C:\\Users\\University User\\Desktop\\projects\\Larva\\bin";
//	public String applicAddress = "C:\\Users\\University User\\Desktop\\projects\\Testing\\bin";
//	public String propertiesAddress = "C:\\Users\\University User\\Desktop\\projects\\Larva\\bin"; 
	public String mainClass = "Events.EventGenerator";
//assuming java is a know command in the command prompt
	public String aspectjAddress = "C:\\Program Files (x86)\\Java\\aspectj1.6";
	public String graphvizAddress = "C:\\Program Files (x86)\\Graphviz 2.21";
	
	public String arguments = "";
	public String path = "\\";
	
	JTabbedPane jtp;
	JButton loadBtn;
	JButton newBtn;
	JButton runBtn;
	JButton stopBtn;
	JButton settingsBtn;
	JButton browseBtn;
	JComboBox combo;
	JTextField urlTxt;
	JDialog jd;
	JTextField compilerTxt;
	JButton brCompilerBtn;
	JTextField applicTxt;
	JButton brApplicBtn;
	//JComboBox mainCmb;
	JTextField mainCmb;
	JTextField argTxt;
	JButton okBtn;
	MouseHandler mh;
	
	

	public GuiFrame(){
		//initializations
//		compilerAddress = "";
//		applicAddress = "";
//		mainClass = "";
//		arguments = "";
		
//		Class cls=this.getClass();
//		String className=cls.getName().concat(".class");
//		Package pck=cls.getPackage();
//		String packageName;
//		if(pck!=null){
//		packageName=pck.getName();
//		if(className.startsWith(packageName))
//		className=className.substring(packageName.length()+1);
//		}
//		java.net.URL url=cls.getResource(className);
//		String classFilePath=url.getPath();
		
		
		try{
			Properties prop = new Properties();
			String className = this.getClass().getName()+ ".class";
			className = className.substring(className.indexOf(".")+1);
			path = this.getClass().getResource(className).getPath().replaceAll("%20", " ");
			path = path.substring(0, path.lastIndexOf("/"));
			path = path.substring(0, path.lastIndexOf("/")+1);
			File f = new File(path + "config.txt");
			prop.load(new FileReader(f));
		
			compilerAddress = prop.getProperty("compilerAddress");
			applicAddress = prop.getProperty("applicAddress");
			propertiesAddress = prop.getProperty("propertiesAddress");
			mainClass = prop.getProperty("mainClass");
			aspectjAddress = prop.getProperty("aspectjAddress");
			graphvizAddress = prop.getProperty("graphvizAddress");
			arguments = prop.getProperty("arguments");
		
		}catch(Exception ex)
		{ex.printStackTrace();
		JOptionPane.showMessageDialog(this, "Configuration file not found.", "Message", JOptionPane.INFORMATION_MESSAGE);
		}
		
		mh = new MouseHandler(this);
		jtp = new JTabbedPane();
		loadBtn = new JButton("Load");
		loadBtn.addMouseListener(mh);
		newBtn = new JButton("New");
		newBtn.addMouseListener(mh);
		runBtn = new JButton("Run");
		runBtn.addMouseListener(mh);
		stopBtn = new JButton("Stop");
		stopBtn.addMouseListener(mh);
		stopBtn.setEnabled(false);
		settingsBtn = new JButton("Settings");
		settingsBtn.addMouseListener(mh);
		browseBtn = new JButton(new ImageIcon(path + "icons\\open.gif"));
		browseBtn.addMouseListener(mh);
		combo = new JComboBox();
		combo.addItem("               ");
		urlTxt = new JTextField(50);
		//urlTxt.addMouseListener(mh);
		urlTxt.addActionListener(mh);
		urlTxt.setText(propertiesAddress);
		
		
		JPanel buttonsPnl = new JPanel(new FlowLayout());
		buttonsPnl.add(newBtn);
		buttonsPnl.add(combo);
		buttonsPnl.add(loadBtn);
		buttonsPnl.add(settingsBtn);
		buttonsPnl.add(runBtn);
		buttonsPnl.add(stopBtn);
		
		JPanel urlPnl = new JPanel(new FlowLayout());
		urlPnl.add(urlTxt);
		urlPnl.add(browseBtn);
		try{
		MouseHandler.fillCombo(new File(urlTxt.getText()), "");
		}
		catch(Exception ex)
		{JOptionPane.showMessageDialog(this, "Error in directory specified!", "Error", JOptionPane.ERROR_MESSAGE);}
		
		JPanel southPnl = new JPanel(new BorderLayout());
		southPnl.add(new JLabel("URL"), BorderLayout.NORTH);
		southPnl.add(urlPnl, BorderLayout.CENTER);
		southPnl.add(buttonsPnl, BorderLayout.SOUTH);
		
		this.setTitle("LARVA GUI");
		this.setLocation(200, 50);
		this.setSize(800, 600);
		this.setLayout(new BorderLayout());
		this.add(jtp, BorderLayout.CENTER);
		this.add(southPnl, BorderLayout.SOUTH);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent arg0) {
				if (stopBtn.isEnabled())
					mh.stopMtd();
				System.exit(0);
			}
		});
		this.setVisible(true);
	}
	
	public void updateEnabled(boolean enabled)
	{
		 loadBtn.setEnabled(enabled);
		 newBtn.setEnabled(enabled);
		 runBtn.setEnabled(enabled);
		 
		 settingsBtn.setEnabled(enabled);
		 browseBtn.setEnabled(enabled);
		 combo.setEnabled(enabled);
		 urlTxt.setEnabled(enabled);
		 for (int i = 0; i < jtp.getTabCount(); i ++)
			{
				TabPanel tab = (TabPanel)jtp.getComponentAt(i); //seems that there is a bug in getTabComponentAt			
				tab.updateEnabled(enabled);	
			}
	}
	
	public TabPanel addTab(String[] files, String path, String name){
		TabPanel tp = new TabPanel(this, files, path, name);
		jtp.add(name, tp);
		jtp.setSelectedComponent(tp);
		return tp;
	}
	
	public static void main(String[] args) {	
		new GuiFrame();
	}
}
