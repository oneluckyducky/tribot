package scripts.pathgen;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;

public class Gui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String pathPre = "public static final RSTile[] MY_PATH = new RSTile[] { ";
	private static final String pathPost = " };";

	private static final String areaPre = "public static final RSArea MY_AREA = new RSArea( ";
	private static final String areaPost = " );";

	private static String tileReplacer = "new RSTile(x, y, z), ";

	public static ArrayList<RSTile> tiles = new ArrayList<RSTile>();

	public Gui() {
		init();
	}

	public JButton add = new JButton("Add");
	public JButton remove = new JButton("Remove");
	public JButton generate = new JButton("Generate");
	public JCheckBox chkArea = new JCheckBox("Area");
	public JTextArea content = new JTextArea();
	public JScrollPane sp;

	public void init() {

		this.setSize(316, 298);
		this.setAlwaysOnTop(true);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		chkArea.setBounds(200, 0, 60, 20);

		add.setBounds(10, 20, 80, 25);
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (Player.getRSPlayer() != null)
					add(Player.getRSPlayer().getPosition());
				else
					return;

			}
		});

		remove.setBounds(add.getX() + add.getWidth() + 15, add.getY(), 80,
				add.getHeight());
		remove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (Player.getRSPlayer() != null)
					remove(Player.getRSPlayer().getPosition());
				else
					return;
			}

		});

		generate.setBounds(remove.getX() + remove.getWidth() + 15,
				remove.getY(), remove.getWidth() + 5, 25);

		generate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final Gui me = PathGenerator.gui;
				if (me.chkArea.isSelected()) {
					System.out.println("area");
					generate(tiles, Gui.areaPre, Gui.areaPost);
				} else {
					System.out.println("!area");
					generate(tiles, Gui.pathPre, Gui.pathPost);
				}

			}

		});

		content.setBounds(add.getX(), add.getY() + add.getHeight() + 15,
				this.getWidth() - 30,
				this.getHeight() - add.getHeight() - add.getY() - 55);
		content.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		content.setWrapStyleWord(true);
		content.setLineWrap(true);

		sp = new JScrollPane(content);
		sp.setBounds(content.getBounds());

		add(chkArea);
		add(add);
		add(remove);
		add(generate);
		add(sp);

		this.setResizable(true);
		this.setVisible(true);
	}

	public void remove(final RSTile t) {

		if (tiles.contains(t))
			tiles.remove(t);
		this.setTitle("Removed " + t.toString());
	}

	public void add(final RSTile t) {
		if (!tiles.contains(t))
			tiles.add(t);
		this.setTitle("Added " + t.toString());
	}

	public void generate(final ArrayList<RSTile> tlist, final String pre,
			final String post) {
		String all = "";
		all = all.concat(pre);
		for (int i = 0; i < tlist.size() - 1; i++) {
			int x = tlist.get(i).getX(), y = tlist.get(i).getY(), z = tlist
					.get(i).getPlane();
			if (i == tlist.size() - 1) {
				tileReplacer = tileReplacer.replace("),", ")");
			}
			all = all.concat(tileReplacer.replace("x", String.valueOf(x))
					.replace("y", String.valueOf(y))
					.replace("z", String.valueOf(z)));
		}
		all = all.concat(post);
		if (all.contains(", }")) {
			System.out.println("Error");
			all = all.replace("),  }", ") }");
			System.out.println(all);
			this.content.setText(all);
		} else {
			System.out.println(all);
			this.content.setText(all);
		}
	}
}