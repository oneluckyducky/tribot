package scripts.pro.geektalk.dwk.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.tribot.api2007.Skills;

import scripts.pro.geektalk.dwk.DarkWizardKiller;
import scripts.pro.geektalk.dwk.misc.Const;
import scripts.pro.geektalk.dwk.misc.Variables;
import scripts.pro.geektalk.dwk.misc.enums.Food;
import scripts.pro.geektalk.dwk.misc.enums.Rune;
import scripts.pro.geektalk.dwk.misc.util.GenProperties;

public class Gui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static JButton start;
	static JList<String> runeList;
	static JComboBox<String> foodList;
	static JCheckBox level20, bank;
	static JNumberField foodAmount, hpToEat;

	private static final Dimension size = new Dimension(347, 250);
	static Vector<String> runes = new Vector<String>();
	static Vector<String> foods = new Vector<String>();
	static GenProperties props = new GenProperties();

	static {
		for (Rune rune : Rune.values()) {
			runes.add(rune.getName());
		}
		for (Food food : Food.values()) {
			foods.add(food.getName());
		}
		start = new JButton("Start");
		runeList = new JList<String>(runes);
		foodList = new JComboBox<String>(foods);
		level20 = new JCheckBox("lvl 20s");
		bank = new JCheckBox("Banking");
		foodAmount = new JNumberField("Food quantity");
		hpToEat = new JNumberField("Hp to eat at");
	}

	public Gui() {

		this.setLayout(null);
		this.setPreferredSize(size);
		this.setMaximumSize(size);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Dark Wizard Killer");

		start.setBounds(230, 8, 80, 25);
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				start();
			}

		});
		add(start);

		runeList.setBounds(10, 10, 120, 200);
		runeList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		// runeList.setSelectedIndices(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
		// 10, 11, 12, 13 });

		JScrollPane sp = new JScrollPane(runeList, 20, 31);
		sp.setBounds(runeList.getBounds());
		add(sp);

		hpToEat.setBounds(sp.getX() + sp.getWidth() + 10, start.getY(), 80, 25);
		this.setMarqueeText(hpToEat, hpToEat.getText());
		add(hpToEat);

		foodList.setBounds(hpToEat.getX(), hpToEat.getY() + hpToEat.getHeight()
				+ 20, 100, 25);
		add(foodList);

		level20.setBounds(foodList.getX(),
				foodList.getY() + foodList.getHeight() + 20, 60, 25);
		level20.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		add(level20);

		bank.setBounds(hpToEat.getX(), level20.getY() + level20.getHeight()
				+ 10, 80, 25);
		add(bank);

		foodAmount.setBounds(bank.getX() + 30, bank.getY() + bank.getHeight()
				+ 5, 80, 25);
		this.setMarqueeText(foodAmount, foodAmount.getText());
		add(foodAmount);

		pack();
		this.setVisible(true);
	}

	public void start() {
		DarkWizardKiller d = new DarkWizardKiller();
		d.log("Starting");
		for (int i : runeList.getSelectedIndices()) {
			Variables.lootList.add(Rune.values()[i].getId());
		}
		int xp = 0;
		for (int i = 0; i < Const.SKILL_NAMES.length; i++) {
			xp += Skills.getXP(Const.SKILL_NAMES[i]);
		}
		d.log(xp);

		Variables.banking = bank.isSelected();
		Variables.food = Food.values()[foodList.getSelectedIndex()];

		String amount = foodAmount.getText();
		props.setFoodQuantity(!amount.isEmpty()
				&& !amount.equalsIgnoreCase("Food quantity") ? Integer
				.parseInt(foodAmount.getText()) : 5);

		d.log(props.getFoodQuantity());

		String to = hpToEat.getText();
		props.setEatingHp(!to.isEmpty() && !to.equalsIgnoreCase("Hp to eat at") ? Integer
				.parseInt(hpToEat.getText()) : 25);

		d.log(props.getEatingHp());

		Variables.lootList.add(886);

		Variables.levels.add(4659);
		Variables.levels.add(4658);
		props.setStartingExp(xp);

		if (level20.isSelected()) {
			Variables.levels.add(4660);
			Variables.levels.add(4661);
		}
		// d.log(Variables.levels.size());
		DarkWizardKiller.run = true;
		// d.log("dwk run " + DWK.run);
		this.dispose();
	}

	public void setMarqueeText(final JTextField jtf, final String condition) {
		final String starting = !jtf.getText().isEmpty() ? jtf.getText() : "";
		jtf.addFocusListener(new FocusAdapter() {
			public void focusGained(final FocusEvent e) {
				String current = jtf.getText();
				if (current.equals(condition)) {
					jtf.setText("");
				}
			}

			public void focusLost(final FocusEvent e) {
				if (jtf.getText().isEmpty()) {
					jtf.setForeground(Color.DARK_GRAY);
					jtf.setText(starting);
				}
			}
		});
	}

}
