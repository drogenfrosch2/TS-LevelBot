import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
	
		private static Logger logger = Logger.getLogger("MyLog");  
	    private static FileHandler fh;  
	    
	    public static void initialise() {
	    try {  

	        // This block configure the logger with handler and formatter  
	        fh = new FileHandler("./MyLogFile.log");  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);
	        
	        logger.setUseParentHandlers(false);
	    } 
	    catch (SecurityException e) {  
	        e.printStackTrace();  
	    	} 
	    catch (IOException ex) {  
	        ex.printStackTrace();  
	        }
	    }  
	    
	    public static void info(String text) {
	    	logger.info(text);
	    }
	    
	    public static void severe(String text) {
	    	logger.severe(text);
	    }
	    
	    public static void fine(String text) {
	    	logger.fine(text);
	    }
	    
	    public static void warning(String text) {
	    	logger.warning(text);
	    }
	}

