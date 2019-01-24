import java.io.IOException;

public class Timercode{
	
	private Program theProgram;
	private EnmTimerStatus status;
	
	public Timercode(Program program) {
		theProgram = program;
		status = EnmTimerStatus.idle;
	}
	
	public void gotException() {
		status = EnmTimerStatus.broken;
	}
	
	public void fixed() {
		status = EnmTimerStatus.idle;
	}
	
	public Runnable tick = new Runnable() {
		public void run() {
			switch (status) {
			
				case idle:
					status = EnmTimerStatus.running;
					try {
						theProgram.tick();
						status = EnmTimerStatus.idle;
					}
					catch (IOException e) {
						Log.severe("Problem with the Connection: "+ e);
						status = EnmTimerStatus.broken;
						theProgram.recovery();
					}
					break;
					
				case running: 
					System.out.println("tick took to long.");
					Log.warning("tick took to long.");
					break;
					
				case broken:
					theProgram.recovery();
					break;
					
				default: 
					break;
			}
			
		}
	};
}


enum EnmTimerStatus{
	idle, running, broken;
}