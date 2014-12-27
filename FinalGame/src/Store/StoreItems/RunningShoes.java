package Store.StoreItems;

import Store.StoreItem;

public class RunningShoes extends StoreItem {

	public RunningShoes(int x, int y, int price) {
		super(x, y, price);

		width = 32;
		height = 32;

		loadGraphic("StoreItems/RunningShoes.png");

		description = "+10 running speed";
	}

	public RunningShoes(int x, int y) {
		super(x, y);

		width = 32;
		height = 32;

		loadGraphic("StoreItems/RunningShoes.png");

		description = "+10 running speed";
	}
}
