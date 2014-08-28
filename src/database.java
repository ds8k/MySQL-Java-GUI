/*
	Name: Cameron Bates
	Course: CNT4714 Fall 2012
	Assignment Title: Project 3 MySQL & JDBC
	Date: October 21, 2012
 */

import java.sql.*;
import java.util.Vector;
import com.mysql.jdbc.MySQLConnection;

public class database 
{
	private ResultSetMetaData meta;
	private MySQLConnection connect;
	private Vector<String> columns;

	public Vector<Vector<String>> runQuery(String query) throws SQLException
	{
		Vector<Vector<String>> result = new Vector<Vector<String>>();
		
		Statement statement = (Statement)this.connect.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		meta = resultSet.getMetaData();

		int numColumn = meta.getColumnCount();
		
		setCol(numColumn, meta);

		while(resultSet.next())
		{
			Vector<String> row = new Vector<String>();
			
			for(int i = 1; i <= numColumn; i++)
			{
				row.add(resultSet.getString(i));
			}
			
			result.add(row);
		}
		
		return result;
	}
	
	public void setCol(int numColumn, ResultSetMetaData meta) throws SQLException
	{
		columns = new Vector<String>();
		
		for(int i = 1; i <= numColumn; i++)
		{
			columns.add(meta.getColumnName(i));
		}
	}
	
	public int update(String query) throws SQLException
	{
		Statement statement = this.connect.createStatement();
		
		return statement.executeUpdate(query);
	}
	
	public database(MySQLConnection Connection)
	{
		this.connect = Connection;
	}

	public Vector<String> getCol() throws SQLException
	{
		return this.columns;
	}
}