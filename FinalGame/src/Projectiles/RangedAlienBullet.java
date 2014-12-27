package Projectiles;

import org.flixel.FlxSprite;

import Misc.RandomNumber;
import Misc.Timer;

public class RangedAlienBullet extends EnemyBullet {

	public final static int bulletVelocity = 200;
	public int spread = 50;

	public RangedAlienBullet(int x, int y, int damage, int direction) {
		super(x, y, damage);

		width = 10;
		height = 3;

		makeGraphic((int) width, (int) height, 0xFF00FF00);

		if (direction == FlxSprite.RIGHT) {
			velocity.x = bulletVelocity;
		} else {
			velocity.x = -bulletVelocity;
		}

		//int randomNumber = RandomNumber.generateRandomNumber(-spread, spread);
		//velocity.y = randomNumber;

		bulletLife = (int) (10 * Timer.FRAMES_TO_SECONDS);
		knockbackForce = 100;
	}
	
}
