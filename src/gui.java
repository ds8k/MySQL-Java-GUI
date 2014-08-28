/*
	Name: Cameron Bates
	Course: CNT4714 Fall 2012
	Assignment Title: Project 3 MySQL & JDBC
	Date: October 21, 2012
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class gui 
{
	private static JLabel user;
	private static JLabel pass;
	private static JLabel connect;
	private static JLabel dbInfo;
	private static JLabel sqlComm;
	private static JLabel sqlExec;
	private static JLabel dbURL;
	private static JLabel driver;
	
	private static JButton execButton;
	private static JButton clearCommButton;
	private static JButton connectButton;
	private static JButton clearResultsButton;
	
	private static JTextField userText;
	private static JTextField passText;
	private static JTextField dbText;
	private static JTextField driverText;
	
	private static JScrollPane resultScroll;
	private static JScrollPane commScroll;
	
	private static JSeparator separate;
	
	private static JPanel main;
	
	private static JTextArea command;
	
	private static JTable results;
	
	private static SpringLayout sLayout;
	
	private static JFrame frame;
	
	private connectionHandler conHandler;
	private windowHandler wHandler;
	private executeHandler execHandler;
	private clearCommandHandler clearCommHandler;
	private clearResultsHandler clearResHandler;
	
	private static engine engine = null;
	private static database database;

	public gui()
	{
		frame = new JFrame("SQL Client GUI");
		main = new JPanel();
		frame.setSize(750,450);

		separate = new JSeparator();
        Dimension dimension = separate.getPreferredSize();  
        dimension.height = 1;
        separate.setPreferredSize(dimension);   

        main.add(separate);
        
        dbInfo = new JLabel("Enter Database Information");
        main.add(dbInfo);
        
        sqlComm = new JLabel("Enter a SQL Command:");
        main.add(sqlComm);
        
        driver = new JLabel("JBDC Driver:");
        main.add(driver);
        
        dbURL = new JLabel("Database URL:");
        main.add(dbURL);
        
        user = new JLabel("Username:");
        main.add(user);
        
		pass = new JLabel("Password:");
		main.add(pass);
		
		connect = new JLabel("No Connection Now");
		main.add(connect);
		
		sqlExec = new JLabel("SQL Execution Result");
		main.add(sqlExec);
		
		userText = new JTextField(20);
		main.add(userText);
		
		passText = new JTextField(20);
		main.add(passText);
		
		command = new JTextArea(5,30);
		results = new JTable();

		driverText = new JTextField("com.mysql.jbdc.Driver");
		driverText.setEnabled(false);
		main.add(driverText);
		
		dbText = new JTextField("jdbc:mysql://localhost:3306/project3");
		dbText.setEnabled(false);
		main.add(dbText);

		resultScroll = new JScrollPane(results);
		main.add(resultScroll);
		
		commScroll = new JScrollPane(command);
		main.add(commScroll);
		
		connectButton = new JButton("Connect");
		conHandler = new connectionHandler(); 
		connectButton.addActionListener(conHandler);
		main.add(connectButton);
		
		execButton = new JButton("Execute SQL Command");
		execButton.setEnabled(false);
		execHandler = new executeHandler();
		execButton.addActionListener(execHandler);
		main.add(execButton);
		
		clearCommButton = new JButton("Clear Command");
		clearCommHandler = new clearCommandHandler();
		clearCommButton.addActionListener(clearCommHandler);
		main.add(clearCommButton);
		
		clearResultsButton = new JButton("Clear Results");
		clearResHandler = new clearResultsHandler();
		clearResultsButton.addActionListener(clearResHandler);
		main.add(clearResultsButton);
	
		sLayout = new SpringLayout();
		
		sLayout.putConstraint(SpringLayout.NORTH, dbInfo, 10, SpringLayout.NORTH, main);
		sLayout.putConstraint(SpringLayout.NORTH, driver,20, SpringLayout.SOUTH,dbInfo);
		sLayout.putConstraint(SpringLayout.NORTH, driverText, 15, SpringLayout.SOUTH, dbInfo);
		sLayout.putConstraint(SpringLayout.NORTH, dbURL,18, SpringLayout.SOUTH,driver);
		sLayout.putConstraint(SpringLayout.NORTH, dbText, 10, SpringLayout.SOUTH, driverText);
		sLayout.putConstraint(SpringLayout.NORTH, user, 20, SpringLayout.SOUTH, dbURL);
		sLayout.putConstraint(SpringLayout.NORTH, userText, 15, SpringLayout.SOUTH, dbText);
		sLayout.putConstraint(SpringLayout.NORTH, pass, 15, SpringLayout.SOUTH, user);
		sLayout.putConstraint(SpringLayout.NORTH, passText, 10, SpringLayout.SOUTH, userText);
		sLayout.putConstraint(SpringLayout.NORTH, sqlComm, 10, SpringLayout.NORTH, main);
		sLayout.putConstraint(SpringLayout.NORTH, commScroll, 10, SpringLayout.SOUTH, sqlComm);
		sLayout.putConstraint(SpringLayout.NORTH, resultScroll, 10, SpringLayout.SOUTH, clearResultsButton);
		sLayout.putConstraint(SpringLayout.NORTH, clearResultsButton, 10, SpringLayout.VERTICAL_CENTER, main);
		sLayout.putConstraint(SpringLayout.NORTH, sqlExec, 10, SpringLayout.VERTICAL_CENTER, main);

		sLayout.putConstraint(SpringLayout.WEST, dbInfo, 5, SpringLayout.WEST, main);
		sLayout.putConstraint(SpringLayout.WEST, driver,5, SpringLayout.WEST,main);
		sLayout.putConstraint(SpringLayout.WEST, driverText, 0, SpringLayout.WEST, userText);
		sLayout.putConstraint(SpringLayout.WEST, dbURL,5, SpringLayout.WEST,main);
		sLayout.putConstraint(SpringLayout.WEST, dbText, 0, SpringLayout.WEST, userText);
		sLayout.putConstraint(SpringLayout.WEST, user, 5, SpringLayout.WEST, main);
		sLayout.putConstraint(SpringLayout.WEST, userText, 30, SpringLayout.EAST, user);
		sLayout.putConstraint(SpringLayout.WEST, pass, 5, SpringLayout.WEST, main);
		sLayout.putConstraint(SpringLayout.WEST, passText, 0, SpringLayout.WEST, userText);
		sLayout.putConstraint(SpringLayout.WEST, connect, 10, SpringLayout.WEST, main);
		sLayout.putConstraint(SpringLayout.WEST, sqlComm, 160, SpringLayout.EAST, dbInfo);
		sLayout.putConstraint(SpringLayout.WEST, sqlComm, 10, SpringLayout.HORIZONTAL_CENTER, main);
		sLayout.putConstraint(SpringLayout.WEST, commScroll, 10, SpringLayout.HORIZONTAL_CENTER, main);
		sLayout.putConstraint(SpringLayout.WEST, execButton, 0, SpringLayout.WEST, commScroll);
		sLayout.putConstraint(SpringLayout.WEST, resultScroll, 10, SpringLayout.WEST, main);
		sLayout.putConstraint(SpringLayout.WEST, sqlExec, 5, SpringLayout.WEST, main);
		sLayout.putConstraint(SpringLayout.WEST, separate, 0, SpringLayout.WEST, main);

		sLayout.putConstraint(SpringLayout.EAST, driverText, -10, SpringLayout.HORIZONTAL_CENTER, main);
		sLayout.putConstraint(SpringLayout.EAST, dbText, -10, SpringLayout.HORIZONTAL_CENTER, main);
		sLayout.putConstraint(SpringLayout.EAST, userText, -10, SpringLayout.HORIZONTAL_CENTER, main);
		sLayout.putConstraint(SpringLayout.EAST, passText, -10, SpringLayout.HORIZONTAL_CENTER, main);
		sLayout.putConstraint(SpringLayout.EAST, connectButton, -10, SpringLayout.HORIZONTAL_CENTER, main);
		sLayout.putConstraint(SpringLayout.EAST, commScroll, -10, SpringLayout.EAST, main);
		sLayout.putConstraint(SpringLayout.EAST, clearCommButton, -10, SpringLayout.EAST, main);
		sLayout.putConstraint(SpringLayout.EAST, resultScroll, -10, SpringLayout.EAST, main);
		sLayout.putConstraint(SpringLayout.EAST, clearResultsButton, 0, SpringLayout.EAST, resultScroll);
		sLayout.putConstraint(SpringLayout.EAST, separate, 0, SpringLayout.EAST, main);

		sLayout.putConstraint(SpringLayout.SOUTH, connect, -10, SpringLayout.VERTICAL_CENTER, main);
		sLayout.putConstraint(SpringLayout.SOUTH, connectButton, -10, SpringLayout.VERTICAL_CENTER, main);
		sLayout.putConstraint(SpringLayout.SOUTH, commScroll, -10, SpringLayout.NORTH, clearCommButton);
		sLayout.putConstraint(SpringLayout.SOUTH, clearCommButton, -10, SpringLayout.VERTICAL_CENTER, main);
		sLayout.putConstraint(SpringLayout.SOUTH, execButton, -10, SpringLayout.VERTICAL_CENTER, main);
		sLayout.putConstraint(SpringLayout.SOUTH, resultScroll, -10, SpringLayout.SOUTH, main);

		sLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, separate, 0, SpringLayout.HORIZONTAL_CENTER, main);
		
		sLayout.putConstraint(SpringLayout.VERTICAL_CENTER, separate, 0, SpringLayout.VERTICAL_CENTER, main);

		main.setLayout(sLayout);
		frame.add(main);

		wHandler = new windowHandler();
		frame.addWindowListener(wHandler);

		frame.setVisible(true);
	}
	
	public class connectionHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			if(!userText.getText().isEmpty() )
			{
				engine = new engine("jdbc:mysql://localhost:3306/project3", userText.getText(), passText.getText());

				try 
				{
					engine.execConnect();
				} 
				catch (SQLException e) 
				{
					JOptionPane.showMessageDialog(main, "Could not connect!");
				}
				if(engine.getConnect() != null)
				{
					connect.setText("jdbc:mysql://localhost:3306/project3");
					execButton.setEnabled(true);
				}
			}
		}
	}
	
	public class executeHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			String query = command.getText();
			database = new database(engine.getConnect());
			Vector<String> column = new Vector<String>();
			Vector<Vector<String>> result = new Vector<Vector<String>>();

			if(query.toLowerCase().startsWith("select"))
			{
				try 
				{
					result = database.runQuery(query);
					column = database.getCol();
					results.setModel(new DefaultTableModel(result,column));
				} 
				catch (SQLException e) 
				{
					JOptionPane.showMessageDialog(main, "Could not execute query.");
				}
			}
			else
			{
				try 
				{
					database.update(query);
					results.setModel(new DefaultTableModel(new String[][]{new String[]{"SQL Command Executed"}},new String[]{""}));
				} 
				catch (SQLException e) 
				{
					JOptionPane.showMessageDialog(main, "Could not execute query.");
				}
			}

		}
	}
	
	public class clearCommandHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			command.setText("");
		}
	}
	
	public class clearResultsHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			results.setModel(new DefaultTableModel(new String[][]{new String[]{""}},new String[]{""}));
		}
	}
	
	public class windowHandler implements WindowListener
	{
		public void windowClosing(WindowEvent arg0) 
		{
			if(engine != null)
			{
				try 
				{
					engine.closeConnect();
				} 
				catch (SQLException e) 
				{
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
			}
			else
			{
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		}
		
		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosed(WindowEvent arg0) {

		}



		@Override
		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		gui test = new gui();
	}
}