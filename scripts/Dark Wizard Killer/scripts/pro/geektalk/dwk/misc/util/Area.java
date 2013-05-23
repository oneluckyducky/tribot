package scripts.pro.geektalk.dwk.misc.util;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.tribot.api.types.generic.Filter;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.api2007.types.RSTile;

/**
 * Created with IntelliJ IDEA. User: Ian Date: 4/11/13 Time: 12:50 PM
 */
public class Area {
	protected final Polygon polygon;
	@SuppressWarnings("unused")
	private final Filter<RSPlayer> IN_AREA = new Filter<RSPlayer>() {
		@Override
		public boolean accept(RSPlayer rsPlayer) {
			return rsPlayer != null
					&& (Player.getRSPlayer() == null || rsPlayer.getName()
							.equals(Player.getRSPlayer().getName()))
					&& contains(rsPlayer);
		}
	};
	protected int plane = -1;
	private RSTile[] tiles = null;

	public Area(final RSTile t1, final RSTile t2) {
		this(new RSTile(Math.min(t1.getX(), t2.getX()), Math.min(t1.getY(),
				t2.getY()), t1.getPlane()), new RSTile(Math.max(t1.getX(),
				t2.getX()), Math.min(t1.getY(), t2.getY()), t1.getPlane()),
				new RSTile(Math.max(t1.getX(), t2.getX()), Math.max(t1.getY(),
						t2.getY()), t2.getPlane()), new RSTile(Math.min(
						t1.getX(), t2.getX()), Math.max(t1.getY(), t2.getY()),
						t2.getPlane()));
	}

	public Area(final RSTile... bounds) {
		polygon = new Polygon();
		for (final RSTile RSTile : bounds) {
			if (plane != -1 && RSTile.getPlane() != plane) {
				throw new RuntimeException("RSArea is not multi-planed");
			}
			plane = RSTile.getPlane();
			addTile(RSTile);
		}
	}

	public void translate(final int x, final int y) {
		polygon.translate(x, y);
		tiles = null;
	}

	public Rectangle getBounds() {
		return polygon.getBounds();
	}

	public int getPlane() {
		return plane;
	}

	public void addTile(final RSTile t) {
		addTile(t.getX(), t.getY());
	}

	public void addTile(final int x, final int y) {
		polygon.addPoint(x, y);
		tiles = null;
	}

	public boolean contains(final int x, final int y) {
		return polygon.contains(x, y);
	}

	public boolean contains(final RSPlayer... players) {
		for (final RSPlayer p : players) {
			if (p == null) {
				continue;
			}
			final RSTile RSTile = p.getPosition();
			if (RSTile != null && plane == RSTile.getPlane()
					&& contains(RSTile.getX(), RSTile.getY())) {
				return true;
			}
		}
		return false;
	}

	public boolean contains(final RSCharacter... chars) {
		for (final RSCharacter p : chars) {
			if (p == null) {
				continue;
			}
			final RSTile RSTile = p.getPosition();
			if (RSTile != null && plane == RSTile.getPlane()
					&& contains(RSTile.getX(), RSTile.getY())) {
				return true;
			}
		}
		return false;
	}

	public void draw(Graphics2D g) {
		draw(g, false);
	}

	public void draw(Graphics2D g, boolean checkReach) {
		for (RSTile t : getTileArray())
			if (t != null && t.isOnScreen()
					&& (!checkReach || PathFinding.canReach(t, false)))
				g.draw(Projection.getTileBoundsPoly(t, 0));
	}

	public boolean containsAll(final RSPlayer... locatables) {
		for (final RSPlayer loc : locatables) {
			if (loc == null || !contains(loc)) {
				return false;
			}
		}
		return true;
	}

	public RSTile getCentralTile() {
		return polygon.npoints > 0 ? new RSTile(
				(int) Math.round(avg(polygon.xpoints)),
				(int) Math.round(avg(polygon.ypoints)), plane) : null;
	}

	public RSTile getNearest() {
		return getNearest(Player.getRSPlayer());
	}

	public RSTile getNearest(final RSPlayer base) {
		final RSTile[] tiles = getTileArray();
		RSTile RSTile = null;
		long dist = Long.MAX_VALUE, temp;
		for (final RSTile t : tiles) {
			temp = (long) base.getPosition().distanceTo(t);
			if (t == null || temp < dist) {
				dist = temp;
				RSTile = t;
			}
		}
		return RSTile;
	}

	public RSTile[] getBoundingTiles() {
		final RSTile[] bounding = new RSTile[polygon.npoints];
		for (int i = 0; i < polygon.npoints; i++) {
			bounding[i] = new RSTile(polygon.xpoints[i], polygon.ypoints[i],
					plane);
		}
		return bounding;
	}

	public RSTile[] getTileArray() {
		if (tiles == null) {
			final Rectangle bounds = getBounds();
			final ArrayList<RSTile> tileSet = new ArrayList<>(bounds.width
					* bounds.height);
			final int xMax = bounds.x + bounds.width, yMax = bounds.y
					+ bounds.height;
			for (int x = bounds.x; x < xMax; x++) {
				for (int y = bounds.y; y < yMax; y++) {
					if (contains(x, y)) {
						tileSet.add(new RSTile(x, y, plane));
					}
				}
			}
			tiles = tileSet.toArray(new RSTile[tileSet.size()]);
		}
		return tiles;
	}

	private double avg(final int... nums) {
		long total = 0;
		for (int i : nums) {
			total += (long) i;
		}
		return (double) total / (double) nums.length;
	}
}