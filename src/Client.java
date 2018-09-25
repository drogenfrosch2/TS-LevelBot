
public class Client {
	
	private int userID;
    private int channelID;
    private String userName;
    private int serverGroup;
    private int time;
    private EnmStatus status;
    
    public Client(int UserID, int ChannelID, String UserName, int ServerGroup, int Time) {
    	
    	userID = UserID;
    	channelID = ChannelID;
    	userName = UserName;
    	serverGroup = ServerGroup;
    	time = Time;
    	status = EnmStatus.online;
    	
    }
    
    public int getUserID() { return userID; }
    
    public int getChannelID() { return channelID; }
    
    public String getUserName() { return userName; }
    
    public int getServerGroup() { return serverGroup; }
    
    public int getTime() { return time; }
    
    public EnmStatus getStatus() { return status; }
    
}

enum EnmStatus{
	online, idle, offline;
}