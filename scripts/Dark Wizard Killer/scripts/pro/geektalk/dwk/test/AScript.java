package scripts.pro.geektalk.dwk.test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.tribot.script.Script;

import scripts.pro.geektalk.dwk.Job;

public abstract class AScript extends Script {

	public static int loop = 100;

	private final static Set<Job> queue = Collections
			.synchronizedSet(new HashSet<Job>());

	public boolean run = true;

	public abstract int loop();

	public void sleep(final long ms) {
		try {
			Thread.sleep(ms);
		} catch (Exception e) {
			e.printStackTrace();
		}
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

}
