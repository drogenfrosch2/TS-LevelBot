import java.io.IOException;
import java.net.SocketException;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Telnet test = new Telnet(/*"127.0.0.1", 10011, "", ""*/);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
