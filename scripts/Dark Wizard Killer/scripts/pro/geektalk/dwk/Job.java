package scripts.pro.geektalk.dwk;

public abstract class Job {

	public abstract void run();

	public abstract boolean canRun();

	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
