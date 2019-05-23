package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class TabPanel extends JPanel{

	JTextAreaReloaded text1;
	//JTextArea text2;
	JTextAreaReloaded text2;
	JButton saveBtn;
	JButton closeBtn;
	JButton compileBtn;
	JButton diagramBtn;
	JButton refreshBtn;
	
	GuiFrame gf;
	static String icons = "icons/"; 
	
	String[] files;
	String path;
	public String name;
	TabPanelHandler tph;
	
	public TabPanel(GuiFrame gf, String[] files, String path, String name){
		tph = new TabPanelHandler(this);
		this.gf = gf;
		this.files = files;
		this.path = path;
		this.name = name + ".txt";
		
		//text2 = new JTextArea();
		try{
			text1 = new JTextAreaReloaded(this.path + "\\" + this.name);
			text1.setEditable(true);
			String url = gf.applicAddress + "\\output_" + this.name;
			File f = new File(url);
			if (!f.exists())
				f.createNewFile();			
			text2 = new JTextAreaReloaded(url);
		}
		catch(Exception ex){
			ex.printStackTrace();
			JOptionPane.showMessageDialog(gf,"Error in connecting to output file!","Error!",JOptionPane.ERROR_MESSAGE);
		}
		
		text1.setAutoscrolls(true);
		text2.setAutoscrolls(true);
		//text1.setLineWrap(true);
		//text2.setLineWrap(true);
		
		
		
		JScrollPane scrl1 = new JScrollPane(text1);
		JScrollPane scrl2 = new JScrollPane(text2);
		
		saveBtn = new JButton("Save");
		saveBtn.addMouseListener(tph);
		closeBtn = new JButton("Close");
		closeBtn.addMouseListener(tph);
		compileBtn = new JButton("Compile");
		compileBtn.addMouseListener(tph);
		diagramBtn = new JButton("Diagrams");
		diagramBtn.addMouseListener(tph);
		refreshBtn = new JButton(new ImageIcon(gf.path + "icons\\refresh.gif"));
		refreshBtn.addMouseListener(tph);
		
		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		jsp.setDividerLocation(370);
		jsp.setAutoscrolls(true);
		jsp.setContinuousLayout(true);
		jsp.setOneTouchExpandable(true);

		jsp.add(scrl1, JSplitPane.LEFT);
		jsp.add(scrl2, JSplitPane.RIGHT);
		
		JPanel aux = new JPanel (new FlowLayout());
		aux.add(new JLabel("Alerts"));
		aux.add(refreshBtn);
		
		GridLayout gl = new GridLayout(1,2);
		gl.setHgap(20);
		gl.setVgap(5);
		JPanel textAreaPnl = new JPanel(gl);
		textAreaPnl.add(new JLabel("Property Text"));
		textAreaPnl.add(aux);
		
		JPanel buttonsPnl = new JPanel(new FlowLayout());
		buttonsPnl.add(saveBtn);
		buttonsPnl.add(closeBtn);
		buttonsPnl.add(compileBtn);
		buttonsPnl.add(diagramBtn);
		
		this.setLayout(new BorderLayout());
		this.add(textAreaPnl, BorderLayout.NORTH);
		this.add(jsp, BorderLayout.CENTER);
		this.add(buttonsPnl, BorderLayout.SOUTH);
	}

	
	public void updateEnabled(boolean enabled)
	{
		saveBtn.setEnabled(enabled);
		closeBtn.setEnabled(enabled);
		compileBtn.setEnabled(enabled);		
		refreshBtn.setEnabled(enabled);
	}

}
