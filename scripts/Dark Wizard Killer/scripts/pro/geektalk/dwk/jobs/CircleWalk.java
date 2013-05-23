package scripts.pro.geektalk.dwk.jobs;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;

import scripts.pro.geektalk.dwk.Job;
import scripts.pro.geektalk.dwk.misc.Const;
import scripts.pro.geektalk.dwk.misc.Methods;
import scripts.pro.geektalk.dwk.misc.util.GenProperties;

public class CircleWalk extends Job {

	GenProperties gen = new GenProperties();

	@Override
	public void run() {
		Methods.s("Walking to wizards");
		PathFinding.aStarWalk(Const.CIRCLE_TILE);
	}

	@Override
	public boolean canRun() {
		return Methods.hasFood() && !Inventory.isFull()
				&& !Const.CIRCLE_AREA.contains(Player.getRSPlayer());
	}

}
