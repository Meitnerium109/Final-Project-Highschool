package Projectiles;

import Misc.RandomNumber;

/**
 * Explosion that usually lasts for one frame and damages everything around it
 * 
 * @author Andrew Lau
 * 
 */
public class Explosion extends Bullet {

	public int dieCounter = 0;
	public int dieTime;
	public int lowRange;
	public int highRange;

	public Explosion(int x, int y, int size, int lowRange, int highRange,
			int numFrames) {
		super(x, y, 0);

		width = size;
		height = size;

		makeGraphic(size, size, 0x00FF0000);

		dieTime = numFrames;

		this.damage = damage;
		this.lowRange = lowRange;
		this.highRange = highRange;
	}

	public int dealDamage() {
		return RandomNumber.generateRandomNumber(lowRange, highRange);
	}

	public void update() {
		super.update();

		if (dieCounter < dieTime) {
			dieCounter++;
		} else {
			kill();
		}

	}

}
