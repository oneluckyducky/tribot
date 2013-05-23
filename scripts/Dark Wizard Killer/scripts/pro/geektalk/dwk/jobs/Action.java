package scripts.pro.geektalk.dwk.jobs;

import org.tribot.api.types.generic.FilterGroup;
import org.tribot.api2007.Camera;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSNPC;

import scripts.pro.geektalk.dwk.Job;
import scripts.pro.geektalk.dwk.misc.Const;
import scripts.pro.geektalk.dwk.misc.Methods;
import scripts.pro.geektalk.dwk.misc.Variables;
import scripts.pro.geektalk.dwk.misc.util.GenProperties;
import scripts.pro.geektalk.dwk.misc.util.Timer;

public class Action extends Job {

	GenProperties gen = new GenProperties();

	@Override
	public void run() {
		if (Methods.checkLoot())
			return;

		final FilterGroup<RSNPC> filterGroup = NPCs
				.generateFilterGroup(Variables.npcFilter);
		final RSNPC[] npcs = NPCs.sortByDistance(Player.getPosition(),
				NPCs.findNearest(filterGroup));
		if (npcs.length > 0) {
			final RSNPC npc = npcs[0];
			if (npc != null) {
				if (Player.getPosition().distanceTo(npc.getPosition()) > 6) {
					Walking.walkPath(Walking.generateStraightPath(npc
							.getPosition()));
				}
				if (npc.isOnScreen()) {
					Methods.s("Attacking " + npc.getID());
					npc.click("Attack", npc.getName());
					final Timer timer = new Timer(1000);
					while (timer.isRunning()) {
						sleep(100);
						if (!timer.isRunning())
							break;
					}
					Variables.kills++;

				} else {
					Methods.s("Turning to " + npc.getID());
					Camera.turnToTile(npc.getPosition());
				}
			}
		}

	}

	@Override
	public boolean canRun() {
		return Methods.isIdle() && Methods.hasFood()
				&& Const.CIRCLE_AREA.contains(Player.getRSPlayer())
				|| Methods.isIdle() && !Methods.hasFood()
				&& Methods.getHealth() >= gen.getEatingHp()
				&& Const.CIRCLE_AREA.contains(Player.getRSPlayer());
	}
}
