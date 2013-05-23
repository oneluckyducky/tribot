package scripts.pro.geektalk.dwk.jobs;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.PathFinding;

import scripts.pro.geektalk.dwk.Job;
import scripts.pro.geektalk.dwk.misc.Const;
import scripts.pro.geektalk.dwk.misc.Methods;
import scripts.pro.geektalk.dwk.misc.Variables;
import scripts.pro.geektalk.dwk.misc.util.GenProperties;

public class BankWalk extends Job {

	GenProperties gen = new GenProperties();

	@Override
	public void run() {

		Methods.s("Walking to bank");
		PathFinding.aStarWalk(Const.BANK_TILE);
	}

	@Override
	public boolean canRun() {
		return Inventory.isFull() && Variables.banking || Variables.banking
				&& !Methods.hasFood()
				&& Methods.getHealth() >= gen.getEatingHp();
	}

}
