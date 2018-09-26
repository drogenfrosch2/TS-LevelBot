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
		soc.connect(new InetSocketAddress(Config.TSHostIP(), Config.TSPort()));
		inReader = new InputStreamReader(soc.getInputStream());
		ouWriter = new OutputStreamWriter(soc.getOutputStream());
		
		disconnect();
	}
	
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
