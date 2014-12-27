package Weapons;

import Misc.Timer;

public class GrenadeLauncher extends Weapon {

	public GrenadeLauncher() {
		super();

		lowDamage = 30;
		highDamage = 50;

		animationFrames = 10;
		canAttack = true;
		cooldownTime = (int) (1 * Timer.FRAMES_TO_SECONDS);
		cooldownTimer.setTotalFrames(cooldownTime);
		spread = 0;
		recoil = 1400;

		makeGraphic(32, 32, 0xFF00FF00);

	}
}
