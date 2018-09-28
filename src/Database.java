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
	 * @param ClientList
	 * @return
	 */
	public List<Client> getClientTime(ArrayList<Client> ClientList) {
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			
			for(int index = 0; index < ClientList.size(); index++) {
				rs = stmt.executeQuery(Convertion.getClientTime(ClientList.get(index).getUserID()));
				
				if(rs.first()) ClientList.get(index).setUserID(rs.getInt(1));
				else this.createClient(ClientList.get(index));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ClientList;
	}
	
	/**
	 * Update the client time in the Database
	 * @param ClientList
	 */
	public void setClientTime(ArrayList<Client> ClientList) {
		java.sql.Statement stmt = null;
		
		try {
			stmt = conn.createStatement();
			
			for(int index = 0; index < ClientList.size(); index++) {
				stmt.executeUpdate(Convertion.setClientTime(ClientList.get(index).getUserID(), ClientList.get(index).getTime(), ClientList.get(index).getUserName()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
