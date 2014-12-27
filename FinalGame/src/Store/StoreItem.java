package Store;

import org.flixel.FlxSprite;
import org.flixel.FlxText;

public class StoreItem extends FlxSprite {

	/**
	 * The default price of the item if no price is inputted
	 */
	public int defaultPrice = 100;
	
	public int price;
	public String description;
	
	/**
	 * The price text is stored in the thing, You have to add the price to the
	 * group for this to work
	 */
	public String priceText;
	public FlxText thingText;
	
	public StoreItem(int x, int y, int price) {
		super(x, y);

		// Width and Height of store items for now, idk
		width = 32;
		height = 32;

		// Load a graphic for each item in here
		makeGraphic((int) width, (int) height, 0xFFFF0000);

		immovable = true;

		this.price = price;
	}

	public StoreItem(int x, int y){
		super(x, y);
		
		width = 32;
		height = 32;
		
		makeGraphic((int)width, (int)height, 0xFFFF0000);
		immovable = true;
		price = defaultPrice;
	}
	public FlxText createPrice(int x, int y, int fontSize) {
		FlxText text = new FlxText(x, y, 32, "$" + price);
		text.setAlignment("center");
		text.setFormat(null, fontSize);
		thingText = text;
		
		return text;
	}
	
	public void kill(){
		thingText.kill();
		super.kill();
	}

}
