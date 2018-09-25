
public class Client {
	
	private int userID;
    private int channelID;
    private String userName;
    private int[] serverGroups;
    private int time;
    private EnmStatus status;
    
    public Client() {
    	
    	status = EnmStatus.online;
    	
    }
    
    public int getUserID() { return userID; }
    public void setUserID(int UserID) { userID = UserID; }
    
    public int getChannelID() { return channelID; }
    public void setChannelID(int ChannelID) {channelID = ChannelID; }
    
    public String getUserName() { return userName; }
    public void setUserName(String UserName) { userName = UserName; }
    
    public int[] getServerGroups() { return serverGroups; }
    public void setServerGroups(int[] ServerGroups) { serverGroups = ServerGroups; }
    
    public int getTime() { return time; }
    public void setTime(int Time) { time = Time; }
    
    public EnmStatus getStatus() { return status; }
    
}

enum EnmStatus{
	online, idle, offline;
}