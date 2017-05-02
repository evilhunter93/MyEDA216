package krustyKookies;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import javax.swing.DefaultListModel;

public class Database {

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
		try{
			String query = "INSERT INTO Pallets (cookieType, location, date)" 
					+ "VALUES (?, ?, ?, ?)";
			stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, cookieType);
			stmt.setString(2, "Deep freeze storage");
			stmt.setDate(3, date);
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if(rs.next()){
				p = new Pallet(rs.getInt("ID"), cookieType, "Deep freeze storage", date);
			}
		}catch(SQLException e){
			e.printStackTrace();
		} finally{
			closeStatement(stmt);
		}
		
		return p;
	}
	
	public Pallet getPallet(int id){
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Pallet p = null;
		
		try{
			String query = "Select * FROM Pallets WHERE ID = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if(rs.next()){
				p = new Pallet(id, rs.getString("cookieType"), rs.getString("Location"), 
						rs.getDate("Date"));
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
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String query = "SELECT distinct name FROM recipes";
			rs = stmt.executeQuery(query);
			while (rs.next())
				list.addElement(rs.getString("name"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeStatement(stmt);
		}
	}
	
	public void getIds(DefaultListModel<Integer> list, String cookieType, Date bDate, Date eDate){
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "SELECT distinct id FROM pallets WHERE cookieType = ? AND date > ? AND date < ?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, cookieType);
			stmt.setDate(2, bDate);
			stmt.setDate(3, eDate);
			rs = stmt.executeQuery(query);
			while (rs.next())
				list.addElement(rs.getInt("id"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeStatement(stmt);
		}
	}
	
	public void blockPallets(String cookieType, Date bDate, Date eDate){
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "UPDATE pallets WHERE cookieType = ? AND date > ? AND date < ?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, cookieType);
			stmt.setDate(2, bDate);
			stmt.setDate(3, eDate);
			stmt.executeUpdate(query);
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
