import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.*;
import java.net.SocketException;

import org.apache.commons.net.telnet.*;

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
		
		//tryhard reading the stream
		while(inReader.ready()) {
			System.out.println(inReader.read());
		}
		
		//login
		String command = "login serveradmin PiW8lxm1\n";
		char[] message = new char[1024];
		command.getChars(0, command.length(), message, 0);
		
		ouWriter.write(message);
		ouWriter.flush();
		
		//the program is faster than the server.
		//through the do while waits the program for a response of the server.
		//although it waits till the world ends, maybe fixing later,
		do {
			
			System.out.println(inReader.read());
			
		} while(inReader.ready());
		
		//closing the socket
		System.out.println("closing socket.");
		soc.close();
	}
}
