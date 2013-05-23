package scripts.pro.geektalk.dwk.misc;

import java.util.ArrayList;

import org.tribot.api.types.generic.Filter;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSNPC;

import scripts.pro.geektalk.dwk.misc.enums.Food;

public class Variables {

	public static String status = "";

	public static Food food = Food.TROUT;

	public static long startTime = 0, kills;

	public static boolean banking = false;

	public static ArrayList<Integer> lootList = new ArrayList<Integer>();

	public static ArrayList<Integer> levels = new ArrayList<Integer>();

	public static Filter<RSNPC> npcFilter = new Filter<RSNPC>() {
		@Override
		public boolean accept(RSNPC n) {
			return n.getName().contains("ark wiz") && !n.isInCombat()
					&& Const.CIRCLE_AREA.contains(n)
					&& levels.contains(n.getID());
		}
	};

	public static Filter<RSGroundItem> itemFilter = new Filter<RSGroundItem>() {
		@Override
		public boolean accept(RSGroundItem g) {
			return lootList.contains(g.getID());
		}
	};

}
