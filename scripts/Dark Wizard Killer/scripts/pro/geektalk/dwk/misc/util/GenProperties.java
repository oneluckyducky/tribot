package scripts.pro.geektalk.dwk.misc.util;

public class GenProperties {

	private static int startingExp = 0, foodAmount = 5, eatAt = 20;

	public int getStartingExp() {
		return GenProperties.startingExp;
	}

	public void setStartingExp(final int startingExp) {
		GenProperties.startingExp = startingExp;
	}

	public int getFoodQuantity() {
		return GenProperties.foodAmount;
	}

	public void setFoodQuantity(final int foodAmount) {
		GenProperties.foodAmount = foodAmount <= 28 ? foodAmount : 28;
	}

	public int getEatingHp() {
		return GenProperties.eatAt;
	}

	public void setEatingHp(final int eatAt) {
		GenProperties.eatAt = eatAt <= 99 ? eatAt : 99;
	}
}
