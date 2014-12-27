package Projectiles;

import org.flixel.FlxSprite;

import Entities.Entity;
import Misc.Timer;

public class Bullet extends Entity {

	public static int bulletLifeCounter;
	public static int bulletLife;
	public int damage;
	public int knockbackForce = 0;
	
	public Bullet(int x, int y, int damage) {
		super(x, y);

		this.damage = damage;
		
		bulletLifeCounter = 0;
		bulletLife = 5 * Timer.FRAMES_TO_SECONDS;
	}

	public int getDamage() {
		return damage;
	}

	public void update() {
		super.update();

		if(velocity.x < 0){
			setFacing(FlxSprite.LEFT);
		} else {
			setFacing(FlxSprite.RIGHT);
		}
		
		// Each bullet only has a certain amount of distance it can travel
		// before it dies

		if (bulletLifeCounter < bulletLife) {
			bulletLifeCounter++;
		} else {
			kill();
		}
	}
}
