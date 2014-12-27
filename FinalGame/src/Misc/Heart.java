package Misc;

import org.flixel.FlxSprite;

public class Heart extends FlxSprite {

	public static final int FULL_HEART = 0;
	public static final int HALF_HEART = 1;

	public Heart(int x, int y, int heartType) {
		super(x, y);

		if (heartType == FULL_HEART) {
			loadGraphic("FullHeart.png");
		} else if (heartType == HALF_HEART) {
			loadGraphic("HalfHeart.png");
		}

	}
}
