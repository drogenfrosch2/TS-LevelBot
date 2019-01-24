import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import static java.util.concurrent.TimeUnit.*;

public class Program {
	
	Database theDatabase;
	Telnet theServer;
	Timercode theTimerCode;
	ScheduledExecutorService theTimer;
	List<Client> ClientList;
	
	public static void main(String[] args) {
		
		new Program();
	}
	
	public Program() {
		Config.loadProperties();
		Log.initialise();
		
		try {
			ClientList = new ArrayList<Client>();
			theDatabase = new Database();
			theServer = new Telnet();
			theTimerCode = new Timercode(this);
			
			theTimer = Executors.newScheduledThreadPool(1);
			theTimer.scheduleAtFixedRate(theTimerCode.tick, 1, Config.TickTime() * 60, SECONDS); //task to call, first delay, delay of the loop
			
			//loops till stop is typed in by the user.
			System.out.println("type 'stop' to stop the server");
			
			
			boolean stop = false;
			String input = null;
			
			do {
				input = System.console().readLine();
				
				if(input != null) {
					switch(input) {
					case "stop":
						stop = true;
						break;
					default:
						System.out.println("unknown command.");
						break;
					}
				}
				
			}while (!stop);
	
			
			System.out.println("shutdown");
			theTimer.shutdown();
			theServer.disconnect();
		
		}
		catch (IOException e) {
			
		}
	}
	
	public void tick() throws IOException {
		System.out.println("begin tick");
		
		
			//Clear the List
			ClientList = null;
			ClientList = new ArrayList<Client>();
			
			//Get the Clients from the Server
			ClientList = theServer.getClientList();
			
			//Get the Times from the Database
			theDatabase.getClientTime(ClientList);
			
			
			//check status, add the Time if online
			for(int index = 0; index < ClientList.size(); index++) {
				
				//check, if the client is in an ignored channel
				for(int x = 0 ; x < Config.IgnoredChannel().length; x++) {
					if(ClientList.get(index).getChannelID() == Config.IgnoredChannel()[x])
						ClientList.get(index).setStatus(EnmStatus.idle);
					
				}
				
				//add time, if he isn't in an ignored channel
				if(ClientList.get(index).getStatus()== EnmStatus.online)
					ClientList.get(index).addTime(Config.TickTime());
			}
			
			//load the levels
			int[][] levelCaps = Config.Level();
			
			for(int index = 0; index < ClientList.size(); index++) {
				//check current level
				int currentlevel = -1;
				
				for(int i = 0; i < levelCaps.length; i++) 
					if(levelCaps[i][0] <= ClientList.get(index).getTime()) currentlevel = levelCaps[i][1];
				
				
				//filter current groups
				int[] usergroups = ClientList.get(index).getServerGroups();
				List<Integer> userGroupList = new ArrayList<Integer>();
				
				for(int x = 0; x < usergroups.length; x++) {
					for(int y = 0; y < levelCaps.length; y++) 
						
						if(usergroups[x] == levelCaps[y][1]) 
							userGroupList.add(levelCaps[y][1]);
				}
				
				//remove groups, if it is more than one and set current one
				if(userGroupList.size() > 1) {
					for(int group : userGroupList) {
						theServer.removeGroup(ClientList.get(index).getUserID(), group);
							
					}
					theServer.addGroup(ClientList.get(index).getUserID(), currentlevel);
				}
				
				//or remove old group and add the new one
				if(userGroupList.size()!=0) {
					if(userGroupList.get(0) != currentlevel) {
						theServer.removeGroup(ClientList.get(index).getUserID(), userGroupList.get(0));
						theServer.addGroup(ClientList.get(index).getUserID(), currentlevel);
					}	
				}//or if there isn't a group set
				else {
					theServer.addGroup(ClientList.get(index).getUserID(), currentlevel);
				}
				
			}
			
			
			
			//update database
			theDatabase.setClientTime(ClientList);
			
			//update scoreboard
			theServer.updateScoreBoard(ClientList);
			System.out.println("finished");
		
		
	}
	
	public void recovery() {
		
		Boolean connected = false;
		
		try {
		theServer = null;
		theServer = new Telnet();
		connected = true;
		}
		catch (IOException e) {
			
		}
		finally 
		{
			if(connected == true) {
				System.out.println("reconnected. resume normal operation");
				theTimerCode.fixed();
			}
			else {
				System.out.println("didn't work.");
			}
		}
	}
	
	
}
