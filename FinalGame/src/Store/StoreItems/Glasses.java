package Store.StoreItems;

import Store.StoreItem;

/**
 * 
 * @author Andrew Lau
 * 
 */
public class Glasses extends StoreItem {

	public Glasses(int x, int y, int price) {
		super(x, y, price);
	}

	public Glasses(int x, int y) {
		super(x, y);

		width = 32;
		height = 32;
	}

}
