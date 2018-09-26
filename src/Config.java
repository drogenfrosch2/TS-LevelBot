import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Config {
	
	private static String file = "config.properties";
	
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
	
	/*
	maybe added later or stored in the database
	
	TickDauer 1
    ServerGroupAndTime 59:10,60:60,61:360,62:1440,63:10080,64:43200
    UnratedChannel 25,26,27,28,169
	ScoreboardChannel 179
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
	
	private static void createProperties() {
		try {
			File configFile = new File(file);
			FileWriter writer = new FileWriter(configFile);
			Properties props = new Properties();
			
			props.setProperty("tsHostIP", "127.0.0.1");
			props.setProperty("tsPort", "10011");
			props.setProperty("tsServerID", "1");
			props.setProperty("tsUserName", "");
			props.setProperty("tsPassword", "");
			props.setProperty("tsNickName", "TSLevelBot");
			
			props.setProperty("dbHostIP", "127.0.0.1");
			props.setProperty("dbPort", "3306");
			props.setProperty("dbUserName", "");
			props.setProperty("dbPassword", "");
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
}
