package Store.StoreItems;

import Store.StoreItem;

public class JumpingShoes extends StoreItem {

	public JumpingShoes(int x, int y, int price){
		super(x, y, price);
		
		width = 32;
		height = 32;
		
		loadGraphic("StoreItems/JumpingShoes.png");
		
		description = "+10 jump power";
	}
	
	public JumpingShoes(int x, int y){
		super(x, y);
		
		width = 32;
		height = 32;
		
		loadGraphic("StoreItems/JumpingShoes.png");
		
		description = "+10 jump power";
	}
}
