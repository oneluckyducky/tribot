package scripts.pro.geektalk.dwk.jobs;

import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;

import scripts.pro.geektalk.dwk.Job;
import scripts.pro.geektalk.dwk.misc.Methods;
import scripts.pro.geektalk.dwk.misc.Variables;
import scripts.pro.geektalk.dwk.misc.util.GenProperties;

public class Eating extends Job {

	GenProperties gen = new GenProperties();

	@Override
	public void run() {
		Methods.s("Can run Eating");
		final RSItem[] food = Inventory.find(Variables.food.getId());
		if (GameTab.getOpen() != GameTab.TABS.INVENTORY) {
			GameTab.open(TABS.INVENTORY);
		}
		if (food.length > 0) {
			Methods.s("Eating " + food[0].getID());
			food[0].click("Eat");
			sleep(1000);
		}

	}

	@Override
	public boolean canRun() {
	//	Methods.s("Eating test");
		return Methods.hasFood() && Methods.getHealth() <= gen.getEatingHp();
	}

}
