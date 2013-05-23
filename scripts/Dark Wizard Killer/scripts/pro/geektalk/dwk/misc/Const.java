package scripts.pro.geektalk.dwk.misc;

import org.tribot.api2007.types.RSTile;

import scripts.pro.geektalk.dwk.misc.util.Area;
import scripts.pro.geektalk.dwk.misc.util.Timer;

public class Const {

	public static final Timer TIMER = new Timer(0);

	public static final String[] SKILL_NAMES = { "Attack", "Defence",
			"Strength", "Hitpoints", "Range", "Prayer", "Magic" };

	public static final RSTile BANK_TILE = new RSTile(3253, 3421, 0);
	public static final RSTile CIRCLE_TILE = new RSTile(3225, 3370, 0);

	public static final Area CIRCLE_AREA = new Area(new RSTile[] {
			new RSTile(3226, 3379, 0), new RSTile(3231, 3377, 0),
			new RSTile(3235, 3374, 0), new RSTile(3237, 3369, 0),
			new RSTile(3234, 3365, 0), new RSTile(3232, 3360, 0),
			new RSTile(3227, 3359, 0), new RSTile(3222, 3360, 0),
			new RSTile(3220, 3365, 0), new RSTile(3218, 3370, 0),
			new RSTile(3218, 3375, 0), new RSTile(3223, 3377, 0) });

}
