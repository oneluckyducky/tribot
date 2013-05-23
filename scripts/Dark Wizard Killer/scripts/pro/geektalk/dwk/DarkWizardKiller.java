package scripts.pro.geektalk.dwk;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.tribot.api.input.Mouse;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Ending;
import org.tribot.script.interfaces.Painting;

import scripts.pro.geektalk.dwk.gui.Gui;
import scripts.pro.geektalk.dwk.jobs.Action;
import scripts.pro.geektalk.dwk.jobs.Bank;
import scripts.pro.geektalk.dwk.jobs.BankWalk;
import scripts.pro.geektalk.dwk.jobs.CircleWalk;
import scripts.pro.geektalk.dwk.jobs.Eating;
import scripts.pro.geektalk.dwk.misc.Const;
import scripts.pro.geektalk.dwk.misc.Methods;
import scripts.pro.geektalk.dwk.misc.Variables;
import scripts.pro.geektalk.dwk.misc.util.GenProperties;
import scripts.pro.geektalk.dwk.misc.util.Random;

@ScriptManifest(authors = { "OneLuckyDuck" }, category = "Combat", name = "Dark Wizard Killer", version = DarkWizardKiller.version, description = "<html> <head> </head> <body bgcolor=black><center> <h2 style=color:#999999><i>Dark Wizard Killer</i></h2> <p style=color:white><i>by OneLuckyDuck</i></p></center> <p style=color:white>Kills dark wizards in varrocks.</p><br /> <p style=color:#999999><b>Features</b></p> <ul style=color:#999999 title= > <li>Banking support</li> <li>Customize looting and food options with prebuilt choices</li> <li>Prebuilt foods</li> <li>Prebuilt looting</li> <li>Fight level 7 only or both level 7 and 20!</li> </ul> </body> </html> ")
public class DarkWizardKiller extends Script implements Painting, Ending {

	Gui gui;
	public static final double version = 0.3;
	public static boolean run = false;
	public static int loop = 100;

	private final static Set<Job> queue = Collections
			.synchronizedSet(new HashSet<Job>());

	GenProperties gen = new GenProperties();

	public int loop() {
		try {
			if (!queue.isEmpty() && run) {
				for (Job job : queue) {
					if (job.canRun()) {
						job.run();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loop;
	}

	public void log(final Object obj) {
		println("[DWK] " + obj);
	}

	public static synchronized final void add(Job... jobs) {
		if (jobs == null)
			return;
		for (Job job : jobs) {
			if (!queue.contains(job))
				queue.add(job);

		}
	}

	public static synchronized final void remove(Job... jobs) {
		if (jobs == null)
			return;
		for (Job job : jobs) {
			if (queue.contains(job))
				queue.remove(job);
		}
	}

	public void onStart() {
		this.setRandomSolverState(true);
		this.setLoginBotState(true);
		log(version);
		Mouse.setSpeed(Random.nextInt(150, 195));
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				gui = new Gui();
			}
		});
		while (!run) {
			sleep(100);
		}
		DarkWizardKiller.add(new Action(), new BankWalk(), new CircleWalk(),
				new Bank(), new Eating());
	}

	@Override
	public void run() {
		onStart();
		while (run) {
			sleep(this.loop());
		}
	}

	@Override
	public void onPaint(Graphics g1) {
		final Graphics2D g = (Graphics2D) g1;
		g.drawString("Time running: " + Const.TIMER.toElapsedString(), 10, 50);
		g.drawString("Status: " + Variables.status, 10, 70);
		g.drawString("Kills: " + Variables.kills, 10, 90);
		g.drawString("Experience Gained: " + Methods.getGainedExperience(), 10,
				110);

	}

	@Override
	public void onEnd() {
		if (gui.isVisible())
			gui.dispose();

	}
}
