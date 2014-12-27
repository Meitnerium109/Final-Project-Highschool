package Misc;

import org.flixel.FlxText;

public class FloatyWord extends FlxText {

	Timer timer;

	public FloatyWord(int x, int y, String word) {
		super(x, y, 1000, word, true);
		setFont(null, 30);

		timer = new Timer(9999);
		timer.start();

	}

	public void update() {
		super.update();
		timer.update();

		if (timer.getCurrentFrame() < (int) (0.3 * Timer.FRAMES_TO_SECONDS)) {
			velocity.y = -100;
		} else {
			velocity.y = -5;
			_alpha -= 0.005;
			if (_alpha <= 0) {
				kill();
			}
		}
	}
}
