package krustyKookies;

import java.sql.Date;
import java.sql.Time;

public class Pallet {
	private int id;
	private String cookieType;
	private String location;
	private Date date;
	
	public Pallet(int id, String cookieType, String location, Date date){
		this.id = id;
		this.cookieType = cookieType;
		this.location = location;
		this.date = date;
	}

	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}
	public String getCookieType() {
		// TODO Auto-generated method stub
		return cookieType;
	}
	public String getLocation() {
		// TODO Auto-generated method stub
		return location;
	}
	public String getDate() {
		// TODO Auto-generated method stub
		return date.toString();
	}
}
