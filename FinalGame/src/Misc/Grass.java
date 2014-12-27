package Misc;

import org.flixel.FlxSprite;

public class Grass extends FlxSprite {

	public Grass(int x, int y, int type) {
		super(x, y);

		switch (type) {
		case 1:
			loadGraphic("Grass1.png");
			break;
		case 2:
			loadGraphic("Grass2.png");
			break;
		default:
			loadGraphic("Grass1.png");
			break;
		}
	}
}
