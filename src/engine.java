/*
	Name: Cameron Bates
	Course: CNT4714 Fall 2012
	Assignment Title: Project 3 MySQL & JDBC
	Date: October 21, 2012
 */

import java.sql.*;
import com.mysql.jdbc.MySQLConnection;

public class engine  
{
	private MySQLConnection connect;
	private String address;
	private String user;
	private String pass;

	public engine(String address, String user, String password)
	{
		this.address = address;
		this.user = user;
		this.pass = password;
	}
	
	public void execConnect() throws SQLException
	{
		connect = (MySQLConnection)DriverManager.getConnection(this.address, this.user, this.pass);
	}

	public MySQLConnection getConnect()
	{
		return this.connect;
	}
	
	public void closeConnect() throws SQLException
	{
		connect.close();
	}
}