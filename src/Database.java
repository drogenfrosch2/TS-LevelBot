import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

/**
 * Connects to the MySQL-Server
 * 
 * @author timob
 *
 */
public class Database {
	
	Connection conn;
	
	public Database(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void connect() {
		
		try {
			//get connection string, connect
			
			conn = DriverManager.getConnection(Convertion.connectionString());
			
		} catch (Exception e){
			
			System.out.println(e);
			
		}
		
		java.sql.Statement stmt = null;	//the sql command
		ResultSet rs = null;			//handles the data from the database

		try {
		    stmt = conn.createStatement();
		    
		    rs = stmt.executeQuery(Convertion.getDatabases());
		    
		    //check if the database is there
		    if(rs.first()) {
		    	//yes, then connect
		    	stmt.execute(Convertion.useDatabase());
		    }
		    else {
		    	//if not make a new one
		    	System.out.println("make new database");
		    	stmt.execute(Convertion.createDatabase());
		    	stmt.execute(Convertion.useDatabase());
		    	stmt.execute(Convertion.createTable());
		    }
		    
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	public void disconnect() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * get the Time of the Clients in the List
	 * @param clientList
	 * @return
	 */
	public List<Client> getClientTime(List<Client> clientList) {
		this.connect();
		
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			
			for(int index = 0; index < clientList.size(); index++) {
				rs = stmt.executeQuery(Convertion.getClientTime(clientList.get(index).getUserID()));
				
				if(rs.first()) clientList.get(index).setTime(rs.getInt(1));
				else this.createClient(clientList.get(index));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		this.disconnect();
		return clientList;
	}
	
	/**
	 * Update the client time in the Database
	 * @param clientList
	 */
	public void setClientTime(List<Client> clientList) {
		this.connect();
		
		java.sql.Statement stmt = null;
		
		try {
			stmt = conn.createStatement();
			
			for(int index = 0; index < clientList.size(); index++) {
				stmt.executeUpdate(Convertion.setClientTime(clientList.get(index).getUserID(), clientList.get(index).getTime(), clientList.get(index).getUserName()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.disconnect();
	}
	
	/**
	 * make a new Client
	 * @param newClient
	 */
	private void createClient(Client newClient) {
		java.sql.Statement stmt = null;
		
		try {
			stmt = conn.createStatement();
			stmt.execute(Convertion.createClient(newClient.getUserID(), newClient.getTime(), newClient.getUserName()));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
