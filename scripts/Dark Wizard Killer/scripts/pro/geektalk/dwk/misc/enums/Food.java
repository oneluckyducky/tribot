package scripts.pro.geektalk.dwk.misc.enums;

public enum Food {
	
	BREAD(2309, "Bread", 5), 
	TROUT(333, "Trout", 7), 
	PIKE(351, "Pike", 8), 
	SALMON(8, "Salmon", 9), 
	TUNA(361, "Tuna", 10), 
	WINE(1993, "Jug of wine", 11), 
	LOBSTER(379, "Lobster", 12), 
	SWORDFISH(373, "Swordfish", 14), 
	MONKFISH(7946, "Monkfish", 16), 
	SHARK(385, "Shark", 20);

	private int id, heal;
	private String name;

	Food(final int id, final String name, final int heal) {
		this.id = id;
		this.name = name;
		this.heal = heal;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public int getHealAmount() {
		return this.heal;
	}

}
