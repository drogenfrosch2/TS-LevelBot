
public class Convertion {

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
		// without the double backslash the code does not cut at | 
		String[] clients = clientList.split("\\|");
				
		Client[] newClients = new Client[clients.length];
				
		for(int x = 0; x < clients.length; x++) {
						
			String[] parameters = clients[x].split(" ");
						
			Client newClient = new Client();
					
			for(int y = 0; y < parameters.length; y++) {
						
				String[] parts = parameters[y].split("=");
							
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
}
