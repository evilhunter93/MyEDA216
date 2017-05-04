package krustyKookies;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Pallet {
	private int id;
	private int orderId;
	private String cookieType;
	private String location;
	private Timestamp timeStamp;
	private boolean blocked;
	
	public Pallet(int id, String cookieType, String location, Timestamp timeStamp, boolean blocked){
		this.id = id;
		this.cookieType = cookieType;
		this.location = location;
		this.timeStamp = timeStamp;
		this.blocked = blocked;
		orderId = -1;
	}

	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}
	
	public int getOrderID() {
		// TODO Auto-generated method stub
		return orderId;
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
		return timeStamp.toString();
	}
	
	public boolean getBlocked(){
		return blocked;
	}
}
