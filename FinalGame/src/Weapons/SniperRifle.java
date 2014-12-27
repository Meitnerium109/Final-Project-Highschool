package Weapons;

import Misc.Timer;

public class SniperRifle extends Weapon {

	public SniperRifle() {
		super();

		lowDamage = 70;
		highDamage = 110;

		animationFrames = 10;
		
		canAttack = true;
		
		cooldownTime = 2 * Timer.FRAMES_TO_SECONDS;
		cooldownTimer.setTotalFrames(cooldownTime);
		
		spread = 0;

		recoil = 200;
		
		loadGraphic("SniperRifleIcon.png");

	}

}
