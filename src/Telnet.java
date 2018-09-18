import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.telnet.*;

/**
 * does the communication to the TS server over Telnet.
 * 
 * @author drogenfrosch2
 */
public class Telnet {
	
	private TelnetClient telClient = new TelnetClient();
	private InputStream inStream = telClient.getInputStream();
	private OutputStream ouStream = telClient.getOutputStream();
	
	private String aip, auser, apassword;
	private int aport;
	
	public Telnet(String ip, int port, String user, String password) throws SocketException, IOException
	{
		aip = ip;
		auser = user;
		apassword = password;
		aport = port;
		
		telClient.connect(aip, aport);
		telClient.disconnect();
	}
}
