package krustyKookies;

import java.sql.*;
import java.util.*;

import javax.swing.DefaultListModel;

/**
 * Database is a class that specifies the interface to the movie database. Uses
 * JDBC.
 */
public class OldDatabase {

	/**
	 * The database connection.
	 */
	private Connection conn;

	/**
	 * Create the database interface object. Connection to the database is
	 * performed later.
	 */
	public OldDatabase() {
		conn = null;
	}

	/**
	 * Open a connection to the database, using the specified user name and
	 * password.
	 */
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

	/* --- insert own code here --- */

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

	public void getMovies(DefaultListModel<String> list) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String query = "SELECT distinct movie FROM shows";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
				list.addElement(rs.getString("movie"));
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

	public void getDateList(DefaultListModel<String> list, String movieName) {
		Statement stmt = null;
		try {
			ResultSet rs = executeQuery(stmt, "SELECT distinct day FROM shows WHERE movie ='" + movieName + "'");

			while (rs.next())
				list.addElement(rs.getString("day"));
		}

		catch (SQLException e) {
			e.printStackTrace();

		} finally {
			closeStatement(stmt);
		}
	}

	public String getTheaterName(String movieName, String day) {
		Statement stmt = null;
		try {

			ResultSet rs = executeQuery(stmt, "Select distinct theater_name " + "FROM shows WHERE movie ='" + movieName
					+ "'" + "AND day ='" + day + "'");

			if (rs.next())
				return rs.getString("theater_name");

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			closeStatement(stmt);
		}

		return null;
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

	public String getSeatsLeft(String movieName, String day) {
		Statement stmt = null;
		try {

			ResultSet rs = executeQuery(stmt, "SELECT available_seats FROM " + "shows WHERE movie='" + movieName + "'"
					+ "AND day ='" + day + "'");

			if (rs.next())
				return rs.getString("available_seats");
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			closeStatement(stmt);
		}

		return null;
	}

	public int bookTicket(String movieName, String day, String username) {
		Statement stmt = null;
		int seats = 0;
		try {

			stmt = conn.createStatement();
			conn.setAutoCommit(false);

			String query = "SELECT available_seats FROM " + "shows WHERE movie='" + movieName + "'" + "AND day ='" + day
					+ "'";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				if (rs.getInt("available_seats") > 0) {
					System.out.println("SEATS AVAILABLE   " + rs.getInt("available_seats"));
					seats = rs.getInt("available_seats");

					conn.createStatement();
					String bookTicketQuery = "UPDATE shows SET available_seats = available_seats - 1" + " WHERE movie='"
							+ movieName + "'" + " AND day ='" + day + "'";

					stmt.executeUpdate(bookTicketQuery);
					seats -= 1;

					String theater = getTheaterName(movieName, day);

					conn.createStatement();
					String bookReservationQuery = "INSERT INTO reservations (day,movie,theater_name,username) VALUES"
							+ "(" + day + "," + "'" + movieName + "'" + "," + "'" + theater + "'" + "," + "'" + username
							+ "'" + ");";

					System.out.println(bookReservationQuery);
					stmt.execute(bookReservationQuery);

					// this handles the real time booking (Mutual exclusion)
					conn.commit();

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			if(conn!=null){
				try{
					System.out.println("Performing rollback");
					conn.rollback();
				} catch(SQLException e2){
					e2.printStackTrace();
				}
			}
		}
		try {
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// printAllReservations();
		return seats;
	}

	private void printAllReservations() {
		Statement stmt = null;
		String temp = "";
		try {
			ResultSet rs = executeQuery(stmt, "SELECT * FROM reservations");

			while (rs.next()) {
				temp += "BOOK ID: " + rs.getString(1) + "\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}

		System.out.println(temp);
	}
}