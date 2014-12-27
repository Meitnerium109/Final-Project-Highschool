package Projectiles;

import org.flixel.FlxSprite;

import Misc.RandomNumber;
import Misc.Timer;

public class ShotgunBullet extends Bullet {

	public static int bulletVelocity = 900;

	public ShotgunBullet(int x, int y, int damage, int direction, int spread) {
		super(x, y, damage);

		width = 5;
		height = 3;

		

		if (direction == FlxSprite.RIGHT) {
			loadGraphic("PistolBullet.png");
			velocity.x = bulletVelocity
					+ RandomNumber.generateRandomNumber(-50, 50);
		} else if (direction == FlxSprite.LEFT) {
			loadGraphic("PistolBulletLeft.png");
			velocity.x = -bulletVelocity
					+ RandomNumber.generateRandomNumber(-50, 50);
		}

		int randomNumber = RandomNumber.generateRandomNumber(-spread, spread);

		velocity.y = randomNumber;

		bulletLife = (int) (1 * Timer.FRAMES_TO_SECONDS);

		knockbackForce = 250;
	}

}
