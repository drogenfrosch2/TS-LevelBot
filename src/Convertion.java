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
	
	public static Client[] convertClientList(String clientList) {
		// split the clients apart 
		// without the double backslash the code does not cut at | 
		String[] clients = clientList.split("\\|");
				
		Client[] newClients = new Client[clients.length];
		
		// for each client
		for(int x = 0; x < clients.length; x++) {
			// split the parameters		
			String[] parameters = clients[x].split(" ");
						
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
			newClients[x] = newClient;
		}
		return newClients;
	}
	
	//all MySQL commands
	public static String connectionString() {
		return "jdbc:mysql://" + Config.DBHostIP() + ":" + Config.DBPort() +"?user=" + Config.DBUserName() + "&password=" + Config.DBPassword();
	}
	
	public static String useDatabase() {
		return "Use "+Config.DBName();
	}
	
	public static String getDatabases() {
		return "SHOW Databases LIKE '" + Config.DBName() + "'";
	}
	
	public static String createDatabase() {
		return "CREATE DATABASE "+Config.DBName();
	}
	
	public static String createTable() {
		return "CREATE TABLE `Clients` ( `ClientID` INT(32) NOT NULL , `ClientTime` INT(32) NOT NULL , `ClientName` VARCHAR(256) NULL DEFAULT NULL , PRIMARY KEY (`ClientID`)) ENGINE = InnoDB";
	}
	
	public static String getClientTime(int ClientID) {
		return "SELECT ClientTime FROM Clients WHERE ClientID = " + ClientID + ";";
	}
	
	public static String setClientTime(int ClientID, int ClientTime, String ClientName) {
		return "UPDATE `clients` SET `ClientTime`="+ClientTime+",`ClientName`='"+ClientName+"' WHERE ClientID = "+ClientID+";";
	}
	
	public static String createClient(int ClientID, int ClientTime, String ClientName) {
		return "INSERT INTO Clients (ClientID, ClientTime, ClientName) VALUES ("+ClientID+", "+ClientTime+", '"+ClientName+"');";
	}
}
