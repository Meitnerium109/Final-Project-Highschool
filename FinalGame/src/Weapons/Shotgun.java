package Weapons;

import Misc.Timer;

public class Shotgun extends Weapon {

	/**
	 * The amount of spread that the shotgun bullets have
	 */
	
	public int numPellets = 8;

	public Shotgun() {
		super();

		/**
		 * Damage per pellet, 5 pellets?
		 */

		lowDamage = 5;
		highDamage = 15;

		animationFrames = 4 * (int) (Timer.FRAMES_TO_SECONDS / 7.0);
		canAttack = true;

		cooldownTime = (int) (1.5 * Timer.FRAMES_TO_SECONDS);
		cooldownTimer.setTotalFrames(cooldownTime);

		spread = 200;
		
		recoil = 1500;
		
		loadGraphic("ShotgunSprite.png");
	}

	public void setNumPellets(int numPellets) {
		this.numPellets = numPellets;
	}
}
