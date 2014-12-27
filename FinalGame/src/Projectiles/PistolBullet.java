package Projectiles;

import org.flixel.FlxSprite;

import Misc.RandomNumber;
import Misc.Timer;

public class PistolBullet extends Bullet {

	public final static int bulletVelocity = 1000;

	public PistolBullet(int x, int y, int damage, int direction, int spread) {
		super(x, y, damage);

		width = 8;
		height = 4;

	

		if (direction == FlxSprite.RIGHT) {
			loadGraphic("PistolBullet.png");
			velocity.x = bulletVelocity;
		} else if (direction == FlxSprite.LEFT) {
			loadGraphic("PistolBulletLeft.png");
			velocity.x = -bulletVelocity;
		}

		int randomNumber = RandomNumber.generateRandomNumber(-spread, spread);
		velocity.y = randomNumber;

		bulletLife = (int) (5 * Timer.FRAMES_TO_SECONDS);

		knockbackForce = 100;
	}

}
