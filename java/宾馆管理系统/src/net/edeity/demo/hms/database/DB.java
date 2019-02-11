package net.edeity.demo.hms.database;

import java.sql.*;

public class DB {
	static Connection conn;
	static Statement stat;
	public static void main(String[] args) throws SQLException {
		Connection con = getConnection();
	}
	public static Connection getConnection() {
	    try {
    		Class.forName("org.sqlite.JDBC");
    		conn = DriverManager.getConnection("jdbc:sqlite:hotel.db");
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
		return conn;
	  }
//以下是采用mysql时的数据库连接，因为不要移植，而且程序对数据库本身要求不大，所以改用sqlite
	/*
	public static Connection getConnection() {
		String url = "jdbc:mysql://10.0.16.16:4066";
		String user = "AV3968Q7";
		String pd = "m34i502w7064";
//		String url = "jdbc:mysql://211.87.225.181:3306/酒店管理系统";
//		String user = "remote";
//		String pd = "remote";
		try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(url, user, pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	*/
	public static ResultSet select(String sql) throws SQLException{
		Connection conn = DB.getConnection();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		return rs;
	}
	public static void insert(String sql) throws SQLException{
		Connection conn = DB.getConnection();
		Statement stat = conn.createStatement();
		stat.executeUpdate(sql);
	}
	public static void update(String sql) throws SQLException{
		Connection conn = DB.getConnection();
		Statement stat = conn.createStatement();
		stat.executeUpdate(sql);
	}
	public static boolean alter(String sql) throws SQLException{
		Connection conn = DB.getConnection();
		Statement stat = conn.createStatement();
		return stat.execute(sql);
	}
	/**
	 * 关闭连接
	 */
	public static void close() {
		if(stat!=null)
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if(conn!=null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
}
