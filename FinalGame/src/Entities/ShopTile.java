package Entities;

import org.flixel.FlxSprite;

public class ShopTile extends FlxSprite {

	public ShopTile(int x, int y){
		this.x = x;
		this.y = y;
		
		width = 32;
		height = 32;
		
		loadGraphic("Shop3.png");
		
	}
}
