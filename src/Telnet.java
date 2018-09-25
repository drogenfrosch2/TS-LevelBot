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
	
	public Telnet() throws IOException
	{
		//connecting and getting the streams
		soc.connect(new InetSocketAddress("127.0.0.1", 10011));
		inReader = new InputStreamReader(soc.getInputStream());
		ouWriter = new OutputStreamWriter(soc.getOutputStream());
		
		System.out.println(read());
		disconnect();
	}
	
	
	
	private Client[] StringToClient(String data) {
		
		// without the double backslash the code does not cut at | 
		String[] clients = data.split("\\|");
		
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
								
						break;
					case "client_servergroups":
						String[] groups = parts[1].split(",");	//split the groups
						int igroups[] = new int[groups.length];
							
						for(int a = 0; a < groups.length; a++) igroups[a] = Integer.parseInt(groups[a]);	//getting the groups into the int array
							
						newClient.setServerGroups(igroups);	//give newClient the array
						break;
					case "client_idle_time":
								
						break;
					default:
						break;
				}
			}
			newClients[x] = newClient;
		}
		return newClients;
	}
	
	
	
	
	//raw telnet connection stuff
	//the separation between the user and the raw data to the server is achieved through the private classes in Telnet.
	
	private String read() throws IOException {
		
		int index = 0;
		int[] intmessage = new int[1024];
		
		
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
	
	private void send(String command) {
		
		//encoding the string to characters for transmission
		char[] message = new char[1024];
		command.getChars(0, command.length(), message, 0);
		
		//sending message
		try {
			ouWriter.write(message);
			ouWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void disconnect() {
		try {
			soc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
