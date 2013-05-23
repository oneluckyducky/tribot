package scripts.pro.geektalk.dwk.misc;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.FilterGroup;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Camera;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItem;

import scripts.pro.geektalk.dwk.DarkWizardKiller;
import scripts.pro.geektalk.dwk.Job;
import scripts.pro.geektalk.dwk.misc.util.Area;
import scripts.pro.geektalk.dwk.misc.util.GenProperties;
import scripts.pro.geektalk.dwk.misc.util.Timer;

public class Methods {
	static GenProperties gen = new GenProperties();

	public static boolean checkLoot() {
		final FilterGroup<RSGroundItem> filterGroup = GroundItems
				.generateFilterGroup(Variables.itemFilter);
		final RSGroundItem[] items = GroundItems.sortByDistance(
				Player.getPosition(), GroundItems.findNearest(filterGroup));
		if (items.length < 1)
			return false;
		final RSGroundItem g = items[0];
		if (g != null) {
			if (PathFinding.canReach(g.getPosition(), false)) {
				if (Player.getPosition().distanceTo(g.getPosition()) > 6) {
					Walking.walkTo(g.getPosition());
				}
				if (!g.isOnScreen()) {
					s("Turning to " + g.getID());
					Camera.turnToTile(g.getPosition());
				} else {
					if (!Inventory.isFull()) {
						g.click("Take");
						final Timer timer = new Timer(500);
						while (g != null && g.isOnScreen()) {
							Job.sleep(100);
							if (Player.isMoving())
								timer.reset();
							if (!timer.isRunning() || !g.isOnScreen()
									|| g == null)
								break;
						}
					} else if (Inventory.isFull()
							&& Inventory.find(g.getID()) != null) {
						g.click("Take");
						final Timer timer = new Timer(500);
						while (g != null && g.isOnScreen()) {
							Job.sleep(100);
							if (Player.isMoving())
								timer.reset();
							if (!timer.isRunning() || !g.isOnScreen()
									|| g == null)
								break;
						}

					} else if (Inventory.isFull()
							&& Inventory.find(g.getID()) == null) {
						s("Eating for space");
						Inventory.find(Variables.food.getId())[0].click("Eat");
						Job.sleep(1000);

						g.click("Take");
						final Timer timer = new Timer(500);
						while (g != null && g.isOnScreen()) {
							Job.sleep(100);
							if (Player.isMoving())
								timer.reset();
							if (!timer.isRunning() || !g.isOnScreen()
									|| g == null)
								break;
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	public static void s(Object o) {
		new DarkWizardKiller().log(o);
		Variables.status = o.toString();
	}

	public static boolean isInArea(final Area area, final RSCharacter rs) {
		return area.contains(rs);
	}

	public static boolean isIdle() {
		return Player.getRSPlayer() != null && !Player.isMoving()
				&& Player.getRSPlayer().getAnimation() == -1
				&& Player.getRSPlayer().getInteractingCharacter() == null;
	}

	public static int getHealth() {
		return Skills.getCurrentLevel("Hitpoints");
	}

	public static boolean hasFood() {
		return Inventory.find(Variables.food.getId()).length > 0;
	}

	public static int getGainedExperience() {
		int exp = 0;
		for (int i = 0; i < Const.SKILL_NAMES.length; i++) {
			exp += Skills.getXP(Const.SKILL_NAMES[i]);
		}
		return exp - gen.getStartingExp();

	}

	public static int BANK_SECTION = 0;

	/**
	 * Withdraws any items from bank if found
	 * 
	 * @param count
	 *            (The amount to withdraw)
	 * @param ids
	 *            (The id as a single integer or an array of integers)
	 * 
	 *            By: Platinum Force Scripts
	 * 
	 **/
	public static void withdraw(int count, int... ids) {

		int section, scrollTo, itemX, itemY;

		for (int i = 0; i < ids.length; i++) {
			RSItem[] items = Banking.find(ids[i]);

			// If the item was not found, skip to next item
			if (items.length == 0)
				continue;

			// "section" is the section of the bank (each section has 6 rows
			// starting from the top)
			// The math is: (item's position in bank) divided by (8 items per
			// row) divided by (6 rows per section)
			// This determines which section of the bank to scroll to to find
			// the item
			section = (int) Math.floor((items[0].getIndex()) / 8.0 / 6.0);

			// "scrollTo" is the pixel (y offset) to scroll to, or to click (On
			// the banks scroll bar)
			scrollTo = (int) Math.round((section * 23.5) + 86);

			// "itemX" is the actual x position of the item after scrolling to
			// it's section in the bank
			itemX = (int) Math.ceil((items[0].getIndex()) % 8);

			// "itemY" is the actual y position of the item after scrolling to
			// it's section in the bank
			itemY = (int) (Math.floor(items[0].getIndex() / 8) % 6);

			// This determines if an item is located in the section of the bank
			// that you already scrolled to.
			// If so, there is no reason to scroll the the same spot again,
			// you're already there!
			// Whenever you close your bank reset "BANK_SECTION" to zero!
			if (section != BANK_SECTION) {
				Mouse.moveBox(469, scrollTo, 481, scrollTo);
				Mouse.click(Mouse.getPos(), 1);
				BANK_SECTION = section;
				Job.sleep(150);
			}

			// Moves the mouse over the item in the bank
			Mouse.move(80 + (itemX * 47) + General.random(7, 22), 59
					+ (itemY * 38) + General.random(13, 25));
			Job.sleep(400);

			// "countBefore" is the current inventory count of the item before
			// you withdraw
			// it is necessary to check if you have succeeded in withdrawing
			// more of that item
			int countBefore = Inventory.getCount(ids[i]);

			if (count == 1)
				Mouse.click(Mouse.getPos(), 1);
			else {
				Mouse.click(Mouse.getPos(), 3);
				Job.sleep(200);

				if (count == 5 || count == 10)
					ChooseOption.select("Withdraw " + count);
				else if (count == 0)
					ChooseOption.select("Withdraw All");
				else {
					ChooseOption.select("Withdraw X");

					/**
					 * Add a wait time below, or a detect if "Enter Amount:" is
					 * visible in Interface(548, 93) Or detect if you can type
					 * in Interface(548, 94), which contains the text "*" I just
					 * added a wait time of 1.5 seconds and a check for null.
					 * But Interface(548, 93) never seems to be null. Hmm...
					 **/

					while (Interfaces.get(548, 93) == null)
						Job.sleep(100);

					Job.sleep(1500);
					Keyboard.typeString(count + "");
					Keyboard.pressEnter();
				}
			}
			Job.sleep(300);

			// If the inventory amount before has not changed for this item, you
			// have failed to withdraw it.
			// It will try again. And if the item isn't found in the bank, it
			// will skip it. So there are
			// no infinite loops
			if (Inventory.getCount(ids[i]) < countBefore)
				i--;
		}
		return;
	}

}
