package Store.StoreItems;

import Store.StoreItem;

public class MetalDetector extends StoreItem{

	public MetalDetector(int x, int y) {
		super(x, y);
		
		width = 32;
		height = 32;
		
		loadGraphic("StoreItems/MetalDetector.png");
		
		description = "+1 coin per enemy kill";
	}

	public MetalDetector(int x, int y, int price){
		super(x, y, price);
		
		width = 32;
		height = 32;
		
		loadGraphic("StoreItems/MetalDetector.png");
		
		description = "+1 coin per enemy kill";
	}
}
