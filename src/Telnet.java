import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.*;
import java.net.SocketException;
import java.util.*;


/**
 * does the communication to the TS server over Telnet.
 * 
 * @author drogenfrosch2
 */
public class Telnet 
{
	
	private Socket soc = new Socket();
	private InputStreamReader inReader;
	private OutputStreamWriter ouWriter;
	
	/**
	 * initializes the class and connects to the server as in the config file.
	 * @throws IOException
	 */
	public Telnet() throws IOException
	{
		this.connect();
	}
	
	/** 
	 * connect to the server and login
	 * @throws IOException 
	 */
	private void connect() throws IOException {
		//connecting and getting the streams
		soc.connect(new InetSocketAddress(Config.TSHostIP(), Config.TSPort()));
		inReader = new InputStreamReader(soc.getInputStream());
		ouWriter = new OutputStreamWriter(soc.getOutputStream());
			
		this.read();
		this.send(Convertion.login());
		this.read();
		this.send(Convertion.useServer());
		this.read();
		this.send(Convertion.renameQuery());
		this.read();
	}
	
	/**
	 * gets the clients from the server
	 * @return
	 * @throws IOException 
	 */
	public List<Client> getClientList() throws IOException{
		
		this.send(Convertion.getClientList());
		
		try {
			return Convertion.convertClientList(this.read());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void updateScoreBoard(List<Client> clientList) throws IOException{
		
		String scores = Convertion.makeScoreboardText(clientList);
		this.send(Convertion.editChannelText(scores, Config.ScoreboardID()));
	}
	
	/**
	 * removes the groups
	 * @throws IOException 
	 */
	public void removeGroup(int UserID, int GroupID) throws IOException {
		this.send(Convertion.removeGroup(UserID, GroupID));
	}
	
	/**
	 * sets the group
	 * @param UserID
	 * @param GroupID
	 * @throws IOException 
	 */
	public void addGroup(int UserID, int GroupID) throws IOException {
		this.send(Convertion.setGroup(UserID, GroupID));
	}
	
	/**
	 * reads the answer from the server and returns it as string.
	 * @return
	 * @throws IOException
	 */
	private String read() throws IOException {
		
		try {
			Thread.sleep(500);	//currently the program is to fast, maybe later a timeout function gets added
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int index = 0;
		int[] intmessage = new int[16384];
		
		
		//the program is faster than the server.
		//through the do while waits the program for a response of the server.
		//although it waits till the world ends, maybe fixing later,
		do {
			
			intmessage[index] = inReader.read();
			index++;
			
		} while(inReader.ready());
		
		//stitching back together the answer of the server
		String message = Character.toString((char)intmessage[0]);
		
		for(int x = 1; x < intmessage.length; x++) {
		message = message + Character.toString((char)intmessage[x]);
		}
		
		return message;
	}
	
	/**
	 * sends the command to the Teamspeak server.
	 * @param command
	 * @throws IOException 
	 */
	private void send(String command) throws IOException {
		//encoding the string to characters for transmission
		char[] message = new char[1024];
		command.getChars(0, command.length(), message, 0);
		
		//sending message
		
		ouWriter.write(message);
		ouWriter.flush();
		
	}
	
	
	/**
	 * closes the connection.
	 */
	public void disconnect() {
		try {
			this.send(Convertion.editChannelText("[B]currently\\soffline[/B]\\n", Config.ScoreboardID()));
			this.send(Convertion.logout());
			soc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
