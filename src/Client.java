/**
 * contains the needed information for the clients.
 * 
 * @author timob
 *
 */
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
    
    public Client(int ID, int Time, String Name) {
    	
    	userID = ID;
    	time = Time;
    	userName = Name;
    	
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
    
    public int getTime() { System.out.println(time); return time; }
    public void setTime(int Time) { time = Time; }
    public void addTime(int Time) { time = time + Time; }
    
    public void setStatus(EnmStatus Status) { status = Status; }
    public EnmStatus getStatus() { return status; }
    
}

enum EnmStatus{
	online, idle, offline;
}