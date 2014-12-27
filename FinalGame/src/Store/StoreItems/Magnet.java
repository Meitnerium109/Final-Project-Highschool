package Store.StoreItems;

import Store.StoreItem;

public class Magnet extends StoreItem {

	public Magnet(int x, int y, int price) {
		super(x, y, price);

		width = 32;
		height = 32;

		loadGraphic("StoreItems/Magnet.png");
		
		description = "attracts coins";
	
	}
	
	public Magnet(int x, int y) {
		super(x, y);

		width = 32;
		height = 32;

		loadGraphic("StoreItems/Magnet.png");
		
		description = "attracts coins";
	}

}
