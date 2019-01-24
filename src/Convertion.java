import java.util.ArrayList;
import java.util.List;

/**
 * Converts the answers from the Teamspeak server and the MySQL server to the Client datatype and the other way around.
 * 
 * @author timob
 *
 */
public class Convertion {

	//all Telnet commands with input from the config file
	public static String login() {
		
		return "login " + Config.TSUserName() + " " + Config.TSPassword() + "\n";
	}
	
	public static String logout() {
		return "logout \n";
	}
	
	public static String useServer() {
		return "use " + Config.TSServerID() + "\n";
	}
	
	public static String renameQuery() {
		return "clientupdate client_nickname=" + Config.TSNickName() + "\n";
	}
	
	public static String getClientList() {
		return "clientlist -away -times -groups\n";
	}
	
	public static String setGroup(int userID, int groupID) {
		return "servergroupaddclient sgid=" + groupID + " cldbid=" + userID + "\n";
	}
	
	public static String removeGroup(int userID, int groupID) {
		return "servergroupdelclient sgid=" + groupID + " cldbid=" + userID + "\n";
	}
	
	public static String editChannelText(String text, int channelID) {
		return "channeledit cid=" + channelID + " channel_description=" + text + "\n";
	}
	
	public static String makeScoreboardText(List<Client> clientList) {
		String scores = "[B]The\\stime\\sin\\shours:[/B]\\n";
		
		for(int x = 0; x < clientList.size(); x++) {
			scores= scores + String.format("%1$s\\s%2$d:%3$02d\\n", clientList.get(x).getUserName(), clientList.get(x).getTime() / 60, clientList.get(x).getTime() % 60);
			
		}
		
		return scores;
	}
	
	public static List<Client> convertClientList(String clientList) {
		Log.info(clientList);
		// split the clients apart 
		// without the double backslash the code does not cut at | 
		String[] clients = clientList.split("\\|");
				
		ArrayList<Client> newClients = new ArrayList<Client>();
		
		// for each client
		for(int x = 0; x < clients.length; x++) {
			// split the parameters		
			String[] parameters = clients[x].split(" ");
			
			Boolean isQuery = false;
			
			Client newClient = new Client();
			//for each parameter		
			for(int y = 0; y < parameters.length; y++) {
				//split parameter name and value		
				String[] parts = parameters[y].split("=");
				//check which parameter and act accordingly			
				switch(parts[0]) {
					case "cid":
						newClient.setChannelID(Integer.parseInt(parts[1]));
						break;
					case "client_database_id":
						newClient.setUserID(Integer.parseInt(parts[1]));
						break;
					case "client_nickname":
						newClient.setUserName(parts[1]);
						break;
					case "client_away":
						//have to see if this is even used	
						break;
					case "client_type":
						//if the client is actually a query, ignores the user
						if(Integer.parseInt(parts[1]) == 1) {	//has to convert to int because otherwise the program doesn't do shit
							y = parameters.length + 1;	//breaks the loop
							isQuery = true;
						}
						break;
					case "client_servergroups":
						String[] groups = parts[1].split(",");	//split the groups
						int igroups[] = new int[groups.length];
									
						for(int a = 0; a < groups.length; a++) igroups[a] = Integer.parseInt(groups[a]);	//getting the groups into the int array
									
						newClient.setServerGroups(igroups);	//give newClient the array
						break;
					case "client_idle_time":
						//have to see if this is even used				
						break;
					default:
						break;
				}
			}
			if(!isQuery) {
				newClients.add(newClient);	//only adds the client, if it isn't a query
			}
		}
		return newClients;
	}
	
	//all MySQL commands
	public static String connectionString() {
		return "jdbc:mysql://" + Config.DBHostIP() + ":" + Config.DBPort()+"?user=" + Config.DBUserName() + "&password=" + Config.DBPassword()+"&useSSL=true";
	}
	
	public static String useDatabase() {
		return "Use "+Config.DBName();
	}
	
	public static String getDatabases() {
		return "SHOW Databases LIKE '" + Config.DBName() + "'";
	}
	
	public static String createDatabase() {
		return "CREATE DATABASE " + Config.DBName();
	}
	
	public static String createTable() {
		return "CREATE TABLE `Clients` ( `ClientID` INT(32) NOT NULL , `ClientTime` INT(32) NOT NULL , `ClientName` VARCHAR(256) NULL DEFAULT NULL , PRIMARY KEY (`ClientID`)) ENGINE = InnoDB";
	}
	
	public static String getClientTime(int ClientID) {
		return "SELECT ClientTime FROM Clients WHERE ClientID = " + ClientID + ";";
	}
	
	public static String setClientTime(int ClientID, int ClientTime, String ClientName) {
		//return "UPDATE `Clients` SET `ClientTime`="+ClientTime+",`ClientName`='"+ClientName+"' WHERE ClientID = "+ClientID+";";
		return "UPDATE `Clients` SET `ClientTime`="+ClientTime+" WHERE ClientID = "+ClientID+";";
	}
	
	public static String createClient(int ClientID, int ClientTime, String ClientName) {
		return "INSERT INTO Clients (ClientID, ClientTime, ClientName) VALUES ("+ClientID+", "+ClientTime+", '"+ClientName+"');";
	}
}
