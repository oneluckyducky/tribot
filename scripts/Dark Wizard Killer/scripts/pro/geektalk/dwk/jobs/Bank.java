package scripts.pro.geektalk.dwk.jobs;

import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSTile;

import scripts.pro.geektalk.dwk.Job;
import scripts.pro.geektalk.dwk.misc.Const;
import scripts.pro.geektalk.dwk.misc.Methods;
import scripts.pro.geektalk.dwk.misc.Variables;
import scripts.pro.geektalk.dwk.misc.util.GenProperties;
import scripts.pro.geektalk.dwk.misc.util.Timer;

public class Bank extends Job {

	GenProperties gen = new GenProperties();

	@Override
	public void run() {
		if (Banking.openBankBooth()) {
			final Timer timer = new Timer(2000);
			while (!Banking.isBankScreenOpen() || !Banking.isPinScreenOpen()) {
				sleep(100);
				if (!timer.isRunning())
					break;
			}
			if (Banking.isBankScreenOpen()) {
				Banking.depositAll();
				if (Inventory.getAll().length == 0) {
					Methods.withdraw(gen.getFoodQuantity(),
							new int[] { Variables.food.getId() });

					sleep(2000);
					if (Methods.hasFood()) {
						Walking.clickTileMM(new RSTile(3245, 3415, 0), 0);
					}
				}
			} else if (Banking.isPinScreenOpen()) {
				Banking.inPin();
			}
		}

	}

	@Override
	public boolean canRun() {
		return Variables.banking
				&& Const.BANK_TILE.distanceTo(Player.getPosition()) <= 5
				&& Inventory.isFull() || Variables.banking
				&& Const.BANK_TILE.distanceTo(Player.getPosition()) <= 5
				&& !Methods.hasFood()
				&& Methods.getHealth() >= gen.getEatingHp();
	}

}
