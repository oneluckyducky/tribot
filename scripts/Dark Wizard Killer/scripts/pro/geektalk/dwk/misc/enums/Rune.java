package scripts.pro.geektalk.dwk.misc.enums;

public enum Rune {
	FIRE(554, "Fire rune"), 
	WATER(555, "Water rune"), 
	AIR(556, "Air rune"), 
	EARTH(557, "Earth rune"), 
	MIND(558, "Mind rune"), 
	BODY(559, "Body rune"), 
	DEATH(560, "Death rune"), 
	NATURE(561, "Nature rune"), 
	CHAOS(562,"Chaos rune"), 
	LAW(563, "Law rune"),
	COSMIC(564, "Cosmic rune"), 
	BLOOD(565, "Blood rune"), 
	SOUL(566, "Soul rune"), 
	ASTRAL(9075, "Astral rune");

	private int id;
	private String name;

	Rune(final int id, final String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

}