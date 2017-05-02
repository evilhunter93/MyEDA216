package krustyKookies;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import javax.swing.DefaultListModel;

public class NewDatabase {

	private Connection conn;
	
	public boolean openConnection(String filename) {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + filename);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Close the connection to the database.
	 */
	public void closeConnection() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check if the connection to the database has been established
	 * 
	 * @return true if the connection has been established
	 */
	public boolean isConnected() {
		return conn != null;
	}
	
	public boolean login(String uid) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		boolean login = false;
		try {
			stmt = conn.createStatement();
			String query = "SELECT * FROM " + "users WHERE username = " + "\"" + uid + "\"";
			rs = stmt.executeQuery(query);
			login = rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (stmt != null)
				stmt.close();
		}
		if (login) {
			return true;
		}
		return false;

	}
	
	public Pallet producePallet(String cookieType){
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Pallet p = null;
		// Convert it to java.sql.Date
		Date date = new Date(System.currentTimeMillis());
		Time time = new Time(System.currentTimeMillis());
		try{
			String query = "INSERT INTO PALLETS (cookieType, location, date, time)" 
					+ "VALUES (?, ?, ?, ?)";
			stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, cookieType);
			stmt.setString(2, "Deep freeze storage");
			stmt.setDate(3, date);
			stmt.setTime(4, time);
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if(rs.next()){
				p = new Pallet(rs.getInt("ID"), cookieType, "Deep freeze storage", date, time);
			}
		}catch(SQLException e){
			e.printStackTrace();
		} finally{
			closeStatement(stmt);
		}
		
		return p;
	}
	
	public void getCookies(DefaultListModel<String> list){
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String query = "SELECT distinct name FROM recipes";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
				list.addElement(rs.getString("name"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeStatement(stmt);
		}
	}
	
	private ResultSet executeQuery(Statement stmt, String query) throws SQLException {
		stmt = conn.createStatement();
		return stmt.executeQuery(query);
	}
	
	private void closeStatement(Statement stmt) {
		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}