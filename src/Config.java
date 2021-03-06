import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
/**
 * handles the config file and gives every other class access to the data.
 * 
 * @author timob
 *
 */
public class Config {
	
	private static String file = "./config.properties";
	
	private static int tickTime;
	private static int scoreboardID;
	private static int[] ignoredChannel;
	private static int[][] timeCaps;
	
	private static String tsHostIP;
	private static String tsPort;
	private static String tsServerID;
	private static String tsUserName;
	private static String tsPassword;
	private static String tsNickName;
	
	private static String dbHostIP;
	private static String dbPort;
	private static String dbUserName;
	private static String dbPassword;
	private static String dbName;
	
	/**
	 * loads the data. has to be run before any data is accessed.
	 * 
	 * if the config file doesn't exist, it creates a new one.
	 */
	public static void loadProperties()
	{
		try {
			File configFile = new File(file);
		 
			FileReader reader = new FileReader(configFile);
		 
			Properties props = new Properties();
		 
			// load the properties file:
			props.load(reader);
			reader.close();
			
			tickTime = Integer.parseInt(props.getProperty("tickTime", "2"));
			scoreboardID = Integer.parseInt(props.getProperty("scoreboardID", "3"));

			
			//get the ignored channel
			String raw = props.getProperty("ignoredChannel");
			String[] numbers = raw.split("\\,");
			ignoredChannel = new int[numbers.length];
			for(int x = 0; x < numbers.length; x++) 
				ignoredChannel[x] = Integer.parseInt(numbers[x]);
			
			raw = null;
			
			//get the groups
			raw = props.getProperty("timeCaps", "");
			String[] pairs = raw.split("\\|");
			
			timeCaps = new int[pairs.length][2];
			
			for(int x = 0; x < pairs.length; x++) {
				String[] single = pairs[x].split(",");
				timeCaps[x][0] = Integer.parseInt(single[0]);	//the first dimension is the pair, the second is time for 0 and group id for 1
				timeCaps[x][1] = Integer.parseInt(single[1]);
			}
			
			
			tsHostIP = props.getProperty("tsHostIP", "127.0.0.1");
			tsPort = props.getProperty("tsPort", "10011");
			tsServerID = props.getProperty("tsServerID", "1");
			tsUserName = props.getProperty("tsUserName");
			tsPassword = props.getProperty("tsPassword");
			tsNickName = props.getProperty("tsNickName", "TSLevelBot");
			
			dbHostIP = props.getProperty("dbHostIP", "127.0.0.1");
			dbPort = props.getProperty("dbPort", "3306");
			dbUserName = props.getProperty("dbUserName");
			dbPassword = props.getProperty("dbPassword");
			dbName = props.getProperty("dbName", "TSLevelBot");
		}
		catch (IOException e){
			System.out.println(e);
			System.out.println("create new config.");
			createProperties();
		}
	}
	
	/**
	 * creates a new config file.
	 */
	private static void createProperties() {
		try {
			File configFile = new File(file);
			FileWriter writer = new FileWriter(configFile);
			Properties props = new Properties();
			String input;
			
			input = System.console().readLine("Delay of ticks in minutes: ");
			props.setProperty("tickTime", input);
			input = System.console().readLine("The ID of the scoreboard channel: ");
			props.setProperty("scoreboardID", input);
			//
			props.setProperty("ignoredChannel", "");
			/*Boolean stop = false;
			String channels = "";
			do {
				input = System.console().readLine("The ID of the scoreboard channel: ");
				channels = channels + input + ",";
			}while(!stop);*/
			props.setProperty("timeCaps", "");
			
			input = System.console().readLine("The IP of the TS Server: ");
			props.setProperty("tsHostIP", input);
			input = System.console().readLine("Telnet Port (default is 10011): ");
			props.setProperty("tsPort", input);
			//
			props.setProperty("tsServerID", "1");
			input = System.console().readLine("Serverquery name for the telnet login: ");
			props.setProperty("tsUserName", input);
			input = System.console().readLine("Serverquery password for the telnet login: ");
			props.setProperty("tsPassword", input);
			input = System.console().readLine("Name for the query on the server: ");
			props.setProperty("tsNickName", input);
			
			input = System.console().readLine("The IP of the MySQL Database: ");
			props.setProperty("dbHostIP", input);
			input = System.console().readLine("The Port of the MySQL Database: ");
			props.setProperty("dbPort", input);
			input = System.console().readLine("User Name for the MySQL login: ");
			props.setProperty("dbUserName", input);
			input = System.console().readLine("Password for the MySQL login: ");
			props.setProperty("dbPassword", input);
			props.setProperty("dbName", "TSLevelBot");
			
			props.store(writer, "host settings");
			writer.close();
		}
		catch (IOException e){
			System.out.println(e);
		}
	}
	
	public static String TSHostIP() {
		return tsHostIP;
	}
	
	public static int TSPort() {
		return Integer.parseInt(tsPort);
	}
	
	public static String TSServerID() {
		return tsServerID;
	}
	
	public static String TSUserName() {
		return tsUserName;
	}
	
	public static String TSPassword() {
		return tsPassword;
	}
	
	public static String TSNickName() {
		return tsNickName;
	}
	
	public static String DBHostIP() {
		return dbHostIP;
	}
	
	public static String DBPort() {
		return dbPort;
	}
	
	public static String DBUserName() {
		return dbUserName;
	}
	
	public static String DBPassword() {
		return dbPassword;
	}
	
	public static String DBName() {
		return dbName;
	}
	
	public static int TickTime() {
		return tickTime;
	}
	
	public static int ScoreboardID() {
		return scoreboardID;
	}
	
	public static int[][] Level(){
		return timeCaps;
	}
	
	public static int[] IgnoredChannel() {
		return ignoredChannel;
	}
}
