package Misc;

import org.flixel.FlxSprite;

public class Smoke extends FlxSprite {

	public int lifeCounter = 0;
	public int lifeTime = (int) (1 * Timer.FRAMES_TO_SECONDS);

	public Smoke(int x, int y) {
		super(x, y);

		width = 83;
		height = 83;

		loadGraphic("Effects/Smoke.png", true, false, (int) width, (int) height);
		addAnimation("SmokeTrail", new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
				10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 }, 20,
				true);
		play("SmokeTrail");
	}

	public void update() {
		super.update();

		_alpha -= 0.01f;
		if(_alpha <= 0){
			kill();
		}
	}
}
