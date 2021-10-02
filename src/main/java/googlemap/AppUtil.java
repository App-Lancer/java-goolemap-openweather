package googlemap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class AppUtil {

	//DB user name
	public static final String DB_USER_NAME = "root";
	//DB user password
	public static final String DB_USER_PASSWORD = "root";
	//DB access URL
	public static final String DB_ACCESS_URL = "jdbc:mysql://localhost:3306/googlemap";
	//OpenWeatherAPI Key
	public static final String OPEN_WEATHER_API_KEY = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
	//Paste your Open Weather API key in the above variable
	
	
	/**
	 * To create connection to a DB*/
	public static Connection getDBConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_ACCESS_URL, DB_USER_NAME, DB_USER_PASSWORD);
			return con;
		}catch(Exception ex) {
			System.out.println("Error while connecting to DB " + ex);
		}
		
		return null;
	}
	
	
	/**
	 * To validate login details with the given user name and password given*/
	public static boolean validateLogin(String email, String password) {
		Connection con = getDBConnection();
		
		try {
			Statement st = con.createStatement();
			String sql = "SELECT * FROM user where email=\"" + email +"\"";
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				String dbPassword = rs.getString("password");
				if(dbPassword.equals(password)) {
					return true;
				}
			}
		}catch(Exception ex) {
			System.out.println("Err in validating login " + ex);
			return false;
		}
		
		return false;
	}
	
	
	/**
	 * To fetch users permission from DB*/
	public static String getPermission(String email) {
		try {
			Connection con = getDBConnection();
			
			Statement st = con.createStatement();
			String sql = "SELECT * FROM user where email=\"" + email +"\"";
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				String permission = rs.getString("permissions");
				return permission;
			}
		}catch(Exception ex) {
			System.out.println("Error while fetching permissions : " + ex);
			return null;
		}
		
		return null;
	}
	
	/**
	 * To fetch address data from the DB*/
	public static ArrayList<String> fetchAddress(String email){
		try {
			
			ArrayList<String> addresses = null;
			Connection con = getDBConnection();
			
			Statement st = con.createStatement();
			String sql = "SELECT home_address, office_address FROM user where email=\"" + email +"\"";
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				addresses = new ArrayList<String>();
				
				addresses.add(rs.getString("home_address"));
				addresses.add(rs.getString("office_address"));
			}
			
			return addresses;
			
		}catch(Exception ex) {
			System.out.println("Error while fetching address");
			return null;
		}
	}
	
}
