import java.util.TimerTask;

public class Timercode extends TimerTask{
	
	private Program theProgram;
	
	public Timercode(Program program) {
		theProgram = program;
	}
	
	@Override public void run() {
		theProgram.tick();
	}
}
