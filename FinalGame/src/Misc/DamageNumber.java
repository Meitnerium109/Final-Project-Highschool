package Misc;

import org.flixel.FlxText;

/**
 * Spawns, moves up fast, and then fades out. The timer must be added to the
 * update sequence.
 * 
 * @author Andrew Lau
 * 
 */
public class DamageNumber extends FlxText {

	Timer timer;

	public DamageNumber(int x, int y, int number, boolean critical) {
		super(x, y, 1000, String.valueOf(number));
		timer = new Timer(9999);
		timer.start();
	
		if(critical){
			setColor(0xFFBB0000);
			setSize(12f);
		} else {
			setSize(10f);
		}
	}

	public void update() {
		super.update();
		timer.update();
		
		if (timer.getCurrentFrame() < (int) (0.3 * Timer.FRAMES_TO_SECONDS)) {
			velocity.y = -100;
		} else {
			velocity.y = -10;
			_alpha -= 0.01;
			if (_alpha <= 0) {
				kill();
			}
		}

	}

}
