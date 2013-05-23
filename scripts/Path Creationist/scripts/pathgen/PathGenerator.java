package scripts.pathgen;

import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;

@ScriptManifest(authors = { "OneLuckyDuck" }, category = "Utilities", name = "Path and Area Maker", description = "Use this RSArea API for the area generator<br/>http://pastebin.com/CwA7W91a")
public class PathGenerator extends Script {

	public static final String DESCRIPTION = "Use this RSArea API for the area generator<br/>http://pastebin.com/CwA7W91a";

	public static boolean run = true;
	public static int loop = 100;
	static Gui gui;
	public static final RSTile[] MY_PATH = new RSTile[] {
			new RSTile(3229, 3370, 0), new RSTile(3226, 3372, 0),
			new RSTile(3220, 3374, 0), new RSTile(3214, 3376, 0),
			new RSTile(3213, 3380, 0), new RSTile(3212, 3385, 0),
			new RSTile(3213, 3389, 0), new RSTile(3219, 3389, 0),
			new RSTile(3224, 3391, 0), new RSTile(3230, 3391, 0),
			new RSTile(3234, 3396, 0), new RSTile(3234, 3400, 0),
			new RSTile(3238, 3400, 0), new RSTile(3241, 3403, 0),
			new RSTile(3241, 3408, 0), new RSTile(3244, 3416, 0),
			new RSTile(3244, 3421, 0), new RSTile(3246, 3426, 0),
			new RSTile(3251, 3428, 0) };

	public int loop() {
		if (!gui.isVisible())
			run = false;
		return loop;
	}

	@Override
	public void run() {
		gui = new Gui();
		sleep(1000);
		while (run) {
			sleep(loop());
		}
	}

}
