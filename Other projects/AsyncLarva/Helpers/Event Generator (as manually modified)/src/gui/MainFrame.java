package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import Events.Template;

public class MainFrame implements ActionListener{

	JButton btnUpdate;
	JButton btnDelete;
	JTextField tfdSql;
	JTextField tfdName;
	JTextField tfdTime;
	JTable tblEvents;
	
	LinkedHashMap<String, Template> events = new LinkedHashMap<String, Template>();
	
	
	public void initialize()
	{		
		//SQL
		JLabel lblSql = new JLabel("SQL statement");
		tfdSql = new JTextField("select * from              ",10);
//		JPanel pnlSql = new JPanel(new FlowLayout());
//		pnlSql.add(lblSql);
//		pnlSql.add(tfdSql);
		
		//name
		JLabel lblName = new JLabel("Name of event");
		tfdName = new JTextField("Event_Name",10);
//		JPanel pnlName = new JPanel(new FlowLayout());
//		pnlName.add(lblName);
//		pnlName.add(tfdName);
		
		//time
		JLabel lblTime = new JLabel("Timestamp field");
		tfdTime = new JTextField("timestamp",10);
//		JPanel pnlTime = new JPanel(new FlowLayout());
//		pnlTime.add(lblTime);
//		pnlTime.add(tfdTime);
		
		//create remove
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(this);
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(this);
		JPanel pnlEventButtons = new JPanel(new FlowLayout());
		pnlEventButtons.add(btnUpdate);
		pnlEventButtons.add(btnDelete);
				
		GridLayout gl = new GridLayout(0,2);
		gl.setVgap(10);
		JPanel pnlEvents = new JPanel(gl);
		pnlEvents.add(new JPanel());pnlEvents.add(new JPanel());
		pnlEvents.add(new JPanel());pnlEvents.add(new JPanel());
		pnlEvents.add(lblSql);
		pnlEvents.add(tfdSql);
		pnlEvents.add(lblName);
		pnlEvents.add(tfdName);
		pnlEvents.add(lblTime);
		pnlEvents.add(tfdTime);
		pnlEvents.add(new JPanel());
		pnlEvents.add(pnlEventButtons);
		pnlEvents.add(new JPanel());pnlEvents.add(new JPanel());
		
		//creation table
		JLabel lblTableEvents = new JLabel("Current Events");
		tblEvents = new JTable(0,3);
		
		JPanel pnlEventsTable = new JPanel(new BorderLayout());
		pnlEventsTable.add(lblTableEvents,BorderLayout.NORTH);
		pnlEventsTable.add(tblEvents,BorderLayout.CENTER);
		
		//all events panel
		JPanel pnlAllEvents = new JPanel(new GridLayout(0,1));
		pnlAllEvents.add(pnlEvents);
		pnlAllEvents.add(pnlEventsTable);
		
		
		
		
		//play panel - west side
		
		//selection of events
		JLabel lblEventList = new JLabel("Event List");
		JComboBox cmbEventList = new JComboBox();
		
		JButton bntAddToList = new JButton("Add to List");
		
		JPanel pnlSelection = new  JPanel(gl);
		pnlSelection.add(new JPanel());pnlSelection.add(new JPanel());
		pnlSelection.add(lblEventList);
		pnlSelection.add(cmbEventList);
		pnlSelection.add(new JPanel());
		pnlSelection.add(bntAddToList);
		pnlSelection.add(new JPanel());pnlSelection.add(new JPanel());
		pnlSelection.add(new JPanel());
		
		//selection table
		JLabel lblSelectedEvents = new JLabel("Current Selection");
		JTable tblSelectedEvents = new JTable(0,1);
		JPanel pnlSelectionTable = new JPanel(new BorderLayout());
		pnlSelectionTable.add(lblSelectedEvents,BorderLayout.NORTH);
		pnlSelectionTable.add(tblSelectedEvents,BorderLayout.CENTER);
	
		//db connection
		JLabel lblDb = new JLabel("Name of DB");
		JTextField tfdDb = new JTextField("DB_Name",10);
		
		JButton bntPlayEvents= new JButton("Play Events");
		
		JPanel pnlPlayEvents = new  JPanel(gl);
		pnlPlayEvents.add(new JPanel());pnlPlayEvents.add(new JPanel());
		pnlPlayEvents.add(lblDb);
		pnlPlayEvents.add(tfdDb);
		pnlPlayEvents.add(new JPanel());
		pnlPlayEvents.add(bntPlayEvents);
		pnlPlayEvents.add(new JPanel());pnlPlayEvents.add(new JPanel());
		pnlPlayEvents.add(new JPanel());
		//
		JPanel pnlPlay = new  JPanel(new GridLayout(0,1));
		//pnlPlay.add(new JPanel());
		pnlPlay.add(pnlSelection);
		pnlPlay.add(pnlSelectionTable);
		pnlPlay.add(pnlPlayEvents);
		//pnlPlay.add(new JPanel());
		
		
		
		
		
		JPanel pnlMain = new JPanel(new BorderLayout());
		pnlMain.add(pnlAllEvents,BorderLayout.WEST);
		pnlMain.add(pnlPlay,BorderLayout.EAST);
		
		JFrame frmMain = new JFrame("Event Player");
		frmMain.setLocation(200, 50);
		frmMain.setSize(800, 600);
		frmMain.add(pnlMain);
		
		frmMain.setVisible(true);
	}
	
	public MainFrame()
	{
		initialize();
	}
	
	public static void main(String[] args)
	{
		new MainFrame();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(btnUpdate))
		{
			
		}
		else if (event.getSource().equals(btnDelete))
		{}
	}
}
