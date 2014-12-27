package Store.StoreItems;

import Store.StoreItem;

public class HeartUp extends StoreItem {

	// TODO Heart effects in PlayState

	public HeartUp(int x, int y, int price) {
		super(x, y, price);

		width = 32;
		height = 32;

		loadGraphic("StoreItems/Heart.png");

		description = "+1 heart";

	}

	public HeartUp(int x, int y) {
		super(x, y);

		width = 32;
		height = 32;

		loadGraphic("StoreItems/Heart.png");

		description = "+1 heart";
	}
}
